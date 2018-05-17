package com.yy.app.newapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.yy.app.R;
import com.yy.cookies.SharedPrefs;
import com.yy.cookies.Statics;
import com.yy.until.ExitManager;
import com.yy.until.MyUntils;
import com.yy.until.StrTool;
import com.yy.until.ThreadPoolManager;
import com.yy.until.VersionUtils;

public class SysSettingActivity extends Activity implements OnClickListener {

	private EditText edip;
	private EditText etduankou;
	private EditText etchuanma;
	private Button ok_bt;
    private TextView versionCodeText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_syssetting);
		Statics.internettime = 3;
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		initview();
	}

	void initview() {
		TextView titlename = (TextView) findViewById(R.id.activity_title_name);
		titlename.setText("系统设置");
		Button back = (Button) findViewById(R.id.activity_title_back);
		back.setOnClickListener(this);
		Button ok = (Button) findViewById(R.id.activity_title_ok);
		ok.setVisibility(View.GONE);
		ok_bt = (Button) findViewById(R.id.ok_bt);
		ok_bt.setOnClickListener(this);
		edip = (EditText) findViewById(R.id.etip);
		etduankou = (EditText) findViewById(R.id.etduankou);
		etchuanma = (EditText) findViewById(R.id.etchuanma);
        versionCodeText= (TextView) findViewById(R.id.versionCode);
        versionCodeText.setText("v"+VersionUtils.getVersion(this));
		edip.setText(Statics.IP);
		etduankou.setText(Statics.Port);
		etchuanma.setText(Statics.API);
		etchuanma.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub

				((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(SysSettingActivity.this
								.getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);

				return false;
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
		// 完成
		case R.id.ok_bt:
			String IP = edip.getText().toString().trim();
			String DuanKou = etduankou.getText().toString().trim();
			String ChuanMa = etchuanma.getText().toString().trim();
			if (!StrTool.isnull(IP) && !StrTool.isnull(DuanKou) && !StrTool.isnull(ChuanMa)) {
				SharedPrefs.writeIP(this, IP);
				SharedPrefs.writeDuanKou(this, DuanKou);
				SharedPrefs.writeChuanMa(this, ChuanMa);
				MyUntils.getUrl(this);
				getdo();
			} else {
				MyUntils.myToast(this, "请完整填写信息");
			}
			break;

		default:
			break;
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 101:
				System.out.println("link=" + link);
				if (link) {
					MyUntils.myToast(SysSettingActivity.this, "设置成功");
					finish();
				} else {
					if (Statics.internetcode != -1) {
						MyUntils.myToast(SysSettingActivity.this,
								Statics.internetstate[Statics.internetcode]);
					}
				}
				break;

			default:
				break;
			}
		};
	};

	private boolean link;

	void getdo() {
		ThreadPoolManager.getInstance().addTask(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				link = MyUntils.link();
				handler.sendEmptyMessage(101);
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Statics.internettime = 6;
		super.onDestroy();
	}
}
