package com.example.accuratetask.api

import com.example.accuratetask.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy

object RestClient {

    private const val BASE_URL = "https://627e360ab75a25d3f3b37d5a.mockapi.io/api/v1/"

    private fun okHttpClient(): OkHttpClient {
        CookieHandler.setDefault(CookieManager(null, CookiePolicy.ACCEPT_ALL))
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        return OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private fun getRetrofit(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient())
            .build()
    }

    fun getService(): Service {
        return getRetrofit().create(Service::class.java)
    }

}