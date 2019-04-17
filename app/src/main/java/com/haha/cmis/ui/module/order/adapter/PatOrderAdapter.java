package com.haha.cmis.ui.module.order.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haha.cmis.R;
import com.haha.cmis.bean.PatOrderBean;

import java.util.ArrayList;
import java.util.List;

import de.mrapp.android.dialog.ProgressDialog;
import de.mrapp.android.dialog.decorator.AbstractDialogDecorator;

/**
 * Created by hah on 2017/11/21.
 */

public class PatOrderAdapter extends BaseQuickAdapter<PatOrderBean, BaseViewHolder> {
    @Nullable
    private List<PatOrderBean> mPatOrderBean = new ArrayList<>();

    public PatOrderAdapter(@LayoutRes int layoutResId, @Nullable List<PatOrderBean> data) {
        super(layoutResId, data);
        mPatOrderBean = data;
    }

    public PatOrderAdapter(@Nullable List<PatOrderBean> data) {
        super(data);
        mPatOrderBean = data;
    }

    public PatOrderAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    public void setNewData(@Nullable List<PatOrderBean> data) {
        super.setNewData(data);
        mPatOrderBean = data;
    }

    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }

    @Override
    public int getItemViewType(int position) {
        if (mPatOrderBean == null || mPatOrderBean.size() == 0) return 0;

        if (mPatOrderBean.get(position).getEXEC_DATETIME() == null) {
            return 1;
        } else if (mPatOrderBean.get(position).getEXEC_DATETIME() != null && mPatOrderBean.get(position).getSYNC_STATUS() != 1) {
            return -1;
        } else if (mPatOrderBean.get(position).getEXEC_DATETIME() != null && mPatOrderBean.get(position).getSYNC_STATUS() == 1) {
            return 0;
        } else {
            return 0;
        }
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, @Nullable PatOrderBean item) {
        if (item == null) return;
        String strOrder = null;
        String[] strTmpOrdertext = item.getORDER_TEXT().split(";");
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        StringBuilder sbOrdertext = new StringBuilder();

        for (int i = 0; i < strTmpOrdertext.length; i++) {
            String[] strOrderInfo = strTmpOrdertext[i].split("\\|", -1);
            for (int j = 0; j < strOrderInfo.length; j++) {
                if (strOrderInfo[j].isEmpty()) {
                    strOrderInfo[j] = "";
                }
            }

            //只有一条医嘱
            if (i == 0) {
                if (i == strTmpOrdertext.length - 1) {
                    strOrderInfo[3] = "      " + strOrderInfo[3];
                } else {
                    strOrderInfo[3] = "┌   " + strOrderInfo[3];
                }
            }

            //多条医嘱 子医嘱
            if (i > 0) {
                if (i == strTmpOrdertext.length - 1) {
                    strOrderInfo[3] = "└   " + strOrderInfo[3];
                } else {
                    strOrderInfo[3] = "│   " + strOrderInfo[3];
                }
            }


            if (!strOrderInfo[4].isEmpty()) {
                Log.d(TAG, "convert: " + strOrderInfo[4]);
                if (strOrderInfo[4].lastIndexOf(".") > 0) {
                    String strTemp = strOrderInfo[4].substring(strOrderInfo[4].lastIndexOf(".") + 1);
                    if (Integer.valueOf(strTemp) == 0) {
                        strOrderInfo[4] = strOrderInfo[4].substring(0, strOrderInfo[4].lastIndexOf("."));
                    } else {
                        strOrderInfo[4] = strTemp;
                    }
                    Log.d(TAG, "convert: " + strTemp);

                }
            }


            Log.d(TAG, "convert: " + i + "   " + strTmpOrdertext.length);
            if (i == strTmpOrdertext.length - 1) {
                sbOrdertext.append("   " + strOrderInfo[3] + " " + strOrderInfo[4] + strOrderInfo[5]);
            } else {
                sbOrdertext.append("   " + strOrderInfo[3] + " " + strOrderInfo[4] + strOrderInfo[5] + "\n");
            }
        }


        //医嘱类型
        TextView tvrepeatOrtemp = helper.getView(R.id.tv_orderrepeatindicator);
        if (item.getREPEAT_INDICATOR().equals(0)) {
            tvrepeatOrtemp.setText("临");
            tvrepeatOrtemp.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.textview_order_temp));
        } else {
            tvrepeatOrtemp.setText("长");
            tvrepeatOrtemp.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.textview_order_repeat));
        }

        helper.setGone(R.id.ll_memo, item.getFREQ_DETAIL() != null);

        //医嘱内容
        helper.setText(R.id.tv_ordertext, sbOrdertext.toString())
                .setText(R.id.tv_orderno, "No." + item.getORDER_NO().toString())
                .setText(R.id.tv_orderadministration, item.getADMINISTRATION())
                .setText(R.id.tv_orderstartdate, item.getSTART_DATE_TIME())
                .setText(R.id.tv_orderfreq, item.getFREQUENCY())
                .setText(R.id.tv_doctor, item.getDOCTOR())
                .setText(R.id.tv_plandate, item.getPLAN_EXEC_DATETIME())
                .setText(R.id.tv_freqdetail, item.getFREQ_DETAIL());


        //更改执行和未执行颜色     //如果还未执行修改执行时间的底色与汉字
        TextView tvPerformdate = helper.getView(R.id.tv_execdate);
        if (item.getEXEC_DATETIME() == null || item.getEXEC_DATETIME().isEmpty()) {
            tvPerformdate.setTextColor(mContext.getResources().getColor(R.color.white));
            tvPerformdate.setText("未执行");
            tvPerformdate.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_noexecute_round));
//            helper.setTextColor(R.id.tv_ordertext, mContext.getResources().getColor(R.color.noexecute_color));
        } else {
            tvPerformdate.setTextColor(mContext.getResources().getColor(R.color.white));
            tvPerformdate.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_execute_round));
            helper.setText(R.id.tv_execdate, item.getEXEC_DATETIME() + "\n" + item.getEXEC_OPERATOR());
//                    .setTextColor(R.id.tv_ordertext, mContext.getResources().getColor(R.color.execute_color));
        }

    }

}
