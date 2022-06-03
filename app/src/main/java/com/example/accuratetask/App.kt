package com.example.accuratetask

import androidx.multidex.MultiDexApplication
import com.example.accuratetask.api.RestClient
import com.example.accuratetask.model.AppDatabase
import com.example.accuratetask.model.repository.Repository

class App: MultiDexApplication() {

    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { Repository(RestClient.getService()) }

    companion object {
        @get:Synchronized
        lateinit var context: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}