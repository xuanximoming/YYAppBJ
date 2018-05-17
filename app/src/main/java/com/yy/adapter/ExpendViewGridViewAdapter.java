package com.yy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.yy.app.AddJiaoJieActivity;
import com.yy.app.AddjiaoyvActivity;
import com.yy.app.AddmrpingguActivity;
import com.yy.app.R;
import com.yy.app.newapp.RYpingguActivity;
import com.yy.cookies.Statics;
import com.yy.entity.LittleType;

import java.util.List;

public class ExpendViewGridViewAdapter extends BaseAdapter {
	private List<LittleType> data;
	private Context context;
	private Holder holder;
	private int groppostion;

	public ExpendViewGridViewAdapter(List<LittleType> data, Context context,
			int gropostion) {
		super();
		System.out.println("context=" + context.toString());
		System.out.println(RYpingguActivity.class.equals(context.toString()));
		this.data = data;
		this.context = context;
		this.groppostion = gropostion;
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
					R.layout.expendgridview_items, null); // 实例化convertView
			holder.checkbox = (CheckBox) convertView
					.findViewById(R.id.expendgridviewitem_checkbox);
			holder.name = (TextView) convertView
					.findViewById(R.id.expendgridviewitem_name);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final LittleType name = data.get(position);
		holder.name.setText(name.getName());
		holder.checkbox.setChecked(name.isChecked());
		holder.checkbox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						name.setChecked(isChecked);
						setbigtypeselect();
					}
				});
		return convertView;

	}

	void setbigtypeselect() {
		boolean checked = true;
		for (int i = 0; i < data.size(); i++) {
			if (!data.get(i).isChecked()) {
				checked = false;
			}
		}
		switch (Statics.ADDAVTIVITY) {
		case Statics.RYpingguActivity:
//			RYpingguActivity.listbigtype.get(groppostion).setChecked(checked);
//			RYpingguActivity.treeViewAdapter.notifyDataSetChanged();
			break;
		case Statics.AddJiaoJieActivity:
			AddJiaoJieActivity.listbigtype.get(groppostion).setChecked(checked);
			AddJiaoJieActivity.treeViewAdapter.notifyDataSetChanged();
			break;
		case Statics.AddjiaoyvActivity:
			AddjiaoyvActivity.listbigtype.get(groppostion).setChecked(checked);
			AddjiaoyvActivity.treeViewAdapter.notifyDataSetChanged();
			break;
		case Statics.AddmrpingguActivity:
			AddmrpingguActivity.listbigtype.get(groppostion).setChecked(checked);
			AddmrpingguActivity.treeViewAdapter.notifyDataSetChanged();
			break;

		default:
			break;
		}

	}

	class Holder {
		public CheckBox checkbox;
		public TextView name;
	}
}
