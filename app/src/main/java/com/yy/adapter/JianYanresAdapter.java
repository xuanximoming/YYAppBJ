package com.yy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yy.app.R;
import com.yy.entity.JianYanRes;

import java.util.List;

public class JianYanresAdapter extends BaseAdapter {
	private List<JianYanRes> data;
	private Context context;
	private Holder holder;

	public JianYanresAdapter(List<JianYanRes> data, Context context) {
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
		JianYanRes JianYan = data.get(position);
		convertView = LayoutInflater.from(context).inflate(R.layout.item_jiancha,
				null); // 实例化convertView

		TextView bigc = (TextView) convertView
				.findViewById(R.id.bigc);
		bigc.setText(JianYan.getREITEM());
		
		TextView smc = (TextView) convertView
				.findViewById(R.id.smc);
		smc.setText(JianYan.getRESULTOK());
		
//		TextView doct = (TextView) convertView
//				.findViewById(R.id.doct);
//		doct.setText("");
		
		TextView timetv = (TextView) convertView
				.findViewById(R.id.time);
//		String getTime = DateUtil.strToStr(JianYan.getJC_DATE(), "yy-MM-dd HH:mm");
//		timetv.setText(getTime);
		timetv.setText(JianYan.getZHENGCHANG());

//		final TextView JianYanbiaozhu = (TextView) convertView
//				.findViewById(R.id.yizhubiaozhu);
//		JianYanbiaozhu.setText("");

		LinearLayout JianYan_msg = (LinearLayout) convertView
				.findViewById(R.id.yizhu_msg);
		String state = JianYan.getSTATE();
		if (state.equals("H")) {
//			JianYan_msg.setBackgroundResource(R.color.Hcolor);
			bigc.setTextColor(context.getResources().getColor(R.color.jianyanresred));
			smc.setTextColor(context.getResources().getColor(R.color.jianyanresred));
			timetv.setTextColor(context.getResources().getColor(R.color.jianyanresred));
		}else if(state.equals("L")){
//			JianYan_msg.setBackgroundResource(R.color.Lcolor);
			bigc.setTextColor(context.getResources().getColor(R.color.jianyanresgreen));
			smc.setTextColor(context.getResources().getColor(R.color.jianyanresgreen));
			timetv.setTextColor(context.getResources().getColor(R.color.jianyanresgreen));
		}
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
