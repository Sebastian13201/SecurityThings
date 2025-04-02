package com.compose.securitythings.domain.usecases


import com.compose.securitythings.data.UiState
import com.compose.securitythings.data.model.CatsItemModel
import com.compose.securitythings.data.network.ApiService
import com.compose.securitythings.data.repo.CatsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class GetCatsUseCase @Inject constructor(private val repository: CatsRepository) {
    operator fun invoke(
        limit:Int=10,
        hasBreeds:Int=1
    ):Flow<UiState<List<CatsItemModel>>> = flow {
        emit(UiState.LOADING)
        val response =repository.getCats(limit,hasBreeds)
        if (response.isSuccessful){
            response.body()?.let {
                emit(UiState.SUCCESS(it))
            }?: throw Exception("Empty Body")
        }else throw Exception("UnSuccessfully Response: ${response.errorBody()}")

    }.catch { e->
        if(e is Exception){
            emit(UiState.ERROR(e))
        }

    }
}