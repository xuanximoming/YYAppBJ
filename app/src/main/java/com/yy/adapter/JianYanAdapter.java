package com.yy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yy.app.R;
import com.yy.entity.JianYan;

import java.util.List;

public class JianYanAdapter extends BaseAdapter {
	private List<JianYan> data;
	private Context context;
	private Holder holder;

	public JianYanAdapter(List<JianYan> data, Context context) {
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		JianYan JianYan = data.get(position);
		convertView = LayoutInflater.from(context).inflate(R.layout.item_jiancha,
				null); // 实例化convertView

		TextView bigc = (TextView) convertView
				.findViewById(R.id.bigc);
		bigc.setText(JianYan.getJY_BIAOBEN());
		
		TextView smc = (TextView) convertView
				.findViewById(R.id.smc);
		smc.setText(JianYan.getJY_ZHENDUAN());
		
//		TextView doct = (TextView) convertView
//				.findViewById(R.id.doct);
//		doct.setText("");
		
		TextView timetv = (TextView) convertView
				.findViewById(R.id.time);
//		String getTime = DateUtil.strToStr(JianYan.getJC_DATE(), "yy-MM-dd HH:mm");
//		timetv.setText(getTime);
		timetv.setText(JianYan.getJY_DATE());

//		final TextView JianYanbiaozhu = (TextView) convertView
//				.findViewById(R.id.yizhubiaozhu);
//		JianYanbiaozhu.setText("");

//		LinearLayout JianYan_msg = (LinearLayout) convertView
//				.findViewById(R.id.yizhu_msg);
//		JianYan_msg.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				int visibility = JianYanbiaozhu.getVisibility();
//				if (visibility == View.GONE) {
//					JianYanbiaozhu.setVisibility(View.VISIBLE);
//				} else {
//					JianYanbiaozhu.setVisibility(View.GONE);
//				}
//			}
//		});

		return convertView;

	}

	class Holder {
		public ImageView customeriv;
		public TextView customername, customerphone, old_price;
	}
}
