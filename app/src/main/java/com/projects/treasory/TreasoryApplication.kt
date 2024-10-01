package com.projects.treasory

import android.app.Application
import com.projects.treasory.data.AppContainer
import com.projects.treasory.data.AppDataContainer

class TreasoryApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
