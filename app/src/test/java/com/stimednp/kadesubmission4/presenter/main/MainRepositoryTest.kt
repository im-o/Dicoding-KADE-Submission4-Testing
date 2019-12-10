package com.stimednp.kadesubmission4.presenter.main

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.stimednp.kadesubmission4.model.DataLeagues
import com.stimednp.kadesubmission4.model.ResponseLeagues
import com.stimednp.kadesubmission4.ui.main.IMainView
import com.stimednp.kadesubmission4.ui.main.MainPresenter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by rivaldy on 12/10/2019.
 */

class MainRepositoryTest {
    @Mock
    lateinit var view: IMainView
    @Mock
    lateinit var mainRepository: MainRepository
    @Mock
    lateinit var dataLeagues: ArrayList<DataLeagues>

    private lateinit var mainPresenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainPresenter = MainPresenter(view, mainRepository)
    }

    @After
    fun tearDown() {
        print("Finish testing")
    }

    @Test
    fun getIdListLeagueSucces() {
        mainPresenter.getLeaguesData()
        argumentCaptor<IMainRepositoryCallback<ResponseLeagues>>().apply {
            verify(mainRepository).getIdListLeague(capture())
            firstValue.onDataLoaded(dataLeagues)
        }
        Mockito.verify(view).onShowLoading()
        Mockito.verify(view).onHideLoading()
        Mockito.verify(view).onDataLoaded(dataLeagues)
    }
}