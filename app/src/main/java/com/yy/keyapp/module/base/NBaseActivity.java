package com.yy.keyapp.module.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.yy.until.ExitManager;

/**
 * Created by Ukey on 2016/1/18.
 * Email:yeshentianyue@sina.com
 */
public abstract class NBaseActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitManager.getInstance().addActivity(this);
    }

    public Context getActivity(){
        return this;
    }

}
