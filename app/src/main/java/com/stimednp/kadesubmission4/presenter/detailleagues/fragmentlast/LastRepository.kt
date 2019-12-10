package com.stimednp.kadesubmission4.presenter.detailleagues.fragmentlast

import com.stimednp.kadesubmission4.api.ApiClient
import com.stimednp.kadesubmission4.model.DataEventLeagues
import com.stimednp.kadesubmission4.model.DataTeamsBadge
import com.stimednp.kadesubmission4.model.ResponseEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by rivaldy on 12/8/2019.
 */

class LastRepository {
    private val tsdbService = ApiClient.iServiceTsdb

    fun getLastMatch(idLeague: String, callback: ILastRepositoryCallback<ResponseEvents>) {
        GlobalScope.launch(Dispatchers.Main) {
            val listIdEvent = tsdbService.getPrevMatch(idLeague)
            try {
                val result = listIdEvent.await()
                val resultBody = result.body()
                val list = resultBody?.events
                savetoArrays(list!!, callback)
            } catch (err: Exception) {
                callback.onDataError()
            }
        }
    }

    private fun savetoArrays(events: ArrayList<DataEventLeagues>, callback: ILastRepositoryCallback<ResponseEvents>) {
        val badgeH = ArrayList<Int>()
        val badgeA = ArrayList<Int>()
        for (i in events.indices) {
            val teamH = events[i].idHomeTeam
            val teamA = events[i].idAwayTeam
            badgeH.add(teamH!!)
            badgeA.add(teamA!!)
        }
        setIdTeam(events, badgeH, badgeA, callback)
    }

    fun setIdTeam(events: ArrayList<DataEventLeagues>, teamH: ArrayList<Int>, teamA: ArrayList<Int>, callback: ILastRepositoryCallback<ResponseEvents>) {
        GlobalScope.launch(Dispatchers.Main) {
            val itemsH = ArrayList<DataTeamsBadge>()
            val itemsA = ArrayList<DataTeamsBadge>()
            for (i in events.indices) {
                val listIdHome = tsdbService.getDetailTeamH(teamH[i])
                val listIdAway = tsdbService.getDetailTeamA(teamA[i])
                try {
                    val responseH = listIdHome.await()
                    val bodyH = responseH.body()
                    val responseA = listIdAway.await()
                    val bodyA = responseA.body()
                    itemsH.addAll(bodyH?.teams!!)
                    itemsA.addAll(bodyA?.teams!!)
                } catch (e: Exception) {
                    callback.onDataError()
                }
            }
            setAdapter(events, itemsH, itemsA, callback)
        }
    }

    private fun setAdapter(itemsE: ArrayList<DataEventLeagues>, itemsH: ArrayList<DataTeamsBadge>, itemsA: ArrayList<DataTeamsBadge>, callback: ILastRepositoryCallback<ResponseEvents>) {
        callback.onDataLoaded(itemsE, itemsH, itemsA)
    }
}