package com.example.nooro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.nooro.api.OpenWeatherApi
import com.example.nooro.data.WeatherResponse
import com.example.nooro.utils.ApiState
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.roundToInt

@Composable
fun WeatherView(
    modifier: Modifier = Modifier,
    results: StateFlow<ApiState<WeatherResponse>>
) {
    val response by results.collectAsStateWithLifecycle()
    val state = response

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (state) {
            is ApiState.Failure -> {}
            is ApiState.Loading -> {
                CircularProgressIndicator()
            }

            is ApiState.Success -> {

                val cityName = state.data.name
                val currentTemp = "${state.data.main.temp.roundToInt()} \u2109"
                val weather = state.data.weather.firstNotNullOfOrNull { it }

                weather?.let {
                    WeatherImage(
                        modifier = modifier,
                        url = it.icon
                    )

                    Text(
                        text = "${it.main}, ${it.description}",
                        modifier = modifier
                    )
                }

                Text(
                    text = cityName,
                    modifier = modifier
                )

                Text(
                    text = currentTemp,
                    modifier = modifier
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun WeatherImage(
    modifier: Modifier = Modifier,
    url: String
) {
    val iconURL = OpenWeatherApi.ICON_URL.format(url)
    val painter = rememberImagePainter(
        data = iconURL,
        builder = {
            placeholder(R.drawable.ic_loader)
            error(R.drawable.ic_error_24)
            crossfade(enable = true)
        }
    )
    Image(
        modifier = modifier,
        painter = painter,
        contentDescription = "Weather Image"
    )
}