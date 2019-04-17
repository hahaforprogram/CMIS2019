package com.haha.cmis.constant;

import com.haha.cmis.utils.MyUtils;
import com.haha.cmis.utils.PreferenceUtil;


/**
 * @author hahame  @date 2018/11/16
 */
public class AppCookie {
    public AppCookie() {
    }

    public static String getDatabaseName() {
        return AppConfig.appSetting.databaseName;
    }

    /**
     * 设置参数 database sqlite 存储路径
     */
    public static void setDataBabasePath(String strDatabaseDIr) {
        PreferenceUtil.set(AppConfig.appSetting.dirDatabase, strDatabaseDIr);
    }

    public static String getDataBabasePath() {
        return PreferenceUtil.getString(AppConfig.appSetting.dirDatabase);
    }

    /**
     * 设置病人头像存储路径
     */
    public static void setPatImgPath(String strPatImg) {
        PreferenceUtil.set(AppConfig.appSetting.dirPatImg, strPatImg);
    }

    public static String getPatImgPath() {
        return PreferenceUtil.getString(AppConfig.appSetting.dirPatImg);
    }

    /**
     * 设置系统日志存储路径
     */
    public static void setApplogPath(String strApplog) {
        PreferenceUtil.set(AppConfig.appSetting.dirApplog, strApplog);
    }

    public static String getApplogPath() {
        return PreferenceUtil.getString(AppConfig.appSetting.dirApplog);
    }

    /**
     * 设置系统日志存储路径
     */
    public static void setSoresImgPath(String strSoresImg) {
        PreferenceUtil.set(AppConfig.appSetting.dirSoresImg, strSoresImg);
    }

    public static String getSoresImgPath() {
        return PreferenceUtil.getString(AppConfig.appSetting.dirSoresImg);
    }

    /**
     * 用户科室代码
     */
    public static void setDeptcode(String strDeptcode) {
        PreferenceUtil.set(AppConfig.userLoginInfo.deptcode, strDeptcode);
    }

    public static String getDeptcode() {
        return PreferenceUtil.getString(AppConfig.userLoginInfo.deptcode);
    }

    /**
     * 用户科室名称
     */
    public static void setDeptname(String strDeptname) {
        PreferenceUtil.set(AppConfig.userLoginInfo.deptname, strDeptname);
    }

    public static String getDeptname() {
        return PreferenceUtil.getString(AppConfig.userLoginInfo.deptname);
    }

    /**
     * 用户ID
     */
    public static void setUserid(String strUserid) {
        PreferenceUtil.set(AppConfig.userLoginInfo.userid, strUserid);
    }

    public static String getUserid() {
        return PreferenceUtil.getString(AppConfig.userLoginInfo.userid);
    }

    /**
     * 用户 usercode
     */
    public static void setUserCode(String strUserCode) {
        PreferenceUtil.set(AppConfig.userLoginInfo.usercode, strUserCode);
    }

    public static String getUsercode() {
        return PreferenceUtil.getString(AppConfig.userLoginInfo.usercode);
    }

    /**
     * 用户姓名
     */
    public static void setUsername(String strUsername) {
        PreferenceUtil.set(AppConfig.userLoginInfo.username, strUsername);
    }

    public static String getUsername() {
        return PreferenceUtil.getString(AppConfig.userLoginInfo.username);
    }

    /**
     * 设备mac
     */
    public static void setDeviceId(String strDeviceId) {
        PreferenceUtil.set(AppConfig.userLoginInfo.deviceid, strDeviceId);
    }

    public static String getDeviceId() {
        return PreferenceUtil.getString(AppConfig.userLoginInfo.deviceid);
    }

    /**
     * 获取当前wifi设置信息
     */
    public static void setWificheckip(String strwificheckip) {
        PreferenceUtil.set(AppConfig.wifiInfo.wificheckip, strwificheckip);
    }

    public static void setWifissid(String strwifissid) {
        PreferenceUtil.set(AppConfig.wifiInfo.wifissid, strwifissid);
    }

    public static void setWifipassword(String strwifipassword) {
        PreferenceUtil.set(AppConfig.wifiInfo.wifipassword, strwifipassword);
    }


    public static String getWificheckip() {
        return MyUtils.getDecodeBase64(PreferenceUtil.getString(AppConfig.wifiInfo.wificheckip));
    }

    public static String getWifissid() {
        return MyUtils.getDecodeBase64(PreferenceUtil.getString(AppConfig.wifiInfo.wifissid));
    }

    public static String getWifipassword() {
        return MyUtils.getDecodeBase64(PreferenceUtil.getString(AppConfig.wifiInfo.wifipassword));
    }

    /**
     * 设置当前扫描二维码类型
     */
    public static void setCurrentBarType(String strcurrentBarType) {
        PreferenceUtil.set(AppConfig.currentBarType, strcurrentBarType);
    }

    public static String getCurrentBarType() {
        return PreferenceUtil.getString(AppConfig.currentBarType);
    }
}
