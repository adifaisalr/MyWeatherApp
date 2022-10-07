package com.adifaisalr.myweather.presentation.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.adifaisalr.myweather.MainCoroutineRule
import com.adifaisalr.myweather.domain.model.CurrentWeather
import com.adifaisalr.myweather.domain.model.DataHolder
import com.adifaisalr.myweather.domain.usecase.GetCurrentWeatherUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for the implementation of [HomeViewModel]
 */
@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val getCurrentWeatherUseCase = mockk<GetCurrentWeatherUseCase>(relaxed = true)

    // Subject under test
    private lateinit var viewModel: HomeViewModel

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // observers
    private lateinit var currentWeatherObserver: Observer<DataHolder<CurrentWeather>>

    @Before
    fun setupViewModel() {
        viewModel = spyk(HomeViewModel(getCurrentWeatherUseCase))
        viewModel.lat = 123.0
        viewModel.lon = 123.0
    }

    @Test
    fun `getCurrentWeather should set DataHolder to loading then set result`() {
        runBlockingTest {
            // given
            observeCurrentWeather()

            val mockData = mockk<CurrentWeather>()
            val mockResult = DataHolder.Success(mockData)
            coEvery {
                getCurrentWeatherUseCase.invoke(any(), any())
            } answers {
                mockResult
            }

            // when
            viewModel.getCurrentWeather()

            // then
            verifyOrder {
                currentWeatherObserver.onChanged(DataHolder.Loading)
                currentWeatherObserver.onChanged(mockResult)
            }
        }
    }

    /** Observers */
    private fun observeCurrentWeather() {
        currentWeatherObserver = mockk(relaxed = true)
        viewModel.currentWeatherResult.observeForever(currentWeatherObserver)
    }
}
