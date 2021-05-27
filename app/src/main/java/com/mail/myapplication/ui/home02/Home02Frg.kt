package com.mail.myapplication.ui.home02

import android.os.Bundle
import com.mail.comm.base.BaseAty
import com.mail.myapplication.BaseXFrg
import com.mail.myapplication.R
import com.mail.myapplication.ui.PopTopMsgHolder
import com.mail.myapplication.ui.TestAty
import com.mail.myapplication.ui.dialog.CanlenderDgFrg
import com.mail.myapplication.ui.dialog.PassDialog
import com.mail.myapplication.ui.video.GsyVideoAty
import com.mail.myapplication.ui.video.IjkplayVideoAty
import com.mail.myapplication.ui.video.jz.JzvdAty
import com.mail.myapplication.ui.video.jz.tiktok.TikTokAty
import kotlinx.android.synthetic.main.frg_home_02.*
import kotlinx.android.synthetic.main.frg_home_02.rootview
import tv.danmaku.ijk.media.example.activities.FileExplorerActivity

class Home02Frg : BaseXFrg(), CanlenderDgFrg.CanlenderCheckDayListen {

    override fun getLayoutId(): Int = R.layout.frg_home_02

    override fun initView() {
    }

    override fun requestData() {
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        btn_01.setOnClickListener {
//            (activity as MainAty).show01();
//        }

        btn_02.setOnClickListener {
            val canlenderDgFrg = CanlenderDgFrg()
            canlenderDgFrg.setCanlendeListen(this)
            canlenderDgFrg.show(childFragmentManager, "CanlenderDgFrg")
        }

        btn_03.setOnClickListener {
            q()
        }

        btn_04.setOnClickListener {
            startActivity(TestAty::class.java)
        }

        btn_05.setOnClickListener {
            showTopPop()
        }

        btn_06.setOnClickListener {
            startActivity(FileExplorerActivity::class.java)
        }
        btn_07.setOnClickListener {
            startActivity(IjkplayVideoAty::class.java)
        }

        btn_08.setOnClickListener {
            startActivity(GsyVideoAty::class.java)
        }
        btn_09.setOnClickListener {
            startActivity(JzvdAty::class.java)
        }

        btn_10.setOnClickListener {
            startActivity(TikTokAty::class.java)
        }
    }

    override fun getCheck(year: Int, monty: Int, day: Int) {
        showToastS(year.toString() + monty.toString() + day.toString())
    }

    var proteCode2Dialog: PassDialog? = null

    fun q() {
        proteCode2Dialog ?: let {
            proteCode2Dialog =
                PassDialog(activity as BaseAty)
            proteCode2Dialog?.setOnScuesssListen(object : PassDialog.onScuesssListen {
                override fun reutrnOK(pwd: String?) {
                    proteCode2Dialog?.clear()
                    proteCode2Dialog?.dismiss()
                    showToastS(pwd)
                }

            })
        }
        proteCode2Dialog?.show()
        proteCode2Dialog?.showSoftInput()
    }

    var bonusViewHolder: PopTopMsgHolder? = null
    fun showTopPop() {
        if (bonusViewHolder == null) {
            bonusViewHolder = PopTopMsgHolder(activity!!, rootview);
            bonusViewHolder?.subscribeActivityLifeCycle()
        }
        bonusViewHolder?.showTopMmgPop()
    }
}