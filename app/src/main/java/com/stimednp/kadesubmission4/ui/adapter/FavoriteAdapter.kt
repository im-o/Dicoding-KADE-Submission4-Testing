package com.stimednp.kadesubmission4.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.stimednp.kadesubmission4.R
import com.stimednp.kadesubmission4.model.DataFavorites
import com.stimednp.kadesubmission4.ui.detailevents.DetailsEventActivity
import com.stimednp.kadesubmission4.util.CustomesUI
import com.stimednp.kadesubmission4.util.invisible
import kotlinx.android.synthetic.main.item_event_match.view.*
import org.jetbrains.anko.startActivity

/**
 * Created by rivaldy on 11/28/2019.
 */

class FavoriteAdapter(
    private val context: Context,
    private val items: ArrayList<DataFavorites>
) : RecyclerView.Adapter<FavoriteAdapter.MatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_event_match,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(items[position])
        holder.view.setOnClickListener() {
            context.startActivity<DetailsEventActivity>(
                DetailsEventActivity.EXTRA_DATA_EVENT to items[position].idEvent,
                DetailsEventActivity.EXTRA_BADGEH to items[position].strBadgeH,
                DetailsEventActivity.EXTRA_BADGEA to items[position].strBadgeA
            )
        }
    }

    class MatchViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(itemsE: DataFavorites?) {
            val urlimgH = "${itemsE?.strBadgeH}/preview"
            val urlimgA = "${itemsE?.strBadgeA}/preview"
            val dateChange = CustomesUI.changeDateFormat(itemsE?.dateEvent!!, itemsE.strTime!!)

            view.tv_league_sport.text = itemsE.strSport
            view.tv_strevent.text = itemsE.strEvent
            view.tv_date_time.text = dateChange
            view.tv_home_score.text = itemsE.intHomeScore?.toString() ?: "-"
            view.tv_away_score.text = itemsE.intAwayScore?.toString() ?: "-"
            view.tv_hometeam.text = itemsE.strHomeTeam
            view.tv_awayteam.text = itemsE.strAwayTeam
            itemsE.intHomeScore ?: itemsE.intAwayScore ?: view.tv_ft.invisible()

            Picasso.get().load(urlimgH).into(view.imgv_hometeam, object : Callback {
                override fun onSuccess() {
                    view.prog_tim_home.invisible()
                }

                override fun onError(e: Exception?) {
                    view.prog_tim_home.invisible()
                }
            })
            Picasso.get().load(urlimgA).into(view.imgv_awayteam, object : Callback {
                override fun onSuccess() {
                    view.prog_tim_away.invisible()
                }

                override fun onError(e: Exception?) {
                    view.prog_tim_away.invisible()
                }
            })
        }
    }
}
