package com.yy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
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
import com.yy.entity.Yizhu;

import java.util.ArrayList;
import java.util.List;

public class YizhuAdapter extends BaseAdapter {
	private List<Yizhu> data;
	private Context context;

	public YizhuAdapter(List<Yizhu> data, Context context) {
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

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Yizhu yizhu = data.get(position);
		convertView = LayoutInflater.from(context).inflate(R.layout.item_yizhu,
				null); // 实例化convertView
		ImageView yizhu_img = (ImageView) convertView
				.findViewById(R.id.yizhu_img);
		Bitmap mbmpTest = Bitmap.createBitmap(50, 50, Config.ARGB_8888);// 创建一个Bitmap对象（空白）
		mbmpTest.eraseColor(context.getResources().getColor(R.color.red));// 使用颜色值来填充位图
		yizhu_img.setImageBitmap(mbmpTest);
		TextView yizhu_item = (TextView) convertView
				.findViewById(R.id.yizhu_item);
		yizhu_item.setText(yizhu.getYZ_ITEM());
		TextView yizhu_jinum = (TextView) convertView
				.findViewById(R.id.yizhu_jinum);
		String string = Stringisnull(yizhu.getYZ_JINUM());
		String jidanwei = Stringisnull(yizhu.getYZ_DANWEI());
		yizhu_jinum.setText(string + jidanwei);

		TextView yizhu_time = (TextView) convertView
				.findViewById(R.id.yizhu_time);
		yizhu_time.setText(yizhu.getZX_TIME());

		yizhu_item.setTextColor(Color.parseColor(yizhu.getYZ_COLOR()));
		yizhu_jinum.setTextColor(Color.parseColor(yizhu.getYZ_COLOR()));
		yizhu_time.setTextColor(Color.parseColor(yizhu.getYZ_COLOR()));
//		String getTime = DateUtil.strToStr(yizhu.getZX_TIME(), "HH:mm");


		final TextView yizhubiaozhu = (TextView) convertView
				.findViewById(R.id.yizhubiaozhu);
		final TextView yizhubiaozhu1 = (TextView) convertView
				.findViewById(R.id.yizhubiaozhu1);
		String memo = Stringisnull(yizhu.getZX_MEMO());
		yizhubiaozhu.setText(memo);

		LinearLayout yizhu_msg = (LinearLayout) convertView
				.findViewById(R.id.yizhu_msg);
		yizhu_msg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int visibility = yizhubiaozhu.getVisibility();
				if (Statics.YiZhuList.size() > 0) {
					for (int i = 0; i < Statics.YiZhuList.size(); i++) {
						Statics.YiZhuList.get(i).setVisibility(View.GONE);
					}
					Statics.YiZhuList = new ArrayList<TextView>();
				}
				if (visibility == View.GONE) {
					yizhubiaozhu.setVisibility(View.VISIBLE);
					yizhubiaozhu1.setVisibility(View.VISIBLE);
					Statics.YiZhuList.add(yizhubiaozhu);
					Statics.YiZhuList.add(yizhubiaozhu1);
				} else {
					yizhubiaozhu.setVisibility(View.GONE);
					yizhubiaozhu1.setVisibility(View.GONE);
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
}
