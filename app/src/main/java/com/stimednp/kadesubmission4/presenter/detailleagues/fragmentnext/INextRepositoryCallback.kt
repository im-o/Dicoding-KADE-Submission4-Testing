package com.stimednp.kadesubmission4.presenter.detailleagues.fragmentnext

import com.stimednp.kadesubmission4.model.DataEventLeagues
import com.stimednp.kadesubmission4.model.DataTeamsBadge

/**
 * Created by rivaldy on 12/8/2019.
 */

interface INextRepositoryCallback<T> {
    fun onDataLoaded(data: ArrayList<DataEventLeagues>, itemsH: ArrayList<DataTeamsBadge>, itemsA: ArrayList<DataTeamsBadge>)
    fun onDataError()
}