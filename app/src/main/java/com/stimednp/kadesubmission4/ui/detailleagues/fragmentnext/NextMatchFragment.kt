package com.stimednp.kadesubmission4.ui.detailleagues.fragmentnext


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
import com.stimednp.kadesubmission4.presenter.detailleagues.fragmentnext.NextRepository
import com.stimednp.kadesubmission4.ui.adapter.EventMatchAdapter
import com.stimednp.kadesubmission4.ui.detailleagues.DetailsLeaguesActivity
import com.stimednp.kadesubmission4.ui.detailleagues.fragementlast.LastMatchPreseter
import com.stimednp.kadesubmission4.util.gone
import com.stimednp.kadesubmission4.util.invisible
import com.stimednp.kadesubmission4.util.visible
//import kotlinx.android.synthetic.main.fragment_last_match.*
import kotlinx.android.synthetic.main.fragment_next_match.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
class NextMatchFragment : Fragment(), INextMatchView {
    private lateinit var nextMatchPresenter: NextMatchPresenter
    var idLeague: String? = null
    var itemEvents = ArrayList<DataEventLeagues>()
    var itemTeamsH = ArrayList<DataTeamsBadge>()
    var itemTeamsA = ArrayList<DataTeamsBadge>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idLeague = DetailsLeaguesActivity.idLeagues
        getNextMatch(idLeague)
        val layoutManager = LinearLayoutManager(context)
        rv_nextmatch.layoutManager = layoutManager
        rv_nextmatch.adapter = EventMatchAdapter(context, itemEvents, itemTeamsH, itemTeamsA)
    }

    private fun getNextMatch(idLeague: String?) {
        nextMatchPresenter = NextMatchPresenter(this, NextRepository())
        nextMatchPresenter.getNextMatch(idLeague.toString())
    }

    override fun showMsgSucces(text: String) {
        toast(text)
    }

    override fun showMsgFail(text: String) {
        toast(text)
    }

    override fun showTextEmpty(text: String) {
        tv_empty_nextmatch.text = text
        tv_empty_nextmatch.visible()
    }

    override fun hideTextEmpty() {
        tv_empty_nextmatch.invisible()
    }

    override fun onDataLoaded(data: ArrayList<DataEventLeagues>, itemsH: ArrayList<DataTeamsBadge>, itemsA: ArrayList<DataTeamsBadge>) {
        itemEvents.clear()
        itemTeamsH.clear()
        itemTeamsA.clear()
        itemEvents.addAll(data)
        itemTeamsH.addAll(itemsH)
        itemTeamsA.addAll(itemsA)
        rv_nextmatch.adapter?.notifyDataSetChanged()
    }

    override fun onDataError() {
        showTextEmpty("Something Error! or No Data!")
    }

    override fun onShowLoading() {
        showTextEmpty("Load data\nPlease wait..")
        progress_nextmatch.visible()
    }

    override fun onHideLoading() {
        progress_nextmatch.invisible()
    }
}
