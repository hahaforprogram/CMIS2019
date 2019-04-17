package com.haha.cmis.utils;

import android.support.annotation.NonNull;


import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * Created by hah on 2018/6/7.
 */

public class DeviceInfoHelper {

    public DeviceInfoHelper() {
    }

    @NonNull
    public static String getDeviceInfo() {
        String strDeviceMac = "";
        //获取mac地址
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {

            }
            byte[] bytes = networkInterface.getHardwareAddress();
            for (byte b : bytes) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            strDeviceMac = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return strDeviceMac;
    }
}
