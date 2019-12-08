package com.stimednp.kadesubmission4.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by rivaldy on 12/8/2019.
 */


data class DataEventLeagues(
    var idEvent: String?,
    var idLeague: String?,
    var idHomeTeam: Int?,
    var idAwayTeam: Int?,
    var strEvent: String?,
    var strSport: String?,
    var strLeague: String?,
    var strHomeTeam: String?,
    var strAwayTeam: String?,
    var intHomeScore: Int?,
    var intAwayScore: Int?,
    var strHomeGoalDetails: String?,

    var strHomeRedCards: String?,
    var strHomeYellowCards: String?,
    var strHomeLineupGoalkeeper: String?,
    var strHomeLineupDefense: String?,
    var strHomeLineupMidfield: String?,
    var strHomeLineupForward: String?,
    var strHomeLineupSubstitutes: String?,
    var strHomeFormation: String?,

    var strAwayRedCards: String?,
    var strAwayYellowCards: String?,
    var strAwayGoalDetails: String?,
    var strAwayLineupGoalkeeper: String?,
    var strAwayLineupDefense: String?,
    var strAwayLineupMidfield: String?,
    var strAwayLineupForward: String?,
    var strAwayLineupSubstitutes: String?,
    var strAwayFormation: String?,

    var intHomeShots: Int?,
    var intAwayShots: Int?,
    var dateEvent: String?,
    var strTime: String?
)
