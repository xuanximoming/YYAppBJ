package com.yy.cookies;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
	public static final String USERNAME = "username";
	public static final String NAME = "name";
	public static final String KESHI = "keshi";
	public static final String KESHISTATE = "userkeshi";
	public static final String REMEMBER = "remember";
	public static final String REMEMBER_STATE = "rem_state";

	// 记住用户名
	public static void writeUersName(Context context, String name) {
		SharedPreferences prefs = context.getSharedPreferences(USERNAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(NAME, name);
		editor.commit();
	}

	// 读取用户名
	public static String readUserName(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(USERNAME,
				Context.MODE_PRIVATE);
		return prefs.getString(NAME, null);
	}

	// 记住科室
	public static void writeKeshi(Context context, String pass) {
		SharedPreferences prefs = context.getSharedPreferences(KESHI,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(KESHISTATE, pass);
		editor.commit();
	}

	// 读取科室
	public static String readKeshi(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KESHI,
				Context.MODE_PRIVATE);
		return prefs.getString(KESHISTATE, null);
	}

	// 记住用户ID
	public static void writeUserID(Context context, String userid) {
		SharedPreferences prefs = context.getSharedPreferences("userid",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("userid_state", userid);
		editor.commit();
	}

	// 读取用户ID
	public static String readUserId(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("userid",
				Context.MODE_PRIVATE);
		return prefs.getString("userid_state", null);
	}

	// 记住护士姓名
	public static void writeHuShiName(Context context, String userid) {
		SharedPreferences prefs = context.getSharedPreferences("hushiname",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("hushiname_state", userid);
		editor.commit();
	}

	// 读取护士姓名
	public static String readHuShiName(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("hushiname",
				Context.MODE_PRIVATE);
		return prefs.getString("hushiname_state", null);
	}

	// 记录“记住密码”的状态
	public static void writeRem(Context context, boolean f) {
		SharedPreferences prefs = context.getSharedPreferences(REMEMBER,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(REMEMBER_STATE, f);
		editor.commit();
	}

	// 读取“记住密码”的状态
	public static Boolean readRem(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(REMEMBER,
				Context.MODE_PRIVATE);
		return prefs.getBoolean(REMEMBER_STATE, false);
	}

	// 记住IP
	public static void writeIP(Context context, String IP) {
		SharedPreferences prefs = context.getSharedPreferences("IP",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("IP_STATE", IP);
		editor.commit();
	}

	// 读取IP
	public static String readIP(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("IP",
				Context.MODE_PRIVATE);
		return prefs.getString("IP_STATE", null);
	}

	// 记住DuanKou
	public static void writeDuanKou(Context context, String DuanKou) {
		SharedPreferences prefs = context.getSharedPreferences("DuanKou",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("DuanKou_STATE", DuanKou);
		editor.commit();
	}

	// 读取DuanKou
	public static String readDuanKou(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("DuanKou",
				Context.MODE_PRIVATE);
		return prefs.getString("DuanKou_STATE", null);
	}

	// 记住ChuanMa
	public static void writeChuanMa(Context context, String ChuanMa) {
		SharedPreferences prefs = context.getSharedPreferences("ChuanMa",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("ChuanMa_STATE", ChuanMa);
		editor.commit();
	}

	// 读取ChuanMa
	public static String readChuanMa(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("ChuanMa",
				Context.MODE_PRIVATE);
		return prefs.getString("ChuanMa_STATE", null);
	}

    //保存密码
    public static void writePassword(Context context, String password) {
        SharedPreferences prefs = context.getSharedPreferences("login_password",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("password", password);
        editor.commit();
    }

    //读取密码
    public static String readPassword(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("login_password",
                Context.MODE_PRIVATE);
        return prefs.getString("password", null);
    }

}
