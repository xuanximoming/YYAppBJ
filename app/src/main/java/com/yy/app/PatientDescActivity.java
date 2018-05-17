package com.yy.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yy.app.newapp.RYpingguActivity;
import com.yy.app.newapp.YizhuzxdActivity;
import com.yy.cookies.Statics;
import com.yy.entity.Patient;
import com.yy.until.ExitManager;
import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;
import com.yy.until.StrTool;
import com.yy.until.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

public class PatientDescActivity extends Activity implements OnClickListener {
	String bid, vid;
	// int position;
	Button back_name, last, next;
	TextView bq_tv, bc_tv, zhuyi_tv, patient_name, patient_gender,
			patient_age, patient_feibie, patient_hldj, patient_bh;
	LinearLayout ll_rypg, ll_yizhu, ll_yzzxd, ll_mrpinggu, ll_huli, ll_jiaoyu,
			ll_jianyan, ll_jiancha, ll_shoushu, ll_ypgl, ll_jiaojieban;
	Patient data;
	Handler mhandler;
	private LinearLayout patientdes_layout;
	private TextView side_time;
	private TextView item_xuhao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patientdesc);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		Intent intent = getIntent();
		bid = intent.getStringExtra("bid");
		vid = intent.getStringExtra("vid");
		// position = intent.getIntExtra("position", -1);
		init();
	}

	View patientdes_inflate;
	private TextView patient_yue;
	private TextView patient_zhenduan;

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		unregisterReceiver(mBroadcastReceiver);
		super.onPause();
	}

	private void init() {
		// TODO Auto-generated method stub
		back_name = (Button) findViewById(R.id.activity_title_back);
		back_name.setOnClickListener(this);
		last = (Button) findViewById(R.id.activity_title_last);
		last.setOnClickListener(this);
		next = (Button) findViewById(R.id.activity_title_next);
		next.setOnClickListener(this);

		bq_tv = (TextView) findViewById(R.id.patient_dept);
		bc_tv = (TextView) findViewById(R.id.patient_bed);
		zhuyi_tv = (TextView) findViewById(R.id.zhuyi_tv);
		patient_name = (TextView) findViewById(R.id.patient_name);
		patient_gender = (TextView) findViewById(R.id.patient_gender);
		patient_age = (TextView) findViewById(R.id.patient_age);
		patient_feibie = (TextView) findViewById(R.id.side_feibie);
		patient_yue = (TextView) findViewById(R.id.side_yue);
		patient_zhenduan = (TextView) findViewById(R.id.side_zhenduan);
		patient_hldj = (TextView) findViewById(R.id.patient_hldj);
		patient_bh = (TextView) findViewById(R.id.patient_ID);

		side_time = (TextView) findViewById(R.id.side_time);// 入住时间
		item_xuhao = (TextView) findViewById(R.id.Visit_ID);// 住院号
		patientdes_layout = (LinearLayout) findViewById(R.id.patientdes_layout);
		patientdes_inflate = LayoutInflater.from(this).inflate(
				R.layout.item_patientpager, null);
		ll_rypg = (LinearLayout) findViewById(R.id.ll_rypg);
		ll_rypg.setOnClickListener(this);
		ll_yizhu = (LinearLayout) findViewById(R.id.ll_yizhu);
		ll_yizhu.setOnClickListener(this);
		ll_yzzxd = (LinearLayout) findViewById(R.id.ll_yzzxd);
		ll_yzzxd.setOnClickListener(this);
		ll_mrpinggu = (LinearLayout) findViewById(R.id.ll_mrpinggu);
		ll_mrpinggu.setOnClickListener(this);
		ll_huli = (LinearLayout) findViewById(R.id.ll_huli);
		ll_huli.setOnClickListener(this);
		ll_jiaoyu = (LinearLayout) findViewById(R.id.ll_jiaoyu);
		ll_jiaoyu.setOnClickListener(this);
		ll_jianyan = (LinearLayout) findViewById(R.id.ll_jianyan);
		ll_jianyan.setOnClickListener(this);
		ll_jiancha = (LinearLayout) findViewById(R.id.ll_jiancha);
		ll_jiancha.setOnClickListener(this);
		ll_shoushu = (LinearLayout) findViewById(R.id.ll_shoushu);
		ll_shoushu.setOnClickListener(this);
		ll_ypgl = (LinearLayout) findViewById(R.id.ll_ypgl);
		ll_ypgl.setOnClickListener(this);
		ll_jiaojieban = (LinearLayout) findViewById(R.id.ll_jiaojieban);
		ll_jiaojieban.setOnClickListener(this);
		setonTuchListener(patientdes_layout);
		setonTuchListener(ll_rypg);
		setonTuchListener(ll_yizhu);
		setonTuchListener(ll_yzzxd);
		setonTuchListener(ll_mrpinggu);
		setonTuchListener(ll_huli);
		setonTuchListener(ll_jiaoyu);
		setonTuchListener(ll_jianyan);
		setonTuchListener(ll_jiancha);
		setonTuchListener(ll_shoushu);
		setonTuchListener(ll_ypgl);
		setonTuchListener(ll_jiaojieban);
		data = new Patient();
		mhandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 101:
					MyUntils.cancleProgress();
					patientdes_layout.setVisibility(View.GONE);
					if (data == null) {
						System.out.println("病人信息为null");
						if (Statics.internetcode != -1) {
							MyUntils.myToast(PatientDescActivity.this,
									Statics.internetstate[Statics.internetcode]);
						} else if (t != null && t.equals("up")) {
							MyUntils.myToast(PatientDescActivity.this,
									"已是第一位病人");
						} else if (t != null && t.equals("next")) {
							MyUntils.myToast(PatientDescActivity.this,
									"已是最后一位病人");
						}
					} else {
						Statics.patientdesc = data;
						back_name.setText(data.getName());
						bq_tv.setText(data.getQuhao());
						bc_tv.setText(data.getChuanghao());
						zhuyi_tv.setText(data.getZhuyi());
						patient_name.setText(data.getName());
						patient_gender.setText(data.getGender());
						patient_age.setText(data.getAge());
						patient_feibie.setText(data.getFEIBIE());
						patient_yue.setText(data.getFEIYUE());
						patient_zhenduan.setText(data.getZHENDUAN());
						patient_hldj.setText(data.getHulidengji());
						patient_bh.setText(data.getPatient_id());// 编号
						side_time.setText(data.getTime());
						item_xuhao.setText(data.getZy_id());
					}
					break;
				case 102:
					if (null != searchpatientlist
							&& searchpatientlist.size() > 0) {
						Statics.patientdesc = searchpatientlist.get(0);
						back_name.setText(data.getName());
						bq_tv.setText(data.getQuhao());
						bc_tv.setText(data.getChuanghao());
						zhuyi_tv.setText(data.getZhuyi());
						patient_name.setText(data.getName());
						patient_gender.setText(data.getGender());
						patient_age.setText(data.getAge());
						patient_feibie.setText(Statics.user.getKeshi());
						patient_hldj.setText(data.getHulidengji());
						patient_bh.setText(data.getPatient_id());// 编号
						side_time.setText(data.getTime());
						item_xuhao.setText(data.getZy_id());
					}
					break;
				default:
					break;
				}
			}

		};
		Statics.patientdesc = new Patient();
		downlaoddata();

	}

	// 扫描代码

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String text = intent.getStringExtra("value");
			text = StrTool.replaceBlank(text);
			// 调用搜索接口 传入扫描结果 text
			downlaoddata1(text);
		}

	};

	public void registerBoradcastReceiver() {
		IntentFilter barcodeFilter = new IntentFilter("com.ge.action.barscan");
		// 注册广播
		registerReceiver(mBroadcastReceiver, barcodeFilter);
	}

	private List<Patient> searchpatientlist = new ArrayList<Patient>();

	void searchdata(final String key) {
		MyUntils.showProgress(this, "正在搜索病人");
		ThreadPoolManager.getInstance().addTask(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				System.out.println("run---getmyPatient");

				searchpatientlist = MyUntils.Searchlist(Statics.user.getUid(),
						"1", key);
				// if (Statics.searchpatientlist != null) {

				// }
				Message msg = new Message();
				msg.what = 102;
				mhandler.sendMessage(msg);
			}
		});
	}

	void setonTuchListener(View view) {
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				onTouchEvent(event);
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_title_back:
			finish();
			break;
		case R.id.activity_title_last:
			lastPatient();
			break;
		case R.id.activity_title_next:
			nextPatient();
			break;
		case R.id.ll_rypg:
			// String
			// htmlurl = "";
			// String title;
			// Intent rypg = new Intent(this, WebViewActivity.class);
			// rypg.putExtra("htmlurl",
			// "http://1.93.13.73:8080/pg.do?api=1qaz2wsx&bid=2000741906&vid=1");
			// rypg.putExtra("title", "入院评估");
			Intent rypg = new Intent(this, RYpingguActivity.class);
			// String htmlurl = "";
			// rypg.putExtra("htmlurl",
			// "http://1.93.13.73:8080/pg.do?api=1qaz2wsx&bid=2000741906&vid=1");
			// rypg.putExtra("title", "入院评估");
			startActivity(rypg);
			break;
		// 医嘱
		case R.id.ll_yizhu:
			if (Statics.patientdesc == null) {
				break;
			}
			Intent intent = new Intent(this, YizhuActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(intent);
			break;
		case R.id.ll_yzzxd:
			Intent yzzxd = new Intent(this, YizhuzxdActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(yzzxd);
			break;
		case R.id.ll_mrpinggu:
			Intent mrpg = new Intent(this, MRPGSelectDateActivity.class);
			// Intent mrpg = new Intent(this, WebViewActivity.class);
			// mrpg.putExtra("htmlurl",
			// "http://1.93.13.73:8080/mrpg.do?api=1qaz2wsx&bid=2000741906&vid=1");
			// mrpg.putExtra("title", "每日评估");
			startActivity(mrpg);
			break;
		case R.id.ll_huli:
			Intent huli = new Intent(this, HuliActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(huli);
			break;
		case R.id.ll_jiaoyu:
			Intent jiaoyv = new Intent(this, JiaoyvActivity.class);
			// Intent jiaoyv = new Intent(this, WebViewActivity.class);
			// jiaoyv.putExtra("htmlurl",
			// "http://1.93.13.73:8080/edu.do?api=1qaz2wsx&bid=2000741906&vid=1");
			// jiaoyv.putExtra("title", "教育");
			startActivity(jiaoyv);
			break;
		case R.id.ll_jianyan:
			if (Statics.patientdesc == null) {
				break;
			}
			Intent jianyan = new Intent(this, JianYanActivity.class);
			startActivity(jianyan);
			break;
		case R.id.ll_jiancha:
			if (Statics.patientdesc == null) {
				break;
			}
			Intent jiancha = new Intent(this, JianChaActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(jiancha);
			break;
		case R.id.ll_shoushu:
			if (Statics.patientdesc == null) {
				break;
			}
			Intent shoushu = new Intent(this, ShouShuActivity.class);
			startActivity(shoushu);
			break;
		case R.id.ll_ypgl:
			Intent ypguanli = new Intent(this, biaobenguanliActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(ypguanli);
			break;
		case R.id.ll_jiaojieban:
			startActivity(new Intent(this, JiaoJieActivity.class));
			break;

		}

	}

	private void lastPatient() {
		patientdes_layout.setVisibility(View.VISIBLE);
		// patientbottom.startAnimation(AnimationUtils.loadAnimation(this,
		// android.R.anim.slide_out_right));
		patientdes_layout.startAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.slide_in_left));
		bid = Statics.patientdesc.getPatient_id();
		vid = Statics.patientdesc.getZy_id();
		t = "up";
		System.out.println("上一床");
		downlaoddata();
	}

	private void nextPatient() {
		patientdes_layout.setVisibility(View.VISIBLE);
		// patientbottom.startAnimation(AnimationUtils.loadAnimation(this,
		// R.anim.push_left_out));
		patientdes_layout.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_left_in));
		bid = Statics.patientdesc.getPatient_id();
		vid = Statics.patientdesc.getZy_id();
		t = "next";
		System.out.println("下一床");
		downlaoddata();
	}

	private String t = null;

	void downlaoddata() {
		if (!HttpUtil.checkNet(this)) {
			Statics.internetcode = 0;
			int code = Statics.internetcode;
			MyUntils.myToast(this, Statics.internetstate[code]);
			return;
		}
		MyUntils.showProgress(this, "正在加载");

		ThreadPoolManager.getInstance().addTask(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				data = MyUntils.GetPatient(bid, vid, t);
				// System.out.println("run---getmyPatient-当前病人信息--"+data.toString());
				Message msg = new Message();
				msg.what = 101;
				mhandler.sendMessage(msg);
			}
		});
	}

	// 通过扫描查找病人 vid为1 写死的 t不传
	void downlaoddata1(final String sbid) {
		if (!HttpUtil.checkNet(this)) {
			Statics.internetcode = 0;
			int code = Statics.internetcode;
			MyUntils.myToast(this, Statics.internetstate[code]);
			return;
		}
		// MyUntils.showProgress(this, "正在加载");
		System.out.println("sbid=" + sbid);

		ThreadPoolManager.getInstance().addTask(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				data = MyUntils.GetPatient(sbid, "1", "");
				// System.out.println("run---getmyPatient-当前病人信息--"+data.toString());
				Message msg = new Message();
				msg.what = 101;
				mhandler.sendMessage(msg);
			}
		});
	}

	// 手势监听，通过屏幕的按下和抬起方法获取
	private int downX, upX;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		try {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				System.out.println("onTouchEvent   按下");
				downX = (int) event.getX(); // 取得按下时的坐标x
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				System.out.println("onTouchEvent   抬起");
				upX = (int) event.getX(); // 取得松开时的坐标x;
				System.out.println("downX - upX =" + (downX - upX));
				if (downX - upX >= 200) {
					// MyUntils.myToast(PatientDescActivity.this, "向左滑动-下一床");
					nextPatient();

					return true;
				} else if (upX - downX > 200) {
					// MyUntils.myToast(PatientDescActivity.this, "向右滑动-上一床");
					lastPatient();

					return true;
				} else if ((upX - downX < 10) || (downX - upX < 10)) {
					System.out.println("非滑动时间  启动点击");

					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			// Log.e(e.getLocalizedMessage(), e.getMessage());
		}

		return false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		registerBoradcastReceiver();
		if (Statics.patientdesc!=null) {
		back_name.setText(data.getName());
		bq_tv.setText(data.getQuhao());
		bc_tv.setText(data.getChuanghao());
		zhuyi_tv.setText(data.getZhuyi());
		patient_name.setText(data.getName());
		patient_gender.setText(data.getGender());
		patient_age.setText(data.getAge());
		patient_feibie.setText(data.getFEIBIE());
		patient_yue.setText(data.getFEIYUE());
		patient_zhenduan.setText(data.getZHENDUAN());
		patient_hldj.setText(data.getHulidengji());
		patient_bh.setText(data.getPatient_id());// 编号
		side_time.setText(data.getTime());
		item_xuhao.setText(data.getZy_id());
		}
		super.onResume();
	}
}
