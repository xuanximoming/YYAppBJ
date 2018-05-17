package com.yy.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yy.adapter.JianChaAdapter;
import com.yy.cookies.Statics;
import com.yy.entity.JianCha;
import com.yy.until.ExitManager;
import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

public class JiaoyuDesActivity extends Activity implements OnClickListener {
	TextView bq_tv, bc_tv, zhuyi_tv, patient_name, patient_gender,
			patient_age, patient_feibie, patient_hldj, patient_bh,
			activity_title_name;
	Handler mhandler;
	List<JianCha> data;
	ListView listview;
	JianChaAdapter adapter;
	Button activity_title_back, activity_title_ok;
	private TextView patient_yue;
	private TextView patient_zhenduan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jiaojiedes);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		activity_title_name = (TextView) findViewById(R.id.activity_title_name);
		activity_title_name.setText("教育");
		activity_title_ok = (Button) findViewById(R.id.activity_title_ok);
		activity_title_ok.setVisibility(View.GONE);
		// activity_title_ok.setText("执行单");
		activity_title_back = (Button) findViewById(R.id.activity_title_back);
		activity_title_back.setOnClickListener(this);

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

		listview = (ListView) findViewById(R.id.listview);

		if (Statics.patientdesc != null) {
			bq_tv.setText(Statics.patientdesc.getQuhao());
			bc_tv.setText(Statics.patientdesc.getChuanghao());
			zhuyi_tv.setText(Statics.patientdesc.getZhuyi());
			patient_name.setText(Statics.patientdesc.getName());
			patient_gender.setText(Statics.patientdesc.getGender());
			patient_age.setText(Statics.patientdesc.getAge());
			patient_feibie.setText(Statics.patientdesc.getFEIBIE());
			patient_yue.setText(Statics.patientdesc.getFEIYUE());
			patient_zhenduan.setText(Statics.patientdesc.getZHENDUAN());
			patient_hldj.setText(Statics.patientdesc.getHulidengji());
			patient_bh.setText(Statics.patientdesc.getPatient_id());// 编号

		}

		data = new ArrayList<JianCha>();
		mhandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 101:
					MyUntils.cancleProgress();
					if (data == null) {
						if (Statics.internetcode != -1) {
							MyUntils.myToast(JiaoyuDesActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
					} else if (data.size() > 0) {
						System.out.println(data.toString());
						adapter = new JianChaAdapter(data,
								JiaoyuDesActivity.this);
						listview.setAdapter(adapter);
					} else {
//						MyUntils.myToast(JiaoyuDesActivity.this, "获取信息失败");
					}
					break;

				default:
					break;
				}
			}

		};
		downlaoddata();
	}

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
				System.out.println("run---getmyPatient");
				String patient_id = Statics.patientdesc.getPatient_id();
				String zid = Statics.patientdesc.getZy_id();

				data = MyUntils.getJianCha(patient_id, zid);
				Message msg = new Message();
				msg.what = 101;
				mhandler.sendMessage(msg);
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

		default:
			break;
		}
	}
}
