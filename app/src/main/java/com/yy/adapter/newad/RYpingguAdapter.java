package com.yy.adapter.newad;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.yy.app.R;
import com.yy.cookies.Statics;
import com.yy.entity.PingGu;
import com.yy.until.MyUntils;
import com.yy.until.StrTool;
import com.yy.until.ThreadPoolManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RYpingguAdapter extends BaseAdapter {
    //用于记录每个RadioButton的状态，并保证只可选一个
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();
    private List<PingGu> data;
    private static Context context;
    int cengji;
    private String saveok;
    private static boolean res = false; //提交结果

    /**
     * @param data
     * @param context
     * @param //cengji第一层也显示checkbox用来提交
     */
    public RYpingguAdapter(List<PingGu> data, Context context, int cengji) {
        super();
        this.cengji = cengji;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_rypinggulist, null); // 实例化convertView
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (data != null && data.size() > 0) {
            final PingGu pinggu = data.get(position);
            final int type = pinggu.getItemtype();
            //写入标题
            holder.tv_name = (TextView) convertView.findViewById(R.id.name);
            try {
                String item = new String(pinggu.getItemname().getBytes(), "utf-8");
                holder.tv_name.setText(item);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            switch (type) {
                case 0:// 有下级，显示下级标志
                    holder.iv_next = (ImageView) convertView.findViewById(R.id.next);
                    holder.iv_next.setVisibility(View.VISIBLE);
                    break;
                case 1:// 单选
                    final RadioButton radio = (RadioButton) convertView.findViewById(R.id.radio);
                    holder.rb_radio = radio;
                    holder.rb_radio.setVisibility(View.VISIBLE);
                    if (!pinggu.getItemtextvalue().equals("") && !pinggu.getItemtextvalue().equals("null"))
                        holder.rb_radio.setChecked(true);
                    holder.rb_radio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (String key : states.keySet()) {
                                states.put(key, false);
                            }
                            states.put(String.valueOf(position), radio.isChecked());
                            RYpingguAdapter.this.notifyDataSetChanged();
                        }
                    });
                    boolean res = false;
                    if (states.get(String.valueOf(position)) == null || !states.get(String.valueOf(position))) {
                        res = false;
                        states.put(String.valueOf(position), false);
                    } else
                        res = true;
                    holder.rb_radio.setChecked(res);
                    break;
                case 2:// 多选
                    holder.cb_check = (CheckBox) convertView.findViewById(R.id.check);
                    holder.cb_check.setVisibility(View.VISIBLE);
                    if (!pinggu.getItemtextvalue().equals("") && !pinggu.getItemtextvalue().equals("null"))
                        holder.cb_check.setChecked(true);
                        holder.cb_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            // TODO Auto-generated method stub
                            if (isChecked) {
                                saveok = Jsontosaveok(pinggu.getItemid(), 1, "");
                                data.get(position).setSelected(1);
                            } else {
                                saveok = Jsontosaveok(pinggu.getItemid(), 0, "");
                                data.get(position).setSelected(0);
                            }
                            submitdata(pinggu.getItemid(),holder);
                        }
                    });
                    break;
                case 3:// 显示输入文本框
                    final EditText editText = (EditText) convertView.findViewById(R.id.edit);
                    holder.et_edit = editText;
                    holder.et_edit.setVisibility(View.VISIBLE);
                    holder.et_edit.setText(pinggu.getItemtextvalue());
                    holder.cb_check = (CheckBox) convertView.findViewById(R.id.check);
                    holder.cb_check.setVisibility(View.VISIBLE);
                    holder.cb_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (holder.et_edit.getText().toString().equals("")) {
                                //EditText is null,for Toast
                                holder.cb_check.setChecked(false);
                                holder.et_edit.setFocusable(true);
                                MyUntils.myToast(context, "请先输入内容后提交！");
                            } else {
                                if (isChecked) {
                                    data.get(position).setItemtextvalue(
                                            holder.et_edit.getText().toString().trim());
                                    data.get(position).setSelected(1);
                                    saveok = Jsontosaveok(pinggu.getItemid(), data.get(position).getSelected(),
                                            holder.et_edit.getText().toString().trim());
                                    submitdata(pinggu.getItemid(),holder);
                                    holder.et_edit.setFocusable(false);
                                    notifyDataSetChanged();
                                } else {
                                    data.get(position).setItemtextvalue(
                                            holder.et_edit.getText().toString().trim());
                                    data.get(position).setSelected(0);
                                    saveok = Jsontosaveok(pinggu.getItemid(), data.get(position).getSelected(),
                                            holder.et_edit.getText().toString().trim());
                                    submitdata(pinggu.getItemid(),holder);
                                    holder.et_edit.setFocusable(false);
                                    notifyDataSetChanged();
                                }
                            }
                        }
                    });
                    break;
                case 4:// 显示输入文本框为时间选择框
                    final EditText editTextdate = (EditText) convertView.findViewById(R.id.edit);
                    holder.et_edit = editTextdate;
                    holder.et_edit.setVisibility(View.VISIBLE);
                    holder.et_edit.setText(pinggu.getItemtextvalue());
                    holder.et_edit.setInputType(InputType.TYPE_NULL);
                    holder.et_edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Calendar calendar = Calendar.getInstance();
                            new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    holder.et_edit.setText(String.valueOf(year) + "-"
                                            + StrTool.IntToString(monthOfYear + 1, 2) + "-"
                                            + StrTool.IntToString(dayOfMonth, 2));
                                    new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            holder.et_edit.setText(holder.et_edit.getText() + " "
                                                    + StrTool.IntToString(hourOfDay, 2) + ":"
                                                    + StrTool.IntToString(minute, 2));

                                            data.get(position).setItemtextvalue(
                                                    holder.et_edit.getText().toString().trim());
                                            saveok = Jsontosaveok(pinggu.getItemid(),
                                                    1, holder.et_edit.getText()
                                                            .toString().trim());
                                            submitdata(pinggu.getItemid(),holder);
                                            notifyDataSetChanged();
                                            holder.et_edit.setFocusable(false);
                                        }
                                    }, calendar.get(Calendar.HOUR_OF_DAY),
                                            calendar.get(Calendar.MINUTE),
                                            true).show();
                                }
                            }, calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                            ).show();
                        }

                    });
                    break;
                default:
                    break;
            }
        }
        return convertView;
    }

    private void submitdata(final String saveid, final Holder holder) {
        MyUntils.getUersInfo(context);
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                res = false;
                res = MyUntils.singlesubmit(Statics.SubmitPingGuURL, Statics.patientdesc.getPatient_id(),
                        Statics.patientdesc.getZy_id(), saveid, saveok, Statics.user.getUid());
                holder.mhandler.sendEmptyMessage(101);
            }
        });
    }

    private String Jsontosaveok(String itemid, int i, String txt) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("itemid", itemid);
            obj.put("savetype", i);
            obj.put("txt", txt);
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(obj);
            return jsonArray.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private static class Holder {
        CheckBox cb_check;
        RadioButton rb_radio;
        TextView tv_name;
        EditText et_edit;
        ImageView iv_next;
        View v_botemline;

        private Handler mhandler =  new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 101:
                        if (res)
                            MyUntils.myToast(context,"提交成功！");
                        else
                            MyUntils.myToast(context,"提交失败！");
                        break;
                    default:
                        break;
                }
            }
        };
    }
}