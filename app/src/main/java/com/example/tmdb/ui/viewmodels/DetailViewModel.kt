package com.example.tmdb.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tmdb.data.services.MoviesService
import com.example.tmdb.ui.navigation.MainRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val moviesService: MoviesService
) : ViewModel() {

    private val movieId = savedStateHandle.toRoute<MainRoutes.Detail>().movieId

    val movie = moviesService.geMovieById(movieId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    val favorite = moviesService.getFavorite(movieId)
        .map { it ?: false }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun updateFavorite(isFavorite: Boolean) {
        viewModelScope.launch {
            moviesService.updateFavorite(movieId, isFavorite)
        }
    }
}