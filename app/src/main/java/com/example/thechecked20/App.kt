package com.example.thechecked20

import android.app.Application
import com.example.thechecked20.model.AppDatabase

class App: Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = AppDatabase.getDatabase(this)

    }
}