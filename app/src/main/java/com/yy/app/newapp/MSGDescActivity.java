package com.yy.app.newapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yy.app.R;
import com.yy.cookies.Statics;
import com.yy.entity.Messages;
import com.yy.until.ExitManager;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

public class MSGDescActivity extends Activity implements OnClickListener {
	private int msgid;
	private TextView msgdes_title;
	private TextView msgdes_time;
	private TextView msgdes_content;
	private int msgtype;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messagedesc);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		msgid = this.getIntent().getIntExtra("msgid", -1);
		msgtype = this.getIntent().getIntExtra("msgtype", -1);
		initview();
		getmsgdesc();
	}

	Button ok;

	void initview() {
		TextView titlename = (TextView) findViewById(R.id.activity_title_name);
		if (msgtype == 0 || msgtype == 1) {
			titlename.setText("消息详情");
		} else {
			titlename.setText("任务详情");
		}
		Button back = (Button) findViewById(R.id.activity_title_back);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(this);
		ok = (Button) findViewById(R.id.activity_title_ok);
		ok.setText("处理");
		ok.setVisibility(View.GONE);
		ok.setOnClickListener(this);

		msgdes_title = (TextView) findViewById(R.id.msgdes_title);
		msgdes_time = (TextView) findViewById(R.id.msgdes_time);
		msgdes_content = (TextView) findViewById(R.id.msgdes_content);
	}

	private Handler myhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 101:
				if (msgdesc != null) {
					msgdes_title.setText(msgdesc.getMsgTitle());
					msgdes_time.setText(msgdesc.getMsgTime());
					msgdes_content.setText(msgdesc.getMsgMemo());
					if (msgdesc.getMsgType() == 2 || msgdesc.getMsgType() == 3) {
						// 任务
						ok.setVisibility(View.VISIBLE);
						if (msgdesc.getMsgread() == 1) {
							setbtnyichuli();
						}
					}
				} else {
					if (Statics.internetcode != -1) {
						MyUntils.myToast(MSGDescActivity.this,
								Statics.internetstate[Statics.internetcode]);
					}
				}
				break;
			case 102:
				if (success) {
					MyUntils.myToast(MSGDescActivity.this, "处理成功");
					setbtnyichuli();
				} else {
					if (Statics.internetcode != -1) {
						MyUntils.myToast(MSGDescActivity.this,
								Statics.internetstate[Statics.internetcode]);
					}
					MyUntils.myToast(MSGDescActivity.this, "处理失败");
				}
				break;
			default:
				break;
			}
		}

	};

	private void setbtnyichuli() {
		ok.setText("已处理");
		i = 1;
		ok.setBackgroundResource(R.drawable.circle_graybg);
		ok.setTextColor(getResources().getColor(R.color.black));
	}

	private Messages msgdesc;

	void getmsgdesc() {
		ThreadPoolManager.getInstance().addTask(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				msgdesc = MyUntils.getMsgDesc(Statics.user.getUid(), msgid);
				myhandler.sendEmptyMessage(101);
			}
		});
	}

	private boolean success;

	void domessage() {
		ThreadPoolManager.getInstance().addTask(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				success = MyUntils.doMsg(Statics.user.getUid(), msgid);
				myhandler.sendEmptyMessage(102);
			}
		});
	}
	int i = 0;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_title_back:
			finish();
			break;
		// 处理
		case R.id.activity_title_ok:
			if (i == 0) {
				domessage();
			}
			break;
		default:
			break;
		}
	}
}
