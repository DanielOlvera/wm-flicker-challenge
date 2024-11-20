package com.example.wmflickerchallenge.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.wmflickerchallenge.model.data.Items

@Composable
fun PhotoItem(photo: Items) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { /* Handle photo click */ }
    ) {
        AsyncImage(
            model = photo.link,
            contentDescription = photo.description,
            modifier = Modifier.fillMaxSize()
        )
    }
}
