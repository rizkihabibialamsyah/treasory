package com.projects.treasory.data.repository

import com.projects.treasory.data.database.dao.ExpenseDao
import com.projects.treasory.data.database.model.Expense
import kotlinx.coroutines.flow.Flow

class OfflineExpensesRepository(private val expenseDao: ExpenseDao) : ExpensesRepository {
    override fun getAllExpensesStream(): Flow<List<Expense>> = expenseDao.getAllExpenses()

    override fun getExpenseStream(id: Int): Flow<Expense?> = expenseDao.getExpense(id)

    override suspend fun insertExpense(item: Expense) = expenseDao.insert(item)

    override suspend fun deleteExpense(item: Expense) = expenseDao.delete(item)

    override suspend fun updateExpense(item: Expense) = expenseDao.update(item)
}
