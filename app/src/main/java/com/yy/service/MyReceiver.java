package com.yy.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yy.cookies.Statics;
import com.yy.keyapp.module.login.LoginActivity;
import com.yy.until.ExitManager;

public class MyReceiver extends BroadcastReceiver {
	long time = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent != null && Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
			long l = System.currentTimeMillis();
			time = l;
			System.out.println("屏幕已锁" + l);
		} else if (intent != null
				&& Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
			long y = System.currentTimeMillis();
			long x = (y - time) / 1000;
			System.out.println("屏幕已亮" + y + "now time" + x + "秒");
			System.out.println("Login-------------------" + x + "........."
					+ Statics.timeout);
			System.out.println(Integer.parseInt(x+"") > Integer.parseInt(Statics.timeout+""));
			if (Integer.parseInt(x+"") > Integer.parseInt(Statics.timeout+"")) {
				System.out.println("Login-------------------" + x + "........."
						+ Statics.timeout);
				context.startActivity(new Intent(context, LoginActivity.class));
				ExitManager.getInstance().exit();

			}

		}
	}

}
