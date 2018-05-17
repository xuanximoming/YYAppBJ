package com.yy.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yy.adapter.newad.RYpingguAdapter;
import com.yy.cookies.Statics;
import com.yy.entity.BigType;
import com.yy.entity.LittleType;
import com.yy.entity.Patient;
import com.yy.entity.SearchForm;
import com.yy.until.ExitManager;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdvSearchActivity extends Activity implements OnClickListener {
	TextView bq_tv, bc_tv, hldj_tv, zhuyi_tv, patient_name, patient_gender,
			patient_age, patient_ks, patient_hldj, patient_bh,
			activity_title_name;
	Button activity_title_back, activity_title_ok;
	Handler mhandler;
	List<SearchForm> data;
//	ListView listview;
	RYpingguAdapter adapter;
	LinearLayout ll_data;
	private ImageView searchbtn;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advsearch);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		intent = this.getIntent();
		// 初始linearlayout
		ll_data = (LinearLayout) findViewById(R.id.ll_data1);
		activity_title_name = (TextView) findViewById(R.id.activity_title_name);
		activity_title_name.setText("高级搜索");
		activity_title_ok = (Button) findViewById(R.id.activity_title_ok);
		activity_title_ok.setText("搜索");
		activity_title_ok.setOnClickListener(this);
		activity_title_back = (Button) findViewById(R.id.activity_title_back);
		activity_title_back.setOnClickListener(this);

//		listview = (ListView) findViewById(R.id.listview);
		searchbtn = (ImageView) findViewById(R.id.searchbtn);
		searchbtn.setOnClickListener(this);
		data = new ArrayList<SearchForm>();
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
							MyUntils.myToast(AdvSearchActivity.this,
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
							MyUntils.myToast(AdvSearchActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
						// MyUntils.myToast(jiaoyvbdupActivity.this, "获取信息失败");
					}
					break;
				case 102:
					MyUntils.cancleProgress();
					if (Statics.searchpatientlist == null) {
						if (Statics.internetcode != -1) {
							MyUntils.myToast(AdvSearchActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
					} else {
							// 提交成功清空提交值
							Statics.searchitemidhashmap = new HashMap();
							Statics.gaojisearch=1;
							setResult(RESULT_OK, intent); 
							finish();
					}
					break;
				default:
					break;
				}
			}

		};
		downlaoddata();
	}

	public static List<BigType> listbigtype;
	public List<LittleType> listlittletype;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_title_ok:

			break;

		case R.id.activity_title_back:
			finish();
			break;
		case R.id.searchbtn:
			updata();
			break;
		}
	}

	void downlaoddata() {
		MyUntils.showProgress(this, "加载数据");

		ThreadPoolManager.getInstance().addTask(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub

				data = MyUntils.getSearchList();
				Message msg = new Message();
				msg.what = 101;
				mhandler.sendMessage(msg);
			}
		});
	}

	void updata() {
		// 提交表单
		upbd();
		MyUntils.showProgress(this, "正在加载");
		Statics.searchpatientlist = new ArrayList<Patient>();
		try{
		ThreadPoolManager.getInstance().addTask(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("run---getmyPatient");

				Statics.searchpatientlist = MyUntils.Searchlist(
						Statics.user.getUid(), "1", "");
				
				Message msg = new Message();
				msg.what = 102;
				mhandler.sendMessage(msg);
			}
		});
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}

	// 遍历数据列表
	void bldata() {

		for (int i = 0; i < data.size(); i++) {

			final SearchForm jiaoyv = data.get(i);
			View view = LayoutInflater.from(this).inflate(
					R.layout.item_advsearch1, null); // 实例化convertView
			TextView yizhu_item = (TextView) view.findViewById(R.id.name);
			try {
				String item = new String(jiaoyv.getOname().getBytes(), "utf-8");
				yizhu_item.setText(item);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			CheckBox checkbox = (CheckBox) view.findViewById(R.id.checkbox);
			EditText edit = (EditText) view.findViewById(R.id.edit);

			final Button check = (Button) view.findViewById(R.id.check);
			String otype = jiaoyv.getOtype();
			if (otype.equals("2")) {
				edit.setVisibility(View.VISIBLE);
			} else if (otype.equals("1")) {
				check.setVisibility(View.VISIBLE);
				check.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						Builder builder = new AlertDialog.Builder(
								AdvSearchActivity.this);
						builder.setItems(jiaoyv.getOvalues(),
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										System.out.println("which=" + which);
										check.setText(jiaoyv.getOvalues()[which]);
										SearchForm jiaoyuform = new SearchForm();
										jiaoyuform.setOname(jiaoyv.getOname());
										String[] object3 = { jiaoyv
												.getOvalues()[which] };
										System.out.println("object3"
												+ object3.toString());
										jiaoyuform.setOvalues(object3);
										Statics.searchitemidhashmap.put(
												jiaoyv.getSrhid()+"", jiaoyuform);
									}
								});
						builder.show();
					}
				});
			} else if (otype.equals("3")) {
				checkbox.setVisibility(View.VISIBLE);
			}
			ll_data.addView(view);
		}
	}

	// 保存edit里的数据
	void upbd() {
		for (int i = 0; i < ll_data.getChildCount(); i++) {
			SearchForm childdata = data.get(i);
			String otype = childdata.getOtype();
			if (otype.equals("2")) {
				EditText edit = (EditText) ll_data.getChildAt(i).findViewById(
						R.id.edit);
				String text = edit.getText().toString();
				SearchForm jiaoyuform = new SearchForm();
				jiaoyuform.setSrhid(childdata.getSrhid());
				String[] object3 = { text };
				System.out.println("object3" + object3.toString());
				jiaoyuform.setOvalues(object3);
				Statics.searchitemidhashmap.put(childdata.getSrhid(),
						jiaoyuform);
			} else if (otype.equals("3")) {
				CheckBox checkbox = (CheckBox) ll_data.getChildAt(i)
						.findViewById(R.id.checkbox);
				boolean checkres = false;
				checkres = checkbox.isChecked();
				SearchForm jiaoyuform = new SearchForm();
				jiaoyuform.setSrhid(childdata.getSrhid());
				String[] object3 = { checkres + "" };
				System.out.println("object3" + object3.toString());
				jiaoyuform.setOvalues(object3);
				Statics.searchitemidhashmap.put(childdata.getSrhid(),
						jiaoyuform);
			}
		}
	}
}
