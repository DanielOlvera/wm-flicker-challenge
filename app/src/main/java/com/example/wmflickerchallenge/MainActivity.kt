package com.example.wmflickerchallenge

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import com.example.wmflickerchallenge.intent.FlickerIntent
import com.example.wmflickerchallenge.model.network.FlickerApiClient
import com.example.wmflickerchallenge.model.repo.FlickerRepository
import com.example.wmflickerchallenge.state.FlickerState
import com.example.wmflickerchallenge.view.FlickerPhotoScreen
import com.example.wmflickerchallenge.viewmodel.FlickerViewModel
import com.example.wmflickerchallenge.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: FlickerViewModel

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = FlickerRepository(FlickerApiClient.flickerApiService)

        val factory = ViewModelFactory { FlickerViewModel(repository) }

        viewModel = ViewModelProvider(this, factory)[FlickerViewModel::class.java]

        setContent {

            FlickerPhotoScreen(
                viewModel
            )
        }
    }
}