package com.example.mycopa.featureview.features

import com.example.mycopa.domain.model.MatchDomain

data class MainUiState(
    val matches: List<MatchDomain> = emptyList(),
    val isLoading: Boolean = false
)

sealed interface MainUiAction {
    object Unexpected : MainUiAction
    data class EnableNotification(val match: MatchDomain) : MainUiAction
    data class DisableNotification(val match: MatchDomain) : MainUiAction
}