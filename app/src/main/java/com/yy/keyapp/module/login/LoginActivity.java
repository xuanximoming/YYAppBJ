package com.yy.keyapp.module.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.yy.app.R;
import com.yy.app.newapp.MainTabActivity;
import com.yy.app.newapp.SysSettingActivity;
import com.yy.cookies.SharedPrefs;
import com.yy.cookies.Statics;
import com.yy.entity.User;
import com.yy.keyapp.module.base.checkVersionUpdate;
import com.yy.service.MyService;
import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;
import com.yy.until.StrTool;
import com.yy.until.ThreadPoolManager;

public class LoginActivity extends Activity implements OnClickListener {
    private Button login_bt;
    private Button set_bt;
    private EditText login_name;
    private EditText login_pazz;
    private CheckBox mRememberPWDCheck;
    private User dologin;
    String username, password;

    //是否记住密码
    private boolean isRememberPWD = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyUntils.getUersInfo(this);
        if (!HttpUtil.checkNet(this)) {
            Statics.internetcode = 0;
            int code = Statics.internetcode;
            MyUntils.myToast(this, Statics.internetstate[code]);
        }
        initview();
    }

    void initview() {

        login_bt = (Button) findViewById(R.id.login_bt);
        login_bt.setOnClickListener(this);
        set_bt = (Button) findViewById(R.id.set_bt);
        set_bt.setOnClickListener(this);
        login_name = (EditText) findViewById(R.id.login_name);
        login_pazz = (EditText) findViewById(R.id.login_pazz);
        mRememberPWDCheck = (CheckBox) findViewById(R.id.isRememberPwd);
        username = SharedPrefs.readUserName(LoginActivity.this);
        if (!"".equals(username) && null != username) {
            login_name.setText(username);
        }
        //是否勾选记住密码
        isRememberPWD = SharedPrefs.readRem(this);
        if (isRememberPWD) {
            password = SharedPrefs.readPassword(this);
            login_pazz.setText(password);
        }
        mRememberPWDCheck.setChecked(isRememberPWD);
        // 检查服务器版本是否需要更新
        checkVersionUpdate checkVersionUpdate = new checkVersionUpdate();
        checkVersionUpdate.checkVersionForUpdate(LoginActivity.this);
    }

    private Handler myhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 101:
                    MyUntils.cancleProgress();
                    if (dologin == null) {
                        if (Statics.internetcode != -1) {
                            MyUntils.myToast(LoginActivity.this,
                                    Statics.internetstate[Statics.internetcode]);
                        } else {
                            MyUntils.myToast(LoginActivity.this, "用户名或者密码错误");
                        }
                    } else {
                        isRememberPWD = mRememberPWDCheck.isChecked();
                        Statics.UNAME = username;
                        SharedPrefs.writeUersName(LoginActivity.this, username);
                        SharedPrefs.writePassword(LoginActivity.this, password);
                        SharedPrefs.writeRem(LoginActivity.this, isRememberPWD);
                        SharedPrefs.writeHuShiName(LoginActivity.this,
                                dologin.getUtname());
                        SharedPrefs.writeKeshi(LoginActivity.this,
                                dologin.getKeshi());
                        SharedPrefs.writeUserID(LoginActivity.this,
                                dologin.getUid());
                        MyUntils.getUersInfo(LoginActivity.this);
                        startActivity(new Intent(LoginActivity.this,
                                MainTabActivity.class));
                        // 开启服务获取信息
                        startService(new Intent(LoginActivity.this,
                                MyService.class));
                        finish();
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };

    void dologin() {
        MyUntils.showProgress(this, "正在登录");
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                dologin = MyUntils.dologin(username, password);
                myhandler.sendEmptyMessage(101);
            }
        });
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login_bt:
                username = login_name.getText().toString().trim();
                password = login_pazz.getText().toString().trim();
                if (!HttpUtil.checkNet(this)) {
                    Statics.internetcode = 0;
                    int code = Statics.internetcode;
                    MyUntils.myToast(this, Statics.internetstate[code]);
                    break;
                }
                if (StrTool.isnull(username) || StrTool.isnull(password)) {
                    MyUntils.myToast(this, "登录信息不完整");
                } else {
                    dologin();
                }
                break;
            case R.id.set_bt:
                startActivity(new Intent(this, SysSettingActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        System.out.println("ip..." + Statics.IP + "...port..." + Statics.Port
                + ".....api...." + Statics.API);
    }
}
