package com.haha.cmis.constant;


import com.haha.cmis.R;

public class AppConfig {
    /**
     * app 系统设置
     */
    public static class appSetting {
        /**
         * app信息
         */
        public static final String databaseName = "Hcis.db";
        /**
         * 数据库存储目录
         */
        public static final String dirDatabase = "DATABASE";
        /**
         * 病人头像存储目录
         */
        public static final String dirPatImg = "PatImg";
        /**
         * 其他图片存储目录
         */
        public static final String dirSoresImg = "SoresImg";
        /**
         * 系统日志
         */
        public static final String dirApplog = "AppLog";
    }

    /**
     * his数据库配置
     */
    public static class hisDatabase {
        public static String DriverClass = "oracle.jdbc.driver.OracleDriver";
        public static String ConnectInfo = "jdbc:oracle:thin:@192.168.202.1:1521:dbserver1";
        public static String ConnectUser = "comminterface";
        public static String ConnectPass = "cp6vm";
    }


    /**
     * app wifi 连接信息
     */
    public static class wifiInfo {
        /**
         * SharedPreferencesHelper 公共变量节点定义
         */
        public static final String wificheckip = "WIFICHECKIP";
        public static final String wifissid = "WIFISSID";
        public static final String wifipassword = "WIFIPASSWORD";
    }

    /**
     * app login info
     */
    public static class userLoginInfo {
        public static final String usercode = "USERCODE";
        public static final String userid = "USERID";
        public static final String username = "USERNAME";
        public static final String deptcode = "DEPTCODE";
        public static final String deptname = "DEPTNAME";
        public static final String deviceid = "DEVICEID";
    }

    /**
     * app 二维码类型/**二维码表示定义
     */
    public static final String currentBarType = "CURRENTSCANBARTYPE";


    /**
     * patmainactivity功能
     */
    public static final String[] patMaintypeName1 = {"生命体征",
            "医嘱执行",
            "翻身管理",
            "病人巡视",
            "护理记录"};
    public static final String[] patMaintypeName2 = {"化验采集", "手术核对", "检查报告", "检验报告"};

    public static final int[] patMaintypeImg1 = {R.drawable.ic_vitalsign_blue,
            R.drawable.ic_order_blue,
            R.drawable.ic_turnoverbody_green,
            R.drawable.ic_tour,
            R.drawable.ic_nursingrecorder_red};
    public static final int[] patMaintypeImg2 = {R.drawable.ic_labcheck_blue,
            R.drawable.ic_oper_red,
            R.drawable.ic_examreport,
            R.drawable.ic_labreport_blue};

    /**
     * 消息传递
     */
    public static class appTransInfo {
        public static String TransBedNo = "BEDNO";
        public static String TransPatientId = "PATIENTID";
        public static String TransVisitId = "VISITID";
        public static String TransPatientName = "PATIENTNAME";
        public static String TransWardCode = "WARDCODE";
        public static String TransFunctionClass = "FUNCTIONCLASS";
        public static String TransOrderTypeFlag = "ORDERTYPEFLAG";
        public static String TransOrderType = "ORDERTYPE";
    }

    /**
     * 医嘱类型
     */
    public static class orderType {
        public static String[] Ordertype = {"口服", "注射", "静推", "输液", "检验", "检查"};
        public static String[] OrderTypeForMedicine = {"口服"};
        public static String[] OrderTypeForInject = {"注射", "静推"};
        public static String[] OrderTypeForInfusion = {"输液"};
        public static String[] OrderTypeForlab = {"检验"};
        public static String[] OrderTypeForExam = {"检查"};
        public static String[] OrdertypeDeal = {"处置"};
        public static String[] OrdertypeNurse = {"护理"};
        public static String[] OrdertypeOperation = {"手术"};
        public static String[] OrdertypeTreat = {"治疗"};
    }

}
