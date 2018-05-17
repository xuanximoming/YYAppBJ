package com.yy.keyapp.module.base;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yy.cookies.Statics;

import wang.jack.update.library.HandyUpdate;
import wang.jack.update.library.UpdateInfo;

/**
 * Created by key on 2017/9/28.
 */
public class checkVersionUpdate {
    /**
     * 通过HandyUpdate来更新
     */
    public void checkVersionForUpdate(Context context) {
        HandyUpdate.setCustomParseListener(new HandyUpdate.UpdateParseListener() {
            @Override
            public UpdateInfo getUpdateInfo(String s) {
                try {
                    Log.d("HandyUpdate", "result=" + s);
                    UpdateInfo updateInfo = new Gson().fromJson(s, UpdateInfo.class);
                    updateInfo.apkUrl = "http://" + Statics.IP + ":" + Statics.Port + updateInfo.apkUrl;
                    Log.d("HandyUpdate", "apkUrl=" + updateInfo.apkUrl);
                    return updateInfo;
                } catch (Exception e) {
                }
                return null;
            }
        });
        HandyUpdate.update(context, "http://" + Statics.IP + ":" + Statics.Port + "/" + Statics.VersionURL);
    }
}
