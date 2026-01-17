package com.example.mycopa.data.data.repository

import com.example.mycopa.data.data.source.MatchesDataSource
import com.example.mycopa.data.remote.extensions.getOrThrowDomainError
import com.example.mycopa.data.remote.mapper.toDomain
import com.example.mycopa.data.remote.model.MatchRemote
import com.example.mycopa.domain.model.MatchDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class MatchesRepositoryImpl(
    private val localDataSource: MatchesDataSource.Local,
    private val remoteDataSource: MatchesDataSource.Remote
) : MatchesRepository {

    override suspend fun getMatches(): Flow<List<MatchDomain>> = flow<List<MatchDomain>> {
        val result: Result<List<MatchRemote>> = remoteDataSource.getMatches()

        // Agora o compilador reconhece o Result e a extensão global funciona
        val remoteMatches = result.getOrThrowDomainError().toDomain()
        emit(remoteMatches)
    }.combine(localDataSource.getActiveNotificationIds()) { matches: List<MatchDomain>, ids: Set<String> ->
        matches.map { it.copy(notificationEnabled = ids.contains(it.id)) }
    }

    override suspend fun enableNotificationFor(id: String) =
        localDataSource.enableNotificationFor(id)

    override suspend fun disableNotificationFor(id: String) =
        localDataSource.disableNotificationFor(id)
}