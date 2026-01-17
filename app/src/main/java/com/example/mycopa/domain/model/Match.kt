package com.example.mycopa.domain.model

import java.time.LocalDateTime

// Remova os typealias daqui, pois eles conflitam com as data classes abaixo
data class MatchDomain(
    val id: String,
    val name: String,
    val stadium: StadiumDomain,
    val team1: TeamDomain,
    val team2: TeamDomain,
    val date: LocalDateTime,
    val notificationEnabled: Boolean = false,
)

data class StadiumDomain(
    val name: String,
    val image: String
)


data class TeamDomain(
    val flag: String,
    val displayName: String
)
