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

