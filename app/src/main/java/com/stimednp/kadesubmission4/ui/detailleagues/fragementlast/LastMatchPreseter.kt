package com.stimednp.kadesubmission4.ui.detailleagues.fragementlast

import com.stimednp.kadesubmission4.model.DataEventLeagues
import com.stimednp.kadesubmission4.model.DataTeamsBadge
import com.stimednp.kadesubmission4.model.ResponseEvents
import com.stimednp.kadesubmission4.presenter.detailleagues.fragmentlast.ILastRepositoryCallback
import com.stimednp.kadesubmission4.presenter.detailleagues.fragmentlast.LastRepository

/**
 * Created by rivaldy on 12/8/2019.
 */

class LastMatchPreseter(private val view: ILastMatchView, private val lastRepository: LastRepository) : ILastMatchPreseter {
    override fun getLastMatch(idLeague: String) {
        view.onShowLoading()
        lastRepository.getLastMatch(idLeague, object : ILastRepositoryCallback<ResponseEvents> {
            override fun onDataLoaded(data: ArrayList<DataEventLeagues>, itemsH: ArrayList<DataTeamsBadge>, itemsA: ArrayList<DataTeamsBadge>) {
                view.onDataLoaded(data, itemsH, itemsA)
                view.onHideLoading()
                view.hideTextEmpty()
            }

            override fun onDataError() {
                view.onDataError()
                view.onHideLoading()
            }
        })
    }

}