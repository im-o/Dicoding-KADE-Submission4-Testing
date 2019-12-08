package com.stimednp.kadesubmission4.presenter.search

import com.stimednp.kadesubmission4.model.DataEventLeagues
import com.stimednp.kadesubmission4.model.DataTeamsBadge

/**
 * Created by rivaldy on 12/8/2019.
 */

interface ISearchRepositoryCallback<T> {
    fun onDataLoaded(data: ArrayList<DataEventLeagues>, teamH: ArrayList<DataTeamsBadge>, teamA: ArrayList<DataTeamsBadge>)
    fun onDataError()
}