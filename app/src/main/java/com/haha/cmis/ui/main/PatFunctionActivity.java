package com.haha.cmis.ui.main;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.Space;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haha.cmis.R;
import com.haha.cmis.base.BaseActivity;
import com.haha.cmis.bean.PatInfoBean;
import com.haha.cmis.constant.AppConfig;
import com.haha.cmis.ui.main.adapter.FunctionFragmentManger;
import com.haha.cmis.widget.GlideCircleTransform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PatFunctionActivity extends BaseActivity {
    @BindView(R.id.tv_patimg)
    ImageView tvPatimg;
    @BindView(R.id.tv_patname)
    TextView tvPatname;
    @BindView(R.id.tv_patinpno)
    TextView tvPatinpno;
    @BindView(R.id.ll_patinfo)
    LinearLayout llPatinfo;
    @BindView(R.id.iv_patsex)
    ImageView ivPatsex;
    @BindView(R.id.space_null)
    Space spaceNull;
    @BindView(R.id.tv_patbedno)
    TextView tvPatbedno;
    @BindView(R.id.tv_patdiagnosis)
    TextView tvPatdiagnosis;
    @BindView(R.id.rl_actionbar)
    RelativeLayout rlActionbar;
    @BindView(R.id.ll_functionclass)
    FrameLayout llFunctionclass;

    private List<PatInfoBean> mPatInfoBeans = new ArrayList<>();
    private String mFunctionClass;
    private FunctionFragmentManger mFunctionFragmentAdapter = new FunctionFragmentManger();
    private Boolean isEnterFunction = false;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_pat_function;
    }

    @Override
    protected Boolean getIsFullScreen() {
        return false;
    }

    @Override
    protected Boolean getIsShowTitle() {
        return true;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        mPatInfoBeans = bundle.getParcelableArrayList("patinfo");
        mFunctionClass = bundle.get(AppConfig.appTransInfo.TransFunctionClass).toString();
        initPatInfoToolBar();
        setFunctionClass(mFunctionClass);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected Boolean getIsScanBar() {
        return true;
    }


    @SuppressLint("CheckResult")
    private void initPatInfoToolBar() {
        io.reactivex.Observable.just(mPatInfoBeans)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(patInfoBeans -> {
                    int intPosition = patInfoBeans.size() - 1;
                    if ("2".equals(patInfoBeans.get(intPosition).getBed_approved_type())) {
                        tvPatbedno.setText("✚" + patInfoBeans.get(intPosition).getBed_no() + "床" + "[" + patInfoBeans.get(intPosition).getRoom_no() + "]");
                    } else {
                        tvPatbedno.setText(patInfoBeans.get(intPosition).getBed_no() + "床" + "[" + patInfoBeans.get(intPosition).getRoom_no() + "]");
                    }
                    tvPatname.setText(patInfoBeans.get(intPosition).getName());
                    tvPatinpno.setText("住院号:" + patInfoBeans.get(intPosition).getInp_no());
                    tvPatdiagnosis.setText("诊断:" + patInfoBeans.get(intPosition).getDiagnosis());

                    if ("男".equals(patInfoBeans.get(intPosition).getSex())) {
                        ivPatsex.setImageResource(R.drawable.ic_male);
                    } else {
                        ivPatsex.setImageResource(R.drawable.ic_female);
                    }

                    switch (patInfoBeans.get(intPosition).getPatient_class()) {
                        case "特级护理":
                            findViewById(R.id.rl_actionbar).setBackgroundColor(getResources().getColor(R.color.color_special_class));
                            break;
                        case "一级护理":
                            findViewById(R.id.rl_actionbar).setBackgroundColor(getResources().getColor(R.color.color_one_class));
                            break;
                        case "二级护理":
                            findViewById(R.id.rl_actionbar).setBackgroundColor(getResources().getColor(R.color.color_two_class));
                            break;
                        case "三级护理":
                            findViewById(R.id.rl_actionbar).setBackgroundColor(getResources().getColor(R.color.color_three_class));
                            break;
                    }

                    //获取病人头像
                    if (!TextUtils.isEmpty(patInfoBeans.get(intPosition).getPatient_image())) {
                        Glide.with(getApplication())
                                .load(Uri.fromFile(new File(patInfoBeans.get(intPosition).getPatient_image()))).dontAnimate().centerCrop()
                                .transform(new GlideCircleTransform(context))
                                .skipMemoryCache(true).into(tvPatimg);
                    } else {
                        tvPatimg.setImageResource(R.drawable.ic_user);
                    }
                }, throwable -> {
                    infoShow("ERROR", "获取病人基本信息失败!");
                });

    }

    private void setFunctionClass(String strFunctionClass) {
        Fragment functionFragment = mFunctionFragmentAdapter.get(strFunctionClass);
        if (null == functionFragment) {
            infoShow("error", strFunctionClass + "功能暂未实现");
            return;
        }
        Bundle mBundle = new Bundle();
        mBundle.putParcelableArrayList("patinfo", (ArrayList<? extends Parcelable>) mPatInfoBeans);
        //判断类型
        switch (strFunctionClass) {
            case "生命体征":
                mBundle.putString(AppConfig.appTransInfo.TransOrderTypeFlag, "1");
                break;
            case "口服药管理":
                mBundle.putString(AppConfig.appTransInfo.TransOrderTypeFlag, "1");
                break;
            case "输液管理":
                mBundle.putString(AppConfig.appTransInfo.TransOrderTypeFlag, "1");
                break;
            case "注射管理":
                mBundle.putString(AppConfig.appTransInfo.TransOrderTypeFlag, "1");
                break;
            case "医嘱执行":
                mBundle.putString(AppConfig.appTransInfo.TransOrderTypeFlag, "2");
                break;
        }
        mBundle.putString(AppConfig.appTransInfo.TransOrderType, strFunctionClass);
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        functionFragment.setArguments(mBundle);
        fm.replace(R.id.ll_functionclass, functionFragment).commitAllowingStateLoss();
        isEnterFunction = true;
    }


}
