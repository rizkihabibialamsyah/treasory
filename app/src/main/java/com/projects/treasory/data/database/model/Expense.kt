package com.projects.treasory.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val spent: Int,
    val budget: Int,
)
