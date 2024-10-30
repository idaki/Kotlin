// WelcomeScreen.kt
package com.serdicagrid.serdicaweatherapp.ui.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.serdicagrid.serdicaweatherapp.databinding.WelcomeScreenFragmentLayoutBinding

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
    AndroidViewBinding(WelcomeScreenFragmentLayoutBinding::inflate) {

    }
}