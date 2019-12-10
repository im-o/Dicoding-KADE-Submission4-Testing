package com.stimednp.kadesubmission4.presenter.detailleagues.fragmentlast

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import com.stimednp.kadesubmission4.model.DataEventLeagues
import com.stimednp.kadesubmission4.model.DataTeamsBadge
import com.stimednp.kadesubmission4.model.ResponseEvents
import com.stimednp.kadesubmission4.ui.detailleagues.fragementlast.ILastMatchView
import com.stimednp.kadesubmission4.ui.detailleagues.fragementlast.LastMatchPreseter
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by rivaldy on 12/10/2019.
 */

class LastRepositoryTest {
    @Mock
    lateinit var view: ILastMatchView
    @Mock
    lateinit var lastRepository: LastRepository
    @Mock
    lateinit var dataEventLeagues: ArrayList<DataEventLeagues>
    @Mock
    lateinit var itemsH: ArrayList<DataTeamsBadge>
    @Mock
    lateinit var itemsA: ArrayList<DataTeamsBadge>

    val data: ArrayList<DataEventLeagues> = arrayListOf()
    val idHomeTeam: ArrayList<Int> = arrayListOf()
    val idAwayTeam: ArrayList<Int> = arrayListOf()

    private lateinit var lastMatchPreseter: LastMatchPreseter

    @Before
    fun setUp() {
//        data.add(DataEventLeagues(idEvent="609433", idLeague="4332", idHomeTeam=133687, idAwayTeam=133674, strEvent="Torino vs Fiorentina", strSport="Soccer", strLeague="Italian Serie A",
//            strHomeTeam="Torino", strAwayTeam="Fiorentina", intHomeScore=2, intAwayScore=1, strHomeGoalDetails="22':Simone Zaza;72':Cristian Daniel Ansaldi;", strHomeRedCards="", strHomeYellowCards="32':Ola Aina;41':Daniele Baselli;62':Tomas Rincon;90':Armando Izzo;", strHomeLineupGoalkeeper="Salvatore Sirigu;" ,
//            strHomeLineupDefense="Armando Izzo; Nicolas N'Koulou; Gleison Bremer;" , strHomeLineupMidfield="Lorenzo De Silvestri; Daniele Baselli; Tomas Rincon; Cristian Daniel Ansaldi; Simone Verdi; Alex Berenguer;" , strHomeLineupForward="Simone Zaza;" , strHomeLineupSubstitutes="Samir Ujkani; Antonio Rosati; Kevin Bonifazi; Vincenzo Millico; Soualiho Meite; Koffi Djidji; Ola Aina; Nicola Rauti; Diego Laxalt;" ,
//            strHomeFormation=null, strAwayRedCards="", strAwayYellowCards="31':Federico Chiesa;49':Martin Caceres;69':Dusan Vlahovic;", strAwayGoalDetails="90':Martin Caceres;, strAwayLineupGoalkeeper=Bartlomiej Dragowski;" , strAwayLineupDefense="Nikola Milenkovic; Federico Ceccherini; Martin Caceres;" , strAwayLineupMidfield="Pol Lirola; Marco Benassi; Erick Pulgar; Gaetano Castrovilli; Dalbert;", strAwayLineupForward="Federico Chiesa; Dusan Vlahovic;",
//            strAwayLineupSubstitutes="Pietro Terracciano; Michele Cerofolini; Milan Badelj; Luca Ranieri; Pedro; Riccardo Sottil; Sebastian Cristoforo; Valentin Eysseric; Pol Lirola; Lorenzo Venuti; Szymon Zurkowski; Aleksa Terzic;",
//            strAwayFormation=null, intHomeShots=null, intAwayShots=null, dateEvent="2019-12-08", strTime="14:00:00", strAwayLineupGoalkeeper = "testing"))
//        idHomeTeam.add(133687)
//        idAwayTeam.add(133674)

        MockitoAnnotations.initMocks(this)
        lastMatchPreseter = LastMatchPreseter(view, lastRepository)
    }

    @After
    fun tearDown() {
        print("Finish testing")
    }

    @Test
    fun getLastMatchSuccess() {
        val idLeague = "4328"
        lastMatchPreseter.getLastMatch(idLeague)
        argumentCaptor<ILastRepositoryCallback<ResponseEvents>>().apply {
            verify(lastRepository).getLastMatch(eq(idLeague), capture())
            firstValue.onDataLoaded(dataEventLeagues, itemsH, itemsA)
        }
        Mockito.verify(view).onShowLoading()
        Mockito.verify(view).onHideLoading()
        Mockito.verify(view).hideTextEmpty()
        Mockito.verify(view).onDataLoaded(dataEventLeagues, itemsH, itemsA)
    }

    @Test
    fun getLastMatchError() {
        val idLeague = ""
        lastMatchPreseter.getLastMatch(idLeague)
        argumentCaptor<ILastRepositoryCallback<ResponseEvents>>().apply {
            verify(lastRepository).getLastMatch(eq(idLeague), capture())
            firstValue.onDataError()
        }
        Mockito.verify(view).onShowLoading()
        Mockito.verify(view).onDataError()
        Mockito.verify(view).onHideLoading()
    }

//    @Test
//    fun setIdTeam(){
////        print("DATA $data, $idHomeTeam, $idAwayTeam")
//        lastMatchPreseter.getLastMatch(idLeague)
//        argumentCaptor<ILastRepositoryCallback<ResponseEvents>>().apply {
//            verify(lastRepository).setIdTeam(eq(data), eq(idHomeTeam), eq(idAwayTeam), capture())
//            firstValue.onDataLoaded(dataEventLeagues, itemsH, itemsA)
//        }
//        Mockito.verify(view).onShowLoading()
//        Mockito.verify(view).onHideLoading()
//        Mockito.verify(view).hideTextEmpty()
//        Mockito.verify(view).onDataLoaded(dataEventLeagues, itemsH, itemsA)
//    }
}