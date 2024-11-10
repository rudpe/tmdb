package com.example.tmdb.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
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
import coil.compose.SubcomposeAsyncImage
import com.example.core.utils.ImageUtil
import com.example.tmdb.ui.components.MovieTopBar
import com.example.tmdb.ui.viewmodels.DetailViewModel

@Composable
fun DetailScreen(onBackButtonClick: () -> Unit, viewModel: DetailViewModel = hiltViewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MovieTopBar("Detail", onBackButtonClick) }
    ) { innerPadding ->
        val movie by viewModel.movie.collectAsStateWithLifecycle()
        val isFavorite by viewModel.favorite.collectAsStateWithLifecycle()
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
            SubcomposeAsyncImage(
                model = ImageUtil.getImagePath(movie?.posterPath.orEmpty()),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .padding(top = 16.dp),
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
            Text(modifier = Modifier.padding(start = 16.dp, end = 32.dp, top = 32.dp), text = movie?.title.orEmpty(), fontWeight = FontWeight.Bold)
            Text(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 32.dp), text = movie?.overview.orEmpty())
            IconButton(modifier = Modifier
                .size(80.dp), onClick = { viewModel.updateFavorite(isFavorite.not()) }) {
                Icon(if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder, contentDescription = "Favorite", Modifier.fillMaxSize())
            }
        }
    }
}