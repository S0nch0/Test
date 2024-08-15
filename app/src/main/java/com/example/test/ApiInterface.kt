package com.example.test

import retrofit2.http.GET

interface ApiInterface {
    @GET("/v2/rates/bitcoin")
    suspend fun getCoinRate(): CoinResponse
}