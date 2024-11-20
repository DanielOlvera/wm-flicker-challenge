package com.example.wmflickerchallenge.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.wmflickerchallenge.intent.FlickerIntent
import com.example.wmflickerchallenge.navigation.ScreenNavigation
import com.example.wmflickerchallenge.state.FlickerState
import com.example.wmflickerchallenge.viewmodel.FlickerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlickerPhotoScreen(
    viewModel: FlickerViewModel,
    navController: NavController = rememberNavController()) {

    val photoState by viewModel.photoState.observeAsState(FlickerState.Loading)
    val searchQuery by viewModel.searchQuery.collectAsState()

    var query by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }


    Column {
        SearchBar(
            query = searchQuery,
            onQueryChange = { newQuery ->
                query = newQuery
                viewModel.handleIntent(FlickerIntent.FetchFlickerData(newQuery))
            },
            onSearch = {
                // Handle any additional action when the search is submitted
                isActive = false
            },
            active = isActive,
            onActiveChange = { isActive = it },
            placeholder = { Text("Search images") },
            modifier = Modifier.fillMaxWidth(),
            content = {}
        )

        when (photoState) {
            is FlickerState.Success -> {
                val photos = (photoState as FlickerState.Success).response
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(photos.items) { photo ->
                        Box(modifier = Modifier.padding(8.dp)) {
                            AsyncImage(
                                model = photo.link,
                                contentDescription = photo.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(
                                            ScreenNavigation.DetailScreen.createRoute(
                                                title = photo.title,
                                                description = photo.description,
                                                author = photo.author,
                                                published = photo.published,
                                                imageUrl = photo.link
                                            )
                                        )
                                    }
                            )
                        }
                    }
                }
            }
            is FlickerState.Error -> {
                Text("Error: ${(photoState as FlickerState.Error).error}")
            }
            FlickerState.Loading -> {
                CircularProgressIndicator()
            }
        }
    }
}
