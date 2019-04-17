package com.haha.cmis;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.service.carrier.CarrierService;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.haha.cmis.barscanreciever.barScanHelper;
import com.haha.cmis.base.BaseActivity;
import com.haha.cmis.constant.AppCookie;
import com.haha.cmis.dbhelper.HisdbManager;
import com.haha.cmis.dbhelper.SyncDataHelper;
import com.haha.cmis.ui.main.MainActivity;
import com.haha.cmis.utils.MyUtils;
import com.haha.cmis.utils.NetworkUtil;

import java.nio.channels.NonWritableChannelException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends BaseActivity {
    private static final String TAG = "FAIROL-WelcomeActivity";
    @BindView(R.id.pb_SyncStatus)
    ProgressBar pbSyncStatus;
    @BindView(R.id.tv_Appname)
    TextView tvAppname;
    @BindView(R.id.tv_Message)
    TextView tvMessage;
    @BindView(R.id.bt_retry)
    Button btRetry;
    @BindView(R.id.tvHosptialname)
    TextView tvHosptialname;
    @BindView(R.id.tvAppVersion)
    TextView tvAppVersion;
    private final int maxRetries = 3;
    private final int retryDelayMillis = 1000;

    private int retryCount;

    @Override
    protected void initView(Bundle savedInstanceState) {
        //清空系统用户参数
        Log.d(TAG, "initView: ");
        AppCookie.setUserCode("");
        AppCookie.setUsername("");
        AppCookie.setUserid("");
        tvAppVersion.setText("软件版本:" + MyUtils.getAppVersion(WelcomeActivity.this));
        tvMessage.setText(R.string.text_initapp);
        btRetry.setVisibility(View.GONE);
        if (TextUtils.isEmpty(AppCookie.getWificheckip()) ||
                TextUtils.isEmpty(AppCookie.getWifissid()) ||
                TextUtils.isEmpty(AppCookie.getWifipassword())) {
            AppCookie.setCurrentBarType("WIFIINFO");
            tvMessage.setText("请先扫描配置二维码");
            btRetry.setVisibility(View.GONE);
            return;
        }
        btRetry.setVisibility(View.VISIBLE);
        initWifiNetwork();
    }

    @SuppressLint("CheckResult")
    private void initWifiNetwork() {
        retryCount = 0;
        btRetry.setVisibility(View.GONE);
        tvMessage.setText("正在尝试连接wifi,请稍等... ...");
        NetworkUtil.getInstance(this).wifiConnect(AppCookie.getWificheckip(), AppCookie.getWifissid(), AppCookie.getWifipassword())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .retryWhen(observable -> observable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                    if (++retryCount <= maxRetries) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMessage.setText("正在第" + String.valueOf(retryCount) + "次，尝试连接wifi,请稍等...");
                            }
                        });
                        return Observable.timer(retryDelayMillis,
                                TimeUnit.MILLISECONDS);
                    } else {
                        return Observable.error(throwable);
                    }
                }))
                .subscribe(aBoolean -> {
                    Log.d(TAG, "initWifiNetwork: " + aBoolean);
                    if (aBoolean) {
                        tvMessage.setText("连接Wifi成功,正在验证此设备的注册信息...");
                        tvMessage.setTextColor(WelcomeActivity.this.getResources().getColor(R.color.black));
                        btRetry.setVisibility(View.GONE);
                        WelcomeActivity.this.intAppRegisterInfo();
                    } else {
                        tvMessage.setTextColor(WelcomeActivity.this.getResources().getColor(R.color.red_normal));
                        tvMessage.setText("连接wifi失败，请重试!");
                        btRetry.setVisibility(View.VISIBLE);
                    }
                }, throwable -> {
                    Log.d(TAG, "initWifiNetwork: error");
                    throwable.printStackTrace();
                    tvMessage.setTextColor(getResources().getColor(R.color.red_normal));
                    tvMessage.setText(throwable.getMessage());
                    btRetry.setVisibility(View.VISIBLE);
                });
    }

    @SuppressLint("CheckResult")
    private void intAppRegisterInfo() {
        HisdbManager.getInstance().getAppRegisterInfo()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        tvMessage.setText("验证注册信息成功.");
                        syncData();
                    } else {
                        tvMessage.setText("验证注册信息失败.");
                        btRetry.setVisibility(View.VISIBLE);
                    }
                }, throwable -> tvMessage.setText(throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    private void syncData() {
        tvMessage.setText("同步数据,请稍等... ...");
        pbSyncStatus.setVisibility(View.VISIBLE);
        Observable<Boolean> syncData = new SyncDataHelper().SyncData();
        syncData.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    Log.d(TAG, "syncData: " + aBoolean);
                    pbSyncStatus.setVisibility(View.GONE);
                    AppCookie.setCurrentBarType("LOGIN");
                    tvMessage.setText(getString(R.string.msg_pleasescanbarcode));
                    onBarScanEvent("NURSE#MDAzOA==");
                }, throwable -> {
                    pbSyncStatus.setVisibility(View.GONE);
                    tvMessage.setText(throwable.getMessage());
                    btRetry.setVisibility(View.VISIBLE);
                });
    }


    @SuppressLint("CheckResult")
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

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected Boolean getIsFullScreen() {
        return true;
    }

    @Override
    protected Boolean getIsShowTitle() {
        return false;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBarScanEvent(String strBarCode) {
        super.onBarScanEvent(strBarCode);
        Log.d(TAG, "BarScanEvent: " + strBarCode);
        barScanHelper.getInstance().barScan(strBarCode)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    btRetry.setVisibility(View.GONE);
                    Log.d(TAG, "BarScanEvent: start ..." + aBoolean);
                    if (aBoolean) {
                        if (strBarCode.contains("WIFIINFO")) {
                            infoShow("info", "App设置成功，正在进行验证及数据同步操作");
                            initWifiNetwork();
                        } else if (strBarCode.contains("NURSE")) {
                            tvMessage.setText("用户验证成功");
                            gotoActivity(MainActivity.class);
                            WelcomeActivity.this.finish();
                        }
                    }
                }, throwable -> {
                    tvMessage.setText(throwable.getMessage());
                    infoShow("ERROR", "扫描失败!\n" + throwable.getMessage());
                });
    }

    @OnClick({R.id.bt_retry})
    public void onViewClicked(View v) {
        initWifiNetwork();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
