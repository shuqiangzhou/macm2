package com.mail.myapplication.ui.home

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mail.myapplication.BaseXFrg


class TabHomeFrgAdapter(val fragments: List<BaseXFrg>,  fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): BaseXFrg {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

}
