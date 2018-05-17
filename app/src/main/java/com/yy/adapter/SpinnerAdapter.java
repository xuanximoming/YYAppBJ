package com.yy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yy.app.R;
import com.yy.entity.Yizhutype;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
	private List<Yizhutype> data;
	private Context context;
	private Holder holder;

	public SpinnerAdapter(List<Yizhutype> data, Context context) {
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
		Yizhutype yizhu = data.get(position);
		convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner,
				null); // 实例化convertView
		TextView yizhu_item = (TextView) convertView
				.findViewById(R.id.sp_name);
		yizhu_item.setText(yizhu.getName());

		return convertView;

	}

	class Holder {
		public ImageView customeriv;
		public TextView customername, customerphone, old_price;
	}
}
