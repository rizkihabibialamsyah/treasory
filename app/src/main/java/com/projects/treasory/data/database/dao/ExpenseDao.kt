package com.projects.treasory.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.projects.treasory.data.database.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Expense)

    @Update
    suspend fun update(item: Expense)

    @Delete
    suspend fun delete(item: Expense)

    @Query("SELECT * from expenses WHERE id = :id")
    fun getExpense(id: Int): Flow<Expense>

    @Query("SELECT * from expenses")
    fun getAllExpenses(): Flow<List<Expense>>
}
