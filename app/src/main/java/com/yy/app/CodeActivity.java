package com.yy.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CodeActivity  extends Activity{
	private final String ACTION_NAME = "发送广播";
	private Button mBtnMsgEvent = null;
	//EditText editText1;
	//Button button1;
	EditText editText1;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//注册广播
		registerBoradcastReceiver();
		
		/*LinearLayout mLinearLayout = new LinearLayout(this);
		mBtnMsgEvent = new Button(this);
		editText1= new EditText(this);
		mBtnMsgEvent.setText("发送广播");
		mLinearLayout.addView(mBtnMsgEvent);
		mLinearLayout.addView(editText1);
		setContentView(mLinearLayout);
		
		mBtnMsgEvent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent(ACTION_NAME);
				mIntent.putExtra("yaner", "发送广播，相当于在这里传送数据");
				
				//发送广播
				sendBroadcast(mIntent);
			}
		});*/
	}
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			String text = intent.getStringExtra("value");
			//editText1.setText(text);
			//拿到扫描的返回值
			System.out.println("text="+text);
		}
		
	};
	public void registerBoradcastReceiver(){
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(ACTION_NAME);
		IntentFilter barcodeFilter = new IntentFilter("com.ge.action.barscan");
		//注册广播      
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

}
