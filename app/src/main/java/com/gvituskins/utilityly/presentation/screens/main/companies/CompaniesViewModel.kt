package com.gvituskins.utilityly.presentation.screens.main.companies

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.domain.models.companies.Company
import com.gvituskins.utilityly.domain.repositories.CompanyRepository
import com.gvituskins.utilityly.presentation.core.utils.handleSnackbarDbCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel @Inject constructor(
    private val companyRepository: CompanyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CompaniesState())
    val uiState = _uiState.asStateFlow()

    init {
        companyRepository.getAllCompanies()
            .onEach { newCompanies ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        companies = newCompanies
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun deleteCompany(company: Company) {
        viewModelScope.launch {
            updateModal(CompanyModal.None)
            handleSnackbarDbCall(
                result = companyRepository.deleteCompany(company),
                successMessage = "Company deleted successfully"
            )
        }
    }

    fun updateModal(newState: CompanyModal) {
        _uiState.update { currentUiState ->
            currentUiState.copy(currentModal = newState)
        }
    }
}



data class CompaniesState(
    val companies: List<Company> = emptyList(),
    val currentModal: CompanyModal = CompanyModal.None,
)

@Immutable
sealed interface CompanyModal {
    data class Delete(val company: Company) : CompanyModal

    data object None : CompanyModal
}
