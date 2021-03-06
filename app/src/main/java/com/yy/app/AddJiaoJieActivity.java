package com.yy.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.yy.adapter.AddJiaoJieAdapter;
import com.yy.adapter.YizhuAdapter;
import com.yy.cookies.Statics;
import com.yy.entity.BigType;
import com.yy.entity.LittleType;
import com.yy.entity.Yizhu;
import com.yy.until.ExitManager;
import com.yy.until.MyUntils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddJiaoJieActivity extends Activity implements OnClickListener {
	TextView bq_tv, bc_tv, zhuyi_tv, patient_name, patient_gender,
			patient_age, patient_feibie, patient_hldj, patient_bh,
			activity_title_name, activity_title_ok;
	Handler mhandler;
	List<Yizhu> data;
	ListView listview;
	YizhuAdapter adapter;
	private ExpandableListView expandableListView;
	public static AddJiaoJieAdapter treeViewAdapter;
	private Button activity_title_back;
	private TextView patient_yue;
	private TextView patient_zhenduan;
	public static List<HashMap<String, Object>> gcategory = new ArrayList<HashMap<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addjiaojie);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		init();
		initData();
		Statics.ADDAVTIVITY = Statics.AddJiaoJieActivity;
	}

	private void init() {
		// TODO Auto-generated method stub
		activity_title_name = (TextView) findViewById(R.id.activity_title_name);
		activity_title_name.setText("10月12日交接单");
		activity_title_back = (Button) findViewById(R.id.activity_title_back);
		activity_title_back.setOnClickListener(this);
		activity_title_ok = (TextView) findViewById(R.id.activity_title_ok);
		activity_title_ok.setText("提交");
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

		data = new ArrayList<Yizhu>();
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
							MyUntils.myToast(AddJiaoJieActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
					} else {
						System.out.println(data.toString());
						adapter = new YizhuAdapter(data,
								AddJiaoJieActivity.this);
						listview.setAdapter(adapter);
					}
					break;

				default:
					break;
				}
			}

		};
		// downlaoddata();
	}

	public static List<BigType> listbigtype;
	public List<LittleType> listlittletype;

	private void initData() {

		expandableListView = (ExpandableListView) this
				.findViewById(R.id.expandableListView);
		listbigtype = new ArrayList<BigType>();
		listlittletype = new ArrayList<LittleType>();
		listlittletype.add(new LittleType("1", "听觉神经", false));
		listlittletype.add(new LittleType("2", "视觉神经", false));
		listlittletype.add(new LittleType("3", "嗅觉神经", false));
		listlittletype.add(new LittleType("4", "感觉神经", false));
		listbigtype.add(new BigType("1", "神经系统", false, listlittletype));

		listlittletype = new ArrayList<LittleType>();
		listlittletype.add(new LittleType("1", "胸科", false));
		listlittletype.add(new LittleType("2", "神经科", false));
		listlittletype.add(new LittleType("3", "妇科", false));
		listlittletype.add(new LittleType("4", "儿科", false));
		listbigtype.add(new BigType("2", "内科", false, listlittletype));
		treeViewAdapter = new AddJiaoJieAdapter(this, listbigtype);
		expandableListView.setAdapter(treeViewAdapter);
		expandableListView.expandGroup(0);
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

	// void downlaoddata() {
	// MyUntils.showProgress(this, "加载数据中......");
	//
	// ThreadPoolManager.getInstance().addTask(new Runnable() {
	// public void run() {
	// // TODO Auto-generated method stub
	// System.out.println("run---getmyPatient");
	// String patient_id = Statics.patientdesc.getPatient_id();
	// String zid = Statics.patientdesc.getZy_id();
	//
	// data = MyUntils.getYizhu(patient_id, zid);
	// Message msg = new Message();
	// msg.what = 101;
	// mhandler.sendMessage(msg);
	// }
	// });
	// }
}
