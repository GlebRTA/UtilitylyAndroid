package com.gvituskins.utilityly.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gvituskins.utilityly.domain.models.enums.ThemeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreUtil @Inject constructor(private val context: Context) {
    private val CURRENT_THEME_KEY = intPreferencesKey("CURRENT_THEME")

    fun currentTheme(): Flow<ThemeType> = context.dataStore.data
        .map { preferences ->
            val ordinal = preferences[CURRENT_THEME_KEY] ?: ThemeType.SYSTEM.ordinal
            ThemeType.entries[ordinal]
        }

    suspend fun changeTheme(type: ThemeType) {
        context.dataStore.edit { preferences ->
            preferences[CURRENT_THEME_KEY] = type.ordinal
        }
    }

    private val CURRENT_LOCATION_ID_KEY = intPreferencesKey("CURRENT_LOCATION_ID")

    fun locationId(): Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[CURRENT_LOCATION_ID_KEY] ?: 0
        }

    suspend fun getCurrentLocationId(): Int = locationId().first()

    suspend fun changeLocationId(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[CURRENT_LOCATION_ID_KEY] = id
        }
    }
}
