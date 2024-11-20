package com.example.wmflickerchallenge.navigation

sealed class ScreenNavigation(val route: String) {
    data object SearchScreen : ScreenNavigation("search_screen")
    data object DetailScreen : ScreenNavigation("detail_screen/{title}/{description}/{author}/{published}/{imageUrl}") {
        fun createRoute(
            title: String?, description: String?, author: String?, published: String?, imageUrl: String?
        ) = "detail_screen/$title/$description/$author/$published/$imageUrl"
    }
}
