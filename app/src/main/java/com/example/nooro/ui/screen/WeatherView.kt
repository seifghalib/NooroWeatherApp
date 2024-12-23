package com.example.nooro.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.nooro.R
import com.example.nooro.api.OpenWeatherApi
import com.example.nooro.data.WeatherResponse
import com.example.nooro.ui.theme.NooroTheme
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
        horizontalAlignment = Alignment.CenterHorizontally
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
                }

                Text(
                    text = cityName,
                    modifier = modifier,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = currentTemp,
                    modifier = modifier,
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(15.dp))

                WeatherCard("")
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchTopBar() {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        query = text,
        onQueryChange = {
            text = it
        },
        onSearch = {
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = {
            Text("Seach Location")
        },
        trailingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        }
    ) {

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
        modifier = Modifier
            .height(123.dp)
            .height(123.dp),
        painter = painter,
        contentDescription = "Weather Image",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun WeatherCard(
    url: String
) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
        ) {

            Text(
                modifier = Modifier.weight(1f),
                text = "Humidity",
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.weight(1f),
                text = "UV",
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.weight(1f),
                text = "Feels Like",
                textAlign = TextAlign.Center
            )
        }

        Row(
            modifier = Modifier.padding(8.dp)
        ) {

            Text(
                modifier = Modifier.weight(1f),
                text = "Humidity",
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.weight(1f),
                text = "UV",
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.weight(1f),
                text = "Feels Like",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NooroTheme {
        SearchTopBar()
    }
}

