package com.steadytech.cekresi.response.rajaongkir

import com.google.gson.annotations.SerializedName

data class ResultsResponse(
    @SerializedName("province_id") var provinceId :String,
    @SerializedName("province") var provinceName :String
)