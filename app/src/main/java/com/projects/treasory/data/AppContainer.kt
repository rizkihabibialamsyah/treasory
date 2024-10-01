package com.projects.treasory.data

import android.content.Context
import com.projects.treasory.data.database.TreasoryDatabase
import com.projects.treasory.data.repository.ExpenseMetadataRepository
import com.projects.treasory.data.repository.ExpensesRepository
import com.projects.treasory.data.repository.OfflineExpensesRepository
import com.projects.treasory.data.repository.OfflineExpenseMetadataRepository

interface AppContainer {
    val expensesRepository: ExpensesRepository
    val expenseMetadataRepository: ExpenseMetadataRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val expensesRepository: ExpensesRepository by lazy {
        OfflineExpensesRepository(TreasoryDatabase.getDatabase(context).expenseDao())
    }

    override val expenseMetadataRepository: ExpenseMetadataRepository by lazy {
        OfflineExpenseMetadataRepository(TreasoryDatabase.getDatabase(context).expenseMetadataDao())
    }
}
