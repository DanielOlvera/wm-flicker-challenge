package com.example.wmflickerchallenge.model.network

import com.example.wmflickerchallenge.model.data.FlickerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickerApiService {

    // https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tags=porcupine

    @GET("services/feeds/photos_public.gne")
    suspend fun getPhotos(
        @Query("tags") tags: String
    ): FlickerResponse
}