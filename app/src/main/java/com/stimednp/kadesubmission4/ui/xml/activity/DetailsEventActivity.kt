package com.stimednp.kadesubmission4.ui.xml.activity

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log.e
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.stimednp.kadesubmission4.R
import com.stimednp.kadesubmission4.api.ApiClient
import com.stimednp.kadesubmission4.db.MydbOpenHelper.databaseLast
import com.stimednp.kadesubmission4.db.MydbOpenHelper.databaseNext
import com.stimednp.kadesubmission4.model.EventsLeagues
import com.stimednp.kadesubmission4.model.Favorites
import com.stimednp.kadesubmission4.util.CustomesUI
import com.stimednp.kadesubmission4.util.invisible
import com.stimednp.kadesubmission4.util.visible
import kotlinx.android.synthetic.main.activity_details_event.*
import kotlinx.android.synthetic.main.item_header_statis.*
import kotlinx.android.synthetic.main.items_body_statis.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.toast

class DetailsEventActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA_EVENT: String = "extra_data_event"
        const val EXTRA_BADGEH: String = "extra_badge_h"
        const val EXTRA_BADGEA: String = "extra_badge_A"
    }

    private val nameSavePref = "my_savepref_fav"
    private var keyIdSavePref = "my_key_default"
    private var menuItem: Menu? = null

    private lateinit var eventsL: EventsLeagues
    private var badgeTeamH: String? = null
    private var badgeTeamA: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_event)
        val eventId: String? = intent.getStringExtra(EXTRA_DATA_EVENT)
        badgeTeamH = intent.getStringExtra(EXTRA_BADGEH)
        badgeTeamA = intent.getStringExtra(EXTRA_BADGEA)
        keyIdSavePref = eventId.toString()

        setToolbar()
        getDetail(eventId)
    }

    private fun setToolbar() {
        setSupportActionBar(tbar_statis)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tbar_statis.setTitle(R.string.app_detail_match)
        tbar_statis.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        tbar_statis.setNavigationOnClickListener { finish() }
    }

    private fun getDetail(idEvent: String?) {
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main) {
            val listDetail = tsdbService.getDetailEvent(idEvent)
            try {
                val response = listDetail.await()
                if (response.isSuccessful) {
                    val resbody = response.body()
                    setData(resbody?.events!![0])
                }
            } catch (er: Exception) {
                e("INIII", "ERRROR : ${er.message}")
                runOnUiThread { }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_addfav, menu)
        menuItem = menu
        changeIconFavorite(keyIdSavePref)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_tofav -> {
                checkMyPref(eventsL.idEvent.toString())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addtoFavoriteLast(listItem: EventsLeagues, badgeH: String, badgeA: String) {
        try {
            databaseLast.use {
                insert(
                    Favorites.TABLE_FAVORITE,
                    Favorites.ID_EVENT to listItem.idEvent,
                    Favorites.STR_DATEEV to listItem.dateEvent,
                    Favorites.STR_TIMEEV to listItem.strTime,
                    Favorites.STR_EVENT to listItem.strEvent,
                    Favorites.STR_SPORT to listItem.strSport,
                    Favorites.STR_LEAGUE to listItem.strLeague,
                    Favorites.STR_TEAMH to listItem.strHomeTeam,
                    Favorites.STR_TEAMA to listItem.strAwayTeam,
                    Favorites.INT_SCOREH to listItem.intHomeScore,
                    Favorites.INT_SCOREA to listItem.intAwayScore,
                    Favorites.STR_BADGEH to badgeH,
                    Favorites.STR_BADGEA to badgeA
                )
            }
            toast("Sukses tambah ke favorite")
        } catch (er: SQLiteConstraintException) {
            toast("Gagal tambah ke favorite -> ${er.message}")
            e("INIII", "ERRROR ${er.message}")
        }
    }

    private fun addtoFavoriteNext(listItem: EventsLeagues, badgeH: String, badgeA: String) {
        try {
            databaseNext.use {
                insert(
                    Favorites.TABLE_FAVORITE,
                    Favorites.ID_EVENT to listItem.idEvent,
                    Favorites.STR_DATEEV to listItem.dateEvent,
                    Favorites.STR_TIMEEV to listItem.strTime,
                    Favorites.STR_EVENT to listItem.strEvent,
                    Favorites.STR_SPORT to listItem.strSport,
                    Favorites.STR_LEAGUE to listItem.strLeague,
                    Favorites.STR_TEAMH to listItem.strHomeTeam,
                    Favorites.STR_TEAMA to listItem.strAwayTeam,
                    Favorites.INT_SCOREH to listItem.intHomeScore,
                    Favorites.INT_SCOREA to listItem.intAwayScore,
                    Favorites.STR_BADGEH to badgeH,
                    Favorites.STR_BADGEA to badgeA
                )
            }
            toast("Sukses tambah ke favorite")
        } catch (er: SQLiteConstraintException) {
            toast("Gagal tambah ke favorite -> ${er.message}")
            e("INIII", "ERRROR ${er.message}")
        }
    }

    private fun removeFavoriteLast(listItem: EventsLeagues) {
        val id: String = listItem.idEvent.toString()
        try {
            databaseLast.use {
                delete(
                    Favorites.TABLE_FAVORITE,
                    "(${Favorites.ID_EVENT} = {id})",
                    "id" to id
                )
            }
            toast("Sukses hapus dari favorite")
        } catch (er: SQLiteConstraintException) {
            toast("Gagal tambah ke favorite -> ${er.message}")
            e("INIII", "ERRROR ${er.message}")
        }
    }

    private fun removeFavoriteNext(listItem: EventsLeagues) {
        val id: String = listItem.idEvent.toString()
        try {
            databaseNext.use {
                delete(
                    Favorites.TABLE_FAVORITE,
                    "(${Favorites.ID_EVENT} = {id})",
                    "id" to id
                )
            }
            toast("Sukses hapus dari favorite")
        } catch (er: SQLiteConstraintException) {
            toast("Gagal hapus data -> ${er.message}")
            e("INIII", "ERRROR ${er.message}")
        }
    }


    private fun checkMyPref(idEvent: String) {
        val isFavorite = checkPrefById(idEvent) // add/true data if false
        if (isFavorite) { //delete data
            setPrefById(idEvent, false)
            changeIconFavorite(idEvent)
            if (eventsL.intHomeScore != null && eventsL.intAwayScore != null) {
                removeFavoriteLast(eventsL)
            } else {
                removeFavoriteNext(eventsL)
            }
        } else { //insert data
            setPrefById(idEvent, true)
            changeIconFavorite(idEvent)
            if (eventsL.intHomeScore != null && eventsL.intAwayScore != null) {
                addtoFavoriteLast(eventsL, badgeTeamH.toString(), badgeTeamA.toString())
            } else {
                addtoFavoriteNext(eventsL, badgeTeamH.toString(), badgeTeamA.toString())
            }
        }
    }

    private fun changeIconFavorite(idEvent: String) {
        val isFavorite = checkPrefById(idEvent)
        if (isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
        }
    }

    private fun checkPrefById(keyPref: String): Boolean {
        val mSharedPref = getSharedPreferences(nameSavePref, Context.MODE_PRIVATE)
        return mSharedPref.getBoolean(keyPref, false)
    }

    private fun setPrefById(keyIdSavePref: String, isFavorite: Boolean) {
        val mSharedPref = getSharedPreferences(nameSavePref, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = mSharedPref.edit()
        editor.putBoolean(keyIdSavePref, isFavorite)
        editor.apply()
    }

    private fun setData(ev: EventsLeagues) {
        eventsL = ev

        val urlimgH = "$badgeTeamH/preview"
        val urlimgA = "$badgeTeamA/preview"
        val dateChange = CustomesUI.changeDateFormat(ev.dateEvent.toString(), ev.strTime.toString())

        //header
        ev.intHomeScore ?: ev.intAwayScore ?: tv_fts.invisible()
        tv_hscore.text = ev.intHomeScore?.toString() ?: "-"
        tv_ascore.text = ev.intAwayScore?.toString() ?: "-"
        tv_hname.text = ev.strHomeTeam
        tv_aname.text = ev.strAwayTeam

        //load image
        Picasso.get().load(urlimgH).into(imgv_hteam, object : Callback {
            override fun onSuccess() {
                prog_hteam.invisible()
            }

            override fun onError(e: Exception?) {
                prog_hteam.visible()
            }
        })
        Picasso.get().load(urlimgA).into(imgv_ateam, object : Callback {
            override fun onSuccess() {
                prog_ateam.invisible()
            }

            override fun onError(e: Exception?) {
                prog_ateam.visible()
            }
        })
        Picasso.get().load(urlimgH).into(img_badgeHb, object : Callback {
            override fun onSuccess() {
                prog_hsteam.invisible()
            }

            override fun onError(e: Exception?) {
                prog_hteam.visible()
            }
        })
        Picasso.get().load(urlimgA).into(img_badgeAb, object : Callback {
            override fun onSuccess() {
                prog_asteam.invisible()
            }

            override fun onError(e: Exception?) {
                prog_ateam.visible()
            }
        })

        //body
        tv_vs.text = ev.strEvent ?: "-"
        tv_date_time.text = dateChange
        tv_tnameh.text = ev.strHomeTeam ?: "-"
        tv_tnamea.text = ev.strAwayTeam ?: "-"
        tv_formationh.text = ev.strHomeFormation ?: "-"
        tv_formationa.text = ev.strAwayFormation ?: "-"
        tv_goalh.text = ev.intHomeScore?.toString() ?: "-"
        tv_goala.text = ev.intAwayScore?.toString() ?: "-"
        tv_shoth.text = ev.intHomeShots?.toString() ?: "-"
        tv_shota.text = ev.intAwayShots?.toString() ?: "-"
        tv_credh.text = ev.strHomeRedCards?.replace(";", "\n") ?: "-"
        tv_creda.text = ev.strAwayRedCards?.replace(";", "\n") ?: "-"
        tv_cyellowh.text = ev.strHomeYellowCards?.replace(";", "\n") ?: "-"
        tv_cyellowa.text = ev.strAwayYellowCards?.replace(";", "\n") ?: "-"
        tv_gkeeperh.text = ev.strHomeLineupGoalkeeper?.replace("; ", "\n") ?: "-"
        tv_gkeepera.text = ev.strAwayLineupGoalkeeper?.replace("; ", "\n") ?: "-"
        tv_defenseh.text = ev.strHomeLineupDefense?.replace("; ", "\n") ?: "-"
        tv_defensea.text = ev.strAwayLineupDefense?.replace("; ", "\n") ?: "-"
        tv_midh.text = ev.strHomeLineupMidfield?.replace("; ", "\n") ?: "-"
        tv_mida.text = ev.strAwayLineupMidfield?.replace("; ", "\n") ?: "-"
        tv_forwardh.text = ev.strHomeLineupForward?.replace("; ", "\n") ?: "-"
        tv_forwarda.text = ev.strAwayLineupForward?.replace("; ", "\n") ?: "-"
        tv_subtih.text = ev.strHomeLineupSubstitutes?.replace("; ", "\n") ?: "-"
        tv_subtia.text = ev.strAwayLineupSubstitutes?.replace("; ", "\n") ?: "-"
        tv_dgoalh.text = ev.strHomeGoalDetails?.replace(";", "\n") ?: "-"
        tv_dgoala.text = ev.strAwayGoalDetails?.replace(";", "\n") ?: "-"
    }
}
