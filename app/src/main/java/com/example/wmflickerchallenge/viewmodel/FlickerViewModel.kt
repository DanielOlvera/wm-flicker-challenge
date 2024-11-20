package com.example.wmflickerchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wmflickerchallenge.intent.FlickerIntent
import com.example.wmflickerchallenge.model.repo.FlickerRepository
import com.example.wmflickerchallenge.state.FlickerState
import kotlinx.coroutines.launch

class FlickerViewModel (private val repository: FlickerRepository): ViewModel() {

    private val _photoState = MutableLiveData<FlickerState>()
    val photoState: LiveData<FlickerState> = _photoState

    fun handleIntent(intent: FlickerIntent) {
        when(intent) {
            is FlickerIntent.FetchFlickerData -> {
                fetchPhotosByTag(intent.tag)
            }
        }
    }

    private fun fetchPhotosByTag(tag: String) {
        viewModelScope.launch {
            try {
                repository.getPhotos(tag).collect { state ->
                    _photoState.value = state
                }
            } catch (e: Exception) {
                _photoState.value = FlickerState.Error(e.message ?: "Failed to fetch photos")
            }
        }
    }
}