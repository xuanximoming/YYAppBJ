package com.yy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yy.app.R;
import com.yy.entity.BigType;
import com.yy.view.FollowListView;

import java.util.List;

public class AddJiaoJieAdapter extends BaseExpandableListAdapter {

	private FollowListView toolbarGrid;

	private Context parentContext;

	private LayoutInflater layoutInflater;
	private List<BigType> listtype;

	public AddJiaoJieAdapter(Context view, List<BigType> listtype) {
		this.listtype = listtype;
		parentContext = view;
	}

	/**
	 * 获取组员名
	 */
	public Object getChild(int groupPosition, int childPosition) {
		return listtype.get(groupPosition).getLittletypelist()
				.get(childPosition);
	}

	/**
	 * 返回值必须为1，否则会重复数据
	 */
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	private BaseAdapter littltypeadapter;

	/**
	 * 自定义组员
	 */
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		layoutInflater = (LayoutInflater) parentContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = layoutInflater
				.inflate(R.layout.expenditem_gridview, null);
		toolbarGrid = (FollowListView) convertView
				.findViewById(R.id.GridView_toolbar);
		littltypeadapter = new ExpendViewGridViewAdapter(listtype.get(
				groupPosition).getLittletypelist(), parentContext,groupPosition);
		toolbarGrid.setAdapter(littltypeadapter);// 设置菜单Adapter
		return convertView;
	}

	/**
	 * 自定义组
	 */
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		convertView = (RelativeLayout) LayoutInflater.from(this.parentContext)
				.inflate(R.layout.expendtitle_items, null);
		CheckBox checkbox = (CheckBox) convertView
				.findViewById(R.id.exptitle_selectimg);
		checkbox.setChecked(listtype.get(groupPosition).isChecked());
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				System.out.println("grop isChecked=" + isChecked);
				listtype.get(groupPosition).setChecked(isChecked);
				if (isChecked) {
					for (int i = 0; i < listtype.get(groupPosition)
							.getLittletypelist().size(); i++) {
						listtype.get(groupPosition).getLittletypelist().get(i)
								.setChecked(true);
						System.out
								.println("child isChecked="
										+ listtype.get(groupPosition)
												.getLittletypelist().get(i)
												.isChecked());
					}
					notifyDataSetChanged();
				}
			}
		});
		TextView textView = (TextView) convertView
				.findViewById(R.id.exptitle_text);
		textView.setText(getGroup(groupPosition).toString());
		return convertView;
	}

	/**
	 * 获取组员id
	 */
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/**
	 * 获取组名
	 */
	public Object getGroup(int groupPosition) {
		return listtype.get(groupPosition).getName();
	}

	/**
	 * 统计组的长度
	 */
	public int getGroupCount() {
		return listtype.size();
	}

	/**
	 * 获取组id
	 */
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**
	 * 定义组员是否可选
	 */
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}

}
