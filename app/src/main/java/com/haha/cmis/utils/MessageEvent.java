package com.haha.cmis.utils;


public class MessageEvent {
    private String code;
    private String data;

    public MessageEvent(String code, String Data) {
        this.code = code;
        this.data = Data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
