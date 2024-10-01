package com.projects.treasory.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.projects.treasory.data.database.model.ExpenseMetadata
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseMetadataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ExpenseMetadata)

    @Update
    suspend fun update(item: ExpenseMetadata)

    @Delete
    suspend fun delete(item: ExpenseMetadata)

    @Query("SELECT * from expense_metadata WHERE id = :id")
    fun getExpenseMetadata(id: Int): Flow<ExpenseMetadata>

    @Query("SELECT * from expense_metadata")
    fun getAllExpenseMetadata(): Flow<List<ExpenseMetadata>>
}
