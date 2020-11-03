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
}