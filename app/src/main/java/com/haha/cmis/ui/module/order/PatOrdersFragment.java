package com.haha.cmis.ui.module.order;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.haha.cmis.R;
import com.haha.cmis.base.BaseFragment;
import com.haha.cmis.bean.PatInfoBean;
import com.haha.cmis.bean.PatOrderBean;
import com.haha.cmis.constant.AppCookie;
import com.haha.cmis.constant.AppSql;
import com.haha.cmis.dbhelper.LocalDbDao;
import com.haha.cmis.ui.module.order.adapter.PatOrderAdapter;
import com.haha.cmis.utils.DateTimeHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatOrdersFragment extends BaseFragment {
    @BindView(R.id.rv_orderlist)
    RecyclerView rvOrderlist;
    @BindView(R.id.tl_orderFilter)
    TabLayout tlOrderFilter;


    private PatOrderAdapter mPatOrderAdapter;
    private final CompositeDisposable mDisposables = new CompositeDisposable();
    private List<PatOrderBean> mPatOrderBean = new ArrayList<>();
    private int mTabSelectedPosition = 0;
    private List<PatInfoBean> mPatInfoBean = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_pat_orders;
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected Boolean getIsScanBar() {
        return true;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPatInfoBean = getArguments().getParcelableArrayList("patinfo");
        if (mPatInfoBean == null || mPatInfoBean.size() == 0) {
            infoShow("error", "传递病人基础数据失败！");
        }
        rvOrderlist.setLayoutManager(new LinearLayoutManager(getContext()));
        mPatOrderAdapter = new PatOrderAdapter(R.layout.item_order_list, null);
        mPatOrderAdapter.openLoadAnimation();
        rvOrderlist.setAdapter(mPatOrderAdapter);
        //点击弹出提示
        mPatOrderAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        //tab选择
        tlOrderFilter.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTabSelectedPosition = tab.getPosition();
                itemSelect(mTabSelectedPosition);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mTabSelectedPosition = tab.getPosition();
                itemSelect(mTabSelectedPosition);
            }
        });
    }

    private void itemSelect(int intPosition) {
        getOrders(intPosition);
    }

    @Override
    protected void initData() {
        getOrders(0);
    }


    @SuppressLint("CheckResult")
    private void getOrders(int intFlag) {
        String strSql;
        switch (intFlag) {
            case 0:
                strSql = AppSql.sqlselectPatsOrders + " order by po.plan_exec_datetime asc";
                break;
            case 1:
                strSql = AppSql.sqlselectPatsOrders + " order by po.plan_exec_datetime asc";
                break;
            case 2:
                strSql = AppSql.sqlselectPatsOrders + " order by po.plan_exec_datetime asc";
                break;
            default:
                strSql = AppSql.sqlselectPatsOrders + " order by po.plan_exec_datetime asc";
                break;
        }
        //查询医嘱信息
        LocalDbDao.getInstance().queryPatsOrder(strSql, new String[]{mPatInfoBean.get(mPatInfoBean.size() - 1).getPatient_id(), String.valueOf(mPatInfoBean.get(mPatInfoBean.size() - 1).getVisit_id())})
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(patOrderBeans -> {
                    mPatOrderAdapter.setNewData(patOrderBeans);
                    Log.d(TAG, "getOrders: " + patOrderBeans.size());
                }, throwable -> {

                });
    }


    private void setUpTabBadge(int badgeNums) {
//        TabLayout.Tab tab = tlOrderFilter.getTabAt(mTabSelectedPosition);
//        tab.setText(tab.getText()+String.valueOf(badgeNums));
    }

    //执行或取消医嘱
    private void executeOrder(final int position, final int intType) {
        final String execdatetime = intType == 1 ? DateTimeHelper.getcurrentDate() : null;
        final String execoperator = intType == 1 ? AppCookie.getUserid() : null;
        //写入执行时间 执行人
        mDisposables.add(new LocalDbDao().executePatsOrder(mPatOrderAdapter.getData().get(position), execdatetime, execoperator)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        mPatOrderAdapter.getItem(position).setEXEC_DATETIME(execdatetime);
                        mPatOrderAdapter.getItem(position).setEXEC_OPERATOR(execoperator);
                        mPatOrderAdapter.notifyItemChanged(position);
                    } else {
                        infoShow("error", mPatOrderAdapter.getItem(position).getORDER_TEXT() + "\n执行失败");
                    }
                }, throwable -> infoShow("error", "操作失败" + throwable.getMessage())));
    }


}
