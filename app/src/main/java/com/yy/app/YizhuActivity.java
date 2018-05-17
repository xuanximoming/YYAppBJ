package com.yy.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.yy.adapter.SpinnerAdapter;
import com.yy.adapter.YizhuAdapter;
import com.yy.cookies.Statics;
import com.yy.entity.Yizhu;
import com.yy.entity.Yizhus;
import com.yy.entity.Yizhutype;
import com.yy.until.ExitManager;
import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

public class YizhuActivity extends Activity implements OnClickListener {
	TextView bq_tv, bc_tv, zhuyi_tv, patient_name, patient_gender,
			patient_age, patient_feibie, patient_hldj, patient_bh,
			activity_title_name;
	Button activity_title_ok, activity_title_back;
	Handler mhandler;
	List<Yizhu> data;
	ListView listview;
	YizhuAdapter adapter;
	Yizhus datas;
	List<Yizhutype> datatype, datacl;
	Spinner sp_lb, sp_cl;
	// int typeid = 0, clid = 0;

	private TextView side_time;
	private TextView item_xuhao;
	private TextView patient_yue;
	private TextView patient_zhenduan;
	boolean isoncreate = false;
	boolean isfirst = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yizhu);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		activity_title_name = (TextView) findViewById(R.id.activity_title_name);
		activity_title_name.setText("医嘱");
		activity_title_ok = (Button) findViewById(R.id.activity_title_ok);
		activity_title_ok.setText("执行单");
		activity_title_ok.setOnClickListener(this);
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

		side_time = (TextView) findViewById(R.id.side_time);// 入住时间
		item_xuhao = (TextView) findViewById(R.id.Visit_ID);// 住院号
		listview = (ListView) findViewById(R.id.listview);
		sp_lb = (Spinner) findViewById(R.id.sp_lb);
		sp_lb.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == 0) {
					ot = "all";
				} else {
					ot = datatype.get(position).getCode();
				}
				if (isfirst) {
					downlaoddata();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		sp_cl = (Spinner) findViewById(R.id.sp_cl);
		sp_cl.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == 0) {
					cl = "all";
				} else {
					cl = datacl.get(position).getCode();
				}
				if (isfirst) {
					downlaoddata();
				}else {
					isfirst = true;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

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

		data = new ArrayList<Yizhu>();
		datas = new Yizhus();
		datatype = new ArrayList<Yizhutype>();
		datacl = new ArrayList<Yizhutype>();

		mhandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 101:
					MyUntils.cancleProgress();

					if (datas == null) {
						if (Statics.internetcode != -1) {
							MyUntils.myToast(YizhuActivity.this,
									Statics.internetstate[Statics.internetcode]);
						} else {
							MyUntils.myToast(YizhuActivity.this, "此病人暂无医嘱信息");
						}
					} else {
						// System.out.println(data.toString());
						data = datas.getYizhulist();
						datatype = datas.getTypelist();
						datacl = datas.getCllist();
						if (data.size() == 0) {
							MyUntils.myToast(YizhuActivity.this, "此病人暂无医嘱信息");
						}
						adapter = new YizhuAdapter(data, YizhuActivity.this);
						listview.setAdapter(adapter);
						if (!isoncreate) {

							SpinnerAdapter typeadapter = new SpinnerAdapter(
									datatype, YizhuActivity.this);
							sp_lb.setAdapter(typeadapter);
							SpinnerAdapter cladapter = new SpinnerAdapter(
									datacl, YizhuActivity.this);
							sp_cl.setAdapter(cladapter);
						}
					}
					isoncreate = true;
					// else {
					// // MyUntils.myToast(YizhuActivity.this, "获取信息失败");
					// }
					break;

				case 102:
					adapter = new YizhuAdapter(data, YizhuActivity.this);
					listview.setAdapter(adapter);
					System.out.println("...spinner data...." + data.toString());
					// adapter.notifyDataSetChanged();
					// MyUntils.cancleProgress();
					break;
				}
			}

		};
		downlaoddata();
	}

	private String ot = "all", cl = "all";

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

				datas = MyUntils.getYizhu(patient_id, zid, ot, cl);
				Message msg = new Message();
				msg.what = 101;
				mhandler.sendMessage(msg);
			}
		});
	}

	// void gettype(final int typeid, final int clid) {
	//
	// ThreadPoolManager.getInstance().addTask(new Runnable() {
	// public void run() {
	// // TODO Auto-generated method stub
	// if (typeid == 0 && clid == 0) {
	// data = datas.getYizhulist();
	// } else if (clid == 0) {
	// data = new ArrayList<Yizhu>();
	// Yizhutype yizhutype = datatype.get(typeid);
	// String code = yizhutype.getCode();
	// List<Yizhu> list = datas.getYizhulist();
	// for (int i = 0; i < list.size(); i++) {
	// Yizhu yizhu = list.get(i);
	// if (yizhu.getYZ_TYPE().equals(code)) {
	// data.add(yizhu);
	// }
	// }
	// } else if (typeid == 0) {
	// data = new ArrayList<Yizhu>();
	// Yizhutype yizhucl = datacl.get(clid);
	// String code = yizhucl.getCode();
	// List<Yizhu> list = datas.getYizhulist();
	// for (int i = 0; i < list.size(); i++) {
	// Yizhu yizhu = list.get(i);
	// if (yizhu.getYZ_CHLIN().equals(code)) {
	// data.add(yizhu);
	// }
	// }
	// } else {
	// data = new ArrayList<Yizhu>();
	// Yizhutype yizhucl = datacl.get(clid);
	// String clcode = yizhucl.getCode();
	// Yizhutype yizhutype = datatype.get(typeid);
	// String typecode = yizhutype.getCode();
	//
	// List<Yizhu> list = datas.getYizhulist();
	// for (int i = 0; i < list.size(); i++) {
	// Yizhu yizhu = list.get(i);
	// if (yizhu.getYZ_CHLIN().equals(clcode)
	// && yizhu.getYZ_TYPE().equals(typecode)) {
	// data.add(yizhu);
	// }
	// }
	// }
	//
	// Message msg = new Message();
	// msg.what = 102;
	// mhandler.sendMessage(msg);
	// }
	// });
	// }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_title_back:
			finish();
			break;
		case R.id.activity_title_ok:
			break;
		default:
			break;
		}
	}
}
