package com.wbw.iloveyou;

import android.app.Application;

import com.wbw.iloveyou.bean.Info;

import cn.bmob.v3.Bmob;

/**
 * Create by Zhangty on 2018/8/13
 */
public class LoveApplication extends Application {

    public static Info info;
    public static  LoveApplication application;
    @Override
    public void onCreate() {
        super.onCreate();        //第一：默认初始化
        application = this;
        Bmob.initialize(this, "a9f641c96900600487a95fd0097b04f1");
    }

    public static LoveApplication getApplication() {
        return application;
    }

    public static void setApplication(LoveApplication application) {
        LoveApplication.application = application;
    }
}
