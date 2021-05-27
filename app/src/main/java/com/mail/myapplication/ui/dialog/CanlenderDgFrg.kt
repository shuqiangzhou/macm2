package com.mail.myapplication.ui.dialog

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mail.comm.base.BaseDgFrg
import com.mail.myapplication.R
import com.mail.myapplication.ui.utils.MyUtils
import com.mail.myapplication.ui.utils.TimeUtilsX
import com.zhy.autolayout.utils.AutoUtils

class CanlenderDgFrg : BaseDgFrg() {

    var tv_year: TextView? = null
    var tv_year2: TextView? = null
    var tv_year3: TextView? = null
    var tv_cancel: TextView? = null
    var tv_ok: TextView? = null
    var imgv_pre: ImageView? = null
    var imgv_next: ImageView? = null
    var recyclerview: RecyclerView? = null

    var list = ArrayList<Int>()
    var currentMonth = MyUtils.getCurrentMonth()
    var currentYear = MyUtils.getCurrentYear()
    var todayDay = MyUtils.getCurrentDay()
    var checkDay = todayDay
    var adapter: GoldRecyclerAdapter? = null
    var listen: CanlenderCheckDayListen? = null

    override fun getLayoutId(): Int = R.layout.dialog_canlender

    override fun getDialogStyle(): Int = R.style.dialogFullscreen

    override fun canCancel(): Boolean = true

    interface CanlenderCheckDayListen {
        fun getCheck(year: Int, monty: Int, day: Int)
    }

    override fun setWindowAttributes(window: Window?) {
        window?.setWindowAnimations(R.style.bottomToTopAnim)
        val m = mContext!!.windowManager
        val d = m.defaultDisplay// 获取屏幕宽、高
        var params = window?.attributes
        params?.width = (d.width * 0.85).toInt()
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        params?.gravity = Gravity.CENTER
        window?.attributes = params
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_year=findViewById<TextView>(R.id.tv_year)
        tv_year2=findViewById<TextView>(R.id.tv_year2)
        tv_year3=findViewById<TextView>(R.id.tv_year3)
        recyclerview=findViewById<RecyclerView>(R.id.recyclerview)
        imgv_pre=findViewById<ImageView>(R.id.imgv_pre)
        imgv_next=findViewById<ImageView>(R.id.imgv_next)
        tv_cancel=findViewById<TextView>(R.id.tv_cancel)
        tv_ok=findViewById<TextView>(R.id.tv_ok)

        tv_year?.text = MyUtils.getCurrentYear().toString()
        tv_year2?.text = TimeUtilsX.getStrTime2()
        adapter = GoldRecyclerAdapter()
        recyclerview?.layoutManager = GridLayoutManager(context, 7)
        recyclerview?.adapter = adapter

        initdata()

        imgv_pre?.setOnClickListener {
            if (currentMonth == 1) {
                currentMonth = 12
                currentYear--
            } else {
                currentMonth--
            }
            checkDay = -1
            initdata()
        }

        imgv_next?.setOnClickListener {
            if (currentMonth == 12) {
                currentMonth = 1
                currentYear++
            } else {
                currentMonth++
            }
            checkDay = -1
            initdata()
        }

        tv_cancel?.setOnClickListener { dismiss() }
        tv_ok?.setOnClickListener {

            if (checkDay == -1) {
                mContext?.showToastS("请选择日期")
                return@setOnClickListener
            }
            listen?.getCheck(currentYear, currentMonth, checkDay)
            dismiss()
        }
    }

    fun setCanlendeListen(listen: CanlenderCheckDayListen) {
        this.listen = listen
    }

    inner class GoldRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val inflater: LayoutInflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            fGoldViewHolder(inflater.inflate(R.layout.item_calander, parent, false))

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            var holder1: fGoldViewHolder = holder as fGoldViewHolder

            with(holder1) {

                tv_count?.text = list[position].toString()

                if (list[position] == checkDay) {
                    tv_count?.setBackgroundResource(R.drawable.q13)
                    tv_count?.setTextColor(context!!.resources.getColor(R.color.white))
                } else {
                    tv_count?.setBackgroundColor(context!!.resources.getColor(R.color.white))
                    tv_count?.setTextColor(context!!.resources.getColor(R.color.ccc_02))
                }
                if (tv_count?.text.toString() == "0") {
                    tv_count?.visibility = View.INVISIBLE
                } else {
                    tv_count?.visibility = View.VISIBLE
                }
                itemView.setOnClickListener {

                    if (tv_count?.text.toString() == "0") return@setOnClickListener

                    checkDay = list[position]
                    notifyDataSetChanged()

                }
            }
        }

        inner class fGoldViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tv_count: TextView? = null

            init {
                AutoUtils.autoSize(itemView)
                tv_count = itemView.findViewById(R.id.tv_day)
            }
        }
    }

    fun initdata() {
        list.clear()
        tv_year3?.text = currentYear.toString() + "年" + currentMonth.toString() + "月"
        val monthOfDays = MyUtils.getMonthOfDays(currentYear, currentMonth)
        val monthOfDays2 = MyUtils.getFirstDayOfMonth(currentYear, currentMonth)
        for (i in 0 until monthOfDays2 - 1) list.add(0)
        for (i in 0 until monthOfDays) list.add(i + 1)
        adapter?.notifyDataSetChanged()

    }
}