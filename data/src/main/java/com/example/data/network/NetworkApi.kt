package com.example.data.network

import com.example.data.db.EmployeesResponse
import retrofit2.Response
import retrofit2.http.GET

internal  interface NetworkApi {

    @GET("65gb/static/raw/master/testTask.json")
    suspend fun getEmployees(): Response<EmployeesResponse>
}