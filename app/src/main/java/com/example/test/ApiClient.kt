package com.example.test

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    fun getService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.coincap.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}