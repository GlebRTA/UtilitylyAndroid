package com.gvituskins.utilityly.presentation.screens.main.utilities.manageUtility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.domain.models.categories.Category
import com.gvituskins.utilityly.domain.models.companies.Company
import com.gvituskins.utilityly.domain.repositories.CategoryRepository
import com.gvituskins.utilityly.domain.repositories.CompanyRepository
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.DropDownTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ManageUtilityViewModel @Inject constructor(
    categoryRepository: CategoryRepository,
    companyRepository: CompanyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManageUtilityState())
    val uiState = _uiState.asStateFlow()

    init {
        categoryRepository.getAllCategories()
            .onEach { categories ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(categories = categories)
                }
                uiState.value.categoryDropState.updateOptions(categories.map { it.name })
            }
            .launchIn(viewModelScope)

        companyRepository.getAllCompanies()
            .onEach { companies ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(companies = companies)
                }
                uiState.value.companyDropState.updateOptions(companies.map { it.name })
            }
            .launchIn(viewModelScope)

    }

}

data class ManageUtilityState(
    val categories: List<Category> = listOf(),
    val categoryDropState: DropDownTextFieldState = DropDownTextFieldState(initialValue = "", listOf()),

    val companies: List<Company> = listOf(),
    val companyDropState: DropDownTextFieldState = DropDownTextFieldState(initialValue = "", listOf()),
)
