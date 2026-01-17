package com.example.mycopa.data.remote.source

import com.example.mycopa.data.data.source.MatchesDataSource
import com.example.mycopa.data.remote.model.MatchRemote
import com.example.mycopa.data.remote.services.MatchesServices

class MatchDataSourceRemote(
    private val service: MatchesServices
) : MatchesDataSource.Remote {

    override suspend fun getMatches(): Result<List<MatchRemote>> = runCatching {
        // O Retrofit agora retorna a lista, e o runCatching a transforma em Result
        service.getMatches()
    }
}