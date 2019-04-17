package com.haha.cmis.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haha.cmis.R;

import java.util.List;

/**
 * Created by hah on 2017/11/17.
 */

public class PatMainAdapter extends BaseMultiItemQuickAdapter<PatMainEntity, BaseViewHolder> {


    public PatMainAdapter(List<PatMainEntity> data) {
        super(data);

        addItemType(1, R.layout.item_patmain_listtype1);
        addItemType(2, R.layout.item_patmain_listtype2);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatMainEntity item) {
        switch (helper.getItemViewType()) {
            case 1:
                helper.setText(R.id.tv_functionname, item.getItemTitle())
                        .setImageResource(R.id.iv_functionimg, item.getItemImageResource());
                break;
            case 2:
                helper.setText(R.id.tv_functionname, item.getItemTitle())
                        .setImageResource(R.id.iv_functionimg, item.getItemImageResource());
                ;
                break;
            default:
                helper.setText(R.id.tv_functionname, item.getItemTitle())
                        .setImageResource(R.id.iv_functionimg, item.getItemImageResource());
                break;
        }

    }


    @Override
    public void setOnItemClick(View v, int position) {
        super.setOnItemClick(v, position);
    }
}
