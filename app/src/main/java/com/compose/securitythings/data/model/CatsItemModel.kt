package com.compose.securitythings.data.model


import com.google.gson.annotations.SerializedName

data class CatsItemModel(
    @SerializedName("breeds")
    val breeds: List<BreedModel>? = listOf(),
    @SerializedName("height")
    val height: Int? = 0,
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("width")
    val width: Int? = 0
)