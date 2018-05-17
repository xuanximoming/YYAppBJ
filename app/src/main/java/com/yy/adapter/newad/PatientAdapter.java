package com.yy.adapter.newad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yy.app.R;
import com.yy.entity.Patient;
import com.yy.until.ImageUtilszm;

import java.util.List;

public class PatientAdapter extends BaseAdapter {
	private List<Patient> data;
	private Context context;

	public PatientAdapter(List<Patient> data, Context context) {
		super();
		this.data = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint({ "ViewHolder", "InflateParams", "SimpleDateFormat" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Patient goods = data.get(position);
		convertView = LayoutInflater.from(context).inflate(
				R.layout.item_patient, null); // 实例化convertView
		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.rl_01);// 区号
		int i = position % 4;
		int bg[] = { R.drawable.fillet_sideitempurple,
				R.drawable.fillet_sideitempink,
				R.drawable.fillet_sideitemorange,
				R.drawable.fillet_sideitemgreen };
		rl.setBackgroundResource(bg[i]);

		TextView name = (TextView) convertView.findViewById(R.id.side_name);// 名
		name.setText(goods.getName());
		TextView bq_num = (TextView) convertView.findViewById(R.id.bq_num);// 区号
		bq_num.setText(goods.getQuhao());
		TextView bc_num = (TextView) convertView.findViewById(R.id.bc_num);// 床号
		bc_num.setText(goods.getChuanghao());
		TextView side_age = (TextView) convertView.findViewById(R.id.side_age);// 年龄
		side_age.setText(goods.getAge());
		TextView side_gender = (TextView) convertView.
				findViewById(R.id.side_gender);// 性别
		ImageView genderiv = (ImageView) convertView
				.findViewById(R.id.genderiv);// 性别
		String gender = goods.getGender().trim();
		side_gender.setText(gender);
		if (gender.equals("男")) {
			ImageUtilszm.setBitmap(context, genderiv, R.drawable.man);
		} else {
			ImageUtilszm.setBitmap(context, genderiv, R.drawable.woman);
		}

		TextView side_hldj = (TextView) convertView
				.findViewById(R.id.side_hldj);// 护理等级
		side_hldj.setText(goods.getHulidengji());
		TextView side_ks = (TextView)
				convertView.findViewById(R.id.side_ks);// 科室
		side_ks.setText(goods.getKeshi());
		TextView side_time = (TextView) convertView
				.findViewById(R.id.side_time);// 入住时间
		String time = goods.getTime();
		side_time.setText(time);
		TextView item_bianshi = (TextView) convertView
				.findViewById(R.id.item_bianshi);// 标识
		String patientid = goods.getPatient_id();
		item_bianshi.setText(patientid);
		TextView item_cishu = (TextView) convertView
				.findViewById(R.id.Visit_ID);// 住院次数
		String zhuyuanid = goods.getZy_id();
		item_cishu.setText(zhuyuanid);
		return convertView;
	}

	class Holder {
		public ImageView customeriv;
		public TextView customername, customerphone, old_price;
	}
}
