package com.yy.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.yy.cookies.Statics;
import com.yy.entity.PingGu;
import com.yy.until.ExitManager;
import com.yy.until.MyUntils;
import com.yy.until.StrTool;
import com.yy.until.ThreadPoolManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RYPinguUPActivity extends Activity implements OnClickListener {
    TextView activity_title_name;
    Button activity_title_back, activity_title_ok;
    Handler mhandler;
    public static List<PingGu> data;
    BaseAdapter adapter;
    LinearLayout ll_data;
    RadioGroup radiogroup;
    String itemid = "0";
    Button bt_save;
    boolean res = false; // 教育表单提交结果
    private String saveok;
    private TextView one_title;
    private String itemname;
    private List<String[]> title;
    List<PingGu> editdata;// 放是edit的数据
    List<PingGu> radiodata;// 放是radio的数据
    int cheradio = -1;// 选中了radiobutton id
    String radiotext = "";
    boolean excp_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingguup);
        ExitManager.getInstance().addActivity(this);
        MyUntils.getUersInfo(this);
        Intent intent = getIntent();
        itemid = intent.getStringExtra("itemid");
        itemname = intent.getStringExtra("itemname");
        title = new ArrayList<String[]>();
        title.add(new String[]{itemid, itemname});
        init();
        refreshtitle();
    }

    private void init() {
        // TODO Auto-generated method stub
        activity_title_name = (TextView) findViewById(R.id.activity_title_name);
        activity_title_name.setText("评估项");
        activity_title_back = (Button) findViewById(R.id.activity_title_back);
        activity_title_back.setOnClickListener(this);
        ll_data = (LinearLayout) findViewById(R.id.ll_data);
        editdata = new ArrayList<PingGu>();
        radiodata = new ArrayList<PingGu>();

        radiogroup = (RadioGroup) findViewById(R.id.radio_group);
        radiogroup
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // TODO Auto-generated method stub
                        cheradio = checkedId;
                        RadioButton radioButton = (RadioButton) findViewById(checkedId);
                        radiotext = radioButton.getText().toString();
                        for (int j = 0; j<data.size();j++){
                            if (String.valueOf(Integer.valueOf(data.get(j).getItemid().trim()))
                                    .equals(String.valueOf(cheradio).trim())
                                    && data.get(j).getExcp() == 1){
                                for (int i = 0;i < ll_data.getChildCount();i++){
                                    EditText edittext =  (EditText) ll_data.getChildAt(i).findViewById(R.id.edit);
                                    if(edittext.getVisibility() == View.VISIBLE){
                                        edittext.setHint(null);
                                        edittext.setInputType(InputType.TYPE_CLASS_TEXT);
                                    }
                                }
                                break;
                            }else {
                                for (int i = 0;i < ll_data.getChildCount();i++){
                                    EditText edittext =  (EditText) ll_data.getChildAt(i).findViewById(R.id.edit);
                                    if(edittext.getVisibility() == View.VISIBLE){
                                        edittext.setInputType(InputType.TYPE_NULL);
                                        SpannableString s = new SpannableString("正常不需填写");
                                        edittext.setHint(s);
                                    }
                                }
                            }
                        }
                    }
                });
        bt_save = (Button) findViewById(R.id.uplowd);
        bt_save.setOnClickListener(this);

        data = new ArrayList<PingGu>();
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
                                MyUntils.myToast(RYPinguUPActivity.this,
                                        Statics.internetstate[Statics.internetcode]);
                            }
                        } else if (data.size() > 0) {
                            bldata();
                        } else {
                            if (Statics.internetcode != -1) {
                                MyUntils.myToast(RYPinguUPActivity.this,
                                        Statics.internetstate[Statics.internetcode]);
                            }
                            MyUntils.myToast(RYPinguUPActivity.this, "获取信息失败");
                        }
                        break;
                    case 102:
                        MyUntils.cancleProgress();
                        if (res) {
                            MyUntils.myToast(RYPinguUPActivity.this, "表单提交成功");
                            if (title.size() > 1) {
                                title.remove(title.size() - 1);
                                itemid = (title.get(title.size() - 1))[0];
                                itemname = (title.get(title.size() - 1))[1];
                                refreshtitle();
                                downlaoddata();
                            } else
                                finish();
                        } else {
                            MyUntils.myToast(RYPinguUPActivity.this, "表单提交失败");
                        }
                        break;
                    default:
                        break;
                }
            }

        };
        downlaoddata();
    }

    private void refreshtitle() {
        one_title = (TextView) findViewById(R.id.one_title);
        one_title.setText(title.get(title.size() - 1)[1]);
    }

    void downlaoddata() {
        MyUntils.showProgress(this, "加载数据");

        ThreadPoolManager.getInstance().addTask(new Runnable() {
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
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.activity_title_back:
                if (title.size() > 1) {
                    title.remove(title.size() - 1);
                    itemid = (title.get(title.size() - 1))[0];
                    itemname = (title.get(title.size() - 1))[1];
                    refreshtitle();
                    downlaoddata();
                } else
                    finish();
                break;
            case R.id.uplowd:
                upbd();
                break;
        }
    }

    // 遍历数据列表
    @SuppressLint("ResourceAsColor")
    void bldata() {
        Statics.ruyuanpingguhashmap.clear();
        radiogroup.removeAllViews();
        ll_data.removeAllViews();
        editdata.clear();
        radiodata.clear();

        for (int i = 0; i < data.size(); i++) {
            final int position = i;
            final PingGu pinggu = data.get(i);
            View convertView = LayoutInflater.from(this).inflate(
                    R.layout.item_rypinguup, null); // 实例化convertView
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (data.get(position).getXiaji() == 1) {
                        itemid = data.get(position).getItemid();
                        itemname = data.get(position).getItemname();
                        title.add(new String[]{itemid, itemname});
                        refreshtitle();
                        downlaoddata();
                    }
                }
            });
            View botemline = convertView.findViewById(R.id.botemline);
            botemline.setVisibility(View.VISIBLE);
            TextView yizhu_item = (TextView) convertView
                    .findViewById(R.id.name);
            yizhu_item.setTextSize(18);
            yizhu_item.setTextColor(getResources().getColor(R.color.gray_80));
            try {
                String item = new String(pinggu.getItemname().getBytes(),
                        "utf-8");
                yizhu_item.setText(item);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            final EditText edit = (EditText) convertView
                    .findViewById(R.id.edit);
            final CheckBox check = (CheckBox) convertView.findViewById(R.id.check);

            final ImageView next = (ImageView) convertView.findViewById(R.id.next);
            final int type = pinggu.getItemtype();
            check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) {
                        saveok = Jsontosaveok(pinggu.getItemid(), 1, pinggu.getItemname());
                        data.get(position).setItemtextvalue(pinggu.getItemname());
                        data.get(position).setSelected(1);
                        pinggu.setSelected(1);
                    } else {
                        data.get(position).setSelected(0);
                        saveok = Jsontosaveok(pinggu.getItemid(), 0, pinggu.getItemname());
                        pinggu.setSelected(0);
                    }
                }
            });
            edit.setText(data.get(position).getItemtextvalue());
//			edit.setOnEditorActionListener(new OnEditorActionListener() {
//				public boolean onEditorAction(TextView v, int actionId,
//						KeyEvent event) {
//					// TODO Auto-generated method stub
//
//					((InputMethodManager) RYPinguUPActivity.this
//							.getSystemService(Context.INPUT_METHOD_SERVICE))
//							.hideSoftInputFromWindow(edit.getWindowToken(),
//									InputMethodManager.HIDE_NOT_ALWAYS);
//					data.get(position).setItemtextvalue(
//							edit.getText().toString().trim());
//					saveok = Jsontosaveok(pinggu.getItemid(),
//							pinggu.getSelected(), edit.getText().toString()
//									.trim());
//					edit.setFocusable(false);
//					pinggu.setItemtextvalue(edit.getText().toString().trim());
//					return false;
//				}
//			});


            switch (type) {
                case 0:// 有下级
                    next.setVisibility(View.VISIBLE);
                    break;
                case 1:// 单选
                    RadioButton button = new RadioButton(this);
                    button.setTextSize(18);
                    button.setTextColor(getResources().getColor(R.color.gray_80));
                    button.setBackgroundResource(R.drawable.fillet_radiobutton); // 设置RadioButton的背景图片
                    button.setButtonDrawable(R.drawable.selector_radiopg); // 设置按钮的样式
                    button.setPadding(70, 10, 0, 10); // 设置文字距离按钮四周的距离
                    button.setText(pinggu.getItemname());
                    String itemid2 = pinggu.getItemid();
                    Integer id = Integer.valueOf(itemid2);
                    button.setId(id);
                    radiodata.add(pinggu);
                    radiogroup.addView(button);
                    if (!pinggu.getItemtextvalue().equals("")
                            && !pinggu.getItemtextvalue().equals("null")) {
                        radiogroup.check(id);
                        if (pinggu.getExcp() != 1) excp_flag = true;
                    }
                    continue;
                    // break;
                case 2:// 多选
                    check.setVisibility(View.VISIBLE);
                    if (!pinggu.getItemtextvalue().equals("") && !pinggu.getItemtextvalue().equals("null"))
                        check.setChecked(true);
                    if (pinggu.getSelected() == 0) {
                        Statics.ruyuanpingguhashmap.remove(data.get(position)
                                .getItemid());
                        check.setChecked(false);
                    } else {
                        Statics.ruyuanpingguhashmap.put(data.get(position)
                                .getItemid(), pinggu);
                        check.setChecked(true);
                    }
                    break;
                case 3:// 输入
                    edit.setVisibility(View.VISIBLE);
                    if (excp_flag) {
                        edit.setInputType(InputType.TYPE_NULL);
                        SpannableString s = new SpannableString("正常不需填写");
                        edit.setHint(s);
                        excp_flag = false;
                    } else {

                    }
                    //Statics.ruyuanpingguhashmap.put(data.get(position).getItemid(),
                            //pinggu);
                    break;
                case 4:// 日期
                    edit.setVisibility(View.VISIBLE);
                    //radio.setVisibility(View.GONE);
                    edit.setInputType(InputType.TYPE_NULL);
                    //点击输入框后弹出时间选择器
                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Calendar calendar = Calendar.getInstance();
                            new DatePickerDialog(RYPinguUPActivity.this,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view,
                                                              int year, int monthOfYear,
                                                              int dayOfMonth) {
                                            // TODO Auto-generated method stub
                                            edit.setText(String.valueOf(year) + "-" + StrTool.IntToString((monthOfYear + 1), 2)
                                                    + "-" + StrTool.IntToString(dayOfMonth, 2));
                                            new TimePickerDialog(RYPinguUPActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                                @Override
                                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                    edit.setText(edit.getText() + " "
                                                            + StrTool.IntToString(hourOfDay, 2)
                                                            + ":" + StrTool.IntToString(minute, 2));

                                                    data.get(position).setItemtextvalue(
                                                            edit.getText().toString().trim());
                                                    saveok = Jsontosaveok(pinggu.getItemid(),
                                                            pinggu.getSelected(), edit
                                                                    .getText().toString()
                                                                    .trim());
                                                    edit.setFocusable(false);
                                                    pinggu.setItemtextvalue(edit.getText()
                                                            .toString().trim());
                                                }
                                            }, calendar.get(Calendar.HOUR_OF_DAY),
                                                    calendar.get(Calendar.MINUTE),
                                                    true).show();
                                        }
                                    }, calendar.get(Calendar.YEAR), calendar
                                    .get(Calendar.MONTH), calendar
                                    .get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });
                    //获取焦点后弹出时间选择框
                    edit.setOnFocusChangeListener(new OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            // TODO Auto-generated method stub
                            if (hasFocus) {
                                final Calendar calendar = Calendar.getInstance();
                                new DatePickerDialog(RYPinguUPActivity.this,
                                        new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(DatePicker view,
                                                                  int year, int monthOfYear,
                                                                  int dayOfMonth) {
                                                // TODO Auto-generated method stub
                                                edit.setText(String.valueOf(year) + "-" + StrTool.IntToString((monthOfYear + 1), 2)
                                                        + "-" + StrTool.IntToString(dayOfMonth, 2));
                                                new TimePickerDialog(RYPinguUPActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                                    @Override
                                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                        edit.setText(edit.getText() + " "
                                                                + StrTool.IntToString(hourOfDay, 2)
                                                                + ":" + StrTool.IntToString(minute, 2));

                                                        data.get(position).setItemtextvalue(
                                                                edit.getText().toString().trim());
                                                        saveok = Jsontosaveok(pinggu.getItemid(),
                                                                pinggu.getSelected(), edit
                                                                        .getText().toString()
                                                                        .trim());
                                                        edit.setFocusable(false);
                                                        pinggu.setItemtextvalue(edit.getText()
                                                                .toString().trim());
                                                    }
                                                }, calendar.get(Calendar.HOUR_OF_DAY),
                                                        calendar.get(Calendar.MINUTE),
                                                        true).show();
                                            }
                                        }, calendar.get(Calendar.YEAR), calendar
                                        .get(Calendar.MONTH), calendar
                                        .get(Calendar.DAY_OF_MONTH)).show();
                            }
                        }
                    });
                    Statics.ruyuanpingguhashmap.put(data.get(position).getItemid(),
                            pinggu);
                    break;
                default:
                    break;
            }
            if (pinggu.getXiaji() == 1) {
                next.setVisibility(View.VISIBLE);
            } else {
                next.setVisibility(View.GONE);
            }
            editdata.add(pinggu);
            ll_data.addView(convertView);
        }
    }

    // 提交数据
    void upbd() {
        MyUntils.showProgress(this, "加载数据");
        for (int i = 0; i < ll_data.getChildCount(); i++) {
            PingGu pinggu = editdata.get(i);
            EditText edit = (EditText) ll_data.getChildAt(i).findViewById(R.id.edit);
            if (!edit.getText().toString().equals("") && !edit.getText().toString().equals("null")){
                pinggu.setItemtextvalue(edit.getText().toString());
                pinggu.setSelected(1);
            }else if (!pinggu.getItemtextvalue().equals("") && !pinggu.getItemtextvalue().equals("null")){
                pinggu.setSelected(1);
            }
            Statics.ruyuanpingguhashmap.put(pinggu.getItemid(), pinggu);
        }
        if (cheradio != -1) {
            PingGu pinggu = new PingGu();
            pinggu.setItemid(cheradio + "");
            pinggu.setSelected(1);
            pinggu.setItemtextvalue(radiotext);
            Statics.ruyuanpingguhashmap.put(cheradio, pinggu);
            for (int i = 0; i < data.size(); i++){
                if (!String.valueOf(Integer.valueOf(data.get(i).getItemid().trim())).equals(String.valueOf(cheradio).trim())){
                    PingGu pingGu = new PingGu();
                    if (data.get(i).getItemtype() != 1 ) continue;
                    pingGu.setItemid(data.get(i).getItemid() + "");
                    pingGu.setSelected(0);
                    pingGu.setItemtextvalue(data.get(i).getItemname());
                    Statics.ruyuanpingguhashmap.put(data.get(i).getItemid(), pingGu);
                }
            }
        }
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                String patient_id = Statics.patientdesc.getPatient_id();
                String zid = Statics.patientdesc.getZy_id();
                res = MyUntils.submitRYdata(Statics.SubmitPingGuURL, patient_id, zid, itemid, "", Statics.user.getUid());
                mhandler.sendEmptyMessage(102);
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

}
