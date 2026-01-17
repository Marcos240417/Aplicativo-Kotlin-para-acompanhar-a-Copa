package com.example.mycopa.data.data.repository

import com.example.mycopa.domain.model.MatchDomain
import kotlinx.coroutines.flow.Flow

interface MatchesRepository {
    suspend fun getMatches(): Flow<List<MatchDomain>>
    suspend fun enableNotificationFor(id: String)
    suspend fun disableNotificationFor(id: String)
}