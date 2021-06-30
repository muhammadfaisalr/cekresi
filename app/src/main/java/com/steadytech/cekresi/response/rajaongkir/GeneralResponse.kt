package com.steadytech.cekresi.response.rajaongkir

import com.google.gson.annotations.SerializedName

data class GeneralResponse(
        @SerializedName("rajaongkir") var rajaOngkirResponse: RajaOngkirResponse
)