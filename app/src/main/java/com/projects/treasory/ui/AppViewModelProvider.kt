package com.projects.treasory.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.projects.treasory.TreasoryApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TreasoryHomeViewModel(
                inventoryApplication().container.expensesRepository,
                inventoryApplication().container.expenseMetadataRepository
            )
        }
    }
}

fun CreationExtras.inventoryApplication(): TreasoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as TreasoryApplication)
