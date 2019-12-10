package com.stimednp.kadesubmission4.ui.search

import com.stimednp.kadesubmission4.model.DataEventLeagues
import com.stimednp.kadesubmission4.model.DataTeamsBadge
import com.stimednp.kadesubmission4.model.ResponseSearch
import com.stimednp.kadesubmission4.presenter.search.ISearchRepositoryCallback
import com.stimednp.kadesubmission4.presenter.search.SearchRepository

/**
 * Created by rivaldy on 12/8/2019.
 */

class SearchPresenter(private val view: ISearchView, private val searchRepository: SearchRepository) : ISearchPresenter {
    override fun getSearchEvent(text: String) {
        view.onShowLoading()
        searchRepository.getSearchData(text, object : ISearchRepositoryCallback<ResponseSearch> {
            override fun onDataLoaded(data: ArrayList<DataEventLeagues>, teamH: ArrayList<DataTeamsBadge>, teamA: ArrayList<DataTeamsBadge>) {
                view.onDataLoaded(data, teamH, teamA)
                view.onHideLoading()
            }

            override fun onDataError() {
                view.onDataError()
                view.onHideLoading()
            }

        })
    }
}