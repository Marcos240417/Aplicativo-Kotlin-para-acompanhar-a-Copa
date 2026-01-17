package com.example.mycopa.data.remote.services

import com.example.mycopa.data.remote.model.MatchRemote
import retrofit2.http.GET

interface MatchesServices {
    @GET("api.json")
    suspend fun getMatches(): List<MatchRemote>
}