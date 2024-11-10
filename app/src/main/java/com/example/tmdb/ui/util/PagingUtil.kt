package com.example.tmdb.ui.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

fun LazyListScope.stateLoader(loadState : CombinedLoadStates) {
    when (val state = loadState.refresh) { //FIRST LOAD
        is LoadState.Error -> {
            item {
                SimplePagerError(state.error.message ?: "Unknown error")
            }
        }

        is LoadState.Loading -> { // Loading UI
            item {
                SimplePagerLoader()
            }
        }

        else -> {}
    }

    when (val state = loadState.append) { // Pagination
        is LoadState.Error -> {
            item {
                SimplePagerError(state.error.message ?: "Unknown error")
            }
        }

        is LoadState.Loading -> { // Pagination Loading UI
            item {
                SimplePagerLoader()
            }
        }

        else -> {}
    }
}

@Composable
fun SimplePagerLoader() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Loading")
        CircularProgressIndicator()
    }
}

@Composable
fun SimplePagerError(error: String) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Error: $error")
    }
}