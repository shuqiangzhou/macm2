package com.mail.myapplication.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mail.comm.utils.ImagesUtils
import com.mail.comm.view.loading.LoadingTipX
import com.mail.comm.view.refresh.XRefreshAndLoadListen
import com.mail.myapplication.BaseXFrg
import com.mail.myapplication.R
import com.mail.myapplication.interfaces.Home
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.zhy.autolayout.utils.AutoUtils
import kotlinx.android.synthetic.main.frg_home_list.*
import org.xutils.http.RequestParams


class HomeListFrg : BaseXFrg(), LoadingTipX.LoadingTipXReloadCallback, XRefreshAndLoadListen {

    var home = Home()

    var type: String? = null

    var list = ArrayList<String>()

    var list_img = ArrayList<HashMap<String, String>>()

    override fun getLayoutId(): Int = R.layout.frg_home_list

    companion object {
        fun getInstance(type: String?): HomeListFrg {
            var frg = HomeListFrg()
            var bundle = Bundle()
            bundle.putString("type", type)
            frg.arguments = bundle
            return frg
        }
    }

    override fun initView() {
        type = arguments?.getString("type")
        for (index in 1!!..20) {
            list.add(index.toString() + type)
        }
        var map = HashMap<String, String>()
        var map1 = HashMap<String, String>()
        var map2 = HashMap<String, String>()
        map.put(
            "url",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3663359702,1992818410&fm=26&gp=0.jpg"
        )
        map1.put(
            "url",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic_360_360%2F64%2F25%2F33%2F642533df78c1ce3ca55ddca1f57d8e80.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1623757816&t=6a3629b39737befbfe3bb6f303b7ddae"
        )
        map2.put(
            "url",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201408%2F07%2F213601f2xz7usscm2z1mjh.jpg&refer=http%3A%2F%2Fattach.bbs.miui.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1623757816&t=c80b89610c686d4f87db65fd5ba5dd81"
        )
        list_img.add(map)
        list_img.add(map1)
        list_img.add(map2)
    }

    override fun requestData() {
        loading.setLoadingTip(LoadingTipX.LoadStatus.loading)
        home.a(this)
    }

    fun requestDataNoTip() {
        home.a(this)
    }

    override fun reload() {
        requestData()
    }

    override fun refreshStart() {
        startProgressDialog()
        requestDataNoTip()
    }

    override fun loadMoreStart() {
        var listmm = ArrayList<String>()
        for (index in list.size!!..list.size + 20) {
            listmm.add(index.toString() + type)
        }
        list.addAll(listmm)
        requestDataNoTip()
    }

    override fun onComplete(var1: RequestParams?, var2: String?, type: String?) {
        super.onComplete(var1, var2, type)
        loading.setLoadingTip(LoadingTipX.LoadStatus.finish)
        stopProgressDialog()
        refreshLayout.finishRefreshing()
        refreshLayout.finishLoadmore()

//        if (list.size > 20) {
//            mAdapter?.notifyDataSetChanged()
//        }
        mAdapter?.notifyDataSetChanged()


    }

    override fun onExceptionType(var1: Throwable?, params: RequestParams?, type: String?) {
        super.onExceptionType(var1, params, type)
        loading.setLoadingTip(LoadingTipX.LoadStatus.error)
        stopProgressDialog()
        refreshLayout.finishRefreshing()
        refreshLayout.finishLoadmore()
    }

    var mAdapter: GoldRecyclerAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerview.layoutManager = GridLayoutManager(activity, 2)
        mAdapter = GoldRecyclerAdapter(activity)
        mAdapter?.setHasStableIds(true)
        recyclerview.adapter = mAdapter
        loading.setLoadingTipXReloadCallback(this)
        refreshLayout.setEnableRefresh(true)
        refreshLayout.setEnableLoadmore(true)
        refreshLayout.setXRefreshAndLoadListen(this)

        var adapter = ImageNetAdapter(list_img)
        bannerX.adapter = adapter
        bannerX.addBannerLifecycleObserver(activity)
        bannerX.indicator = CircleIndicator(activity)
        bannerX.setOnBannerListener { data, position ->
            var map = data as HashMap<String, String>
            showToastS(position.toString() + "," + map.get("url"))
        }
    }


    inner class GoldRecyclerAdapter(context: Context?) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val inflater: LayoutInflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view: View
            when (viewType) {
                0 -> {
                    view = inflater.inflate(R.layout.item_home_list_head, parent, false)
                    return fGoldViewHeadHolder(view)
                }
                else -> {
                    view = inflater.inflate(R.layout.item_home_list, parent, false)
                    return fGoldViewHolder(view)
                }
            }
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            if (holder is fGoldViewHolder) {
                with(holder) {
                    tv_name?.text = list[position]
                    var url = "https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2021%2F0114%2Fea09bd73p00qmxkvg008wc000fk00f4m.png&thumbnail=650x2147483647&quality=80&type=jpg"

//                    if (position != imgv?.tag) {
////                        ImagesUtils.disImg3(activity, url, holder.imgv)
//
//                        Glide.with(imgv!!)
//                            .load(DesLoaderUrl("https://testaksjdfgksadglakjljk.fengtianhaishi.com/admin/images/f95fc225dba2b1b54eff9726f88b09a8.html?sign=71e5bf704abf8cb9cd2c1da922426af3&t=1621588601"))
//                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
//                            .into(imgv!!)
//                    }
//                    if (imgv!!.tag == null) {
//                        imgv?.tag = position
////                        ImagesUtils.disImg3(activity, url, holder.imgv)
//
//                        Glide.with(imgv!!)
//                            .load(DesLoaderUrl("https://testaksjdfgksadglakjljk.fengtianhaishi.com/admin/images/f95fc225dba2b1b54eff9726f88b09a8.html?sign=71e5bf704abf8cb9cd2c1da922426af3&t=1621588601"))
//                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
//                            .into(imgv!!)
//
//
//                    }

                    ImagesUtils.disImg2(activity, url, holder.imgv)

                    Log.e("========",position.toString()+",,,"+url)
                    if (position % 2 == 0) {
                        v_02?.visibility = View.VISIBLE
                        v_01?.visibility = View.GONE
                    } else {
                        v_01?.visibility = View.VISIBLE
                        v_02?.visibility = View.GONE
                    }
                }
            }

            if (holder is fGoldViewHeadHolder) {
                with(holder) {

                    var adapter = ImageNetAdapter(list_img)
                    bannerX?.adapter = adapter
                    bannerX?.addBannerLifecycleObserver(activity)
                    bannerX?.indicator = CircleIndicator(activity)
                    bannerX?.setOnBannerListener { data, position ->
                        var map = data as HashMap<String, String>
                        showToastS(position.toString() + "," + map.get("url"))
                    }
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            if (position % 2 == 0) return 1 else return 1
        }

        inner class fGoldViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tv_name: TextView? = null
            var imgv: ImageView? = null
            var v_01: View? = null
            var v_02: View? = null

            init {
                AutoUtils.autoSize(itemView)
                tv_name = itemView.findViewById(R.id.tv_name)
                imgv = itemView.findViewById(R.id.imgv)
                v_01 = itemView.findViewById(R.id.v_01)
                v_02 = itemView.findViewById(R.id.v_02)
            }
        }

        inner class fGoldViewHeadHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var bannerX: Banner<HashMap<String, String>, ImageNetAdapter>? = null

            init {
                AutoUtils.autoSize(itemView)
                bannerX = itemView.findViewById(R.id.bannerX)
            }
        }


    }


}