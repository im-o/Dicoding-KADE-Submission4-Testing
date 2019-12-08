package com.stimednp.kadesubmission4.ui.main.anko

import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import com.stimednp.kadesubmission4.R
import com.stimednp.kadesubmission4.R.color.*
import com.stimednp.kadesubmission4.model.Leagues
import com.stimednp.kadesubmission4.ui.adapter.HomeAdapter
import com.stimednp.kadesubmission4.ui.detailleagues.DetailsLeaguesActivity
import com.stimednp.kadesubmission4.ui.main.MainActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * Created by rivaldy on 11/10/2019.
 */

class MainUI(val items: ArrayList<Leagues>) : AnkoComponent<MainActivity> {
    companion object {
        lateinit var rv_main: RecyclerView
        lateinit var tv_nodata: TextView
        lateinit var swipeRefresh: SwipeRefreshLayout
        lateinit var tbar_main: Toolbar
    }

    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        coordinatorLayout {
            lparams(matchParent, matchParent)
            fitsSystemWindows = true

            themedAppBarLayout(R.style.AppTheme_AppBarOverlay) {
                tbar_main = toolbar {
                    title = resources.getString(R.string.app_title)
                    backgroundColor = getColor(context, colorPrimary)
                }.lparams(matchParent, wrapContent) {
                    scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                }
            }.lparams(matchParent, wrapContent)

            swipeRefresh = swipeRefreshLayout {
                id = R.id.swipe_main
                setColorSchemeResources(colorAccent, colorTwitter, colorYoutube, colorFacebook)
                relativeLayout {
                    lparams(matchParent, matchParent)
                    rv_main = recyclerView {
                        id = R.id.rv_main
                        lparams(matchParent, matchParent)
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter = HomeAdapter(items) {
                            startActivity<DetailsLeaguesActivity>(DetailsLeaguesActivity.EXTRA_DATA to it)
                        }
                    }
                    tv_nodata = textView {
                        text = resources.getString(R.string.str_nodata)
                        textColor = getColor(context, colorAccent)
                        textSize = 32f
                        typeface = Typeface.DEFAULT_BOLD
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }.lparams(matchParent, wrapContent) {
                        topMargin = dip(80)
                        alignParentTop()
                    }
                }
            }.lparams(matchParent, matchParent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }
    }
}