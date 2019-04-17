package com.haha.cmis.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author hahame  @date 2018-11-15
 */

public class PreferenceUtil {
    private static String spKey;
    private volatile static PreferenceUtil instance;
    private static SharedPreferences mSharedPreferences;

    private PreferenceUtil(@NonNull Context context) {
        mSharedPreferences = context.getSharedPreferences(spKey, Context.MODE_PRIVATE);
    }

    /**
     * 初始化单例
     *
     * @param context
     */
    public static synchronized PreferenceUtil init(@NonNull Context context, String strSpKey) {
        spKey = strSpKey;
        if (instance == null) {
            synchronized (PreferenceUtil.class) {
                instance = new PreferenceUtil(context);
            }
        }
        return instance;
    }

    public static void set(String strKey, @NonNull Object object) {
        String type = object.getClass().getSimpleName();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if ("String".equals(type)) {
            editor.putString(strKey, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(strKey, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(strKey, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(strKey, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(strKey, (Long) object);
        }
        editor.commit();
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    @Nullable
    public static Object get(String key, @NonNull Object defValue) {
        String type = defValue.getClass().getSimpleName();
        if ("Integer".equals(type)) {
            return mSharedPreferences.getInt(key, (Integer) defValue);
        } else if ("Boolean".equals(type)) {
            return mSharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if ("String".equals(type)) {
            return mSharedPreferences.getString(key, (String) defValue);
        } else if ("Float".equals(type)) {
            return mSharedPreferences.getFloat(key, (Float) defValue);
        } else if ("Long".equals(type)) {
            return mSharedPreferences.getLong(key, (Long) defValue);
        }

        return null;
    }

    public static Integer getInteger(String key, Integer defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    public static Boolean getBoolean(String key, Boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }


    @Nullable
    public static String getString(String key) {
        String defValue = "";
        return mSharedPreferences.getString(key, "");
    }


    public static Float getFloat(String key, Float defValue) {
        return mSharedPreferences.getFloat(key, defValue);
    }


    public static Long getLong(String key, Long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

    /**
     * 清除指定数据 editor.remove("KEY");
     */
    public static void clear(String strKey) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(strKey).commit();
    }

    /**
     * 清除所有数据
     */
    public static void clearAll() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear().commit();
    }


}
