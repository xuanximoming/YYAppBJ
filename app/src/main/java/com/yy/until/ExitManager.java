package com.yy.until;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;

public class ExitManager extends Application {
	public static List<Activity> activityList = new LinkedList<Activity>();
	public static ExitManager instance;

	private ExitManager() {

	}

	public static ExitManager getInstance() {
		if (instance == null) {
			instance = new ExitManager();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public void exit() {
	
		for (Activity activity : activityList) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}

//		 int id = android.os.Process.myPid();
//		 if (id != 0) {
//		 android.os.Process.killProcess(id);
//		 }
	}
}
