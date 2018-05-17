package com.yy.app;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.yy.adapter.newad.RYpingguAdapter;
import com.yy.cookies.Statics;
import com.yy.entity.PingGu;
import com.yy.until.ExitManager;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

public class MRPingguActivity extends Activity implements OnClickListener {
	TextView activity_title_name;
	Button activity_title_back, activity_title_ok;
	Handler mhandler;
	List<PingGu> data;
	GridView gridview;
	BaseAdapter adapter;
	List<String> strlist;
	private ListView listview;
	private String date;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rypinggulist);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		date=this.getIntent().getStringExtra("date");
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		activity_title_name = (TextView) findViewById(R.id.activity_title_name);
		activity_title_name.setText("每日评估");
		activity_title_ok = (Button) findViewById(R.id.activity_title_ok);
		activity_title_ok.setText("添加");
		activity_title_ok.setVisibility(View.GONE);
		activity_title_ok.setOnClickListener(this);
		activity_title_back = (Button) findViewById(R.id.activity_title_back);
		activity_title_back.setOnClickListener(this);

		listview = (ListView) findViewById(R.id.listview);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String itemid = data.get(position).getItemid();
				String itemname = data.get(position).getItemname();
				Intent intent = new Intent(MRPingguActivity.this,
						PinguUPActivity.class);
				intent.putExtra("itemid", itemid);
				intent.putExtra("itemname", itemname);
				intent.putExtra("date", date);
				startActivity(intent);
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
							MyUntils.myToast(MRPingguActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}else{
							MyUntils.myToast(MRPingguActivity.this,"此病人暂无每日评估信息");
						}
					} else if(data.size()>0){
						adapter = new RYpingguAdapter(data,
								MRPingguActivity.this, Integer.parseInt(itemid));
						listview.setAdapter(adapter);
					} else{
						MyUntils.myToast(MRPingguActivity.this,"此病人暂无每日评估信息");
					}
					break;

				default:
					break;
				}
			}

		};
		downlaoddata();
	}

	private String itemid = "0";

	void downlaoddata() {
		MyUntils.showProgress(this, "加载数据");

		ThreadPoolManager.getInstance().addTask(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				String patient_id = Statics.patientdesc.getPatient_id();
				String zid = Statics.patientdesc.getZy_id();

				data = MyUntils.getMRPinggu(patient_id, zid, itemid,date);
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
			startActivity(new Intent(this, AddmrpingguActivity.class));
			break;
		}
	}
}
