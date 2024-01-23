package com.joaorodrigues.tasks.service.repository.remote

import com.joaorodrigues.tasks.service.constants.TaskConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor(){

    companion object {
        private lateinit var retrofit: Retrofit
        private var token: String = ""
        private var personKey: String = ""
        private const val baseUrl = "http://devmasterteam.com/CursoAndroidAPI/"

        private fun getRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader(TaskConstants.HEADER.TOKEN_KEY, token)
                    .addHeader(TaskConstants.HEADER.PERSON_KEY, personKey)
                    .build()
                chain.proceed(request)
            }

            if (!::retrofit.isInitialized) {
                synchronized(RetrofitClient::class.java) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
            return retrofit
        }

        fun <T>getService(serviceClass: Class<T>): T {
            return getRetrofitInstance().create(serviceClass)
        }

        fun addHeaders(tokenValue: String, personKeyValue: String){
            token = tokenValue
            personKey = personKeyValue
        }
    }
}