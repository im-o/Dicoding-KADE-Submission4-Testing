package com.stimednp.kadesubmission4.presenter.main

import android.annotation.SuppressLint
import com.stimednp.kadesubmission4.api.ApiClient
import com.stimednp.kadesubmission4.model.Leagues
import com.stimednp.kadesubmission4.model.ResponseLeagues
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by rivaldy on 12/8/2019.
 */

class MainRepository() {
    private val tsdbService = ApiClient.iServiceTsdb
    private val items: ArrayList<Leagues> = ArrayList()
    private var sizeItems: Int? = null

    fun getIdListLeague(callback: IMainRepositoryCallback<ResponseLeagues>) {
        GlobalScope.launch(Dispatchers.IO) {
            val listIdLeagues = tsdbService.getListLeagues()
            try {
                val response = listIdLeagues.await()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val leagues = responseBody?.leagues
                    filterById(leagues, callback)
                }
            } catch (er: Exception) {
                callback.onDataError()
            }
        }
    }

    @SuppressLint("DefaultLocale")
    fun filterById(leagues: ArrayList<Leagues>?, callbackMain: IMainRepositoryCallback<ResponseLeagues>) {
        val listIdLeagues: ArrayList<String> = ArrayList()
        for (i in leagues?.indices!!) {
            val sportSoccer = leagues[i].strSport?.toLowerCase()
            val id = leagues[i].idLeague!!
            if (sportSoccer == "soccer") {
                listIdLeagues.add(id)
            }
        }
        sizeItems = listIdLeagues.size
        for (i in listIdLeagues.indices) {
            getDataById(listIdLeagues[i], callbackMain)
        }
    }

    fun getDataById(id: String?, callbackMain: IMainRepositoryCallback<ResponseLeagues>) {
        GlobalScope.launch(Dispatchers.Main) {
            val listIdLeagues = tsdbService.getDetailById(id)
            try {
                val respose = listIdLeagues.await()
                val responseBody = respose.body()
                val leagues = responseBody?.leagues
                items.addAll(leagues!!)
                if (items.size == sizeItems) filterList(callbackMain)
            } catch (er: Exception) {
                callbackMain.onDataError()
            }
        }
    }

    fun filterList(callbackMain: IMainRepositoryCallback<ResponseLeagues>) {
        if (items.size > 0){
            callbackMain.onDataLoaded(items)
        } else callbackMain.onDataError()
    }
}