package com.yy.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yy.cookies.Statics;
import com.yy.until.ExitManager;
import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

public class AccountManagerActivity extends Activity implements OnClickListener {

	private TextView tvname;
	private TextView tvbingqu;//变更为科室
	private TextView tvusername;
	private EditText etpassword;
	private Button change_bt;
	boolean res = false; //返回结果

	Handler mhandler ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accmanager);
		MyUntils.getUersInfo(this);
		ExitManager.getInstance().addActivity(this);
		initview();
	}

	void initview() {
		TextView titlename = (TextView) findViewById(R.id.activity_title_name);
		titlename.setText("护士账户管理");
		Button back = (Button) findViewById(R.id.activity_title_back);
		back.setOnClickListener(this);
		Button ok = (Button) findViewById(R.id.activity_title_ok);
		ok.setVisibility(View.GONE);

		tvname = (TextView) findViewById(R.id.tvname);
		tvname.setText(Statics.user.getUtname());
		tvbingqu = (TextView) findViewById(R.id.tvbingqu);
		tvbingqu.setText(Statics.user.getKeshi());
		tvusername = (TextView) findViewById(R.id.tvusername);
		tvusername.setText(Statics.UNAME);
		etpassword = (EditText) findViewById(R.id.etpassword);
		change_bt = (Button) findViewById(R.id.change_bt);
		change_bt.setOnClickListener(this);
		
		mhandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 101:
					MyUntils.cancleProgress();
					if(res){
						MyUntils.myToast(AccountManagerActivity.this, "密码修改成功");
					}else{
						if (Statics.internetcode != -1) {
							MyUntils.myToast(AccountManagerActivity.this,
									Statics.internetstate[Statics.internetcode]);
						}
						MyUntils.myToast(AccountManagerActivity.this, "密码修改失败");
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
			final String pass = etpassword.getText().toString();
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
					String uid = Statics.user.getUid();
					res = MyUntils.ChangePassword(uid, pass);

					Message msg = new Message();
					msg.what = 101;
					mhandler.sendMessage(msg);
				}
			});
			
			break;

		default:
			break;
		}
	}
}
