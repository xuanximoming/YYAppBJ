package com.yy.until;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.yy.cookies.Statics;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

public class HttpUtil {

    private static final String TAG = "HttpUtil";
    public static String urlResult = "";

    public static HttpGet getHttpGet(String paramString) {
        return new HttpGet(paramString);
    }

    public static HttpPost getHttpPost(String paramString) {
        return new HttpPost(paramString);
    }

    public static HttpResponse getHttpResponse(HttpGet paramHttpGet)
            throws ClientProtocolException, IOException {

        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters,
                Statics.internettime * 1000);// 联网超时
        HttpClient httpClient = new DefaultHttpClient(httpParameters);
        // return new DefaultHttpClient().execute(paramHttpGet);
        HttpResponse execute = httpClient.execute(paramHttpGet);
        return execute;
    }

    public static HttpResponse getHttpResponse(HttpPost paramHttpPost)
            throws ClientProtocolException, IOException {
        return new DefaultHttpClient().execute(paramHttpPost);
    }

    /**
     * @param paramString 访问网络的地址及参数
     * @return 返回字符串
     * @category 使用get方式访问网络
     */
    public static String queryStringForGet(String paramString) {
        HttpGet localHttpGet = getHttpGet(paramString);
        try {

            HttpResponse localHttpResponse = getHttpResponse(localHttpGet);

            System.out.println("网络请求返回码"
                    + localHttpResponse.getStatusLine().getStatusCode());
            if (localHttpResponse.getStatusLine().getStatusCode() != 200) {
                System.out.println("返回码不是200而是"
                        + localHttpResponse.getStatusLine().getStatusCode());
                Statics.internetcode = 2;
                return null;
            }
            Statics.internetcode = -1;
            String str = EntityUtils.toString(localHttpResponse.getEntity(),
                    "UTF-8");
//			System.out.println("网络下载str===========" + str);
            return str;
        } catch (ClientProtocolException localClientProtocolException) {
            localClientProtocolException.printStackTrace();
            Statics.internetcode = 2;
            Log.e("HttpUtil", "result1 = " + "网络连接异常");
            return null;
        } catch (ConnectTimeoutException e) {
            System.out.println("网络连接超时");
            Statics.internetcode = 1;
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            System.out.println("日期转换出错");
            // TODO Auto-generated catch block
            Statics.internetcode = 2;
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Statics.internetcode = 2;
            System.out.println("IO异常");
            e.printStackTrace();
            return null;
        }
        // catch (Exception e) {
        // // TODO Auto-generated catch block
        // System.out.println("其它异常");
        // e.printStackTrace();
        // Statics.internetcode = 2;
        // return null;
        // }
        // catch (HttpHostConnectException e){
        //
        // }
    }

    /**
     * 通过post方式访问网络
     *
     * @param paramString 需要访问的接口地址
     * @param parmstring1 接口所需要的key值的数组
     * @param parmstring2 接口需要key值相对应的value值的数组
     * @return
     */
    public static String queryStringForPost(String paramString,
                                            String[] parmstring1, String[] parmstring2) {
        HttpPost localHttpPost = getHttpPost(paramString);
        ArrayList<BasicNameValuePair> localArrayList = new ArrayList<BasicNameValuePair>();
        try {
            for (int i = 0; i < parmstring1.length; i++) {
                System.out.println("循环次数====" + i);
                localArrayList.add(new BasicNameValuePair(parmstring1[i],
                        parmstring2[i]));
                System.out.println("parmstring1[i]===" + parmstring1[i]
                        + "-----parmstring2[i]==" + parmstring2[i]);
            }
            localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList,
                    "UTF-8"));
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    Statics.internettime * 1000);// 联结超时
            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpResponse localHttpResponse = httpClient.execute(localHttpPost);
            int j = localHttpResponse.getStatusLine().getStatusCode();
            System.out.println("返回码：=" + j);
            Object localObject = null;
            if (j == 200) {
                String str = EntityUtils
                        .toString(localHttpResponse.getEntity());
                localObject = str;
            }
            return (String) localObject;
        } catch (ClientProtocolException localClientProtocolException) {
            localClientProtocolException.printStackTrace();
            Log.e("HttpUtil", "result1 = " + "网络连接异常");
            return null;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
            Log.e("HttpUtil", "result2 = " + "网络连接异常");
            return null;
        }
    }

    /**
     * 网络是否连接
     *
     * @param context 当前界面的值
     * @return boolean 返回是否有网络连接
     */
    public static boolean checkNet(Context context) {// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

}
