package com.stimednp.kadesubmission4.ui.detailevents

import com.stimednp.kadesubmission4.model.DataEventLeagues
import com.stimednp.kadesubmission4.model.ResponseEvents
import com.stimednp.kadesubmission4.presenter.detailematch.DetailsERepository
import com.stimednp.kadesubmission4.presenter.detailematch.IDetailsERepositoryCallback

/**
 * Created by rivaldy on 12/9/2019.
 */

class DetailsEPresenter(private val view: IDetailsEView, private val detailsERepository: DetailsERepository) : IDetailsEPresenter {
    override fun getEventsDetail(id: String?) {
        view.onShowLoading()
        detailsERepository.getDetailEvent(id.toString(), object : IDetailsERepositoryCallback<ResponseEvents> {
            override fun onDataLoaded(data: DataEventLeagues) {
                view.onDataLoaded(data)
            }

            override fun onDataError() {
                view.onDataError()
            }

        })
    }
}