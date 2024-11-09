package com.example.tmdb.ui.viewmodels

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tmdb.data.services.MoviesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    moviesService: MoviesService
) : ViewModel() {

    val moviesPager = moviesService.moviesPager.cachedIn(viewModelScope)
}