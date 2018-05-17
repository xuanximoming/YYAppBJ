package com.yy.app.newapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yy.adapter.YizhuAdapter;
import com.yy.app.R;
import com.yy.cookies.Statics;
import com.yy.entity.Yizhu;
import com.yy.entity.Yizhus;
import com.yy.key.QRcodeScan.activity.CaptureActivity;
import com.yy.keyapp.module.base.BaseServer;
import com.yy.until.ExitManager;
import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;
import com.yy.until.StrTool;
import com.yy.until.ThreadPoolManager;
import com.yy.until.VibratorUtil;
import com.yy.view.MyDialog;
import com.yy.view.MyDialog.LeaveMyDialogListener;

import java.util.ArrayList;
import java.util.List;

public class YizhuzxdActivity extends Activity implements OnClickListener {
    TextView bq_tv, bc_tv, zhuyi_tv, patient_name, patient_gender,
            patient_age, patient_feibie, patient_hldj, patient_bh,
            activity_title_name;
    List<Yizhu> data;
    Handler mhandler;
    ProgressBar progress;
    ListView listview;
    Yizhus datas;
    YizhuAdapter adapter;
    Button activity_title_back, activity_title_ok;
    RadioButton rgb_fuyaodan, rgb_zhushedan, rgb_shuyedan, rgb_zhiliaodan,
            rgb_hulidan;
    /**
     * 默认A 服药单 B、注射单 C、输液单 D、治疗单 E、护理单
     */
    String zt = "C";
    /**
     * 医嘱执行单号
     */
    String yzoid = "";
    String scancodetext = "";

    private TextView side_time;
    private TextView item_xuhao;
    /**
     * 立即执行结果 默认false
     */
    boolean res = false;
    private TextView patient_yue;
    private TextView patient_zhenduan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yizhuzxd);
        ExitManager.getInstance().addActivity(this);
        MyUntils.getUersInfo(this);
        init();
        // dia();
    }

    private void dia() {
        // TODO Auto-generated method stub
        String pid = Statics.patientdesc.getPatient_id();
        pid = pid.toUpperCase();
        yzoid = yzoid.toUpperCase();
        String text = pid + "T" + yzoid;
        if (mydialog != null) {
            mydialog.dismiss();
            mydialog = null;
        }
        if (!text.equals(pid + "T" + yzoid)) {

            showfailedDialog();
            VibratorUtil.Vibrate(YizhuzxdActivity.this, 2000);

        } else {
            showSuccessDialog();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        /**
         * 扫描广播注册
         */
        registerReceiver(mBroadcastReceiver, new BaseServer().RegisterBoradcastReceiver());
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        /**
         * 取消扫描广播注册
         */
        unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    public static String Failmsg = null;

    private void init() {
        // TODO Auto-generated method stub
        progress = (ProgressBar) findViewById(R.id.progress);
        activity_title_name = (TextView) findViewById(R.id.activity_title_name);
        activity_title_name.setText("医嘱执行单");
        activity_title_ok = (Button) findViewById(R.id.activity_title_ok);
        activity_title_ok.setVisibility(View.VISIBLE);
        activity_title_ok.setText("扫描");
        activity_title_ok.setOnClickListener(this);
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

        rgb_fuyaodan = (RadioButton) findViewById(R.id.rgb_fuyaodan);
        rgb_fuyaodan.setOnClickListener(this);
        rgb_zhushedan = (RadioButton) findViewById(R.id.rgb_zhushedan);
        rgb_zhushedan.setOnClickListener(this);
        rgb_shuyedan = (RadioButton) findViewById(R.id.rgb_shuyedan);
        rgb_shuyedan.setOnClickListener(this);
        rgb_zhiliaodan = (RadioButton) findViewById(R.id.rgb_zhiliaodan);
        rgb_zhiliaodan.setOnClickListener(this);
        rgb_hulidan = (RadioButton) findViewById(R.id.rgb_hulidan);
        rgb_hulidan.setOnClickListener(this);

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

        data = new ArrayList<Yizhu>();

        mhandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                    case 101:
                        if (datas != null) {
                            data = datas.getYizhulist();
                            progress.setVisibility(View.GONE);
                            listview.setVisibility(View.VISIBLE);
                            if (data == null || data.size() == 0) {
                                data.clear();
                                adapter = new YizhuAdapter(data, YizhuzxdActivity.this);
                                listview.setAdapter(adapter);
                                if (Statics.internetcode != -1) {
                                    MyUntils.myToast(YizhuzxdActivity.this,
                                            Statics.internetstate[Statics.internetcode]);
                                }
                                if (Failmsg != null || !Failmsg.equals("")) {
                                    MyUntils.myToast(YizhuzxdActivity.this,
                                            Failmsg);
                                } else {
                                    MyUntils.myToast(YizhuzxdActivity.this,
                                            "此病人暂无执行单信息");
                                }
                            } else if (data.size() > 0) {
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
                                yzoid = data.get(0).getYZOID();
                                System.out.println(data.toString());
                                data = datas.getYizhulist();
                                adapter = new YizhuAdapter(data, YizhuzxdActivity.this);
                                adapter.notifyDataSetChanged();
                                listview.setAdapter(adapter);
                            } else {
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
                                data.clear();
                                adapter = new YizhuAdapter(data, YizhuzxdActivity.this);
                                listview.setAdapter(adapter);
                                MyUntils.myToast(YizhuzxdActivity.this, "此病人暂无执行单信息");
                            }
                        }
                        break;
                    case 102:
                        if (res) {
                            MyUntils.myToast(YizhuzxdActivity.this, "医嘱执行单执行成功");
                            downlaoddata();
                        } else {
                            MyUntils.myToast(YizhuzxdActivity.this, "医嘱执行单执行失败");
                        }
                        break;
                    default:
                        break;
                }
            }

        };
        downlaoddata();
    }

    void downlaoddata() {
        if (!HttpUtil.checkNet(this)) {
            Statics.internetcode = 0;
            int code = Statics.internetcode;
            MyUntils.myToast(this, Statics.internetstate[code]);
            System.out.println("在下载的时候。。。。。。。");
            return;
        }
        progress.setVisibility(View.VISIBLE);
        listview.setVisibility(View.GONE);
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                String patient_id = Statics.patientdesc.getPatient_id();
                String zid = Statics.patientdesc.getZy_id();
                datas = MyUntils.getYizhuzxd(patient_id, zid, zt, "all");
                mhandler.sendEmptyMessage(101);
            }
        });
    }

    void downlaoddatarefalsh(final String patientid, final String zids) {
        if (!HttpUtil.checkNet(this)) {
            Statics.internetcode = 0;
            int code = Statics.internetcode;
            MyUntils.myToast(this, Statics.internetstate[code]);
            System.out.println("在下载的时候。。。。。。。");
            return;
        }
        progress.setVisibility(View.VISIBLE);
        listview.setVisibility(View.GONE);
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                String patient_id = Statics.patientdesc.getPatient_id();
                String zid = Statics.patientdesc.getZy_id();
                datas = MyUntils.getYizhuzxd(patientid, zids, zt, "all");
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
            case R.id.rgb_fuyaodan:
                zt = "A";
                downlaoddata();
                break;
            case R.id.rgb_zhushedan:
                zt = "B";
                downlaoddata();
                break;
            case R.id.rgb_shuyedan:
                zt = "C";
                downlaoddata();
                break;
            case R.id.rgb_zhiliaodan:
                zt = "D";
                downlaoddata();
                break;
            case R.id.rgb_hulidan:
                zt = "E";
                downlaoddata();
                break;
            case R.id.activity_title_ok:
                startActivityForResult(new Intent(YizhuzxdActivity.this, CaptureActivity.class), CaptureActivity.REQ_CODE);
                break;
            default:
                break;
        }
    }


    /**
     * 扫描注册实现方法
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            barscan(context,intent,null);
        }
    };

    /**
     * 使用摄像头扫描的结果处理方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        Statics.Qcvalue = data.getStringExtra("change01");
                        if (!(Statics.Qcvalue.equals("") && Statics.Qcvalue != null)){
                            barscan(null,null,Statics.Qcvalue);
                            Statics.Qcvalue = "";
                        }
                        break;
                    case RESULT_CANCELED:
                        if (data != null) {
                            // for some reason camera is not working correctly
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    /**
     * 格式化扫描到的医嘱执行单时间
     *
     * @return
     */
    public String getScanTime(String scanTime) {
        String[] newlyDotimeArr = scanTime.split("-");
        String newlyYear = newlyDotimeArr[0];
        String newlyMonth = newlyDotimeArr[1];
        String newlyDay = newlyDotimeArr[2].substring(0, 2);

        String newlyTimeAll = newlyDotimeArr[2].substring(2, newlyDotimeArr[2].length());
        String hour, minute, second;
        if (newlyTimeAll.length() == 5) {
            hour = newlyTimeAll.substring(0, 1);
            minute = newlyTimeAll.substring(1, 3);
            second = newlyTimeAll.substring(3, 5);
        } else {
            hour = newlyTimeAll.substring(0, 2);
            minute = newlyTimeAll.substring(2, 4);
            second = newlyTimeAll.substring(4, 6);
        }
        if (newlyMonth.length() == 1) {
            newlyMonth = "0" + newlyMonth;
        }
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        String qrcodeDate = newlyYear + "-" + newlyMonth + "-" + newlyDay;
        String qrcodeTime = hour + ":" + minute + ":" + second;
        return qrcodeDate + " " + qrcodeTime;
    }

    MyDialog mydialog = null;

    public void showfailedDialog() {
        mydialog = new MyDialog(this, R.style.CustomProgressDialog,
                R.layout.dialog_failed, new LeaveMyDialogListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                switch (view.getId()) {
                    case R.id.dialog_btnok:
                        mydialog.dismiss();
                        break;

                    default:
                        break;
                }
            }
        });
        mydialog.show();
    }

    public void show1Dialog() {
        mydialog = new MyDialog(this, R.style.CustomProgressDialog,
                R.layout.dialog_1, new LeaveMyDialogListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                switch (view.getId()) {
                    case R.id.dialog_btnok:
                        mydialog.dismiss();
                        break;

                    default:
                        break;
                }
            }
        });
        mydialog.show();
    }

    public void showSuccessDialog() {
        mydialog = new MyDialog(this, R.style.CustomProgressDialog,
                R.layout.dialog_success, new LeaveMyDialogListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                switch (view.getId()) {
                    case R.id.dialog_btnok:
                        mydialog.dismiss();
                        if (!HttpUtil.checkNet(YizhuzxdActivity.this)) {
                            Statics.internetcode = 0;
                            int code = Statics.internetcode;
                            MyUntils.myToast(YizhuzxdActivity.this,
                                    Statics.internetstate[code]);
                            System.out.println("在showsuccessdialog");
                            break;
                        }
                        ThreadPoolManager.getInstance().addTask(
                                new Runnable() {
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        String patient_id =
                                                Statics.patientdesc.getPatient_id();
                                        String zid =
                                                Statics.patientdesc.getZy_id();
                                        res = MyUntils.getYizhuzxdljzx(
                                                patient_id, zid, zt,
                                                scancodetext,
                                                Statics.UNAME);
                                        mhandler.sendEmptyMessage(102);
                                    }
                                });
                        break;

                    default:
                        break;
                }
            }
        });
        mydialog.show();
    }


    /**
     * 解析并处理扫描后的数据
     * @param context  传入向下文
     * @param intent   扫描后的结果
     */
    private void barscan(Context context, Intent intent,String qcvalue){

        //扫描结果
        String text;
        if (intent != null){
            text = intent.getStringExtra("value");
        }else {
            text = qcvalue;
        }

        text = StrTool.replaceBlank(text);
        // 拿到扫描的返回值
        System.out.println("text=" + text);
        String pid = Statics.patientdesc.getPatient_id();
        pid = pid.toUpperCase();
        yzoid = yzoid.toUpperCase();
        text = text.toUpperCase();
        scancodetext = text;
        // 接口返回的医嘱执行单号 规则（核对号：当核对号为“病人IDT*”T后边为星号时，则只核对病人ID
        // 即只要扫描出来的“病人IDT单号”中的病人ID，与核对号中的病人ID相同，则核对成功。
        // 如T后边不是星号，则直接整串核对。）


        if (datas != null) {

            String HEDUICODE = datas.getHEDUICODE();

            if (HEDUICODE == null || "".equals(HEDUICODE)) {
                HEDUICODE = "";
            } else {
                HEDUICODE = HEDUICODE.toUpperCase();
            }
            if (mydialog != null) {
                mydialog.dismiss();
                mydialog = null;
            }
            System.out.println("扫描到的结果是：" + text);


            //扫描到的信息包含T
            if ((text.contains("T")) && (!"".equals(text))) {
                //截取后的接口字符
                String HEDUICODE1 = "";
                //截取后的扫描字符
                String text1 = "";
                //接口返回的信息包含T*  只对比T*之前的内容 所以要截取字符串 对应的扫描结果text同样截取出T之前的标识号
                if ((HEDUICODE.contains("T*")) && (!"".equals(HEDUICODE)) && (text.contains("T")) && (!"".equals(text))) {
                    text1 = text.substring(0, text.indexOf("T"));
                    HEDUICODE1 = HEDUICODE.substring(0, HEDUICODE.indexOf("T"));
                }

                //接口返回的信息包含T* 对比T*之前的内容
                if ((HEDUICODE.contains("T*")) && (!"".equals(HEDUICODE))
                       && (HEDUICODE1.equals(text1))) {
                        /*showSuccessDialog();*/

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("datas!=null  and scanText=" + text).append("\n");
                    List<Yizhu> yizhuList = datas.getYizhulist();
                    if (yizhuList != null && yizhuList.size() != 0) {
                        buffer.append("yizhuList.size()=" + yizhuList.size()).append("\n");
                        int status = 0;//1是可以执行的医嘱，2是已执行的医嘱，0该药品不在执行单内
                        String scanYizhuOId = text.split("T")[1];
                        String scanYizhuDate = text.split("T")[2];
                        buffer.append("scanYizhuOId=" + scanYizhuOId).append("\n");
                        buffer.append("scanYizhuDate=" + scanYizhuDate).append("\n");
                        for (int i = 0; i < yizhuList.size(); i++) {
                            Yizhu item = yizhuList.get(i);
                            buffer.append("Yizhu item YZOID=" + item.getYZOID()).append("\n");
                            if (item.getYZOID().equals(scanYizhuOId)) {
                                buffer.append("item.getYZOID().equals(scanYizhuOId)").append("\n");
                                String formatScanTimeDate = getScanTime(scanYizhuDate);
                                buffer.append("formatScanTimeDate=" + formatScanTimeDate).append("\n");
                                if (item.getZX_TIME().equals(formatScanTimeDate)) {
                                    buffer.append("item.getYZ_COLOR()=" + item.getYZ_COLOR()).append("\n");
                                    if (item.getYZ_COLOR().equals("#009900")) {
                                        buffer.append("item.getYZ_COLOR().equals(\"#009900\"").append("\n");
                                        status = 2;
                                    } else {
                                        buffer.append("!!!!item.getYZ_COLOR().equals(\"#009900\"").append("\n");
                                        status = 1;
                                    }
                                    break;
                                }
                            }
                        }
                        buffer.append("status=" + status).append("\n");
                        final int finalStatus = status;
                        new AlertDialog.Builder(YizhuzxdActivity.this)
                                .setTitle("仅供测试，请先截图该对话框")
                                .setMessage("" + buffer.toString())
                                .setPositiveButton("点击确定继续", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (finalStatus) {
                                            case 0:
                                                Toast.makeText(YizhuzxdActivity.this, "该药品不在执行单内，请核对！", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 1:
                                                showSuccessDialog();
                                                break;
                                            case 2:
                                                show1Dialog();
                                                break;
                                        }
                                    }
                                }).create().show();


                    } else {
                        Toast.makeText(YizhuzxdActivity.this, "该药品不在执行单内，请核对！", Toast.LENGTH_SHORT).show();
                    }

                }
                //接口返回的信息包含T不包含* 则整个HEDUICODE和扫描结果对比
                else if ((HEDUICODE.contains("T")) && (!"".equals(HEDUICODE))
                        && (HEDUICODE.equals(text))) {

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("datas!=null  and scanText=" + text).append("\n");
                    List<Yizhu> yizhuList = datas.getYizhulist();
                    if (yizhuList != null && yizhuList.size() != 0) {
                        buffer.append("yizhuList.size()=" + yizhuList.size()).append("\n");
                        int status = 0;//1是可以执行的医嘱，2是已执行的医嘱，0该药品不在执行单内
                        String scanYizhuOId = text.split("T")[1];
                        String scanYizhuDate = text.split("T")[2];
                        buffer.append("scanYizhuOId=" + scanYizhuOId).append("\n");
                        buffer.append("scanYizhuDate=" + scanYizhuDate).append("\n");
                        for (int i = 0; i < yizhuList.size(); i++) {
                            Yizhu item = yizhuList.get(i);
                            buffer.append("Yizhu item YZOID=" + item.getYZOID()).append("\n");
                            if (item.getYZOID().equals(scanYizhuOId)) {
                                buffer.append("item.getYZOID().equals(scanYizhuOId)").append("\n");
                                String formatScanTimeDate = getScanTime(scanYizhuDate);
                                buffer.append("formatScanTimeDate=" + formatScanTimeDate).append("\n");
                                if (item.getZX_TIME().equals(formatScanTimeDate)) {
                                    buffer.append("item.getYZ_COLOR()=" + item.getYZ_COLOR()).append("\n");
                                    if (item.getYZ_COLOR().equals("#009900")) {
                                        buffer.append("item.getYZ_COLOR().equals(\"#009900\"").append("\n");
                                        status = 2;
                                    } else {
                                        buffer.append("!!!!item.getYZ_COLOR().equals(\"#009900\"").append("\n");
                                        status = 1;
                                    }
                                    break;
                                }
                            }
                        }
                        buffer.append("status=" + status).append("\n");
                        final int finalStatus = status;
                        new AlertDialog.Builder(YizhuzxdActivity.this)
                                .setTitle("仅供测试，请先截图该对话框")
                                .setMessage("" + buffer.toString())
                                .setPositiveButton("点击确定继续", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (finalStatus) {
                                            case 0:
                                                Toast.makeText(YizhuzxdActivity.this, "该药品不在执行单内，请核对！", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 1:
                                                showSuccessDialog();
                                                break;
                                            case 2:
                                                show1Dialog();
                                                break;
                                        }
                                    }
                                }).create().show();


                    } else {
                        Toast.makeText(YizhuzxdActivity.this, "该药品不在执行单内，请核对！", Toast.LENGTH_SHORT).show();
                    }

                }
                //其他情况都是弹出失败
                else {
                    showfailedDialog();
                    VibratorUtil.Vibrate(YizhuzxdActivity.this, 2000);
                }

            }
            //扫描到的信息包含不包含T 为病人id
            else {
                //病人id为当前病人id时不做任何操作
                if (text.equals(pid)) {

                }
                //不是当前病人id 则刷新当前页面信息
                else {
                    downlaoddatarefalsh(text, "0");
                }
            }

        }

    }


}
