package com.deuksoft.smartiv

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitInterface {
    @Headers("accept: application/json","content-type: application/json")
    @POST("/android/getuserdata/")
    fun getPatientInfo(
        @Body body: HashMap<String, String>
    ): Call<List<PatientData>>

    @Headers("accept: application/json","content-type: application/json")
    @POST("/socket/start")
    fun startMoter(
        @Body body: HashMap<String, String>
    ):Call<getData>

    @Headers("accept: application/json","content-type: application/json")
    @POST("/socket/calldoc")
    fun callDoctor(
        @Body body: HashMap<String, String>
    ):Call<getData>

    @Headers("accept: application/json","content-type: application/json")
    @POST("/socket/restart")
    fun restart(
        @Body body: HashMap<String, String>
    ):Call<getData>

    @Headers("accept: application/json","content-type: application/json")
    @POST("/login/android")
    fun loginss(
        @Body body: HashMap<String, String>
    ):Call<loginss>
}