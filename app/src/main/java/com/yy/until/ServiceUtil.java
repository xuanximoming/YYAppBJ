package com.yy.until;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class ServiceUtil {
	/**
     * 用来判断服务是否后台运行
     * @param mContext //
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext,String className) {
        boolean IsRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE); 
        List<ActivityManager.RunningServiceInfo> serviceList   = activityManager.getRunningServices(30);
       if (!(serviceList.size()>0)) {
            return false;
        }
        for (int i=0; i<serviceList.size(); i++) {
        	System.out.println("serviceList.get(i).service.getClassName()"+serviceList.get(i).service.getClassName());
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                IsRunning = true;
                break;
            }
        }
        return IsRunning ;
    }
}
