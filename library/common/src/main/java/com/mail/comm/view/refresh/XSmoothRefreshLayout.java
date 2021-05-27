package com.mail.comm.view.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.mail.comm.view.refresh.XRefreshAndLoadListen;
import com.zhy.autolayout.utils.AutoUtils;

import me.dkzwm.widget.srl.RefreshingListenerAdapter;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.extra.footer.MaterialFooter;
import me.dkzwm.widget.srl.extra.header.MaterialHeader;
import me.dkzwm.widget.srl.indicator.IIndicator;
import me.dkzwm.widget.srl.util.PixelUtl;

public class XSmoothRefreshLayout extends SmoothRefreshLayout {


    private XRefreshAndLoadListen refreshAndLoadListen;

    public void setXRefreshAndLoadListen(XRefreshAndLoadListen refreshAndLoadListen) {
        this.refreshAndLoadListen = refreshAndLoadListen;
    }

    public XSmoothRefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public XSmoothRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public XSmoothRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setEnableRefresh(boolean f) {
        setDisableRefresh(!f);
    }

    public void setEnableLoadmore(boolean f) {
        setDisableLoadMore(!f);
    }

    public void startRefresh() {
        autoRefresh(true);
    }

    public  void setIsPinContentView(boolean s){
        setEnablePinContentView(s);
    }


    private void init(Context context) {
        int[] src = {0xff000000};
        MaterialHeader header = new MaterialHeader(context);
        header.setColorSchemeColors(src);
//        header.setP
//        header.setColorSchemeColors();
        header.setPadding(0, PixelUtl.dp2px(context, 40), 0, PixelUtl.dp2px(context, 20));
        setHeaderView(header);
        setEnablePullToRefresh(false);
//        setEnableNextPtrAtOnce(false);
        setDisableLoadMore(true);
        MaterialFooter footer = new MaterialFooter(context);

        footer.setProgressBarColors(src);
        footer.setProgressBarWidth(5);
        footer.setProgressBarRadius(AutoUtils.getPercentWidthSize(20));
        setFooterView(footer);

//        setDisableRefresh();
        setIndicatorOffsetCalculator(new IIndicator.IOffsetCalculator() {
            @Override
            public float calculate(int status, int currentPos, float offset) {
                if (status == IIndicator.DEFAULT_RATIO_TO_REFRESH) {
                    if (offset < 0) {
                        return offset;
                    }
                    return (float) Math.pow(Math.pow(currentPos / 2, 1.28d) + offset, 1 / 1.28d) * 2 - currentPos;
                } else if (status == IIndicator.DEFAULT_RATIO_TO_REFRESH) {
                    if (offset > 0) {
                        return offset;
                    }
                    return -((float) Math.pow(Math.pow(currentPos / 2, 1.28d) - offset, 1 / 1.28d) * 2 - currentPos);
                } else {
                    if (offset > 0) {
                        return (float) Math.pow(offset, 1 / 1.28d) * 2;
                    } else if (offset < 0) {
                        return -(float) Math.pow(-offset, 1 / 1.28d) * 2;
                    } else {
                        return offset;
                    }
                }
            }
        });

        setOnRefreshListener(new RefreshingListenerAdapter() {
            @Override
            public void onRefreshing() {
                if (refreshAndLoadListen != null) {
                    refreshAndLoadListen.refreshStart();
                }
            }

            @Override
            public void onLoadingMore() {
                if (refreshAndLoadListen != null) {
                    refreshAndLoadListen.loadMoreStart();
                }
            }
        });
    }


    public void loadMoreReturn() {
        setDisableLoadMore(false);
//        setDisablePerformRefresh(true);
        setDisablePerformLoadMore(true);
        getFooterView().getView().setVisibility(View.GONE);
    }

    public void loadMoreReturn2() {
        setDisableLoadMore(false);
        setDisablePerformRefresh(true);
        setDisablePerformLoadMore(true);
        setEnableKeepRefreshView(false);
        getHeaderView().getView().setVisibility(View.GONE);
        getFooterView().getView().setVisibility(View.GONE);
    }

    public void finishLoadmore() {
        refreshComplete();
    }

    public void finishRefreshing() {
        refreshComplete();
    }

}
