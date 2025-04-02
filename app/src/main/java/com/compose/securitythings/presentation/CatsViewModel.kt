package com.compose.securitythings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.securitythings.data.UiState
import com.compose.securitythings.data.model.CatsItemModel
import com.compose.securitythings.domain.usecases.GetCatsUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(private val getCats: GetCatsUseCase):ViewModel() {

    private val _cats=MutableStateFlow<UiState<List<CatsItemModel>>>(UiState.LOADING)
    val cats:StateFlow<UiState<List<CatsItemModel>>> = _cats

    init {
        getCatsList()
    }

    fun getCatsList(){
        viewModelScope.launch {
            getCats().collect {
                _cats.value=it
            }
        }
    }
}