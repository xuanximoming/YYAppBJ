package com.yy.cookies;

import android.widget.TextView;

import com.yy.entity.Patient;
import com.yy.entity.SearchForm;
import com.yy.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ----------------->已完成
 * =================>测试数据，sql语句待定
 */
public class Statics {
    public static User user = new User();
    public static int internettime = 10;
    public static long timeout = 100;

    public static String UNAME = null;
    public static String IP = "101.200.131.64";
    public static String Port = "80";
    public static String API = "1qaz2wsx";
    public static String LINKURL = "/link.do?";// 检测服务器是否连接------------------
    public static String LoginURL = "/login.do?";// 登录--------------------
    public static String PatientListURL = "/bingrenlist.do?";// 病人列表-----------------
    public static String PatientDescURL = "/bingren.do?";// 病人详情--------------------
    public static String NEWMessageURL = "/msglistening.do?";// 推送信息------------
    public static String MessageListURL = "/messages.do?";// 信息列表------------------
    public static String MessageDescURL = "/msginfos.do?";// 信息详情--------------------
    public static String MessageDoURL = "/msginfook.do?";// 任务处理--------------------
    public static String YizhuURL = "/yizhu.do?";// 医嘱----------------------
    public static String Yizhu_zxdURL = "/yz_zhixingdan.do?";// 医嘱执行单--------------------
    public static String Yizhu_ljzxURL = "/Yzzxd_dook.do?";// 医嘱执行单立即执行------------------
    public static String JianChaURL = "/jiancha.do?";// 检查-----------------------
    public static String JianYanURL = "/jianyan.do?";// 检验------------------------
    public static String JianYanresURL = "/jianyan_result.do?";// 检验-----------------
    public static String ShouShuURL = "/shoushu.do?";// 手术--------------------
    public static String HuliURL = "/huli_list.do?";// 护理列表-----------------
    public static String HulilishiURL = "/huli_orderok.do?";// 护理历史-------------
    public static String HulilitypeURL = "/huli_order.do?";// 护理类别 ----------
    public static String SubmitHuliURL = "/huli_save.do";// 提交护理表单-----------------
    public static String YangbenURL = "/biaoben.do?";// 样本管理------------------
    public static String SearchYangbenURL = "/biaobenscan.do?";// 搜索样本
    public static String YangbenCaiJiURL = "/biaobencaiji.do?";// 样本采集触发
    public static String JiaoJieBanListURL = "/jiaojielist.do?";// 交接班时间列表--------------------
    public static String JiaoJieBanDESCURL = "/jiaojieban.do?";// 交接班详情-------------------------
    public static String VersionURL = "/checkversion.do?";// 服务器apk版本检查
    public static String RYPingGuURL = "/new_pglist.do?";// 入院评估-----------------
    public static String SubmitPingGuURL = "/pgsave.do?";// 保存入院评估-----------------
    public static String SubmitMRPingGuURL = "/mrpgsave.do?";// 保存每日评估---------------
    public static String MRPingGuListURL = "/mrpg_list.do?";// 每日评估时间列表-----------------
    public static String MRPingGuURL = "/new_mrpglist.do?";// 每日评估-------------------
    public static String JiaoYuListURL = "/new_edulist.do?";// 教育列表---------------
    public static String JiaoYuFormURL = "/eduorder.do?";// 教育表单------------------
    public static String JiaoYuFormPostURL = "/eduorderok.do?";// 教育表单提交---------------
    public static String XiuGaiMiMaURL = "/modpwd.do?";// 修改密码
    public static String BanQuanURL = "/verinfo.do?";// 版权信息------------------------------------
    public static String SearchTiaojianURL = "/br_search.do?";// 高级条件搜索
    public static String SearURL = "/bingrenlist.do?";// 搜索病人列表
    public static String ApkVersion = "";// apk服务器版本号
    public static String ApkURL = "/app/yidonghuli";// apk服务器地址
    public static Patient patientdesc = null;
    public static HashMap jiaoyuitemidhashmap = new HashMap();// 记录教育列表选择的项目id
    public static HashMap<String, SearchForm> searchitemidhashmap = new HashMap<String, SearchForm>();// 高级搜索列表选择的项目id
    public static HashMap ruyuanpingguhashmap = new HashMap();// 入院评估的选择项
    public static int gaojisearch = 0;// 是否为病人列表高级搜索 操作：1是、0否 默认0

    public static List<Patient> searchpatientlist = null;

    public static int ADDAVTIVITY;
    public static final int RYpingguActivity = 1;
    public static final int AddJiaoJieActivity = 2;
    public static final int AddjiaoyvActivity = 4;
    public static final int AddmrpingguActivity = 5;

    // 用于记录检查提示是否显示
    public static List<TextView> JianChaList = new ArrayList<TextView>();
    // 用于记录医嘱提示是否显示
    public static List<TextView> YiZhuList = new ArrayList<TextView>();

    public static String[] internetstate = {"网络连接失败，请检查设置", "网络连接超时",
            "服务器连接失败，请检查设置"};
    public static int internetcode = 1;

    //扫描二维码得到的字符串
    public static String Qcvalue = null;

}
