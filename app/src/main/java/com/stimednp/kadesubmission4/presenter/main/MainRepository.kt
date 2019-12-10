package com.stimednp.kadesubmission4.presenter.main

import android.annotation.SuppressLint
import android.util.Log.e
import com.stimednp.kadesubmission4.api.ApiClient
import com.stimednp.kadesubmission4.model.DataLeagues
import com.stimednp.kadesubmission4.model.ResponseLeagues
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by rivaldy on 12/8/2019.
 */

class MainRepository() {
    private val tsdbService = ApiClient.iServiceTsdb
    private val items: ArrayList<DataLeagues> = ArrayList()
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
                    e("INIII","HASIL 1 (leagues) : $leagues")
                }
            } catch (er: Exception) {
                callback.onDataError()
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun filterById(leagues: ArrayList<DataLeagues>?, callbackMain: IMainRepositoryCallback<ResponseLeagues>) {
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

    private fun getDataById(id: String?, callbackMain: IMainRepositoryCallback<ResponseLeagues>) {
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

    private fun filterList(callbackMain: IMainRepositoryCallback<ResponseLeagues>) {
        if (items.size > 0){
            callbackMain.onDataLoaded(items)
            e("INIII","HASIL 1 (items) : $items")
        } else callbackMain.onDataError()
    }
}