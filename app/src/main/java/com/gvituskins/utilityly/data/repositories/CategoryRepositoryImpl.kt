package com.gvituskins.utilityly.data.repositories

import com.gvituskins.utilityly.data.db.dao.CategoryDao
import com.gvituskins.utilityly.data.mappers.toCategory
import com.gvituskins.utilityly.data.mappers.toCategoryEntity
import com.gvituskins.utilityly.data.mappers.toCategoryParameterEntity
import com.gvituskins.utilityly.domain.models.categories.Category
import com.gvituskins.utilityly.domain.models.categories.CategoryParameter
import com.gvituskins.utilityly.domain.repositories.CategoryRepository
import com.gvituskins.utilityly.presentation.core.utils.debugLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().map { categories ->
            categories.map { it.toCategory(emptyList()) }
        }
    }

    override suspend fun getCategoryById(id: Int): Category {
        val categoryParameters = categoryDao.getCategoryParameters(id)
        return categoryDao.getCategoryById(id).toCategory(categoryParameters.parameters)
    }

    override suspend fun addNewCategory(category: Category) {
        categoryDao.addCategoryWithParameters(
            category = category.toCategoryEntity(),
            parameters = category.parameters.map { it.toCategoryParameterEntity() }
        )
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category.toCategoryEntity())
    }

    override suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category.toCategoryEntity())
    }

    override suspend fun addCategoryParameter(
        categoryId: Int,
        parameter: CategoryParameter
    ) {
        categoryDao.addCategoryParameter(parameter.toCategoryParameterEntity(categoryId))
    }
}
