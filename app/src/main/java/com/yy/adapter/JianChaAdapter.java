package com.yy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yy.app.R;
import com.yy.cookies.Statics;
import com.yy.entity.JianCha;

import java.util.ArrayList;
import java.util.List;

public class JianChaAdapter extends BaseAdapter {
	private List<JianCha> data;
	private Context context;
	private Holder holder;

	public JianChaAdapter(List<JianCha> data, Context context) {
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
		JianCha JianCha = data.get(position);
		convertView = LayoutInflater.from(context).inflate(
				R.layout.item_jiancha, null); // 实例化convertView

		TextView bigc = (TextView) convertView.findViewById(R.id.bigc);
		bigc.setText(JianCha.getJC_BIGC());

		TextView smc = (TextView) convertView.findViewById(R.id.smc);
		smc.setText(JianCha.getJC_SMC());

		TextView doct = (TextView) convertView.findViewById(R.id.doct);
		String doctstr = Stringisnull(JianCha.getJC_DOCT());
		doct.setText(doctstr);

		TextView timetv = (TextView) convertView.findViewById(R.id.time);
		// String getTime = DateUtil.strToStr(JianCha.getJC_DATE(),
		// "yy-MM-dd HH:mm");
		timetv.setText(JianCha.getJC_DATE());

		final TextView JianChabiaozhu = (TextView) convertView
				.findViewById(R.id.yizhubiaozhu);
		final TextView JianChabiaozhu1 = (TextView) convertView
				.findViewById(R.id.yizhubiaozhu1);
		String biaozhu = Stringisnull(JianCha.getJC_MEMO());
		JianChabiaozhu.setText(biaozhu);

		LinearLayout JianCha_msg = (LinearLayout) convertView
				.findViewById(R.id.yizhu_msg);
		JianCha_msg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int visibility = JianChabiaozhu.getVisibility();
				if (Statics.JianChaList.size() > 0) {
					for (int i = 0; i < Statics.JianChaList.size(); i++) {
						Statics.JianChaList.get(i).setVisibility(View.GONE);
					}
					Statics.JianChaList = new ArrayList<TextView>();
				}
				if (visibility == View.GONE) {
					JianChabiaozhu.setVisibility(View.VISIBLE);
					JianChabiaozhu1.setVisibility(View.VISIBLE);
					Statics.JianChaList.add(JianChabiaozhu);
					Statics.JianChaList.add(JianChabiaozhu1);
				} else {
					JianChabiaozhu.setVisibility(View.GONE);
					JianChabiaozhu1.setVisibility(View.GONE);
				}
			}
		});

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
		public ImageView customeriv;
		public TextView customername, customerphone, old_price;
	}
}
