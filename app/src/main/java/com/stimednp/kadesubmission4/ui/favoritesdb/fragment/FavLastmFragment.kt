package com.stimednp.kadesubmission4.ui.favoritesdb.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.stimednp.kadesubmission4.R
import com.stimednp.kadesubmission4.db.MydbOpenHelper.databaseLast
import com.stimednp.kadesubmission4.model.DataFavorites
import com.stimednp.kadesubmission4.ui.adapter.FavoriteAdapter
import com.stimednp.kadesubmission4.utils.visible
import kotlinx.android.synthetic.main.fragment_fav_lastm.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh

/**
 * A simple [Fragment] subclass.
 */
class FavLastmFragment : Fragment() {
    var itemFavorites: ArrayList<DataFavorites> = arrayListOf()
    private var adapter: FavoriteAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fav_lastm, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initial()
        swipe_lastm.isRefreshing = true
        swipe_lastm.setColorSchemeResources(R.color.colorAccent, R.color.colorTwitter, R.color.colorYoutube, R.color.colorFacebook)
        swipe_lastm.onRefresh { showFavorite() }
    }

    private fun initial() {
        rv_fav_lastm.layoutManager = LinearLayoutManager(context)
        adapter = FavoriteAdapter(context!!, itemFavorites)
        rv_fav_lastm.adapter = adapter
    }

    private fun showFavorite() {
        itemFavorites.clear()
        context?.databaseLast?.use {
            val result = select(DataFavorites.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<DataFavorites>())
            itemFavorites.addAll(favorite)
            adapter?.notifyDataSetChanged()
        }
        if (itemFavorites.size <= 0) enableNoData()
        disableLoad()
    }

    private fun disableLoad() {
        if (swipe_lastm.isRefreshing) {
            swipe_lastm.isRefreshing = false
        }
    }

    private fun enableNoData() {
        tv_fav_emptyl.visible()
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

}
