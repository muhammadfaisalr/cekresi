package com.steadytech.cekresi.builder

import com.steadytech.cekresi.constant.Constant
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**

Created By : Steady Tech.

 **/

class RetrofitBuilder {
    companion object{
        fun checkAwb() : Retrofit{
            return Retrofit.Builder()
                .baseUrl(Constant.URL.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }
}