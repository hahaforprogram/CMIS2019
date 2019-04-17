package com.haha.cmis.ui.home;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haha.cmis.R;
import com.haha.cmis.base.BaseFragment;
import com.haha.cmis.bean.PatInfoBean;
import com.haha.cmis.constant.AppCookie;
import com.haha.cmis.dbhelper.LocalDbDao;
import com.haha.cmis.ui.main.PatMainActivity;
import com.haha.cmis.ui.mainhome.adapter.HomeBedInfoAdapter;
import com.haha.cmis.widget.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 病人列表信息
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.rv_bedinfo)
    SwipeMenuRecyclerView rvBedinfo;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;

    private List<PatInfoBean> mPatinfobean;
    private HomeBedInfoAdapter mHomeBedInfoAdapter;

    @Override
    protected void initEvent() {

    }

    @Override
    protected Boolean getIsScanBar() {
        return true;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        rvBedinfo.setHasFixedSize(true);
        rvBedinfo.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBedinfo.addItemDecoration(new RecyclerViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.gray)));
        mHomeBedInfoAdapter = new HomeBedInfoAdapter(R.layout.item_home_bedinfo_list);
        rvBedinfo.setAdapter(mHomeBedInfoAdapter);
        mHomeBedInfoAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mHomeBedInfoAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<PatInfoBean> transPatInfoBeans = new ArrayList<>();
            transPatInfoBeans.add(mPatinfobean.get(position));
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("patinfo", (ArrayList<? extends Parcelable>) transPatInfoBeans);
            HomeFragment.this.gotoActivity(PatMainActivity.class, bundle);
        });
        mHomeBedInfoAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            infoShow("info", "abccccc");
            return false;
        });

        refreshLayout.setOnRefreshListener(refreshlayout -> {
            initData();
            refreshlayout.finishRefresh();
        });

    }


    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        LogUtils.d("initData: ");
        LocalDbDao.getInstance().queryPatsInfo(AppCookie.getDeptcode(), null, null, null)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(patInfoBeans -> {
                    Log.d(TAG, "initData: " + patInfoBeans.size());
                    mPatinfobean = patInfoBeans;
                    mHomeBedInfoAdapter.setNewData(mPatinfobean);
                }, throwable -> {
                    infoShow("error", "数据获取错误\n" + throwable.getMessage());
                });

    }

    @Override
    public void onBarScanEvent(String strBarCode) {
        super.onBarScanEvent(strBarCode);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
