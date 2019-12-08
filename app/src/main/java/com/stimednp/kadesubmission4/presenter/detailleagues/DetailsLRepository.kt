package com.stimednp.kadesubmission4.presenter.detailleagues

import com.stimednp.kadesubmission4.api.ApiClient
import com.stimednp.kadesubmission4.model.ResponseLeagues
import com.stimednp.kadesubmission4.presenter.main.IMainRepositoryCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by rivaldy on 12/8/2019.
 */

class DetailsLRepository {
    private val tsdbService = ApiClient.iServiceTsdb
    fun getDataById(id: String?, callback: IDetailsLRepositoryCallback<ResponseLeagues>) {
        GlobalScope.launch(Dispatchers.Main) {
            val listIdLeagues = tsdbService.getDetailById(id)
            try {
                val respose = listIdLeagues.await()
                val responseBody = respose.body()
                val leagues = responseBody?.leagues
                callback.onDataLoaded(leagues!!)
            } catch (er: Exception) {
                callback.onDataError()
            }
        }
    }
}