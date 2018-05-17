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
import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

public class BanQuanActivity extends Activity implements OnClickListener {

	private TextView bqtitle;
	private TextView bqtext;//
	String res = "";
	Handler mhandler ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banquan);
		MyUntils.getUersInfo(this);
		ExitManager.getInstance().addActivity(this);
		initview();
	}

	void initview() {
		TextView titlename = (TextView) findViewById(R.id.activity_title_name);
		titlename.setText("版权信息");
		Button back = (Button) findViewById(R.id.activity_title_back);
		back.setOnClickListener(this);
		Button ok = (Button) findViewById(R.id.activity_title_ok);
		ok.setVisibility(View.GONE);

		bqtext = (TextView) findViewById(R.id.bqtext);
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
				res = MyUntils.getBanQuan();

				Message msg = new Message();
				msg.what = 101;
				mhandler.sendMessage(msg);
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
					if(!"".equals(res)){
						bqtext.setText(""+res);
					}else{
						if (Statics.internetcode != -1) {
							MyUntils.myToast(BanQuanActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
					}
					break;

				default:
					break;
				}
			}

		};
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_title_back:
			finish();
			break;
		case R.id.change_bt:
			
			
			break;

		default:
			break;
		}
	}
}
