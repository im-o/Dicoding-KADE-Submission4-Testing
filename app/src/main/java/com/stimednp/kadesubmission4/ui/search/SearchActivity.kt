package com.stimednp.kadesubmission4.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.stimednp.kadesubmission4.R
import com.stimednp.kadesubmission4.R.color.*
import com.stimednp.kadesubmission4.model.DataEventLeagues
import com.stimednp.kadesubmission4.model.DataTeamsBadge
import com.stimednp.kadesubmission4.presenter.search.SearchRepository
import com.stimednp.kadesubmission4.ui.adapter.EventMatchAdapter
import com.stimednp.kadesubmission4.utils.EspressoIdlingResource
import com.stimednp.kadesubmission4.utils.invisible
import com.stimednp.kadesubmission4.utils.visible
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.support.v4.onRefresh

@SuppressLint("Registered")
class SearchActivity : AppCompatActivity(), ISearchView, SearchView.OnQueryTextListener {
    lateinit var searchPresenter: SearchPresenter
    var itemEvents = ArrayList<DataEventLeagues>()
    var itemTeamsH = ArrayList<DataTeamsBadge>()
    var itemTeamsA = ArrayList<DataTeamsBadge>()
    lateinit var textSearch: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setToolbar()
        initial()

        swipe_search.setColorSchemeResources(colorAccent, colorTwitter, colorYoutube, colorFacebook)
        swipe_search.onRefresh {
            getSearchData(textSearch)
            clearData()
            rv_search.adapter?.notifyDataSetChanged()
        }
    }

    private fun getSearchData(text: String) {
        searchPresenter = SearchPresenter(this, SearchRepository())
        searchPresenter.getSearchEvent(text)
    }

    private fun initial() {
        showTextEmpty("Search your list match..")
        rv_search.layoutManager = LinearLayoutManager(this)
        rv_search.adapter = EventMatchAdapter(this, itemEvents, itemTeamsH, itemTeamsA)
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

    override fun onQueryTextSubmit(query: String): Boolean {
        clearData()
        rv_search.adapter?.notifyDataSetChanged()
        getSearchData(query)
        textSearch = query
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun showMsgSucces(text: String) {
        //need this for future
    }

    override fun showMsgFail(text: String) {
        //need this for future
    }

    override fun showTextEmpty(text: String) {
        tv_empty_datas.visible()
        tv_empty_datas.text = text
    }

    override fun hideTextEmpty() {
        tv_empty_datas.invisible()
    }

    override fun onDataLoaded(data: ArrayList<DataEventLeagues>, teamH: ArrayList<DataTeamsBadge>, teamA: ArrayList<DataTeamsBadge>) {
        if (!EspressoIdlingResource.idlingResource.isIdleNow){
            //task is complete -> DELETE this after test (Memory leak)
            EspressoIdlingResource.decrement()
        }
        clearData()
        hideTextEmpty()
        itemEvents.addAll(data)
        itemTeamsH.addAll(teamH)
        itemTeamsA.addAll(teamA)
        rv_search.adapter?.notifyDataSetChanged()
    }

    override fun onDataError() {
        if (!EspressoIdlingResource.idlingResource.isIdleNow){
            //task is complete -> DELETE this after test (Memory leak)
            EspressoIdlingResource.decrement()
        }
        showTextEmpty("No Data...")
    }

    override fun onShowLoading() {
        swipe_search.isRefreshing = true
        showTextEmpty("Searching...\nPlease wait...")
    }

    override fun onHideLoading() {
        swipe_search.isRefreshing = false
    }

    private fun clearData() {
        itemEvents.clear()
        itemTeamsH.clear()
        itemTeamsA.clear()
    }
}
