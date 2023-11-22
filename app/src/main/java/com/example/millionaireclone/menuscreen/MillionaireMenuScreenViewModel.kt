package com.example.millionaireclone.menuscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MillionaireMenuScreenViewModel @Inject constructor() : ViewModel() {
    var isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)

    init {
        viewModelScope.launch(Dispatchers.Default) {
            delay(2000)
            isLoading.update { false }
        }
    }
}