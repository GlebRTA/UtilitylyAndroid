package com.gvituskins.utilityly.presentation.screens.main.utilities.manageUtility

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.gvituskins.utilityly.data.preferences.DataStoreUtil
import com.gvituskins.utilityly.domain.models.categories.Category
import com.gvituskins.utilityly.domain.models.categories.CategoryParameter
import com.gvituskins.utilityly.domain.models.companies.Company
import com.gvituskins.utilityly.domain.models.enums.PaidStatus
import com.gvituskins.utilityly.domain.models.enums.UtilityRepeat
import com.gvituskins.utilityly.domain.models.locations.Location
import com.gvituskins.utilityly.domain.models.utilities.Utility
import com.gvituskins.utilityly.domain.repositories.CategoryRepository
import com.gvituskins.utilityly.domain.repositories.CompanyRepository
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.DropDownTextFieldState
import com.gvituskins.utilityly.presentation.navigation.graphs.UtilitiesNavGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ManageUtilityViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    categoryRepository: CategoryRepository,
    companyRepository: CompanyRepository,
    private val utilityRepository: UtilityRepository,
    private val preferences: DataStoreUtil
) : ViewModel() {
    private val manageUtility = savedStateHandle.toRoute<UtilitiesNavGraph.ManageUtility>()

    private val _uiState = MutableStateFlow(ManageUtilityState())
    val uiState = _uiState.asStateFlow()

    private val _label = Channel<ManageUtilityOneTime>()
    val label = _label.receiveAsFlow()

    init {
        manageUtility.utilityId?.let { initUtilityId ->
            viewModelScope.launch {
                val initUtility = utilityRepository.getUtilityById(initUtilityId)
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        editUtility = initUtility,
                        dueDate = initUtility.dueDate.time,
                        repeat = initUtility.repeat,
                    )
                }

                uiState.value.categoryDropState.updateValue(initUtility.category)
                initUtility.company?.let { uiState.value.companyDropState.updateValue(it) }

                uiState.value.amount.setTextAndPlaceCursorAtEnd(initUtility.amount.toString())
            }
        }

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

    fun manageUtility() {
        viewModelScope.launch {
            if (uiState.value.isAddMode) addUtility() else editUtility()
            _label.send(ManageUtilityOneTime.NavigateBack)
        }
    }

    private suspend fun addUtility() {
        val category = uiState.value.categoryDropState.value ?: return
        val company = uiState.value.companyDropState.value

        utilityRepository.addNewUtility(
            Utility(
                id = 0,
                category = category,
                company = company,
                repeat = uiState.value.repeat,
                amount = uiState.value.amount.text.toString().toDoubleOrNull() ?: 0.0,
                location = Location(id = preferences.getCurrentLocationId(), name = ""),
                dateCreated = Date(),
                paidStatus = PaidStatus.UNPAID,
                dueDate = uiState.value.dueDate?.let { Date(it) } ?: Date(),
                datePaid = null,
                previousUtilityId = utilityRepository.getPreviousUtility(category.id)?.id
            )
        )
    }

    private suspend fun editUtility() {
        val editUtility = uiState.value.editUtility ?: return
        val category = uiState.value.categoryDropState.value ?: return
        val company = uiState.value.companyDropState.value

        utilityRepository.updateUtility(
            editUtility.copy(
                category = category,
                company = company,
                repeat = uiState.value.repeat,
                amount = uiState.value.amount.text.toString().toDoubleOrNull() ?: 0.0,
                dueDate = uiState.value.dueDate?.let { Date(it) } ?: Date(),
            )
        )
    }

}

sealed interface ManageUtilityOneTime {
    data object NavigateBack : ManageUtilityOneTime
}

data class ManageUtilityState(
    val editUtility: Utility? = null,

    val categoryDropState: DropDownTextFieldState<Category?> = DropDownTextFieldState(initialValue = null, listOf()),
    val categoryParameters: List<CategoryParameter> = listOf(),

    val companyDropState: DropDownTextFieldState<Company?> = DropDownTextFieldState(initialValue = null, listOf()),

    val dueDate: Long? = null,
    val repeat: UtilityRepeat? = null,
    val amount: TextFieldState = TextFieldState()
) {
    val isAddMode: Boolean
        get() = editUtility == null
}
