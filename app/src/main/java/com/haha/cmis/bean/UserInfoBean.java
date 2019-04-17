package com.haha.cmis.bean;

/**
 * Created by Haha on 2017/10/9.
 */

public class UserInfoBean {
    public static final String COLUMN_NAME_USERCODE = "usercode";
    public static final String COLUMN_NAME_USERID = "userid";
    public static final String COLUMN_NAME_USERNAME = "username";
    public static final String COLUMN_NAME_DEPTCODE = "userdeptcode";
    public static final String COLUMN_NAME_DEPTNAME = "userdeptname";
    public static final String COLUMN_NAME_USERROLE = "userrole";

    private static String usercode;
    private static String userid;
    private static String username;
    private static String userdeptcode;
    private static String userdeptname;
    private static String userrole;


    public static String getUsercode() {
        return usercode;
    }

    public static void setUsercode(String usercode) {
        UserInfoBean.usercode = usercode;
    }

    public static String getUserid() {
        return userid;
    }

    public static void setUserid(String userid) {
        UserInfoBean.userid = userid;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserInfoBean.username = username;
    }

    public static String getUserdeptcode() {
        return userdeptcode;
    }

    public static void setUserdeptcode(String userdeptcode) {
        UserInfoBean.userdeptcode = userdeptcode;
    }

    public static String getUserdeptname() {
        return userdeptname;
    }

    public static void setUserdeptname(String userdeptname) {
        UserInfoBean.userdeptname = userdeptname;
    }

    public static String getUserrole() {
        return userrole;
    }

    public static void setUserrole(String userrole) {
        UserInfoBean.userrole = userrole;
    }

}
