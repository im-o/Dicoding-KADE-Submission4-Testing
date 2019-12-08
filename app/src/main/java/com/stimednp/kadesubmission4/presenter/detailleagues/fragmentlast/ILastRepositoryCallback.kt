package com.stimednp.kadesubmission4.presenter.detailleagues.fragmentlast

import com.stimednp.kadesubmission4.model.DataEventLeagues
import com.stimednp.kadesubmission4.model.DataTeamsBadge

/**
 * Created by rivaldy on 12/8/2019.
 */

interface ILastRepositoryCallback<T> {
    fun onDataLoaded(data: ArrayList<DataEventLeagues>, itemsH: ArrayList<DataTeamsBadge>, itemsA: ArrayList<DataTeamsBadge>)
    fun onDataError()
}