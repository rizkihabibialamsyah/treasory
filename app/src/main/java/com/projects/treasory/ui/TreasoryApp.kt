package com.projects.treasory.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.treasory.ui.components.ExpenseItem
import com.projects.treasory.ui.components.TreasoryTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreasoryApp(
    viewModel: TreasoryHomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                title = {
                    TreasoryTextField(
                        value = uiState.title,
                        onValueChange = { uiState.updateTitle(it) },
                        onValueSet = {
                            coroutineScope.launch {
                                viewModel.updateExpenseMetadata()
                            }
                        },
                        fontSize = 24.sp
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                itemsIndexed(uiState.expenses) { index, item ->
                    ExpenseItem(
                        name = item.name,
                        spent = item.spent.toString(),
                        budget = item.budget.toString(),
                        onNameChange = { name ->
                            uiState.updateExpenseName(index, name)
                        },
                        onNameSet = {
                            coroutineScope.launch {
                                viewModel.updateExpense(index)
                            }
                        },
                        onSpentChange = { spentStr ->
                            uiState.updateExpenseSpent(index, spentStr)
                        },
                        onSpentSet = {
                            coroutineScope.launch {
                                viewModel.updateExpense(index)
                            }
                        },
                        onBudgetChange = { budgetStr ->
                            uiState.updateExpenseBudget(index, budgetStr)
                        },
                        onBudgetSet = {
                            coroutineScope.launch {
                                viewModel.updateExpense(index)
                            }
                        },
                        onDeleted = {
                            coroutineScope.launch {
                                viewModel.deleteExpense(index)
                            }
                        }
                    )

                    HorizontalDivider()
                }

                item {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Button(
                            modifier = Modifier
                                .align(alignment = Alignment.Center),
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.addExpense()
                                }
                            }
                        ) {
                            Text(text = "Add")
                        }
                    }
                }

                item {
                    Text(text = "Spent: " + uiState.totalSpent + " / " + uiState.totalBudget)
                }

                item {
                    Row() {
                        Text(text = "Balance: " + (uiState.balance - uiState.totalSpent) + " / ")
                        TreasoryTextField(
                            value = uiState.balance.toString(),
                            onValueChange = { uiState.updateBalance(it) },
                            onValueSet = {
                                coroutineScope.launch {
                                    viewModel.updateExpenseMetadata()
                                }
                            }
                        )
                    }
                }

                item {
                    Text(text = "Last updated: " + uiState.lastUpdated)
                }
            }
        }
    }
}
