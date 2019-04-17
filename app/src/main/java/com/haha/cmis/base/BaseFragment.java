package com.haha.cmis.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.haha.cmis.Myapplication;
import com.haha.cmis.barscanreciever.barScanReciever;

import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements barScanReciever.BarScanInteraction {
    protected final String TAG = "FAIROL-" + this.getClass().getSimpleName();

    private static final String lachesisBarScancode = "lachesis_barcode_value_notice_broadcast";
    private barScanReciever mBarScanReciever = null;
    private boolean isHasViewVisible = false;
    Unbinder unbinder;
    protected Context context;

    /***设置布局名称R.layout.xxxxx*/
    protected abstract int getContentViewLayoutID();

    /*** 初始化界面 @savedInstanceState*/
    protected abstract void initView(Bundle savedInstanceState);

    /*** 初始化数据*/
    protected abstract void initData();

    /*** 绑定事件*/
    protected abstract void initEvent();

    /***是否扫描广播*/
    protected abstract Boolean getIsScanBar();


    /**
     * 注册广播 扫描
     */
    private void registerBarScanListener() {
        if (getIsScanBar()) {
            if (mBarScanReciever == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(lachesisBarScancode);
                mBarScanReciever = new barScanReciever();
                Myapplication.getInstance().registerReceiver(mBarScanReciever, intentFilter);
                mBarScanReciever.BarScanInteractionListener(this);
                Log.d(TAG, "registerBarScanBroadcast: start");
            }
        }
    }

    /**
     * 销毁广播 扫描
     */
    private void unregisterBarScanListener() {
        if (mBarScanReciever != null) {
            Log.d(TAG, "unregisterBarScanListener: " + mBarScanReciever.toString());
            try {
                Myapplication.getInstance().unregisterReceiver(mBarScanReciever);
                mBarScanReciever = null;
                Log.d(TAG, "unregisterBarScanBroadcast: stop");
            } catch (Exception ex) {
                throw ex;
            }
        }
    }


    public void hideSoftInputMethod(@android.support.annotation.NonNull EditText ed) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        String methodName = null;
        int currentVersion = Build.VERSION.SDK_INT;
        if (currentVersion >= 16) {          // 4.2
            methodName = "setShowSoftInputOnFocus";  //
        } else if (currentVersion >= 14) {            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            //最低级最不济的方式，这个方式会把光标给屏蔽
            ed.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public Context getContext() {
        if (context == null) {
            context = getActivity();
        }
        return context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            return inflater.inflate(getContentViewLayoutID(), container, false);
        }
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initView(savedInstanceState);
        initData();
        initEvent();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
        this.context = (Activity) context;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }


    @Override
    public void onStop() {
        super.onStop();
        unregisterBarScanListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /*** Toasty 提示*/
    public void infoShow(String strType, String strMsg) {
        switch (strType.toUpperCase()) {
            case "INFO":
                Toasty.info(context, strMsg, Toast.LENGTH_SHORT).show();
                break;
            case "ERROR":
                Toasty.error(context, strMsg, Toast.LENGTH_SHORT).show();
                break;
            case "OK":
                Toasty.success(context, strMsg, Toast.LENGTH_SHORT).show();
                break;
            case "warning":
                Toasty.warning(context, strMsg, Toast.LENGTH_SHORT).show();
                break;
            default:
                Toasty.info(context, strMsg, Toast.LENGTH_SHORT).show();
                break;
        }
    }
    /*** dialog提示框*/


    /**
     * [携带数据的页面跳转] @param clz @param bundle
     */
    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, null);
    }

    public void gotoActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);

    }

    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "xxxxsetUserVisibleHint: 1" + isVisibleToUser);
        if (isVisibleToUser) {
            registerBarScanListener();
        } else {
            unregisterBarScanListener();
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "xxxxonHiddenChanged: 1" + hidden);

    }

    @Override
    public void onBarScanEvent(String strBarCode) {
        Log.d(TAG, "onBarScanEvent: " + strBarCode);
    }
}
