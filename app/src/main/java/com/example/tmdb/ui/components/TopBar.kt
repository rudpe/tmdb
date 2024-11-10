package com.example.tmdb.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopBar(
    title: String = "",
    onBackButtonClick: (() -> Unit)?,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        },
        navigationIcon = if (onBackButtonClick != null) {
            {
                IconButton(onClick = onBackButtonClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "")
                }
            }
        } else {
            {}
        }
    )
}