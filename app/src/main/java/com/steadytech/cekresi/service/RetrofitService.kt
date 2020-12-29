package com.steadytech.cekresi.service

import com.steadytech.cekresi.response.awb.AWBResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**

Created by : Steady Tech.

 **/

interface RetrofitService {
    @GET("v1/track")
    fun getAwbDetail (
        @Query("api_key") apiKey : String,
        @Query("courier") courier : String,
        @Query("awb") awb : String
    ) : Observable<AWBResponse>
}