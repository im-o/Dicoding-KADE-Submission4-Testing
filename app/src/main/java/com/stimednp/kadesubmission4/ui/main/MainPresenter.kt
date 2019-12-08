package com.stimednp.kadesubmission4.ui.main

import com.stimednp.kadesubmission4.model.Leagues
import com.stimednp.kadesubmission4.model.ResponseLeagues
import com.stimednp.kadesubmission4.presenter.main.IMainRepositoryCallback
import com.stimednp.kadesubmission4.presenter.main.MainRepository

/**
 * Created by rivaldy on 12/8/2019.
 */

class MainPresenter(private val view: IMainView, private val mainRepository: MainRepository): IMainPresenter {
    override fun getLeaguesData() {
        view.onShowLoading()
        mainRepository.getIdListLeague(object: IMainRepositoryCallback<ResponseLeagues> {
            override fun onDataLoaded(data: ArrayList<Leagues>) {
                view.onDataLoaded(data)
            }
            override fun onDataError() {
                view.onDataError()
            }
        })
    }
}