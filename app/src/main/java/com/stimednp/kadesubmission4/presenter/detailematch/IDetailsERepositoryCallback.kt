package com.stimednp.kadesubmission4.presenter.detailematch

import com.stimednp.kadesubmission4.model.DataEventLeagues

/**
 * Created by rivaldy on 12/9/2019.
 */

interface IDetailsERepositoryCallback<T> {
    fun onDataLoaded(data: DataEventLeagues)
    fun onDataError()
}