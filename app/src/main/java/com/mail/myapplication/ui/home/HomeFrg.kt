package com.mail.myapplication.ui.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.RelativeLayout
import com.mail.myapplication.BaseXFrg
import com.mail.myapplication.R
import com.mail.myapplication.interfaces.Home
import com.zhy.autolayout.utils.AutoUtils
import kotlinx.android.synthetic.main.frg_home.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

class HomeFrg : BaseXFrg() {

    var list=ArrayList<String>()

    var list_fragments= ArrayList<BaseXFrg>()

    var arry =arrayOf("推荐", "H动漫", "国产", "91专区", "狼式上传", "欧美美女", "日本无码")

    var home = Home();

    override fun getLayoutId(): Int = R.layout.frg_home

    override fun initView() {

        for (index in arry.indices){
            list.add(arry[index])
            list_fragments.add(HomeListFrg.getInstance(arry[index]))
        }
    }

    override fun requestData() {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewpager.adapter = TabHomeFrgAdapter(list_fragments, childFragmentManager)
        initMagicIndicator()
        val params = indicator.layoutParams as RelativeLayout.LayoutParams
        params.height =  AutoUtils.getPercentHeightSize(130)
        indicator.layoutParams = params

        viewpager.setOffscreenPageLimit(list_fragments.size - 1)

    }

    private fun initMagicIndicator() {
        indicator.setBackgroundColor(Color.parseColor("#000000"))
        val commonNavigator = CommonNavigator(activity)
//        commonNavigator.scrollPivotX = 0.25f
        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getCount(): Int =list.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.setText(list[index])
                simplePagerTitleView.normalColor = Color.parseColor("#c8e6c9")
                simplePagerTitleView.selectedColor = Color.WHITE
                simplePagerTitleView.setOnClickListener { viewpager.setCurrentItem(index) }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.yOffset = UIUtil.dip2px(context, 3.0).toFloat()
                indicator.setColors(Color.parseColor("#ffffff"))
                return indicator
            }
        }
        indicator.navigator = commonNavigator
        ViewPagerHelper.bind(indicator, viewpager)
    }

}