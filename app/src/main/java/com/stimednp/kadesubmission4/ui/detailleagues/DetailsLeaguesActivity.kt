package com.stimednp.kadesubmission4.ui.detailleagues

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.stimednp.kadesubmission4.R
import com.stimednp.kadesubmission4.model.Leagues
import com.stimednp.kadesubmission4.ui.adapter.ViewPagerAdapter
import com.stimednp.kadesubmission4.ui.xml.activity.SearchActivity
import com.stimednp.kadesubmission4.ui.xml.fragment.LastMatchFragment
import com.stimednp.kadesubmission4.ui.xml.fragment.NextMatchFragment
import kotlinx.android.synthetic.main.activity_details_leagues.*
import kotlinx.android.synthetic.main.item_header.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class DetailsLeaguesActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EXTRA_DATA: String = "extra_data"
        var items: Leagues? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_leagues)
        items = intent.getParcelableExtra(EXTRA_DATA)
        initClick()
        setToolbar()
        showData()
        setupViewPager()
    }

    private fun initClick() {
        tv_web.setOnClickListener(this)
        tv_fb.setOnClickListener(this)
        tv_twit.setOnClickListener(this)
        tv_yt.setOnClickListener(this)
    }

    private fun showData() {
        val url = "${items?.strBadge}/preview"
        tv_name_league.text = items?.strLeague
        tv_desc_league.text = items?.strDescriptionEN
        Picasso.get().load(url).into(img_badgeHb)
    }

    private fun setToolbar() {
        setSupportActionBar(htab_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        htab_toolbar.setTitle(R.string.app_detail_leagues)
        htab_toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        htab_toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupViewPager() {
        val pages = listOf(LastMatchFragment(), NextMatchFragment())
        val strTab = listOf(R.string.str_last_match, R.string.str_next_match)
        val strIc = listOf(R.drawable.ic_event_complete_black, R.drawable.ic_event_next_black)
        htab_viewpager.adapter = ViewPagerAdapter(this, strTab, pages, supportFragmentManager)
        htab_tablayout.setupWithViewPager(htab_viewpager)
        for (i in pages.indices) htab_tablayout.getTabAt(i)?.setIcon(strIc[i])
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_search, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_search) {
            startActivity<SearchActivity>()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(id: View?) {
        when (id) {
            tv_web -> goUri(items?.strWebsite)
            tv_fb -> goUri(items?.strFacebook)
            tv_twit -> goUri(items?.strTwitter)
            tv_yt -> goUri(items?.strYoutube)
        }
    }

    private fun goUri(url: String?) {
        if (url == "") {
            toast(getString(R.string.str_nourl))
        } else {
            try {
                val uri = Uri.parse("http://$url")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                Handler().postDelayed({ startActivity(intent) }, 100)
            } catch (e: Exception) {
                toast("Something Error uri : $url")
            }
        }
    }
}
