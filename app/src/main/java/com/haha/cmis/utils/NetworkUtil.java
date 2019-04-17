package com.haha.cmis.utils;

/**
 * Created by Haha on 2017/10/9.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public class NetworkUtil {
    private static NetworkUtil instance = null;
    private Context context;
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;
    private List<ScanResult> mWifiList;
    private List<WifiConfiguration> mWificonfiguration;
    private WifiManager.WifiLock mWifiLock;

    private static final String TAG = "FAIROL-NetworkUtil";


    public NetworkUtil(Context context) {
        this.context = context;
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    public static NetworkUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (NetworkUtil.class) {
                if (instance == null) {
                    instance = new NetworkUtil(context);
                }
            }
        }
        return instance;
    }


//     this.context = context;
//    mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//    mWifiInfo = mWifiManager.getConnectionInfo();
//

    public void openWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            try {
                mWifiManager.setWifiEnabled(true);
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void closeWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    public int checkState() {
        return mWifiManager.getWifiState();
    }

    public void acquireWifiLoc() {
        mWifiLock.acquire();
    }

    public void releaseWifiLock() {
        if (mWifiLock.isHeld()) {
            mWifiLock.acquire();
        }
    }

    public void createWifiLock() {
        mWifiLock = mWifiManager.createWifiLock("");
    }

    public List<WifiConfiguration> getConfigurations() {
        return mWificonfiguration;
    }

    @NonNull
    public Boolean connectConfiguration(int index) {
        mWifiManager.enableNetwork(index, true);
        mWifiManager.saveConfiguration();
        mWifiManager.reconnect();
        return true;
    }

    public void startScan() {
        closeWifi();
        openWifi();
        mWifiManager.startScan();
        mWifiList = mWifiManager.getScanResults();
        mWificonfiguration = mWifiManager.getConfiguredNetworks();
    }

    public List<ScanResult> getmWifiList() {
        return mWifiList;
    }

    @NonNull
    public StringBuilder lookUpScan() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mWifiList.size(); i++) {
            stringBuilder.append("Index_" + String.valueOf(i + 1) + ":");
            stringBuilder.append(mWifiList.get(i).toString());
            stringBuilder.append("/n");
        }
        return stringBuilder;
    }

    @NonNull
    public String getMacAddress() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
    }

    @NonNull
    public String getSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getSSID();
    }

    public int getIpAddress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    public int getNetworkId() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
    }

    @NonNull
    public String getWifiInfo() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
    }

    public boolean addNetWork(WifiConfiguration wifiConfiguration) {
        int wcgID = mWifiManager.addNetwork(wifiConfiguration);
        Log.e("haha", wcgID + "true");
        mWifiManager.enableNetwork(wcgID, true);
        mWifiManager.saveConfiguration();
        mWifiManager.reconnect();
        return true;
    }

    public void disconnectWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }

    @NonNull
    public WifiConfiguration createWifiInfo(String SSID, String Password, int Type) {
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.allowedAuthAlgorithms.clear();
        configuration.allowedGroupCiphers.clear();
        configuration.allowedKeyManagement.clear();
        configuration.allowedPairwiseCiphers.clear();
        configuration.allowedProtocols.clear();
        configuration.SSID = "\"" + SSID + "\"";

        WifiConfiguration tempConfig = this.isExists(SSID);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }
        switch (Type) {
            case 1:
                configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                break;
            case 2:
                configuration.hiddenSSID = false;
                configuration.wepKeys[0] = "\"" + Password + "\"";
                configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                break;
            case 3:
                configuration.preSharedKey = "\"" + Password + "\"";
                configuration.hiddenSSID = false;
                // configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                configuration.status = WifiConfiguration.Status.ENABLED;
                break;
        }
        return configuration;
    }

    private WifiConfiguration isExists(String SSID) {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig :
                existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }

    public boolean isWifiConnect(@Nullable Context context) {
        if (context != null) {
            ConnectivityManager connCm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connCm != null) {
                NetworkInfo info = connCm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (info.isAvailable() && info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean pingIpAddress(String ipAddress) {
        try {
            Process process = Runtime.getRuntime().exec("/system/bin/ping -c 1 -w 1 " + ipAddress);
            int status = process.waitFor();
            return status == 0;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Observable<Boolean> wifiConnect(final String strIP, final String strSSID, final String strPASSWORD) {
        return Observable.create(e -> {
            Log.d(TAG, "wifiConnect: " + strIP + strSSID + strPASSWORD);
            if (isWifiConnect(context) && pingIpAddress(strIP)) {
                e.onNext(true);
            } else {
                startScan();
                Thread.sleep(2000);
                addNetWork(createWifiInfo(strSSID, strPASSWORD, 3));
                Thread.sleep(1000);
                if (isWifiConnect(context)) {
                    if (pingIpAddress(strIP)) {
                        e.onNext(true);
                    } else {
                        e.onError(new Throwable("连接医院病区WIFI成功，但访问网络失败"));
                    }
                } else {
                    e.onError(new Throwable("连接医院病区WIFI失败，请确认是否在信号覆盖区域"));
                }
            }

            e.onComplete();
        });
    }


}