package com.stimednp.kadesubmission4.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.stimednp.kadesubmission4.R
import com.stimednp.kadesubmission4.model.Leagues
import com.stimednp.kadesubmission4.presenter.main.MainRepository
import com.stimednp.kadesubmission4.ui.main.anko.MainUI
import com.stimednp.kadesubmission4.ui.main.anko.MainUI.Companion.rv_main
import com.stimednp.kadesubmission4.ui.main.anko.MainUI.Companion.swipeRefresh
import com.stimednp.kadesubmission4.ui.main.anko.MainUI.Companion.tbar_main
import com.stimednp.kadesubmission4.ui.main.anko.MainUI.Companion.tv_nodata
import com.stimednp.kadesubmission4.ui.xml.activity.FavoritesActivity
import com.stimednp.kadesubmission4.util.invisible
import com.stimednp.kadesubmission4.util.visible
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), IMainView {
    var leagueList = ArrayList<Leagues>()
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

    private fun setAdapter(leagues: ArrayList<Leagues>) {
        leagueList.addAll(leagues)
        rv_main.adapter?.notifyDataSetChanged()
    }

    override fun showMessage(text: String) {
        toast(text)
    }

    override fun showTextEmpty(text: String) {
        tv_nodata.text = text
        tv_nodata.visible()
    }

    override fun onDataLoaded(data: ArrayList<Leagues>) {
        leagueList.clear()
        setAdapter(data)
        onHideLoading()
    }

    override fun onDataError() {
        showTextEmpty("Failed Load data")
        showMessage("Something Error")
    }

    override fun onShowLoading() {
        swipeRefresh.isRefreshing = true
        showTextEmpty("Load data\nPlease wait..")
    }

    override fun onHideLoading() {
        swipeRefresh.isRefreshing = false
        tv_nodata.invisible()
    }

}
