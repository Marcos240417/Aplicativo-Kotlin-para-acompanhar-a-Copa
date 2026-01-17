package com.example.mycopa.domain.usecase

import com.example.mycopa.data.data.repository.MatchesRepository

class GetMatchesUseCase(
    private val repository: MatchesRepository
) {
    suspend operator fun invoke() = repository.getMatches()
}