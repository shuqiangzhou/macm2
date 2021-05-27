package com.mail.myapplication.ui

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.mail.myapplication.BaseXAty
import com.mail.myapplication.R
import com.mail.myapplication.interfaces.Home

class TestAty :BaseXAty(){

    var relay_top_bg:RelativeLayout?=null
    var tv_title:TextView?=null

    override fun getLayoutId(): Int = R.layout.aty_test

    override fun initView() {

        relay_top_bg =findViewById(R.id.relay_top_bg)
        tv_title =findViewById(R.id.tv_title)


    }


    override fun requestData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTopview(relay_top_bg)
        tv_title?.text= "test"
    }


    fun mainClick(v:View){

        when(v.id){
            R.id.relay_back->{
                finish()
            }
        }

    }
}