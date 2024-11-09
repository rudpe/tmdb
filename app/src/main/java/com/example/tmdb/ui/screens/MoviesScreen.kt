package com.example.tmdb.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tmdb.Greeting
import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.models.MovieEntity
import com.example.tmdb.ui.util.stateLoader
import com.example.tmdb.ui.viewmodels.MoviesViewModel

@Composable
fun MoviesScreen(viewModel: MoviesViewModel = hiltViewModel()) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        val pager = viewModel.moviesPager.collectAsLazyPagingItems()
        LazyColumn(Modifier.padding(innerPadding)) {
            items(pager.itemCount) { index ->
                pager[index]?.let { item ->
                    MovieItem(movie = item)
                }

            }
            stateLoader(state = pager.loadState)
        }
    }
}

@Composable
fun MovieItem(movie: MovieEntity) {
    Column {
        Row(Modifier.padding(16.dp)) {
            Text(text = movie.id.toString())
            Spacer(modifier = Modifier.weight(1f))
            Text(text = movie.title)

        }
        HorizontalDivider(Modifier.fillMaxWidth())
    }
}