package com.steadytech.cekresi.response.rajaongkir

import com.google.gson.annotations.SerializedName

data class RajaOngkirResponse (
    @SerializedName("query") var queryResponse: List<QueryResponse>,
    @SerializedName("status") var statusResponse: StatusResponse,
    @SerializedName("results") var resultsResponse: List<ResultsResponse>
)