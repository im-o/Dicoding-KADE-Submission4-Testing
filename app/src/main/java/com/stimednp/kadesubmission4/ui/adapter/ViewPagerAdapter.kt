package com.stimednp.kadesubmission4.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by rivaldy on 11/13/2019.
 */

class ViewPagerAdapter(val context: Context, val listStr: List<Int>, val listFrag: List<Fragment>, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return listFrag[position]
    }

    override fun getCount(): Int {
        return listFrag.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(listStr[position])
    }

}