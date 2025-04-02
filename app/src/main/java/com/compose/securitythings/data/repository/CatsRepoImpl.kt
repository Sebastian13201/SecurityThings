package com.compose.securitythings.data.repo

import com.compose.securitythings.data.model.CatsItemModel
import com.compose.securitythings.data.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class CatsRepoImpl @Inject constructor(private val apiService: ApiService): CatsRepository {
    override suspend fun getCats(limit: Int, hasBreeds: Int): Response<List<CatsItemModel>> {
        return apiService.getCats(limit, hasBreeds)
    }
}