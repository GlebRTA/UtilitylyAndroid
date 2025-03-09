package com.gvituskins.utilityly.domain.repositories

import com.gvituskins.utilityly.domain.models.categories.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllUtilities(): Flow<List<Category>>
}
