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
        fun baseBinderbyte() : Retrofit{
            return Retrofit.Builder()
                .baseUrl(Constant.URL.BINDERBYTE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        fun baseRajaOngkir() : Retrofit{
            return Retrofit.Builder()
                .baseUrl(Constant.URL.RAJAONGKIR_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }
}