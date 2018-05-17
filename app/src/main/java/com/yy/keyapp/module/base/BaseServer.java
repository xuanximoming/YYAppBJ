package com.yy.keyapp.module.base;

import android.content.IntentFilter;

/**
 * Created by key on 2017/10/13.
 */
public class BaseServer {
    /**
     * 用于使用专业扫码设备，注册服务用方法
     * @return IntentFilter
     */
    public IntentFilter RegisterBoradcastReceiver() {
            IntentFilter myIntentFilter = new IntentFilter();
            /**
             * 扫码设备接口方法
             */
            myIntentFilter.addAction("com.ge.action.barscan");
            return myIntentFilter;
    }



}
