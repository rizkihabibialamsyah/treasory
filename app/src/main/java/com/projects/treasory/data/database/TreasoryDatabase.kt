package com.projects.treasory.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projects.treasory.data.database.dao.ExpenseDao
import com.projects.treasory.data.database.dao.ExpenseMetadataDao
import com.projects.treasory.data.database.model.Expense
import com.projects.treasory.data.database.model.ExpenseMetadata

@Database(
    entities = [
        Expense::class,
        ExpenseMetadata::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TreasoryDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun expenseMetadataDao(): ExpenseMetadataDao

    companion object {
        @Volatile
        private var Instance: TreasoryDatabase? = null

        fun getDatabase(context: Context): TreasoryDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TreasoryDatabase::class.java, "treasory_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
