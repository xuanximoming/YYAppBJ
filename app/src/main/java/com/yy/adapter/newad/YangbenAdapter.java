package com.yy.adapter.newad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yy.app.R;
import com.yy.entity.Yangben;
import com.yy.until.DateUtil;
import com.yy.until.StrTool;

import java.util.List;

public class YangbenAdapter extends BaseAdapter {
    private List<Yangben> data;
    private Context context;
    Yangben yangben;
    LinearLayout JianCha_msg;

    public YangbenAdapter(List<Yangben> data, Context context) {
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
        yangben = data.get(position);
        convertView = LayoutInflater.from(context).inflate(
                R.layout.item_yangben, null); // 实例化convertView

        TextView bigc = (TextView) convertView.findViewById(R.id.ybname);
        bigc.setText(yangben.getYBNAME());

        TextView smc = (TextView) convertView.findViewById(R.id.ybcode);
        smc.setText(yangben.getTEST_NO_SRC());

        TextView doct = (TextView) convertView.findViewById(R.id.cjname);
        String doctstr = Stringisnull(yangben.getCJNAME());
        doct.setText(doctstr);

        TextView timetv = (TextView) convertView.findViewById(R.id.cjtime);
        String getTime = DateUtil.strToStr(yangben.getCJTIME(),
                "MM-dd HH:mm");
        timetv.setText(StrTool.null2str(getTime));
        JianCha_msg = (LinearLayout) convertView.findViewById(R.id.yizhu_msg);

        switch (yangben.getCAIOK()){
            case 0:
                JianCha_msg.setBackgroundResource(R.drawable.fillet_ypred);
                break;
            case 1:
                JianCha_msg.setBackgroundResource(R.drawable.fillet_ypgreen);
                break;
            case 2:
                JianCha_msg.setBackgroundResource(R.drawable.fillet_ypblue);
                break;
            default:
                JianCha_msg.setBackgroundResource(R.drawable.fillet_ypred);
                break;
        }

        return convertView;

    }

    public static String Stringisnull(String trim) {
        String trim2 = trim.toString().trim();
        if (trim2.equals("null") || trim2.equals("") || trim2 == null) {
            return "";
        } else {
            return trim2;
        }
    }
}
