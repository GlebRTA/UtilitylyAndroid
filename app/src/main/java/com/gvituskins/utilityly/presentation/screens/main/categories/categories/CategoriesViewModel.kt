package com.gvituskins.utilityly.presentation.screens.main.categories.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.domain.models.categories.Category
import com.gvituskins.utilityly.domain.repositories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesState())
    val uiState = _uiState.asStateFlow()

    init {
        categoryRepository.getAllCategories()
            .onEach { categories ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(categories = categories)
                }
            }
            .launchIn(viewModelScope)
    }

    fun deleteCategory(categoryToDelete: Category) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(categoryToDelete)
            _uiState.update { currentUiState ->
                currentUiState.copy(deleteCategory = null)
            }
        }
    }

    fun updateDeleteDialogVisibility(category: Category?) {
        _uiState.update { currentUiState ->
            currentUiState.copy(deleteCategory = category)
        }
    }

}

data class CategoriesState(
    val categories: List<Category> = emptyList(),
    val deleteCategory: Category? = null,
)
