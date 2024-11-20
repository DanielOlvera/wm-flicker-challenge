package com.example.wmflickerchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wmflickerchallenge.view.FlickerPhotoScreen
import com.example.wmflickerchallenge.view.PhotoDetail
import com.example.wmflickerchallenge.viewmodel.FlickerViewModel

@Composable
fun ScreenNavigation(
    navController: NavHostController,
    viewModel: FlickerViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "search_screen" // The starting screen in your app
    ) {
        searchScreen(navController, viewModel)
        photoDetailScreen()
    }
}

fun NavGraphBuilder.searchScreen(
    navController: NavHostController,
    viewModel: FlickerViewModel
) {
    composable("search_screen") {
        // Pass the NavController to the SearchScreen
        FlickerPhotoScreen(navController = navController, viewModel = viewModel)
    }
}

fun NavGraphBuilder.photoDetailScreen() {
    composable(
        route = "photo_detail_screen/{title}/{description}/{author}/{published}/{imageUrl}",
        arguments = listOf(
            navArgument("title") { type = NavType.StringType },
            navArgument("description") { type = NavType.StringType },
            navArgument("author") { type = NavType.StringType },
            navArgument("published") { type = NavType.StringType },
            navArgument("imageUrl") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?: ""
        val description = backStackEntry.arguments?.getString("description") ?: ""
        val author = backStackEntry.arguments?.getString("author") ?: ""
        val published = backStackEntry.arguments?.getString("published") ?: ""
        val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""

        PhotoDetail(
            title = title,
            description = description,
            author = author,
            published = published,
            imageUrl = imageUrl
        )
    }
}
