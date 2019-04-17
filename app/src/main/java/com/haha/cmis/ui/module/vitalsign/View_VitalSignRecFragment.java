package com.haha.cmis.ui.module.vitalsign;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haha.cmis.R;
import com.haha.cmis.base.BaseFragment;
import com.haha.cmis.bean.PatInfoBean;
import com.haha.cmis.bean.PatVitalSignRecBean;
import com.haha.cmis.dbhelper.LocalDbDao;
import com.haha.cmis.ui.module.vitalsign.adapter.PatVitalSignRecAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class View_VitalSignRecFragment extends BaseFragment {

    @BindView(R.id.rv_vitalsigrnRecList)
    SwipeMenuRecyclerView rvVitalsigrnRecList;
    Unbinder unbinder;

    private PatVitalSignRecAdapter patVitalSignRecAdapter;
    private List<PatInfoBean> mPatInfoBean = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_view_vital_sign_rec;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        rvVitalsigrnRecList.setHasFixedSize(true);
        rvVitalsigrnRecList.setLayoutManager(new LinearLayoutManager(getContext()));
        patVitalSignRecAdapter = new PatVitalSignRecAdapter(R.layout.item_vital_sign_rec_list);

        mPatInfoBean = getArguments().getParcelableArrayList("patinfo");
        if (mPatInfoBean == null || mPatInfoBean.size() == 0) {
            infoShow("error", "传递病人基础数据失败！");
        } else {

        }


    }

    @Override
    protected void initData() {
        getPatVitalSignRec(mPatInfoBean.get(mPatInfoBean.size() - 1).getPatient_id(), mPatInfoBean.get(mPatInfoBean.size() - 1).getVisit_id());
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected Boolean getIsScanBar() {
        return null;
    }


    @SuppressLint("CheckResult")
    private void getPatVitalSignRec(String strPatientid, int intVisitid) {
        Log.d(TAG, "getPatVitalSignRec: " + strPatientid + intVisitid);
        //查询体温数据
        Observable<List<PatVitalSignRecBean>> patvitalsignrec = LocalDbDao.getInstance().querypatsvitalsignrec(strPatientid, intVisitid);
        patvitalsignrec.subscribe(patVitalSignRecBeen -> {
            Log.d(TAG, "getPatVitalSignRec: " + patVitalSignRecBeen.size());
            patVitalSignRecAdapter.setNewData(patVitalSignRecBeen);
            rvVitalsigrnRecList.setAdapter(patVitalSignRecAdapter);
        }, throwable -> infoShow("ERROR", "获取病人体征信息失败\n" + throwable.getMessage()));
    }

}
