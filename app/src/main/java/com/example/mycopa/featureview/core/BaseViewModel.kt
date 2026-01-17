package com.example.mycopa.featureview.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<UiState, UiAction>(initialState: UiState) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<UiState> = _state

    private val _action = MutableSharedFlow<UiAction>()
    val action: SharedFlow<UiAction> = _action

    // Propriedade para leitura rápida do estado atual
    val stateValue: UiState
        get() = state.value

    protected fun setState(block: UiState.() -> UiState) {
        // USO DA PROPRIEDADE: Substituímos _state.value por stateValue
        // para remover o aviso de "never used" e centralizar o acesso.
        _state.value = stateValue.block()
    }

    protected fun sendAction(action: UiAction) {
        viewModelScope.launch {
            _action.emit(action)
        }
    }
}