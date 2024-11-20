package com.example.wmflickerchallenge.state

import com.example.wmflickerchallenge.model.data.FlickerResponse

sealed class FlickerState {
    data object Loading: FlickerState()
    data class Success(val response: FlickerResponse): FlickerState()
    data class Error(val error: String): FlickerState()
}