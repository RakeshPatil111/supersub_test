package com.example.supersubtest.repository.service

import com.example.supersubtest.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/***
 * This is retrofit client builder class
 * It gives us retrofit client, so that we can communicate with server
 */
class RetrofitClient {
    companion object {
        private val retrofitClient by lazy {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api by lazy {
            retrofitClient.create(ExerciseApi::class.java)
        }
    }
}