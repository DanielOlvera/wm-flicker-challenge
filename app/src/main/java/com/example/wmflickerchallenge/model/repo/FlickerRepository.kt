package com.example.wmflickerchallenge.model.repo

import com.example.wmflickerchallenge.model.network.FlickerApiService

class FlickerRepository(private val flickerApiService: FlickerApiService) {

    fun getPhotos(tag: String): Flow<>
}