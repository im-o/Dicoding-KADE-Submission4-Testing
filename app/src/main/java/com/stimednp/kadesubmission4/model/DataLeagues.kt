package com.stimednp.kadesubmission4.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by rivaldy on 12/8/2019.
 */

@Parcelize
data class DataLeagues(
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