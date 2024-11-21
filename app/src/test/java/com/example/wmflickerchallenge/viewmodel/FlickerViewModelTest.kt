package com.example.wmflickerchallenge.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.wmflickerchallenge.intent.FlickerIntent
import com.example.wmflickerchallenge.model.data.FlickerResponse
import com.example.wmflickerchallenge.model.data.Items
import com.example.wmflickerchallenge.model.data.Media
import com.example.wmflickerchallenge.model.repo.FlickerRepository
import com.example.wmflickerchallenge.state.FlickerState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.timeout
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class FlickerViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FlickerViewModel
    private lateinit var repository: FlickerRepository
    private lateinit var photoStateObserver: Observer<FlickerState>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = mock(FlickerRepository::class.java)
        viewModel = FlickerViewModel(repository)
        photoStateObserver = mock(Observer::class.java) as Observer<FlickerState>
        viewModel.photoState.observeForever(photoStateObserver)
    }

    @After
    fun tearDown() {
        viewModel.photoState.removeObserver(photoStateObserver)
    }

    @Test
    fun `fetchPhotosByTag emits Loading and Success states`() {
        val photos =  FlickerResponse()

        // Mock repository behavior:
        `when`(repository.getPhotos("test")).thenReturn(
            flow {
                emit(FlickerState.Loading)
                withContext(Dispatchers.IO) {
                    TimeUnit.MILLISECONDS.sleep(100)
                } // Simulate a delay
                emit(FlickerState.Success(photos))
            }
        )

        // Trigger fetching data:
        viewModel.handleIntent(FlickerIntent.FetchFlickerData(tag = "test"))

        // Verify state transitions:
        verify(photoStateObserver, timeout(1000)).onChanged(FlickerState.Loading)
        verify(photoStateObserver, timeout(1000)).onChanged(FlickerState.Success(photos))
        verifyNoMoreInteractions(photoStateObserver)
    }

    @Test
    fun `fetchPhotosByTag emits Error state when repository throws exception`() {
        val exception = Exception("Network error")
        `when`(repository.getPhotos("test")).thenThrow(exception)

        viewModel.handleIntent(FlickerIntent.FetchFlickerData(tag = "test"))

        verify(photoStateObserver, timeout(1000)).onChanged(FlickerState.Error(exception.message ?: "Unknown error"))
        verifyNoMoreInteractions(photoStateObserver)
    }

    @Test
    fun `searchQuery updates with handleIntent`() {
        val testQuery = "test"
        viewModel.handleIntent(FlickerIntent.FetchFlickerData(tag = testQuery))

        assertEquals(testQuery, viewModel.searchQuery.value)
    }
}