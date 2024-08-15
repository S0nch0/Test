package com.example.test

class Repository(client: ApiClient) {
    private val client = client.getService().create(ApiInterface::class.java)

    suspend fun getCoinRate():CoinResponse {
        return client.getCoinRate()
    }
}