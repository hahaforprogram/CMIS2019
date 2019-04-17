package com.haha.cmis.bean;

/**
 * Created by haha on 2018/9/14.
 */

public class InfusionInspectionBean {
    public static final String COLUMN_NAME_INDEX_ID = "index_id";
    public static final String COLUMN_NAME_PATIENT_ID = "patient_id";
    public static final String COLUMN_NAME_VISIT_ID = "visit_id";
    public static final String COLUMN_NAME_ORDER_NO = "order_no";
    public static final String COLUMN_NAME_INSPECT_DATETIME = "INSPECT_DATETIME";
    public static final String COLUMN_NAME_DROPNUMBER = "Dropnumber";
    public static final String COLUMN_NAME_INSPECT_ISSUE = "INSPECT_ISSUE";
    public static final String COLUMN_NAME_EXEC_FLAG = "exec_flag";
    public static final String COLUMN_NAME_EXEC_DATETIME = "exec_datetime";
    public static final String COLUMN_NAME_EXEC_OPERATOR = "exec_operator";
    public static final String COLUMN_NAME_SYNC_FLAG = "sync_flag";
    public static final String COLUMN_NAME_SYNC_DATETIME = "sync_datetime";
    public static final String COLUMN_NAME_SYNC_MACHINE = "sync_machine";


    private String INDEX_ID;
    private String PATIENT_ID;
    private Integer VISIT_ID;
    private Integer ORDER_NO;

    private String INSPECT_DATETIME;
    private String Dropnumber;
    private Integer INSPECT_ISSUE;

    private int EXEC_FLAG;
    private String EXEC_DATETIME;
    private String EXEC_OPERATOR;

    private Integer SYNC_FLAG;
    private String SYNC_DATETIME;
    private String SYNC_MACHINE;


}
