package com.stimednp.kadesubmission4.presenter.search

import android.annotation.SuppressLint
import android.util.Log
import com.stimednp.kadesubmission4.R
import com.stimednp.kadesubmission4.api.ApiClient
import com.stimednp.kadesubmission4.model.DataEventLeagues
import com.stimednp.kadesubmission4.model.DataTeamsBadge
import com.stimednp.kadesubmission4.model.ResponseEvents
import com.stimednp.kadesubmission4.model.ResponseSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import java.util.ArrayList

/**
 * Created by rivaldy on 12/8/2019.
 */

class SearchRepository {
    private val tsdbService = ApiClient.iServiceTsdb
    fun getSearchData(text: String, callback: ISearchRepositoryCallback<ResponseSearch>){
        GlobalScope.launch(Dispatchers.Main) {
            val listEvents = tsdbService.getSearchEvent(text)
            try {
                val responseE = listEvents.await()
                val resBodyE = responseE.body()
                val event = resBodyE?.event
                savetoArrays(event, callback)
            } catch (er: Exception) {
                Log.e("INIII", "ERRROR SEARCH 1 $er")
                callback.onDataError()
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun savetoArrays(events: ArrayList<DataEventLeagues>?, callback: ISearchRepositoryCallback<ResponseSearch>) {
        val eventItems = ArrayList<DataEventLeagues>()
        val badgeH = ArrayList<Int>()
        val badgeA = ArrayList<Int>()

        for (i in events?.indices!!) {
            val idH = events[i].idHomeTeam
            val idA = events[i].idAwayTeam
            val ev = events[i]
            val sportSoccer = events[i].strSport?.toLowerCase()

            if (sportSoccer == "soccer") {
                badgeH.add(idH as Int)
                badgeA.add(idA as Int)
                eventItems.addAll(listOf(ev))
            }
        }
        if (eventItems.size > 0) {
            setIdTeam(eventItems, badgeH, badgeA, callback)
        } else {
            callback.onDataError()
        }
    }

    private fun setIdTeam(events: ArrayList<DataEventLeagues>, teamH: ArrayList<Int>, teamA: ArrayList<Int>, callback: ISearchRepositoryCallback<ResponseSearch>) {
        GlobalScope.launch(Dispatchers.Main) {
            val itemsH = ArrayList<DataTeamsBadge>()
            val itemsA = ArrayList<DataTeamsBadge>()
            if (events.size > 0) {
                for (i in events.indices) {
                    try {
                        val listIdHome = tsdbService.getDetailTeamH(teamH[i])
                        val listIdAway = tsdbService.getDetailTeamA(teamA[i])
                        val responseH = listIdHome.await()
                        val bodyH = responseH.body()
                        val responseA = listIdAway.await()
                        val bodyA = responseA.body()
                        itemsH.addAll(bodyH?.teams!!)
                        itemsA.addAll(bodyA?.teams!!)
                    } catch (er: Exception) {
                        Log.e("INIII", "ERRROR SEARCH 2 $er")
                    }
                }
                callback.onDataLoaded(events, itemsH, itemsA)
            } else {
                callback.onDataError()
            }
        }
    }
}