package com.haha.cmis.base;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.haha.cmis.R;
import com.haha.cmis.barscanreciever.barScanReciever;

import java.lang.reflect.Method;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public abstract class BaseActivity extends AppCompatActivity implements barScanReciever.BarScanInteraction {
    protected final String TAG = "FAIROL-" + this.getClass().getSimpleName();
    private static final String lachesisBarScancode = "lachesis_barcode_value_notice_broadcast";

    private barScanReciever mBarScanReciever = null;
    protected Context context;

    /***设置布局名称R.layout.xxxxx*/
    protected abstract int getContentViewLayoutID();

    /***设置是否全屏*/
    protected abstract Boolean getIsFullScreen();

    /***设置是否需要标题*/
    protected abstract Boolean getIsShowTitle();

    /*** 初始化界面*/
    protected abstract void initView(Bundle savedInstanceState);

    /*** 初始化数据*/
    protected abstract void initData();

    /*** 绑定事件*/
    protected abstract void initEvent();

    /***是否扫描广播*/
    protected abstract Boolean getIsScanBar();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        context = this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setShowTitle(getIsShowTitle());
        initSystemBarTint();
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            setFullScreen(getIsFullScreen());
            initView(savedInstanceState);
        }
        initEvent();
        initData();
    }


    /*** 窗口全屏*/
    private void setFullScreen(Boolean fullScreen) {
        if (fullScreen) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                requestWindowFeature(Window.FEATURE_NO_TITLE);
            } else {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().hide();
                }
            }

        }
    }

    /*** 窗口标题*/
    private void setShowTitle(Boolean showTitle) {
        if (!showTitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    /**
     * 子类可以重写决定是否使用透明状态栏
     */
    protected boolean translucentStatusBar() {
        return true;
    }

    /**
     * 子类可以重写改变状态栏颜色
     */
    protected int setStatusBarColor() {
        return R.color.colorPrimary;
    }

    /*** Toasty 提示*/
    public void infoShow(String strType, String strMsg) {
        switch (strType.toUpperCase()) {
            case "INFO":
                Toasty.info(this, strMsg, Toast.LENGTH_SHORT).show();
                break;
            case "ERROR":
                Toasty.error(this, strMsg, Toast.LENGTH_SHORT).show();
                break;
            case "OK":
                Toasty.success(this, strMsg, Toast.LENGTH_SHORT).show();
                break;
            case "warning":
                Toasty.warning(this, strMsg, Toast.LENGTH_SHORT).show();
                break;
            default:
                Toasty.info(this, strMsg, Toast.LENGTH_SHORT).show();
                break;
        }
    }
    /*** dialog提示框*/

    /**
     * 沉浸状态栏（4.4以上系统有效） 设置状态栏颜色
     */
    protected void initSystemBarTint() {
        Window window = getWindow();
        if (translucentStatusBar()) {
            /** 设置状态栏全透明*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        /** 沉浸式状态栏*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /**5.0以上使用原生方法*/
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(setStatusBarColor());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            /**4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式*/
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(setStatusBarColor());
        }
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * [携带数据的页面跳转] @param clz @param bundle
     */
    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity) {
        gotoActivity(clz, isCloseCurrentActivity, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (isCloseCurrentActivity) {
            this.finish();
        }
    }


    /**
     * 注册广播 扫描
     */
    private void registerBarScanListener() {
        if (getIsScanBar()) {
            if (mBarScanReciever == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(lachesisBarScancode);
                mBarScanReciever = new barScanReciever();
                registerReceiver(mBarScanReciever, intentFilter);
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
            try {
                unregisterReceiver(mBarScanReciever);
                mBarScanReciever = null;
                Log.d(TAG, "unregisterBarScanBroadcast: stop");
            } catch (Exception ex) {
                throw ex;
            }
        }
    }

    @Override
    public void onBarScanEvent(String strBarCode) {
        Log.d(TAG, "onBarScanEvent: " + strBarCode);
    }

    public void hideSoftInputMethod(@android.support.annotation.NonNull EditText ed) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
    protected void onResume() {
        super.onResume();
        registerBarScanListener();
        Log.d(TAG, "onResume: registerBarScanListener start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterBarScanListener();
        Log.d(TAG, "onResume: registerBarScanListener stop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterBarScanListener();
        Log.d(TAG, "onPause: unregisterBarScanListener stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBarScanListener();
        Log.d(TAG, "onDestroy: unregisterBarScanListener();");
    }


}
