package com.yy.adapter.newad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yy.app.R;
import com.yy.entity.Messages;

import java.util.List;

public class MSGListAdapter extends BaseAdapter {
    private List<Messages> data;
    private Context context;

    public MSGListAdapter(List<Messages> data, Context context) {
        super();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_newmsg, null); // 实例化convertView
            holder.readis = (ImageView) convertView.findViewById(R.id.state);
            holder.msgtype = (ImageView) convertView.findViewById(R.id.icon);
            holder.msgtitle = (TextView) convertView.findViewById(R.id.title);
            holder.msgmemo = (TextView) convertView.findViewById(R.id.desc);
            holder.msgtime = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }


        if (data != null && data.size() > 0) {

            Messages msg = data.get(position);
            holder.msgtitle.setText(msg.getMsgTitle());
            holder.msgtime.setText(msg.getMsgTime());
            holder.msgmemo.setText(msg.getMsgMemo());
            if (msg.getMsgread() > 0) {// 为0为未读或未处理，大于0则为已读或已处理
                // holder.msgtype.setBackgroundResource(R.drawable.msg_read);
                holder.readis.setVisibility(View.INVISIBLE);
            } else {
                holder.readis.setVisibility(View.VISIBLE);
            }

            switch (msg.getMsgType()) {// ：0为群发通知，1为一对一通知，2为群发任务，3为一对一任务
                case 0:
                    holder.msgtype.setBackgroundResource(R.drawable.icon_qumsg);
                    break;

                case 1:
                    holder.msgtype.setBackgroundResource(R.drawable.icon_onemsg);
                    break;
                case 2:
                    holder.msgtype.setBackgroundResource(R.drawable.icon_qujob);
                    break;
                case 3:
                    holder.msgtype.setBackgroundResource(R.drawable.icon_onejob);
                    break;
            }
        }
        return convertView;

    }

    final class Holder {
        public ImageView msgtype, readis;
        public TextView msgtitle, msgmemo, msgtime;
    }
}
