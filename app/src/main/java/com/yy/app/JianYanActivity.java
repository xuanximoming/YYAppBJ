package com.yy.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yy.adapter.JianYanAdapter;
import com.yy.cookies.Statics;
import com.yy.entity.JianYan;
import com.yy.until.ExitManager;
import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

public class JianYanActivity extends Activity implements OnClickListener {
	TextView bq_tv, bc_tv, zhuyi_tv, patient_name, patient_gender,
			patient_age, patient_feibie, patient_hldj, patient_bh,
			activity_title_name;
	Handler mhandler;
	List<JianYan> data;
	ListView listview;
	JianYanAdapter adapter;
	Button activity_title_back, activity_title_ok;
	private TextView side_time;
	private TextView item_xuhao;
	private TextView patient_yue;
	private TextView patient_zhenduan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jianyan);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		activity_title_name = (TextView) findViewById(R.id.activity_title_name);
		activity_title_name.setText("检验");
		activity_title_ok = (Button) findViewById(R.id.activity_title_ok);
		activity_title_ok.setVisibility(View.GONE);
		// activity_title_ok.setText("执行单");
		activity_title_back = (Button) findViewById(R.id.activity_title_back);
		activity_title_back.setOnClickListener(this);

		side_time = (TextView) findViewById(R.id.side_time);// 入住时间
		item_xuhao = (TextView) findViewById(R.id.Visit_ID);// 住院号
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
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(JianYanActivity.this,
						JianYanresActivity.class);
				// Intent intent = new Intent(PatientActivity.this,
				// PatientDescPagerActivity.class);
//				Patient patient = data.get(position);
//				intent.putExtra("bid", patient.getPatient_id());
//				intent.putExtra("vid", patient.getZy_id());
				intent.putExtra("jyid", data.get(position).getJY_ID());
				startActivity(intent);
			}
		});
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
			side_time.setText(Statics.patientdesc.getTime());
			item_xuhao.setText(Statics.patientdesc.getZy_id());

		}

		data = new ArrayList<JianYan>();
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
							MyUntils.myToast(JianYanActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}else{
							MyUntils.myToast(JianYanActivity.this,"此病人暂无检验信息");
						}
					} else if (data.size() > 0) {
						System.out.println(data.toString());
						adapter = new JianYanAdapter(data, JianYanActivity.this);
						listview.setAdapter(adapter);
					} else {
						// MyUntils.myToast(JianYanActivity.this, "获取信息失败");
						MyUntils.myToast(JianYanActivity.this,"此病人暂无检验信息");
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

				data = MyUntils.getJianYan(patient_id, zid);
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
