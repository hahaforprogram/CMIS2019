package com.haha.cmis.ui.module.vitalsign;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.bean.DateType;
import com.haha.cmis.R;
import com.haha.cmis.base.BaseFragment;
import com.haha.cmis.bean.PatInfoBean;
import com.haha.cmis.bean.PatVitalSignRecBean;
import com.haha.cmis.constant.AppCookie;
import com.haha.cmis.customkeyboard.CustomBaseKeyboard;
import com.haha.cmis.customkeyboard.CustomKeyboardManager;
import com.haha.cmis.dbhelper.LocalDbDao;
import com.haha.cmis.ui.module.vitalsign.adapter.PatVitalSignRecAdapter;
import com.haha.cmis.utils.MyUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */
public class VitalSignRecFragment extends BaseFragment {


    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_timeunits)
    TextView tvTimeunits;
    @BindView(R.id.tv_twtext)
    TextView tvTwtext;
    @BindView(R.id.ll_tw)
    LinearLayout llTw;
    @BindView(R.id.view_tw)
    View viewTw;
    @BindView(R.id.et_tw)
    EditText etTw;
    @BindView(R.id.tv_xytext)
    TextView tvXytext;
    @BindView(R.id.ll_xy)
    LinearLayout llXy;
    @BindView(R.id.view_xy)
    View viewXy;
    @BindView(R.id.et_xyssy)
    EditText etXyssy;
    @BindView(R.id.et_xyszy)
    EditText etXyszy;
    @BindView(R.id.tv_xltext)
    TextView tvXltext;
    @BindView(R.id.ll_xl)
    LinearLayout llXl;
    @BindView(R.id.view_xl)
    View viewXl;
    @BindView(R.id.et_xl)
    EditText etXl;
    @BindView(R.id.tv_mbtext)
    TextView tvMbtext;
    @BindView(R.id.ll_mb)
    LinearLayout llMb;
    @BindView(R.id.view_mb)
    View viewMb;
    @BindView(R.id.et_mb)
    EditText etMb;
    @BindView(R.id.tv_hxtext)
    TextView tvHxtext;
    @BindView(R.id.ll_hx)
    LinearLayout llHx;
    @BindView(R.id.view_hx)
    View viewHx;
    @BindView(R.id.et_hx)
    EditText etHx;
    @BindView(R.id.tv_xttext)
    TextView tvXttext;
    @BindView(R.id.ll_xt)
    LinearLayout llXt;
    @BindView(R.id.view_xt)
    View viewXt;
    @BindView(R.id.et_xt)
    EditText etXt;

    @BindView(R.id.tv_zdytext)
    TextView tvZdytext;
    @BindView(R.id.ll_zdy)
    LinearLayout llZdy;
    @BindView(R.id.view_zdy)
    View viewZdy;
    @BindView(R.id.et_zdy)
    EditText etZdy;
    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.sv_main)
    ScrollView svMain;
    Unbinder unbinder;
    private CustomKeyboardManager customKeyboardManager;
    private CustomBaseKeyboard vitalSignCustomKeyboard, otherCustomKeyboard;
    private OptionsPickerView customOptionsPickerView;
    private List<String> mCustomVitalSignDataBean = new ArrayList<>();
    private boolean bPickViewStatus = false;
    private boolean bCustomKeyBoardStatus = false;
    private List<PatInfoBean> mPatInfoBean = new ArrayList<>();
    private PatVitalSignRecAdapter patVitalSignRecAdapter;
    //所有数据 edittext
    private List<EditText> sEditTextList = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_vitalsignrec;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initNowDate();
        initPickCustomView();
        initKeyBoard();

        etZdy.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //判断是否选择
                    if (tvZdytext.getText().equals("用户自定义")) {
                        vitalSignCustomKeyboard.hideKeyboard();
                        otherCustomKeyboard.hideKeyboard();
                        if (!customOptionsPickerView.isShowing()) {
                            hideSoftInputMethod(etZdy);
                            tvZdytext.clearFocus();
                            customOptionsPickerView.show();
                            bPickViewStatus = true;
                        }
                    }
                } else {

                }
            }
        });
    }

    @Override
    protected void initData() {
        mPatInfoBean = getArguments().getParcelableArrayList("patinfo");
        if (mPatInfoBean == null || mPatInfoBean.size() == 0) {
            infoShow("error", "传递病人基础数据失败！");
        } else {

        }

        infoShow("info", mPatInfoBean.get(0).getName() + mPatInfoBean.get(0).getBed_no());

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected Boolean getIsScanBar() {
        return null;
    }


    private void initKeyBoard() {
        //初始化键盘
        initCustomKeyboard();
        sEditTextList.add(etTw);
        customKeyboardManager.attachTo(etTw, vitalSignCustomKeyboard);
        sEditTextList.add(etXyssy);
        customKeyboardManager.attachTo(etXyssy, otherCustomKeyboard);
        sEditTextList.add(etXyszy);
        customKeyboardManager.attachTo(etXyszy, otherCustomKeyboard);
        sEditTextList.add(etXl);
        customKeyboardManager.attachTo(etXl, otherCustomKeyboard);
        sEditTextList.add(etMb);
        customKeyboardManager.attachTo(etMb, otherCustomKeyboard);
        sEditTextList.add(etHx);
        customKeyboardManager.attachTo(etHx, otherCustomKeyboard);
        sEditTextList.add(etXt);
        customKeyboardManager.attachTo(etXt, otherCustomKeyboard);
        sEditTextList.add(etZdy);
        customKeyboardManager.attachTo(etZdy, otherCustomKeyboard);
        hideSoftInputMethod(etTw);
        hideSoftInputMethod(etXyssy);
        hideSoftInputMethod(etXyszy);
        hideSoftInputMethod(etXl);
        hideSoftInputMethod(etMb);
        hideSoftInputMethod(etHx);
        hideSoftInputMethod(etXt);
        hideSoftInputMethod(etZdy);
        viewTw.requestFocus();
    }


    private void initCustomKeyboard() {
        customKeyboardManager = new CustomKeyboardManager(getActivity());
        vitalSignCustomKeyboard = new CustomBaseKeyboard(getActivity(), R.xml.custom_keyboard_for_temperature) {
            @Override
            public boolean handleSpecialKey(@NonNull EditText etCurrent, int primaryCode) {
                if (primaryCode == -1000) {
                    tvTwtext.setText("体温-腋下");
                    return true;
                }
                if (primaryCode == -1001) {
                    tvTwtext.setText("体温-口表");
                    return true;
                }
                if (primaryCode == -1002) {
                    tvTwtext.setText("体温-肛表");
                    return true;
                }
                if (primaryCode == -1003) {
                    tvTwtext.setText("体温-物理降温");
                    return true;
                }
                if (primaryCode <= -3600 && primaryCode >= -3900) {
                    etCurrent.setText(String.valueOf(primaryCode).substring(1, 3) + ".");
                    Selection.setSelection(etCurrent.getText(), etCurrent.getText().length());
                    return true;
                }
                if (primaryCode == -9000) {
                    customKeyboardManager.hideSoftKeyboard(etCurrent);
                    return true;
                }
                return false;
            }
        };
        vitalSignCustomKeyboard.setCustomKeyStyle(new CustomBaseKeyboard.SimpleCustomKeyStyle() {

            @Override
            public Drawable getKeyBackground(@NonNull Keyboard.Key key, @NonNull EditText etCur) {
                if (-3900 == key.codes[0]) {
                    return getDrawable(etCur.getContext(), R.drawable.bg_custom_key_blue);
                }
                return super.getKeyBackground(key, etCur);
            }

            @Override
            public CharSequence getKeyLabel(Keyboard.Key key, EditText etCur) {
                return super.getKeyLabel(key, etCur);
            }

            @Override
            public Integer getKeyTextColor(@NonNull Keyboard.Key key, EditText etCur) {
                if (-3900 == key.codes[0]) {
                    return getResources().getColor(R.color.red_marker);
                }
                return super.getKeyTextColor(key, etCur);
            }
        });
        otherCustomKeyboard = new CustomBaseKeyboard(getActivity(), R.xml.custom_keyboard_for_other) {
            @Override
            public boolean handleSpecialKey(@NonNull EditText etCurrent, int primaryCode) {
                if (primaryCode == 1) {
                    return true;
                }
                if (primaryCode == -9000) {
                    customKeyboardManager.hideSoftKeyboard(etCurrent);
                    return true;
                }
                return false;
            }
        };
    }

    private void initPickCustomView() {
        getCustomVitalSignDataBean();
        customOptionsPickerView = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                etZdy.setFocusable(true);
                etZdy.setFocusableInTouchMode(true);
                etZdy.requestFocus();
            }
        }).setTitleText("自定义选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.BLACK)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.LTGRAY)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .setOutSideCancelable(true)
                .setOptionsSelectChangeListener((options1, options2, options3) -> {
                    LogUtils.d("onOptionsSelectChanged: " + options1 + options2 + options3);
                    tvZdytext.setText(mCustomVitalSignDataBean.get(options1));
                    etZdy.setFocusable(true);
                    etZdy.setFocusableInTouchMode(true);
                    etZdy.requestFocus();
                    etZdy.setTag(mCustomVitalSignDataBean.get(options1));
                }).build();
        customOptionsPickerView.setPicker(mCustomVitalSignDataBean);
    }

    private void initNowDate() {
        //当前时间  如果超过50则算下个时段
        Calendar calendar = Calendar.getInstance();
        long curdate = System.currentTimeMillis();
        //分钟
        int minute = calendar.get(Calendar.MINUTE);
        if (minute > 50) {
            curdate += 60 * 60 * 1000;
        }
        Date nowDate = new Date(curdate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH", Locale.CHINA);
        tvDate.setText(sdf.format(nowDate));
        tvTime.setText(sdf1.format(nowDate) + ":00");
    }

    private void showDatePickDialog(DateType type) {
        bPickViewStatus = true;
        DatePickDialog dialog = new DatePickDialog(getContext());
        //设置上下年分限制
        dialog.setYearLimt(0);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(type);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(date -> {

        });
        //设置点击确定按钮回调
        dialog.setOnSureLisener(date -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH", Locale.CHINA);
            tvDate.setText(sdf.format(date));
            tvTime.setText(sdf1.format(date) + ":00");
            bPickViewStatus = false;
        });
        dialog.show();
    }

    @OnClick({R.id.tv_date, R.id.tv_time, R.id.tv_zdytext, R.id.et_zdy, R.id.btn_save})
    public void onViewClicked(@NonNull View v) {
        if (v.getId() == R.id.tv_zdytext) {
            if (!customOptionsPickerView.isShowing()) {
                tvZdytext.clearFocus();
                customOptionsPickerView.show();
                bPickViewStatus = true;
            }
        } else if (v.getId() == R.id.tv_date) {
            initNowDate();
        } else if (v.getId() == R.id.tv_time) {
            showDatePickDialog(DateType.TYPE_YMDH);
        } else if (v.getId() == R.id.et_zdy) {
            Log.d(TAG, "onViewClicked: " + tvZdytext.getText());
            if (tvZdytext.getText().equals("用户自定义") && !customOptionsPickerView.isShowing()) {
                tvZdytext.clearFocus();
                customOptionsPickerView.show();
            }
        } else if (v.getId() == R.id.btn_save) {
            saveVitalSignRec();
        }
    }


    public void getCustomVitalSignDataBean() {
        mCustomVitalSignDataBean.add("中国:");
        mCustomVitalSignDataBean.add("北京:");
        mCustomVitalSignDataBean.add("布鞋:");
        mCustomVitalSignDataBean.add("撒地方:");
    }

    //保存数据
    @SuppressWarnings("CheckResult")
    private void saveVitalSignRec() {
        //判断自定义的是否已经选择 否则提示
        if (tvZdytext.getText().equals("用户自定义") && (!TextUtils.isEmpty(etZdy.getText()))) {
            infoShow("error", "自定义选择项未选择!");
            return;
        }

        List<PatVitalSignRecBean> patVitalSignRecBeans;
        patVitalSignRecBeans = getItemsData();
        if (patVitalSignRecBeans.isEmpty() || patVitalSignRecBeans.size() <= 0) {
            infoShow("info", "无数据");
            return;
        }
        LocalDbDao.getInstance().insertPatsVitalSignRec(patVitalSignRecBeans)
                .subscribe(aBoolean -> {
                    getPatVitalSignRec(mPatInfoBean.get(mPatInfoBean.size() - 1).getPatient_id(), mPatInfoBean.get(mPatInfoBean.size() - 1).getVisit_id());
//                    btnSave.setText("保存成功");
                    infoShow("INFO", "保存成功");
                }, throwable -> {
                    infoShow("INFO", "保存失败\n" + throwable.getMessage());
//                    btnSave.setText("保存失败");

                });
    }


    @SuppressLint("CheckResult")
    private void getPatVitalSignRec(String strPatientid, int intVisitid) {
        //查询体温数据
        Observable<List<PatVitalSignRecBean>> patvitalsignrec = LocalDbDao.getInstance().querypatsvitalsignrec(strPatientid, intVisitid);
        patvitalsignrec.subscribe(patVitalSignRecBeen -> {
            patVitalSignRecAdapter = new PatVitalSignRecAdapter(R.layout.item_vital_sign_rec_list, patVitalSignRecBeen);
        }, throwable -> infoShow("ERROR", "获取病人体征信息失败\n" + throwable.getMessage()));
    }


    //获取输入的值 并返回 
    private List<PatVitalSignRecBean> getItemsData() {
        List<PatVitalSignRecBean> patVitalSignRecBeans = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);  //取日期
        for (int i = 0; i < sEditTextList.size(); i++) {
            String strData = sEditTextList.get(i).getText().toString().trim();
            if (TextUtils.isEmpty(strData)) {
                continue;
            }

            String strDataUnits = sEditTextList.get(i).getTag().toString();
            String[] dataUnits = strDataUnits.split(":");
            PatVitalSignRecBean data = new PatVitalSignRecBean();
            data.setVital_sign_values(strData);
            data.setOperator(AppCookie.getUsercode());
            data.setEnter_date_time(sdf.format(new Date(System.currentTimeMillis())));
            data.setSync_flag(0);
            data.setSync_machine(AppCookie.getDeviceId());
            data.setPatient_id(mPatInfoBean.get(mPatInfoBean.size() - 1).getPatient_id());
            data.setVisit_id(mPatInfoBean.get(mPatInfoBean.size() - 1).getVisit_id());
            data.setRecording_date(tvDate.getText().toString());
            data.setTime_point(tvDate.getText().toString() + " " + tvTime.getText().toString() + ":00");
            if (dataUnits.length == 1) { //没有单位的项目
                data.setVital_sign(dataUnits[0]);
                data.setUnits("");
            } else if (dataUnits.length == 2) {
                data.setVital_sign(dataUnits[0]);
                data.setUnits(dataUnits[1]);
            } else {
                return patVitalSignRecBeans;
            }
            patVitalSignRecBeans.add(data);
        }
        return patVitalSignRecBeans;
    }


}
