package com.yy.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yy.app.HuliActivity;
import com.yy.app.JianChaActivity;
import com.yy.app.JianYanActivity;
import com.yy.app.JiaoJieActivity;
import com.yy.app.JiaoyvActivity;
import com.yy.app.MRPingguActivity;
import com.yy.app.PatientDescPagerActivity;
import com.yy.app.R;
import com.yy.app.newapp.RYpingguActivity;
import com.yy.app.ShouShuActivity;
import com.yy.app.biaobenguanliActivity;
import com.yy.app.YizhuActivity;
import com.yy.app.newapp.YizhuzxdActivity;
import com.yy.cookies.Statics;
import com.yy.entity.Patient;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

public class PatientFragment extends Fragment implements OnClickListener {
	Activity activity;
	Patient data = new Patient();
	ListView mListView;
	String text;
	// TextView detail_loading;
	public final static int SET_NEWSLIST = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle args = getArguments();
		text = args != null ? args.getString("text") : "";
		downlaoddata();
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		this.activity = activity;
		super.onAttach(activity);
	}

	/** 此方法意思为fragment是否可见 ,可见时候加载数据 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			// fragment可见时加载数据
			if (data != null) {
				handler.obtainMessage(SET_NEWSLIST).sendToTarget();
			} else {
				downlaoddata();
			}
		} else {
			// fragment不可见时不执行操作
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	TextView bq_tv, bc_tv, zhuyi_tv, patient_name, patient_gender,
			patient_age, patient_feibie, patient_hldj, patient_bh;
	LinearLayout ll_rypg, ll_yizhu, ll_yzzxd, ll_mrpinggu, ll_huli, ll_jiaoyu,
			ll_jianyan, ll_jiancha, ll_shoushu, ll_ypgl, ll_jiaojieban;
	private TextView patient_yue;
	private TextView patient_zhenduan;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.item_patientpager, null);
		bq_tv = (TextView) view.findViewById(R.id.patient_dept);
		bc_tv = (TextView) view.findViewById(R.id.patient_bed);
		zhuyi_tv = (TextView) view.findViewById(R.id.zhuyi_tv);
		patient_name = (TextView) view.findViewById(R.id.patient_name);
		patient_gender = (TextView) view.findViewById(R.id.patient_gender);
		patient_age = (TextView) view.findViewById(R.id.patient_age);
		patient_feibie = (TextView) view.findViewById(R.id.side_feibie);
		patient_yue = (TextView) view.findViewById(R.id.side_yue);
		patient_zhenduan = (TextView) view.findViewById(R.id.side_zhenduan);
		patient_hldj = (TextView) view.findViewById(R.id.patient_hldj);
		patient_bh = (TextView) view.findViewById(R.id.patient_ID);

		ll_rypg = (LinearLayout) view.findViewById(R.id.ll_rypg);
		ll_rypg.setOnClickListener(this);
		ll_yizhu = (LinearLayout) view.findViewById(R.id.ll_yizhu);
		ll_yizhu.setOnClickListener(this);
		ll_yzzxd = (LinearLayout) view.findViewById(R.id.ll_yzzxd);
		ll_yzzxd.setOnClickListener(this);
		ll_mrpinggu = (LinearLayout) view.findViewById(R.id.ll_mrpinggu);
		ll_mrpinggu.setOnClickListener(this);
		ll_huli = (LinearLayout) view.findViewById(R.id.ll_huli);
		ll_huli.setOnClickListener(this);
		ll_jiaoyu = (LinearLayout) view.findViewById(R.id.ll_jiaoyu);
		ll_jiaoyu.setOnClickListener(this);
		ll_jianyan = (LinearLayout) view.findViewById(R.id.ll_jianyan);
		ll_jianyan.setOnClickListener(this);
		ll_jiancha = (LinearLayout) view.findViewById(R.id.ll_jiancha);
		ll_jiancha.setOnClickListener(this);
		ll_shoushu = (LinearLayout) view.findViewById(R.id.ll_shoushu);
		ll_shoushu.setOnClickListener(this);
		ll_ypgl = (LinearLayout) view.findViewById(R.id.ll_ypgl);
		ll_ypgl.setOnClickListener(this);
		ll_jiaojieban = (LinearLayout) view.findViewById(R.id.ll_jiaojieban);
		ll_jiaojieban.setOnClickListener(this);
		return view;
	}

	void downlaoddata() {
		// MyUntils.showProgress(this.getActivity(), "正在加载");
		ThreadPoolManager.getInstance().addTask(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("run---getmyPatient");
				// data = MyUntils.GetPatient(PatientDescPagerActivity.bid,
				// PatientDescPagerActivity.vid);
				Message msg = new Message();
				msg.what = 101;
				handler.sendMessage(msg);
			}
		});
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case SET_NEWSLIST:
				// detail_loading.setVisibility(View.GONE);
				MyUntils.cancleProgress();
				Statics.patientdesc = data;
				PatientDescPagerActivity.back_name.setText(data.getName());
				bq_tv.setText(data.getQuhao());
				bc_tv.setText(data.getChuanghao());
				zhuyi_tv.setText(data.getZhuyi());
				patient_name.setText(data.getName());
				patient_gender.setText(data.getGender());
				patient_age.setText(data.getAge());
				patient_feibie.setText(data.getFEIBIE());
				patient_yue.setText(data.getFEIYUE());
				patient_zhenduan.setText(data.getZHENDUAN());
				patient_hldj.setText(data.getHulidengji());
				patient_bh.setText(data.getPatient_id());// 编号
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.ll_rypg:
			Intent rypg = new Intent(this.getActivity(), RYpingguActivity.class);
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
			Intent intent = new Intent(this.getActivity(), YizhuActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(intent);
			break;
		case R.id.ll_yzzxd:
			Intent yzzxd = new Intent(this.getActivity(),
					YizhuzxdActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(yzzxd);
			break;
		case R.id.ll_mrpinggu:
			Intent mrpg = new Intent(this.getActivity(), MRPingguActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(mrpg);
			break;
		case R.id.ll_huli:
			Intent huli = new Intent(this.getActivity(), HuliActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(huli);
			break;
		case R.id.ll_jiaoyu:
			Intent jiaoyv = new Intent(this.getActivity(), JiaoyvActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(jiaoyv);
			break;
		case R.id.ll_jianyan:
			if (Statics.patientdesc == null) {
				break;
			}
			Intent jianyan = new Intent(this.getActivity(),
					JianYanActivity.class);
			startActivity(jianyan);
			break;
		case R.id.ll_jiancha:
			if (Statics.patientdesc == null) {
				break;
			}
			Intent jiancha = new Intent(this.getActivity(),
					JianChaActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(jiancha);
			break;
		case R.id.ll_shoushu:
			if (Statics.patientdesc == null) {
				break;
			}
			Intent shoushu = new Intent(this.getActivity(),
					ShouShuActivity.class);
			startActivity(shoushu);
			break;
		case R.id.ll_ypgl:
			Intent ypguanli = new Intent(this.getActivity(),
					biaobenguanliActivity.class);
			// intent.putExtra("bid", data.getPatient_id());
			// intent.putExtra("vid", data.getZy_id());
			// intent.putExtra("position", position);
			startActivity(ypguanli);
			break;
		case R.id.ll_jiaojieban:
			startActivity(new Intent(this.getActivity(), JiaoJieActivity.class));
			break;
		}
	}
}
