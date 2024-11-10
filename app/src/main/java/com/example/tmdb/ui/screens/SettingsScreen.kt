package com.example.tmdb.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tmdb.data.models.AppTheme
import com.example.tmdb.ui.components.MovieTopBar
import com.example.tmdb.ui.viewmodels.SettingsViewModel

@Composable
fun SettingsScreen(onBackButtonClick: () -> Unit, themeChanged: (AppTheme) -> Unit, viewModel: SettingsViewModel = hiltViewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MovieTopBar("Settings", onBackButtonClick, null, null) }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            val theme by viewModel.theme.collectAsStateWithLifecycle()

            Option(label = "System default theme", selected = theme == AppTheme.SYSTEM) {
                viewModel.updateTheme(AppTheme.SYSTEM)
                themeChanged(AppTheme.SYSTEM)
            }
            Option(label = "Light", selected = theme == AppTheme.LIGHT) {
                viewModel.updateTheme(AppTheme.LIGHT)
                themeChanged(AppTheme.LIGHT)
            }
            Option(label = "Dark", selected = theme == AppTheme.DARK) {
                viewModel.updateTheme(AppTheme.DARK)
                themeChanged(AppTheme.DARK)
            }
        }
    }
}

@Composable
private fun Option(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 16.dp).clickable { onClick() }, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = onClick)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label)
    }
}