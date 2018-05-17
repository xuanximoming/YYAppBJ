package com.yy.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

/**
 * 添加护理单
 */
public class AddHuliActivity extends Activity implements OnClickListener {
	TextView bq_tv, bc_tv, zhuyi_tv, patient_name, patient_gender,
			patient_age, patient_feibie, patient_hldj, patient_bh,
			activity_title_name;
	Button activity_title_back, activity_title_ok;
	Button add_items;
	Handler mhandler;
	RadioGroup rgb_group;
	private List<LinearLayout> layoutlist;
	private int selectpostion = 0;

	private TextView side_time;
	private TextView item_xuhao;
	List<HuLiDesc> data;

	private String cls = "A";
	private List<String> clss = new ArrayList<String>();
	private TextView patient_yue;
	private TextView patient_zhenduan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addhuli);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		init();
		selectLayout(selectpostion);
	}

	private void init() {
		// TODO Auto-generated method stub
		 clss.add("A");
		 clss.add("B");
		 clss.add("C");
		 clss.add("D");
		 clss.add("E");

		activity_title_name = (TextView) findViewById(R.id.activity_title_name);
		activity_title_name.setText("护理");
		activity_title_ok = (Button) findViewById(R.id.activity_title_ok);
		activity_title_ok.setText("提交");
		activity_title_ok.setVisibility(View.VISIBLE);
		activity_title_ok.setOnClickListener(this);
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
		layoutlist = new ArrayList<LinearLayout>();
		layoutlist.add((LinearLayout) findViewById(R.id.ll_shengmingtizheng));
		layoutlist.add((LinearLayout) findViewById(R.id.ll_chuliang));
		layoutlist.add((LinearLayout) findViewById(R.id.ll_ruliang));
		layoutlist.add((LinearLayout) findViewById(R.id.ll_qita));
		layoutlist.add((LinearLayout) findViewById(R.id.ll_shigu));

		add_items = (Button) findViewById(R.id.add_items);
		add_items.setOnClickListener(this);

		rgb_group = (RadioGroup) findViewById(R.id.rgb_group);
		rgb_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				// 生命特征
				case R.id.rgb_shengmingtizheng:
					selectpostion = 0;
					cls = "A";
					//System.out.println("cls = " + cls);
					break;
				// 出量
				case R.id.rgb_chuliang:
					selectpostion = 1;
					cls = "B";

					break;
				// 入量
				case R.id.rgb_ruliang:
					selectpostion = 2;
					cls = "C";
					break;
				// 其他
				case R.id.rgb_qita:
					selectpostion = 3;
					cls = "D";
					break;
				// 事故
				case R.id.rgb_shigu:
					selectpostion = 4;
					cls = "E";
					break;
				default:
					break;
				}
				//System.out.println(clss.contains(cls));
				//if (!clss.contains(cls)) {
					downlaoddata();
				//}
				System.out.println("selectpostion=" + selectpostion);
				selectLayout(selectpostion);
			}
		});
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
							MyUntils.myToast(AddHuliActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
					}
					switch (selectpostion) {
					// 生命特征
					case 0:
						// layoutlist.get(selectpostion).clearChildFocus(child);
						addShengMingTeZhengitems(data);
						break;
					// 出量
					case 1:
							addChuliangitems(data);
						break;
					// 入量
					case 2:
							addRuliangitems(data);
						break;
					// 其他
					case 3:
							addQitaitems(data);
						break;
					// 事故
					case 4:
							addShiguitems(data);
						break;

					default:
						break;
					}
					System.out.println("data size=" + data.size());
					break;
				case 102:
					MyUntils.cancleProgress();
					if (sucess == false) {
						if (Statics.internetcode != -1) {
							MyUntils.myToast(AddHuliActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
					} else {
						MyUntils.myToast(AddHuliActivity.this, "表单提交成功");
					}
					break;
				default:
					break;
				}
			}

		};
		downlaoddata();
	}

	private void selectLayout(int postion) {
		for (int i = 0; i < layoutlist.size(); i++) {
			layoutlist.get(i).setVisibility(View.GONE);
			if (postion == i) {
				layoutlist.get(i).setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_title_back:
			finish();
			break;
		// 添加项目
		case R.id.add_items:
			// switch (selectpostion) {
			// // 生命特征
			// case 0:
			// addShengMingTeZhengitems("脉搏（次/分）");
			// break;
			// // 出量
			// case 1:
			// addChuliangitems("大小");
			// break;
			// // 入量
			// case 2:
			// addRuliangitems("大小");
			// break;
			// // 其他
			// case 3:
			// addQitaitems("其他");
			// break;
			// // 事故
			// case 4:
			// addShiguitems("大出血");
			// break;
			//
			// default:
			// break;
			// }
			break;
		// 提交表单
		case R.id.activity_title_ok:
			Submitdata();
			break;
		default:
			break;
		}
	}

	void downlaoddata() {
		//clss.add(cls);
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
				data = MyUntils.getHulilitypeURL(cls,Statics.patientdesc.getPatient_id());
				Message msg = new Message();
				msg.what = 101;
				mhandler.sendMessage(msg);
			}
		});
	}

	private boolean sucess = false;

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
				System.out.println("selectpostion2=" + selectpostion);
				switch (selectpostion) {
				// 生命特征
				case 0:
					for (int i = 0; i < data.size(); i++) {
						data.get(i).setHL_VALUE(
								shengmingtezhengtextlist.get(i).getText()
										.toString());
						System.out.println(shengmingtezhengtextlist.get(i)
								.getText().toString());
					}
					break;
				// 出量
				case 1:
					for (int i = 0; i < data.size(); i++) {
						data.get(i).setHL_VALUE(
								chuliangtextlist.get(i).getText().toString());
					}
					break;
				// 入量
				case 2:
					for (int i = 0; i < data.size(); i++) {
						data.get(i).setHL_VALUE(
								ruliangtextlist.get(i).getText().toString());
						System.out.println(ruliangtextlist.get(i).getText().toString());
					}
					break;
				// 其他
				case 3:
					for (int i = 0; i < data.size(); i++) {
						data.get(i).setHL_VALUE(
								qitatextlist.get(i).getText().toString());
					}
					break;
				// 事故
				case 4:
					for (int i = 0; i < data.size(); i++) {
						data.get(i).setHL_VALUE(
								shigutextlist.get(i).getText().toString());
					}
					break;

				default:
					break;
				}
				/*String saveok = MyUntils.gethulidescjson(data);//原方式*/
				//2016.1.19修改
                String saveok = new Gson().toJson(data);

                System.out.println("saveok=-------   "+saveok);
				try{
				sucess = MyUntils.submithulidata(patient_id, zid,
						clss.get(selectpostion), saveok, Statics.UNAME);//Statics.UNAME.toUpperCase()是护理添加需要该护士的登录名
				}catch(Exception e){
					e.printStackTrace();
				}
				mhandler.sendEmptyMessage(102);
			}
		});
	}

	private List<HuLiDesc> shengmingtezhengstrlist = null;
	private List<EditText> shengmingtezhengtextlist = null;

	void addShengMingTeZhengitems(final List<HuLiDesc> list) {
		shengmingtezhengstrlist = new ArrayList<HuLiDesc>();
		shengmingtezhengtextlist = new ArrayList<EditText>();
		layoutlist.get(selectpostion).removeAllViews();
		System.out.println("list ======"+list.size());
		for (final HuLiDesc str : list) {
			View view = LayoutInflater.from(this).inflate(
					R.layout.item_addhuli, null);
			TextView item_addhuli_keytext = (TextView) view
					.findViewById(R.id.item_addhuli_keytext);
			item_addhuli_keytext.setText(str.getHL_ITEM());
			shengmingtezhengstrlist.add(str);
			final EditText item_addhuli_valueedtext = (EditText) view
					.findViewById(R.id.item_addhuli_valueedtext);
			item_addhuli_valueedtext
					.setOnFocusChangeListener(new OnFocusChangeListener() {

						@Override
						public void onFocusChange(View v, boolean hasFocus) {
							// TODO Auto-generated method stub
							if (hasFocus == false) {
								if (!StrTool.isnull(item_addhuli_valueedtext
										.getText().toString())
										&& (Double
												.parseDouble(item_addhuli_valueedtext
														.getText().toString()) < str
												.getMIN_VALUE() || Double
												.parseDouble(item_addhuli_valueedtext
														.getText().toString()) > str
												.getMAX_VALUE())) {
									MyUntils.myToast(AddHuliActivity.this,
											str.getALERTMSG());
								}
							}
						}
					});
			shengmingtezhengtextlist.add(item_addhuli_valueedtext);
			layoutlist.get(selectpostion).addView(view);
		}
		System.out.println("list1 ======"+list.size());
	}

	List<HuLiDesc> chuliangstrlist = null;
	List<EditText> chuliangtextlist = null;

	void addChuliangitems(final List<HuLiDesc> list) {
		 chuliangstrlist = new ArrayList<HuLiDesc>();
	     chuliangtextlist = new ArrayList<EditText>();
	     layoutlist.get(selectpostion).removeAllViews();
	     System.out.println("list3 ======"+list.size());
	     for (final HuLiDesc str : list) {
		View view = LayoutInflater.from(this).inflate(R.layout.item_addhuli,
				null);
		TextView item_addhuli_keytext = (TextView) view
				.findViewById(R.id.item_addhuli_keytext);
		item_addhuli_keytext.setText(str.getHL_ITEM());
		chuliangstrlist.add(str);
		System.out.println("chuliangstrlist json size:"
				+ chuliangstrlist.size());
		final EditText item_addhuli_valueedtext = (EditText) view
				.findViewById(R.id.item_addhuli_valueedtext);
		item_addhuli_valueedtext
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						if (hasFocus == false) {
							if (!StrTool.isnull(item_addhuli_valueedtext
									.getText().toString())
									&& (Double
											.parseDouble(item_addhuli_valueedtext
													.getText().toString()) < str
											.getMIN_VALUE() || Double
											.parseDouble(item_addhuli_valueedtext
													.getText().toString()) > str
											.getMAX_VALUE())) {
								MyUntils.myToast(AddHuliActivity.this,
										str.getALERTMSG());
							}
						}
					}
				});
		chuliangtextlist.add(item_addhuli_valueedtext);
		System.out.println("chuliangtextlist json size:"
				+ chuliangtextlist.size());
		layoutlist.get(selectpostion).addView(view);
	     }
	     System.out.println("list4 ======"+list.size());
	}

	private List<HuLiDesc> ruliangstrlist = null;
	private List<EditText> ruliangtextlist = null;
	void addRuliangitems(final List<HuLiDesc> list) {
		ruliangstrlist = new ArrayList<HuLiDesc>();
		ruliangtextlist = new ArrayList<EditText>();
		layoutlist.get(selectpostion).removeAllViews();
		for (final HuLiDesc str : list) {
		View view = LayoutInflater.from(this).inflate(R.layout.item_addhuli,
				null);
		TextView item_addhuli_keytext = (TextView) view
				.findViewById(R.id.item_addhuli_keytext);
		item_addhuli_keytext.setText(str.getHL_ITEM());
		ruliangstrlist.add(str);
		final EditText item_addhuli_valueedtext = (EditText) view
				.findViewById(R.id.item_addhuli_valueedtext);
		item_addhuli_valueedtext
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						if (hasFocus == false) {
							if (!StrTool.isnull(item_addhuli_valueedtext
									.getText().toString())
									&& (Double
											.parseDouble(item_addhuli_valueedtext
													.getText().toString()) < str
											.getMIN_VALUE() || Double
											.parseDouble(item_addhuli_valueedtext
													.getText().toString()) > str
											.getMAX_VALUE())) {
								MyUntils.myToast(AddHuliActivity.this,
										str.getALERTMSG());
							}
						}
					}
				});
		ruliangtextlist.add(item_addhuli_valueedtext);
		layoutlist.get(selectpostion).addView(view);
		}
	}

	private List<HuLiDesc> qitastrlist = null;
	private List<EditText> qitatextlist = null;

	void addQitaitems(final List<HuLiDesc> list) {
		qitastrlist = new ArrayList<HuLiDesc>();
		qitatextlist = new ArrayList<EditText>();
		layoutlist.get(selectpostion).removeAllViews();
		for (final HuLiDesc str : list) {
		View view = LayoutInflater.from(this).inflate(R.layout.item_addhuli,
				null);
		TextView item_addhuli_keytext = (TextView) view
				.findViewById(R.id.item_addhuli_keytext);
		item_addhuli_keytext.setText(str.getHL_ITEM());
		qitastrlist.add(str);
		final EditText item_addhuli_valueedtext = (EditText) view
				.findViewById(R.id.item_addhuli_valueedtext);
		item_addhuli_valueedtext
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						if (hasFocus == false) {
							if (!StrTool.isnull(item_addhuli_valueedtext
									.getText().toString())
									&& (Double
											.parseDouble(item_addhuli_valueedtext
													.getText().toString()) < str
											.getMIN_VALUE() || Double
											.parseDouble(item_addhuli_valueedtext
													.getText().toString()) > str
											.getMAX_VALUE())) {
								MyUntils.myToast(AddHuliActivity.this,
										str.getALERTMSG());
							}
						}
					}
				});
		qitatextlist.add(item_addhuli_valueedtext);
		layoutlist.get(selectpostion).addView(view);
		}
	}

	private List<HuLiDesc> shigustrlist = null;
	private List<EditText> shigutextlist = null;

	void addShiguitems(final List<HuLiDesc> list) {
		 shigustrlist = new ArrayList<HuLiDesc>();
		shigutextlist = new ArrayList<EditText>();
		layoutlist.get(selectpostion).removeAllViews();
		for (final HuLiDesc str : list) {
		View view = LayoutInflater.from(this).inflate(R.layout.item_addhuli,
				null);
		TextView item_addhuli_keytext = (TextView) view
				.findViewById(R.id.item_addhuli_keytext);
		item_addhuli_keytext.setText(str.getHL_ITEM());
		shigustrlist.add(str);
		final EditText item_addhuli_valueedtext = (EditText) view
				.findViewById(R.id.item_addhuli_valueedtext);
		item_addhuli_valueedtext
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						if (hasFocus == false) {
							if (!StrTool.isnull(item_addhuli_valueedtext
									.getText().toString())
									&& (Double
											.parseDouble(item_addhuli_valueedtext
													.getText().toString()) < str
											.getMIN_VALUE() || Double
											.parseDouble(item_addhuli_valueedtext
													.getText().toString()) > str
											.getMAX_VALUE())) {
								MyUntils.myToast(AddHuliActivity.this,
										str.getALERTMSG());
							}
						}
					}
				});
		shigutextlist.add(item_addhuli_valueedtext);
		layoutlist.get(selectpostion).addView(view);
		}
	}
}
