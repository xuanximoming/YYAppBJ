package com.yy.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.yy.app.newapp.MainTabActivity;
import com.yy.app.R;
import com.yy.cookies.Statics;
import com.yy.entity.Messages;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

import java.util.List;

public class MyService extends Service {

    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("service已经启动--------------");
        MyUntils.getUersInfo(this);
        ThreadPoolManager.getInstance().addTask(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    while (true) {
                        DownlaodCMC();
                        Thread.sleep(10000);
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    public static int i = 0;

    void DownlaodCMC() {
        List<Messages> newMsgList = MyUntils.getNewMsgList(Statics.user
                .getUid());
        NotificationManager nManager = (NotificationManager) MyService.this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (newMsgList != null && newMsgList.size() > 0) {
            i++;
            // 收到新的未读消息，发送Notification
            Intent aIntent = new Intent(MyService.this, MainTabActivity.class);
            aIntent.putExtra("msg", true);
            PendingIntent pIntent = PendingIntent.getActivity(MyService.this,
                    0, aIntent, 0);
            if (i == 1) {
                // 发通知
                Notification nf = new Notification();
                // 设置属性
                nf.icon = R.drawable.icon; // 图标
                nf.tickerText = "最新消息"; // 图标旁文字
                nf.when = System.currentTimeMillis(); // 通知发送时间，大都使用
                nf.flags |= Notification.FLAG_NO_CLEAR; // 通知不能清除
                nf.flags |= Notification.FLAG_AUTO_CANCEL; // 通知查看后，图标消失
                nf.defaults |= Notification.DEFAULT_SOUND;
                nf.defaults |= Notification.DEFAULT_VIBRATE;
                // 设置方法，用来设置通知滑下后的显示内容和单击动作
                nf.setLatestEventInfo(MyService.this, "有新消息",
                        "消息中心", pIntent);
                nManager.notify(9527, nf);
            }
            MainTabActivity.handler.sendEmptyMessage(101);
        }else {
            nManager.cancel(9527);
        }
    }

}
