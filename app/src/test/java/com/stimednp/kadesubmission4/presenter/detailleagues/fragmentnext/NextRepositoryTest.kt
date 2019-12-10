package com.stimednp.kadesubmission4.presenter.detailleagues.fragmentnext

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import com.stimednp.kadesubmission4.model.DataEventLeagues
import com.stimednp.kadesubmission4.model.DataTeamsBadge
import com.stimednp.kadesubmission4.model.ResponseEvents
import com.stimednp.kadesubmission4.ui.detailleagues.fragmentnext.INextMatchView
import com.stimednp.kadesubmission4.ui.detailleagues.fragmentnext.NextMatchPresenter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by rivaldy on 12/10/2019.
 */

class NextRepositoryTest {
    @Mock
    lateinit var view: INextMatchView
    @Mock
    lateinit var nextRepository: NextRepository
    @Mock
    lateinit var dataEventLeagues: ArrayList<DataEventLeagues>
    @Mock
    lateinit var itemsH: ArrayList<DataTeamsBadge>
    @Mock
    lateinit var itemsA: ArrayList<DataTeamsBadge>

    private lateinit var nextMatchPresenter: NextMatchPresenter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        nextMatchPresenter = NextMatchPresenter(view, nextRepository)
    }

    @After
    fun tearDown() {
        print("Finish testing")
    }

    @Test
    fun getNextMatchSucces() {
        val idLeague = "4328"
        nextMatchPresenter.getNextMatch(idLeague)
        argumentCaptor<INextRepositoryCallback<ResponseEvents>>().apply {
            verify(nextRepository).getNextMatch(eq(idLeague), capture())
            firstValue.onDataLoaded(dataEventLeagues, itemsH, itemsA)
        }
        Mockito.verify(view).onShowLoading()
        Mockito.verify(view).onHideLoading()
        Mockito.verify(view).hideTextEmpty()
        Mockito.verify(view).onDataLoaded(dataEventLeagues, itemsH, itemsA)
    }

    @Test
    fun getNextMatchError() {
        val idLeague = ""
        nextMatchPresenter.getNextMatch(idLeague)
        argumentCaptor<INextRepositoryCallback<ResponseEvents>>().apply {
            verify(nextRepository).getNextMatch(eq(idLeague), capture())
            firstValue.onDataError()
        }
        Mockito.verify(view).onShowLoading()
        Mockito.verify(view).onDataError()
        Mockito.verify(view).onHideLoading()
    }
}