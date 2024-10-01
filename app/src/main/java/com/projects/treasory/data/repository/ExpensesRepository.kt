package com.projects.treasory.data.repository

import com.projects.treasory.data.database.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpensesRepository {
    fun getAllExpensesStream(): Flow<List<Expense>>

    fun getExpenseStream(id: Int): Flow<Expense?>

    suspend fun insertExpense(item: Expense)

    suspend fun deleteExpense(item: Expense)

    suspend fun updateExpense(item: Expense)
}
