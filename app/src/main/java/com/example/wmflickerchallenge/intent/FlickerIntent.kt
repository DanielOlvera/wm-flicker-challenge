package com.example.wmflickerchallenge.intent

sealed class FlickerIntent {
    data class FetchFlickerData(val tag: String): FlickerIntent()
}