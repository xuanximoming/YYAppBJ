package com.yy.app;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yy.adapter.PatientFragmentPagerAdapter;
import com.yy.app.newapp.RYpingguActivity;
import com.yy.app.newapp.YizhuzxdActivity;
import com.yy.cookies.Statics;
import com.yy.entity.Patient;
import com.yy.fragment.PatientFragment;
import com.yy.until.ExitManager;
import com.yy.until.MyUntils;

public class PatientDescPagerActivity extends FragmentActivity implements
		OnClickListener {
	public static String bid, vid;
	int position;
	public static Button back_name, last, next;
	TextView bq_tv, bc_tv, hldj_tv, zhuyi_tv, patient_name, patient_gender,
			patient_age, patient_ks, patient_hldj, patient_bh;
	LinearLayout ll_rypg, ll_yizhu, ll_yzzxd, ll_mrpinggu, ll_huli, ll_jiaoyu,
			ll_jianyan, ll_jiancha, ll_shoushu, ll_ypgl, ll_jiaojieban;

	private ViewPager mViewPager;
	private ArrayList<PatientFragment> fragments = new ArrayList<PatientFragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patientdescpater);
		ExitManager.getInstance().addActivity(this);
		MyUntils.getUersInfo(this);
		Intent intent = getIntent();
		bid = intent.getStringExtra("bid");
		vid = intent.getStringExtra("vid");
		position = intent.getIntExtra("position", -1);
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		back_name = (Button) findViewById(R.id.activity_title_back);
		back_name.setOnClickListener(this);
		last = (Button) findViewById(R.id.activity_title_last);
		last.setOnClickListener(this);
		next = (Button) findViewById(R.id.activity_title_next);
		next.setOnClickListener(this);

		mViewPager = (ViewPager) findViewById(R.id.mViewPager);
		Statics.patientdesc = new Patient();

		int count = 10000;
		for (int i = 0; i < count; i++) {
			Bundle data = new Bundle();
			data.putString("text", "");
			PatientFragment newfragment = new PatientFragment();
			newfragment.setArguments(data);
			fragments.add(newfragment);
		}
		PatientFragmentPagerAdapter mAdapetr = new PatientFragmentPagerAdapter(
				getSupportFragmentManager(), fragments);
		mViewPager.setOffscreenPageLimit(0);
		mViewPager.setAdapter(mAdapetr);
		mViewPager.setOnPageChangeListener(pageListener);
		if (position!=-1) {
			mViewPager.setCurrentItem(position);	
		}
	}

	/**
	 * ViewPager切换监听方法
	 * */
	public OnPageChangeListener pageListener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			mViewPager.setCurrentItem(position);
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_title_back:
			finish();
			break;
		case R.id.activity_title_last:

			break;

		case R.id.activity_title_next:
			break;
		case R.id.ll_rypg:
			Intent rypg = new Intent(this, RYpingguActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(rypg);
			break;
		// 医嘱
		case R.id.ll_yizhu:
			if (Statics.patientdesc == null) {
				break;
			}
			Intent intent = new Intent(this, YizhuActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(intent);
			break;
		case R.id.ll_yzzxd:
			Intent yzzxd = new Intent(this, YizhuzxdActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(yzzxd);
			break;
		case R.id.ll_mrpinggu:
			Intent mrpg = new Intent(this, MRPingguActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(mrpg);
			break;
		case R.id.ll_huli:
			Intent huli = new Intent(this, HuliActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(huli);
			break;
		case R.id.ll_jiaoyu:
			Intent jiaoyv = new Intent(this, JiaoyvActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(jiaoyv);
			break;
		case R.id.ll_jianyan:
			if (Statics.patientdesc == null) {
				break;
			}
			Intent jianyan = new Intent(this, JianYanActivity.class);
			startActivity(jianyan);
			break;
		case R.id.ll_jiancha:
			if (Statics.patientdesc == null) {
				break;
			}
			Intent jiancha = new Intent(this, JianChaActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(jiancha);
			break;
		case R.id.ll_shoushu:
			if (Statics.patientdesc == null) {
				break;
			}
			Intent shoushu = new Intent(this, ShouShuActivity.class);
			startActivity(shoushu);
			break;
		case R.id.ll_ypgl:
			Intent ypguanli = new Intent(this, biaobenguanliActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(ypguanli);
			break;
		case R.id.ll_jiaojieban:
			startActivity(new Intent(this, JiaoJieActivity.class));
			break;

		}

	}
}
