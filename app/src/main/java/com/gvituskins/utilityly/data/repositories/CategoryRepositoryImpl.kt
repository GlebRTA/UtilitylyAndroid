package com.gvituskins.utilityly.data.repositories

import com.gvituskins.utilityly.data.db.dao.CategoryDao
import com.gvituskins.utilityly.data.mappers.toCategory
import com.gvituskins.utilityly.data.mappers.toCategoryEntity
import com.gvituskins.utilityly.domain.models.categories.Category
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
            categories.map { it.toCategory() }
        }
    }

    override suspend fun getCategoryById(id: Int): Category {
        return categoryDao.getCategoryById(id).toCategory()
    }

    override suspend fun addNewCategory(category: Category) {
        categoryDao.addNewCategory(category.toCategoryEntity())
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category.toCategoryEntity())
    }

    override suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category.toCategoryEntity())
    }

    fun getAllCategoryParameters() {
        debugLog(categoryDao.getCategoryParameters().toString())
    }
}
