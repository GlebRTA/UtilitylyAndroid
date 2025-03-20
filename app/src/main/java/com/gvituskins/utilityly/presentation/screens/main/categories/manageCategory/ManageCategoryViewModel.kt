package com.gvituskins.utilityly.presentation.screens.main.categories.manageCategory

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.gvituskins.utilityly.domain.models.categories.Category
import com.gvituskins.utilityly.domain.models.categories.CategoryParameter
import com.gvituskins.utilityly.domain.repositories.CategoryRepository
import com.gvituskins.utilityly.presentation.navigation.graphs.CategoriesNavGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageCategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val manageCategory = savedStateHandle.toRoute<CategoriesNavGraph.ManageCategory>()

    private val _uiState = MutableStateFlow(ManageCategoryState())
    val uiState = _uiState.asStateFlow()

    private val _label = Channel<ManageCategoryOneTime>()
    val label = _label.receiveAsFlow()

    init {
        manageCategory.categoryId?.let { initCategoryId ->
            viewModelScope.launch {
                val initCategory = categoryRepository.getCategoryById(initCategoryId)
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        editCategory = initCategory,
                        name = TextFieldState(initCategory.name),
                        description = TextFieldState(initCategory.description ?: ""),

                        localParameters = initCategory.parameters,
                    )
                }
            }
        }
    }

    fun manageCategory() {
        viewModelScope.launch {
            if (uiState.value.isAddMode) addCategory() else editCategory()
            _label.send(ManageCategoryOneTime.NavigateBack)
        }
    }

    private suspend fun addCategory() {
        categoryRepository.addNewCategory(
            Category(
                name = uiState.value.name.text.toString(),
                description = uiState.value.description.text.toString(),
                iconUrl = null,
                parameters = emptyList()
            )
        )
    }

    private suspend fun editCategory() {
        uiState.value.editCategory?.let { categoryToEdit ->
            categoryRepository.updateCategory(
                categoryToEdit.copy(
                    name = uiState.value.name.text.toString(),
                    description = uiState.value.description.text.toString(),
                )
            )
        }
    }

    fun addLocalParameter(name: String) {
        _uiState.update {
            it.copy(
                localParameters = it.localParameters.plus(CategoryParameter(name = name))
            )
        }
    }

    fun updateAddParameterDialog(newState: Boolean) {
        _uiState.update {
            it.copy(
                showAddParameter = newState
            )
        }
    }
}

sealed interface ManageCategoryOneTime {
    data object NavigateBack : ManageCategoryOneTime
}

data class ManageCategoryState(
    val editCategory: Category? = null,

    val name: TextFieldState = TextFieldState(),
    val description: TextFieldState = TextFieldState(),

    val showAddParameter: Boolean = false,
    val localParameters: List<CategoryParameter> = emptyList(),
) {
    val nameIsError: Boolean
        get() = name.text.isEmpty()

    val isValidToManage: Boolean
        get() = !nameIsError

    val isAddMode: Boolean
        get() = editCategory == null
}
