//<editor-fold desc="Description">
//region Description
package com.haha.cmis.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.haha.cmis.constant.AppCookie;
import com.haha.cmis.constant.AppSql;


/**
 * Created by Haha on 2017/10/9.
 */

public class LocalDbHelper extends SQLiteOpenHelper {
    private volatile static LocalDbHelper instance;

    private static final String TAG = "FAIROL-LocalDbHelper";

    private LocalDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private LocalDbHelper(Context context) {
        this(context, AppCookie.getDataBabasePath() + AppCookie.getDatabaseName(), null, AppSql.appDbversion);
    }

    /**
     * 双重验证的单例方法，通过getDBHelper得到helper对象来得到数据库，保证helper类是单例的
     */
    public static LocalDbHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (LocalDbHelper.class) {
                if (instance == null) {
                    instance = new LocalDbHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase sqLiteDatabase) {
        /**创建用户信息表*/
        sqLiteDatabase.execSQL(AppSql.Sql_appusers);
        /***创建病人信息表*/
        sqLiteDatabase.execSQL(AppSql.Sql_patsinfo);
        /***创建病人医嘱信息表*/
        sqLiteDatabase.execSQL(AppSql.Sql_patsorders);
        /***创建病人体征信息表*/
        sqLiteDatabase.execSQL(AppSql.Sql_patvitalsignrec);
        /**创建医嘱索引*/
        sqLiteDatabase.execSQL(AppSql.sql_create_patsorders_index);
        Log.d(TAG, "onCreate: DATABASE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


} 

