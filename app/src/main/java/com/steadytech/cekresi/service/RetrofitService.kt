package com.steadytech.cekresi.service

import com.steadytech.cekresi.response.awb.AWBResponse
import com.steadytech.cekresi.response.rajaongkir.GeneralResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**

Created by : Steady Tech.

 **/

interface RetrofitService {
    @GET("v1/track")
    fun getAwbDetail(
        @Query("api_key") apiKey: String,
        @Query("courier") courier: String,
        @Query("awb") awb: String
    ): Observable<AWBResponse>

    @GET("province")
    fun getProvince(@Header("key") rajaOngkirApiKey: String): Observable<GeneralResponse>
}