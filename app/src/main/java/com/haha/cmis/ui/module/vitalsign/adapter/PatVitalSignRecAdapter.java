package com.haha.cmis.ui.module.vitalsign.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.ThemeEnforcement;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haha.cmis.R;
import com.haha.cmis.bean.PatVitalSignRecBean;
;

import java.util.List;

/**
 * Created by haha on 2018/7/7.
 */

public class PatVitalSignRecAdapter extends BaseQuickAdapter<PatVitalSignRecBean, BaseViewHolder> {
    @Nullable
    List<PatVitalSignRecBean> mPatVitalSignRecBean;

    public PatVitalSignRecAdapter(@LayoutRes int layoutResId, @Nullable List<PatVitalSignRecBean> data) {
        super(layoutResId, data);
        mPatVitalSignRecBean = data;
    }

    public PatVitalSignRecAdapter(@Nullable List<PatVitalSignRecBean> data) {
        super(data);
    }

    public PatVitalSignRecAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    public void setNewData(@Nullable List<PatVitalSignRecBean> data) {
        super.setNewData(data);
        mPatVitalSignRecBean = data;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, @NonNull PatVitalSignRecBean item) {
        helper.setText(R.id.tv_vitalsign, item.getVital_sign())
                .setText(R.id.tv_vitalsignvalue, item.getVital_sign_values() + item.getUnits())
                .setText(R.id.tv_index, item.getTime_point());
        if (item.getSync_flag().equals(1)) {
            helper.setText(R.id.tv_sync, "已同步")
                    .setTextColor(R.id.tv_sync, Color.RED);
        } else {
            helper.setText(R.id.tv_sync, "未同步")
                    .setTextColor(R.id.tv_sync, Color.RED);
            ;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder helper, int position) {
        super.onBindViewHolder(helper, position);

        if (position == 0 || !mPatVitalSignRecBean.get(position - 1).getTime_point().equals(mPatVitalSignRecBean.get(position).getTime_point())) {
            helper.setVisible(R.id.tv_index, true);
        } else {
            helper.setGone(R.id.tv_index, false);
        }
    }
}
