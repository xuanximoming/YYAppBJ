package com.yy.keyapp.config;


import com.yy.cookies.Statics;

/**
 * Created by Ukey on 2016/1/18.
 * Email:yeshentianyue@sina.com
 * 请求接口
 */
public class NAPI {

    public static String patientList(String uid, int pageNo){
        System.out.println("http://" + Statics.IP + ":"
                + Statics.Port + Statics.PatientListURL + "api=" + Statics.API
                + "&uid=" + uid + "&curpage=" + pageNo);
        return "http://" + Statics.IP + ":"
                + Statics.Port + Statics.PatientListURL + "api=" + Statics.API
                + "&uid=" + uid + "&curpage=" + pageNo;
    }

}
