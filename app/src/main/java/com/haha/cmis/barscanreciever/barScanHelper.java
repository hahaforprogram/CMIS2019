package com.haha.cmis.barscanreciever;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.haha.cmis.Myapplication;
import com.haha.cmis.constant.AppCookie;
import com.haha.cmis.dbhelper.LocalDbDao;
import com.haha.cmis.utils.EventBusUtils;
import com.haha.cmis.utils.MessageEvent;
import com.haha.cmis.utils.MyUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author haha
 */

public class barScanHelper {
    private static barScanHelper instance;
    private static final String TAG = "FAIROL-barScanHelper";


    public static barScanHelper getInstance() {
        if (instance == null) {
            synchronized (barScanHelper.class) {
                if (instance == null) {
                    instance = new barScanHelper();
                }
            }
        }
        return instance;
    }

    @SuppressLint("CheckResult")
    public Observable<Boolean> barScan(final String strBarcode) {
        return new Observable<Boolean>() {
            @Override
            protected void subscribeActual(final Observer<? super Boolean> observer) {
                String strBarInfo[] = strBarcode.split("#");
                Log.d(TAG, "barScanProcess: begin.....");
                //判断扫描的是否为空
                if (TextUtils.isEmpty(strBarcode.trim())) {
                    observer.onError(new Throwable("扫描二维码Null异常,请检查后再试！"));
                    Log.d(TAG, "subscribeActual: 1----------");
                    observer.onComplete();
                    Log.d(TAG, "subscribeActual: 1**********");
                    return;
                }

                //如果有设置当期扫描标识
                if (!TextUtils.isEmpty(AppCookie.getCurrentBarType()) && !strBarInfo[0].equals(AppCookie.getCurrentBarType())) {
                    //扫描的二维码类型不与系统设定的相符 则提示 报错
                    if ("LOGIN".equals(AppCookie.getCurrentBarType()) && strBarInfo[0].equals("NURSE")) {
                        /**第一次登陆LOGIN只验证当前app数据库是否存在此用户*/
                        Boolean mboolean = (Boolean) LocalDbDao.queryUserLoginInfo(MyUtils.getDecodeBase64(strBarInfo[1]), AppCookie.getDeptcode());
                        if (mboolean) {
                            observer.onNext(mboolean);
                        } else {
                            observer.onError(new Throwable("此用户不存在\n" + AppCookie.getCurrentBarType()));
                        }
                    } else {
                        observer.onError(new Throwable("二维码类型错误,请重新扫描\n" + AppCookie.getCurrentBarType()));
                    }
                    Log.d(TAG, "subscribeActual: 2----------");
                    observer.onComplete();
                    Log.d(TAG, "subscribeActual: 2**********");
                    return;
                }

                /**根据二维码类型处理数据 //保存wifi链接信息 */
                if (strBarInfo[0].equals("WIFIINFO")) {
                    AppCookie.setWificheckip(strBarInfo[1]);
                    AppCookie.setWifissid(strBarInfo[2]);
                    AppCookie.setWifipassword(strBarInfo[3]);
                    observer.onNext(true);
                } else if (strBarInfo[0].equals("NURSE")) {
                    /**验证数据库中的用户是否存在*/
                    observer.onNext(true);
                }

                Log.d(TAG, "subscribeActual: 3----------");
                observer.onComplete();
                Log.d(TAG, "subscribeActual: 3**********");
                return;


            }
        };
    }


    public static boolean barScanProcess(String strBarcode) {
        String strBarInfo[];
        strBarInfo = strBarcode.split("#");
        Log.d(TAG, "barScanProcess: begin.....");
        //判断扫描的是否为空
        if (TextUtils.isEmpty(strBarcode.trim())) {
            EventBusUtils.postSticky(new MessageEvent("info", "扫描二维码Null异常,请检查后再试！"));
            return false;
        }

        Log.d(TAG, "barScanProcess: " + AppCookie.getCurrentBarType());

        //如果有设置当期扫描标识
        if (!TextUtils.isEmpty(AppCookie.getCurrentBarType())) {
            if (!strBarInfo[0].equals(AppCookie.getCurrentBarType())) {
                /**扫描的二维码类型不与系统设定的相符 则提示 报错*/
                EventBusUtils.postSticky(new MessageEvent("info", "二维码类型错误,请重新扫描\n" + AppCookie.getCurrentBarType() + "\n" + strBarcode));
                return false;
            } else if ("LOGIN".equals(AppCookie.getCurrentBarType()) && strBarInfo[0].equals("NURSE")) {
                /**第一次登陆LOGIN只验证当前app数据库是否存在此用户*/
                LocalDbDao localDbDao = new LocalDbDao();
                localDbDao.queryUserInfo(strBarInfo[1], AppCookie.getDeptcode())
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {

                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        });
            }
        }

        //根据二维码类型处理数据

        //保存wifi链接信息
        if (strBarInfo[0].equals("WIFIINFO")) {
            strBarInfo = strBarcode.split("#");
            AppCookie.setWificheckip(strBarInfo[1]);
            AppCookie.setWifissid(strBarInfo[2]);
            AppCookie.setWifipassword(strBarInfo[3]);
        } else if (strBarInfo[0].equals("NURSE")) {
            //验证数据库中的用户是否存在

        }


        return true;
    }
}
