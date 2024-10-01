package com.projects.treasory.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.treasory.data.database.model.Expense
import com.projects.treasory.data.database.model.ExpenseMetadata
import com.projects.treasory.data.repository.ExpenseMetadataRepository
import com.projects.treasory.data.repository.ExpensesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TreasoryHomeViewModel(
    private val expensesRepository: ExpensesRepository,
    private val expenseMetadataRepository: ExpenseMetadataRepository
) : ViewModel() {
    private val metadataFlow = expenseMetadataRepository.getAllExpenseMetadataStream()
    private val expensesFlow = expensesRepository.getAllExpensesStream()

    val uiState: StateFlow<TreasoryHomeUiState> =
        combine(metadataFlow, expensesFlow) { metadata, expenses ->
            if (metadata.isEmpty()) {
                runBlocking {
                    expenseMetadataRepository.insertExpenseMetadata(
                        ExpenseMetadata(
                            title = "Untitled",
                            initialBalance = 0,
                            lastUpdated = "-"
                        )
                    )
                    TreasoryHomeUiState(
                        "Untitled",
                        expenses,
                        getTotalSpent(expenses),
                        getTotalBudget(expenses),
                        0,
                        "-"
                    )
                }
            } else {
                TreasoryHomeUiState(
                    metadata[0].title,
                    expenses,
                    getTotalSpent(expenses),
                    getTotalBudget(expenses),
                    metadata[0].initialBalance,
                    metadata[0].lastUpdated
                )
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = TreasoryHomeUiState()
            )

    private fun getTotalSpent(expenseList: List<Expense>): Int {
        var totalSpent = 0
        for (expenseItem in expenseList) {
            totalSpent += expenseItem.spent
        }

        return totalSpent
    }

    private fun getTotalBudget(expenseList: List<Expense>): Int {
        var totalBudget = 0
        for (expenseItem in expenseList) {
            totalBudget += expenseItem.budget
        }

        return totalBudget
    }

    suspend fun updateExpenseMetadata() {
        expenseMetadataRepository.updateExpenseMetadata(
            ExpenseMetadata(
                id = 1,
                title = uiState.value.title,
                initialBalance = uiState.value.balance,
                lastUpdated = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(
                    LocalDateTime.now()
                )
            )
        )
    }

    suspend fun addExpense() {
        expensesRepository.insertExpense(
            Expense(name = "", budget = 0, spent = 0)
        )
        updateExpenseMetadata()
    }

    suspend fun deleteExpense(index: Int) {
        expensesRepository.deleteExpense(uiState.value.expenses[index])
        updateExpenseMetadata()
    }

    suspend fun updateExpense(index: Int) {
        expensesRepository.updateExpense(uiState.value.expenses[index])
        updateExpenseMetadata()
    }
}

data class TreasoryHomeUiState(
    val initialTitle: String = "",
    val initialExpenses: List<Expense> = listOf(),
    val totalSpent: Int = 0,
    val totalBudget: Int = 0,
    val initialBalance: Int = 0,
    val lastUpdated: String = ""
) {
    var title by mutableStateOf(initialTitle)
        private set

    private val _expenses: MutableList<Expense> = initialExpenses.toMutableStateList()
    val expenses: List<Expense> = _expenses

    var balance by mutableStateOf(initialBalance)
        private set

    private fun getNewValue(string: String, currentValue: Int): Int? {
        val newValueString = if (currentValue == 0) {
            if (string.isEmpty()) {
                return null
            } else {
                string.trim('0')
            }
        } else {
            string
        }

        return if (newValueString.isEmpty()) 0 else newValueString.toIntOrNull()
    }

    fun updateTitle(newTitle: String) {
        title = newTitle
    }

    fun updateBalance(balanceStr: String) {
        val newBalance = balanceStr.toIntOrNull()
        if (newBalance != null) {
            balance = newBalance
        }
    }

    fun updateExpenseName(index: Int, name: String) {
        _expenses[index] = _expenses[index].copy(name = name)
    }

    fun updateExpenseSpent(index: Int, spentStr: String) {
        val newSpent = getNewValue(spentStr, _expenses[index].spent)
        if (newSpent != null) {
            _expenses[index] = _expenses[index].copy(spent = newSpent)
        }
    }

    fun updateExpenseBudget(index: Int, budgetStr: String) {
        val newBudget = getNewValue(budgetStr, _expenses[index].budget)
        if (newBudget != null) {
            _expenses[index] = _expenses[index].copy(budget = newBudget)
        }
    }
}
