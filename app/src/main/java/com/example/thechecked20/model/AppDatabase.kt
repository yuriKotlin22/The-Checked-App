package com.example.thechecked20.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(DateConverter::class)

@Database(entities = [DadosChecklist::class], version = 1)
 abstract class AppDatabase : RoomDatabase() {

    abstract fun checklistDao(): ChecklistDAO

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "TheChecked"

                    ).build()

                }
                return INSTANCE as AppDatabase
            } else {
                return INSTANCE as AppDatabase
            }

        }
    }
}