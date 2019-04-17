package com.haha.cmis.dbhelper;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.view.View;

import com.haha.cmis.Myapplication;
import com.haha.cmis.R;
import com.haha.cmis.bean.UserInfoBean;
import com.haha.cmis.constant.AppCookie;
import com.haha.cmis.constant.AppSql;
import com.haha.cmis.utils.EventBusUtils;
import com.haha.cmis.utils.FileHelper;
import com.haha.cmis.utils.MyUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;


/**
 * @author hahame  @date 2018/12/24
 */
public class SyncDataHelper {
    /**
     * his连接数据库信息
     */
    private volatile static SyncDataHelper instance;
    private static SQLiteStatement localstmt = null;
    private static SQLiteDatabase localdb = null;
    private int intAppUserRowcount = 0;
    private int intPatInfoRowcount = 0;
    private int intPatOrderRowcount = 0;
    private static final String TAG = "FAIROL-SyncDataHelper";

    /**
     * 初始化单例* @param
     */
    public static synchronized SyncDataHelper getInstance() {
        if (instance == null) {
            synchronized (SyncDataHelper.class) {
                if (instance == null) {
                    instance = new SyncDataHelper();
                }
            }
        }
        return instance;
    }


    /**
     * 获取用户信息
     */
    public Observable<Boolean> getUserInfo() {
        return new Observable<Boolean>() {
            @Override
            protected void subscribeActual(Observer<? super Boolean> observer) {
                try {
                    intAppUserRowcount = 0;
                    /**打开本地数据库*/
                    EventBusUtils.postSticky("INFO", "正在同步用户信息，请稍等... ...");
                    LocalDbHelper localDbHelper = LocalDbHelper.getInstance(Myapplication.getInstance());
                    localdb = localDbHelper.getWritableDatabase();
                    localdb.beginTransaction();
                    localstmt = localdb.compileStatement(AppSql.sqlInsertAppuser);
                    Log.d(TAG, "getUsersInfo: " + AppCookie.getDeptcode());
                    ResultSet hisResultSet = HisdbManager.getInstance().executePLSQL("pck_hcis.getUsersInfo(?,?)", AppCookie.getDeptcode());
                    while (hisResultSet.next()) {
                        localstmt.bindString(1, new String(hisResultSet.getString(UserInfoBean.COLUMN_NAME_USERCODE).getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(2, new String(hisResultSet.getString(UserInfoBean.COLUMN_NAME_USERID).getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(3, new String(hisResultSet.getString(UserInfoBean.COLUMN_NAME_USERNAME).getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(4, new String(hisResultSet.getString(UserInfoBean.COLUMN_NAME_DEPTCODE).getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(5, new String(hisResultSet.getString(UserInfoBean.COLUMN_NAME_DEPTNAME).getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(6, new String(hisResultSet.getString(UserInfoBean.COLUMN_NAME_USERROLE).getBytes("iso-8859-1"), "gbk"));
                        localstmt.execute();
                        localstmt.clearBindings();
                        intAppUserRowcount++;
                    }
                    Log.d(TAG, "subscribe: " + intAppUserRowcount);
                    localdb.setTransactionSuccessful();
                    hisResultSet.close();
                    hisResultSet = null;
                    observer.onNext(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    observer.onError(new Throwable("同步数据出错\n错误信息:SQLException\n" + ex.getMessage()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    observer.onError(new Throwable("同步数据出错\n错误信息:Exception\n" + ex.getMessage()));
                } finally {
                    localdb.endTransaction();
                    observer.onComplete();
                    EventBusUtils.postSticky("INFO", "同步用户信息完成");
                }
            }
        };
    }

    /**
     * 获取病人基本信息
     */
    public Observable<Boolean> getPatsInfo() {
        return new Observable<Boolean>() {
            @Override
            protected void subscribeActual(Observer<? super Boolean> observer) {
                try {
                    intPatInfoRowcount = 0;
                    /**获取病人信息写入本地表*/
                    /**打开本地数据库*/
                    EventBusUtils.postSticky("INFO", "正在同步病人信息，请稍等... ...");
                    intPatInfoRowcount = 0;
                    LocalDbHelper localDbHelper = LocalDbHelper.getInstance(Myapplication.getInstance());
                    localdb = localDbHelper.getWritableDatabase();
                    localdb.beginTransaction();
                    localdb.execSQL(AppSql.sqlDeletePatInfo);
                    localdb.setTransactionSuccessful();
                    localdb.endTransaction();
                    localdb = localDbHelper.getWritableDatabase();
                    localdb.beginTransaction();
                    localstmt = localdb.compileStatement(AppSql.sqlInsertPatInfo);
                    Log.d(TAG, "getPatsInfo: " + AppCookie.getDeptcode());
                    ResultSet hisResultSet = HisdbManager.getInstance().executePLSQL("pck_hcis.getPatsInfo(?,?)", AppCookie.getDeptcode());
                    while (hisResultSet.next()) {
                        localstmt.clearBindings();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                        localstmt.bindString(1, new String(hisResultSet.getString("inp_no").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(2, new String(hisResultSet.getString("patient_id").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindLong(3, hisResultSet.getInt("visit_id"));
                        localstmt.bindString(4, new String(hisResultSet.getString("name").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(5, new String(hisResultSet.getString("sex").getBytes("iso-8859-1"), "gbk"));

                        if (!MyUtils.isObjectNull(hisResultSet.getTimestamp("birth_date"))) {
                            localstmt.bindString(6, sdf.format(hisResultSet.getTimestamp("birth_date")));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("charge_type"))) {
                            localstmt.bindString(7, new String(hisResultSet.getString("charge_type").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("identity"))) {
                            localstmt.bindString(8, new String(hisResultSet.getString("identity").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("diagnosis"))) {
                            localstmt.bindString(9, new String(hisResultSet.getString("diagnosis").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getTimestamp("admission_date_time"))) {
                            localstmt.bindString(10, sdf.format(hisResultSet.getTimestamp("admission_date_time")));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getTimestamp("operating_date"))) {
                            localstmt.bindString(11, sdf.format(hisResultSet.getTimestamp("operating_date")));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("bed_approved_type"))) {
                            localstmt.bindString(12, hisResultSet.getString("bed_approved_type"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("room_no"))) {
                            localstmt.bindString(13, hisResultSet.getString("room_no"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("bed_no"))) {
                            localstmt.bindString(14, hisResultSet.getString("bed_no"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("body_weight"))) {
                            localstmt.bindString(15, hisResultSet.getString("body_weight"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("body_height"))) {
                            localstmt.bindString(16, hisResultSet.getString("body_height"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("blood_type"))) {
                            localstmt.bindString(17, hisResultSet.getString("blood_type"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("dept_code"))) {
                            localstmt.bindString(18, new String(hisResultSet.getString("dept_code").getBytes("iso-8859-1"), "gbk"));
                        }
                        localstmt.bindString(19, new String(hisResultSet.getString("dept_name").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(20, new String(hisResultSet.getString("ward_code").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(21, new String(hisResultSet.getString("ward_name").getBytes("iso-8859-1"), "gbk"));
                        if (!MyUtils.isObjectNull(hisResultSet.getString("patient_class"))) {
                            localstmt.bindString(22, new String(hisResultSet.getString("patient_class").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("patient_cond"))) {
                            localstmt.bindString(23, new String(hisResultSet.getString("patient_cond").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("doctor_name"))) {
                            localstmt.bindString(24, new String(hisResultSet.getString("doctor_name").getBytes("iso-8859-1"), "gbk"));
                        }
                        //头像处理
                        byte[] bytes = hisResultSet.getBytes("patient_image");
                        if (bytes != null) {
                            String filename = AppCookie.getPatImgPath() + hisResultSet.getString("inp_no");
                            Boolean ibFile = FileHelper.createFile(bytes, AppCookie.getPatImgPath(), hisResultSet.getString("inp_no"), ".bmp");
                            if (ibFile) {
                                localstmt.bindString(25, filename + ".bmp");
                            }
                            bytes = null;
                        }
                        /**过敏药物*/
                        if (!MyUtils.isObjectNull(hisResultSet.getString("alergy_drugs"))) {
                            localstmt.bindString(26, new String(hisResultSet.getString("alergy_drugs").getBytes("iso-8859-1"), "gbk"));
                        }
                        /**预出院日期*/
                        if (!MyUtils.isObjectNull(hisResultSet.getString("predischarge_date"))) {
                            localstmt.bindString(27, new String(hisResultSet.getString("predischarge_date").getBytes("iso-8859-1"), "gbk"));
                        }


                        if (!MyUtils.isObjectNull(hisResultSet.getString("nation"))) {
                            localstmt.bindString(28, new String(hisResultSet.getString("nation").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("mailing_address"))) {
                            localstmt.bindString(29, new String(hisResultSet.getString("mailing_address").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("next_of_kin"))) {
                            localstmt.bindString(30, new String(hisResultSet.getString("next_of_kin").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("next_of_kin_phone"))) {
                            localstmt.bindString(31, new String(hisResultSet.getString("next_of_kin_phone").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("phone_number_home"))) {
                            localstmt.bindString(32, new String(hisResultSet.getString("phone_number_home").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("phone_number_business"))) {
                            localstmt.bindString(33, new String(hisResultSet.getString("phone_number_business").getBytes("iso-8859-1"), "gbk"));
                        }
                        localstmt.execute();
                        intPatInfoRowcount++;
                    }
                    Log.d(TAG, "subscribeActual: " + intPatInfoRowcount);
                    localdb.setTransactionSuccessful();
                    hisResultSet.close();
                    hisResultSet = null;
                    observer.onNext(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    observer.onError(new Throwable("同步数据出错\n错误信息:SQLException " + ex.getMessage()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    observer.onError(new Throwable("同步数据出错\n错误信息:Exception " + ex.getMessage()));
                } finally {
                    EventBusUtils.postSticky("INFO", "同步病人信息完成");
                    localdb.endTransaction();
                    observer.onComplete();
                }
            }
        };
    }

    /**
     * 获取病人医嘱
     */
    public Observable<Boolean> getPatsOrder() {
        return new Observable<Boolean>() {
            @Override
            protected void subscribeActual(Observer<? super Boolean> observer) {
                try {
                    intPatOrderRowcount = 0;
                    EventBusUtils.postSticky("INFO", "正在同步病人医嘱信息，请稍等... ...");
                    /**获取病人信息写入本地表  打开本地数据库*/
                    intPatInfoRowcount = 0;
                    LocalDbHelper localDbHelper = LocalDbHelper.getInstance(Myapplication.getInstance());
                    /**打开本地数据库*/
                    localdb = localDbHelper.getWritableDatabase();
                    localdb.beginTransaction();
                    localdb.execSQL(AppSql.sqlDeletePatOrder);
                    localdb.setTransactionSuccessful();
                    localdb.endTransaction();
                    localdb = localDbHelper.getWritableDatabase();
                    localdb.beginTransaction();
                    localstmt = localdb.compileStatement(AppSql.sqlInsertPatOrder);
                    ResultSet hisResultSet = HisdbManager.getInstance().executePLSQL("pck_hcis.getPatOrders(?,?)", MyUtils.getHisXmlPara(AppCookie.getDeptcode(), "WARDCODE"));
                    while (hisResultSet.next()) {
                        localstmt.clearBindings();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                        localstmt.bindString(1, new String(hisResultSet.getString("index_id").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(2, new String(hisResultSet.getString("patient_id").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindLong(3, hisResultSet.getInt("visit_id"));
                        localstmt.bindString(4, new String(hisResultSet.getString("ordering_dept").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindLong(5, hisResultSet.getInt("order_no"));
                        localstmt.bindLong(6, hisResultSet.getInt("repeat_indicator"));
                        if (!MyUtils.isObjectNull(hisResultSet.getTimestamp("start_date_time"))) {
                            localstmt.bindString(7, sdf.format(hisResultSet.getTimestamp("start_date_time")));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getTimestamp("stop_date_time"))) {
                            localstmt.bindString(8, sdf.format(hisResultSet.getTimestamp("stop_date_time")));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("freq_detail"))) {
                            localstmt.bindString(9, new String(hisResultSet.getString("freq_detail").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("default_schedule"))) {
                            localstmt.bindString(10, new String(hisResultSet.getString("default_schedule").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("new_default_schedule"))) {
                            localstmt.bindString(11, new String(hisResultSet.getString("new_default_schedule").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getTimestamp("plan_exec_datetime"))) {
                            localstmt.bindString(12, sdf.format(hisResultSet.getTimestamp("plan_exec_datetime")));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("lab_exam_class"))) {
                            localstmt.bindString(13, new String(hisResultSet.getString("lab_exam_class").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getInt("IS_EMER"))) {
                            localstmt.bindLong(14, hisResultSet.getInt("IS_EMER"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("ajust_memo"))) {
                            localstmt.bindString(15, new String(hisResultSet.getString("ajust_memo").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("order_class"))) {
                            localstmt.bindString(16, new String(hisResultSet.getString("order_class").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("order_text"))) {
                            localstmt.bindString(17, new String(hisResultSet.getString("order_text").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("administration"))) {
                            localstmt.bindString(18, new String(hisResultSet.getString("administration").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("frequency"))) {
                            localstmt.bindString(19, new String(hisResultSet.getString("frequency").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("freq_counter"))) {
                            localstmt.bindString(20, new String(hisResultSet.getString("freq_counter").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getInt("freq_interval"))) {
                            localstmt.bindLong(21, hisResultSet.getInt("freq_interval"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("freq_interval_unit"))) {
                            localstmt.bindString(22, new String(hisResultSet.getString("freq_interval_unit").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getInt("order_status"))) {
                            localstmt.bindLong(23, hisResultSet.getInt("order_status"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("doctor"))) {
                            localstmt.bindString(24, new String(hisResultSet.getString("doctor").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("nurse"))) {
                            localstmt.bindString(25, new String(hisResultSet.getString("nurse").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("stop_info"))) {
                            localstmt.bindString(26, new String(hisResultSet.getString("stop_info").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("relative_no"))) {
                            localstmt.bindString(27, new String(hisResultSet.getString("relative_no").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("order_type"))) {
                            localstmt.bindString(28, new String(hisResultSet.getString("order_type").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getTimestamp("exec_datetime"))) {
                            localstmt.bindString(29, sdf.format(hisResultSet.getTimestamp("exec_datetime")));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getString("exec_operator"))) {
                            localstmt.bindString(30, new String(hisResultSet.getString("exec_operator").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!MyUtils.isObjectNull(hisResultSet.getInt("sync_status"))) {
                            localstmt.bindLong(31, hisResultSet.getInt("sync_status"));
                        }
                        localstmt.execute();
                        intPatOrderRowcount++;
                    }
                    Log.d(TAG, "subscribeActual: " + intPatOrderRowcount);
                    localdb.setTransactionSuccessful();
                    hisResultSet.close();
                    observer.onNext(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    observer.onError(new Throwable("同步数据出错\n错误信息:SQLException\n" + ex.getMessage()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    observer.onError(new Throwable("同步数据出错\n错误信息:Exception\n" + ex.getMessage()));
                } finally {
                    EventBusUtils.postSticky("INFO", "同步病人医嘱完成");
                    localdb.endTransaction();
                    observer.onComplete();
                }
            }
        };
    }

    @SuppressLint("CheckResult")
    public Observable<Boolean> SyncData() {
        return new Observable<Boolean>() {
            @Override
            protected void subscribeActual(Observer<? super Boolean> observer) {
                Observable<Boolean> userinfo = SyncDataHelper.getInstance().getUserInfo();
                Observable<Boolean> patsinfo = SyncDataHelper.getInstance().getPatsInfo();
                Observable<Boolean> patsorder = SyncDataHelper.getInstance().getPatsOrder();
                Observable.zip(userinfo, patsinfo, patsorder, (Function3<Boolean, Boolean, Boolean, Object>) (aBoolean, aBoolean2, aBoolean3) -> aBoolean && aBoolean2 && aBoolean3)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o -> {
                            observer.onNext(true);
                            observer.onComplete();
                        }, throwable -> {
                            observer.onError(new Throwable(throwable.getMessage()));
                            observer.onComplete();
                        });
            }
        };
    }


}
