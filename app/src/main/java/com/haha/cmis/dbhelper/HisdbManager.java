package com.haha.cmis.dbhelper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.haha.cmis.constant.AppConfig;
import com.haha.cmis.constant.AppCookie;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.reactivex.Observable;
import oracle.jdbc.OracleTypes;

/**
 * @author hahame  @date 2019/1/3
 */
public class HisdbManager {
    private static final String TAG = "FAIROL-HisdbManager";
    private static HisdbManager instance;
    private static Connection hisConnection = null;
    private static ResultSet hisResultSet = null;
    private static SQLiteStatement localstmt = null;
    private static SQLiteDatabase localdb = null;


    public HisdbManager() {
    }

    /**
     * 初始化单例* @param
     */
    public static synchronized HisdbManager getInstance() {
        if (instance == null) {
            synchronized (HisdbManager.class) {
                if (instance == null) {
                    instance = new HisdbManager();
                }
            }
        }
        return instance;
    }


    /**连接数据库*/

    /**
     * 通用执行oracle存储过程 返回 resultset
     */
    public static ResultSet executePLSQL(String strPLSQL, String strParameters) {
        /**his oracle 执行存储过错 */
        CallableStatement hisPlSql;
        try {
            if (hisConnection == null || hisConnection.isClosed()) {
                Class.forName(AppConfig.hisDatabase.DriverClass);
                hisConnection = DriverManager.getConnection(AppConfig.hisDatabase.ConnectInfo, AppConfig.hisDatabase.ConnectUser, AppConfig.hisDatabase.ConnectPass);
            }
            strPLSQL = "{ call " + strPLSQL + " }";
            hisPlSql = hisConnection.prepareCall(strPLSQL);
            hisPlSql.setString(1, strParameters);
            hisPlSql.registerOutParameter(2, OracleTypes.CURSOR);
            hisPlSql.execute();
            ResultSet rs = (ResultSet) hisPlSql.getObject(2);
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 获取设备信息
     */
    public Observable<Boolean> getAppRegisterInfo() {
        return Observable.create(e -> {
            int intDeviceStatus = -1;
            String strDeptcode = null;
            String strDeptName = null;
            Boolean isHasRows = false;
            Log.d(TAG, "getAppRegisterInfo: " + AppCookie.getDeviceId());
            try {
                hisResultSet = executePLSQL("pck_hcis.gethcisdeviceinfo(?,?)", AppCookie.getDeviceId());
                while (hisResultSet.next()) {
                    isHasRows = true;
                    intDeviceStatus = hisResultSet.getInt("device_status");
                    strDeptcode = new String(hisResultSet.getString("dept_code").getBytes("iso-8859-1"), "gbk");
                    strDeptName = new String(hisResultSet.getString("dept_name").getBytes("iso-8859-1"), "gbk");
                    if (intDeviceStatus == -1) {
                        e.onError(new Throwable("此设备已注销，请联系相关人员进行重新注册！"));
                    }
                }
                if (!isHasRows) {
                    e.onError(new Throwable("此设备尚未注册！"));
                } else {
                    AppCookie.setDeptcode(strDeptcode);
                    AppCookie.setDeptname(strDeptName);
                    e.onNext(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                e.onError(new Throwable("获取设备注册信息错误，连接数据库失败:SQLException\n" + ex.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
                e.onError(new Throwable("获取设备注册信息错误，连接数据库失败:Exception\n" + ex.getMessage()));
            } finally {
                if (hisResultSet != null) {
                    hisResultSet.close();
                }
                if (hisConnection != null) {
                    hisConnection.close();
                }
                e.onComplete();
            }
            e.onNext(false);
        });
    }


    public void close() {
        try {
            // 应该明确地关闭所有的数据库资源
            if (null != hisResultSet) {
                hisResultSet.close();
            }

            if (null != localstmt) {
                localstmt.close();
            }

            if (null != hisConnection) {
                hisConnection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

