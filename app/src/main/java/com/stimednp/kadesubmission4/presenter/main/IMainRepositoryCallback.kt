package com.stimednp.kadesubmission4.presenter.main

import com.stimednp.kadesubmission4.model.DataLeagues

/**
 * Created by rivaldy on 12/8/2019.
 */

interface IMainRepositoryCallback<T> {
    fun onDataLoaded(data: ArrayList<DataLeagues>)
    fun onDataError()
}