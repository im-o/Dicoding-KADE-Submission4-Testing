package com.stimednp.kadesubmission4.ui.xml.fragment


import android.os.Bundle
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.stimednp.kadesubmission4.R
import com.stimednp.kadesubmission4.api.ApiClient
import com.stimednp.kadesubmission4.model.EventsLeagues
import com.stimednp.kadesubmission4.model.TeamsBadge
import com.stimednp.kadesubmission4.ui.adapter.EventMatchAdapter
import com.stimednp.kadesubmission4.ui.detailleagues.DetailsLeaguesActivity
import com.stimednp.kadesubmission4.util.gone
import com.stimednp.kadesubmission4.util.visible
import kotlinx.android.synthetic.main.fragment_next_match.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.runOnUiThread

/**
 * A simple [Fragment] subclass.
 */
class NextMatchFragment : Fragment() {
    var idLeague: String? = null
    var itemEvents = ArrayList<EventsLeagues>()
    var itemTeamsH = ArrayList<TeamsBadge>()
    var itemTeamsA = ArrayList<TeamsBadge>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataItems = DetailsLeaguesActivity.items
        idLeague = dataItems?.idLeague!!
        setIdEvent(idLeague!!)
        val layoutManager = LinearLayoutManager(context)
        rv_nextmatch.layoutManager = layoutManager
        rv_nextmatch.adapter = EventMatchAdapter(context!!, itemEvents, itemTeamsH, itemTeamsA)
    }

    private fun setIdEvent(idLeague: String) {
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main) {
            val listIdEvents = tsdbService.getNextMatch(idLeague)
            try {
                val responseE = listIdEvents.await()
                val resBodyE = responseE.body()
                savetoArrays(resBodyE?.events!!)
            } catch (er: Exception) {
                e("INIII", "ERRROR NEXT 1 $er")
                if (er.message == KotlinNullPointerException().message) {
                    runOnUiThread {
                        tv_empty_nextmatch.visible()
                        disabelProgress()
                    }
                }
            }
        }
    }

    private fun setIdTeam(events: ArrayList<EventsLeagues>, teamH: ArrayList<Int>, teamA: ArrayList<Int>) {
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main) {
            val itemsH = ArrayList<TeamsBadge>()
            val itemsA = ArrayList<TeamsBadge>()
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
                } catch (er: Exception) {
                    e("INIII", "ERRROR NEXT 2 $er")
                    runOnUiThread { disabelProgress() }
                }
            }
            setAdapter(events, itemsH, itemsA)
        }
    }

    private fun savetoArrays(events: ArrayList<EventsLeagues>) {
        val badgeH = ArrayList<Int>()
        val badgeA = ArrayList<Int>()

        for (i in events.indices) {
            val teamH = events[i].idHomeTeam
            val teamA = events[i].idAwayTeam

            badgeH.add(teamH!!)
            badgeA.add(teamA!!)
        }
        setIdTeam(events, badgeH, badgeA)
    }

    private fun disabelProgress() {
        if (progress_nextmatch != null) progress_nextmatch.gone()
    }

    private fun setAdapter(itemsE: ArrayList<EventsLeagues>, itemsH: ArrayList<TeamsBadge>, itemsA: ArrayList<TeamsBadge>) {
        itemEvents.clear()
        itemTeamsH.clear()
        itemEvents.addAll(itemsE)
        itemTeamsH.addAll(itemsH)
        itemTeamsA.addAll(itemsA)
        if (rv_nextmatch != null) rv_nextmatch.adapter?.notifyDataSetChanged() else if (idLeague != null) setIdEvent(idLeague!!)
        disabelProgress()
    }
}
