package com.haha.cmis.bean;

import android.support.annotation.NonNull;

/**
 * Created by hah on 2018/1/15.
 */

public class AppDeviceRegistBean {
    @NonNull
    private String cdeptcode = "DEPT_CODE";
    @NonNull
    private String cdeptname = "DEPT_NAME";
    @NonNull
    private String cdevicestatus = "DEVICE_STATUS";

    private String DEPT_CODE;
    private String DEPT_NAME;
    private int DEVICE_STATUS;


    public String getDEPT_CODE() {
        return DEPT_CODE;
    }

    public void setDEPT_CODE(String DEPT_CODE) {
        this.DEPT_CODE = DEPT_CODE;
    }

    public String getDEPT_NAME() {
        return DEPT_NAME;
    }

    public void setDEPT_NAME(String DEPT_NAME) {
        this.DEPT_NAME = DEPT_NAME;
    }

    public int getDEVICE_STATUS() {
        return DEVICE_STATUS;
    }

    public void setDEVICE_STATUS(int DEVICE_STATUS) {
        this.DEVICE_STATUS = DEVICE_STATUS;
    }
}
