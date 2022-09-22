package com.rafi.suitmediatest.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
//    @GET("api/users?page=1")
    @GET("api/users")
    fun getUser(
        @QueryMap parameters : HashMap<String, String>
    ): Call<UserResponse>
}