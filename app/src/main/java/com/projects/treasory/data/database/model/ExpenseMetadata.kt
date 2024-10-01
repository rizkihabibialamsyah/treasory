package com.projects.treasory.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_metadata")
data class ExpenseMetadata(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val initialBalance: Int,
    val lastUpdated: String
)
