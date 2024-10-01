package com.projects.treasory.data.repository

import com.projects.treasory.data.database.dao.ExpenseMetadataDao
import com.projects.treasory.data.database.model.ExpenseMetadata
import kotlinx.coroutines.flow.Flow

class OfflineExpenseMetadataRepository(private val expenseMetadataDao: ExpenseMetadataDao) : ExpenseMetadataRepository {
    override fun getAllExpenseMetadataStream(): Flow<List<ExpenseMetadata>> = expenseMetadataDao.getAllExpenseMetadata()

    override fun getExpenseMetadataStream(id: Int): Flow<ExpenseMetadata?> = expenseMetadataDao.getExpenseMetadata(id)

    override suspend fun insertExpenseMetadata(item: ExpenseMetadata) = expenseMetadataDao.insert(item)

    override suspend fun deleteExpenseMetadata(item: ExpenseMetadata) = expenseMetadataDao.delete(item)

    override suspend fun updateExpenseMetadata(item: ExpenseMetadata) = expenseMetadataDao.update(item)
}
