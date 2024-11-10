package com.example.tmdb.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.example.core.utils.ImageUtil
import com.example.tmdb.data.models.MovieEntity
import com.example.tmdb.ui.components.MovieTopBar
import com.example.tmdb.ui.util.stateLoader
import com.example.tmdb.ui.viewmodels.MoviesViewModel

@Composable
fun MoviesScreen(onMovieClick: (Long) -> Unit, onInfoClick: () -> Unit, onSettingsClick: () -> Unit, viewModel: MoviesViewModel = hiltViewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MovieTopBar("Movies", null, onSettingsClick, onInfoClick) }
    ) { innerPadding ->
        val favorites by viewModel.favorites.collectAsStateWithLifecycle()
        val pager = viewModel.moviesPager.collectAsLazyPagingItems()
        LazyColumn(Modifier.padding(innerPadding)) {
            items(pager.itemCount) { index ->
                pager[index]?.let { item ->
                    MovieItem(
                        movie = item,
                        isFavorite = favorites.firstOrNull { it.id == item.id }?.isFavorite ?: false,
                        onFavoriteClick = { isFavorite ->
                            viewModel.updateFavorite(item.id, isFavorite)
                        },
                        onMovieClick = onMovieClick
                    )
                }
            }
            stateLoader(loadState = pager.loadState)
        }
    }
}

@Composable
fun MovieItem(movie: MovieEntity, isFavorite: Boolean, onFavoriteClick: (Boolean) -> Unit, onMovieClick: (Long) -> Unit) {
    Column {
        Row(Modifier.padding(16.dp).clickable { onMovieClick(movie.id) }) {
            SubcomposeAsyncImage(
                model = ImageUtil.getImagePath(movie.posterPath),
                contentDescription = null,
                modifier = Modifier
                    .height(60.dp),
                loading = {
                    Column(
                        Modifier
                            .padding(26.dp)
                            .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            )
            Column(Modifier.weight(1f)) {
                Text(modifier = Modifier.padding(start = 16.dp, end = 32.dp), text = movie.title, fontWeight = FontWeight.Bold)
                IconButton(modifier = Modifier.size(20.dp).align(Alignment.End), onClick = { onFavoriteClick(isFavorite.not()) }) {
                    Icon(if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder, contentDescription = "Favorite")
                }
            }
        }
        HorizontalDivider(Modifier.fillMaxWidth())
    }
}