package com.stimednp.kadesubmission4.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by rivaldy on 11/10/2019.
 */

class ResponseLeagues {
    var leagues = ArrayList<Leagues>()
}

class ResponseEvents {
    var events = ArrayList<EventsLeagues>()
}

class ResponseTeamsH {
    var teams = ArrayList<TeamsBadge>()
}

class ResponseTeamsA {
    var teams = ArrayList<TeamsBadge>()
}

class ResponseSearch {
    var event = ArrayList<EventsLeagues>()
}

@Parcelize
data class Leagues(
    var idLeague: String?,
    var strSport: String?,
    var strLeague: String?,
    var intFormedYear: Int?,
    var dateFirstEvent: String?,
    var strWebsite: String?,
    var strFacebook: String?,
    var strTwitter: String?,
    var strYoutube: String?,
    var strDescriptionEN: String?,
    var strBadge: String?,
    var strLogo: String?,
    var strComplete: String?
) : Parcelable

@Parcelize
data class EventsLeagues(
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
) : Parcelable

@Parcelize
data class TeamsBadge(
    var idTeam: Int?,
    var strTeamBadge: String?
) : Parcelable