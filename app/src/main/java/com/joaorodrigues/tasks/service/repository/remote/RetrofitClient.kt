package com.joaorodrigues.tasks.service.repository.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor(){

    companion object {
        private lateinit var retrofit: Retrofit
        private const val baseUrl = "http://devmasterteam.com/CursoAndroidAPI/"

        private fun getRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder().build()

            if (!::retrofit.isInitialized) {
                synchronized(RetrofitClient::class.java) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(httpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
            return retrofit
        }

        fun <T>getService(serviceClass: Class<T>): T {
            return getRetrofitInstance().create(serviceClass)
        }

        fun <S> createService(serviceClass: Class<S>): S {
            return getRetrofitInstance().create(serviceClass)
        }
    }
}