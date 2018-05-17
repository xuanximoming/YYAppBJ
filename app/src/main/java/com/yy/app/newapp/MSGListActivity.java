package com.yy.app.newapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.yy.adapter.newad.MSGListAdapter;
import com.yy.app.R;
import com.yy.cookies.Statics;
import com.yy.entity.Messages;
import com.yy.until.ExitManager;
import com.yy.until.HttpUtil;
import com.yy.until.MyUntils;
import com.yy.until.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

public class MSGListActivity extends Activity implements OnClickListener,
        OnScrollListener {
    private ListView list;
    int page = 1;// 请求数据页数
    boolean isdownload = false;
    MSGListAdapter adapter;
    int lastItem;// 当前末尾项
    int itemcount = 0;// item的总个数
    List<Messages> newdata;// 每次请求数据
    private List<Messages> msglist, alldata;
    private Button activity_msgleftbtn;
    private Button activity_msgrigthbtn;
    private Button allbtn;
    EditText search_msg;
    private long mExitTime;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagelist);
        ExitManager.getInstance().addActivity(this);
        MyUntils.getUersInfo(this);
        initview();
    }

    void initview() {
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        allbtn = (Button) findViewById(R.id.activity_title_ok);
        allbtn.setOnClickListener(this);
        allbtn.setPressed(true);
        activity_msgleftbtn = (Button) findViewById(R.id.activity_msgleftbt);
        activity_msgleftbtn.setOnClickListener(this);
        activity_msgrigthbtn = (Button) findViewById(R.id.activity_msgrigthbt);
        activity_msgrigthbtn.setOnClickListener(this);
        search_msg = (EditText) findViewById(R.id.search_msg);
        search_msg.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    search_msg.setHint(null);
                } else {
                    search_msg.setHint("消息搜索");
                }
            }
        });
        search_msg.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // TODO Auto-generated method stub
                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    searchloaddata(v.getText().toString());
                    System.out.println("搜索消息");
                    // return true;
                    ((InputMethodManager) MSGListActivity.this
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(v.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

        list = (ListView) findViewById(R.id.msglistview);
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MSGListActivity.this,
                        MSGDescActivity.class);
                intent.putExtra("msgid", msglist.get(position).getMsgID());
                intent.putExtra("msgtype", msglist.get(position).getMsgType());
                startActivity(intent);
            }
        });
        list.setOnScrollListener(this);
        msglist = new ArrayList<Messages>();
        newdata = new ArrayList<Messages>();
        alldata = new ArrayList<Messages>();
    }

    private Handler myhandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 101:
                    progressbar.setVisibility(View.GONE);
                    MyUntils.cancleProgress();
                    if (newdata == null) {
                        if (Statics.internetcode != -1) {
                            MyUntils.myToast(MSGListActivity.this,
                                    Statics.internetstate[Statics.internetcode]);
                        }
                    } else if (newdata.size() > 0) {
                        for (int i = 0; i < newdata.size(); i++) {
                            msglist.add(newdata.get(i));
                        }
                        alldata = msglist;
                        itemcount = msglist.size();
                        if (page == 1) {
                            adapter = new MSGListAdapter(msglist,
                                    MSGListActivity.this);
                            list.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                        isdownload = false;// 下载结束
                        MyUntils.myToast(MSGListActivity.this, "获取信息成功");
                        MainTabActivity.badge1.hide();
                        if (newdata.size() < 10) {
                            isdownload = true;
                        }
                        page++;
                    } else {
                        isdownload = true;
                        MyUntils.myToast(MSGListActivity.this, "没有获取到信息");
                    }
                    break;

                default:
                    break;
            }
        }
    };


    void downloaddata() {
        progressbar.setVisibility(View.VISIBLE);
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
                isdownload = true;// 正在下载
                newdata = MyUntils.getMsgList(Statics.user.getUid(), page);
                myhandler.sendEmptyMessage(101);
            }
        });
    }

    // 搜索
    void searchloaddata(final String key) {
        if (!HttpUtil.checkNet(this)) {
            Statics.internetcode = 0;
            int code = Statics.internetcode;
            MyUntils.myToast(this, Statics.internetstate[code]);
            return;
        }
        if (page != 1) {
            System.out.println("pagNum=" + page);
        } else {
            MyUntils.showProgress(MSGListActivity.this, "正在加载信息");
        }
        msglist.clear();
        page = 1;// 请求数据页数
        ThreadPoolManager.getInstance().addTask(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                isdownload = true;// 正在下载
                newdata = MyUntils.getMsgList(Statics.user.getUid(), page, key);
                myhandler.sendEmptyMessage(101);
            }
        });
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
        lastItem = firstVisibleItem + visibleItemCount - 1;
        System.out.println("lastitem" + lastItem + "totalitemCount"
                + totalItemCount);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        activity_msgleftbtn.setPressed(false);
        activity_msgrigthbtn.setPressed(false);
        // if (!(msglist.size() > 0)) {
        if (null != msglist) {
            msglist.clear();
            alldata.clear();
        }
        page = 1;// 请求数据页数
        downloaddata();
        // }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.activity_msgleftbt:
                activity_msgrigthbtn.setPressed(true);
                allbtn.setPressed(false);
                if (alldata.size() > 0) {
                    msglist = new ArrayList<Messages>();
                    for (int i = 0; i < alldata.size(); i++) {
                        Messages messages = alldata.get(i);
                        if (messages.getMsgType() == 0
                                || messages.getMsgType() == 1) {
                            msglist.add(messages);
                        }
                    }
                }
                adapter = new MSGListAdapter(msglist, MSGListActivity.this);
                list.setAdapter(adapter);
                break;
            case R.id.activity_msgrigthbt:
                activity_msgleftbtn.setPressed(true);
                allbtn.setPressed(false);
                if (alldata.size() > 0) {
                    msglist = new ArrayList<Messages>();
                    for (int i = 0; i < alldata.size(); i++) {
                        Messages messages = alldata.get(i);
                        if (messages.getMsgType() == 0
                                || messages.getMsgType() == 1) {
                        } else {
                            msglist.add(messages);

                        }
                    }
                }
                adapter = new MSGListAdapter(msglist, MSGListActivity.this);
                list.setAdapter(adapter);
                break;
            case R.id.activity_title_ok:
                activity_msgleftbtn.setPressed(false);
                activity_msgrigthbtn.setPressed(false);
                msglist = new ArrayList<Messages>();
                msglist = alldata;
                adapter = new MSGListAdapter(msglist, MSGListActivity.this);
                list.setAdapter(adapter);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 3500) {
                Toast.makeText(this, "在按一次退出", Toast.LENGTH_LONG).show();
                mExitTime = System.currentTimeMillis();
            } else {
                ExitManager.getInstance().exit();
            }
            return true;
        }
        // 拦截MENU按钮点击事件，让他无任何操作
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
