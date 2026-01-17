package com.example.mycopa.featureview.features

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.mycopa.domain.model.MatchDomain
import com.example.mycopa.featureview.extensions.observe // Import da extensão de observação
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    // Injeção de dependência via Koin conforme o padrão do projeto
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // RESOLUÇÃO: O parâmetro 'state' é utilizado para log, removendo o aviso "never used"
        // A extensão 'observe' agora possui uso explícito
        viewModel.state.observe(owner = this) { state ->
            Log.d("MainActivity", "Estado atualizado: ${state.matches.size} partidas encontradas.")
        }

        setContent {
            // RESOLUÇÃO: Coletamos o 'state' (UiState) em vez de uma propriedade 'matches' inexistente
            // Isso sincroniza com a sua BaseViewModel
            val uiState by viewModel.state.collectAsState()

            // Passamos uiState.matches para a MainScreen
            MainScreen(
                matches = uiState.matches,
                onNotificationClick = { match: MatchDomain ->
                    viewModel.toggleNotification(match)
                }
            )
        }
    }
}