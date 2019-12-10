package com.stimednp.kadesubmission4.presenter.detailleagues

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import com.stimednp.kadesubmission4.model.DataLeagues
import com.stimednp.kadesubmission4.model.ResponseLeagues
import com.stimednp.kadesubmission4.ui.detailleagues.DetailsPresenter
import com.stimednp.kadesubmission4.ui.detailleagues.IDetailsLView
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by rivaldy on 12/10/2019.
 */

class DetailsLRepositoryTest {
    @Mock
    lateinit var view: IDetailsLView
    @Mock
    lateinit var detailsLRepository: DetailsLRepository
    @Mock
    lateinit var dataLeagues: DataLeagues

    private lateinit var detailsPresenter: DetailsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailsPresenter = DetailsPresenter(view, detailsLRepository)
    }

    @After
    fun tearDown() {
        print("Finish testing")
    }

    @Test
    fun getDataByIdSucces() {
        val id = "4328"
        detailsPresenter.getLeaguesDetail(id)
        argumentCaptor<IDetailsLRepositoryCallback<ResponseLeagues>>().apply {
            verify(detailsLRepository).getDataById(eq(id), capture())
            firstValue.onDataLoaded(dataLeagues)
        }
        Mockito.verify(view).onShowLoading()
        Mockito.verify(view).onHideLoading()
        Mockito.verify(view).onDataLoaded(dataLeagues)
    }

    @Test
    fun getDataByIdError() {
        val id = ""
        detailsPresenter.getLeaguesDetail(id)
        argumentCaptor<IDetailsLRepositoryCallback<ResponseLeagues>>().apply {
            verify(detailsLRepository).getDataById(eq(id), capture())
            firstValue.onDataError()
        }
        Mockito.verify(view).onShowLoading()
        Mockito.verify(view).onHideLoading()
        Mockito.verify(view).onDataError()
    }
}