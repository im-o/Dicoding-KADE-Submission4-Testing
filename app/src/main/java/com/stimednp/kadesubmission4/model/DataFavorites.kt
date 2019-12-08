package com.stimednp.kadesubmission4.model

/**
 * Created by rivaldy on 11/27/2019.
 */

data class DataFavorites(
    val id: Long?,
    val idEvent: String?,
    val dateEvent: String?,
    val strTime: String?,
    val strEvent: String?,
    val strSport: String?,
    val strLeague: String?,
    val strHomeTeam: String?,
    val strAwayTeam: String?,
    val intHomeScore: Int?,
    val intAwayScore: Int?,
    val strBadgeH: String?,
    val strBadgeA: String?
) {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val ID_EVENT: String = "ID_EVENT"
        const val STR_DATEEV: String = "STR_DATEEV"
        const val STR_TIMEEV: String = "STR_TIMEEV"
        const val STR_EVENT: String = "STR_EVENT"
        const val STR_SPORT: String = "STR_SPORT"
        const val STR_LEAGUE: String = "STR_LEAGUE"
        const val STR_TEAMH: String = "STR_TEAMH"
        const val STR_TEAMA: String = "STR_TEAMA"
        const val INT_SCOREH: String = "INT_SCOREH"
        const val INT_SCOREA: String = "INT_SCOREA"
        const val STR_BADGEH: String = "STR_BADGEH"
        const val STR_BADGEA: String = "STR_BADGEA"
    }
}