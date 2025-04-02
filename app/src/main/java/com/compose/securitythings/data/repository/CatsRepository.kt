package com.compose.securitythings.data.repo

import com.compose.securitythings.data.model.CatsItemModel
import retrofit2.Response

interface CatsRepository {
    suspend fun getCats(limit:Int,hasBreeds:Int):Response<List<CatsItemModel>>
}