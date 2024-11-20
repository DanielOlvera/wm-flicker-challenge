package com.example.wmflickerchallenge.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.example.wmflickerchallenge.intent.FlickerIntent
import com.example.wmflickerchallenge.state.FlickerState
import com.example.wmflickerchallenge.viewmodel.FlickerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlickerPhotoScreen(
    viewModel: FlickerViewModel,
    navController: NavController
) {
    val photoState by viewModel.photoState.observeAsState()
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
                        Log.d("Dang", "FlickerPhotoScreen: $photo")
                        Box(modifier = Modifier.padding(8.dp)) {
                            // Use of Coil to load the image
                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest.Builder(LocalContext.current)
                                        .data(photo.media?.m)
                                        .build()
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(128.dp)
                                    .clickable {
                                        navController.navigate(
                                            "photo_detail_screen/${photo.title}/${photo.description}/${photo.author}/${photo.published}/${photo.media?.m}"
                                        )
                                    }
                            )
//                            AsyncImage(
//                                model = photo.media, // Image URL
//                                contentDescription = photo.title,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .clickable {
//                                        navController.navigate(
//                                            "photo_detail_screen/${photo.title}/${photo.description}/${photo.author}/${photo.published}/${photo.link}"
//                                        )
//                                    }
//                            )
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
            else -> {
                Text("Unknown State, how it landed here?")
            }
        }
    }
}
