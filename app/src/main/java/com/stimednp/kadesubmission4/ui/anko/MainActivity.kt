package com.stimednp.kadesubmission4.ui.anko

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.stimednp.kadesubmission4.R
import com.stimednp.kadesubmission4.api.ApiClient
import com.stimednp.kadesubmission4.model.Leagues
import com.stimednp.kadesubmission4.ui.anko.MainUI.Companion.rv_main
import com.stimednp.kadesubmission4.ui.anko.MainUI.Companion.swipeRefresh
import com.stimednp.kadesubmission4.ui.anko.MainUI.Companion.tbar_main
import com.stimednp.kadesubmission4.ui.anko.MainUI.Companion.tv_nodata
import com.stimednp.kadesubmission4.ui.xml.activity.FavoritesActivity
import com.stimednp.kadesubmission4.util.CustomesUI.showProgress
import com.stimednp.kadesubmission4.util.CustomesUI.showProgressDialog
import com.stimednp.kadesubmission4.util.invisible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onRefresh

class MainActivity : AppCompatActivity() {
    var leagueList = ArrayList<Leagues>()
    var leagueAddto = ArrayList<Leagues>()
    var sizeListId: Int = 0
    val items = ArrayList<Leagues>()
    var sizeLen = 0
    var isBackPress = false
    lateinit var tvProgress: TextView
    lateinit var strProgress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainUI(leagueList).setContentView(this)
        setToolbar()
        init()
        getIdListLeague()
        swipeRefresh.onRefresh {
            reloadData()
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

    private fun init() {
        showProgressDialog(this)
        tvProgress = showProgress.find(R.id.tv_progress_cust)
        strProgress = getString(R.string.str_progress)
    }

    private fun showDataProgress() {
        tvProgress.text = strProgress
        showProgress.show()
    }

    private fun getIdListLeague() {
        showDataProgress()
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.IO) {
            val listIdLeagues = tsdbService.getListLeagues()
            try {
                val respose = listIdLeagues.await()
                val responseBody = respose.body()
                setById(responseBody?.leagues!!)
            } catch (er: Exception) {
                runOnUiThread {
                    disableProgress()
                    toast(getString(R.string.no_ineter))
                }
            }
        }
    }

    private fun getDataById(id: Int) {
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main) {
            val listIdLeagues = tsdbService.getDetailById(id)
            try {
                val respose = listIdLeagues.await()
                val responseBody = respose.body()
                savetoArrays(responseBody?.leagues!!)
                filterList(responseBody.leagues)
            } catch (er: Exception) {
                runOnUiThread {
                    disableProgress()
                    toast(getString(R.string.no_ineter))
                }
            }
        }
    }

    private fun filterList(leagues: ArrayList<Leagues>) { //loop stop after size == leagues.size
        if (leagues.size > 0) {
            tv_nodata.invisible()
            items.addAll(leagues)
            if (sizeListId > 20) {
                sizeLen = sizeLen + 1
                if (items.size == 20) {
                    setAdapter(items)
                    items.clear()
                } else if (sizeListId.equals(sizeLen) && items.size != 0) {
                    setAdapter(items)
                    items.clear()
                }
            } else {
                setAdapter(items)
                items.clear()
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun setById(leagues: ArrayList<Leagues>) {
        val listIdLeagues: MutableList<Int> = ArrayList()
        for (i in leagues.indices) {
            val sportSoccer = leagues[i].strSport?.toLowerCase()
            val id = leagues[i].idLeague!!.toInt()
            if (sportSoccer == "soccer") {
                listIdLeagues.add(id)
            }
        }
        sizeListId = listIdLeagues.size
        for (i in listIdLeagues.indices) {
            val id = listIdLeagues[i]
            getDataById(id)
        }
    }

    private fun reloadData() {
        if (leagueAddto.size >= sizeListId && sizeListId != 0) {
            leagueList.clear()
            setAdapter(leagueAddto)
            toast(getString(R.string.nomore_data))
            disableProgress()
        } else {
            getIdListLeague()
        }
    }

    fun savetoArrays(leagues: ArrayList<Leagues>) {
        val strProg = ("$strProgress ${leagueAddto.size} of $sizeListId")
        leagueAddto.addAll(leagues)
        tvProgress.text = strProg

        if (leagueAddto.size == sizeListId) {
            disableProgress()
            longToast(getString(R.string.str_loadsucces))
        }
    }

    override fun onBackPressed() {
        val strClose = resources.getString(R.string.tap_to_close)
        if (isBackPress) {
            super.onBackPressed()
        }
        isBackPress = true
        toast(strClose)
        Handler().postDelayed({
            isBackPress = false
            disableProgress()
        }, 2000)
    }

    private fun disableProgress() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
        if (showProgress.isShowing) {
            showProgress.dismiss()
        }
    }

    private fun setAdapter(leagues: ArrayList<Leagues>) {
        leagueList.addAll(leagues)
        rv_main.adapter?.notifyDataSetChanged()
    }

}
