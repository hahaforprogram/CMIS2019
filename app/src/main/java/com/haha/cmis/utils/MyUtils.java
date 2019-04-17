package com.haha.cmis.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;


import com.haha.cmis.Myapplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.NetworkInterface;
import java.net.SocketException;

import java.util.List;
import java.util.Map;

/**
 * Created by Haha on 2017/10/13.
 */

public class MyUtils {
    /**
     * 获取设备信息
     */
    @NonNull
    public static Map<String, String> getDeviceInfo() {
        String strMODELinfo = "";
        String strSERIALinfo = "";
        String strDeviceMac = "";
        Map<String, String> deviceinfo = null;
        /**获取手机类型字典 */
        Field[] fields = Build.class.getDeclaredFields();
        //遍历字段名数组
        for (Field field : fields) {
            try {
                /** 将字段都设为public可获取 */
                field.setAccessible(true);
                /**filed.get(null)得到的即是设备信息 */
                Log.d("CrashHandler", field.getName() + " : " + field.get(null));
                switch (field.getName()) {
                    case "MODEL":
                        strMODELinfo = "设备型号:" + field.get(null).toString();
                        break;
                    case "SERIAL":
                        strSERIALinfo = "设备序列号:" + field.get(null).toString();
                        break;
                    default:
                        strMODELinfo = "设备型号:" + field.get(null).toString();
                        strSERIALinfo = "设备序列号:" + field.get(null).toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        deviceinfo.put("MODE", strMODELinfo);
        deviceinfo.put("SERIAL", strSERIALinfo);

        /***获取mac地址*/
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {

            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            strDeviceMac = buf.toString();
        } catch (SocketException e) {
            strDeviceMac = "";
            e.printStackTrace();
        }
        deviceinfo.put("MAC", strDeviceMac);
        return deviceinfo;
    }

    /**
     * base64 解码
     */
    @NonNull
    public static String getDecodeBase64(String str) {
        byte[] strByte = Base64.decode(str, Base64.DEFAULT);
        return new String(strByte);
    }

    /**
     * 字符 转 xml <?>value </?>
     */
    public static String getHisXmlPara(String strVal, String strFormat) {
        StringBuffer sb = new StringBuffer();
        sb.append("<");
        sb.append(strFormat);
        sb.append(">");
        sb.append(strVal);
        sb.append("</");
        sb.append(strFormat);
        sb.append(">");
        return sb.toString();
    }

    /**
     * 设置pda 震动
     */
    public static void errVIBRATE(long longSec) {
        Vibrator vibrator = (Vibrator) Myapplication.getInstance().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(longSec * 1000);
        }
    }

    public static boolean isObjectNull(@Nullable Object o) {
        return o == null;
    }

    /**
     * 字符转小数
     */
    public static float stringToFloat(@NonNull String strValue) {
        int intIndexof = 0;
        intIndexof = strValue.indexOf(".");
        if (intIndexof > 0 && intIndexof == strValue.length()) {  //存在小数点
            strValue = strValue + "0";
        }
        try {
            return Float.parseFloat(strValue);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    /**
     * list 深拷贝
     */
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }

    /**
     * sql语句 生成 ?
     */
    public static String sqlMakePlaceholders(int len) {
        if (len < 1) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }


    /***获取当前app 版本号*/
    public static String getAppVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


}
