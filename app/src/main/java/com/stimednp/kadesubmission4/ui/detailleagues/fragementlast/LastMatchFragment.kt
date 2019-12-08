package com.stimednp.kadesubmission4.ui.detailleagues.fragementlast


import android.os.Bundle
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.stimednp.kadesubmission4.R
import com.stimednp.kadesubmission4.api.ApiClient
import com.stimednp.kadesubmission4.model.DataEventLeagues
import com.stimednp.kadesubmission4.model.DataTeamsBadge
import com.stimednp.kadesubmission4.presenter.detailleagues.fragmentlast.LastRepository
import com.stimednp.kadesubmission4.ui.adapter.EventMatchAdapter
import com.stimednp.kadesubmission4.ui.detailleagues.DetailsLeaguesActivity
import com.stimednp.kadesubmission4.util.gone
import com.stimednp.kadesubmission4.util.invisible
import com.stimednp.kadesubmission4.util.visible
import kotlinx.android.synthetic.main.fragment_last_match.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

/**
 * A simple [Fragment] subclass.
 */
class LastMatchFragment : Fragment(), ILastMatchView {
    private lateinit var lastMatchPreseter: LastMatchPreseter
    private var idLeague: String? = null
    private var itemEvents = ArrayList<DataEventLeagues>()
    private var itemTeamsH = ArrayList<DataTeamsBadge>()
    private var itemTeamsA = ArrayList<DataTeamsBadge>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idLeague = DetailsLeaguesActivity.idLeagues
        getLastMatch(idLeague.toString())
        rv_lastmatch.layoutManager = LinearLayoutManager(context)
        rv_lastmatch.adapter = EventMatchAdapter(context, itemEvents, itemTeamsH, itemTeamsA)
    }

    private fun getLastMatch(idLeague: String) {
        lastMatchPreseter = LastMatchPreseter(this, LastRepository())
        lastMatchPreseter.getLastMatch(idLeague)
    }

    override fun showMsgSucces(text: String) {
        toast(text)
    }

    override fun showMsgFail(text: String) {
        toast(text)
    }

    override fun showTextEmpty(text: String) {
        tv_loadempty.text = text
        tv_loadempty.visible()
    }

    override fun hideTextEmpty() {
        tv_loadempty.invisible()
    }

    override fun onDataLoaded(data: ArrayList<DataEventLeagues>, itemsH: ArrayList<DataTeamsBadge>, itemsA: ArrayList<DataTeamsBadge>) {
        itemEvents.clear()
        itemTeamsH.clear()
        itemTeamsA.clear()
        itemEvents.addAll(data)
        itemTeamsH.addAll(itemsH)
        itemTeamsA.addAll(itemsA)
        rv_lastmatch.adapter?.notifyDataSetChanged()
    }

    override fun onDataError() {
//        showMsgFail("Something Error")
        showTextEmpty("Something Error! or No Data!")
    }

    override fun onShowLoading() {
        showTextEmpty("Load data\nPlease wait..")
        progress_lastmatch.visible()
    }

    override fun onHideLoading() {
        progress_lastmatch.invisible()
    }
}