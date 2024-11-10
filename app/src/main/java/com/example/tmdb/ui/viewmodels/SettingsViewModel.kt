package com.example.tmdb.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.tmdb.data.StoreSettings
import com.example.tmdb.data.models.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val storeSettings: StoreSettings
) : ViewModel() {

    private val _theme = MutableStateFlow(storeSettings.getTheme())
    val theme = _theme.asStateFlow()

    fun updateTheme(theme: AppTheme) {
        storeSettings.updateTheme(theme)
        _theme.update { theme }
    }
}