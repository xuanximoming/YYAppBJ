//package com.yy.adapter;
//
//import java.io.UnsupportedEncodingException;
//import java.util.HashMap;
//import java.util.List;
//
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.os.Handler;
//import android.os.Message;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.TextView.OnEditorActionListener;
//import android.widget.Toast;
//
//import com.yy.app.R;
//import com.yy.app.jiaoyvbdActivity;
//import com.yy.cookies.Statics;
//import com.yy.entity.JiaoYuForm;
//import com.yy.until.MyUntils;
//
//public class jiaoyvbdAdapter extends BaseAdapter implements
//		OnEditorActionListener {
//	private List<JiaoYuForm> data;
//	private Context context;
//	private Holder holder;
//	EditText edits;
//	EditText edit;
//	CharSequence textstr = "";// 拿到输入框的值
//	Handler mhandler;
//
//	/**
//	 * 
//	 * @param data
//	 * @param context
//	 */
//	public jiaoyvbdAdapter(List<JiaoYuForm> data, Context context) {
//		super();
//		this.data = data;
//		this.context = context;
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return data.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return data.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		final JiaoYuForm jiaoyv = data.get(position);
//		convertView = LayoutInflater.from(context).inflate(
//				R.layout.item_jiaoyvbd, null); // 实例化convertView
//		// ImageView yizhu_img = (ImageView) convertView
//		// .findViewById(R.id.yizhu_img);
//		// Bitmap mbmpTest = Bitmap.createBitmap(50, 50, Config.ARGB_8888);//
//		// 创建一个Bitmap对象（空白）
//		// mbmpTest.eraseColor(context.getResources().getColor(R.color.red));//
//		// 使用颜色值来填充位图
//		// yizhu_img.setImageBitmap(mbmpTest);
//		TextView yizhu_item = (TextView) convertView.findViewById(R.id.name);
//		try {
//			String item = new String(jiaoyv.getOname().getBytes(), "utf-8");
//			yizhu_item.setText(item);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		edits = (EditText) convertView.findViewById(R.id.edits);
//		edit = (EditText) convertView.findViewById(R.id.edit);
//
//		/*
//		 * edit.addTextChangedListener(mTextWatcher);
//		 * edits.addTextChangedListener(mTextWatcher);
//		 */
//		edits.setOnEditorActionListener(this);
//		edit.setOnEditorActionListener(this);
//		edits.setText(jiaoyv.getTexts());
//		edit.setText(jiaoyv.getTexts());
//		final Button check = (Button) convertView.findViewById(R.id.check);
//		String otype = jiaoyv.getOtype();
//		mhandler = new Handler() {
//
//			@Override
//			public void handleMessage(Message msg) {
//				// TODO Auto-generated method stub
//				super.handleMessage(msg);
//				switch (msg.what) {
//				case 101:
//					JiaoYuForm jiaoyuform = new JiaoYuForm();
//					jiaoyuform.setOname(jiaoyv.getOname());
//					jiaoyvbdActivity.data.get(position).setTexts(
//							textstr.toString());
//					notifyDataSetChanged();
//					String[] object3 = { textstr.toString() };
//					// object3[which] = jiaoyv.getOvalues()[which];
//					System.out.println("object3" + object3.toString());
//					jiaoyuform.setOvalues(object3);
//					Statics.jiaoyuitemidhashmap.put(jiaoyv.getOname(),
//							jiaoyuform);
//					break;
//				default:
//					break;
//				}
//			}
//
//		};
//		if (otype.equals("0")) {
//			edit.setVisibility(View.VISIBLE);
//			/*
//			 * JiaoYuForm jiaoyuform = new JiaoYuForm();
//			 * jiaoyuform.setOname(jiaoyv.getOname()); String[] object3 =
//			 * {textstr.toString()}; //object3[which] =
//			 * jiaoyv.getOvalues()[which];
//			 * System.out.println("object3"+object3.toString());
//			 * jiaoyuform.setOvalues(object3);
//			 * Statics.jiaoyuitemidhashmap.put(jiaoyv.getOname(), jiaoyuform);
//			 */
//		} else if (otype.equals("1")) {
//			check.setVisibility(View.VISIBLE);
//			check.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//
//					Builder builder = new AlertDialog.Builder(context);
//					builder.setItems(jiaoyv.getOvalues(),
//							new DialogInterface.OnClickListener() {
//
//								@Override
//								public void onClick(DialogInterface dialog,
//										int which) {
//									// TODO Auto-generated method stub
//									System.out.println("which=" + which);
//									check.setText(jiaoyv.getOvalues()[which]);
//									JiaoYuForm jiaoyuform = new JiaoYuForm();
//									jiaoyuform.setOname(jiaoyv.getOname());
//									String[] object3 = { jiaoyv.getOvalues()[which] };
//									// object3[which] =
//									// jiaoyv.getOvalues()[which];
//									System.out.println("object3"
//											+ object3.toString());
//									jiaoyuform.setOvalues(object3);
//									Statics.jiaoyuitemidhashmap.put(
//											jiaoyv.getOname(), jiaoyuform);
//								}
//							});
//					builder.show();
//				}
//			});
//		} else if (otype.equals("2")) {
//			edits.setVisibility(View.VISIBLE);
//		}
//
//		// RadioButton radio = (RadioButton)
//		// convertView.findViewById(R.id.radio);
//
//		//
//		// TextView name = (TextView) convertView
//		// .findViewById(R.id.name);
//		// // String getTime = DateUtil.strToStr(yizhu.getZX_TIME(), "HH:mm");
//		// name.setText(yizhu.getItemname());
//
//		// ImageView next = (ImageView) convertView.findViewById(R.id.next);
//
//		// switch (type) {
//		// case 0://有下级
//		// next.setVisibility(View.VISIBLE);
//		// break;
//		//
//		// case 1://单选
//		// radio.setVisibility(View.VISIBLE);
//		// break;
//		// case 2://多选
//		// check.setVisibility(View.VISIBLE);
//		// break;
//		// case 3://输入
//		// edit.setVisibility(View.VISIBLE);
//		// break;
//		// case 4://日期
//		// edit.setVisibility(View.VISIBLE);
//		// break;
//		// }
//		// if (cengji == 0) {
//		// check.setVisibility(View.VISIBLE);
//		// int selected = yizhu.getSelected();
//		// if (selected == 1) {
//		// check.setChecked(true);
//		// }
//
//		// }
//
//		return convertView;
//
//	}
//
//	public static String Stringisnull(String trim) {
//		String trim2 = trim.toString().trim();
//		if (trim2.equals("null") || trim2.equals("") || trim2 == null) {
//			return "";
//		} else {
//			return trim2;
//		}
//	}
//
//	class Holder {
//		public ImageView customeriv;
//		public TextView customername, customerphone, old_price;
//	}
//
//	@Override
//	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//		// TODO Auto-generated method stub
//		// switch(actionId){
//		// case EditorInfo.IME_NULL:
//		// System.out.println("null for default_content: " + v.getText() );
//		// break;
//		// case EditorInfo.IME_ACTION_SEND:
//		// System.out.println("action send for email_content: " + v.getText());
//		// break;
//		// case EditorInfo.IME_ACTION_DONE:
//		// System.out.println("action done for number_content: " + v.getText());
//		textstr = v.getText().toString().trim();
//		System.out.println("textstr++++++++" + textstr);
//		Message msg = new Message();
//		msg.what = 101;
//		mhandler.sendMessage(msg);
//
//		((InputMethodManager) context
//				.getSystemService(Context.INPUT_METHOD_SERVICE))
//				.hideSoftInputFromWindow(edit.getWindowToken(),
//						InputMethodManager.HIDE_NOT_ALWAYS);
//		// edit.setFocusable(false);
//		// break;
//		// }
//
//		// Toast.makeText(this, v.getText()+"--" + actionId,
//		// Toast.LENGTH_LONG).show();
//		return false;
//	}
//
//}
