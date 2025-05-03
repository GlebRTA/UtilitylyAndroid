package com.gvituskins.utilityly.presentation.screens.main.categories.manageCategory

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
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
                        color = initCategory.color,

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

    @OptIn(ExperimentalStdlibApi::class)
    private suspend fun addCategory() {
        val color = uiState.value.color ?: return

        categoryRepository.addNewCategory(
            Category(
                name = uiState.value.name.text.toString(),
                description = uiState.value.description.text.toString(),
                color = color,
                parameters = uiState.value.localParameters
            )
        )
    }

    @OptIn(ExperimentalStdlibApi::class)
    private suspend fun editCategory() {
        val color = uiState.value.color ?: return

        uiState.value.editCategory?.let { categoryToEdit ->
            categoryRepository.updateCategory(
                categoryToEdit.copy(
                    name = uiState.value.name.text.toString(),
                    description = uiState.value.description.text.toString(),
                    color = color,
                    parameters = uiState.value.localParameters
                )
            )
        }
    }

    fun addLocalParameter(name: String) {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                localParameters = currentUiState.localParameters.plus(CategoryParameter(id = 0, name = name)),
                currentModal = ManageCategoryModal.None
            )
        }
    }

    fun deleteLocalParameter(index: Int) {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                localParameters = currentUiState.localParameters.minus(currentUiState.localParameters.elementAt(index)),
                currentModal = ManageCategoryModal.None
            )
        }
    }

    fun editLocalParameter(parameter: CategoryParameter, newName: String) {
        _uiState.update { currentUiState ->
            currentUiState.localParameters.find { it == parameter }?.copy(name = newName)?.let { newParameter ->
                currentUiState.copy(
                    localParameters = currentUiState.localParameters.map { if (it == parameter) newParameter else it },
                    currentModal = ManageCategoryModal.None
                )
            } ?: currentUiState
        }
    }

    fun updateParameterSheet(newState: ManageCategoryModal) {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                currentModal = newState
            )
        }
    }

    fun updateColor(color: Color) {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                color = color,
                currentModal = ManageCategoryModal.None
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
    val color: Color? = null,

    val currentModal: ManageCategoryModal = ManageCategoryModal.None,
    val localParameters: List<CategoryParameter> = emptyList(),
) {
    val nameIsError: Boolean
        get() = name.text.isEmpty()

    val colorIsError: Boolean
        get() = color == null

    val isValidToManage: Boolean
        get() = !nameIsError && !colorIsError

    val isAddMode: Boolean
        get() = editCategory == null
}

@Immutable
sealed interface ManageCategoryModal {
    data class Parameter(val parameter: CategoryParameter?): ManageCategoryModal
    data object ColorPicker: ManageCategoryModal
    data object None : ManageCategoryModal
}
