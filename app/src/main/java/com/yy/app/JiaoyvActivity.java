package com.yy.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yy.adapter.JiaoyvlistAdapter;
import com.yy.cookies.Statics;
import com.yy.entity.PingGu;
import com.yy.until.ExitManager;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

public class JiaoyvActivity extends Activity implements OnClickListener {
	TextView activity_title_name;
	Button activity_title_back, activity_title_ok;
	Handler mhandler;
	List<PingGu> data, childdata;
	GridView gridview;
	JiaoyvlistAdapter adapter;
	List<String> strlist;
	private ListView listview;
	LinearLayout lastdata;// 显示级别
	String itemid ;// 当前请求id
	int cengji = 0;// 当前层级
	List<String> itemids;
	boolean islast = false;
	boolean isfirst = true;
	String lastname;
	boolean isonresem = false;// 刷新或向下一级

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rypinggulist);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		itemid="0";
		
		activity_title_name = (TextView) findViewById(R.id.activity_title_name);
		activity_title_name.setText("教育");
		activity_title_ok = (Button) findViewById(R.id.activity_title_ok);
		activity_title_ok.setText("添加");
		activity_title_ok.setOnClickListener(this);
		activity_title_back = (Button) findViewById(R.id.activity_title_back);
		activity_title_back.setOnClickListener(this);

		itemids = new ArrayList<String>();
		itemids.add(itemid);
		lastdata = (LinearLayout) findViewById(R.id.lastdata);
		// lastdata.setVisibility(View.VISIBLE);
		listview = (ListView) findViewById(R.id.listview);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// 获取点击的 项目id
				// Statics.jiaoyuitemidlist.add(data.get(position).getItemid());
				int xiaji = data.get(position).getXiaji();
				System.out.println(xiaji + "下级");
				if (xiaji == 1) {
					islast = false;
					itemid = data.get(position).getItemid();
					lastname = data.get(position).getItemname();
					isfirst = false;
					downlaoddata();
				} else {
					Intent intent = new Intent(JiaoyvActivity.this,
							jiaoyvbdupActivity.class);
					intent.putExtra("itemid", data.get(position).getItemid());
					// intent.putStringArrayListExtra("jiaoyuitemidlist",
					// (ArrayList<String>) Statics.jiaoyuitemidlist);
					startActivity(intent);
				}

			}
		});
		data = new ArrayList<PingGu>();
		childdata = new ArrayList<PingGu>();
		mhandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 101:
					MyUntils.cancleProgress();
					if (childdata != null) {
						data = childdata;
						adapter = new JiaoyvlistAdapter(data,
								JiaoyvActivity.this);
						listview.setAdapter(adapter);
						if (!isfirst) {
							if (islast) {
								itemids.remove(cengji);
								cengji--;
								lastdata.removeViews(cengji, 1);
							} else {
								lastdata.setVisibility(View.VISIBLE);
								cengji++;
								itemids.add(cengji, itemid);
								addcengji(lastname);
							}
						}
						System.out.println("itemidsxxxxxxxx"
								+ itemids.toString());
					} else {
						if (Statics.internetcode != -1) {
							MyUntils.myToast(JiaoyvActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
						// MyUntils.myToast(JiaoyvActivity.this, "获取信息失败");
						if (!isfirst) {

						}
					}
					break;

				case 102:
					MyUntils.cancleProgress();
					isonresem = false;
					if (childdata != null) {
						data = childdata;
						adapter = new JiaoyvlistAdapter(data,
								JiaoyvActivity.this);
						listview.setAdapter(adapter);
					} else {
						if (Statics.internetcode != -1) {
							MyUntils.myToast(JiaoyvActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
						// MyUntils.myToast(JiaoyvActivity.this, "获取信息失败");
					}
					break;
				}
			}

		};
		// downlaoddata();
	}

	void downlaoddata() {
		MyUntils.showProgress(this, "正在加载");

		ThreadPoolManager.getInstance().addTask(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				String patient_id = Statics.patientdesc.getPatient_id();
				String zid = Statics.patientdesc.getZy_id();
				System.out.println("run---getmyPatient"+patient_id+"....."+ zid+"....."+ itemid);

				
				childdata = MyUntils.getJiaoYuList(patient_id, zid, itemid);
				Message msg = new Message();
				if (isonresem) {
					msg.what = 102;
				} else {
					msg.what = 101;
				}
				mhandler.sendMessage(msg);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_title_back:
			datalast();
			break;

		case R.id.activity_title_ok:
			// startActivity(new Intent(this, AddjiaoyvActivity.class));
			break;
		}
	}

	void addcengji(String name) {
		View view = LayoutInflater.from(this).inflate(R.layout.item_last, null);
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(name);
		lastdata.addView(view);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			datalast();
			return true;
		}
		// 拦截MENU按钮点击事件，让他无任何操作
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	void datalast() {
		islast = true;
		if (cengji == 0) {
			finish();
		} else {
			itemid = itemids.get(cengji - 1);
			downlaoddata();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		data.clear();
		System.out.println("onresum..............");
		isonresem = true;
		downlaoddata();
	}
}
