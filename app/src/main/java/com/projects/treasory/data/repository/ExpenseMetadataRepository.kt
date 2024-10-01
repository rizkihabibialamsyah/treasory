package com.projects.treasory.data.repository

import com.projects.treasory.data.database.model.ExpenseMetadata
import kotlinx.coroutines.flow.Flow

interface ExpenseMetadataRepository {
    fun getAllExpenseMetadataStream(): Flow<List<ExpenseMetadata>>

    fun getExpenseMetadataStream(id: Int): Flow<ExpenseMetadata?>

    suspend fun insertExpenseMetadata(item: ExpenseMetadata)

    suspend fun deleteExpenseMetadata(item: ExpenseMetadata)

    suspend fun updateExpenseMetadata(item: ExpenseMetadata)
}
