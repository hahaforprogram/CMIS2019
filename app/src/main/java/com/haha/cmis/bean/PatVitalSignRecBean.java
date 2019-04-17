package com.haha.cmis.bean;

/**
 * Created by hah on 2018/5/2.
 */

public class PatVitalSignRecBean {

    public static final String COLUMN_NAME_PATIENT_ID = "patient_id";
    public static final String COLUMN_NAME_VISIT_ID = "visit_id";
    public static final String COLUMN_NAME_RECORDING_DATE = "recording_date";
    public static final String COLUMN_NAME_TIME_POINT = "time_point";
    public static final String COLUMN_NAME_VITAL_SIGN = "vital_sign";
    public static final String COLUMN_NAME_VITAL_SIGN_VALUES = "vital_sign_values";
    public static final String COLUMN_NAME_UNITS = "units";
    public static final String COLUMN_NAME_ENTER_DATE_TIME = "enter_date_time";
    public static final String COLUMN_NAME_OPERATOR = "operator";
    public static final String COLUMN_NAME_SYNC_MACHINE = "sync_machine";
    public static final String COLUMN_NAME_SYNC_FLAG = "sync_flag";
    public static final String COLUMN_NAME_SYNC_DATETIME = "sync_datetime";


    private String patient_id;
    private Integer visit_id;
    private String recording_date;
    private String time_point;
    private String vital_sign;
    private String vital_sign_values;
    private String units;
    private String enter_date_time;
    private String operator;
    private String sync_machine;
    private Integer sync_flag; //0 新增  （1下载  2已同步） -1 修改数据  -100 删除数据
    private String sync_datetime;


    public Integer getSync_flag() {
        return sync_flag;
    }

    public void setSync_flag(Integer sync_flag) {
        this.sync_flag = sync_flag;
    }

    public String getSync_datetime() {
        return sync_datetime;
    }

    public void setSync_datetime(String sync_datetime) {
        this.sync_datetime = sync_datetime;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public String getSync_machine() {
        return sync_machine;
    }

    public void setSync_machine(String sync_machine) {
        this.sync_machine = sync_machine;
    }

    public String getVital_sign_values() {
        return vital_sign_values;
    }

    public void setVital_sign_values(String vital_sign_values) {
        this.vital_sign_values = vital_sign_values;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public Integer getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(Integer visit_id) {
        this.visit_id = visit_id;
    }

    public String getRecording_date() {
        return recording_date;
    }

    public void setRecording_date(String recording_date) {
        this.recording_date = recording_date;
    }

    public String getTime_point() {
        return time_point;
    }

    public void setTime_point(String time_point) {
        this.time_point = time_point;
    }

    public String getVital_sign() {
        return vital_sign;
    }

    public void setVital_sign(String vital_sign) {
        this.vital_sign = vital_sign;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getEnter_date_time() {
        return enter_date_time;
    }

    public void setEnter_date_time(String enter_date_time) {
        this.enter_date_time = enter_date_time;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }


}
