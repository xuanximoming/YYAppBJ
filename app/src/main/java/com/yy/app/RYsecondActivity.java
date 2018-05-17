package com.yy.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.yy.adapter.RYpgSecondAdapter;
import com.yy.cookies.Statics;
import com.yy.entity.BigType;
import com.yy.entity.LittleType;
import com.yy.entity.PingGu;
import com.yy.until.ExitManager;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

public class RYsecondActivity extends Activity implements OnClickListener {
	TextView activity_title_name;
	Button activity_title_back, activity_title_ok;
	Handler mhandler;
	List<PingGu> data;
	ListView listview;
	RYpgSecondAdapter adapter;
	String itemid, itemname;
	private TextView one_title;
	private List<String[]> title;
	private Button uplowd;
	boolean res = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rypinggusecond);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		Intent intent = getIntent();
		itemid = intent.getStringExtra("itemid");
		itemname = intent.getStringExtra("itemname");
		title=new ArrayList<String[]>();
		title.add(new String[] { itemid, itemname });
		refreshtitle();
		init();
	}

	private void refreshtitle() {
		one_title = (TextView) findViewById(R.id.one_title);
		one_title.setText(title.get(title.size()-1)[1]);
	};

	private void init() {
		// TODO Auto-generated method stub
		activity_title_name = (TextView) findViewById(R.id.activity_title_name);
		activity_title_name.setText("评估项");
		activity_title_ok = (Button) findViewById(R.id.activity_title_ok);
		activity_title_ok.setText("提交");
		activity_title_ok.setOnClickListener(this);
		activity_title_back = (Button) findViewById(R.id.activity_title_back);
		activity_title_back.setOnClickListener(this);
		
		uplowd = (Button) findViewById(R.id.uplowd);
//		uplowd.setVisibility(View.GONE);
		uplowd.setOnClickListener(this);
		
		listview = (ListView) findViewById(R.id.listview);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (data.get(position).getXiaji()==1) {
					itemid = data.get(position).getItemid();
					itemname = data.get(position).getItemname();
					title.add(new String[] { itemid, itemname });
					refreshtitle();
					downlaoddata();
				}
			}
		});
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
							MyUntils.myToast(RYsecondActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
					} else {
						adapter = new RYpgSecondAdapter(data,
								RYsecondActivity.this);
						listview.setAdapter(adapter);
					}
					break;

				case 102:
					//提交完成处理
					if(res){
						MyUntils.myToast(RYsecondActivity.this,"保存成功！");
						Statics.ruyuanpingguhashmap = new HashMap();
						finish();
					}else{
						MyUntils.myToast(RYsecondActivity.this,"保存失败！");
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
			if (title.size()==1) {
				finish();
			}else {
				title.remove(title.size()-1);
				refreshtitle();
				itemid=title.get(title.size()-1)[0];
				downlaoddata();
			}
			break;
		case R.id.uplowd:
			ThreadPoolManager.getInstance().addTask(new Runnable() {
				public void run() {
					// TODO Auto-generated method stub
					String patient_id = Statics.patientdesc.getPatient_id();
					String zid = Statics.patientdesc.getZy_id();

					res = MyUntils.submitRYdata(Statics.SubmitPingGuURL,patient_id, zid, itemid,"",Statics.user.getUid());
					Message msg = new Message();
					msg.what = 102;
					mhandler.sendMessage(msg);
				}
			});
			break;
		}
	}

	void downlaoddata() {
		MyUntils.showProgress(this, "加载数据");

		ThreadPoolManager.getInstance().addTask(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				String patient_id = Statics.patientdesc.getPatient_id();
				String zid = Statics.patientdesc.getZy_id();

				data = MyUntils.getRYPinggu(patient_id, zid, itemid);
				Message msg = new Message();
				msg.what = 101;
				mhandler.sendMessage(msg);
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (title.size()==1) {
				finish();
			}else {
				title.remove(title.size()-1);
				refreshtitle();
				itemid=title.get(title.size()-1)[0];
				downlaoddata();
			}
			return true;
		}
		// 拦截MENU按钮点击事件，让他无任何操作
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
