package com.yy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yy.app.R;
import com.yy.entity.PingGu;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class JiaoyvlistAdapter extends BaseAdapter {
	private List<PingGu> data;
	private Context context;
	private Holder holder;

	/**
	 * 
	 * @param data
	 * @param context
	 * @param cengji第一层也显示checkbox用来提交
	 */
	public JiaoyvlistAdapter(List<PingGu> data, Context context) {
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
		PingGu yizhu = data.get(position);
		convertView = LayoutInflater.from(context).inflate(
				R.layout.item_rypgsecond, null); // 实例化convertView
		// ImageView yizhu_img = (ImageView) convertView
		// .findViewById(R.id.yizhu_img);
		// Bitmap mbmpTest = Bitmap.createBitmap(50, 50, Config.ARGB_8888);//
		// 创建一个Bitmap对象（空白）
		// mbmpTest.eraseColor(context.getResources().getColor(R.color.red));//
		// 使用颜色值来填充位图
		// yizhu_img.setImageBitmap(mbmpTest);
		TextView yizhu_item = (TextView) convertView.findViewById(R.id.name);
		try {
			String item = new String(yizhu.getItemname().getBytes(), "utf-8");
			yizhu_item.setText(item);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int type = yizhu.getItemtype();

		RadioButton radio = (RadioButton) convertView.findViewById(R.id.radio);

		//
		// TextView name = (TextView) convertView
		// .findViewById(R.id.name);
		// // String getTime = DateUtil.strToStr(yizhu.getZX_TIME(), "HH:mm");
		// name.setText(yizhu.getItemname());

		EditText edit = (EditText) convertView.findViewById(R.id.edit);

		CheckBox check = (CheckBox) convertView.findViewById(R.id.check);

		ImageView next = (ImageView) convertView.findViewById(R.id.next);

//		switch (type) {
//		case 0://有下级
			next.setVisibility(View.VISIBLE);
//			break;
//
//		case 1://单选
//			radio.setVisibility(View.VISIBLE);
//			break;
//		case 2://多选
//			check.setVisibility(View.VISIBLE);
//			break;
//		case 3://输入
//			edit.setVisibility(View.VISIBLE);
//			break;
//		case 4://日期
//			edit.setVisibility(View.VISIBLE);
//			break;
//		}
//		if (cengji == 0) {
			check.setVisibility(View.VISIBLE);
			int selected = yizhu.getSelected();
			if (selected==1) {
				check.setChecked(true);
			}
			
//		}
			
			
			
		

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
