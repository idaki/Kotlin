// WelcomeScreen.kt
package com.serdicagrid.serdicaweatherapp.ui.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.serdicagrid.serdicaweatherapp.databinding.MyFragmentLayoutBinding

@Composable
fun WelcomeScreen() {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            FragmentInCompose()
        }
    }
}

@Composable
fun FragmentInCompose() {
    AndroidViewBinding(MyFragmentLayoutBinding::inflate) {

    }
}