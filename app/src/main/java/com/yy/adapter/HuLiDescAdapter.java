package com.yy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.yy.app.R;
import com.yy.entity.HuLiDesc;

import java.util.List;

public class HuLiDescAdapter extends BaseAdapter {
	private List<HuLiDesc> data;
	private Context context;
	private Holder holder;

	public HuLiDescAdapter(List<HuLiDesc> data, Context context) {
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
		// Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_hulidesc, null); // 实例化convertView
			holder.keytv = (TextView) convertView
					.findViewById(R.id.item_hulidesc_keytv);
			holder.valuetv = (EditText) convertView
					.findViewById(R.id.item_hulidesc_valuetv);
			holder.timetv = (TextView) convertView
					.findViewById(R.id.item_hulidesc_timetv);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		HuLiDesc hulidesc = data.get(position);
		holder.valuetv.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus == false) {
					data.get(position).setHL_VALUE(
							holder.valuetv.getText().toString());
				}
			}
		});
		holder.keytv.setText(Stringisnull(hulidesc.getHL_ITEM()));
		holder.timetv.setText(Stringisnull(hulidesc.getHL_TIME()));
		holder.valuetv.setText(Stringisnull(hulidesc.getHL_VALUE())
				+ Stringisnull(hulidesc.getUNITS()));

		return convertView;

	}

	public static String Stringisnull(String trim) {
		String trim2 = trim.toString().trim();
		if (trim2.equals("null") || trim2.equals("") || trim2 == null) {
			return "";
		} else {
			return trim2;
		}
	}

	class Holder {
		public TextView keytv, timetv;
		public EditText valuetv;
	}
}
