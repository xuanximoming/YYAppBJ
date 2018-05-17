package com.yy.keyapp.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.yy.app.R;
import com.yy.keyapp.module.base.NBaseActivity;
import com.yy.until.MyUntils;

public class WelcomeActivity extends NBaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// //取消标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// //取消状态栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		this.setContentView(R.layout.activity_welcome);

		MyUntils.getUrl(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it = new Intent(WelcomeActivity.this,
                        LoginActivity.class);
                startActivity(it);
                finish();
            }
        },1500);

	}

}
