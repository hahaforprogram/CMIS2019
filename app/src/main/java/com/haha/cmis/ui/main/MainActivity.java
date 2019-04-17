package com.haha.cmis.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.haha.cmis.R;
import com.haha.cmis.base.BaseActivity;
import com.haha.cmis.constant.AppCookie;

import com.haha.cmis.dbhelper.SyncDataHelper;
import com.haha.cmis.ui.home.DeptDynamicsFragment;
import com.haha.cmis.ui.home.HomeFragment;
import com.haha.cmis.ui.mainhome.InjectFragment;
import com.haha.cmis.ui.mainhome.SetupFragment;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

import static android.support.v4.view.ViewPager.*;

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    @BindView(R.id.main_navigation)
    BottomNavigationView mainNavigation;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected Boolean getIsFullScreen() {
        return false;
    }

    @Override
    protected Boolean getIsShowTitle() {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Log.d(TAG, "initView: ");
        //初始化toolbar菜单
        initToolBar(mainToolbar, false, AppCookie.getDeptname(), AppCookie.getUsername() + "[" + AppCookie.getUserid() + "]");
    }

    /**
     * 初始化 Toolbar
     */
    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title, String subtitle) {
        toolbar.setTitle(title);
        toolbar.setSubtitle(subtitle);
        toolbar.setNavigationIcon(null);
        toolbar.setLogo(R.drawable.ic_nurse);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.toolbar_title));
        toolbar.setSubtitleTextColor(ContextCompat.getColor(this, R.color.toolbar_subtitle));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    @Override
    protected void initData() {
        com.haha.cmis.ui.domain.adapter.MainActivityAdapter mainActivityAdapter;
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new DeptDynamicsFragment());
        fragments.add(new HomeFragment());
        fragments.add(new InjectFragment());
        fragments.add(new SetupFragment());
        mainActivityAdapter = new com.haha.cmis.ui.domain.adapter.MainActivityAdapter(getSupportFragmentManager(), fragments);
        mainViewpager.setAdapter(mainActivityAdapter);
        mainViewpager.setOffscreenPageLimit(1);  //预加载剩下两页
//        点击底部导航
        mainNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_deptdynamics:
                    mainViewpager.setCurrentItem(0);
                    break;
                case R.id.navigation_home:
                    mainViewpager.setCurrentItem(1);
                    break;
                case R.id.navigation_inject:
                    mainViewpager.setCurrentItem(2);
                    break;
                case R.id.navigation_setup:
                    mainViewpager.setCurrentItem(3);
            }
            return false;
        });
        //页面变化时的监听器
        mainViewpager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mainNavigation.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected Boolean getIsScanBar() {
        return false;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_sync:
                syncData();

                break;
        }
        return true;
    }

    @SuppressLint("CheckResult")
    private void syncData() {
        infoShow("Info", "同步数据中...");
        Observable<Boolean> userinfo = SyncDataHelper.getInstance().getUserInfo();
        Observable<Boolean> patsinfo = SyncDataHelper.getInstance().getPatsInfo();
        Observable<Boolean> patsorder = SyncDataHelper.getInstance().getPatsOrder();
        Observable.zip(userinfo, patsinfo, patsorder, (Function3<Boolean, Boolean, Boolean, Object>) (aBoolean, aBoolean2, aBoolean3) -> aBoolean && aBoolean2 && aBoolean3)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    infoShow("INfo", "同步成功");
                }, throwable -> {
                    infoShow("INfo", "同步失败" + throwable.getMessage());
                });
    }

}
