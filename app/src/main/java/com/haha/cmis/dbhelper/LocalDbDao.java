package com.haha.cmis.dbhelper;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.telephony.RadioAccessSpecifier;
import android.text.TextUtils;
import android.util.Log;


import com.haha.cmis.Myapplication;
import com.haha.cmis.bean.PatInfoBean;
import com.haha.cmis.bean.PatOrderBean;
import com.haha.cmis.bean.PatVitalSignRecBean;
import com.haha.cmis.constant.AppConfig;
import com.haha.cmis.constant.AppCookie;
import com.haha.cmis.constant.AppSql;
import com.haha.cmis.utils.MyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * @author hahame  @date 2018/11/28
 */
public class LocalDbDao {
    private static LocalDbDao instance;
    private static final String TAG = "FAIROL-LocalDbDao";

    public LocalDbDao() {
    }

    /**
     * 双重验证的单例方法，通过getDBHelper得到helper对象来得到数据库，保证helper类是单例的
     */
    public static LocalDbDao getInstance() {
        if (instance == null) {
            synchronized (LocalDbDao.class) {
                if (instance == null) {
                    instance = new LocalDbDao();
                }
            }
        }
        return instance;
    }

    /**
     * 查询用户信息
     */
    public static Boolean queryUserLoginInfo(final String strUserCode, final String strUserDeptcode) {
        SQLiteDatabase db = LocalDbHelper.getInstance(Myapplication.getInstance()).getReadableDatabase();
        try {
            Cursor rs = db.rawQuery(AppSql.sqlQueryAppuser, new String[]{strUserCode, strUserDeptcode});
            if (rs.moveToFirst()) {
                AppCookie.setUserCode(rs.getString(0));
                AppCookie.setUserid(rs.getString(1));
                AppCookie.setUsername(rs.getString(2));
                Log.d(TAG, "queryUserLoginInfo: " + rs.getString(2));
                rs.close();
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    /**
     * 查询用户信息
     */
    public Observable<Boolean> queryUserInfo(final String strUserCode, final String strUserDeptcode) {
        return Observable.create(e -> {
            SQLiteDatabase db = LocalDbHelper.getInstance(Myapplication.getInstance()).getReadableDatabase();
            db.beginTransaction();
            try {
                Cursor rs = db.rawQuery(AppSql.sqlQueryAppuser, new String[]{strUserCode, strUserDeptcode});
                if (rs.moveToFirst()) {
                    AppCookie.setUserCode(rs.getString(0));
                    AppCookie.setUserid(rs.getString(1));
                    AppCookie.setUsername(rs.getString(2));
                    rs.close();
                    e.onNext(true);
                } else {
                    e.onNext(false);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                e.onError(new Throwable(ex.getMessage()));
            } finally {
                e.onComplete();
            }
        });
    }


    /**
     * 查询病人信息
     */
    public Observable<List<PatInfoBean>> queryPatsInfo(final String strWardcode, final String strBedno, final String strPatid, final String strVisitid) {
        return Observable.create((ObservableOnSubscribe<List<PatInfoBean>>) e -> {
            Log.d(TAG, "queryPatsInfo: " + strWardcode + strBedno + strPatid + strVisitid);
            List<PatInfoBean> patinfo = new ArrayList<>();
            String strSql = null;
            SQLiteDatabase db = LocalDbHelper.getInstance(Myapplication.getInstance()).getReadableDatabase();
            Log.d(TAG, "queryPatsInfo: " + strWardcode + strBedno + strPatid + strVisitid);
            try {
                Cursor rs = null;
                if (!TextUtils.isEmpty(strPatid) && !TextUtils.isEmpty(strVisitid)) {  /**按具体病人id号及住院次数查询*/
                    strSql = AppSql.sqlQueryPatInfoByPatient;
                    rs = db.rawQuery(strSql, new String[]{strPatid, strVisitid});
                } else if (!TextUtils.isEmpty(strWardcode) && !TextUtils.isEmpty(strBedno)) { /**按病区代码及床号查询*/
                    strSql = AppSql.sqlQueryPatInfoByBedno;
                    rs = db.rawQuery(strSql, new String[]{strWardcode, strBedno});
                } else if (!TextUtils.isEmpty(strWardcode) && TextUtils.isEmpty(strPatid) && TextUtils.isEmpty(strVisitid)) { /**按病区代码查询*/
                    Log.d(TAG, "queryPatsInfo: " + AppSql.sqlQueryPatInfoByWard + strWardcode);
                    strSql = AppSql.sqlQueryPatInfoByWard;
                    rs = db.rawQuery(strSql, new String[]{strWardcode});
                    Log.d(TAG, "queryPatsInfo: " + AppSql.sqlQueryPatInfoByWard + strWardcode);
                }
                Log.d(TAG, "queryPatsInfo: " + strSql + strWardcode + strBedno + strPatid + strVisitid);

                if (rs != null && rs.getCount() > 0) {
                    while (rs.moveToNext()) {
                        PatInfoBean pat = new PatInfoBean();
                        pat.setInp_no(rs.getString(0));
                        pat.setPatient_id(rs.getString(1));
                        pat.setVisit_id(rs.getInt(2));
                        pat.setName(rs.getString(3));
                        pat.setSex(rs.getString(4));
                        pat.setBirth_date(rs.getString(5));
                        pat.setCharge_type(rs.getString(6));
                        pat.setIdentity(rs.getString(7));
                        pat.setDiagnosis(rs.getString(8));
                        pat.setAdmission_date_time(rs.getString(9));
                        pat.setOperating_date(rs.getString(10));
                        pat.setBed_approved_type(rs.getString(11));
                        pat.setRoom_no(rs.getString(12));
                        pat.setBed_no(rs.getString(13));

                        pat.setBody_weight(rs.getString(14));
                        pat.setBody_height(rs.getString(15));
                        pat.setBlood_type(rs.getString(16));


                        pat.setDept_code(rs.getString(17));
                        pat.setDept_name(rs.getString(18));
                        pat.setWard_code(rs.getString(19));
                        pat.setWard_name(rs.getString(20));

                        pat.setPatient_class(rs.getString(21));
                        pat.setPatient_cond(rs.getString(22));
                        pat.setDoctor_name(rs.getString(23));
                        pat.setPatient_image(rs.getString(24));
                        pat.setAlergy_drugs(rs.getString(25));
                        pat.setPredischarge_date(rs.getString(26));


                        pat.setNation(rs.getString(27));
                        pat.setMailing_address(rs.getString(28));
                        pat.setNext_of_kin(rs.getString(29));
                        pat.setNext_of_kin_phone(rs.getString(30));
                        pat.setPhone_number_home(rs.getString(31));
                        pat.setPhone_number_business(rs.getString(32));
                        patinfo.add(pat);
                        Log.d(TAG, "queryPatsInfo: --------------------");
                    }
                }
                rs.close();
                e.onNext(patinfo);
            } catch (SQLException ex) {
                ex.printStackTrace();
                e.onError(new Throwable("查询床位病人信息失败:" + ex.getMessage()));
            } finally {
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());
    }


    /**
     * 查询病人医嘱
     */
    public Observable<List<PatOrderBean>> queryPatsOrder(final String strSql, final String[] strParms) {
        return Observable.create(e -> {
            List<PatOrderBean> patorder = new ArrayList<>();
            try {
                SQLiteDatabase db = LocalDbHelper.getInstance(Myapplication.getInstance()).getReadableDatabase();
                Log.d(TAG, "queryPatsOrder: " + strSql);
                Cursor rs = db.rawQuery(strSql, strParms);
                Log.d(TAG, "queryPatsOrder: " + rs.getCount() + db.isOpen() + strParms[0] + strParms[1]);
                if (rs != null && rs.getCount() > 0) {
                    while (rs.moveToNext()) {
                        PatOrderBean orders = new PatOrderBean();
                        orders.setINDEX_ID(rs.getString(0));
                        orders.setPATIENT_ID(rs.getString(1));
                        orders.setVISIT_ID(rs.getInt(2));
                        orders.setORDER_NO(rs.getInt(3));
                        orders.setORDERING_DEPT(rs.getString(4));
                        orders.setREPEAT_INDICATOR(rs.getInt(5));
                        orders.setSTART_DATE_TIME(rs.getString(6));
                        orders.setSTOP_DATE_TIME(rs.getString(7));
                        orders.setFREQ_DETAIL(rs.getString(8));
                        orders.setDEFAULT_SCHEDULE(rs.getString(9));
                        orders.setNEW_DEFAULT_SCHEDULE(rs.getString(10));
                        orders.setPLAN_EXEC_DATETIME(rs.getString(11));
                        orders.setLAB_EXAM_CLASS(rs.getString(12));
                        orders.setIS_EMER(rs.getInt(13));
                        orders.setAJUST_MEMO(rs.getString(14));
                        orders.setORDER_CLASS(rs.getString(15));
                        orders.setORDER_TEXT(rs.getString(16));
                        orders.setADMINISTRATION(rs.getString(17));
                        orders.setFREQUENCY(rs.getString(18));
                        orders.setFREQ_COUNTER(rs.getString(19));
                        orders.setFREQ_INTERVAL(rs.getInt(20));
                        orders.setFREQ_INTERVAL_UNIT(rs.getString(21));
                        orders.setORDER_STATUS(rs.getInt(22));
                        orders.setDOCTOR(rs.getString(23));
                        orders.setNURSE(rs.getString(24));
                        orders.setSTOP_INFO(rs.getString(25));
                        orders.setRELATIVE_NO(rs.getString(26));
                        orders.setORDER_TYPE(rs.getString(27));
                        orders.setEXEC_DATETIME(rs.getString(28));
                        orders.setEXEC_OPERATOR(rs.getString(29));
                        orders.setSYNC_STATUS(rs.getInt(30));
                        patorder.add(orders);
                    }
                }
                rs.close();
                e.onNext(patorder);
            } catch (SQLException ex) {
                ex.printStackTrace();
                e.onError(new Throwable("查询病人医嘱失败:" + ex.getMessage()));
            } finally {
                e.onComplete();
            }
        });
    }

    /**
     * 更新执行人
     */
    public Observable<Boolean> executePatsOrder(@NonNull final PatOrderBean patOrderBean, final String strExecuteDatatime, final String strExecuteOperator) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                SQLiteDatabase db = LocalDbHelper.getInstance(Myapplication.getInstance()).getWritableDatabase();
                db.beginTransaction();
                db.compileStatement(AppSql.sqlExecutePatsOrder);
                try {
                    db.execSQL(AppSql.sqlExecutePatsOrder, new String[]{strExecuteDatatime, strExecuteOperator,
                            patOrderBean.getINDEX_ID()});
                    db.setTransactionSuccessful();
                    e.onNext(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    e.onError(new Throwable("执行医嘱失败" + ex.getMessage()));
                } finally {
                    db.endTransaction();
                    e.onComplete();
                }
            }
        });
    }

    /**
     * 查询科室信息
     */
    public Observable<Map<String, String>> queryDeptDynamics(final String strDeptcode) {
        return Observable.create(e -> {
            SQLiteDatabase db = LocalDbHelper.getInstance(Myapplication.getInstance()).getReadableDatabase();
            try {
                Cursor rs = db.rawQuery(AppSql.sqlQueryDeptDynamics, new String[]{strDeptcode});
                db.beginTransaction();
                Map<String, String> data = new HashMap<>();
                if (rs != null && rs.getCount() > 0) {
                    rs.moveToFirst();
                    data.put("total", String.valueOf(rs.getInt(0)));
                    data.put("newin", String.valueOf(rs.getInt(1)));
                    data.put("preout", String.valueOf(rs.getInt(2)));
                    data.put("emer", String.valueOf(rs.getInt(3)));
                    data.put("grave", String.valueOf(rs.getInt(4)));
                    data.put("normal", String.valueOf(rs.getInt(5)));
                    data.put("death", String.valueOf(rs.getInt(6)));
                }
                rs.close();
                e.onNext(data);
            } catch (SQLException ex) {
                ex.printStackTrace();
                e.onError(new Throwable("查询科室信息失败:" + ex.getMessage()));
            } finally {
                db.endTransaction();
                e.onComplete();
            }
        });
    }

    public Observable<List<PatVitalSignRecBean>> querypatsvitalsignrec(final String strPatientid, final int intVisitid) {
        return Observable.create(e -> {
            List<PatVitalSignRecBean> patVitalSignRecBeen = new ArrayList<>();
            SQLiteDatabase db = LocalDbHelper.getInstance(Myapplication.getInstance()).getReadableDatabase();
            try {
                Cursor rs = db.rawQuery(AppSql.sqlselectPatsVitalSignRec, new String[]{strPatientid, String.valueOf(intVisitid)});
                if (rs != null && rs.getCount() > 0) {
                    while (rs.moveToNext()) {
                        PatVitalSignRecBean patVitalSignRecBean = new PatVitalSignRecBean();
                        patVitalSignRecBean.setPatient_id(rs.getString(0));
                        patVitalSignRecBean.setVisit_id(rs.getInt(1));
                        patVitalSignRecBean.setRecording_date(rs.getString(2));
                        patVitalSignRecBean.setTime_point(rs.getString(3));
                        patVitalSignRecBean.setVital_sign(rs.getString(4));
                        patVitalSignRecBean.setVital_sign_values(rs.getString(5));
                        patVitalSignRecBean.setUnits(rs.getString(6));
                        patVitalSignRecBean.setEnter_date_time(rs.getString(7));
                        patVitalSignRecBean.setOperator(rs.getString(8));
                        patVitalSignRecBean.setSync_machine(rs.getString(9));
                        patVitalSignRecBean.setSync_flag(rs.getInt(10));
                        patVitalSignRecBean.setSync_datetime(rs.getString(11));
                        patVitalSignRecBeen.add(patVitalSignRecBean);
                    }
                }
                rs.close();
                e.onNext(patVitalSignRecBeen);
            } catch (SQLException ex) {
                ex.printStackTrace();
                e.onError(new Throwable("查询vital sign rec 信息失败:" + ex.getMessage()));
            } finally {
                e.onComplete();
            }
        });


    }

    /**
     * 写入体温等数据
     */
    public Observable<Boolean> insertPatsVitalSignRec(final List<PatVitalSignRecBean> patVitalSignRec) {
        return Observable.create(e -> {
            SQLiteDatabase db = LocalDbHelper.getInstance(Myapplication.getInstance()).getWritableDatabase();
            db.beginTransaction();
            try {
                for (PatVitalSignRecBean patVitalSignRecBean : patVitalSignRec) {
                    db.execSQL(AppSql.sqlreplacePatsVitalSignRec, new String[]{patVitalSignRecBean.getPatient_id(), patVitalSignRecBean.getVisit_id().toString(), patVitalSignRecBean.getRecording_date(), patVitalSignRecBean.getTime_point(), patVitalSignRecBean.getVital_sign(), patVitalSignRecBean.getVital_sign_values(), patVitalSignRecBean.getUnits(), patVitalSignRecBean.getEnter_date_time(), patVitalSignRecBean.getOperator(), patVitalSignRecBean.getSync_machine(), patVitalSignRecBean.getSync_flag().toString()});
                }
                db.setTransactionSuccessful();
                e.onNext(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
                e.onError(new Throwable("插入病人体征信息失败:" + ex.getMessage()));
            } finally {
                db.endTransaction();
                e.onComplete();
            }
        });
    }

    /**
     * 删除体温
     */
    public Observable<Boolean> deletePatsVitalSignRec(@NonNull final PatVitalSignRecBean patVitalSignRecBean) {
        return Observable.create(e -> {
            SQLiteDatabase db = LocalDbHelper.getInstance(Myapplication.getInstance()).getReadableDatabase();
            db.beginTransaction();
            try {
                db.execSQL(AppSql.sqlDeletePatsTPRBP, new String[]{patVitalSignRecBean.getPatient_id(), patVitalSignRecBean.getVisit_id().toString(), patVitalSignRecBean.getRecording_date(), patVitalSignRecBean.getTime_point(), patVitalSignRecBean.getVital_sign()});
                db.setTransactionSuccessful();
                e.onNext(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
                e.onError(new Throwable("删除病人体征信息失败:" + ex.getMessage()));
            } finally {
                db.endTransaction();
                e.onComplete();
            }
            e.onNext(false);
        });
    }

}
