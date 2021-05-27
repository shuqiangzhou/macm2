package com.mail.myapplication.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mail.myapplication.BaseXAty
import com.mail.myapplication.R
import com.mail.myapplication.ui.home.HomeFrg
import com.mail.myapplication.ui.home02.Home02Frg
import kotlinx.android.synthetic.main.aty_main.*


class MainAty : BaseXAty() {

    var position: Int = 0

    var list_tv: Array<TextView>? = null

    var list_imgv: Array<ImageView>? = null

    override fun getLayoutId(): Int = R.layout.aty_main

    override fun getFragmentContainerId(): Int = R.id.fralay_content

    override fun initView() {
        setBackTwo(true)
        list_tv = arrayOf(tv_01, tv_02, tv_03, tv_04)
        list_imgv = arrayOf(imgv_01, imgv_02, imgv_03, imgv_04)
    }

    override fun requestData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(HomeFrg::class.java,null)
    }

    fun mainClick(v: View) {

        when (v.id) {

            R.id.linlay_01 -> {
                if (position == 0) return
                position = 0
                list_tv?.let { t1 -> list_imgv?.let { t2 -> setSelector(tv_01, t1, t2) } }
                addFragment(HomeFrg::class.java,null)
            }

            R.id.linlay_02 -> {
                if (position == 1) return
                position = 1
                list_tv?.let { t1 -> list_imgv?.let { t2 -> setSelector(tv_02, t1, t2) } }
                addFragment(Home02Frg::class.java,null)
            }

            R.id.linlay_03 -> {
                if (position == 2) return
                position = 2
                list_tv?.let { t1 -> list_imgv?.let { t2 -> setSelector(tv_03, t1, t2) } }
            }

            R.id.linlay_04 -> {
                if (position == 3) return
                position = 3
                list_tv?.let { t1 -> list_imgv?.let { t2 -> setSelector(tv_04, t1, t2) } }
            }
        }
    }

    private fun setSelector(tv: TextView, list_tv: Array<TextView>, list_imgv: Array<ImageView>) {
        for (i in list_tv.indices) {
            if (tv === list_tv[i]) {
                list_tv[i].setTextColor(resources.getColor(R.color.main_color))
                when (i) {
                    0 -> list_imgv[i].setImageResource(R.drawable.ic_main_tab1_checked)
                    1 -> list_imgv[i].setImageResource(R.drawable.ic_main_tab2_checked)
                    2 -> list_imgv[i].setImageResource(R.drawable.ic_main_tab3_checked)
                    3 -> list_imgv[i].setImageResource(R.drawable.ic_main_tab4_checked)
                }
            } else {
                list_tv[i].setTextColor(resources.getColor(R.color.mainnocheck))
                when (i) {
                    0 -> list_imgv[i].setImageResource(R.drawable.ic_main_tab1_normal)
                    1 -> list_imgv[i].setImageResource(R.drawable.ic_main_tab2_normal)
                    2 -> list_imgv[i].setImageResource(R.drawable.ic_main_tab3_normal)
                    3 -> list_imgv[i].setImageResource(R.drawable.ic_main_tab4_normal)
                }
            }
        }
    }


}