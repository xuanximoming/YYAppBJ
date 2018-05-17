package com.yy.keyapp.module.patient;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.yy.app.AccountManagerActivity;
import com.yy.app.R;
import com.yy.cookies.Statics;
import com.yy.key.QRcodeScan.activity.CaptureActivity;


public class DialogActivity extends Activity implements OnClickListener{
	private LinearLayout layout01,layout02,layout03,layout04;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog);

		initView();
	}


	private void initView(){
		layout01 = (LinearLayout)findViewById(R.id.llayout01);
		layout02 = (LinearLayout)findViewById(R.id.llayout02);
		layout03 = (LinearLayout)findViewById(R.id.llayout03);
		layout04 = (LinearLayout)findViewById(R.id.llayout04);
		layout01.setOnClickListener(this);
		layout02.setOnClickListener(this);
		layout03.setOnClickListener(this);
		layout04.setOnClickListener(this);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.llayout01:

				break;
			case R.id.llayout02:

				break;
			case R.id.llayout03:
				try {
					startActivity(new Intent(DialogActivity.this,AccountManagerActivity.class));
					finish();
				}
				catch (Exception ex){
					System.out.println(ex.getMessage());
				}
				break;
			case R.id.llayout04:
				try {
					startActivityForResult(new Intent(DialogActivity.this, CaptureActivity.class), CaptureActivity.REQ_CODE);
					//startActivity(new Intent(DialogActivity.this, com.yy.key.activity.CaptureActivity.class));

				}catch (Exception ex){
					System.out.println(ex.getMessage());
				}
				break;
			default:
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case CaptureActivity.REQ_CODE:
				switch (resultCode) {
					case RESULT_OK:
						Statics.Qcvalue = data.getStringExtra("change01");
						break;
					case RESULT_CANCELED:
						if (data != null) {
							// for some reason camera is not working correctly
							}
						break;
				}
				break;
		}
		finish();
	}
}
