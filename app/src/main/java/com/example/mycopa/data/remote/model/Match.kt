package com.example.mycopa.data.remote.model

import java.util.Date

internal typealias MatchRemote = Match

data class Match(
    val name: String,
    val stadium: Stadium,
    val team1: String,
    val team2: String,
    val date: Date,
    val notificationEnabled: Boolean
)

internal typealias StadiumRemote = Stadium

data class Stadium(
    val name: String,
    val image: String
)