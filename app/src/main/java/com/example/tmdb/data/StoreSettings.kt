package com.example.tmdb.data

import android.content.Context
import com.example.tmdb.data.models.AppTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val THEME_KEY = "theme_key"

class StoreSettings @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences = context.getSharedPreferences("movies_pref", Context.MODE_PRIVATE)

    fun getTheme(): AppTheme {
        val value = sharedPreferences.getString(THEME_KEY, AppTheme.SYSTEM.name) ?: AppTheme.SYSTEM.name
        return AppTheme.valueOf(value)
    }

    fun updateTheme(theme: AppTheme) {
        sharedPreferences.edit().putString(THEME_KEY, theme.name).apply()
    }
}
