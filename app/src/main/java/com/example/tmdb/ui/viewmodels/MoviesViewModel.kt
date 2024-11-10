package com.example.tmdb.ui.viewmodels

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tmdb.data.services.MoviesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesService: MoviesService
) : ViewModel() {

    val moviesPager = moviesService.moviesPager.cachedIn(viewModelScope)

    val favorites = moviesService.getFavoritesFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun updateFavorite(id: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            moviesService.updateFavorite(id, isFavorite)
        }
    }
}