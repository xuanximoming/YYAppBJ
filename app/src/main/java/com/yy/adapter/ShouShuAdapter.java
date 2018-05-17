package com.yy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yy.app.R;
import com.yy.entity.ShouShu;

import java.util.List;

public class ShouShuAdapter extends BaseAdapter {
	private List<ShouShu> data;
	private Context context;
	private Holder holder;

	public ShouShuAdapter(List<ShouShu> data, Context context) {
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
		ShouShu ShouShu = data.get(position);
		convertView = LayoutInflater.from(context).inflate(R.layout.item_shoushu,
				null); // 实例化convertView

		TextView mingcheng = (TextView) convertView
				.findViewById(R.id.ssmc);
		mingcheng.setText(ShouShu.getSHOUSHU_NAME());
		
		TextView buwei = (TextView) convertView
				.findViewById(R.id.ssbw);
		buwei.setText(ShouShu.getSHOUSHU_BUWEI());
		
		TextView shijian = (TextView) convertView
				.findViewById(R.id.sssj);
		shijian.setText(ShouShu.getSHOUSHU_DATE());
		
		TextView yisheng = (TextView) convertView
				.findViewById(R.id.ssys);
//		String getTime = DateUtil.strToStr(ShouShu.getJC_DATE(), "yy-MM-dd HH:mm");
//		timetv.setText(getTime);
		yisheng.setText(ShouShu.getSHOUSHU_YISHI());

		final TextView ShouShubiaozhu = (TextView) convertView
				.findViewById(R.id.yizhubiaozhu);
		ShouShubiaozhu.setText("");

	/*	LinearLayout ShouShu_msg = (LinearLayout) convertView
				.findViewById(R.id.yizhu_msg);
		ShouShu_msg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int visibility = ShouShubiaozhu.getVisibility();
				if (visibility == View.GONE) {
					ShouShubiaozhu.setVisibility(View.VISIBLE);
				} else {
					ShouShubiaozhu.setVisibility(View.GONE);
				}
			}
		});*/

		return convertView;

	}

	class Holder {
		public ImageView customeriv;
		public TextView customername, customerphone, old_price;
	}
}
