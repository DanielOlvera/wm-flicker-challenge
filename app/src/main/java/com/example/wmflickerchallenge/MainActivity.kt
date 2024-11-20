package com.example.wmflickerchallenge

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.wmflickerchallenge.model.network.FlickerApiClient
import com.example.wmflickerchallenge.model.repo.FlickerRepository
import com.example.wmflickerchallenge.navigation.ScreenNavigation
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

            val navigationController = rememberNavController()
            ScreenNavigation(navController = navigationController, viewModel = viewModel)

        }
    }
}