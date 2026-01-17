package com.example.mycopa.data.local.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.mycopa.data.data.source.MatchesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MatchDataSourceLocal(
    private val dataStore: DataStore<Preferences>
) : MatchesDataSource.Local {

    private val key = stringSetPreferencesKey("notification_ids")

    override fun getActiveNotificationIds(): Flow<Set<String>> =
        dataStore.data.map { preferences ->
            preferences[key] ?: emptySet()
        }

    override suspend fun enableNotificationFor(id: String) {
        dataStore.edit { settings ->
            val currentIds = settings[key] ?: emptySet()
            settings[key] = currentIds + id
        }
    }

    override suspend fun disableNotificationFor(id: String) {
        dataStore.edit { settings ->
            val currentIds = settings[key] ?: return@edit
            settings[key] = currentIds - id
        }
    }
}