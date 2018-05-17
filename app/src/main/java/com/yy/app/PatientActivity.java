package com.yy.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.yy.adapter.newad.PatientAdapter;
import com.yy.cookies.Statics;
import com.yy.entity.Patient;
import com.yy.until.ExitManager;
import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;
import com.yy.until.StrTool;
import com.yy.until.ThreadPoolManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PatientActivity extends Activity implements OnClickListener,
        OnScrollListener {
    int page = 1;// 请求数据页数
    boolean isdownload = false;
    List<Patient> newdata;// 每次请求数据
    Handler mhandler;
    ListView list;
    PatientAdapter adapter;
    int lastItem;// 当前末尾项
    int itemcount = 0;// item的总个数
    public static List<Patient> data;
    private TextView hushiname;
    private Button activity_title_bt2;
    private ImageButton gaojisearch;
    EditText search_patient;
    ProgressBar progressbar;
    private boolean isjianyisearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlist);
        ExitManager.getInstance().addActivity(this);
        MyUntils.getUersInfo(this);
        InitView();
        MyUntils.myToast(PatientActivity.this, "onCreate sussesfull");
    }

    /***
     * 初始化view
     */
    void InitView() {
        data = new ArrayList<Patient>();
        list = (ListView) findViewById(R.id.listview);
        hushiname = (TextView) findViewById(R.id.activity_title_back);
        hushiname.setText(Statics.user.getUtname());
        activity_title_bt2 = (Button) findViewById(R.id.activity_title_bt2);
        activity_title_bt2.setOnClickListener(this);
        gaojisearch = (ImageButton) findViewById(R.id.gaojisearch);
        gaojisearch.setOnClickListener(mGetResult);
        adapter = new PatientAdapter(data, this);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        list.setAdapter(adapter);
        list.setOnScrollListener(this);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                if (isdownload) {
                    return;
                }
                Intent intent = new Intent(PatientActivity.this,
                        PatientDescActivity.class);
                // Intent intent = new Intent(PatientActivity.this,
                // PatientDescPagerActivity.class);
                Patient patient = data.get(position);
                intent.putExtra("bid", patient.getPatient_id());
                intent.putExtra("vid", patient.getZy_id());
                // intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        mhandler = new Handler() {
            @SuppressLint("HandlerLeak")
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                System.out.println("PatientActivity msg.what" + msg.what);
                switch (msg.what) {
                    case 101:
                        MyUntils.cancleProgress();
                        progressbar.setVisibility(View.GONE);
                        isdownload = false;// 下载结束
                        System.out.println("run---101");
                        if (newdata == null) {
                            if (Statics.internetcode != -1) {
                                MyUntils.myToast(PatientActivity.this,
                                        Statics.internetstate[Statics.internetcode]);
                            } else {
                                System.out.println("连网正常");
                                MyUntils.myToast(PatientActivity.this, "病人已加载完毕");

                            }
                        } else if (newdata.size() > 0) {
                            page++;
                            for (int i = 0; i < newdata.size(); i++) {
                                data.add(newdata.get(i));
                            }
                            itemcount = data.size();
                            adapter.notifyDataSetChanged();
                            if (newdata.size() < 10) {
                            }
                        } else {
                            MyUntils.myToast(PatientActivity.this, "病人已加载完毕");
                        }
                        break;
                    case 102:
                        progressbar.setVisibility(View.GONE);
                        // MyUntils.cancleProgress();
                        // 提交成功清空提交值
                        Statics.searchitemidhashmap = new HashMap();
                        // onResume();
                        if (Statics.searchpatientlist != null && isjianyisearch
                                && Statics.searchpatientlist.size() > 0) {
                            Intent intent = new Intent(PatientActivity.this,
                                    PatientDescActivity.class);
                            // Intent intent = new Intent(PatientActivity.this,
                            // PatientDescPagerActivity.class);
                            Patient patient = Statics.searchpatientlist.get(0);
                            intent.putExtra("bid", patient.getPatient_id());
                            intent.putExtra("vid", patient.getZy_id());
                            // intent.putExtra("position", position);
                            startActivity(intent);
                            isjianyisearch = false;
                        } else if (Statics.searchpatientlist != null) {
                            data = Statics.searchpatientlist;
                            adapter = new PatientAdapter(data, PatientActivity.this);
                            list.setAdapter(adapter);
                        } else {
                            MyUntils.myToast(PatientActivity.this, "没有搜索到病人信息");
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        // 搜索病人
        search_patient = (EditText) findViewById(R.id.search_patient);
        search_patient.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                // if (hasFocus) {
                search_patient.setHint(null);
                // } else {
                // search_patient.setHint("病人搜索");
                // }
            }
        });
        search_patient.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                search_patient.setHint(null);
            }
        });
        search_patient.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // TODO Auto-generated method stub

                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(PatientActivity.this
                                        .getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    isjianyisearch = true;
                    updata();
                }
                return false;
            }
        });
        downloaddata();
    }


    void downloaddata() {
        progressbar.setVisibility(View.VISIBLE);
        if (!HttpUtil.checkNet(this)) {
            Statics.internetcode = 0;
            int code = Statics.internetcode;
            MyUntils.myToast(this, Statics.internetstate[code]);
            return;
        }
        // progress.setVisibility(View.VISIBLE);
        MyUntils.showProgress(this, "正在加载");
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                System.out.println("run---getmyPatient");
                isdownload = true;// 正在下载
                newdata = MyUntils.GetPatientlist(Statics.user.getUid(), page);
                Message msg = new Message();
                msg.what = 101;
                mhandler.sendMessage(msg);
            }
        });
    }

    void updata() {
        // MyUntils.showProgress(this, "正在加载");
        Statics.searchpatientlist = new ArrayList<Patient>();
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                System.out.println("run---getmyPatient");

                Statics.searchpatientlist = MyUntils.Searchlist(
                        Statics.user.getUid(), "1", search_patient.getText()
                                .toString().trim());
                // if (Statics.searchpatientlist != null) {

                // }
                Message msg = new Message();
                msg.what = 102;
                mhandler.sendMessage(msg);
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        registerBoradcastReceiver();
        System.out.println("Statics.gaojisearch=" + Statics.gaojisearch);
        if (Statics.gaojisearch == 0) {
            search_patient.setText(null);
            search_patient.setHint("病人搜索");
            updata();
        } else {
            Statics.gaojisearch = 0;
        }
        if (search_patient != null) {
            search_patient.setText(null);
            search_patient.setHint("病人搜索");
        }
        // if (Statics.searchpatientlist != null) {
        // data = Statics.searchpatientlist;
        // adapter = new PatientAdapter(data, this);
        // list.setAdapter(adapter);
        // }
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.activity_title_bt2:
                startActivity(new Intent(this, AccountManagerActivity.class));
                break;
            case R.id.gaojisearch:
                startActivity(new Intent(this, AdvSearchActivity.class));
                break;
            default:
                break;
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        if (lastItem == itemcount && scrollState == this.SCROLL_STATE_IDLE) {
            if (!isdownload) {
                // 如果是最后一个，也没有在进行下载任务，进行下载
                downloaddata();
            }
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        // 当前list位置
        lastItem = firstVisibleItem + visibleItemCount;
        System.out.println("lastitem" + lastItem + "totalitemCount"
                + totalItemCount);
    }

    // @Override
    // protected void onResume() {
    // // TODO Auto-generated method stub
    // super.onResume();
    // if (!(data.size() > 0)) {
    // page = 1;// 请求数据页数
    // downloaddata();
    // }
    // }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // if ((System.currentTimeMillis() - mExitTime) > 2000) {
            // Toast.makeText(this, "在按一次退出", 2000).show();
            // mExitTime = System.currentTimeMillis();
            // } else {
            // ExitManager.getInstance().exit();
            // }
            return true;
        }
        // 拦截MENU按钮点击事件，让他无任何操作
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 扫描代码

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("value");
            text = StrTool.replaceBlank(text);
            search_patient.setText(text);
            // 拿到扫描的返回值
            System.out.println("text=" + text);
            // MyUntils.myToast(PatientActivity.this, text);
            // 调用搜索接口 传入扫描结果 text
            isjianyisearch = true;
            updata();
        }

    };

    public void registerBoradcastReceiver() {
        IntentFilter barcodeFilter = new IntentFilter("com.ge.action.barscan");
        // 注册广播
        registerReceiver(mBroadcastReceiver, barcodeFilter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if (requestCode == GET_CODE) {
            // Editable text = (Editable)mTextView.getText();

            if (resultCode == RESULT_OK) {
                // text.append("Cancel");
                Statics.gaojisearch = 1;
                onResume();
            } else {
                // text.append(data.getAction());
            }
        }
    }

    /*
     * GET_CODE requestCode
     */
    private final int GET_CODE = 0;

    private OnClickListener mGetResult = new OnClickListener() {
        public void onClick(View v) {
            Intent i = new Intent(PatientActivity.this, AdvSearchActivity.class);
            startActivityForResult(i, GET_CODE);
            Statics.gaojisearch = 1;
        }
    };
}
