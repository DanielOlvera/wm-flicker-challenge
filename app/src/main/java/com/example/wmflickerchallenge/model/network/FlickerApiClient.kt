package com.example.wmflickerchallenge.model.network

import com.example.wmflickerchallenge.model.network.utils.ApiUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FlickerApiClient {

    private val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val okHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(logging)
    }

    val flickerApiService: FlickerApiService by lazy {
        Retrofit.Builder()
            .baseUrl(ApiUtils.BASE_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlickerApiService::class.java)
    }
}