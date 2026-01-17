package com.example.mycopa.data.remote.mapper

import com.example.mycopa.data.remote.model.MatchRemote
import com.example.mycopa.data.remote.model.StadiumRemote
import com.example.mycopa.domain.model.MatchDomain
import com.example.mycopa.domain.model.StadiumDomain
import com.example.mycopa.domain.model.TeamDomain
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale

internal fun List<MatchRemote>.toDomain() = map { it.toDomain() }

fun MatchRemote.toDomain(): MatchDomain {
    return MatchDomain(
        id = "${this.team1}-${this.team2}",
        name = this.name,
        team1 = this.team1.toTeam(),
        team2 = this.team2.toTeam(),
        stadium = this.stadium.toDomain(),
        date = this.date.toLocalDateTime(),
        notificationEnabled = false
    )
}

private fun Date.toLocalDateTime(): LocalDateTime {
    return toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
}

private fun String.toTeam(): TeamDomain {
    val locale = Locale.Builder().setRegion(this).build()
    return TeamDomain(
        flag = getTeamFlag(this),
        displayName = locale.getDisplayCountry(Locale("pt", "BR"))
    )
}

private fun getTeamFlag(team: String): String {
    return team.uppercase().map { char ->
        String(Character.toChars(char.code + 127397))
    }.joinToString("")
}

fun StadiumRemote.toDomain(): StadiumDomain {
    return StadiumDomain(
        name = name,
        image = image
    )
}