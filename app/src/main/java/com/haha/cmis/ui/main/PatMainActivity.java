package com.haha.cmis.ui.main;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.Space;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.haha.cmis.ui.main.adapter.PatMainAdapter;
import com.haha.cmis.widget.GlideCircleTransform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PatMainActivity extends BaseActivity {
    @BindView(R.id.tv_next_of_kin)
    TextView tvNextOfKin;
    @BindView(R.id.tv_next_of_kin_phone)
    TextView tvNextOfKinPhone;
    @BindView(R.id.tv_mailing_address)
    TextView tvMailingAddress;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
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
    @BindView(R.id.bt_vitalsignview)
    Button btVitalsignview;
    @BindView(R.id.bt_vitalsigninput)
    Button btVitalsigninput;
    @BindView(R.id.bt_orderview)
    Button btOrderview;
    @BindView(R.id.bt_orderinput)
    Button btOrderinput;
    @BindView(R.id.bt_turnoverbodyview)
    Button btTurnoverbodyview;
    @BindView(R.id.bt_turnoverbodyinput)
    Button btTurnoverbodyinput;
    @BindView(R.id.bt_tourview)
    Button btTourview;
    @BindView(R.id.bt_tourinput)
    Button btTourinput;
    @BindView(R.id.bt_nursingrecordview)
    Button btNursingrecordview;
    @BindView(R.id.bt_nursingrecordinput)
    Button btNursingrecordinput;
    @BindView(R.id.iv_vitalsign)
    ImageView ivVitalsign;
    @BindView(R.id.tv_vitalsign)
    TextView tvVitalsign;
    @BindView(R.id.iv_order)
    ImageView ivOrder;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.iv_turnoverbody)
    ImageView ivTurnoverbody;
    @BindView(R.id.tv_turnoverbody)
    TextView tvTurnoverbody;
    @BindView(R.id.iv_tour)
    ImageView ivTour;
    @BindView(R.id.tv_tour)
    TextView tvTour;
    @BindView(R.id.iv_nursingrecord)
    ImageView ivNursingrecord;
    @BindView(R.id.tv_nursingrecord)
    TextView tvNursingrecord;
    @BindView(R.id.iv_labcheck)
    ImageView ivLabcheck;
    @BindView(R.id.tv_labcheck)
    TextView tvLabcheck;
    @BindView(R.id.iv_oper)
    ImageView ivOper;
    @BindView(R.id.tv_oper)
    TextView tvOper;
    @BindView(R.id.iv_exam)
    ImageView ivExam;
    @BindView(R.id.tv_exam)
    TextView tvExam;
    @BindView(R.id.iv_lab)
    ImageView ivLab;
    @BindView(R.id.tv_lab)
    TextView tvLab;

    private String mPatBedno;
    private String mPatientId;
    private int mVisitid;
    private String mPatientName;
    private PatMainAdapter mPatMainAdapter;
    private List<PatInfoBean> mPatInfoBeans = new ArrayList<>();
    private FunctionFragmentManger mFunctionFragmentAdapter = new FunctionFragmentManger();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_pat_main;
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
        initPatInfoToolBar();
//        initPatMainFunction();
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
        Observable.just(mPatInfoBeans)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(patInfoBeans -> {
                    int intPosition = patInfoBeans.size() - 1;
                    if ("2".equals(patInfoBeans.get(0).getBed_approved_type())) {
                        tvPatbedno.setText("✚" + patInfoBeans.get(intPosition).getBed_no() + "床" + "[" + patInfoBeans.get(intPosition).getRoom_no() + "]");
                    } else {
                        tvPatbedno.setText(patInfoBeans.get(intPosition).getBed_no() + "床" + "[" + patInfoBeans.get(intPosition).getRoom_no() + "]");
                    }
                    tvPatname.setText(patInfoBeans.get(intPosition).getName());
                    tvPatinpno.setText("住院号:" + patInfoBeans.get(intPosition).getInp_no());
                    tvPatdiagnosis.setText("诊断:" + patInfoBeans.get(intPosition).getDiagnosis());

                    tvMailingAddress.setText(patInfoBeans.get(intPosition).getMailing_address() == null ? "未填" : patInfoBeans.get(intPosition).getMailing_address());
                    tvNextOfKin.setText(patInfoBeans.get(intPosition).getNext_of_kin());
                    tvNextOfKinPhone.setText(patInfoBeans.get(intPosition).getNext_of_kin_phone() == null ? "" : patInfoBeans.get(intPosition).getNext_of_kin_phone());
                    tvPhoneNumber.setText(patInfoBeans.get(intPosition).getPhone_number_home() == null ? "" : patInfoBeans.get(intPosition).getPhone_number_home() + " " +
                            patInfoBeans.get(intPosition).getPhone_number_business() == null ? "" : patInfoBeans.get(intPosition).getPhone_number_business());

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_vitalsignview, R.id.bt_vitalsigninput, R.id.bt_orderview, R.id.bt_orderinput, R.id.bt_turnoverbodyview, R.id.bt_turnoverbodyinput, R.id.bt_tourview, R.id.bt_tourinput, R.id.bt_nursingrecordview, R.id.bt_nursingrecordinput, R.id.tv_labcheck, R.id.tv_oper, R.id.tv_exam, R.id.tv_lab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_vitalsignview:
                funcExecute("生命体征查看");
                break;
            case R.id.bt_vitalsigninput:
                funcExecute("生命体征");
                break;
            case R.id.bt_orderview:
                break;
            case R.id.bt_orderinput:
                funcExecute("医嘱执行");
                break;
            case R.id.bt_turnoverbodyview:
                break;
            case R.id.bt_turnoverbodyinput:
                funcExecute("翻身管理");
                break;
            case R.id.bt_tourview:
                break;
            case R.id.bt_tourinput:
                funcExecute("病人巡视");
                break;
            case R.id.bt_nursingrecordview:
                break;
            case R.id.bt_nursingrecordinput:
                funcExecute("护理记录");
                break;
            case R.id.tv_labcheck:
                funcExecute("化验采集");
                break;
            case R.id.tv_oper:
                funcExecute("手术核查");
                break;
            case R.id.tv_exam:
                funcExecute("检查报告");
                break;
            case R.id.tv_lab:
                funcExecute("检验报告");
                break;
        }
    }

    private void funcExecute(String strType) {
        Fragment functionFragment = mFunctionFragmentAdapter.get(strType);
        if (null == functionFragment) {
            infoShow("error", mFunctionFragmentAdapter.get(strType) + "功能暂未实现");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("patinfo", (ArrayList<? extends Parcelable>) mPatInfoBeans);
        bundle.putString(AppConfig.appTransInfo.TransFunctionClass, strType);
        gotoActivity(PatFunctionActivity.class, false, bundle);
    }


//    private void initPatMainFunction() {
//        List<PatMainEntity> functionList = new ArrayList<>();
//        for (int i = 0; i < AppConfig.patMaintypeName1.length; i++) {
//            functionList.add(new PatMainEntity(1, AppConfig.patMaintypeName1[i], AppConfig.patMaintypeImg1[i], 4));
//        }
//
//        for (int i = 0; i < AppConfig.patMaintypeName2.length; i++) {
//            functionList.add(new PatMainEntity(2, AppConfig.patMaintypeName2[i], AppConfig.patMaintypeImg2[i], 2));
//        }
//
//        rvPatmain.setLayoutManager(new GridLayoutManager(this, 4));
//        mPatMainAdapter = new PatMainAdapter(functionList);
//        rvPatmain.setHasFixedSize(true);
//        mPatMainAdapter.setSpanSizeLookup((gridLayoutManager, position) -> functionList.get(position).getSpanSize());
//        rvPatmain.setAdapter(mPatMainAdapter);
//
//
//        mPatMainAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                Log.d(TAG, "onItemChildClick: " );
//                Fragment functionFragment = mFunctionFragmentAdapter.get(mPatMainAdapter.getItem(position).getItemTitle());
//                if (null == functionFragment) {
//                    infoShow("error", mPatMainAdapter.getItem(position).getItemTitle() + "功能暂未实现");
//                    return;
//                }
//                if (view.getId()==R.id.bt_view){
//                    infoShow("", "bt_view");
//                } else if (view.getId()==R.id.bt_input) {
//                    infoShow("", "bt_input");
//                }
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("patinfo", (ArrayList<? extends Parcelable>) mPatInfoBeans);
//                bundle.putString(AppConfig.appTransInfo.TransFunctionClass, mPatMainAdapter.getItem(position).getItemTitle());
//                gotoActivity(PatFunctionActivity.class, false, bundle);
//            }
//
//        });
//    }

}
