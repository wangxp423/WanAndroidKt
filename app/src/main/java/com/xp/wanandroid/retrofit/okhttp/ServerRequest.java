package com.xp.wanandroid.retrofit.okhttp;

import com.xp.wanandroid.util.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ServerRequest {
    public static int REQUEST_LOGIN_TAG = 10001;
    private OkhttpRequest mClient;
    private static ServerRequest instance;

    private ServerRequest() {
        mClient = new OkhttpRequest();
    }

    public static synchronized ServerRequest getInstance() {
        if (instance == null) {
            instance = new ServerRequest();
        }
        return instance;
    }


    /**
     * get请求 拼接参数字符串
     */
    public static String creatGetUrl(String url, HashMap<String, String> params) {
        if (null == params)
            params = new HashMap<String, String>();
        StringBuilder mUrl = new StringBuilder();
        mUrl.append(url);
        if (params != null && !params.isEmpty()) {
            boolean firstParam = true;
            for (Map.Entry<String, String> e : params.entrySet()) {
                if (firstParam) {
                    firstParam = false;
                    mUrl.append("?");
                } else {
                    mUrl.append("&");
                }

                mUrl.append(e.getKey()).append("=").append(e.getValue());
            }
        }
        return mUrl.toString();

    }

    /**
     * 拼接post param 参数 将hashMap转为字符串
     */
    public static String setParams(HashMap<String, String> params) {
        JSONObject jsonObject = new JSONObject();
        if (null != params) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    jsonObject.putOpt(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject.toString();
    }

    //get 生成url
    private String setParams(String url, String key, HashMap<String, String> params) {
        StringBuilder builder = new StringBuilder();
        return builder.append(url).append("?").append(key).append("=").append(setParams(params)).toString();
    }

    //post 请求body参数
    private HashMap<String, String> setPostBody(String params) {
        HashMap<String, String> postParams = new HashMap<String, String>();
        postParams.put("param", params);
        return postParams;
    }

    public void login(String username, String pwdMD5,
                      OkhttpRequest.HttpCallback httpCallback) {
        String url = Constant.REQUEST_BASE_URL + "/user/login";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", pwdMD5);
        mClient.okHttpPost(REQUEST_LOGIN_TAG, url, params, httpCallback);
    }
}
