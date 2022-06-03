package com.example.accuratetask.api

import com.example.accuratetask.model.entity.City
import com.example.accuratetask.model.entity.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

    @GET("accurate/user")
    suspend fun getUsers(): List<User>

    @GET("accurate/city")
    suspend fun getCities(): List<City>

    @POST("accurate/user")
    fun addUser(@Body user: User): Call<User>

}