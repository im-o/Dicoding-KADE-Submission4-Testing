package com.stimednp.kadesubmission4.presenter.detailleagues

import com.stimednp.kadesubmission4.model.DataLeagues

/**
 * Created by rivaldy on 12/8/2019.
 */

interface IDetailsLRepositoryCallback<T> {
    fun onDataLoaded(data: DataLeagues)
    fun onDataError()
}