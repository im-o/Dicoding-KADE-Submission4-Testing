package com.stimednp.kadesubmission4.ui.detailleagues.fragmentnext

import com.stimednp.kadesubmission4.model.DataEventLeagues
import com.stimednp.kadesubmission4.model.DataTeamsBadge
import com.stimednp.kadesubmission4.model.ResponseEvents
import com.stimednp.kadesubmission4.presenter.detailleagues.fragmentnext.INextRepositoryCallback
import com.stimednp.kadesubmission4.presenter.detailleagues.fragmentnext.NextRepository

/**
 * Created by rivaldy on 12/8/2019.
 */

class NextMatchPresenter(private val view: INextMatchView, private val nextRepository: NextRepository): INextMatchPresenter {
    override fun getNextMatch(idLeague: String) {
        view.onShowLoading()
        nextRepository.getNextMatch(idLeague, object : INextRepositoryCallback<ResponseEvents>{
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