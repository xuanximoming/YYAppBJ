package com.yy.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.yy.app.R;
import com.yy.cookies.Statics;
import com.yy.entity.PingGu;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

public class RYpgSecondAdapter extends BaseAdapter {
	private List<PingGu> data;
	private Context context;
	private Holder holder;

	/**
	 * 
	 * @param data
	 * @param context
	 * @param //cengji第一层也显示checkbox用来提交
	 */
	public RYpgSecondAdapter(List<PingGu> data, Context context) {
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

	PingGu pinggu;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		pinggu = data.get(position);
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
			String item = new String(pinggu.getItemname().getBytes(), "utf-8");
			yizhu_item.setText(item);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final EditText edit = (EditText) convertView.findViewById(R.id.edit);
		CheckBox check = (CheckBox) convertView.findViewById(R.id.check);

		ImageView next = (ImageView) convertView.findViewById(R.id.next);
		final int type = pinggu.getItemtype();
		check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					saveok = Jsontosaveok(pinggu.getItemid(), 1, edit.getText()
							.toString().trim());
					data.get(position).setSelected(1);
					pinggu.setSelected(1);
				} else {
					data.get(position).setSelected(0);
					saveok = Jsontosaveok(pinggu.getItemid(), 0, edit.getText()
							.toString().trim());
					pinggu.setSelected(0);

				}
				// submitdata(pinggu.getItemid());
				// notifyDataSetChanged();
			}
		});
		RadioButton radio = (RadioButton) convertView.findViewById(R.id.radio);
		radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					for (int i = 0; i < data.size(); i++) {
						if (position == i) {
							data.get(i).setSelected(1);
							pinggu.setSelected(1);
						} else {
							data.get(i).setSelected(0);
							pinggu.setSelected(0);
						}
					}
					saveok = Jsontosaveok(pinggu.getItemid(),
							pinggu.getSelected(), edit.getText().toString()
									.trim());
					// submitdata(pinggu.getItemid());
					notifyDataSetChanged();
				}
			}
		});
		//
		// TextView name = (TextView) convertView
		// .findViewById(R.id.name);
		// // String getTime = DateUtil.strToStr(yizhu.getZX_TIME(), "HH:mm");
		// name.setText(yizhu.getItemname());
		edit.setText(data.get(position).getItemtextvalue());
		edit.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub

				((InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(edit.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				// MyUntils.myToast(context, edit.getText().toString());
				data.get(position).setItemtextvalue(
						edit.getText().toString().trim());
				notifyDataSetChanged();
				saveok = Jsontosaveok(pinggu.getItemid(), pinggu.getSelected(),
						edit.getText().toString().trim());
				// submitdata(pinggu.getItemid());
				edit.setFocusable(false);
				pinggu.setItemtextvalue(edit.getText().toString().trim());
				return false;
			}
		});
		edit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("选择日期");
				Calendar calendar = Calendar.getInstance();
				new DatePickerDialog(context,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								// TODO Auto-generated method stub
								edit.setText(year + "-" + (monthOfYear + 1)
										+ "-" + dayOfMonth);
								data.get(position).setItemtextvalue(
										edit.getText().toString().trim());
								saveok = Jsontosaveok(pinggu.getItemid(),
										pinggu.getSelected(), edit.getText()
												.toString().trim());
								// submitdata(pinggu.getItemid());
								notifyDataSetChanged();
								edit.setFocusable(false);
								pinggu.setItemtextvalue(edit.getText()
										.toString().trim());
								return;
							}
						}, calendar.get(Calendar.YEAR), calendar
								.get(Calendar.MONTH), calendar
								.get(Calendar.DAY_OF_MONTH)).show();

			}
		});
		edit.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				System.out.println("选择日期___focus" + hasFocus);
				// TODO Auto-generated method stub
				// if (hasFocus && type == 4) {
				// Calendar calendar = Calendar.getInstance();
				// new DatePickerDialog(context,
				// new DatePickerDialog.OnDateSetListener() {
				//
				// @Override
				// public void onDateSet(DatePicker view,
				// int year, int monthOfYear,
				// int dayOfMonth) {
				// // TODO Auto-generated method stub
				// edit.setText(year + "-" + (monthOfYear + 1)
				// + "-" + dayOfMonth);
				// data.get(position).setItemtextvalue(
				// edit.getText().toString().trim());
				// notifyDataSetChanged();
				// saveok = Jsontosaveok(pinggu.getItemid(),
				// pinggu.getSelected(), edit
				// .getText().toString()
				// .trim());
				// submitdata(pinggu.getItemid());
				// edit.setFocusable(false);
				// return;
				// }
				// }, calendar.get(Calendar.YEAR), calendar
				// .get(Calendar.MONTH), calendar
				// .get(Calendar.DAY_OF_MONTH)).show();
				// }
			}
		});

		switch (type) {
		case 0:// 有下级
			next.setVisibility(View.VISIBLE);
			break;
		case 1:// 单选
			radio.setVisibility(View.VISIBLE);
			if (pinggu.getSelected() == 0) {
				Statics.ruyuanpingguhashmap.remove(data.get(position)
						.getItemid());
				radio.setChecked(false);
			} else {
				Statics.ruyuanpingguhashmap.put(data.get(position).getItemid(),
						pinggu);
				radio.setChecked(true);
			}
			break;
		case 2:// 多选
			check.setVisibility(View.VISIBLE);
			if (pinggu.getSelected() == 0) {
				Statics.ruyuanpingguhashmap.remove(data.get(position)
						.getItemid());
				check.setChecked(false);
			} else {
				Statics.ruyuanpingguhashmap.put(data.get(position).getItemid(),
						pinggu);
				check.setChecked(true);
			}
			break;
		case 3:// 输入
			edit.setVisibility(View.VISIBLE);
			radio.setVisibility(View.GONE);
			Statics.ruyuanpingguhashmap.put(data.get(position).getItemid(),
					pinggu);
			break;
		case 4:// 日期
			edit.setVisibility(View.VISIBLE);
			radio.setVisibility(View.GONE);
			Statics.ruyuanpingguhashmap.put(data.get(position).getItemid(),
					pinggu);
			break;
		}
		if (pinggu.getXiaji() == 1) {
			next.setVisibility(View.VISIBLE);
		} else {
			next.setVisibility(View.GONE);
		}

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

	private String saveok;

	private void submitdata(final String saveid) {
		MyUntils.getUersInfo(context);
		ThreadPoolManager.getInstance().addTask(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				MyUntils.submitRYdata(Statics.SubmitPingGuURL,
						Statics.patientdesc.getPatient_id(),
						Statics.patientdesc.getZy_id(), saveid, saveok,
						Statics.user.getUid());
			}
		});
	}

	private String Jsontosaveok(String itemid, int i, String txt) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("itemid", itemid);
			obj.put("savetype", i);
			obj.put("txt", txt);
			System.out.println("obj.toString()=" + obj.toString());
			return obj.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	class Holder {
		public ImageView customeriv;
		public TextView customername, customerphone, old_price;
	}
}
