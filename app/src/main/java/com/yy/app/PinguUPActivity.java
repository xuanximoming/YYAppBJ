package com.yy.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yy.cookies.Statics;
import com.yy.entity.JiaoYuForm;
import com.yy.entity.PingGu;
import com.yy.until.ExitManager;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PinguUPActivity extends Activity implements OnClickListener {
	TextView activity_title_name;
	Button activity_title_back, activity_title_ok;
	Handler mhandler;
	public static List<PingGu> data = new ArrayList<PingGu>();
	GridView gridview;
	BaseAdapter adapter;
	List<String> strlist;
	LinearLayout ll_data;
	RadioGroup radiogroup;
	// private ListView listview;
	String itemid = "0";
	Button uplowd;
	boolean res = false; // 教育表单提交结果
	private String saveok;
	public static List<JiaoYuForm> jiaoyuitemidlist1 = new ArrayList<JiaoYuForm>();// 记录教育列表选择的Oname：表单项Ovalue：存入值
	private View findViewById;
	private TextView one_title;
	private String itemname;
	private List<String[]> title;
	List<PingGu> editdata;// 放是edit的数据
	List<PingGu> radiodata;// 放是radio的数据
	int cheradio = -1;// 选中了radiobutton id
	String radiotext = "";
	private String date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pingguup);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		Intent intent = getIntent();
		itemid = intent.getStringExtra("itemid");
		itemname = intent.getStringExtra("itemname");
		date = intent.getStringExtra("date");
		title = new ArrayList<String[]>();
		title.add(new String[] { itemid, itemname });
		init();
		refreshtitle();
	}

	private void init() {
		// TODO Auto-generated method stub
		activity_title_name = (TextView) findViewById(R.id.activity_title_name);
		activity_title_name.setText("评估项");
		activity_title_ok = (Button) findViewById(R.id.activity_title_ok);
		activity_title_ok.setText("提交");
		// activity_title_ok.setVisibility(View.VISIBLE);
		activity_title_ok.setOnClickListener(this);
		activity_title_back = (Button) findViewById(R.id.activity_title_back);
		activity_title_back.setOnClickListener(this);

		ll_data = (LinearLayout) findViewById(R.id.ll_data);
		editdata = new ArrayList<PingGu>();
		radiodata = new ArrayList<PingGu>();

		radiogroup = (RadioGroup) findViewById(R.id.radio_group);
		radiogroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						cheradio = checkedId;
						RadioButton radioButton = (RadioButton) findViewById(checkedId);
						radiotext = radioButton.getText().toString();
						System.out.println("............radio......"
								+ radiotext);
					}
				});
		uplowd = (Button) findViewById(R.id.uplowd);
		// uplowd.setVisibility(View.GONE);
		uplowd.setOnClickListener(this);

		data = new ArrayList<PingGu>();
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
							MyUntils.myToast(PinguUPActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
					} else if (data.size() > 0) {
						System.out.println(data.toString());
						// adapter = new jiaoyvbdAdapter(data,
						// jiaoyvbdupActivity.this);
						// listview.setAdapter(adapter);
						bldata();
					} else {
						MyUntils.myToast(PinguUPActivity.this,"此病人暂无每日评估信息");
						if (Statics.internetcode != -1) {
							MyUntils.myToast(PinguUPActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
						// MyUntils.myToast(jiaoyvbdupActivity.this, "获取信息失败");
					}
					break;
				case 102:
					MyUntils.cancleProgress();

					if (res) {
						MyUntils.myToast(PinguUPActivity.this, "表单提交成功");
						finish();
					} else {
						MyUntils.myToast(PinguUPActivity.this, "表单提交失败");
					}
					break;
				default:
					break;
				}
			}

		};
		downlaoddata();
	}

	private void refreshtitle() {
		one_title = (TextView) findViewById(R.id.one_title);
		one_title.setText(title.get(title.size() - 1)[1]);
	};

	void downlaoddata() {
		MyUntils.showProgress(this, "加载数据");

		ThreadPoolManager.getInstance().addTask(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				String patient_id = Statics.patientdesc.getPatient_id();
				String zid = Statics.patientdesc.getZy_id();

				data = MyUntils.getMRPinggu(patient_id, zid, itemid, date);
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

			break;
		case R.id.uplowd:
			upbd();
			break;
		}
	}

	// 遍历数据列表
	@SuppressLint("ResourceAsColor")
	void bldata() {
		Statics.ruyuanpingguhashmap.clear();
		radiogroup.removeAllViews();
		ll_data.removeAllViews();

		for (int i = 0; i < data.size(); i++) {
			final int position = i;
			final PingGu pinggu = data.get(i);
			View convertView = LayoutInflater.from(this).inflate(
					R.layout.item_rypgsecond, null); // 实例化convertView
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (data.get(position).getXiaji() == 1) {
						itemid = data.get(position).getItemid();
						itemname = data.get(position).getItemname();
						title.add(new String[] { itemid, itemname });
						refreshtitle();
						downlaoddata();
					}
				}
			});
			View botemline = convertView.findViewById(R.id.botemline);
			botemline.setVisibility(View.VISIBLE);
			TextView yizhu_item = (TextView) convertView
					.findViewById(R.id.name);
			yizhu_item.setTextSize(18);
			yizhu_item.setTextColor(getResources().getColor(R.color.gray_80));
			yizhu_item.setText(pinggu.getItemname());
			// try {
			// String item = new String(pinggu.getItemname().getBytes(),
			// "utf-8");
			// yizhu_item.setText(item);
			// } catch (UnsupportedEncodingException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			final EditText edit = (EditText) convertView
					.findViewById(R.id.edit);
			CheckBox check = (CheckBox) convertView.findViewById(R.id.check);

			ImageView next = (ImageView) convertView.findViewById(R.id.next);
			final int type = pinggu.getItemtype();
			check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					// TODO Auto-generated method stub
					if (isChecked) {
						saveok = Jsontosaveok(pinggu.getItemid(), 1, edit
								.getText().toString().trim());
						data.get(position).setSelected(1);
						pinggu.setSelected(1);
					} else {
						data.get(position).setSelected(0);
						saveok = Jsontosaveok(pinggu.getItemid(), 0, edit
								.getText().toString().trim());
						pinggu.setSelected(0);

					}
					submitdata(pinggu.getItemid());
				}
			});
			RadioButton radio = (RadioButton) convertView
					.findViewById(R.id.radio);
			radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (isChecked) {
						for (int i = 0; i < data.size(); i++) {
							if (position == i) {
								data.get(i).setSelected(1);
								pinggu.setSelected(1);
							} else {
								data.get(i).setSelected(0);
								pinggu.setSelected(0);
							}
						}
						saveok = Jsontosaveok(pinggu.getItemid(),
								pinggu.getSelected(), edit.getText().toString()
										.trim());
					}
				}
			});
			edit.setText(data.get(position).getItemtextvalue());
			// edit.setOnEditorActionListener(new OnEditorActionListener() {
			// public boolean onEditorAction(TextView v, int actionId,
			// KeyEvent event) {
			// // TODO Auto-generated method stub
			//
			// ((InputMethodManager) PinguUPActivity.this
			// .getSystemService(Context.INPUT_METHOD_SERVICE))
			// .hideSoftInputFromWindow(edit.getWindowToken(),
			// InputMethodManager.HIDE_NOT_ALWAYS);
			// data.get(position).setItemtextvalue(
			// edit.getText().toString().trim());
			// saveok = Jsontosaveok(pinggu.getItemid(),
			// pinggu.getSelected(), edit.getText().toString()
			// .trim());
			// edit.setFocusable(false);
			// pinggu.setItemtextvalue(edit.getText().toString().trim());
			// return false;
			// }
			// });
			

			switch (type) {
			case 0:// 有下级
				next.setVisibility(View.VISIBLE);
				break;
			case 1:// 单选
				RadioButton button = new RadioButton(this);
				button.setBackgroundResource(R.drawable.fillet_radiobutton); // 设置RadioButton的背景图片
				button.setButtonDrawable(R.drawable.selector_radiopg); // 设置按钮的样式
				button.setPadding(70, 10, 0, 10); // 设置文字距离按钮四周的距离
				button.setTextSize(18);
				button.setTextColor(getResources().getColor(R.color.gray_80));
				// radio.setVisibility(View.VISIBLE);
				/*
				 * LayoutParams layoutParams = button.getLayoutParams();
				 * layoutParams.height = 50;
				 * button.setLayoutParams(layoutParams);
				 */
				// if (pinggu.getSelected() == 0) {
				// Statics.ruyuanpingguhashmap.remove(data.get(position)
				// .getItemid());
				// radio.setChecked(false);
				// } else {
				// Statics.ruyuanpingguhashmap.put(data.get(position)
				// .getItemid(), pinggu);
				// radio.setChecked(true);
				// }
				// RadioButton button = (RadioButton) LayoutInflater.from(this)
				// .inflate(R.layout.item_radiobt, null);
				// RadioButton button = new RadioButton(this);
				// button.setTextAppearance(this, R.style.radio_pg);
				button.setText(pinggu.getItemname());
				String itemid2 = pinggu.getItemid();
				Integer id = Integer.valueOf(itemid2);
				button.setId(id);
				radiodata.add(pinggu);
				radiogroup.addView(button,
						LinearLayout.LayoutParams.FILL_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				continue;
				// break;
			case 2:// 多选
				check.setVisibility(View.VISIBLE);
				if (pinggu.getSelected() == 0) {
					Statics.ruyuanpingguhashmap.remove(data.get(position)
							.getItemid());
					check.setChecked(false);
				} else {
					Statics.ruyuanpingguhashmap.put(data.get(position)
							.getItemid(), pinggu);
					check.setChecked(true);
				}
				break;
			case 3:// 输入
				edit.setVisibility(View.VISIBLE);
				radio.setVisibility(View.GONE);
				Statics.ruyuanpingguhashmap.put(data.get(position).getItemid(),
						pinggu);
				break;
			case 4:// 日期
				edit.setVisibility(View.VISIBLE);
				radio.setVisibility(View.GONE);
				edit.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						System.out.println("选择日期");
						Calendar calendar = Calendar.getInstance();
						new DatePickerDialog(PinguUPActivity.this,
								new DatePickerDialog.OnDateSetListener() {

									@Override
									public void onDateSet(DatePicker view,
											int year, int monthOfYear,
											int dayOfMonth) {
										// TODO Auto-generated method stub
										edit.setText(year + "-" + (monthOfYear + 1)
												+ "-" + dayOfMonth);
										data.get(position).setItemtextvalue(
												edit.getText().toString().trim());
										saveok = Jsontosaveok(pinggu.getItemid(),
												pinggu.getSelected(), edit
														.getText().toString()
														.trim());
										edit.setFocusable(false);
										pinggu.setItemtextvalue(edit.getText()
												.toString().trim());
										return;
									}
								}, calendar.get(Calendar.YEAR), calendar
										.get(Calendar.MONTH), calendar
										.get(Calendar.DAY_OF_MONTH)).show();

					}
				});
				edit.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						System.out.println("选择日期___focus" + hasFocus);
						// TODO Auto-generated method stub
						if (hasFocus) {
							System.out.println("选择日期");
							Calendar calendar = Calendar.getInstance();
							new DatePickerDialog(PinguUPActivity.this,
									new DatePickerDialog.OnDateSetListener() {

										@Override
										public void onDateSet(DatePicker view,
												int year, int monthOfYear,
												int dayOfMonth) {
											// TODO Auto-generated method stub
											edit.setText(year + "-" + (monthOfYear + 1)
													+ "-" + dayOfMonth);
											data.get(position).setItemtextvalue(
													edit.getText().toString().trim());
											saveok = Jsontosaveok(pinggu.getItemid(),
													pinggu.getSelected(), edit
															.getText().toString()
															.trim());
											edit.setFocusable(false);
											pinggu.setItemtextvalue(edit.getText()
													.toString().trim());
											return;
										}
									}, calendar.get(Calendar.YEAR), calendar
											.get(Calendar.MONTH), calendar
											.get(Calendar.DAY_OF_MONTH)).show();

						}
					}
				});
				Statics.ruyuanpingguhashmap.put(data.get(position).getItemid(),
						pinggu);
				break;
			}
			if (pinggu.getXiaji() == 1) {
				next.setVisibility(View.VISIBLE);
			} else {
				next.setVisibility(View.GONE);
			}
			editdata.add(pinggu);
			ll_data.addView(convertView);
		}
	}

	// 提交数据
	void upbd() {
		MyUntils.showProgress(this, "加载数据");
		for (int i = 0; i < ll_data.getChildCount(); i++) {
			System.out.println("data.get=="+data.get(i).toString());
			PingGu pinggu = data.get(i);
			EditText edit = (EditText) ll_data.getChildAt(i).findViewById(
					R.id.edit);
			String text = edit.getText().toString();
			pinggu.setItemtextvalue(text);
			Statics.ruyuanpingguhashmap.put(pinggu.getItemid(), pinggu);
		}
		if (cheradio != -1) {
			PingGu pinggu = new PingGu();
			pinggu.setItemid(cheradio + "");
			pinggu.setSelected(1);
			pinggu.setItemtextvalue(radiotext);
			Statics.ruyuanpingguhashmap.put(cheradio, pinggu);
		}
		ThreadPoolManager.getInstance().addTask(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				String patient_id = Statics.patientdesc.getPatient_id();
				String zid = Statics.patientdesc.getZy_id();

				res = MyUntils.submitRYdata(Statics.SubmitMRPingGuURL,patient_id, zid, itemid, "",Statics.user.getUid());
				Message msg = new Message();
				msg.what = 102;
				mhandler.sendMessage(msg);
			}
		});
	}

	private String Jsontosaveok(String itemid, int i, String txt) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("itemid", itemid);
			obj.put("savetype", i);
			obj.put("txt", txt);
			System.out.println("obj.toString()=" + obj.toString());
			return obj.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void submitdata(final String saveid) {
		MyUntils.getUersInfo(this);
		ThreadPoolManager.getInstance().addTask(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				MyUntils.singlesubmit(Statics.SubmitMRPingGuURL,Statics.patientdesc.getPatient_id(),
						Statics.patientdesc.getZy_id(), saveid, saveok,Statics.user.getUid());
			}
		});
	}
}
