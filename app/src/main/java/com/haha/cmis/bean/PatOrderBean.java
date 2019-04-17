package com.haha.cmis.bean;

/**
 * Created by hah on 2017/11/9.
 */

public class PatOrderBean {

    public static final String COLUMN_NAME_INDEX_ID = "index_id";
    public static final String COLUMN_NAME_PATIENT_ID = "patient_id";
    public static final String COLUMN_NAME_VISIT_ID = "visit_id";
    public static final String COLUMN_NAME_ORDER_NO = "order_no";
    public static final String COLUMN_NAME_ORDERING_DEPT = "ordering_dept";
    public static final String COLUMN_NAME_REPEAT_INDICATOR = "repeat_indicator";
    public static final String COLUMN_NAME_START_DATE_TIME = "start_date_time";
    public static final String COLUMN_NAME_STOP_DATE_TIME = "stop_date_time";
    public static final String COLUMN_NAME_FREQ_DETAIL = "freq_detail";
    public static final String COLUMN_NAME_DEFAULT_SCHEDULE = "default_schedule";
    public static final String COLUMN_NAME_NEW_DEFAULT_SCHEDULE = "new_default_schedule";
    public static final String COLUMN_NAME_PLAN_EXEC_DATETIME = "plan_exec_datetime";
    public static final String COLUMN_NAME_LAB_EXAM_CLASS = "lab_exam_class";
    public static final String COLUMN_NAME_IS_EMER = "is_emer";
    public static final String COLUMN_NAME_AJUST_MEMO = "ajust_memo";
    public static final String COLUMN_NAME_ORDER_CLASS = "order_class";
    public static final String COLUMN_NAME_ORDER_TEXT = "order_text";
    public static final String COLUMN_NAME_ADMINISTRATION = "administration";
    public static final String COLUMN_NAME_FREQUENCY = "frequency";
    public static final String COLUMN_NAME_FREQ_COUNTER = "freq_counter";
    public static final String COLUMN_NAME_FREQ_INTERVAL = "freq_interval";
    public static final String COLUMN_NAME_FREQ_INTERVAL_UNIT = "freq_interval_unit";
    public static final String COLUMN_NAME_ORDER_STATUS = "order_status";
    public static final String COLUMN_NAME_DOCTOR = "doctor";
    public static final String COLUMN_NAME_NURSE = "nurse";
    public static final String COLUMN_NAME_STOP_INFO = "stop_info";
    public static final String COLUMN_NAME_RELATIVE_NO = "relative_no";
    public static final String COLUMN_NAME_ORDER_TYPE = "order_type";
    public static final String COLUMN_NAME_EXEC_STATUS = "exec_status";
    public static final String COLUMN_NAME_EXEC_DATETIME = "exec_datetime";
    public static final String COLUMN_NAME_EXEC_OPERATOR = "exec_operator";
    public static final String COLUMN_NAME_FIX_EXEC_DATETIME = "fix_exec_datetime";
    public static final String COLUMN_NAME_FIX_EXEC_OPERATOR = "fix_exec_operator";
    public static final String COLUMN_NAME_SYNC_STATUS = "sync_status";
    public static final String COLUMN_NAME_SYNC_DATETIME = "sync_datetime";
    public static final String COLUMN_NAME_SYNC_MACHINE = "sync_machine";
    public static final String COLUMN_NAME_FLAG = "flag";

    private String INDEX_ID;
    private String PATIENT_ID;
    private Integer VISIT_ID;
    private Integer ORDER_NO;
    private String ORDERING_DEPT;
    private Integer REPEAT_INDICATOR;
    private String START_DATE_TIME;
    private String STOP_DATE_TIME;
    private String FREQ_DETAIL;
    private String DEFAULT_SCHEDULE;
    private String NEW_DEFAULT_SCHEDULE;
    private String PLAN_EXEC_DATETIME;
    private String LAB_EXAM_CLASS;
    private Integer IS_EMER;
    private String AJUST_MEMO;
    private String ORDER_CLASS;
    private String ORDER_TEXT;
    private String ADMINISTRATION;
    private String FREQUENCY;
    private String FREQ_COUNTER;
    private Integer FREQ_INTERVAL;
    private String FREQ_INTERVAL_UNIT;
    private Integer ORDER_STATUS;
    private String DOCTOR;
    private String NURSE;
    private String STOP_INFO;
    private String RELATIVE_NO;
    private String ORDER_TYPE;
    private int EXEC_STATUS;
    private String EXEC_DATETIME;
    private String EXEC_OPERATOR;
    private String FIX_EXEC_DATETIME;
    private String FIX_EXEC_OPERATOR;
    private Integer SYNC_STATUS;
    private String SYNC_DATETIME;
    private String SYNC_MACHINE;
    private Integer FLAG;


    public String getINDEX_ID() {
        return INDEX_ID;
    }

    public void setINDEX_ID(String INDEX_ID) {
        this.INDEX_ID = INDEX_ID;
    }

    public String getPATIENT_ID() {
        return PATIENT_ID;
    }

    public void setPATIENT_ID(String PATIENT_ID) {
        this.PATIENT_ID = PATIENT_ID;
    }

    public Integer getVISIT_ID() {
        return VISIT_ID;
    }

    public void setVISIT_ID(Integer VISIT_ID) {
        this.VISIT_ID = VISIT_ID;
    }

    public Integer getORDER_NO() {
        return ORDER_NO;
    }

    public void setORDER_NO(Integer ORDER_NO) {
        this.ORDER_NO = ORDER_NO;
    }

    public String getORDERING_DEPT() {
        return ORDERING_DEPT;
    }

    public void setORDERING_DEPT(String ORDERING_DEPT) {
        this.ORDERING_DEPT = ORDERING_DEPT;
    }

    public Integer getREPEAT_INDICATOR() {
        return REPEAT_INDICATOR;
    }

    public void setREPEAT_INDICATOR(Integer REPEAT_INDICATOR) {
        this.REPEAT_INDICATOR = REPEAT_INDICATOR;
    }

    public String getSTART_DATE_TIME() {
        return START_DATE_TIME;
    }

    public void setSTART_DATE_TIME(String START_DATE_TIME) {
        this.START_DATE_TIME = START_DATE_TIME;
    }

    public String getSTOP_DATE_TIME() {
        return STOP_DATE_TIME;
    }

    public void setSTOP_DATE_TIME(String STOP_DATE_TIME) {
        this.STOP_DATE_TIME = STOP_DATE_TIME;
    }

    public String getFREQ_DETAIL() {
        return FREQ_DETAIL;
    }

    public void setFREQ_DETAIL(String FREQ_DETAIL) {
        this.FREQ_DETAIL = FREQ_DETAIL;
    }

    public String getDEFAULT_SCHEDULE() {
        return DEFAULT_SCHEDULE;
    }

    public void setDEFAULT_SCHEDULE(String DEFAULT_SCHEDULE) {
        this.DEFAULT_SCHEDULE = DEFAULT_SCHEDULE;
    }

    public String getNEW_DEFAULT_SCHEDULE() {
        return NEW_DEFAULT_SCHEDULE;
    }

    public void setNEW_DEFAULT_SCHEDULE(String NEW_DEFAULT_SCHEDULE) {
        this.NEW_DEFAULT_SCHEDULE = NEW_DEFAULT_SCHEDULE;
    }

    public String getPLAN_EXEC_DATETIME() {
        return PLAN_EXEC_DATETIME;
    }

    public void setPLAN_EXEC_DATETIME(String PLAN_EXEC_DATETIME) {
        this.PLAN_EXEC_DATETIME = PLAN_EXEC_DATETIME;
    }

    public String getLAB_EXAM_CLASS() {
        return LAB_EXAM_CLASS;
    }

    public void setLAB_EXAM_CLASS(String LAB_EXAM_CLASS) {
        this.LAB_EXAM_CLASS = LAB_EXAM_CLASS;
    }

    public Integer getIS_EMER() {
        return IS_EMER;
    }

    public void setIS_EMER(Integer IS_EMER) {
        this.IS_EMER = IS_EMER;
    }

    public String getAJUST_MEMO() {
        return AJUST_MEMO;
    }

    public void setAJUST_MEMO(String AJUST_MEMO) {
        this.AJUST_MEMO = AJUST_MEMO;
    }

    public String getORDER_CLASS() {
        return ORDER_CLASS;
    }

    public void setORDER_CLASS(String ORDER_CLASS) {
        this.ORDER_CLASS = ORDER_CLASS;
    }

    public String getORDER_TEXT() {
        return ORDER_TEXT;
    }

    public void setORDER_TEXT(String ORDER_TEXT) {
        this.ORDER_TEXT = ORDER_TEXT;
    }

    public String getADMINISTRATION() {
        return ADMINISTRATION;
    }

    public void setADMINISTRATION(String ADMINISTRATION) {
        this.ADMINISTRATION = ADMINISTRATION;
    }

    public String getFREQUENCY() {
        return FREQUENCY;
    }

    public void setFREQUENCY(String FREQUENCY) {
        this.FREQUENCY = FREQUENCY;
    }

    public String getFREQ_COUNTER() {
        return FREQ_COUNTER;
    }

    public void setFREQ_COUNTER(String FREQ_COUNTER) {
        this.FREQ_COUNTER = FREQ_COUNTER;
    }

    public Integer getFREQ_INTERVAL() {
        return FREQ_INTERVAL;
    }

    public void setFREQ_INTERVAL(Integer FREQ_INTERVAL) {
        this.FREQ_INTERVAL = FREQ_INTERVAL;
    }

    public String getFREQ_INTERVAL_UNIT() {
        return FREQ_INTERVAL_UNIT;
    }

    public void setFREQ_INTERVAL_UNIT(String FREQ_INTERVAL_UNIT) {
        this.FREQ_INTERVAL_UNIT = FREQ_INTERVAL_UNIT;
    }

    public Integer getORDER_STATUS() {
        return ORDER_STATUS;
    }

    public void setORDER_STATUS(Integer ORDER_STATUS) {
        this.ORDER_STATUS = ORDER_STATUS;
    }

    public String getDOCTOR() {
        return DOCTOR;
    }

    public void setDOCTOR(String DOCTOR) {
        this.DOCTOR = DOCTOR;
    }

    public String getNURSE() {
        return NURSE;
    }

    public void setNURSE(String NURSE) {
        this.NURSE = NURSE;
    }

    public String getSTOP_INFO() {
        return STOP_INFO;
    }

    public void setSTOP_INFO(String STOP_INFO) {
        this.STOP_INFO = STOP_INFO;
    }

    public String getRELATIVE_NO() {
        return RELATIVE_NO;
    }

    public void setRELATIVE_NO(String RELATIVE_NO) {
        this.RELATIVE_NO = RELATIVE_NO;
    }

    public String getORDER_TYPE() {
        return ORDER_TYPE;
    }

    public void setORDER_TYPE(String ORDER_TYPE) {
        this.ORDER_TYPE = ORDER_TYPE;
    }

    public int getEXEC_STATUS() {
        return EXEC_STATUS;
    }

    public void setEXEC_STATUS(int EXEC_STATUS) {
        this.EXEC_STATUS = EXEC_STATUS;
    }

    public String getEXEC_DATETIME() {
        return EXEC_DATETIME;
    }

    public void setEXEC_DATETIME(String EXEC_DATETIME) {
        this.EXEC_DATETIME = EXEC_DATETIME;
    }

    public String getEXEC_OPERATOR() {
        return EXEC_OPERATOR;
    }

    public void setEXEC_OPERATOR(String EXEC_OPERATOR) {
        this.EXEC_OPERATOR = EXEC_OPERATOR;
    }

    public String getFIX_EXEC_DATETIME() {
        return FIX_EXEC_DATETIME;
    }

    public void setFIX_EXEC_DATETIME(String FIX_EXEC_DATETIME) {
        this.FIX_EXEC_DATETIME = FIX_EXEC_DATETIME;
    }

    public String getFIX_EXEC_OPERATOR() {
        return FIX_EXEC_OPERATOR;
    }

    public void setFIX_EXEC_OPERATOR(String FIX_EXEC_OPERATOR) {
        this.FIX_EXEC_OPERATOR = FIX_EXEC_OPERATOR;
    }

    public Integer getSYNC_STATUS() {
        return SYNC_STATUS;
    }

    public void setSYNC_STATUS(Integer SYNC_STATUS) {
        this.SYNC_STATUS = SYNC_STATUS;
    }

    public String getSYNC_DATETIME() {
        return SYNC_DATETIME;
    }

    public void setSYNC_DATETIME(String SYNC_DATETIME) {
        this.SYNC_DATETIME = SYNC_DATETIME;
    }

    public String getSYNC_MACHINE() {
        return SYNC_MACHINE;
    }

    public void setSYNC_MACHINE(String SYNC_MACHINE) {
        this.SYNC_MACHINE = SYNC_MACHINE;
    }

    public Integer getFLAG() {
        return FLAG;
    }

    public void setFLAG(Integer FLAG) {
        this.FLAG = FLAG;
    }
}
