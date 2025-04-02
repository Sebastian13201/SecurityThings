package com.compose.securitythings.data.model


import com.google.gson.annotations.SerializedName

data class WeightModel(
    @SerializedName("imperial")
    val imperial: String? = "",
    @SerializedName("metric")
    val metric: String? = ""
)