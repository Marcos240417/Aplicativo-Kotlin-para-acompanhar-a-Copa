package com.example.mycopa.featureview.features

import androidx.lifecycle.viewModelScope
import com.example.mycopa.domain.model.MatchDomain
import com.example.mycopa.domain.usecase.DisableNotificationUseCase
import com.example.mycopa.domain.usecase.EnableNotificationUseCase
import com.example.mycopa.domain.usecase.GetMatchesUseCase
import com.example.mycopa.featureview.core.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch



class MainViewModel (
    private val getMatchesUseCase: GetMatchesUseCase,
    private val disableNotificationUseCase: DisableNotificationUseCase,
    private val enableNotificationUseCase: EnableNotificationUseCase,
) : BaseViewModel<MainUiState, MainUiAction>(MainUiState()) {

    init {
        fetchMatches()
    }

    private fun fetchMatches() = viewModelScope.launch {
        getMatchesUseCase()
            .catch {
                sendAction(MainUiAction.Unexpected)
            }
            .collect { matches ->
                setState { copy(matches = matches) }
            }
    }

    fun toggleNotification(match: MatchDomain) {
        viewModelScope.launch {
            if (match.notificationEnabled) {
                disableNotificationUseCase(match.id)
                sendAction(MainUiAction.DisableNotification(match))
            } else {
                enableNotificationUseCase(match.id)
                sendAction(MainUiAction.EnableNotification(match))
            }
        }
    }
}