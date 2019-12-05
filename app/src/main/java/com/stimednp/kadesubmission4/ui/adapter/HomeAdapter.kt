package com.stimednp.kadesubmission4.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.stimednp.kadesubmission4.R
import com.stimednp.kadesubmission4.model.Leagues
import com.stimednp.kadesubmission4.ui.anko.ItemLeaguesUI
import com.stimednp.kadesubmission4.util.invisible
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

/**
 * Created by rivaldy on 11/10/2019.
 */

class HomeAdapter(val items: ArrayList<Leagues>, val listener: (Leagues) -> Unit) : RecyclerView.Adapter<HomeAdapter.HomeAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapterViewHolder {
        return HomeAdapterViewHolder(ItemLeaguesUI().createView(AnkoContext.Companion.create(parent.context, parent)))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HomeAdapterViewHolder, position: Int) {
        holder.bindItem(items[position], position, listener)
    }

    class HomeAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ligaName: TextView = view.find(R.id.liga_name)
        private val ligaDesc: TextView = view.find(R.id.liga_desc)
        private val ligaImg: ImageView = view.find(R.id.liga_img)
        private val progressBar: ProgressBar = view.find(R.id.liga_progress)
        private val card: CardView = view.find(R.id.liga_cardv)

        fun bindItem(leagues: Leagues, position: Int, listener: (Leagues) -> Unit) {
            val strUrl = "${leagues.strBadge}/preview"
            val strName = "${position + 1}. ${leagues.strLeague}"
            ligaName.text = strName
            ligaDesc.text = leagues.strDescriptionEN
            Picasso.get().load(strUrl).fit().into(ligaImg, object : Callback {
                override fun onSuccess() {
                    progressBar.invisible()
                }

                override fun onError(e: Exception?) {
                    progressBar.invisible()
                }

            })
            card.setOnClickListener {
                listener(leagues)
            }
        }
    }
}