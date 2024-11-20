package com.example.wmflickerchallenge.model.repo

import com.example.wmflickerchallenge.model.network.FlickerApiService
import com.example.wmflickerchallenge.state.FlickerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FlickerRepository(private val flickerApiService: FlickerApiService) {

    fun getPhotos(tag: String): Flow<FlickerState> = flow {
        emit(FlickerState.Loading)
        try {
            val response = flickerApiService.getPhotos(tag)
            emit(FlickerState.Success(response))
        } catch (e: Exception) {
            emit(FlickerState.Error(e.message ?: "Unknown error"))
        }
    }
}