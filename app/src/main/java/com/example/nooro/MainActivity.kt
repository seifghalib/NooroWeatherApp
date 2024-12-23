package com.example.nooro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.nooro.ui.screen.HomeViewModel
import com.example.nooro.ui.screen.SearchTopBar
import com.example.nooro.ui.screen.WeatherView
import com.example.nooro.ui.theme.NooroTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModule by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NooroTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        SearchTopBar()
                    }) { innerPadding ->
                    WeatherView(
                        modifier = Modifier.padding(innerPadding),
                        results = viewModule.resultFlow
                    )
                }
            }
        }
    }
}
