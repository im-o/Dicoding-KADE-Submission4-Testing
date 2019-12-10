package com.stimednp.kadesubmission4.presenter.search

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import com.stimednp.kadesubmission4.model.DataEventLeagues
import com.stimednp.kadesubmission4.model.DataTeamsBadge
import com.stimednp.kadesubmission4.model.ResponseSearch
import com.stimednp.kadesubmission4.ui.search.ISearchView
import com.stimednp.kadesubmission4.ui.search.SearchPresenter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by rivaldy on 12/10/2019.
 */

class SearchRepositoryTest {
    @Mock
    lateinit var view: ISearchView
    @Mock
    lateinit var searchRepository: SearchRepository
    @Mock
    lateinit var dataEventLeagues: ArrayList<DataEventLeagues>
    @Mock
    lateinit var itemsH: ArrayList<DataTeamsBadge>
    @Mock
    lateinit var itemsA: ArrayList<DataTeamsBadge>

    private lateinit var searchPresenter: SearchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchPresenter = SearchPresenter(view, searchRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getSearchDataSuccess() {
        val queryText = "Arsenal"
        searchPresenter.getSearchEvent(queryText)
        argumentCaptor<ISearchRepositoryCallback<ResponseSearch>>().apply {
            verify(searchRepository).getSearchData(eq(queryText), capture())
            firstValue.onDataLoaded(dataEventLeagues, itemsH, itemsA)
        }
        Mockito.verify(view).onShowLoading()
        Mockito.verify(view).onHideLoading()
        Mockito.verify(view).onDataLoaded(dataEventLeagues, itemsH, itemsA)
    }

    @Test
    fun getSearchDataError() {
        val queryText = ""
        searchPresenter.getSearchEvent(queryText)
        argumentCaptor<ISearchRepositoryCallback<ResponseSearch>>().apply {
            verify(searchRepository).getSearchData(eq(queryText), capture())
            firstValue.onDataError()
        }
        Mockito.verify(view).onShowLoading()
        Mockito.verify(view).onDataError()
        Mockito.verify(view).onHideLoading()
    }
}