package com.example.mycopa.domain.usecase

import com.example.mycopa.data.data.repository.MatchesRepository

class DisableNotificationUseCase(
    private val repository: MatchesRepository
) {
    suspend operator fun invoke(id: String) {
        repository.disableNotificationFor(id)
    }
}