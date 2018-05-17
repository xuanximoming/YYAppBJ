package com.yy.until;

import android.content.Context;
import android.content.pm.PackageManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ukey on 2015/11/6.
 * Email:yeshentianyue@sina.com
 */
public class VersionUtils {

    public static String getVersion(Context context) {
        String version;
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            version = "UnknownVersion";
        }
        return version;
    }
    public static String getVersionCode(Context context) {
        String version;
        try {
            version = String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String  str = formatter.format(curDate);
            version = str + "01";
        }
        return version;
    }

}
