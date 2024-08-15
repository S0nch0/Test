package com.example.test

import android.app.Application

class App: Application() {
    lateinit var repo: Repository

    override fun onCreate() {
        super.onCreate()
        instance = this
        repo = Repository(ApiClient())
    }

    companion object{
        lateinit var instance:App
            private set
    }
}