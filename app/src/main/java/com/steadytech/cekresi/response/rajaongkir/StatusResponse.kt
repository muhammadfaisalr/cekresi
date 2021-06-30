package com.steadytech.cekresi.response.rajaongkir

import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("code") var code: Int,
    @SerializedName("description") var description: String
)