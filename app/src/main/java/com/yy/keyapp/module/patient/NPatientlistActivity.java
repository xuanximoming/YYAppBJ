package com.yy.keyapp.module.patient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.yy.adapter.newad.PatientAdapter;
import com.yy.app.AdvSearchActivity;
import com.yy.app.R;
import com.yy.cookies.Statics;
import com.yy.entity.Patient;
import com.yy.keyapp.config.NAPI;
import com.yy.keyapp.module.base.NBaseActivity;
import com.yy.until.ExitManager;
import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ukey on 2016/1/18.
 * Email:yeshentianyue@sina.com
 * 病人列表界面
 */
public class NPatientlistActivity extends NBaseActivity {

    //views
    ListView mListView;
    private TextView hushiname;
    private Button activity_title_bt2;
    private ImageButton gaojisearch;
    EditText search_patient;
    ProgressBar progressbar;
    private long mExitTime;

    PatientAdapter adapter;
    private List<Patient> data;

    private final int GET_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlist);
        MyUntils.getUersInfo(this);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] PatientInfo;
        if (!(Statics.Qcvalue == null)) {
            PatientInfo = Statics.Qcvalue.split("_");
            Statics.Qcvalue = null;
            search_patient.setText(PatientInfo[0]);
            Intent intent = new Intent(getActivity(),NPatientDescActivity.class);
            intent.putExtra("bid", PatientInfo[0]);
            intent.putExtra("vid", PatientInfo[1]);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        //连续两次返回键退出
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 3500) {
                Toast.makeText(this, "在按一次退出", Toast.LENGTH_LONG).show();
                mExitTime = System.currentTimeMillis();
            } else {
                ExitManager.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        hushiname = (TextView) findViewById(R.id.activity_title_back);
        hushiname.setText(Statics.user.getUtname());
        activity_title_bt2 = (Button) findViewById(R.id.activity_title_bt2);
        activity_title_bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(NPatientlistActivity.this, DialogActivity.class));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        gaojisearch = (ImageButton) findViewById(R.id.gaojisearch);
        gaojisearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AdvSearchActivity.class);
                startActivityForResult(i, GET_CODE);
                Statics.gaojisearch = 1;
            }
        });
        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(),
                        NPatientDescActivity.class);
                Patient patient = data.get(position);
                intent.putExtra("bid", patient.getPatient_id());
                intent.putExtra("vid", patient.getZy_id());
                startActivity(intent);
            }
        });

        // 搜索病人
        search_patient = (EditText) findViewById(R.id.search_patient);
        search_patient.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus)
                    search_patient.setHint(null);
            }
        });
        search_patient.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                search_patient.setHint(null);
            }
        });
        /**
         * EditText change even
         */
        search_patient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        search_patient.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // TODO Auto-generated method stub
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(NPatientlistActivity.this
                                        .getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                }
                return false;
            }
        });
    }

    private void initData() {
        progressbar.setVisibility(View.VISIBLE);
        if (!HttpUtil.checkNet(this)) {
            Statics.internetcode = 0;
            int code = Statics.internetcode;
            MyUntils.myToast(this, Statics.internetstate[code]);
            return;
        }
        Ion.with(this)
                .load(NAPI.patientList(Statics.user.getUid(), 1))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        progressbar.setVisibility(View.GONE);
                        if (e != null) {
                            return;
                        }
                        try {
                            JSONArray array = new JSONArray(result);
                            List<Patient> list = new ArrayList<Patient>();
                            if (array.length() > 0) {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject obj = array.getJSONObject(i);
                                    Patient patient = new Patient();
                                    patient.setPatient_id(obj.getString("BRID"));
                                    patient.setZy_id(obj.getString("ZYID"));
                                    patient.setName(obj.getString("BRNAME"));
                                    patient.setChuanghao(obj.optString("CHUANG"));
                                    patient.setGender(obj.getString("SEX"));
                                    patient.setZhuyi(obj.getString("ZHUYI"));
                                    patient.setPatient_birth(obj.getString("BIRTH"));
                                    patient.setTime(obj.getString("ZYDATE"));
                                    patient.setQuhao(obj.getString("BINGQU"));
                                    patient.setHulidengji(obj.getString("HULIDJ"));
                                    patient.setKeshi(obj.getString("DEPTNAME"));
                                    String str = obj.getString("BIRTH");
                                    SimpleDateFormat myformat = new SimpleDateFormat(
                                            "yyyy-MM-dd");
                                    Date date = new Date();
                                    Date mydata = myformat.parse(str);
                                    long day = (date.getTime() - mydata.getTime())
                                            / (24 * 60 * 60 * 1000) + 1;
                                    String year = new java.text.DecimalFormat("#")
                                            .format(day / 365f);
                                    patient.setAge(year);
                                    list.add(patient);
                                }
                                data = list;
                                adapter = new PatientAdapter(data, getActivity());
                                mListView.setAdapter(adapter);
                            } else {
                                MyUntils.myToast(getActivity(), "暂无信息");
                            }
                        } catch (JSONException eee) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (ParseException eeee) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
    }


}
