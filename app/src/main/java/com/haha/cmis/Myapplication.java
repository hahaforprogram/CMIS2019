package com.haha.cmis;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.haha.cmis.constant.AppConfig;
import com.haha.cmis.constant.AppCookie;
import com.haha.cmis.utils.DeviceInfoHelper;
import com.haha.cmis.utils.FileHelper;
import com.haha.cmis.utils.PreferenceUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * @author hahame  @date 2018/12/28
 */
public class Myapplication extends Application {

    private static Myapplication instance = null;
    private static final String TAG = "Myapplication";

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.toolbar_backgroud, android.R.color.white);//全局设置主题颜色
            return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });

//        //设置全局的Footer构建器
//        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
//            //指定为经典Footer，默认是 BallPulseFooter
//            return new ClassicsFooter(context).setDrawableSize(20);
//        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        PreferenceUtil.init(this, getPackageName());
        initApp();
    }

    public static Myapplication getInstance() {
        if (instance == null) {
            synchronized (Myapplication.class) {
                if (instance == null) {
                    instance = new Myapplication();
                }
            }
        }
        return instance;
    }

    private void initApp() {
        Log.d(TAG, "initApp: ");
        String strPath;
        /**创建相关目录 设置数据库路径 */
        strPath = getPackageName() + "/" + AppConfig.appSetting.dirDatabase;
        AppCookie.setDataBabasePath(FileHelper.createDirectory(strPath));
        /**设置病人头像存储路径*/
        AppCookie.setPatImgPath(FileHelper.createDirectory(getPackageName() + "/" + AppConfig.appSetting.dirPatImg));
        /**设置app 日志 路径*/
        AppCookie.setApplogPath(FileHelper.createDirectory(getPackageName() + "/" + AppConfig.appSetting.dirSoresImg));
        /**设置其他图片存储路径*/
        AppCookie.setApplogPath(FileHelper.createDirectory(getPackageName() + "/" + AppConfig.appSetting.dirApplog));
        /**设置设备mac*/
        AppCookie.setDeviceId(DeviceInfoHelper.getDeviceInfo());
    }


}
