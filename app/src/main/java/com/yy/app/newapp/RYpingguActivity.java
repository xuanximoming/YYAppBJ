package com.yy.app.newapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yy.adapter.newad.RYpingguAdapter;
import com.yy.app.R;
import com.yy.app.RYPinguUPActivity;
import com.yy.cookies.Statics;
import com.yy.entity.PingGu;
import com.yy.until.ExitManager;
import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

public class RYpingguActivity extends Activity implements OnClickListener {
    TextView activity_title_name;
    Button activity_title_back;
    int page = 1;// 请求数据页数
    List<PingGu> data;
    private List<PingGu> newdata;
    ListView listview;
    RYpingguAdapter adapter;
    private String itemid = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rypinggulist);
        ExitManager.getInstance().addActivity(this);
        MyUntils.getUersInfo(this);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        activity_title_name = (TextView) findViewById(R.id.activity_title_name);
        activity_title_name.setText("入院评估");
        activity_title_back = (Button) findViewById(R.id.activity_title_back);
        activity_title_back.setOnClickListener(this);
        listview = (ListView) findViewById(R.id.listview);
        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                final int type = newdata.get(position).getItemtype();
                if (type == 0) {
                    String itemid = newdata.get(position).getItemid();
                    String itemname = newdata.get(position).getItemname();
                    Intent intent = new Intent(RYpingguActivity.this,
                            RYPinguUPActivity.class);
                    intent.putExtra("itemid", itemid);
                    intent.putExtra("itemname", itemname);
                    startActivity(intent);
                }
            }
        });
        data = new ArrayList<PingGu>();
        newdata = new ArrayList<PingGu>();
    }

    public Handler mhandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 101:
                    if (data == null) {
                        if (Statics.internetcode != -1) {
                            MyUntils.myToast(RYpingguActivity.this,
                                    Statics.internetstate[Statics.internetcode]);
                        }
                    } else if (data.size() > 0) {
                        if (page == 1) {
                            for(int i = data.size() - 1;i >= 0; i--){
                                newdata.add(data.get(i));
                            }
                            adapter = new RYpingguAdapter(newdata,
                                    RYpingguActivity.this, Integer.parseInt(itemid));
                            listview.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                        MyUntils.myToast(RYpingguActivity.this, "获取信息成功");
                        page++;
                    } else {
                        MyUntils.myToast(RYpingguActivity.this, "没有获取到信息");
                    }
                    break;

                default:
                    break;
            }
        }

    };


    void downlaoddata() {
        if (!HttpUtil.checkNet(this)) {
            Statics.internetcode = 0;
            int code = Statics.internetcode;
            MyUntils.myToast(this, Statics.internetstate[code]);
            return;
        }
        if (page != 1) {
            System.out.println("pagNum=" + page);
        }
        ThreadPoolManager.getInstance().addTask(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                String patient_id = Statics.patientdesc.getPatient_id();
                String zid = Statics.patientdesc.getZy_id();
                data = MyUntils.getRYPinggu(patient_id, zid, itemid);
                mhandler.sendEmptyMessage(101);
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        downlaoddata();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.activity_title_back:
                finish();
                break;
            default:
                break;
        }
    }
}
