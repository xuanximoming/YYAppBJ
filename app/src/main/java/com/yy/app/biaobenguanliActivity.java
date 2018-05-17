package com.yy.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yy.adapter.newad.YangbenAdapter;
import com.yy.cookies.Statics;
import com.yy.entity.Yangben;
import com.yy.keyapp.module.base.BaseServer;
import com.yy.until.ExitManager;
import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;
import com.yy.until.StrTool;
import com.yy.until.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

public class biaobenguanliActivity extends Activity implements OnClickListener {
    TextView bq_tv, bc_tv, zhuyi_tv, patient_name, patient_gender,
            patient_age, patient_feibie, patient_hldj, patient_bh,
            activity_title_name;
    Handler mhandler;
    List<Yangben> data;
    ListView listview;
    YangbenAdapter adapter;
    Button activity_title_back, activity_title_ok;
    private TextView side_time;
    private TextView item_xuhao;
    public static String Failmsg = null;
    private int longclickpostion;
    private TextView patient_yue;
    private TextView patient_zhenduan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biaobenguanli);
        ExitManager.getInstance().addActivity(this);
        MyUntils.getUersInfo(this);
        init();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        /**
         * 注册广播接收器
         */
        registerReceiver(mBroadcastReceiver, new BaseServer().RegisterBoradcastReceiver());
        super.onResume();
    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        // 解除广播接收器
        unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }



    // 扫描代码
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("value");
            // 拿到扫描的返回值
            System.out.println("text=" + text);
            searchBiaoBen(text);
        }
    };




    private void init() {
        // TODO Auto-generated method stub
        activity_title_name = (TextView) findViewById(R.id.activity_title_name);
        activity_title_name.setText("标本管理");
        activity_title_back = (Button) findViewById(R.id.activity_title_back);
        activity_title_back.setOnClickListener(this);

        side_time = (TextView) findViewById(R.id.side_time);// 入住时间
        item_xuhao = (TextView) findViewById(R.id.Visit_ID);// 住院号
        bq_tv = (TextView) findViewById(R.id.patient_dept);
        bc_tv = (TextView) findViewById(R.id.patient_bed);
        zhuyi_tv = (TextView) findViewById(R.id.zhuyi_tv);
        patient_name = (TextView) findViewById(R.id.patient_name);
        patient_gender = (TextView) findViewById(R.id.patient_gender);
        patient_age = (TextView) findViewById(R.id.patient_age);
        patient_feibie = (TextView) findViewById(R.id.side_feibie);
        patient_yue = (TextView) findViewById(R.id.side_yue);
        patient_zhenduan = (TextView) findViewById(R.id.side_zhenduan);
        patient_hldj = (TextView) findViewById(R.id.patient_hldj);
        patient_bh = (TextView) findViewById(R.id.patient_ID);

        listview = (ListView) findViewById(R.id.listview);

        /**
         * Create a long press event
         */
        listview.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub
                longclickpostion = position;
                Builder builder = new AlertDialog.Builder(biaobenguanliActivity.this)
                        .setTitle(data.get(position).getTESTNAME());
                builder.setItems(new String[]{"已采", "撤销", "送出"},
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                System.out.println("which=" + which);
                                switch (which) {
                                    case 0:
                                        ThreadPoolManager.getInstance().addTask(
                                                new Runnable() {
                                                    public void run() {
                                                        boolean res;
                                                        res = MyUntils
                                                                .yanbencaiji(
                                                                        data.get(
                                                                                longclickpostion)
                                                                                .getBRID(),
                                                                        data.get(
                                                                                longclickpostion)
                                                                                .getZYID(),
                                                                        data.get(
                                                                                longclickpostion)
                                                                                .getTEST_NO_SRC(),
                                                                        "1",
                                                                        Statics.user
                                                                                .getUid());
                                                        if (res) {
                                                            mhandler.sendEmptyMessage(102);
                                                        }
                                                    }
                                                });
                                        break;
                                    case 1:
                                        ThreadPoolManager.getInstance().addTask(
                                                new Runnable() {
                                                    public void run() {
                                                        boolean res;
                                                        res = MyUntils
                                                                .yanbencaiji(
                                                                        data.get(
                                                                                longclickpostion)
                                                                                .getBRID(),
                                                                        data.get(
                                                                                longclickpostion)
                                                                                .getZYID(),
                                                                        data.get(
                                                                                longclickpostion)
                                                                                .getTEST_NO_SRC(),
                                                                        "0",
                                                                        Statics.user
                                                                                .getUid());
                                                        if (res) {
                                                            mhandler.sendEmptyMessage(102);
                                                        }
                                                    }
                                                });
                                        break;
                                    case 2:
                                        ThreadPoolManager.getInstance().addTask(
                                                new Runnable() {
                                                    public void run() {
                                                        boolean res;
                                                        res = MyUntils
                                                                .yanbencaiji(
                                                                        data.get(
                                                                                longclickpostion)
                                                                                .getBRID(),
                                                                        data.get(
                                                                                longclickpostion)
                                                                                .getZYID(),
                                                                        data.get(
                                                                                longclickpostion)
                                                                                .getTEST_NO_SRC(),
                                                                        "2",
                                                                        Statics.user
                                                                                .getUid());
                                                        if (res) {
                                                            mhandler.sendEmptyMessage(102);
                                                        }
                                                    }
                                                });
                                        break;
                                    default:

                                        break;
                                }
                            }
                        });
                builder.show();
                return false;
            }
        });
        if (Statics.patientdesc != null) {
            bq_tv.setText(Statics.patientdesc.getQuhao());
            bc_tv.setText(Statics.patientdesc.getChuanghao());
            zhuyi_tv.setText(Statics.patientdesc.getZhuyi());
            patient_name.setText(Statics.patientdesc.getName());
            patient_gender.setText(Statics.patientdesc.getGender());
            patient_age.setText(Statics.patientdesc.getAge());
            patient_feibie.setText(Statics.patientdesc.getFEIBIE());
            patient_yue.setText(Statics.patientdesc.getFEIYUE());
            patient_zhenduan.setText(Statics.patientdesc.getZHENDUAN());
            patient_hldj.setText(Statics.patientdesc.getHulidengji());
            patient_bh.setText(Statics.patientdesc.getPatient_id());// 编号
            side_time.setText(Statics.patientdesc.getTime());
            item_xuhao.setText(Statics.patientdesc.getZy_id());
        }

        data = new ArrayList<Yangben>();
        mhandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                    case 101:
                        MyUntils.cancleProgress();
                        if (data == null) {
                            if (Statics.internetcode != -1) {
                                MyUntils.myToast(biaobenguanliActivity.this,
                                        Statics.internetstate[Statics.internetcode]);
                            } else {
                                MyUntils.myToast(biaobenguanliActivity.this, "此病人暂无样品信息");
                            }
                        } else if (data.size() > 0) {

                            System.out.println(data.toString());
                            adapter = new YangbenAdapter(data,
                                    biaobenguanliActivity.this);
                            listview.setAdapter(adapter);
                        } else {
                            MyUntils.myToast(biaobenguanliActivity.this, "此病人暂无样品信息");
                        }
                        break;
                    case 102:
                        MyUntils.myToast(biaobenguanliActivity.this,"标本保存成功!");
                        downlaoddata();
                        adapter.notifyDataSetChanged();
                        break;
                    case 103:
                        MyUntils.cancleProgress();
                        if (data == null) {
                            if (Statics.internetcode != -1) {
                                MyUntils.myToast(biaobenguanliActivity.this,
                                        Statics.internetstate[Statics.internetcode]);
                            }
                            if (Failmsg != null) {
                                MyUntils.myToast(biaobenguanliActivity.this, Failmsg);
                            }
                        } else if (data.size() > 0) {
                            if (Statics.patientdesc != null) {
                                bq_tv.setText(Statics.patientdesc.getQuhao());
                                bc_tv.setText(Statics.patientdesc.getChuanghao());

                                zhuyi_tv.setText(Statics.patientdesc.getZhuyi());
                                patient_name.setText(Statics.patientdesc.getName());
                                patient_gender.setText(Statics.patientdesc
                                        .getGender());
                                patient_age.setText(Statics.patientdesc.getAge());
                                patient_feibie.setText(Statics.patientdesc
                                        .getFEIBIE());
                                patient_yue
                                        .setText(Statics.patientdesc.getFEIYUE());
                                patient_zhenduan.setText(Statics.patientdesc
                                        .getZHENDUAN());
                                patient_hldj.setText(Statics.patientdesc
                                        .getHulidengji());
                                patient_bh.setText(Statics.patientdesc
                                        .getPatient_id());// 编号
                                side_time.setText(Statics.patientdesc.getTime());
                                item_xuhao.setText(Statics.patientdesc.getZy_id());
                            }
                            if (data.get(0).getCAIOK() == 0) {
                                // 原来CAIOK 为0 未采 询问是否送出
                                Builder builder = new AlertDialog.Builder(
                                        biaobenguanliActivity.this);
                                builder.setTitle("提示");
                                builder.setMessage("此标本尚未采集，是否采集？");
                                builder.setPositiveButton("是",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                // TODO Auto-generated method stub
                                                ThreadPoolManager.getInstance()
                                                        .addTask(new Runnable() {
                                                            public void run() {
                                                                boolean res = false;
                                                                res = MyUntils
                                                                        .yanbencaiji(
                                                                                data.get(
                                                                                        0)
                                                                                        .getBRID(),
                                                                                data.get(
                                                                                        0)
                                                                                        .getZYID(),
                                                                                TEST_NO_SRC,
                                                                                "1",
                                                                                Statics.user
                                                                                        .getUid());
                                                                if (res) {
                                                                    mhandler.sendEmptyMessage(102);
                                                                }
                                                            }
                                                        });
                                            }
                                        });
                                builder.setNegativeButton("否",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                // TODO Auto-generated method stub
                                                downlaoddata();
                                            }
                                        });
                                builder.show();
                            } else if (data.get(0).getCAIOK() == 1) {
                                // 原来CAIOK 为1已采 询问是否送出
                                Builder builder = new AlertDialog.Builder(
                                        biaobenguanliActivity.this);
                                builder.setTitle("提示");
                                builder.setMessage("此标本已采集，是否送出？");
                                builder.setPositiveButton("是",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                // TODO Auto-generated method stub
                                                ThreadPoolManager.getInstance()
                                                        .addTask(new Runnable() {
                                                            public void run() {
                                                                boolean res;
                                                                res = MyUntils
                                                                        .yanbencaiji(
                                                                                data.get(
                                                                                        0)
                                                                                        .getBRID(),
                                                                                data.get(
                                                                                        0)
                                                                                        .getZYID(),
                                                                                TEST_NO_SRC,
                                                                                "2",
                                                                                Statics.user
                                                                                        .getUid());
                                                                if (res) {
                                                                    mhandler.sendEmptyMessage(102);
                                                                }
                                                            }
                                                        });
                                            }
                                        });
                                builder.setNegativeButton("否",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                // TODO Auto-generated method stub
                                                downlaoddata();
                                            }
                                        });
                                builder.show();
                            }
                            downlaoddata();
                        }
                        break;
                    default:
                        break;
                }
            }

        };
        downlaoddata();
    }

    public static String TEST_NO_SRC = null;

    /**
     * 扫描定位的病人
     * @param tno
     */
    void searchBiaoBen(final String tno) {
        if (!HttpUtil.checkNet(this)) {
            Statics.internetcode = 0;
            int code = Statics.internetcode;
            MyUntils.myToast(this, Statics.internetstate[code]);
            return;
        }
        MyUntils.showProgress(this, "正在加载");

        ThreadPoolManager.getInstance().addTask(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                String patient_id = Statics.patientdesc.getPatient_id();
                String zid = Statics.patientdesc.getZy_id();
                data = MyUntils.searchBiaoBen(patient_id, zid,
                        StrTool.replaceBlank(tno));
                mhandler.sendEmptyMessage(103);
            }
        });
    }

    /**
     * downlaoddata from DataBase
     */
    void downlaoddata() {
        if (!HttpUtil.checkNet(this)) {
            Statics.internetcode = 0;
            int code = Statics.internetcode;
            MyUntils.myToast(this, Statics.internetstate[code]);
            return;
        }
        MyUntils.showProgress(this, "正在加载");

        ThreadPoolManager.getInstance().addTask(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                System.out.println("run---getmyPatient");
                String patient_id = Statics.patientdesc.getPatient_id();
                String zid = Statics.patientdesc.getZy_id();

                data = MyUntils.getYangben(patient_id, zid);
                mhandler.sendEmptyMessage(101);
            }
        });
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
