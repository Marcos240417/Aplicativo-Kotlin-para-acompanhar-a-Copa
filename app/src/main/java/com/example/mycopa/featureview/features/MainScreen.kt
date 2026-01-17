package com.example.mycopa.featureview.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mycopa.domain.extensions.getDate // Importante para formatar a data
import com.example.mycopa.domain.model.MatchDomain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    matches: List<MatchDomain>,
    onNotificationClick: (MatchDomain) -> Unit
) {
    // Scaffold fornece a estrutura básica do Material 3 com suporte a TopBar
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MyCopa - Partidas") }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(matches) { match ->
                    // Chama o componente individual para cada partida
                    MatchItem(match, onNotificationClick)
                }
            }
        }
    }
}

@Composable
fun MatchItem(
    match: MatchDomain,
    onNotificationClick: (MatchDomain) -> Unit
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(modifier = Modifier.height(180.dp)) {
            // Imagem do estádio via Coil
            AsyncImage(
                model = match.stadium.image,
                contentDescription = "Estádio ${match.stadium.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Camada de conteúdo (Foreground)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Linha superior: Ícone de Notificação (Material Icons)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    val icon = if (match.notificationEnabled)
                        Icons.Default.NotificationsActive
                    else
                        Icons.Default.Notifications

                    Icon(
                        imageVector = icon,
                        contentDescription = "Alternar Notificação",
                        tint = if (match.notificationEnabled) Color.Yellow else Color.White,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { onNotificationClick(match) }
                    )
                }

                // Conteúdo central/inferior: Data e Placar
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // USO DA FUNÇÃO getDate(): Resolve o aviso de "never used"
                    Text(
                        text = "${match.date.getDate()} - ${match.name}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = match.team1.flag, style = MaterialTheme.typography.headlineMedium)
                        Text(text = "  X  ", style = MaterialTheme.typography.titleLarge, color = Color.White)
                        Text(text = match.team2.flag, style = MaterialTheme.typography.headlineMedium)
                    }
                }
            }
        }
    }
}