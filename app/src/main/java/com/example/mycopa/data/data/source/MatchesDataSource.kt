package com.example.mycopa.data.data.source

import com.example.mycopa.data.remote.model.MatchRemote
import kotlinx.coroutines.flow.Flow

sealed interface MatchesDataSource {
    interface Remote : MatchesDataSource {
        suspend fun getMatches(): Result<List<MatchRemote>>
    }

    interface Local : MatchesDataSource {
        fun getActiveNotificationIds(): Flow<Set<String>>
        suspend fun enableNotificationFor(id: String)
        suspend fun disableNotificationFor(id: String)
    }
}