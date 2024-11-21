package com.example.wmflickerchallenge.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wmflickerchallenge.model.data.Items
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
        photoDetailScreen(navController)
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

fun NavGraphBuilder.photoDetailScreen(navController: NavHostController) {
    composable(
        route = "photo_detail_screen"
    ) {

        val item = navController.previousBackStackEntry?.savedStateHandle?.get<Items>("item")

        Log.d("Dang", "photoDetailScreen: $item")
        PhotoDetail(item)
    }
}
