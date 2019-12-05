package com.stimednp.kadesubmission4.ui.xml.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log.e
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.stimednp.kadesubmission4.R
import com.stimednp.kadesubmission4.R.color.*
import com.stimednp.kadesubmission4.api.ApiClient
import com.stimednp.kadesubmission4.model.EventsLeagues
import com.stimednp.kadesubmission4.model.TeamsBadge
import com.stimednp.kadesubmission4.ui.adapter.EventMatchAdapter
import com.stimednp.kadesubmission4.util.CustomesUI.showProgress
import com.stimednp.kadesubmission4.util.CustomesUI.showProgressDialog
import com.stimednp.kadesubmission4.util.invisible
import com.stimednp.kadesubmission4.util.visible
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    var itemEvents = ArrayList<EventsLeagues>()
    var itemTeamsH = ArrayList<TeamsBadge>()
    var itemTeamsA = ArrayList<TeamsBadge>()
    var itemSave = ArrayList<EventsLeagues>()
    var textSearch: String? = null
    lateinit var tvProgress: TextView
    lateinit var strProgress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setToolbar()
        initial()

        swipe_search.setColorSchemeResources(colorAccent, colorTwitter, colorYoutube, colorFacebook)
        swipe_search.onRefresh {
            if (!textSearch.equals(null)) {
                if (itemSave.equals(itemEvents)) {
                    toast(getString(R.string.nomore_data))
                    disabelProgress()
                } else {
                    searchEvent(textSearch ?: "")
                }
            } else {
                toast(getString(R.string.str_search))
                disabelProgress()
            }
        }
    }

    private fun initial() {
        showProgressDialog(this)
        tvProgress = showProgress.find(R.id.tv_progress_cust)
        strProgress = getString(R.string.str_loadmatch)

        rv_search.layoutManager = LinearLayoutManager(this)
        rv_search.adapter = EventMatchAdapter(this, itemEvents, itemTeamsH, itemTeamsA)
    }

    private fun showDataProgress() {
        tvProgress.text = strProgress
        showProgress.show()
    }

    private fun setToolbar() {
        setSupportActionBar(tb_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tb_search.setTitle(R.string.app_title)
        tb_search.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        tb_search.setNavigationOnClickListener { finish() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_search_menu, menu)
        val searchItem = menu?.findItem(R.id.item_search_action)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.app_search_event)
        searchView.isIconified = false
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            clearData()
            rv_search.adapter?.notifyDataSetChanged()
            searchEvent(query)
            textSearch = query
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    private fun searchEvent(strEvent: String) {
        enableProgress()
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main) {
            val listEvents = tsdbService.getSearchEvent(strEvent)
            try {
                val responseE = listEvents.await()
                val resBodyE = responseE.body()
                savetoArrays(resBodyE?.event)
            } catch (er: Exception) {
                e("INIII", "ERRROR SEARCH 1 $er")
                runOnUiThread {
                    disabelProgress()
                    enableNodata()
                    clearData()
                }
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun savetoArrays(events: ArrayList<EventsLeagues>?) {
        val eventItems = ArrayList<EventsLeagues>()
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
            setIdTeam(eventItems, badgeH, badgeA)
        } else {
            toast(getString(R.string.str_nomatch))
            disabelProgress()
            enableNodata()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setIdTeam(events: ArrayList<EventsLeagues>, teamH: ArrayList<Int>, teamA: ArrayList<Int>) {
        showDataProgress()
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main) {
            val itemsH = ArrayList<TeamsBadge>()
            val itemsA = ArrayList<TeamsBadge>()
            if (events.size > 0) {
                for (i in events.indices) {
                    val strPrg = "$strProgress $i of ${events.size}"
                    tvProgress.text = strPrg
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
                        e("INIII", "ERRROR SEARCH 2 $er")
                        runOnUiThread { disabelProgress() }
                    }
                }
                setAdapter(events, itemsH, itemsA)
            } else {
                enableNodata()
                disabelProgress()
                clearData()
            }
        }
    }


    private fun setAdapter(itemsE: ArrayList<EventsLeagues>, itemsH: ArrayList<TeamsBadge>, itemsA: ArrayList<TeamsBadge>) {
        clearData()
        itemEvents.addAll(itemsE)
        itemTeamsH.addAll(itemsH)
        itemTeamsA.addAll(itemsA)
        itemSave.addAll(itemsE)
        if (rv_search.adapter != null) {
            rv_search.adapter?.notifyDataSetChanged()
        }
        tv_empty_datas.invisible()
        disabelProgress()
    }

    private fun disabelProgress() {
        if (swipe_search.isRefreshing) {
            swipe_search.isRefreshing = false
        }
        if (showProgress.isShowing) {
            showProgress.dismiss()
        }
    }

    private fun enableProgress() {
        if (!swipe_search.isRefreshing) {
            swipe_search.isRefreshing = true
        }
    }

    private fun enableNodata() {
        tv_empty_datas.visible()
    }

    private fun clearData() {
        itemEvents.clear()
        itemTeamsH.clear()
        itemTeamsA.clear()
        itemSave.clear()
    }
}
