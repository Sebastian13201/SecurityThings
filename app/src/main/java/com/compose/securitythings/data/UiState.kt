package com.compose.securitythings.data

sealed class UiState<out T> {
    object LOADING:UiState<Nothing>()
    data class SUCCESS<out T>(val data:T):UiState<T>()
    data class ERROR(val message:Exception):UiState<Nothing>()
}