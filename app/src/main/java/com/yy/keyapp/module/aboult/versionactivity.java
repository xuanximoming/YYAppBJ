package com.yy.keyapp.module.aboult;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yy.app.R;
import com.yy.keyapp.module.base.NBaseActivity;
import com.yy.keyapp.module.base.checkVersionUpdate;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;
import com.yy.until.VersionUtils;

/**
 * Created by key on 2017/9/28.
 */
public class versionactivity extends NBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        initview();
    }

    void initview() {
        TextView activity_title = (TextView)findViewById(R.id.activity_title_name);
        activity_title.setText("版本信息");
        TextView tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText("V" + VersionUtils.getVersion(this)
                + "(" + VersionUtils.getVersionCode(this) + ")");
        Button bt_version = (Button) findViewById(R.id.bt_checkversion);
        Button bt_back = (Button)findViewById(R.id.activity_title_back);
        bt_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_version.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadPoolManager.getInstance().addTask(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (MyUntils.getVersion(VersionUtils.getVersionCode(getActivity()))) {
                            checkVersionUpdate checkVersionUpdate = new checkVersionUpdate();
                            checkVersionUpdate.checkVersionForUpdate(getActivity());
                        } else {
                            myhandler.sendEmptyMessage(101);
                        }
                    }
                });
            }
        });
    }

    private Handler myhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 101://检测更新发送
                    Toast.makeText(getActivity(),"已经是最新版本！",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };
}
