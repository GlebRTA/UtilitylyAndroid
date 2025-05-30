package com.gvituskins.utilityly.domain.repositories

import com.gvituskins.utilityly.domain.models.categories.Category
import com.gvituskins.utilityly.domain.models.categories.CategoryParameter
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllCategories(): Flow<List<Category>>

    suspend fun getCategoryById(id: Int): Category

    suspend fun addNewCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    suspend fun updateCategory(category: Category)

    suspend fun addCategoryParameter(categoryId: Int, parameter: CategoryParameter)
}
