package com.example.wmflickerchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wmflickerchallenge.intent.FlickerIntent
import com.example.wmflickerchallenge.model.repo.FlickerRepository
import com.example.wmflickerchallenge.state.FlickerState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class FlickerViewModel (private val repository: FlickerRepository): ViewModel() {

    private val _photoState = MutableLiveData<FlickerState>()
    val photoState: LiveData<FlickerState> = _photoState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val debounceTime = 500L

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(debounceTime)
                .filter { it.isNotBlank() } // We ignore blank searches
                .collect { query ->
                    fetchPhotosByTag(query)
                }
        }
    }

    fun handleIntent(intent: FlickerIntent) {
        when(intent) {
            is FlickerIntent.FetchFlickerData -> {
                _searchQuery.value = intent.tag
            }
        }
    }

    fun updateSearch(tag: String) {

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