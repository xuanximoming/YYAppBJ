package com.yy.app;

import android.app.Activity;
import android.app.ProgressDialog;
//import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;

public class WebViewActivity extends Activity implements OnClickListener {

	public static final int REFRESH_LOGIN = 1;
	public ProgressDialog pd;
	/** Called when the activity is first created. */
	WebView mWebView = null;
	String htmlurl = "";
	String title;
	private TextView activity_title_name;
	private Button activity_title_ok;
	private Button activity_title_back;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		htmlurl = this.getIntent().getStringExtra("htmlurl");
		title = this.getIntent().getStringExtra("title");
		System.out.println(title + htmlurl);
		activity_title_name = (TextView) findViewById(R.id.activity_title_name);
		activity_title_name.setText(title);
		activity_title_ok = (Button) findViewById(R.id.activity_title_ok);
		activity_title_ok.setText("提交");
		activity_title_ok.setOnClickListener(this);
		activity_title_back = (Button) findViewById(R.id.activity_title_back);
		activity_title_back.setOnClickListener(this);
	}

	private class HelloWebViewClient extends WebViewClient {
		// 在WebView中而不是默认浏览器中显示页面
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			view.loadUrl(url);
			return true;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("这是暂停方法");
		if (null == mWebView) {
			init();
		}
	}

	public void init() {

		// 网络监察
		if (HttpUtil.checkNet(this)) {
			mWebView = (WebView) findViewById(R.id.chromeView);
			mWebView.getSettings().setJavaScriptEnabled(true);
			// 设置可以支持缩放
			mWebView.getSettings().setSupportZoom(true);
			// 设置默认缩放方式尺寸是far
			mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
			// 设置出现缩放工具
			mWebView.getSettings().setBuiltInZoomControls(true);
			// 让网页自适应屏幕宽度
			// webView: 类WebView的实例
			mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			mWebView.getSettings().setFixedFontFamily("25");
			mWebView.getSettings().setUseWideViewPort(true);
			mWebView.getSettings().setLoadWithOverviewMode(true);
//			mWebView.setWebChromeClient(new WebChromeClient() {
//				@Override
//				public boolean onJsConfirm(WebView view, String url,
//						String message, JsResult result) {
//					// TODO Auto-generated method stub
//					view.loadUrl(htmlurl);
//					return true;
//				}
//
//			});
			mWebView.loadUrl(htmlurl);
//			mWebView.setWebViewClient(new HelloWebViewClient() {
//				public void onReceivedSslError(WebView view,
//						SslErrorHandler handler, SslError error) {
//					// handler.cancel(); // Android默认的处理方式
//					handler.proceed(); // 接受所有网站的证书
//					// handleMessage(Message msg); // 进行其他处理
//				}
//			});

		} else { // 弹出网络异常对话框

			MyUntils.myToast(WebViewActivity.this, "连接失败，请检查服务器设置");

		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_title_back:
			finish();
			break;
		case R.id.activity_title_bt2:
			// 数据库入库操作
			break;
		default:
			break;
		}
	}

}