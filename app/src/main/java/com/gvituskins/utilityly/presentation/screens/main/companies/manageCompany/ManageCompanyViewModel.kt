package com.gvituskins.utilityly.presentation.screens.main.companies.manageCompany

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.gvituskins.utilityly.domain.models.companies.Company
import com.gvituskins.utilityly.domain.repositories.CompanyRepository
import com.gvituskins.utilityly.presentation.navigation.graphs.MoreNavGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageCompanyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val companyRepository: CompanyRepository,
) : ViewModel() {
    private val manageCompany = savedStateHandle.toRoute<MoreNavGraph.ManageCompany>()

    private val _uiState = MutableStateFlow(ManageCompanyState())
    val uiState = _uiState.asStateFlow()

    private val _label = Channel<ManageCompanyOneTime>()
    val label = _label.receiveAsFlow()

    init {
        manageCompany.companyId?.let { initCompanyId ->
            viewModelScope.launch {
                val initCompany = companyRepository.getCompanyById(initCompanyId)
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        editCompany = initCompany,

                        name = TextFieldState(initCompany.name),
                        address = TextFieldState(initCompany.address ?: ""),
                        phone = TextFieldState(initCompany.phone ?: ""),
                        webPage = TextFieldState(initCompany.webPageUrl ?: ""),
                        email = TextFieldState(initCompany.email ?: ""),
                    )
                }
            }
        }
    }

    fun manageCategory() {
        viewModelScope.launch {
            if (uiState.value.isAddMode) addCompany() else editCompany()
            _label.send(ManageCompanyOneTime.NavigateBack)
        }
    }

    private suspend fun addCompany() {
        companyRepository.addCompany(
            Company(
                name = uiState.value.name.text.toString(),
                address = uiState.value.address.text.toString(),
                phone = uiState.value.phone.text.toString(),
                webPageUrl = uiState.value.webPage.text.toString(),
                email = uiState.value.email.text.toString(),
            )
        )
    }

    private suspend fun editCompany() {
        uiState.value.editCompany?.let { companyToEdit ->
            companyRepository.updateCompany(
                companyToEdit.copy(
                    name = uiState.value.name.text.toString(),
                    address = uiState.value.address.text.toString(),
                    phone = uiState.value.phone.text.toString(),
                    webPageUrl = uiState.value.webPage.text.toString(),
                    email = uiState.value.email.text.toString(),
                )
            )
        }
    }

}

sealed interface ManageCompanyOneTime {
    data object NavigateBack : ManageCompanyOneTime
}

@Immutable
data class ManageCompanyState(
    val editCompany: Company? = null,

    val name: TextFieldState = TextFieldState(),
    val address: TextFieldState = TextFieldState(),
    val phone: TextFieldState = TextFieldState(),
    val webPage: TextFieldState = TextFieldState(),
    val email: TextFieldState = TextFieldState(),
) {
    val isAddMode: Boolean
        get() = editCompany == null

    val nameIsError: Boolean
        get() = name.text.isEmpty()
}
