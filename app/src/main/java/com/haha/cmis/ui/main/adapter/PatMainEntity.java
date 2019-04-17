package com.haha.cmis.ui.main.adapter;


import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author hahame  @date 2019/2/12
 */
public class PatMainEntity implements MultiItemEntity {
    public int itemtype;
    public String itemTitle;
    public int itemImageResource;
    public String itemView;
    public String itemInput;
    private int spanSize;


    public PatMainEntity(int itemtype, String itemTitle, int itemImageResource, int spanSize) {
        this.itemtype = itemtype;
        this.itemTitle = itemTitle;
        this.itemImageResource = itemImageResource;
        this.spanSize = spanSize;
    }

    @Override
    public int getItemType() {
        return itemtype;
    }


    public int getItemtype() {
        return itemtype;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public int getItemImageResource() {
        return itemImageResource;
    }

    public String getItemView() {
        return itemView;
    }

    public String getItemInput() {
        return itemInput;
    }

    public int getSpanSize() {
        return spanSize;
    }


}
