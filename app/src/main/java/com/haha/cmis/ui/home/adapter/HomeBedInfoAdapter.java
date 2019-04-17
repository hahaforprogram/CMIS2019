package com.haha.cmis.ui.mainhome.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haha.cmis.R;
import com.haha.cmis.bean.PatInfoBean;
import com.haha.cmis.widget.SlantedTextView;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by hah on 2017/10/25.
 */

public class HomeBedInfoAdapter extends BaseQuickAdapter<PatInfoBean, BaseViewHolder> {
    @NonNull
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public HomeBedInfoAdapter(@LayoutRes int layoutResId, @Nullable List<PatInfoBean> data) {
        super(layoutResId, data);
    }

    public HomeBedInfoAdapter(@Nullable List<PatInfoBean> data) {
        super(data);
    }

    public HomeBedInfoAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, @NonNull PatInfoBean item) {
        helper.setText(R.id.tv_patinpno, item.getInp_no())
                .setText(R.id.tv_patbedno, item.getBed_no().toString())
                .setText(R.id.tv_patsex, item.getSex())
                .setText(R.id.tv_patname, item.getName())
                .setText(R.id.tv_patdiagnosis, item.getDiagnosis())
                .setText(R.id.tv_chargetype, item.getCharge_type());

        SlantedTextView stvPatbed = helper.getView(R.id.stv_bed);
        switch (item.getPatient_class()) {
            case "特级护理":
                stvPatbed.setSlantedBackgroundColor(mContext.getResources().getColor(R.color.color_special_class)).setSlantedLength(120).setMode(SlantedTextView.MODE_LEFT);
                helper.setTextColor(R.id.tv_patbedno, mContext.getResources().getColor(R.color.color_special_class));
                break;
            case "一级护理":
                stvPatbed.setSlantedBackgroundColor(mContext.getResources().getColor(R.color.color_one_class)).setSlantedLength(120).setMode(SlantedTextView.MODE_LEFT);
                helper.setTextColor(R.id.tv_patbedno, mContext.getResources().getColor(R.color.color_one_class));
                break;
            case "二级护理":
                stvPatbed.setSlantedBackgroundColor(mContext.getResources().getColor(R.color.color_two_class)).setSlantedLength(120).setMode(SlantedTextView.MODE_LEFT);
                helper.setTextColor(R.id.tv_patbedno, mContext.getResources().getColor(R.color.color_two_class));
                break;
            case "三级护理":
                stvPatbed.setSlantedBackgroundColor(mContext.getResources().getColor(R.color.color_three_class)).setSlantedLength(120).setMode(SlantedTextView.MODE_LEFT);
                helper.setTextColor(R.id.tv_patbedno, mContext.getResources().getColor(R.color.color_three_class));
                break;
            default:
                stvPatbed.setSlantedBackgroundColor(mContext.getResources().getColor(R.color.color_three_class)).setSlantedLength(120).setMode(SlantedTextView.MODE_LEFT);
                helper.setTextColor(R.id.tv_patbedno, mContext.getResources().getColor(R.color.color_three_class));
                break;
        }

        //计算年龄
        try {
            java.util.Date curDate = new Date(System.currentTimeMillis());
            if (item.getBirth_date() != null) {
                java.util.Date date1 = sdf.parse(item.getBirth_date());
                long bc = (curDate.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24) / 365;
                helper.setText(R.id.tv_patage, String.valueOf(bc) + "岁");
            }

            if (item.getAdmission_date_time() != null) {
                java.util.Date admissiondate = sdf.parse(item.getAdmission_date_time());
                long ry = (curDate.getTime() - admissiondate.getTime()) / (1000 * 60 * 60 * 24);
                helper.setText(R.id.tv_patindays, "入院" + String.valueOf(ry + 1) + "天");
            }

            if (item.getOperating_date() != null) {
                java.util.Date operatingdate = sdf.parse(item.getOperating_date());
                long ss = (curDate.getTime() - operatingdate.getTime()) / (1000 * 60 * 60 * 24);
                if (ss > 0) {
                    helper.setText(R.id.tv_patotherinfo, "术后" + String.valueOf(ss + 1) + "天");
                    helper.setVisible(R.id.tv_patotherinfo, true);
                }
            } else {
                helper.setVisible(R.id.tv_patotherinfo, false);
            }

            if (item.getAlergy_drugs() != null) {
                if (!item.getAlergy_drugs().equals("无")) {
                    helper.setText(R.id.tv_patalerydrugs, item.getAlergy_drugs());
                }
            }

            //头像
            if (item.getPatient_image() != null) {
                Glide.with(mContext)
                        .load(item.getPatient_image())
                        .asBitmap()
                        .skipMemoryCache(true).into((ImageView) helper.getView(R.id.tv_patimg));
            } else {
                helper.setImageResource(R.id.tv_patimg, R.drawable.ic_user);
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }


}
