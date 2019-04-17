package com.haha.cmis.ui.home;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haha.cmis.R;
import com.haha.cmis.base.BaseFragment;
import com.haha.cmis.constant.AppCookie;
import com.haha.cmis.dbhelper.LocalDbDao;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author haha
 */
public class DeptDynamicsFragment extends BaseFragment {

    @BindView(R.id.tv_totaltext)
    TextView tvTotaltext;
    @BindView(R.id.tv_totalnums)
    TextView tvTotalnums;
    @BindView(R.id.tv_newintext)
    TextView tvNewintext;
    @BindView(R.id.tv_newinnums)
    TextView tvNewinnums;
    @BindView(R.id.tv_predistext)
    TextView tvPredistext;
    @BindView(R.id.tv_predisnums)
    TextView tvPredisnums;
    @BindView(R.id.tv_wnums)
    TextView tvWnums;
    @BindView(R.id.tv_znums)
    TextView tvZnums;
    @BindView(R.id.tv_ybnums)
    TextView tvYbnums;
    @BindView(R.id.tv_swnums)
    TextView tvSwnums;
    @BindView(R.id.home_operation_center)
    RelativeLayout homeOperationCenter;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_deptdynamics;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
                refreshLayout.finishRefresh();
            }
        });

    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        /**获取科室当前病人统计信息*/
        LocalDbDao.getInstance().queryDeptDynamics(AppCookie.getDeptcode())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringStringMap -> {
                            tvTotalnums.setText(stringStringMap.get("total"));
                            tvNewinnums.setText(stringStringMap.get("newin"));
                            tvPredisnums.setText(stringStringMap.get("preout"));
                            tvWnums.setText(stringStringMap.get("emer"));
                            tvZnums.setText(stringStringMap.get("grave"));
                            tvYbnums.setText(stringStringMap.get("normal"));
                            tvSwnums.setText(stringStringMap.get("death"));
                        },
                        throwable -> {
                            infoShow("error", "数据获取错误！");
                        });

        /**获取当前任务*/


    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected Boolean getIsScanBar() {
        return true;
    }


    @Override
    public void onBarScanEvent(String strBarCode) {
        super.onBarScanEvent(strBarCode);
    }


}
