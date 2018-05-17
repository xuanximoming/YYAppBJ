package com.yy.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yy.app.newapp.SysSettingActivity;
import com.yy.cookies.Statics;
import com.yy.entity.Messages;
import com.yy.keyapp.module.aboult.versionactivity;
import com.yy.until.ExitManager;
import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;
import com.yy.until.VersionUtils;

public class SettingActivity extends Activity implements OnClickListener {
    private int msgid;
    private TextView msgdes_title;
    private TextView msgdes_time;
    private TextView msgdes_content;
    private TextView setting_banbenhao;
    private RelativeLayout layout_accountmanager;
    private RelativeLayout layout_syssetting;
    private RelativeLayout layout_banbenxx;
    private RelativeLayout layout_banquanxx;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ExitManager.getInstance().addActivity(this);
        MyUntils.getUersInfo(this);
        initview();
    }

    void initview() {
        TextView titlename = (TextView) findViewById(R.id.activity_title_name);
        titlename.setText("设置");
        TextView setting_banbenhao = (TextView) findViewById(R.id.setting_banbenhao);
        setting_banbenhao.setText("V" + VersionUtils.getVersion(this));
        Button back = (Button) findViewById(R.id.activity_title_back);
        back.setVisibility(View.GONE);
        back.setOnClickListener(this);
        Button ok = (Button) findViewById(R.id.activity_title_ok);
        ok.setVisibility(View.GONE);

        layout_accountmanager = (RelativeLayout) findViewById(R.id.layout_accountmanage);
        layout_accountmanager.setOnClickListener(this);
        layout_syssetting = (RelativeLayout) findViewById(R.id.layout_syssetting);
        layout_syssetting.setOnClickListener(this);
        layout_banbenxx = (RelativeLayout) findViewById(R.id.layout_banbenxx);
        layout_banbenxx.setOnClickListener(this);
        layout_banquanxx = (RelativeLayout) findViewById(R.id.layout_banquanxx);
        layout_banquanxx.setOnClickListener(this);

    }

    private Handler myhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 101:
                    if (msgdesc == null) {
                        if (Statics.internetcode != -1) {
                            MyUntils.myToast(SettingActivity.this,
                                    Statics.internetstate[Statics.internetcode]);
                        }
                    } else {
                        msgdes_title.setText(msgdesc.getMsgTitle());
                        msgdes_time.setText(msgdesc.getMsgTime());
                        msgdes_content.setText(msgdesc.getMsgMemo());
                    }
                    // else {
                    // MyUntils.myToast(SettingActivity.this, "获取失败");
                    // }
                    break;
                default:
                    break;
            }
        }
    };

    private Messages msgdesc;

    void getmsgdesc() {
        if (!HttpUtil.checkNet(this)) {
            Statics.internetcode = 0;
            int code = Statics.internetcode;
            MyUntils.myToast(this, Statics.internetstate[code]);
            return;
        }
        ThreadPoolManager.getInstance().addTask(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                msgdesc = MyUntils.getMsgDesc(Statics.user.getUid(), msgid);
                myhandler.sendEmptyMessage(101);
            }
        });
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            // 护士账户管理
            case R.id.layout_accountmanage:
                startActivity(new Intent(this, AccountManagerActivity.class));
                break;
            // 系统设置
            case R.id.layout_syssetting:
                startActivity(new Intent(this, SysSettingActivity.class));
                break;
            // 版权信息
            case R.id.layout_banquanxx:
                startActivity(new Intent(this, BanQuanActivity.class));
                break;
            // 版本信息
            case R.id.layout_banbenxx:
//                 new MyAutoUpdate(SettingActivity.this).check();
//			ThreadPoolManager.getInstance().addTask(new Runnable() {
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					version = MyUntils.getVersion(getVersionName());
//					myhandler.sendEmptyMessage(102);
//				}
//			});
                startActivity(new Intent(SettingActivity.this, versionactivity.class));
//                HandyUpdate.update(SettingActivity.this, "http://" + Statics.IP + ":" + Statics.Port + "/checkversion.do", new UpdateParam.Builder(SettingActivity.this)
//                        .setUpdatePrompt(true)
//                        .build());
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 3500) {
                Toast.makeText(this, "在按一次退出", Toast.LENGTH_LONG).show();
                mExitTime = System.currentTimeMillis();
            } else {
                ExitManager.getInstance().exit();
            }
            return true;
        }
        // 拦截MENU按钮点击事件，让他无任何操作
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
