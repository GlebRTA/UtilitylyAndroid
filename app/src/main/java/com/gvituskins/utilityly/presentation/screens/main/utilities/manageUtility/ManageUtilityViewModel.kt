package com.gvituskins.utilityly.presentation.screens.main.utilities.manageUtility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.data.preferences.DataStoreUtil
import com.gvituskins.utilityly.domain.models.categories.Category
import com.gvituskins.utilityly.domain.models.categories.CategoryParameter
import com.gvituskins.utilityly.domain.models.companies.Company
import com.gvituskins.utilityly.domain.models.enums.PaidStatus
import com.gvituskins.utilityly.domain.models.enums.UtilityRepeat
import com.gvituskins.utilityly.domain.models.utilities.Utility
import com.gvituskins.utilityly.domain.repositories.CategoryRepository
import com.gvituskins.utilityly.domain.repositories.CompanyRepository
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.DropDownTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ManageUtilityViewModel @Inject constructor(
    categoryRepository: CategoryRepository,
    companyRepository: CompanyRepository,
    private val utilityRepository: UtilityRepository,
    private val preferences: DataStoreUtil
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManageUtilityState())
    val uiState = _uiState.asStateFlow()

    init {
        categoryRepository.getAllCategories()
            .onEach { categories ->
                uiState.value.categoryDropState.updateOptions(categories)
            }
            .launchIn(viewModelScope)

        companyRepository.getAllCompanies()
            .onEach { companies ->
                uiState.value.companyDropState.updateOptions(companies)
            }
            .launchIn(viewModelScope)

    }

    fun updateCategoryParameters(category: Category?) {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                categoryParameters = category?.parameters ?: listOf()
            )
        }
    }

    fun updateDueDate(newValue: Long?) {
        _uiState.update { currentUiState ->
            currentUiState.copy(dueDate = newValue)
        }
    }

    fun updateRepeat(newRepeat: UtilityRepeat?) {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                repeat = newRepeat.takeIf { it != uiState.value.repeat}
            )
        }
    }

    fun addUtility() {
        val categoryId = uiState.value.categoryDropState.value?.id ?: return
        val companyId = uiState.value.companyDropState.value?.id ?: return

        viewModelScope.launch {
            utilityRepository.addNewUtility(
                Utility(
                    id = 0,
                    categoryId = categoryId,
                    companyId = companyId,
                    repeat = uiState.value.repeat,
                    amount = uiState.value.amountToPay,
                    locationId = preferences.getCurrentLocationId(),
                    dateCreated = Date(),
                    paidStatus = PaidStatus.UNPAID,
                    dueDate = uiState.value.dueDate?.let { Date(it) },
                    datePaid = null,
                    previousUtilityId = utilityRepository.getPreviousUtility(categoryId)?.id
                )
            )
        }
    }

}

data class ManageUtilityState(
    val categoryParameters: List<CategoryParameter> = listOf(),
    val categoryDropState: DropDownTextFieldState<Category?> = DropDownTextFieldState(initialValue = null, listOf()),

    val companyDropState: DropDownTextFieldState<Company?> = DropDownTextFieldState(initialValue = null, listOf()),

    val dueDate: Long? = null,
    val repeat: UtilityRepeat? = null,
    val amountToPay: Double = 0.0,
)
