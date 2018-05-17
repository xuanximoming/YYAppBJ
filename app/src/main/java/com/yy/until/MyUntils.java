package com.yy.until;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.yy.app.biaobenguanliActivity;
import com.yy.app.newapp.YizhuzxdActivity;
import com.yy.cookies.SharedPrefs;
import com.yy.cookies.Statics;
import com.yy.entity.HuLiDesc;
import com.yy.entity.Hulilist;
import com.yy.entity.JianCha;
import com.yy.entity.JianYan;
import com.yy.entity.JianYanRes;
import com.yy.entity.JiaoJieBan;
import com.yy.entity.JiaoJieBanDes;
import com.yy.entity.JiaoYuForm;
import com.yy.entity.Messages;
import com.yy.entity.Patient;
import com.yy.entity.PingGu;
import com.yy.entity.SearchForm;
import com.yy.entity.ShouShu;
import com.yy.entity.User;
import com.yy.entity.Yangben;
import com.yy.entity.Yizhu;
import com.yy.entity.Yizhus;
import com.yy.entity.Yizhutype;
import com.yy.view.CustomProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyUntils {
    /**
     * 功能描述：从SharedPreferences中获取登录信息配置
     *
     * @param context
     */
    public static void getUrl(Context context) {
        String readIP = SharedPrefs.readIP(context);
        String readDuanKou = SharedPrefs.readDuanKou(context);
        String readChuanMa = SharedPrefs.readChuanMa(context);
        if (!StrTool.isnull(readChuanMa) && !StrTool.isnull(readDuanKou)
                && !StrTool.isnull(readIP)) {
            Statics.IP = readIP;
            Statics.Port = readDuanKou;
            Statics.API = readChuanMa;
        }
    }

    /**
     * 功能描述：获取登录的护士信息
     *
     * @param context
     */
    public static void getUersInfo(Context context) {
        String userid = SharedPrefs.readUserId(context);
        String hushiname = SharedPrefs.readHuShiName(context);
        String keshi = SharedPrefs.readKeshi(context);
        String username = SharedPrefs.readUserName(context);
        if (!StrTool.isnull(userid) && !StrTool.isnull(hushiname)
                && !StrTool.isnull(keshi) && !StrTool.isnull(username)) {
            Statics.UNAME = username;
            Statics.user.setKeshi(keshi);
            Statics.user.setUid(userid);
            Statics.user.setUtname(hushiname);
        }
    }

    public static CustomProgressDialog prg;

    public static void showProgress(Context context, String msg) {
        prg = CustomProgressDialog.createDialog(context);
        prg.setMessage(msg);
        prg.setCancelable(false);// 返回都不管用
        prg.show();
    }

    public static void cancleProgress() {
        if (prg != null) {
            prg.cancel();
        }
    }

    public static void myToast(Context context, String str) {
        // TODO Auto-generated method stub
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 检测是否连接服务器
     *
     * @param
     * @param
     * @return
     */
    public static boolean link() {
        String forGet = HttpUtil.queryStringForGet("http://" + Statics.IP + ":"
                + Statics.Port + Statics.LINKURL + "api=" + Statics.API);
        System.out.println("http://" + Statics.IP + ":" + Statics.Port
                + Statics.LINKURL + "api=" + Statics.API);
        if (forGet == null || forGet.equals("")) {
            return false;
        }
        try {
            JSONObject jsobj = new JSONObject(forGet);
            int status = jsobj.getInt("status");
            String linkok = jsobj.getString("linkok");
            if (status == 1 && linkok.equals("linkok")) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 病人列表
     *
     * @param uid
     * @param pageNo
     * @return
     */
    public static List<Patient> GetPatientlist(String uid, int pageNo) {
        String forGet = HttpUtil.queryStringForGet("http://" + Statics.IP + ":"
                + Statics.Port + Statics.PatientListURL + "api=" + Statics.API
                + "&uid=" + uid + "&curpage=" + pageNo);
        System.out.println("http://" + Statics.IP + ":" + Statics.Port
                + Statics.PatientListURL + "api=" + Statics.API + "&uid=" + uid
                + "&curpage=" + pageNo);
        if (forGet == null || forGet.equals("")) {
            return null;
        }
        try {

            JSONArray array = new JSONArray(forGet);
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
                    /*
                     * String date = obj.getString("createTime"); if (null !=
					 * date && !"".equals(date) && !"null".equals(date)) {
					 * Patient.setCreateTime(DateUtil.strToStr(date,
					 * "yy-MM-dd HH:mm")); } else { Patient.setCreateTime(""); }
					 */
                    list.add(patient);
                }
                return list;
            } else {
                return null;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 功能描述： 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     * <p/>
     * 1.select * from staff_dict where user_name='' and password=''
     * <p/>
     * 2.select dept_name from dept_dict where dept_code IN
     * (select dept_code from dept_vs_ward where ward_code=
     * (select group_code from staff_vs_group where emp_no='' and rownum <=1 ))
     */
    public static User dologin(String username, String password) {
        try { //加密用户密码
            password = password.toString().toUpperCase();
            String Passwordtmp, Strtem = "";
            int k;
            for (int i = 0; i < password.length(); i++) {
                char[] Singword;
                Passwordtmp = password.substring(i, i + 1);
                Singword = Passwordtmp.toCharArray();
                if ((i + 1) % 2 == 0) {
                    k = (int) Singword[0] + (i + 1) - 32;
                } else {
                    k = (int) Singword[0] - (i + 1) + 8;
                }
                Strtem = Strtem + (char) k;
            }
            password = Strtem;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("登录URL＝" + "http://" + Statics.IP + ":"
                + Statics.Port + Statics.LoginURL + "api=" + Statics.API
                + "&uname=" + username + "&upwd=" + password);
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.LoginURL + "api="
                + Statics.API + "&uname=" + username + "&upwd=" + password);

        System.out.println("登录返回" + queryStringForGet);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        User user = new User();
        try {
            JSONObject jsobj = new JSONObject(queryStringForGet);
            int status = jsobj.getInt("status");
            if (status == 0)
                return null;
            user.setUid(jsobj.getString("uid"));
            user.setUtname(jsobj.getString("utname"));// 返回用户名信息为UTF-8编码格式字符串
            user.setKeshi(jsobj.getString("keshi"));
            user.setTimeout(jsobj.getInt("timeout"));
            Statics.timeout = user.getTimeout();
            System.out.println("timeout=====" + Statics.timeout);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 病人详细信息
     *
     * @param bid 病人ID
     * @param vid 本次住院ID
     * @param t   上一床和下一床的标识（up，next）
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Patient GetPatient(String bid, String vid, String t) {
        String string;
        if (t == null || "".equals(t)) {
            System.out.println("http://" + Statics.IP + ":" + Statics.Port
                    + Statics.PatientDescURL + "api=" + Statics.API + "&bid="
                    + bid + "&vid=" + vid);
            string = HttpUtil.queryStringForGet("http://" + Statics.IP + ":"
                    + Statics.Port + Statics.PatientDescURL + "api="
                    + Statics.API + "&bid=" + bid + "&vid=" + vid);
        } else {
            string = HttpUtil.queryStringForGet("http://" + Statics.IP + ":"
                    + Statics.Port + Statics.PatientDescURL + "api="
                    + Statics.API + "&bid=" + bid + "&vid=" + vid + "&t=" + t);
            System.out.println(Statics.PatientDescURL + "api=" + Statics.API
                    + "&bid=" + bid + "&vid=" + vid + "&t=" + t);
        }
        System.out.println("GetPatient" + string);
        if (string == null || string.equals("")) {
            return null;
        }
        try {
            JSONArray array = new JSONArray(string);
            if (array.length() > 0) {
                JSONObject obj = array.getJSONObject(0);
                Patient patient = new Patient();
                // "BRID":"1000073170","ZYID":1,
                // "BRNAME":"\u674E\u514B\u8363","CHUANG":30,
                // "SEX":"\u5973","ZHUYI":"\u53F6\u632F",""
                // "BIRTH":"1955-5-28 1:00:00","ZYDATE":"\u53F6\u632F",
                // "BINGQU":"\u56DB\u75C5\u533A","HULIDJ":"\u4E8C\u7EA7\u62A4\u7406"
                patient.setPatient_id(obj.getString("BRID"));
                patient.setZy_id(obj.getString("ZYID"));
                patient.setName(obj.getString("BRNAME"));
                patient.setChuanghao(obj.getString("CHUANG"));
                patient.setGender(obj.getString("SEX"));
                patient.setZhuyi(obj.getString("ZHUYI"));

                patient.setPatient_birth(obj.getString("BIRTH"));

                patient.setTime(obj.getString("ZYDATE"));
                patient.setQuhao(obj.getString("BINGQU"));
                patient.setHulidengji(obj.getString("HULIDJ"));
                patient.setZHENDUAN(obj.getString("ZHENDUAN"));
                patient.setFEIBIE(obj.getString("FEIBIE"));
                patient.setFEIYUE(obj.getString("FEIYUE"));

                // SimpleDateFormat sdf = new
                // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // // String str ="2013-12-03 14:42:41.0";
                String str = obj.getString("BIRTH");
                // String substring = str.substring(0, str.length() - 2);
                // System.out.println(substring);
                // Date date = sdf.parse(substring); // string转换为date
                // Calendar cal = Calendar.getInstance(); // date to calendar
                // cal.setTime(date); // 设置时间

                SimpleDateFormat myformat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Date mydata = myformat.parse(str);
                long day = (date.getTime() - mydata.getTime())
                        / (24 * 60 * 60 * 1000) + 1;
                String year = new java.text.DecimalFormat("#")
                        .format(day / 365f);
                patient.setAge(year);
                System.out.println("病人详细返回" + patient.toString());
                return patient;
            } else {
                System.out.println("病人详细返回null");
                return null;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 功能描述：返回消息列表
     *
     * @param uid     用户id
     * @param curpage 页数
     * @return
     */
    public static List<Messages> getMsgList(String uid, int curpage) {
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.MessageListURL
                + "api=" + Statics.API + "&uid=" + uid + "&curpage=" + curpage);
        System.out.println("http://"
                + Statics.IP + ":" + Statics.Port + Statics.MessageListURL
                + "api=" + Statics.API + "&uid=" + uid + "&curpage=" + curpage);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        List<Messages> msglist = new ArrayList<Messages>();
        try {
            JSONArray jsarray = new JSONArray(queryStringForGet);
            System.out.println("jsarray=" + jsarray.length());
            for (int i = 0; i < jsarray.length(); i++) {
                JSONObject jsobj = jsarray.getJSONObject(i);
                Messages msg = new Messages();
                msg.setMsgID(jsobj.optInt("msgID"));
                msg.setMsgType(jsobj.optInt("msgType"));
                msg.setMsgTitle(jsobj.optString("msgTitle"));
                msg.setMsgMemo(jsobj.optString("msgmemo"));
                msg.setMsgTime(jsobj.optString("msgTime"));
                msg.setMsgread(jsobj.optInt("msgread"));
                msglist.add(msg);
            }
            Collections.reverse(msglist);
            return msglist;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return msglist;
        }

    }

    /**
     * 搜索消息
     *
     * @param uid
     * @param curpage
     * @param srhkey
     * @return
     */
    public static List<Messages> getMsgList(String uid, int curpage,
                                            String srhkey) {
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.MessageListURL
                + "api=" + Statics.API + "&uid=" + uid + "&curpage=" + curpage
                + "&srhkey=" + srhkey);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        System.out.println("getMsgList search=" + queryStringForGet);
        List<Messages> msglist = new ArrayList<Messages>();
        try {
            JSONArray jsarray = new JSONArray(queryStringForGet);
            System.out.println("jsarray=" + jsarray.length());
            for (int i = 0; i < jsarray.length(); i++) {
                JSONObject jsobj = jsarray.getJSONObject(i);
                Messages msg = new Messages();
                msg.setMsgID(jsobj.getInt("msgID"));
                msg.setMsgType(jsobj.getInt("msgType"));
                msg.setMsgTitle(jsobj.getString("msgTitle"));
                msg.setMsgMemo(jsobj.getString("msgmemo"));
                msg.setMsgTime(jsobj.getString("msgTime"));
                msg.setMsgread(jsobj.getInt("msgread"));
                msglist.add(msg);
            }
            Collections.reverse(msglist);
            return msglist;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 功能描述：返回推送消息列表
     *
     * @param uid 用户id
     * @param
     * @return List<> 页数
     */
    public static List<Messages> getNewMsgList(String uid) {
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.NEWMessageURL
                + "api=" + Statics.API + "&uid=" + uid);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        List<Messages> msglist = new ArrayList<Messages>();
        try {
            JSONArray jsarray = new JSONArray(queryStringForGet);
            System.out.println("推送信息-jsarray=" + jsarray.length());
            for (int i = 0; i < jsarray.length(); i++) {
                JSONObject jsobj = jsarray.getJSONObject(i);
                Messages msg = new Messages();
                msg.setMsgID(jsobj.getInt("msgID"));
                msg.setMsgType(jsobj.getInt("msgType"));
                msg.setMsgTitle(jsobj.getString("msgTitle"));
                msg.setMsgMemo(jsobj.getString("msgmemo"));
                msg.setMsgTime(jsobj.getString("msgTime"));
                // msg.setMsgread(jsobj.getInt("msgread"));
                msglist.add(msg);
            }
            return msglist;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 医嘱
     *
     * @param bid
     * @param vid
     * @return
     */
    public static Yizhus getYizhu(String bid, String vid, String ot, String cl) {
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.YizhuURL + "api="
                + Statics.API + "&bid=" + bid + "&vid=" + vid + "&ot=" + ot
                + "&cl=" + cl);
        System.out.println("http://" + Statics.IP + ":" + Statics.Port
                + Statics.YizhuURL + "api=" + Statics.API + "&bid=" + bid
                + "&vid=" + vid + "&ot=" + ot + "&cl=" + cl);
        System.out.println("返回信息" + queryStringForGet);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        Yizhus data = new Yizhus();
        List<Yizhu> yizhulist = new ArrayList<Yizhu>();
        List<Yizhutype> typelist = new ArrayList<Yizhutype>();
        List<Yizhutype> cllist = new ArrayList<Yizhutype>();
        try {
            JSONObject jsonObject = new JSONObject(queryStringForGet);

            JSONArray type = jsonObject.getJSONArray("orderType");
            JSONArray clarry = jsonObject.getJSONArray("orderCL");
            JSONArray array = jsonObject.getJSONArray("orderList");

            // 第一个是全部
            Yizhutype yizhutypeall = new Yizhutype();
            yizhutypeall.setName("全部");
            typelist.add(yizhutypeall);
            for (int i = 0; i < type.length(); i++) {
                Yizhutype yizhutype = new Yizhutype();
                JSONObject typeobj = type.getJSONObject(i);
                yizhutype.setName(typeobj.getString("name"));
                yizhutype.setCode(typeobj.getString("code"));
                typelist.add(yizhutype);
            }
            data.setTypelist(typelist);

            // 第一个是全部
            Yizhutype yizhuclall = new Yizhutype();
            yizhuclall.setName("全部");
            cllist.add(yizhuclall);
            for (int i = 0; i < clarry.length(); i++) {
                Yizhutype yizhucl = new Yizhutype();
                JSONObject clobj = clarry.getJSONObject(i);
                yizhucl.setName(clobj.getString("name"));
                yizhucl.setCode(clobj.getString("code"));
                yizhucl.setColor(clobj.getString("color"));
                cllist.add(yizhucl);
            }
            data.setCllist(cllist);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Yizhu yizhu = new Yizhu();
                yizhu.setBRID(object.getString("BRID"));
                yizhu.setYZ_DANWEI(object.getString("YZ_DANWEI"));
                yizhu.setYZ_ITEM(object.getString("YZ_ITEM"));
                yizhu.setYZ_JINUM(object.optString("YZ_JINUM"));
                yizhu.setZYID(object.getString("ZYID"));
                yizhu.setZX_TIME(object.getString("ZX_TIME"));
                yizhu.setZX_MEMO(object.getString("ORDER_CLASS_NAME"));
                yizhu.setYZ_COLOR(object.getString("YZ_COLOR"));
                yizhu.setYZ_TYPE(object.getString("YZ_TYPE"));
                yizhu.setYZ_CHLIN(object.getString("YZ_CHLIN"));
                yizhulist.add(yizhu);

            }
            data.setYizhulist(yizhulist);
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 医嘱执行单
     *
     * @param bid
     * @param vid
     * @param zt
     * @return
     */
    public static Yizhus getYizhuzxd(String bid, String vid, String zt,
                                     String ot) {
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.Yizhu_zxdURL
                + "api=" + Statics.API + "&bid=" + bid + "&vid=" + vid + "&ot="
                + ot + "&zt=" + zt + "&cl=all");
        System.out.println("http://" + Statics.IP + ":" + Statics.Port
                + Statics.Yizhu_zxdURL + "api=" + Statics.API + "&bid=" + bid
                + "&vid=" + vid + "&ot=" + ot + "&zt=" + zt + "&cl=all");
        // System.out.println("返回信息" + queryStringForGet);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        System.out.println("resultJson=" + queryStringForGet);
        Yizhus data = new Yizhus();
        List<Yizhu> yizhulist = new ArrayList<Yizhu>();
        List<Yizhutype> typelist = new ArrayList<Yizhutype>();
        List<Yizhutype> cllist = new ArrayList<Yizhutype>();
        try {
            JSONObject jsonObject = new JSONObject(queryStringForGet);

            // JSONArray type = jsonObject.getJSONArray("orderType");
            // JSONArray cl = jsonObject.getJSONArray("orderCL");
            YizhuzxdActivity.Failmsg = jsonObject.optString("Failmsg");
            System.out.println("YizhuzxdActivity.Failmsg=" + YizhuzxdActivity.Failmsg);
            JSONArray array = jsonObject.optJSONArray("orderList");
            JSONArray bingarry = jsonObject.optJSONArray("bingren");
            if (bingarry.length() > 0) {
                JSONObject bingrenjson = bingarry.optJSONObject(0);
                Statics.patientdesc.setPatient_id(bingrenjson.optString("BRID"));
                Statics.patientdesc.setZy_id(bingrenjson.optString("ZYID"));
                Statics.patientdesc.setName(bingrenjson.optString("BRNAME"));
                Statics.patientdesc.setChuanghao(bingrenjson.optString("CHUANG"));
                Statics.patientdesc.setGender(bingrenjson.optString("SEX"));
                Statics.patientdesc.setZhuyi(bingrenjson.optString("ZHUYI"));
                Statics.patientdesc.setTime(bingrenjson.optString("ZYDATE"));
                Statics.patientdesc.setQuhao(bingrenjson.optString("BINGQU"));
                Statics.patientdesc
                        .setHulidengji(bingrenjson.optString("HULIDJ"));
                Statics.patientdesc
                        .setZHENDUAN(bingrenjson.optString("ZHENDUAN"));
                Statics.patientdesc.setFEIBIE(bingrenjson.optString("FEIBIE"));
                Statics.patientdesc.setFEIYUE(bingrenjson.optString("FEIYUE"));
                String str = bingrenjson.optString("BIRTH");
                SimpleDateFormat myformat = new SimpleDateFormat(
                        "yyyy-MM-dd");
                Date date = new Date();
                Date mydata = myformat.parse(str);
                long day = (date.getTime() - mydata.getTime())
                        / (24 * 60 * 60 * 1000) + 1;
                String year = new java.text.DecimalFormat("#")
                        .format(day / 365f);
                Statics.patientdesc.setAge(year);
            }
            int TOP1YZOID = jsonObject.optInt("TOP1YZOID");
            String HEDUICODE = jsonObject.optString("HEDUICODE");
            data.setTOP1YZOID(TOP1YZOID);
            data.setHEDUICODE(HEDUICODE);

            // // 第一个是全部
            // Yizhutype yizhutypeall = new Yizhutype();
            // yizhutypeall.setName("全部");
            // typelist.add(yizhutypeall);
            // for (int i = 0; i < type.length(); i++) {
            // Yizhutype yizhutype = new Yizhutype();
            // JSONObject typeobj = type.getJSONObject(i);
            // yizhutype.setName(typeobj.getString("name"));
            // yizhutype.setCode(typeobj.getString("code"));
            // typelist.add(yizhutype);
            // }
            // data.setTypelist(typelist);

            // 第一个是全部
            // Yizhutype yizhuclall = new Yizhutype();
            // yizhuclall.setName("全部");
            // cllist.add(yizhuclall);
            // for (int i = 0; i < cl.length(); i++) {
            // Yizhutype yizhucl = new Yizhutype();
            // JSONObject clobj = cl.getJSONObject(i);
            // yizhucl.setName(clobj.getString("name"));
            // yizhucl.setCode(clobj.getString("code"));
            // cllist.add(yizhucl);
            // }
            // data.setCllist(cllist);
            if (array != null) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    Yizhu yizhu = new Yizhu();
                    yizhu.setBRID(object.optString("BRID"));
                    yizhu.setYZ_DANWEI(object.optString("YZ_DANWEI"));
                    yizhu.setYZ_ITEM(object.optString("YZ_ITEM"));
                    yizhu.setYZ_JINUM(object.optString("YZ_JINUM"));
                    yizhu.setZYID(object.optString("ZYID"));
                    yizhu.setYZOID(object.optString("YZOID"));// 医嘱执行单号
                    yizhu.setZX_TIME(object.optString("ZX_TIME"));
                    yizhu.setZX_MEMO(object.optString("ORDER_CLASS_NAME"));
                    yizhu.setYZ_COLOR(object.optString("YZ_COLOR"));
                    yizhu.setYZ_TYPE(object.optString("YZ_TYPE"));
                    yizhu.setYZ_CHLIN(object.optString("YZ_CHLIN"));
                    yizhu.setORDER_CLASS_NAME(object.optString("ORDER_CLASS_NAME"));
                    yizhulist.add(yizhu);
                }
            }
            data.setYizhulist(yizhulist);
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 医嘱执行单立即执行
     * @param bid 病人ID
     * @param vid 住院次数
     * @param zt
     * @param decode
     * @param uid  用户名
     * @return
     */
    public static boolean getYizhuzxdljzx(String bid, String vid, String zt,
                                          String decode, String uid) {
        System.out.println("http://" + Statics.IP + ":" + Statics.Port
                + Statics.Yizhu_ljzxURL + "api=" + Statics.API + "&bid=" + bid
                + "&vid=" + vid + "&oid=" + zt + "&otype=" + decode);
        String queryStringForPost = HttpUtil.queryStringForPost("http://"
                        + Statics.IP + ":" + Statics.Port + Statics.Yizhu_ljzxURL,
                new String[]{"api", "bid", "vid", "zt", "decode", "uid"},
                new String[]{Statics.API, bid, vid, zt, decode, uid});
        System.out.println("submithulidata=" + queryStringForPost);
        if (queryStringForPost == null || queryStringForPost.equals("")) {
            return false;
        }
        try {
            JSONObject jsobj = new JSONObject(queryStringForPost);
            int state = jsobj.getInt("status");// 0为存入失败，1为存入成功
            if (state == 1) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 检查
     *
     * @param bid
     * @param vid
     * @return
     */
    public static List<JianCha> getJianCha(String bid, String vid) {
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.JianChaURL + "api="
                + Statics.API + "&bid=" + bid + "&vid=" + vid);
        System.out.println(Statics.JianChaURL + "api=" + Statics.API + "&bid="
                + bid + "&vid=" + vid);
        System.out.println("返回信息" + queryStringForGet);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        List<JianCha> data = new ArrayList<JianCha>();
        try {
            JSONArray array = new JSONArray(queryStringForGet);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                JianCha yizhu = new JianCha();
                yizhu.setBRID(object.getString("BRID"));
                yizhu.setZYID(object.getString("ZYID"));
                yizhu.setJC_ID(object.getString("JC_ID"));
                yizhu.setJC_BIGC(object.getString("JC_BIGC"));
                yizhu.setJC_SMC(object.getString("JC_SMC"));
                yizhu.setJC_MEMO(object.getString("JC_MEMO"));
                yizhu.setJC_DATE(object.getString("JC_DATE"));
                yizhu.setJC_DOCT(object.getString("JC_DOCT"));
                data.add(yizhu);

            }
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检验
     *
     * @param bid
     * @param vid
     * @return
     */
    public static List<JianYan> getJianYan(String bid, String vid) {
        System.out.println("http://" + Statics.IP + ":" + Statics.Port
                + Statics.JianYanURL + "api=" + Statics.API + "&bid=" + bid
                + "&vid=" + vid);
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.JianYanURL + "api="
                + Statics.API + "&bid=" + bid + "&vid=" + vid);

        System.out.println("返回信息" + queryStringForGet);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        List<JianYan> data = new ArrayList<JianYan>();
        try {
            JSONArray array = new JSONArray(queryStringForGet);
            /*
             * 检验ID 检验标本 临床诊断 检验日期 检验结果列表 项目名称 结果 标准值
			 * 
			 * String JY_ID, JY_BIAOBEN, JY_ZHENDUAN, JY_DATE, JY_RESULT,
			 * REITEM, RESULTOK, ZHENGCHANG;
			 */
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                JianYan jianyan = new JianYan();
                jianyan.setJY_ID(object.getString("JY_ID"));
                jianyan.setJY_BIAOBEN(object.getString("JY_BIAOBEN"));
                jianyan.setJY_ZHENDUAN(object.getString("JY_ZHENDUAN"));
                jianyan.setJY_DATE(object.getString("JY_DATE"));
                // JSONArray arrayres = new JSONArray(
                // object.getString("JY_RESULT"));
                // List<JianYanRes> jianyandata = new ArrayList<JianYanRes>();
                // for (int j = 0; j < arrayres.length(); j++) {
                // JSONObject objectres = array.getJSONObject(j);
                // JianYanRes jianyanres = new JianYanRes();
                // jianyanres.setREITEM(objectres.getString("REITEM"));
                // jianyanres.setRESULTOK(objectres.getString("RESULTOK"));
                // jianyanres.setZHENGCHANG(objectres.getString("ZHENGCHANG"));
                // jianyandata.add(jianyanres);
                // }
                // jianyan.setJY_RESULT(jianyandata);
                data.add(jianyan);

            }
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param bid  病人ID
     * @param vid  住院次数
     * @param jyid ID
     * @return
     */
    public static List<JianYanRes> getJianYanres(String bid, String vid,
                                                 String jyid) {
        System.out.println("http://" + Statics.IP + ":" + Statics.Port
                + Statics.JianYanresURL + "api=" + Statics.API + "&bid=" + bid
                + "&vid=" + vid + "&jyid=" + jyid);
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.JianYanresURL
                + "api=" + Statics.API + "&bid=" + bid + "&vid=" + vid
                + "&jyid=" + jyid);

        System.out.println("返回信息" + queryStringForGet);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        List<JianYanRes> jianyandata = new ArrayList<JianYanRes>();
        try {
            JSONArray array = new JSONArray(queryStringForGet);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                JianYanRes jianyanres = new JianYanRes();
                jianyanres.setREITEM(obj.optString("REPORT_ITEM_NAME"));
                String resultok = obj.optString("RESULT") + obj.optString("UNITS");
                jianyanres.setRESULTOK(resultok);
                jianyanres.setZHENGCHANG(obj.optString("RESULT_RANGE"));
                String state = obj.optString("ABNORMAL_INDICATOR");
                if (TextUtils.isEmpty(state)) {
                    state = "N";
                }
                jianyanres.setSTATE(state);
                jianyandata.add(jianyanres);
            }
            return jianyandata;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return jianyandata;
        }
    }

    /**
     * 手术
     *
     * @param bid
     * @param vid
     * @return
     */
    public static List<ShouShu> getShouShu(String bid, String vid) {
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.ShouShuURL + "api="
                + Statics.API + "&bid=" + bid + "&vid=" + vid);
        System.out.println("http://" + Statics.IP + ":" + Statics.Port
                + Statics.ShouShuURL + "api=" + Statics.API + "&bid=" + bid
                + "&vid=" + vid);
        System.out.println("返回信息" + queryStringForGet);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        List<ShouShu> data = new ArrayList<ShouShu>();
        try {
            JSONArray array = new JSONArray(queryStringForGet);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                ShouShu shoushu = new ShouShu();
                shoushu.setBRID(object.getString("BRID"));
                shoushu.setZYID(object.getString("ZYID"));
                shoushu.setSHOUSHU_ID(object.getString("SHOUSHU_ID"));
                shoushu.setSHOUSHU_NAME(object.getString("SHOUSHU_NAME"));
                shoushu.setSHOUSHU_DATE(object.getString("SHOUSHU_DATE"));
                shoushu.setSHOUSHU_BUWEI(object.getString("SHOUSHU_BUWEI"));
                shoushu.setSHOUSHU_YISHI(object.getString("SHOUSHU_YISHI"));
                data.add(shoushu);

            }
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 功能描述：获取信息详情
     *
     * @param uid   用户ID
     * @param msgid 信息ID
     * @return 信息详情
     */
    public static Messages getMsgDesc(String uid, int msgid) {
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.MessageDescURL
                + "api=" + Statics.API + "&uid=" + uid + "&msgid=" + msgid);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        try {
            JSONObject jsobj = new JSONObject(queryStringForGet);
            Messages msg = null;
            msg = new Messages();
            msg.setMsgID(jsobj.optInt("msgID"));
            msg.setMsgType(jsobj.optInt("msgType"));
            msg.setMsgTitle(jsobj.optString("msgTitle"));
            msg.setMsgMemo(jsobj.optString("msgmemo"));
            msg.setMsgTime(jsobj.optString("msgTime"));
            msg.setMsgread(jsobj.optInt("msgread"));
            return msg;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 功能描述：信息处理（任务处理）
     *
     * @param uid
     * @param msgid
     * @return
     */
    public static boolean doMsg(String uid, int msgid) {
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.MessageDoURL
                + "api=" + Statics.API + "&uid=" + uid + "&msgid=" + msgid);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return false;
        }
        try {
            JSONObject jsobj = new JSONObject(queryStringForGet);
            boolean success = false;
            // 返回status：1（处理成功），msgread:1（已处理）
            int int1 = jsobj.getInt("status");
            if (int1 == 1) {
                success = true;
            }
            return success;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 护理列表
     *
     * @param bid
     * @param zid
     * @return没访问到返回null，无数据返回空list
     */
    public static List<Hulilist> getHuliList(String bid, String zid) {
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.HuliURL + "api="
                + Statics.API + "&bid=" + bid + "&vid=" + zid);
        ArrayList<Hulilist> data = new ArrayList<Hulilist>();
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        try {
            JSONArray array = new JSONArray(queryStringForGet);
            for (int i = 0; i < array.length(); i++) {
                Hulilist huli = new Hulilist();
                JSONObject obj = array.getJSONObject(i);
                huli.setCOUNUM(obj.getString("COUNUM"));
                huli.setRECORDING_DATE(obj.getString("RECORDING_DATE"));
                data.add(huli);
            }
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 护理历史
     *
     * @param bid
     * @param vid
     * @param date
     * @param cls  A：生命体征 ，B：出量 ，C：入量 ，D：其他 ，E：事故
     * @return
     * @return没访问到返回null，无数据返回空list
     */
    public static List<HuLiDesc> getHuLiDescList(String bid, String vid,
                                                 String date, String cls) {
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.HulilishiURL
                + "api=" + Statics.API + "&bid=" + bid + "&vid=" + vid
                + "&hldate=" + date + "&cls=" + cls);
        System.out.println("getHuLiDescList=" + queryStringForGet);
        ArrayList<HuLiDesc> data = new ArrayList<HuLiDesc>();
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        try {
            JSONArray array = new JSONArray(queryStringForGet);
            for (int i = 0; i < array.length(); i++) {
                HuLiDesc huli = new HuLiDesc();
                JSONObject obj = array.optJSONObject(i);
                huli.setCLASS_CODE(obj.optString("CLASS_CODE"));
                huli.setHL_ITEM(obj.optString("HL_ITEM"));
                huli.setUNITS(obj.optString("UNITS"));
                huli.setVITAL_CODE(obj.optString("VITAL_CODE"));
                huli.setHL_VALUE(obj.optString("HL_VALUE"));
                huli.setHL_TIME(obj.optString("HL_TIME"));
                huli.setMIN_VALUE(obj.optInt("MIN_VALUE"));
                huli.setMAX_VALUE(obj.optInt("MAX_VALUE"));
                huli.setALERTMSG(obj.optString("ALERTMSG"));
                huli.WARD_CODE = "";//新增护理所需要的WARD_CODE,已交于后台处理
                huli.NEW_CLASS_VITAL_CODE = obj.optString("VITAL_CODE");//新增护理所需要的vital_code，和huli.setVITAL_CODE(obj.getString("hlid"));不同
                data.add(huli);
            }
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 获取 护理类别项目名称
     *
     * @param cls A：生命体征 ，B：出量 ，C：入量 ，D：其他 ，E：事故
     * @return
     */
    public static List<HuLiDesc> getHulilitypeURL(String cls, String bid) {
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.HulilitypeURL
                + "api=" + Statics.API + "&cls=" + cls + "&bid=" + bid);
        ArrayList<HuLiDesc> data = new ArrayList<HuLiDesc>();
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        try {
            System.out.println("getHulilitypeURL=" + queryStringForGet);
            JSONObject jsobj = new JSONObject(queryStringForGet);
            JSONArray array = jsobj.getJSONArray("orderitem");
            for (int i = 0; i < array.length(); i++) {
                HuLiDesc huli = new HuLiDesc();
                JSONObject obj = array.getJSONObject(i);
                huli.setHl_type(obj.getString("hl_type"));
                huli.setHL_VALUE(obj.getString("hl_value"));
                huli.setHL_ITEM(obj.getString("hl_item"));
                huli.setVITAL_CODE(obj.getString("hlid"));
                huli.setMIN_VALUE(obj.getInt("min_value"));
                huli.setMAX_VALUE(obj.getInt("max_value"));
                huli.setALERTMSG(obj.getString("alertmsg"));
                huli.setUNITS(obj.optString("units"));//新增护理所需要的单位
                huli.setCLASS_CODE(obj.optString("class_code"));//新增护理所需要的class_code
                huli.WARD_CODE = obj.optString("ward_code");//新增护理所需要的WARD_CODE
                huli.NEW_CLASS_VITAL_CODE = obj.optString("new_class_vital_code");//新增护理所需要的vital_code，和huli.setVITAL_CODE(obj.getString("hlid"));不同
                data.add(huli);
            }
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 添加护理表单
     *
     * @param bid
     * @param vid
     * @param saveok 护理表单信息
     * @param cls    A：生命体征 ，B：出量 ，C：入量 ，D：其他 ，E：事故
     * @return
     * @return没访问到返回null，无数据返回空list
     */
    public static boolean submithulidata(String bid, String vid, String cls,
                                         String saveok, String uid) {
        System.out.println("submithulidata=" + "http://" + Statics.IP + ":"
                + Statics.Port + Statics.SubmitHuliURL + "api=" + Statics.API
                + "&bid=" + bid + "&vid=" + vid + "&cls=" + cls + "&saveok="
                + saveok + "&uid=" + uid);
        // String queryStringForGet = HttpUtil.queryStringForGet("http://"
        // + Statics.IP + ":" + Statics.Port + Statics.SubmitHuliURL
        // + "api=" + Statics.API + "&bid=" + bid + "&vid=" + vid
        // + "&cls=" + cls + "&saveok=" + saveok);
        String queryStringForPost = HttpUtil.queryStringForPost("http://"
                        + Statics.IP + ":" + Statics.Port + Statics.SubmitHuliURL,
                new String[]{"api", "bid", "vid", "cls", "saveok", "uid"},
                new String[]{Statics.API, bid, vid, cls, saveok, uid});
        System.out.println("submithulidata=" + queryStringForPost);
        if (queryStringForPost == null || queryStringForPost.equals("")) {
            return false;
        }
        try {
            JSONObject jsobj = new JSONObject(queryStringForPost);
            int state = jsobj.getInt("status");// 0为存入失败，1为存入成功
            if (state == 1) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 交接班列表
     *
     * @return
     * @return没访问到返回null，无数据返回空list BRID INT 病人ID ZYID INT 本次住院ID JIAOBAN_DATE
     * String 交班时间
     */
    public static List<JiaoJieBan> JiaoJieBanlist(String bid, String vid) {
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.JiaoJieBanListURL
                + "api=" + Statics.API + "&bid=" + bid + "&vid=" + vid);
        ArrayList<JiaoJieBan> data = new ArrayList<JiaoJieBan>();
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        try {
            JSONArray array = new JSONArray(queryStringForGet);
            for (int i = 0; i < array.length(); i++) {
                JiaoJieBan jiaojieban = new JiaoJieBan();
                JSONObject obj = array.getJSONObject(i);
                jiaojieban.setBRID(obj.getString("BRID"));
                jiaojieban.setZYID(obj.getString("ZYID"));
                jiaojieban.setJIAOBAN_DATE(obj.getString("JIAOBANDATE"));
                data.add(jiaojieban);
            }
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 交接班詳情
     *
     * @return
     * @return没访问到返回null，无数据返回空list Api String No 1qaz2wsx 通用api接口码 bid Int No
     * 病人ID vid Int No 本次住院ID jbdate String No
     * 交接班日期
     */
    public static JiaoJieBanDes JiaoJieBanDesDes(String bid, String vid,
                                                 String jbdate) {
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.JiaoJieBanDESCURL
                + "api=" + Statics.API + "&bid=" + bid + "&vid=" + vid
                + "&jbdate=" + jbdate);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        try {
			/*
			 * status INT 获取信息成功 jb_date String 交班时间 jb_memos String 交班内容
			 */
            JiaoJieBanDes jiaojieban = new JiaoJieBanDes();
            JSONObject obj = new JSONObject(queryStringForGet);
            jiaojieban.setINT(obj.getString("status"));
            jiaojieban.setJb_date(obj.getString("jb_date"));
            jiaojieban.setJb_memos(obj.getString("jb_memos"));
            return jiaojieban;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 样本列表
     *
     * @param bid
     * @param vid
     * @return
     */
    public static List<Yangben> getYangben(String bid, String vid) {
        System.out.println(("http://" + Statics.IP + ":" + Statics.Port
                + Statics.YangbenURL + "api=" + Statics.API + "&bid=" + bid
                + "&vid=" + vid));
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.YangbenURL + "api="
                + Statics.API + "&bid=" + bid + "&vid=" + vid);
        ArrayList<Yangben> data = new ArrayList<Yangben>();
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }

        JSONArray array;
        try {
            array = new JSONArray(queryStringForGet);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Yangben yangben = new Yangben();
                yangben.setBRID(object.getString("BRID"));
                yangben.setZYID(object.getString("ZYID"));
                yangben.setTEST_NO_SRC(object.getString("TEST_NO_SRC"));
                yangben.setYBNAME(object.getString("YBNAME"));
                yangben.setCJNAME(object.optString("CJNAME"));
                yangben.setCJTIME(object.optString("CJTIME"));
                yangben.setCAIOK(object.getInt("CAIOK"));
                yangben.setTESTNAME(object.optString("TESTNAME"));
                data.add(yangben);
            }
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 功能描述：搜索扫描到的版本号
     *
     * @param bid 病人id
     * @param vid 住院id
     * @param tno 扫描到的标本号
     * @return
     */
    public static List<Yangben> searchBiaoBen(String bid, String vid, String tno) {
        System.out.println(("http://" + Statics.IP + ":" + Statics.Port
                + Statics.SearchYangbenURL + "api=" + Statics.API + "&bid="
                + bid + "&vid=" + vid + "&tno=" + tno));
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.SearchYangbenURL
                + "api=" + Statics.API + "&bid=" + bid + "&vid=" + vid
                + "&tno=" + tno);
        ArrayList<Yangben> data = new ArrayList<Yangben>();
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        try {
            JSONObject jsobj = new JSONObject(queryStringForGet);
            int Status = jsobj.getInt("Status");
            if (Status == 1) {
                if (jsobj.getInt("Reload") == 1) {
                    Statics.patientdesc.setPatient_id(jsobj.getString("BRID"));
                    Statics.patientdesc.setZy_id(jsobj.getString("ZYID"));
                    Statics.patientdesc.setName(jsobj.getString("BRNAME"));
                    Statics.patientdesc.setChuanghao(jsobj.getString("CHUANG"));
                    Statics.patientdesc.setGender(jsobj.getString("SEX"));
                    Statics.patientdesc.setZhuyi(jsobj.getString("ZHUYI"));

                    Statics.patientdesc.setPatient_birth(jsobj
                            .getString("BIRTH"));

                    Statics.patientdesc.setTime(jsobj.getString("ZYDATE"));
                    Statics.patientdesc.setQuhao(jsobj.getString("BINGQU"));
                    Statics.patientdesc
                            .setHulidengji(jsobj.getString("HULIDJ"));
                    Statics.patientdesc
                            .setZHENDUAN(jsobj.getString("ZHENDUAN"));
                    Statics.patientdesc.setFEIBIE(jsobj.getString("FEIBIE"));
                    Statics.patientdesc.setFEIYUE(jsobj.getString("FEIYUE"));

                    // SimpleDateFormat sdf = new
                    // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    // // String str ="2013-12-03 14:42:41.0";
                    String str = jsobj.getString("BIRTH");
                    // String substring = str.substring(0, str.length() - 2);
                    // System.out.println(substring);
                    // Date date = sdf.parse(substring); // string转换为date
                    // Calendar cal = Calendar.getInstance(); // date to
                    // calendar
                    // cal.setTime(date); // 设置时间

                    SimpleDateFormat myformat = new SimpleDateFormat(
                            "yyyy-MM-dd");
                    Date date = new Date();
                    Date mydata = myformat.parse(str);
                    long day = (date.getTime() - mydata.getTime())
                            / (24 * 60 * 60 * 1000) + 1;
                    String year = new java.text.DecimalFormat("#")
                            .format(day / 365f);
                    Statics.patientdesc.setAge(year);
                }
                JSONArray array = jsobj.getJSONArray("biaobenlist");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    Yangben yangben = new Yangben();
                    yangben.setBRID(object.getString("BRID"));
                    yangben.setZYID(object.getString("ZYID"));
                    yangben.setTEST_NO_SRC(object.getString("TEST_NO_SRC"));
                    biaobenguanliActivity.TEST_NO_SRC = object.getString("TEST_NO_SRC");
                    yangben.setYBNAME(object.getString("YBNAME"));
                    yangben.setCJNAME(object.getString("CJNAME"));
                    yangben.setCJTIME(object.getString("CJTIME"));
                    yangben.setCAIOK(object.getInt("CAIOK"));
                    data.add(yangben);
                }
                return data;
            } else {
                biaobenguanliActivity.Failmsg = jsobj.getString("Failmsg");
                return null;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return data;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 样本采集触发方法
     *
     * @param bid  //
     * @param vid  //
     * @param tsno 护理表单信息
     * @param act  A：生命体征 ，B：出量 ，C：入量 ，D：其他 ，E：事故
     * @param uid  用户ID
     * @return 没访问到返回null，无数据返回空list
     */
    public static boolean yanbencaiji(String bid, String vid, String tsno,
                                      String act, String uid) {
        System.out.println("YangbenCaiJiURL=" + "http://" + Statics.IP + ":"
                + Statics.Port + Statics.YangbenCaiJiURL + "api=" + Statics.API
                + "&bid=" + bid + "&vid=" + vid + "&tsno=" + tsno + "&act="
                + act + "&uid=" + uid);
        String queryStringForPost = HttpUtil.queryStringForPost("http://"
                        + Statics.IP + ":" + Statics.Port + Statics.YangbenCaiJiURL,
                new String[]{"api", "bid", "vid", "tsno", "act", "uid"},
                new String[]{Statics.API, bid, vid, tsno, act, uid});
        System.out.println("submithulidata=" + queryStringForPost);
        if (queryStringForPost == null || queryStringForPost.equals("")) {
            return false;
        }
        try {
            JSONObject jsobj = new JSONObject(queryStringForPost);
            int state = jsobj.getInt("status");// 0为存入失败，1为存入成功

            if (state == 1) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 服务器apk版本检查
     *
     * @param ver //
     * @return
     */

    public static boolean getVersion(String ver) {
        String vercheck;
        boolean res = false;
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.VersionURL + "api="
                + Statics.API);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return res;
        }
        try {
            JSONObject object = new JSONObject(queryStringForGet);
            vercheck = object.getString("versionCode").trim();
            if (vercheck.compareTo(ver) > 0) {
                res = true;
                Statics.ApkVersion = vercheck;
            } else {
                res = false;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return res;
        }
        return res;
    }

    /**
     * 拼接jsonobj
     *
     * @param list //
     * @return
     */
    public static String gethulidescjson(List<HuLiDesc> list) {
        JSONArray array;
        array = new JSONArray();
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                JSONObject object = new JSONObject();
                HuLiDesc hulidesc = list.get(i);
				/*
				 * HL_ITEM = hl_item VITAL_CODE = hlid HL_VALUE = hl_value
				 * hl_type = hl_type
				 */
                try {
                    object.put("hlid", hulidesc.getVITAL_CODE());
                    object.put("hl_type", hulidesc.getHl_type());
                    object.put("hl_item", hulidesc.getHL_ITEM());
                    object.put("hl_value", hulidesc.getHL_VALUE());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                array.put(object);
            }
        }
        return array.toString();

    }

    /**
     * 入院评估
     *
     * @param bid
     * @param vid
     * @return
     */
    public static List<PingGu> getRYPinggu(String bid, String vid, String itemid) {
        String queryStringForPost = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.RYPingGuURL
                + "api=" + Statics.API + "&bid=" + bid + "&vid=" + vid
                + "&itemid=" + itemid);
        System.out.println("http://" + Statics.IP + ":" + Statics.Port
                + Statics.RYPingGuURL + "api=" + Statics.API + "&bid=" + bid
                + "&vid=" + vid + "&itemid=" + itemid);
        if (queryStringForPost == null || queryStringForPost.equals("")) {
            return null;
        }
        ArrayList<PingGu> data = new ArrayList<PingGu>();
        try {
            JSONArray array = new JSONArray(queryStringForPost);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                PingGu gu = new PingGu();
                int xiaji = object.optInt("xiaji");
                if (xiaji >= 1) {
                    xiaji = 1;
                }
                gu.setXiaji(xiaji);
                gu.setItemid(object.optString("itemid"));
                gu.setItemname(object.optString("itemname"));
                gu.setSelected(object.optInt("selected"));
                gu.setItemtype(object.optInt("itemtype"));
                gu.setItemtextvalue(object.optString("value"));
                gu.setExcp(object.optInt("excp"));
                data.add(gu);
            }
            Collections.reverse(data);
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 功能描述：保存评估表单(入院和每日都是此接口地址所以都是调用此方法)
     *
     * @param bid
     * @param vid
     * @param saveid
     * @param saveok
     * @return
     */
    public static boolean submitRYdata(String submitPingGuURL, String bid,
                                       String vid, String saveid, String saveok, String uid) {
        boolean res = false;
        String values = "";// 表单项值
        String postsaves = "";// 最终拼接值
        JSONArray jsonarr = new JSONArray();// 最终拼接值
        Set set = Statics.ruyuanpingguhashmap.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            JSONObject jsonobj = new JSONObject();
            Map.Entry mapentry = (Map.Entry) iterator.next();
            PingGu pingguform = new PingGu();
            pingguform = (PingGu) mapentry.getValue();
            try {
                jsonobj.put("itemid", mapentry.getKey());
                jsonobj.put("savetype", pingguform.getSelected());
                jsonobj.put("txt", pingguform.getItemtextvalue());
                jsonarr.put(jsonobj);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        postsaves = jsonarr.toString();

        String queryStringForGet = HttpUtil
                .queryStringForPost("http://" + Statics.IP + ":" + Statics.Port
                        + submitPingGuURL, new String[]{"api", "bid",
                        "vid", "saveid", "saveok", "uid"}, new String[]{
                        Statics.API, bid, vid, saveid, postsaves, uid});

        System.out.println("http://" + Statics.IP + ":" + Statics.Port
                + submitPingGuURL + "api=" + Statics.API + "&bid=" + bid
                + "&vid=" + vid + "&saveid=" + saveid + "&saveok=" + postsaves);
        System.out.println("submitRYdata=" + queryStringForGet);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return false;
        }
        try {
            JSONObject jsobj = new JSONObject(queryStringForGet);
            int state = jsobj.getInt("status");// 0为存入失败，1为存入成功
            if (state == 1) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 功能描述：单独提交保存评估表单(入院和每日都是此接口地址所以都是调用此方法)
     *
     * @param bid
     * @param vid
     * @param saveid
     * @param saveok
     * @return
     */
    public static boolean singlesubmit(String submitPingGuURL, String bid,
                                       String vid, String saveid, String saveok, String uid) {
        String postsaves = saveok;// 最终拼接值

        System.out.println("http://" + Statics.IP + ":" + Statics.Port
                + submitPingGuURL + "api=" + Statics.API + "&bid="
                + bid + "&vid=" + vid + "&saveid=" + saveid + "&saveok="
                + postsaves);

        String queryStringForGet = HttpUtil
                .queryStringForPost("http://" + Statics.IP + ":" + Statics.Port
                        + submitPingGuURL, new String[]{"api", "bid", "vid",
                        "saveid", "saveok", "uid"}, new String[]{
                        Statics.API, bid, vid, saveid, postsaves, uid});
        System.out.println("submitRYdata=" + queryStringForGet);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return false;
        }
        try {
            JSONObject jsobj = new JSONObject(queryStringForGet);
            int state = jsobj.getInt("status");// 0为存入失败，1为存入成功
            if (state == 1) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 功能描述：获取每日评估日期列表
     *
     * @param bid
     * @param zid
     * @return
     */
    public static List<Hulilist> getPingGuList(String bid, String zid) {
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.MRPingGuListURL
                + "api=" + Statics.API + "&bid=" + bid + "&vid=" + zid);
        System.out.println("http://" + Statics.IP + ":" + Statics.Port
                + Statics.MRPingGuListURL + "api=" + Statics.API + "&bid="
                + bid + "&vid=" + zid);
        System.out.println("getPingGuList=" + queryStringForGet);
        ArrayList<Hulilist> data = new ArrayList<Hulilist>();
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return null;
        }
        try {
            JSONArray array = new JSONArray(queryStringForGet);
            for (int i = 0; i < array.length(); i++) {
                Hulilist huli = new Hulilist();
                JSONObject obj = array.getJSONObject(i);
                huli.setRECORDING_DATE(obj.getString("MRPG_DATE"));
                data.add(huli);
            }
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 每日评估
     *
     * @param bid //
     * @param vid
     * @return
     */
    public static List<PingGu> getMRPinggu(String bid, String vid,
                                           String itemid, String date) {
        String datestr = date;
        datestr = DateUtil.strToStr(datestr, "yyyy-MM-dd");
        System.out.println("getMRPinggu=http://" + Statics.IP + ":"
                + Statics.Port + Statics.MRPingGuURL + "api=" + Statics.API
                + "&bid=" + bid + "&vid=" + vid + "&pgdate=" + datestr
                + "&itemid=" + itemid);
        String queryStringForPost = null;
        try {
            queryStringForPost = HttpUtil.queryStringForGet("http://"
                    + Statics.IP + ":" + Statics.Port + Statics.MRPingGuURL
                    + "api=" + Statics.API + "&bid=" + bid + "&vid=" + vid
                    + "&pgdate=" + datestr + "&itemid=" + itemid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("getMRPinggu=" + queryStringForPost);
        if (queryStringForPost == null || queryStringForPost.equals("")) {
            return null;
        }
        ArrayList<PingGu> data = new ArrayList<PingGu>();
        try {
            JSONArray array = new JSONArray(queryStringForPost);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                if (!object.optString("item_name").contains("wnl")) {
                    PingGu gu = new PingGu();
                    int xiaji = object.optInt("itemn");
                    if (xiaji >= 1) {
                        xiaji = 1;
                    }
                    gu.setXiaji(xiaji);
                    gu.setItemid(object.optString("item_id"));
                    gu.setItemname(object.optString("item_name"));
                    gu.setSelected(object.optInt("selected"));
                    // if (StrTool.isnull(object.getString("itemtype"))) {
                    // gu.setItemtype(0);
                    // }else {
                    int itemType = object.optInt("flag");
                    if (itemType == 13) {
                        itemType = 0;
                    }
                    gu.setItemtype(itemType);
                    // }
                    data.add(gu);
                }
            }
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 教育选项列表
     *
     * @param bid
     * @param vid
     * @param itemid
     * @return
     */
    public static List<PingGu> getJiaoYuList(String bid, String vid,
                                             String itemid) {
        String queryStringForPost = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.JiaoYuListURL
                + "api=" + Statics.API + "&bid=" + bid + "&vid=" + vid
                + "&itemid=" + itemid);
        System.out.println("http://"
                + Statics.IP + ":" + Statics.Port + Statics.JiaoYuListURL
                + "api=" + Statics.API + "&bid=" + bid + "&vid=" + vid
                + "&itemid=" + itemid);
        if (queryStringForPost == null || queryStringForPost.equals("")) {
            return null;
        }
        ArrayList<PingGu> data = new ArrayList<PingGu>();
        try {
            JSONArray array = new JSONArray(queryStringForPost);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.optJSONObject(i);
                PingGu gu = new PingGu();
                int xiaji = object.optInt("itemn");
                if (xiaji > 0) {
                    xiaji = 1;
                }
                gu.setXiaji(xiaji);
                gu.setItemid(object.optString("item_id"));
                gu.setItemname(object.optString("item_name"));
                gu.setSelected(object.optInt("selected"));
                data.add(gu);
            }
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 功能描述：获取教育表单
     *
     * @param bid
     * @param vid
     * @param eduid 教育项id
     * @return
     */
    public static List<JiaoYuForm> getJiaoYuFormList(String bid, String vid,
                                                     String eduid) {
        String queryStringForPost = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.JiaoYuFormURL
                + "api=" + Statics.API + "&bid=" + bid + "&vid=" + vid
                + "&eduid=" + eduid);
        System.out.println("http://" + Statics.IP + ":" + Statics.Port
                + Statics.JiaoYuFormURL + "api=" + Statics.API + "&bid=" + bid
                + "&vid=" + vid + "&eduid=" + eduid);
        if (queryStringForPost == null || queryStringForPost.equals("")) {
            return null;
        }
        ArrayList<JiaoYuForm> data = new ArrayList<JiaoYuForm>();
        try {
            System.out.println("queryStringForPost=" + queryStringForPost);
            JSONArray array = new JSONArray(queryStringForPost);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                JiaoYuForm jiaoyuform = new JiaoYuForm();
                jiaoyuform.setOname(object.getString("oname"));
                jiaoyuform.setOtype(object.getString("otype"));

                // JSONArray array11 = object.getJSONArray("ovalues");
                List<String[]> object3 = new ArrayList<String[]>();

                if (object.getString("otype").equals("1")) {
                    JSONArray array11 = object.getJSONArray("ovalues");
                    for (int j = 0; j < array11.length(); j++) {
                        JSONObject jsobj11 = array11.getJSONObject(j);
                        String itemname = jsobj11.getString("itemname");
                        String selected = jsobj11.getString("selected");
                        object3.add(new String[]{itemname, selected});
                    }
                    jiaoyuform.setOvalues(object3);
                } else {
                    jiaoyuform.setTexts(object.getString("ovalues"));
                    System.out.println("object.getStringovalues" + object.getString("ovalues"));
                }
                data.add(jiaoyuform);
            }
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 功能描述：提交教育表单
     *
     * @param bid
     * @param vid
     * @param eduid 教育项id
     * @return
     */
    public static boolean submitJiaoYuFormList(String bid, String vid,
                                               String eduid, String uid/* , HashMap<String , JiaoYuForm> hashmap */) {
        boolean res = false;
        String values = "";// 表单项值
        String postsaves = "";// 最终拼接值
        JSONArray jsonarr = new JSONArray();// 最终拼接值
        Set set = Statics.jiaoyuitemidhashmap.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            JSONObject jsonobj = new JSONObject();
            Map.Entry mapentry = (Map.Entry) iterator.next();
            JiaoYuForm jiaoyuform = new JiaoYuForm();
            jiaoyuform = (JiaoYuForm) mapentry.getValue();
            // System.out.println(mapentry.getKey()+"" +
            // jiaoyuform.getOvalues()[0] + "/");
            try {
                jsonobj.put("oname", mapentry.getKey());
                if (jiaoyuform.getOtype().equals("1")) {

                    for (int i = 0; i < jiaoyuform.getOvalues().size(); i++) {
                        if (jiaoyuform.getOvalues().get(i)[1].equals("1")) {
                            values = jiaoyuform.getOvalues().get(i)[0];
                        }
                    }
                    jsonobj.put("ovalue", values);
                } else {
                    jsonobj.put("ovalue", jiaoyuform.getTexts());
                }
                // postsaves += jsonobj.toString();
                jsonarr.put(jsonobj);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        postsaves = jsonarr.toString();
        String queryStringForPost = "";
        try {
            System.out.println("postsaves" + postsaves);
            queryStringForPost = HttpUtil.queryStringForPost("http://"
                    + Statics.IP + ":" + Statics.Port
                    + Statics.JiaoYuFormPostURL, new String[]{"api", "bid",
                    "vid", "eduid", "postsave", "uid"}, new String[]{
                    Statics.API, bid, vid, eduid, postsaves, uid});
            System.out
                    .println(("http://" + Statics.IP + ":" + Statics.Port
                            + Statics.JiaoYuFormPostURL + "api" + Statics.API
                            + "bid" + bid + "vid" + vid + "eduid" + eduid
                            + "postsave" + postsaves));
            System.out.println("返回数据" + queryStringForPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
		/*
		 * String queryStringForPost = HttpUtil.queryStringForGet("http://" +
		 * Statics.IP + ":" + Statics.Port + Statics.JiaoYuFormPostURL + "api="
		 * + Statics.API + "&bid=" + bid + "&vid=" + vid + "&eduid=" + eduid +
		 * "&postsave=" + postsaves);
		 */
        if (queryStringForPost == null || queryStringForPost.equals("")) {
            return res;
        }
        try {
            JSONObject object = new JSONObject(queryStringForPost);
            int i = 0;
            i = object.getInt("status");
            if (i == 1)
                res = true;
            return res;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return res;
        }
    }

    /**
     * 功能描述：修改密码（护士账号信息 修改密码接口）
     *
     * @param uid    //
     * @param modpwd //
     * @return
     */
    public static boolean ChangePassword(String uid, String modpwd) {
        System.out.println("ChangePassword=" + "http://" + Statics.IP + ":"
                + Statics.Port + Statics.XiuGaiMiMaURL + "api=" + Statics.API
                + "&uid=" + uid + "&modpwd=" + modpwd);
        String queryStringForPost = HttpUtil.queryStringForPost("http://"
                        + Statics.IP + ":" + Statics.Port + Statics.XiuGaiMiMaURL,
                new String[]{"api", "uid", "modpwd"}, new String[]{
                        Statics.API, uid, modpwd});
		/*
		 * String queryStringForGet = HttpUtil.queryStringForGet("http://" +
		 * Statics.IP + ":" + Statics.Port + Statics.XiuGaiMiMaURL + "api=" +
		 * Statics.API + "&uid=" + uid + "&modpwd=" + modpwd);
		 */
        System.out.println("submitRYdata=" + queryStringForPost);
        if (queryStringForPost == null || queryStringForPost.equals("")) {
            return false;
        }
        try {
            JSONObject jsobj = new JSONObject(queryStringForPost);
            int state = jsobj.getInt("status");// 0为修改失败，1为修改成功
            if (state == 1) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 功能描述：获取版权信息（接口）
     *
     * @return
     */
    public static String getBanQuan() {
        // http://1.93.13.73:8080/verinfo.do?api=1qaz2wsx
        String verinfo = "";
        String queryStringForGet = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.BanQuanURL + "api="
                + Statics.API);
        System.out.println("submitRYdata=" + queryStringForGet);
        if (queryStringForGet == null || queryStringForGet.equals("")) {
            return "";
        }
        try {
            JSONObject jsobj = new JSONObject(queryStringForGet);
            int state = jsobj.getInt("status");// 1为成功、0为失败
            verinfo = jsobj.getString("verinfo");// 版权信息文本
            if (state == 1) {
                return verinfo;
            } else {
                return "";
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 功能描述：获取搜索数据
     *
     * @return
     */
    public static List<SearchForm> getSearchList() {
        String queryStringForPost = HttpUtil.queryStringForGet("http://"
                + Statics.IP + ":" + Statics.Port + Statics.SearchTiaojianURL
                + "api=" + Statics.API);
        if (queryStringForPost == null || queryStringForPost.equals("")) {
            return null;
        }
        ArrayList<SearchForm> data = new ArrayList<SearchForm>();
        try {
            JSONArray array = new JSONArray(queryStringForPost);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                SearchForm jiaoyuform = new SearchForm();
                jiaoyuform.setSrhid(object.getString("srhid"));
                jiaoyuform.setOname(object.getString("oname"));
                jiaoyuform.setOtype(object.getString("otype"));
                String[] object3 = null;
                if (object.getString("otype").equals("1")) {
                    JSONObject array11 = object.getJSONObject("ovalues");
                    // object3 =
                    // StrTool.replacestr(array11.getString("ovalue")).split(",");
                    String doustr = StrTool.replacestr(array11
                            .getString("ovalue"));
                    if (!"".equals(doustr)) {
                        object3 = doustr.split(",");
                    }
                }
                jiaoyuform.setOvalues(object3);
                data.add(jiaoyuform);
            }
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 功能描述：高级搜索病人列表
     *
     * @param uid
     * @param pageNo
     * @return
     */
    public static List<Patient> Searchlist(String uid, String pageNo, String key) {

		/*
		 * String forGet = HttpUtil.queryStringForPost("http://" + Statics.IP +
		 * ":" + Statics.Port + Statics.PatientListURL + "api=" + Statics.API +
		 * "&uid=" + uid + "&curpage=" + pageNo+ "&key=" + key);
		 */
        String values = "";// 表单项值
        String postsaves = "";// 最终拼接值
        System.out.println("+++++++" + Statics.searchitemidhashmap.toString());
        Set set = Statics.searchitemidhashmap.entrySet();
        Iterator iterator = set.iterator();
        JSONArray jsonarry = new JSONArray();
        while (iterator.hasNext()) {
            JSONObject jsonobj = new JSONObject();
            Map.Entry mapentry = (Map.Entry) iterator.next();
            SearchForm searchform = new SearchForm();
            // System.out.println(mapentry.getKey()+"" +
            // jiaoyuform.getOvalues()[0] + "/");
            try {
                searchform = (SearchForm) mapentry.getValue();
                jsonobj.put("srhid", mapentry.getKey());
                for (int i = 0; i < searchform.getOvalues().length; i++) {
                    values = searchform.getOvalues()[i];
                }
                jsonobj.put("srhvalue", values);
                // postsaves += jsonobj.toString();
                jsonarry.put(jsonobj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        postsaves = jsonarry.toString();
        System.out.println("postsaves=" + postsaves);
        String queryStringForPost = "";
        if ("".equals(postsaves)) {
			/*
			 * queryStringForPost = HttpUtil.queryStringForPost("http://" +
			 * Statics.IP + ":" + Statics.Port + Statics.PatientListURL, new
			 * String[] { "api", "uid", "curpage","key" }, new String[] {
			 * Statics.API, uid, pageNo,key});
			 */
            queryStringForPost = HttpUtil.queryStringForGet("http://"
                    + Statics.IP + ":" + Statics.Port + Statics.PatientListURL
                    + "api=" + Statics.API + "&uid=" + uid + "&curpage="
                    + pageNo + "&key=" + key);
            System.out.println("病人搜索http://" + Statics.IP + ":" + Statics.Port
                    + Statics.PatientListURL + "api=" + Statics.API + "&uid="
                    + uid + "&curpage=" + pageNo + "&key=" + key);
        } else {
            queryStringForPost = HttpUtil
                    .queryStringForPost("http://" + Statics.IP + ":"
                                    + Statics.Port + Statics.PatientListURL,
                            new String[]{"api", "uid", "curpage", "key",
                                    "Searchok"}, new String[]{Statics.API,
                                    uid, pageNo, key, postsaves});
            System.out.println("病人高级搜索http://" + Statics.IP + ":"
                    + Statics.Port + Statics.PatientListURL + "api="
                    + Statics.API + "&uid=" + uid + "&curpage=" + pageNo
                    + "&key=" + key + "&Searchok=" + postsaves);
        }
        System.out.println("高级搜索=" + queryStringForPost);
        if (queryStringForPost == null || queryStringForPost.equals("")) {
            return null;
        }
        try {

            JSONArray array = new JSONArray(queryStringForPost);
            List<Patient> list = new ArrayList<Patient>();
            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    Patient patient = new Patient();
                    patient.setPatient_id(obj.getString("BRID"));
                    patient.setZy_id(obj.getString("ZYID"));
                    patient.setName(obj.getString("BRNAME"));
                    patient.setChuanghao(obj.getString("CHUANG"));
                    patient.setGender(obj.getString("SEX"));
                    patient.setZhuyi(obj.getString("ZHUYI"));
                    patient.setPatient_birth(obj.getString("BIRTH"));
                    patient.setTime(obj.getString("ZYDATE"));
                    patient.setQuhao(obj.getString("BINGQU"));
                    patient.setHulidengji(obj.getString("HULIDJ"));

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
					/*
					 * String date = obj.getString("createTime"); if (null !=
					 * date && !"".equals(date) && !"null".equals(date)) {
					 * Patient.setCreateTime(DateUtil.strToStr(date,
					 * "yy-MM-dd HH:mm")); } else { Patient.setCreateTime(""); }
					 */
                    list.add(patient);
                }
                return list;
            } else {
                return null;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}
