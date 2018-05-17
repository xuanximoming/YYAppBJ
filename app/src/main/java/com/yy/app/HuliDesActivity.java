package com.yy.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yy.cookies.Statics;
import com.yy.entity.HuLiDesc;
import com.yy.until.ExitManager;
import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;
import com.yy.until.StrTool;
import com.yy.until.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

public class HuliDesActivity extends Activity implements OnClickListener {
	TextView bq_tv, bc_tv, zhuyi_tv, patient_name, patient_gender,
			patient_age, patient_feibie, patient_hldj, patient_bh,
			activity_title_name;
	Handler mhandler;
	List<HuLiDesc> data;
	ListView listview;
	BaseAdapter adapter;
	Button activity_title_back, activity_title_ok;
	private TextView side_time;
	private TextView item_xuhao;
	private RadioGroup rgb_group;
	private TextView patient_yue;
	private TextView patient_zhenduan;
	LinearLayout ll_data;
	private List<String> clss = new ArrayList<String>();
	private String cls = "A";
	private String date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hulidesc);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		date = this.getIntent().getStringExtra("date");
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		activity_title_name = (TextView) findViewById(R.id.activity_title_name);
		activity_title_name.setText("护理");
		activity_title_ok = (Button) findViewById(R.id.activity_title_ok);
		activity_title_ok.setVisibility(View.VISIBLE);
		activity_title_ok.setText("提交");
		activity_title_ok.setOnClickListener(this);
		activity_title_back = (Button) findViewById(R.id.activity_title_back);
		activity_title_back.setOnClickListener(this);

		ll_data = (LinearLayout) findViewById(R.id.ll_data);

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

		// listview = (ListView) findViewById(R.id.hulidesc_listview);
		rgb_group = (RadioGroup) findViewById(R.id.rgb_group);
		rgb_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				// 生命特征
				case R.id.rgb_shengmingtizheng:
					cls = "A";
					break;
				// 出量
				case R.id.rgb_chuliang:
					cls = "B";
					break;
				// 入量
				case R.id.rgb_ruliang:
					cls = "C";
					break;
				// 其他
				case R.id.rgb_qita:
					cls = "D";
					break;
				// 事故
				case R.id.rgb_shigu:
					cls = "E";
					break;
				default:
					break;
				}
				downlaoddata();
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

		data = new ArrayList<HuLiDesc>();
		mhandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 101:
					ll_data.removeAllViews();

					MyUntils.cancleProgress();
					if (data == null) {
						if (Statics.internetcode != -1) {
							MyUntils.myToast(HuliDesActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
						break;
					}
					shengmingtezhengstrlist.clear();
					shengmingtezhengtextlist.clear();
					for (int i = 0; i < data.size(); i++) {
						addShengMingTeZhengitems(data.get(i));
					}
					break;
				case 102:
					MyUntils.cancleProgress();
					if (sucess == false) {
						if (Statics.internetcode != -1) {
							MyUntils.myToast(HuliDesActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
					} else {
						MyUntils.myToast(HuliDesActivity.this, "表单提交成功");
						downlaoddata();
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
				data = MyUntils.getHuLiDescList(patient_id, zid, date, cls);
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

		case R.id.activity_title_ok:
			Submitdata();
			break;
		}
	}

	private List<HuLiDesc> shengmingtezhengstrlist = new ArrayList<HuLiDesc>();
	private List<EditText> shengmingtezhengtextlist = new ArrayList<EditText>();

	void addShengMingTeZhengitems(final HuLiDesc str) {
		View convertView = LayoutInflater.from(this).inflate(
				R.layout.item_hulidesc, null); // 实例化convertView
		TextView keytv = (TextView) convertView
				.findViewById(R.id.item_hulidesc_keytv);
		final EditText valuetv = (EditText) convertView
				.findViewById(R.id.item_hulidesc_valuetv);
		TextView timetv = (TextView) convertView
				.findViewById(R.id.item_hulidesc_timetv);
		TextView item_hulidesc_dwtv = (TextView) convertView
				.findViewById(R.id.item_hulidesc_dwtv);
		keytv.setText(Stringisnull(str.getHL_ITEM()));
		timetv.setText(Stringisnull(str.getHL_TIME()));
		valuetv.setText(Stringisnull(str.getHL_VALUE()));
		item_hulidesc_dwtv.setText(Stringisnull(str.getUNITS()));
		shengmingtezhengstrlist.add(str);
		valuetv.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus == false) {
					try {
						if (!StrTool
								.isnull(valuetv.getText().toString().trim())
								&& (Double.parseDouble(valuetv.getText()
										.toString()) < str.getMIN_VALUE() || Double
										.parseDouble(valuetv.getText()
												.toString()) > str
										.getMAX_VALUE())) {
							MyUntils.myToast(HuliDesActivity.this,
									str.getALERTMSG());
						}
					} catch (Exception e) {
						// TODO: handle exception
						MyUntils.myToast(HuliDesActivity.this, "数据格式错误");
					}

				}
			}
		});
		shengmingtezhengtextlist.add(valuetv);
		ll_data.addView(convertView);
	}

	public static String Stringisnull(String trim) {
		String trim2 = trim.toString().trim();
		if (trim2.equals("null") || trim2.equals("") || trim2 == null) {
			return "";
		} else {
			return trim2;
		}
	}

	private boolean sucess = false;

	// 提交数据
	void Submitdata() {
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
				for (int i = 0; i < shengmingtezhengstrlist.size(); i++) {
					shengmingtezhengstrlist.get(i).setHL_VALUE(
							shengmingtezhengtextlist.get(i).getText()
									.toString());

				}
/*				String saveok = MyUntils
						.gethulidescjson(shengmingtezhengstrlist);*/
                String saveok=new Gson().toJson(shengmingtezhengstrlist);
				sucess = MyUntils.submithulidata(patient_id, zid, cls, saveok,
                        Statics.UNAME);
				mhandler.sendEmptyMessage(102);
			}
		});
	}

}
