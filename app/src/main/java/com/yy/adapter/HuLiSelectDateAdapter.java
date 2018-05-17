package com.yy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yy.app.R;
import com.yy.entity.Hulilist;

import java.util.List;

public class HuLiSelectDateAdapter extends BaseAdapter {
	private List<Hulilist> data;
	private Context context;
	private Holder holder;

	public HuLiSelectDateAdapter(List<Hulilist> data,
			Context context) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_selectdate, null); // 实例化convertView
			holder.date = (TextView) convertView
					.findViewById(R.id.date);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		String name = data.get(position).getRECORDING_DATE();
		holder.date.setText(name);
		return convertView;

	}

	class Holder {
		public TextView date;
	}
}
