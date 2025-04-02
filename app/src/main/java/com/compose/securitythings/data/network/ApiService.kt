package com.compose.securitythings.data.network

import com.compose.securitythings.data.model.CatsItemModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v1/images/search")
    suspend fun getCats(
        @Query("limit")limit:Int,
        @Query("has_breeds")hasBreeds:Int
    ):Response<List<CatsItemModel>>
}