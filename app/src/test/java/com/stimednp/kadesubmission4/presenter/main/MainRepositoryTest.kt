package com.stimednp.kadesubmission4.presenter.main

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.stimednp.kadesubmission4.model.DataLeagues
import com.stimednp.kadesubmission4.model.ResponseLeagues
import com.stimednp.kadesubmission4.ui.main.IMainView
import com.stimednp.kadesubmission4.ui.main.MainPresenter
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
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

//    val leagues: ArrayList<DataLeagues> = arrayListOf()
//    val id = "4328"

    @Before
    fun setUp(){
//        leagues.add(DataLeagues(idLeague="4328", strSport="Soccer", strLeague="English Premier League", intFormedYear=null, dateFirstEvent=null, strWebsite=null, strFacebook=null, strTwitter=null, strYoutube=null, strDescriptionEN=null, strBadge=null, strLogo=null, strComplete=null))
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

//    @Test
//    fun filterById() {
//        print("eksekusi filterById\n")
//        mainPresenter.getLeaguesData()
//        argumentCaptor<IMainRepositoryCallback<ResponseLeagues>>().apply {
//            verify(mainRepository, times(1)).filterById(eq(leagues), capture())
//            firstValue.onDataLoaded(dataLeagues)
//        }
//        Mockito.verify(view).onShowLoading()
//        Mockito.verify(view).onHideLoading()
//        Mockito.verify(view).onDataLoaded(dataLeagues)
//    }
//
//
//    @Test
//    fun getDataById() {
//        mainPresenter.getLeaguesData()
//        argumentCaptor<IMainRepositoryCallback<ResponseLeagues>>().apply {
//            verify(mainRepository, times(1)).getDataById(eq(id), capture())
//            firstValue.onDataLoaded(dataLeagues)
//        }
////        Mockito.verify(view).onShowLoading()
////        Mockito.verify(view).onHideLoading()
////        Mockito.verify(view).onDataLoaded(dataLeagues)
//    }
//
//
//    @Test
//    fun filterList() {
//        print("eksekusi filterById\n")
//        mainPresenter.getLeaguesData()
//        argumentCaptor<IMainRepositoryCallback<ResponseLeagues>>().apply {
//            verify(mainRepository, times(1)).filterList(capture())
//            secondValue.onDataLoaded(dataLeagues)
//        }
//        Mockito.verify(view).onShowLoading()
//        Mockito.verify(view).onHideLoading()
//        Mockito.verify(view).onDataLoaded(dataLeagues)
//    }
}