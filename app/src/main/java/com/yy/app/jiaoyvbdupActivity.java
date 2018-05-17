package com.yy.app;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yy.cookies.Statics;
import com.yy.entity.JiaoYuForm;
import com.yy.until.ExitManager;
import com.yy.until.MyUntils;
import com.yy.until.StrTool;
import com.yy.until.ThreadPoolManager;

public class jiaoyvbdupActivity extends Activity implements OnClickListener {
	TextView activity_title_name;
	Button activity_title_back, activity_title_ok;
	Handler mhandler;
	public static List<JiaoYuForm> data = new ArrayList<JiaoYuForm>();
	GridView gridview;
	BaseAdapter adapter;
	List<String> strlist;
	LinearLayout ll_data;
	// private ListView listview;
	String itemid = "0";
	Button uplowd;
	boolean res = false; // 教育表单提交结果

	public static List<JiaoYuForm> jiaoyuitemidlist1 = new ArrayList<JiaoYuForm>();// 记录教育列表选择的Oname：表单项Ovalue：存入值

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jiaoyvbd);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		Intent intent = getIntent();
		itemid = intent.getStringExtra("itemid");
		// 获取树中 父级itemid
		// jiaoyuitemidlist1 =
		// intent.getStringArrayListExtra("jiaoyuitemidlist");
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		activity_title_name = (TextView) findViewById(R.id.activity_title_name);
		activity_title_name.setText("教育表单");
		activity_title_ok = (Button) findViewById(R.id.activity_title_ok);
		activity_title_ok.setText("提交");
		activity_title_ok.setVisibility(View.VISIBLE);
		activity_title_ok.setOnClickListener(this);
		activity_title_back = (Button) findViewById(R.id.activity_title_back);
		activity_title_back.setOnClickListener(this);

		ll_data = (LinearLayout) findViewById(R.id.ll_data);
		uplowd = (Button) findViewById(R.id.uplowd);
		uplowd.setVisibility(View.GONE);
		uplowd.setOnClickListener(this);
		// listview = (ListView) findViewById(R.id.listview);
		// listview.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// // TODO Auto-generated method stub
		//
		// // int xiaji = childdata.get(position).getXiaji();
		// // if (xiaji == 1) {
		// // itemid = childdata.get(position).getItemid();
		// // downlaoddata();
		// //
		// // }else {
		// // // Intent intent = new
		// // Intent(jiaoyvbdActivity.this,jiaoyvbdAdapter);
		// // }
		// //获取当前表单选择项id
		// //jiaoyuitemidlist1.add(jiaoyuform);
		// }
		// });
		data = new ArrayList<JiaoYuForm>();
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
							MyUntils.myToast(jiaoyvbdupActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
					} else if (data.size() > 0) {
						System.out.println(data.toString());
						// adapter = new jiaoyvbdAdapter(data,
						// jiaoyvbdupActivity.this);
						// listview.setAdapter(adapter);
						bldata();
					} else {
						if (Statics.internetcode != -1) {
							MyUntils.myToast(jiaoyvbdupActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
						// MyUntils.myToast(jiaoyvbdupActivity.this, "获取信息失败");
					}
					break;
				case 102:
					MyUntils.cancleProgress();

					if (res) {
						MyUntils.myToast(jiaoyvbdupActivity.this, "表单提交成功");
						finish();
					} else {
						MyUntils.myToast(jiaoyvbdupActivity.this, "表单提交失败");
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
		MyUntils.showProgress(this, "正在加载");

		ThreadPoolManager.getInstance().addTask(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				//System.out.println("run---getmyPatient"+itemid);
				String patient_id = Statics.patientdesc.getPatient_id();
				String zid = Statics.patientdesc.getZy_id();

				data = MyUntils.getJiaoYuFormList(patient_id, zid, itemid);
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
			updata();
			break;
		case R.id.uplowd:
			break;
		}
	}

	void updata() {
		// 提交表单
		upbd();
		MyUntils.showProgress(this, "正在加载");

		ThreadPoolManager.getInstance().addTask(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("run---getmyPatient"+itemid);
				String patient_id = Statics.patientdesc.getPatient_id();
				String zid = Statics.patientdesc.getZy_id();

				res = MyUntils.submitJiaoYuFormList(patient_id, zid, itemid,Statics.user.getUtname());
				// if(res){
				// 提交成功清空提交值
				Statics.jiaoyuitemidhashmap = new HashMap();
				// }
				Message msg = new Message();
				msg.what = 102;
				mhandler.sendMessage(msg);
			}
		});
	}

	// 遍历数据列表
	void bldata() {

		for (int i = 0; i < data.size(); i++) {

			final JiaoYuForm jiaoyv = data.get(i);
			View view = LayoutInflater.from(this).inflate(
					R.layout.item_jiaoyvbd, null); // 实例化convertView
			TextView yizhu_item = (TextView) view.findViewById(R.id.name);
			try {
				String item = new String(jiaoyv.getOname().getBytes(), "utf-8");
				yizhu_item.setText(item);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			EditText edits = (EditText) view.findViewById(R.id.edits);
			final EditText edit = (EditText) view.findViewById(R.id.edit);
			edit.setGravity(Gravity.RIGHT);
//			if (i==0) {
//			edit.setOnClickListener(new View.OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					System.out.println("选择日期");
//					Calendar calendar = Calendar.getInstance();
//					new DatePickerDialog(jiaoyvbdupActivity.this,
//							new DatePickerDialog.OnDateSetListener() {
//
//								@Override
//								public void onDateSet(DatePicker view,
//										int year, int monthOfYear,
//										int dayOfMonth) {
//									// TODO Auto-generated method stub
//									edit.setText(year + "-" + (monthOfYear + 1)
//											+ "-" + dayOfMonth );
//									return;
//								}
//								
//							}, calendar.get(Calendar.YEAR), calendar
//									.get(Calendar.MONTH), calendar
//									.get(Calendar.DAY_OF_MONTH)).show();
//
//				}
//			});
//			edit.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//				@Override
//				public void onFocusChange(View v, boolean hasFocus) {
//					System.out.println("选择日期___focus" + hasFocus);
//					// TODO Auto-generated method stub
//					if (hasFocus) {
//						System.out.println("选择日期");
//						Calendar calendar = Calendar.getInstance();
//						new DatePickerDialog(jiaoyvbdupActivity.this,
//								new DatePickerDialog.OnDateSetListener() {
//
//									@Override
//									public void onDateSet(DatePicker view,
//											int year, int monthOfYear,
//											int dayOfMonth) {
//										// TODO Auto-generated method stub
//										edit.setText(year + "-" + (monthOfYear + 1)
//												+ "-" + dayOfMonth);
//										return;
//									}
//								}, calendar.get(Calendar.YEAR), calendar
//										.get(Calendar.MONTH), calendar
//										.get(Calendar.DAY_OF_MONTH)).show();
//
//					}
//				}
//			});}
			// edits.setText(jiaoyv.getTexts());
			// edit.setText(jiaoyv.getTexts());
			final Button check = (Button) view.findViewById(R.id.check);
			String otype = jiaoyv.getOtype();
			if (otype.equals("0")) {
				edit.setVisibility(View.VISIBLE);
				if (!StrTool.isnull(jiaoyv.getTexts())) {
					edit.setText(jiaoyv.getTexts());
				}
			} else if (otype.equals("1")) {
				final String[] items=new String[jiaoyv.getOvalues().size()];
				for (int j = 0; j < items.length; j++) {
					items[j]=jiaoyv.getOvalues().get(j)[0];
					if (jiaoyv.getOvalues().get(j)[1].equals("1")) {
						check.setText(jiaoyv.getOvalues().get(j)[0]);
					}
				}
				check.setVisibility(View.VISIBLE);
				check.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						Builder builder = new AlertDialog.Builder(
								jiaoyvbdupActivity.this);
						builder.setItems(items,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										System.out.println("which=" + which);
										check.setText(jiaoyv.getOvalues().get(which)[0]);
										JiaoYuForm jiaoyuform = new JiaoYuForm();
										jiaoyuform.setOname(jiaoyv.getOname());
										jiaoyuform.setOtype(jiaoyv.getOtype());
										for (int j = 0; j < jiaoyv.getOvalues().size(); j++) {
											jiaoyv.getOvalues().get(j)[1]="0";
											if (j==which) {
												jiaoyv.getOvalues().get(j)[1]="1";
											}
										}
										jiaoyuform.setOvalues(jiaoyv.getOvalues());
										Statics.jiaoyuitemidhashmap.put(
												jiaoyv.getOname(), jiaoyuform);
									}
								});
						builder.show();
					}
				});
			} else if (otype.equals("2")) {
				edits.setVisibility(View.VISIBLE);
				edits.setText(""+jiaoyv.getTexts());
			}
			ll_data.addView(view);
		}
	}

	// 保存edit里的数据
	void upbd() {
		for (int i = 0; i < ll_data.getChildCount(); i++) {
			JiaoYuForm childdata = data.get(i);
			String otype = childdata.getOtype();
			if (otype.equals("0")) {
				EditText edit = (EditText) ll_data.getChildAt(i).findViewById(
						R.id.edit);
				String text = edit.getText().toString();
				JiaoYuForm jiaoyuform = new JiaoYuForm();
				jiaoyuform.setOname(childdata.getOname());
				jiaoyuform.setOtype(childdata.getOtype());
//				String[] object3 = { text };
//				System.out.println("object3" + object3.toString());
				jiaoyuform.setTexts(text);
				Statics.jiaoyuitemidhashmap.put(childdata.getOname(),
						jiaoyuform);
			} else if (otype.equals("2")) {
				EditText edits = (EditText) ll_data.getChildAt(i).findViewById(
						R.id.edits);
				String text = edits.getText().toString();
				JiaoYuForm jiaoyuform = new JiaoYuForm();
				jiaoyuform.setOname(childdata.getOname());
				jiaoyuform.setOtype(childdata.getOtype());
//				String[] object3 = { text };
//				System.out.println("object3" + object3.toString());
				jiaoyuform.setTexts(text);
				Statics.jiaoyuitemidhashmap.put(childdata.getOname(),
						jiaoyuform);
			}
		}
	}
}
