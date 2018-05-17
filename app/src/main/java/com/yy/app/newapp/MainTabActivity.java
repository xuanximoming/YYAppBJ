package com.yy.app.newapp;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.yy.app.R;
import com.yy.app.SettingActivity;
import com.yy.keyapp.module.patient.NPatientlistActivity;
import com.yy.service.MyReceiver;
import com.yy.service.MyService;
import com.yy.until.ExitManager;
import com.yy.until.MyUntils;
import com.yy.view.BadgeView;

public class MainTabActivity extends TabActivity {
    private TabHost mth;
    private Button button1;
    public static RadioGroup radioGroup;
    public static MyReceiver receiver;

    boolean booleanExtra;

    public static final String TAB_MESSAGE = "消息";
    public static final String TAB_PATIENT = "病人";
    public static final String TAB_SETTING = "设置";

    public static BadgeView badge1;
    private boolean isregister = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintab);
        init();
    }

    private void init(){
        // 启动广播接收
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        isregister = true;
        this.registerReceiver(receiver, filter);

        ExitManager.getInstance().addActivity(this);
        MyUntils.getUersInfo(this);
        button1 = (Button) findViewById(R.id.bt);
        badge1 = new BadgeView(this, button1);// 创建一个BadgeView对象，view为你需要显示提醒的控件
        booleanExtra = this.getIntent().getBooleanExtra("msg", false);

        mth = getTabHost();
        Button exit = (Button) findViewById(R.id.main_tab_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        MainTabActivity.this);
                alertDialog.setTitle("退出提示");
                alertDialog.setMessage("确定退出吗？");
                alertDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                NotificationManager notificationManager =
                                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.cancel(0);
                                MyService.i = 0;
                                ExitManager.getInstance().exit();
                            }
                        });
                alertDialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                radioGroup.check(R.id.main_tab_patient);
                                return;
                            }
                        });
                alertDialog.show();
            }
        });
        // 病人
        TabHost.TabSpec ts1 = mth.newTabSpec(TAB_PATIENT).setIndicator(TAB_PATIENT);
        ts1.setContent(new Intent(MainTabActivity.this, NPatientlistActivity.class));
        mth.addTab(ts1);
        // 消息
        TabHost.TabSpec ts2 = mth.newTabSpec(TAB_MESSAGE).setIndicator(TAB_MESSAGE);
        ts2.setContent(new Intent(MainTabActivity.this, MSGListActivity.class));
        mth.addTab(ts2);
        // 设置
        TabHost.TabSpec ts3 = mth.newTabSpec(TAB_SETTING).setIndicator(TAB_SETTING);
        ts3.setContent(new Intent(MainTabActivity.this, SettingActivity.class));
        mth.addTab(ts3);
        radioGroup = (RadioGroup) findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.main_tab_message:
                        mth.setCurrentTabByTag(TAB_MESSAGE);
                        badge1.hide();// 影藏显示
                        break;
                    case R.id.main_tab_patient:
                        mth.setCurrentTabByTag(TAB_PATIENT);
                        break;
                    case R.id.main_tab_setting:
                        mth.setCurrentTabByTag(TAB_SETTING);
                        break;
                }
            }
        });
        if (booleanExtra) {
            radioGroup.check(R.id.main_tab_message);
            MyService.i = 0;
        } else {
            radioGroup.check(R.id.main_tab_patient);
        }
    }

    public static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 101:
                    showBadgeView();
                    break;

                default:
                    break;
            }
        }
    };

    // BadgeView的具体使用
    public static void showBadgeView() {
        // 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badge1.setTextColor(Color.WHITE); // 文本颜色
        badge1.setTextSize(5); // 文本大小
        badge1.setHeight(15);
        badge1.setWidth(15);
        badge1.show();// 只有显示
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (isregister) {
            try {
                unregisterReceiver(receiver);

            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("key notice:com.yy.app.newapp.MainTabActivity.onDestroy----" + e.getMessage());
            }
        }
        // 创建Intent
        Intent intent = new Intent();
        // 设置Class属性
        intent.setClass(this, MyService.class);
        // 启动该Service
        stopService(intent);
    }
}
