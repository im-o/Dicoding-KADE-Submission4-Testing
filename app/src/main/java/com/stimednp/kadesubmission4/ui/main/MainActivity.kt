package com.stimednp.kadesubmission4.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.stimednp.kadesubmission4.R
import com.stimednp.kadesubmission4.model.DataLeagues
import com.stimednp.kadesubmission4.presenter.main.MainRepository
import com.stimednp.kadesubmission4.ui.favoritesdb.FavoritesActivity
import com.stimednp.kadesubmission4.ui.main.anko.MainUI
import com.stimednp.kadesubmission4.ui.main.anko.MainUI.Companion.rv_main
import com.stimednp.kadesubmission4.ui.main.anko.MainUI.Companion.swipeRefresh
import com.stimednp.kadesubmission4.ui.main.anko.MainUI.Companion.tbar_main
import com.stimednp.kadesubmission4.ui.main.anko.MainUI.Companion.tv_nodata
import com.stimednp.kadesubmission4.utils.invisible
import com.stimednp.kadesubmission4.utils.visible
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), IMainView {
    var leagueList = ArrayList<DataLeagues>()
    private lateinit var mainPresenter: IMainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainUI(leagueList).setContentView(this)
        setToolbar()
        getLeaguesData()
        swipeRefresh.onRefresh {
            getLeaguesData()
        }
    }

    private fun setToolbar() {
        setSupportActionBar(tbar_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.my_favorites -> {
                startActivity<FavoritesActivity>()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getLeaguesData() {
        mainPresenter = MainPresenter(this, MainRepository())
        mainPresenter.getLeaguesData()
    }

    private fun setAdapter(leagues: ArrayList<DataLeagues>) {
        leagueList.clear()
        leagueList.addAll(leagues)
        rv_main.adapter?.notifyDataSetChanged()
    }

    override fun showMsgSucces(text: String) {
        toast(text)
    }

    override fun showMsgFail(text: String) {
        toast(text)
    }

    override fun showTextEmpty(text: String) {
        tv_nodata.text = text
        tv_nodata.visible()
    }

    override fun hideTextEmpty() {
        tv_nodata.invisible()
    }

    override fun onDataLoaded(data: ArrayList<DataLeagues>) {
        leagueList.clear()
        hideTextEmpty()
        setAdapter(data)
    }

    override fun onDataError() {
//        showMsgFail("Something Error!")
        showTextEmpty("No data show!!")
    }

    override fun onShowLoading() {
        swipeRefresh.isRefreshing = true
        showTextEmpty("Load data\nPlease wait..")
    }

    override fun onHideLoading() {
        swipeRefresh.isRefreshing = false
    }

}
