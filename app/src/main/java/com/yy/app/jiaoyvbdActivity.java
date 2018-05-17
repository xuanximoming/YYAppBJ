//package com.yy.app;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.yy.adapter.jiaoyvbdAdapter;
//import com.yy.cookies.Statics;
//import com.yy.entity.JiaoYuForm;
//import com.yy.until.ExitManager;
//import com.yy.until.MyUntils;
//import com.yy.until.ThreadPoolManager;
//
//public class jiaoyvbdActivity extends Activity implements OnClickListener {
//	TextView activity_title_name;
//	Button activity_title_back, activity_title_ok;
//	Handler mhandler;
//	public static List<JiaoYuForm> data = new ArrayList<JiaoYuForm>();
//	GridView gridview;
//	BaseAdapter adapter;
//	List<String> strlist;
//	private ListView listview;
//	String itemid = "0";
//	boolean res = false; // 教育表单提交结果
//
//	public static List<JiaoYuForm> jiaoyuitemidlist1 = new ArrayList<JiaoYuForm>();// 记录教育列表选择的Oname：表单项Ovalue：存入值
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_jiaoyvbd);
//		ExitManager.getInstance().addActivity(this);
//		MyUntils.getUersInfo(this);
//		Intent intent = getIntent();
//		itemid = intent.getStringExtra("itemid");
//		// 获取树中 父级itemid
//		// jiaoyuitemidlist1 =
//		// intent.getStringArrayListExtra("jiaoyuitemidlist");
//		init();
//	}
//
//	private void init() {
//		// TODO Auto-generated method stub
//		activity_title_name = (TextView) findViewById(R.id.activity_title_name);
//		activity_title_name.setText("教育表单");
//		activity_title_ok = (Button) findViewById(R.id.activity_title_ok);
//		activity_title_ok.setText("提交");
//		activity_title_ok.setVisibility(View.VISIBLE);
//		activity_title_ok.setOnClickListener(this);
//		activity_title_back = (Button) findViewById(R.id.activity_title_back);
//		activity_title_back.setOnClickListener(this);
//
//		listview = (ListView) findViewById(R.id.listview);
//		listview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//
//				// int xiaji = childdata.get(position).getXiaji();
//				// if (xiaji == 1) {
//				// itemid = childdata.get(position).getItemid();
//				// downlaoddata();
//				//
//				// }else {
//				// // Intent intent = new
//				// Intent(jiaoyvbdActivity.this,jiaoyvbdAdapter);
//				// }
//				// 获取当前表单选择项id
//				// jiaoyuitemidlist1.add(jiaoyuform);
//			}
//		});
//		data = new ArrayList<JiaoYuForm>();
//		mhandler = new Handler() {
//
//			@Override
//			public void handleMessage(Message msg) {
//				// TODO Auto-generated method stub
//				super.handleMessage(msg);
//				switch (msg.what) {
//				case 101:
//					MyUntils.cancleProgress();
//					if (data != null) {
//						System.out.println(data.toString());
//						adapter = new jiaoyvbdAdapter(data,
//								jiaoyvbdActivity.this);
//						listview.setAdapter(adapter);
//					} else {
//						if (Statics.internetcode != -1) {
//							MyUntils.myToast(jiaoyvbdActivity.this,
//									Statics.internetstate[Statics.internetcode]);
//						}
//						// MyUntils.myToast(jiaoyvbdActivity.this, "获取信息失败");
//					}
//					break;
//				case 102:
//					if (res) {
//						MyUntils.myToast(jiaoyvbdActivity.this, "表单提交成功");
//						finish();
//					} else {
//						// if (Statics.internetcode != -1) {
//						// MyUntils.myToast(jiaoyvbdActivity.this,
//						// Statics.internetstate[Statics.internetcode]);
//						// }
//						MyUntils.myToast(jiaoyvbdActivity.this, "表单提交失败");
//					}
//					break;
//				default:
//					break;
//				}
//			}
//
//		};
//		downlaoddata();
//	}
//
//	void downlaoddata() {
//		MyUntils.showProgress(this, "正在加载");
//
//		ThreadPoolManager.getInstance().addTask(new Runnable() {
//			public void run() {
//				// TODO Auto-generated method stub
//				System.out.println("run---getmyPatient");
//				String patient_id = Statics.patientdesc.getPatient_id();
//				String zid = Statics.patientdesc.getZy_id();
//
//				data = MyUntils.getJiaoYuFormList(patient_id, zid, itemid);
//				Message msg = new Message();
//				msg.what = 101;
//				mhandler.sendMessage(msg);
//			}
//		});
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.activity_title_back:
//			finish();
//			break;
//
//		case R.id.activity_title_ok:
//			// startActivity(new Intent(this, AddjiaoyvActivity.class));
//			/*
//			 * Set set = Statics.jiaoyuitemidhashmap.entrySet(); Iterator
//			 * iterator = set.iterator(); while (iterator.hasNext()) { Map.Entry
//			 * mapentry = (Map.Entry) iterator.next(); JiaoYuForm jiaoyuform =
//			 * new JiaoYuForm(); jiaoyuform = (JiaoYuForm) mapentry.getValue();
//			 * System.out.println(mapentry.getKey()+"" +
//			 * jiaoyuform.getOvalues()[0] + "/");
//			 * 
//			 * }
//			 */
//			// 提交表单
//			ThreadPoolManager.getInstance().addTask(new Runnable() {
//				public void run() {
//					// TODO Auto-generated method stub
//					System.out.println("run---getmyPatient");
//					String patient_id = Statics.patientdesc.getPatient_id();
//					String zid = Statics.patientdesc.getZy_id();
//					System.out.println(patient_id + "xxxxxx" + zid);
//
////<<<<<<< .mine
////					res = MyUntils
////							.submitJiaoYuFormList(patient_id, zid, itemid);
////					// if(res){
////					// 提交成功清空提交值
////					Statics.jiaoyuitemidhashmap = new HashMap();
////					// }
////=======
//					res = MyUntils.submitJiaoYuFormList(patient_id, zid,
//							itemid, Statics.user.getUid());
//					// if(res){
//					// 提交成功清空提交值
//					Statics.jiaoyuitemidhashmap = new HashMap();
//					// }
//					Message msg = new Message();
//					msg.what = 102;
//					mhandler.sendMessage(msg);
//				}
//			});
//
//			break;
//		}
//	}
//}
