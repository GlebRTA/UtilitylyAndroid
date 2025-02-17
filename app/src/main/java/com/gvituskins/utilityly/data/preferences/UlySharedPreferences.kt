package com.gvituskins.utilityly.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.gvituskins.utilityly.domain.models.enums.ThemeType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UlySharedPreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val preferences: SharedPreferences = context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    var themeType: ThemeType
        get() {
            val id = preferences.getInt(THEME_TYPE, ThemeType.SYSTEM.ordinal)
            return ThemeType.entries[id]
        }
        set(value) {
            preferences.edit { editor ->
                editor.putInt(THEME_TYPE, value.ordinal)
            }
        }

    companion object {
        const val PREFERENCES_NAME = "simple_preferences"

        private const val THEME_TYPE = "THEME_TYPE"
    }
}