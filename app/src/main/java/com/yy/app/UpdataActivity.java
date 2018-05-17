package com.yy.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yy.cookies.Statics;
import com.yy.until.ExitManager;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

public class UpdataActivity extends Activity implements OnClickListener {
	Handler mhandler;
	private TextView activity_title_name;
	private Button activity_title_ok;
	private Button activity_title_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updata);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		activity_title_name = (TextView) findViewById(R.id.activity_title_name);
		activity_title_name.setText("提交数据");
		activity_title_ok = (Button) findViewById(R.id.activity_title_ok);
		activity_title_ok.setText("提交");
		activity_title_ok.setOnClickListener(this);
		activity_title_back = (Button) findViewById(R.id.activity_title_back);
		activity_title_back.setOnClickListener(this);

		mhandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 101:
					MyUntils.cancleProgress();
//					if (data != null) {
//						 adapter = new RYpingguAdapter(data,
//						 UpdataActivity.this);
//						 listview.setAdapter(adapter);
//					} else {
//						MyUntils.myToast(UpdataActivity.this, "获取信息失败");
//					}
					break;

				default:
					break;
				}
			}

		};
//		downlaoddata();
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_title_ok:

			break;

		case R.id.activity_title_back:
			finish();
			break;
		}
	}

	void downlaoddata() {
		MyUntils.showProgress(this, "加载数据中......");

		ThreadPoolManager.getInstance().addTask(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				String patient_id = Statics.patientdesc.getPatient_id();
				String zid = Statics.patientdesc.getZy_id();

//				data = MyUntils.getPinggu(patient_id, zid);
				Message msg = new Message();
				msg.what = 101;
				mhandler.sendMessage(msg);
			}
		});
	}
}
