package com.stimednp.kadesubmission4.presenter.detailematch

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import com.stimednp.kadesubmission4.model.DataEventLeagues
import com.stimednp.kadesubmission4.model.DataLeagues
import com.stimednp.kadesubmission4.model.ResponseEvents
import com.stimednp.kadesubmission4.presenter.detailleagues.DetailsLRepository
import com.stimednp.kadesubmission4.ui.detailevents.DetailsEPresenter
import com.stimednp.kadesubmission4.ui.detailevents.IDetailsEView
import com.stimednp.kadesubmission4.ui.detailleagues.DetailsPresenter
import com.stimednp.kadesubmission4.ui.detailleagues.IDetailsLView
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

class DetailsERepositoryTest {
    @Mock
    lateinit var view: IDetailsEView
    @Mock
    lateinit var detailsERepository: DetailsERepository
    @Mock
    lateinit var dataEventLeagues: DataEventLeagues

    private lateinit var detailsEPresenter: DetailsEPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailsEPresenter = DetailsEPresenter(view, detailsERepository)
    }

    @After
    fun tearDown() {
        print("Finish testing")
    }

    @Test
    fun getDetailEventSucces() {
        val idEvent = "441613"
        detailsEPresenter.getEventsDetail(idEvent)
        argumentCaptor<IDetailsERepositoryCallback<ResponseEvents>>().apply {
            verify(detailsERepository).getDetailEvent(eq(idEvent), capture())
            firstValue.onDataLoaded(dataEventLeagues)
        }
        Mockito.verify(view).onShowLoading()
        Mockito.verify(view).onHideLoading()
        Mockito.verify(view).onDataLoaded(dataEventLeagues)
    }

    @Test
    fun getDetailEventError() {
        val idEvent = ""
        detailsEPresenter.getEventsDetail(idEvent)
        argumentCaptor<IDetailsERepositoryCallback<ResponseEvents>>().apply {
            verify(detailsERepository).getDetailEvent(eq(idEvent), capture())
            firstValue.onDataError()
        }
        Mockito.verify(view).onShowLoading()
        Mockito.verify(view).onHideLoading()
        Mockito.verify(view).onDataError()
    }
}