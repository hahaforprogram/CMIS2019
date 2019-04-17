package com.haha.cmis.ui.main.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import com.haha.cmis.ui.module.order.PatOrdersFragment;
import com.haha.cmis.ui.module.vitalsign.View_VitalSignRecFragment;
import com.haha.cmis.ui.module.vitalsign.VitalSignRecFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hah on 2018/6/14.
 */

public class FunctionFragmentManger {

    @NonNull
    Map<String, Fragment> ffm = new HashMap<String, Fragment>();

    public FunctionFragmentManger() {
        set();
    }

    public void set() {
        ffm.put("生命体征", new VitalSignRecFragment());
//        ffm.put("口服药管理",new PatOrdersFragment());
        ffm.put("医嘱执行", new PatOrdersFragment());
//        ffm.put("注射管理",new PatOrdersFragment());
//        ffm.put("输液管理",new PatOrdersFragment());
        ffm.put("生命体征查看", new View_VitalSignRecFragment());
    }


    @Nullable
    public Fragment get(String clsClass) {
        for (Map.Entry<String, Fragment> entry : ffm.entrySet()) {
            if (entry.getKey().equals(clsClass)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @NonNull
    public String getOrderType(@NonNull String strType) {
        String strOrdertype = "";
        switch (strType) {
            case "生命体征":
                break;
            case "口服药管理":
                strOrdertype = "口服";
                break;
            case "医嘱执行":
                break;
            case "注射管理":
                break;
            case "输液管理":
                break;
        }
        return strOrdertype;
    }


}
