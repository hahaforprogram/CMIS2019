package com.haha.cmis.ui.domain.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hah on 2018/3/29.
 */

public class MainActivityAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();

    public MainActivityAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainActivityAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.fragments = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}
