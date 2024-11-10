package com.example.tmdb.ui.screens

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tmdb.BuildConfig
import com.example.tmdb.ui.components.MovieTopBar

@Composable
fun InfoScreen(onBackButtonClick: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MovieTopBar("Info", onBackButtonClick, null, null) }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            Text(modifier = Modifier.padding(16.dp), text = "App version: ${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})")
            Text(modifier = Modifier.padding(16.dp), text = "Developer: Rudolf Petras")
            Text(modifier = Modifier.padding(16.dp), text = "Device: ${Build.DEVICE}")

        }
    }
}