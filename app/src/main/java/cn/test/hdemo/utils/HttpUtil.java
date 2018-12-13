package cn.test.hdemo.utils;

import android.content.Context;
import android.util.Log;

import com.kongzue.baseokhttp.HttpRequest;
import com.kongzue.baseokhttp.listener.ResponseListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import cn.test.hdemo.api.API;
import cn.test.hdemo.entity.AVEntity;

/**
 * Created by yzk on 2018/12/11
 */

public class HttpUtil {
    private static String TAG = "HttpUtil";

    public static void getAV(final Context context, String start, String count,ResponseListener responseListener){
       HttpRequest.GET(context, API.AV_LIST, new Parameter().add("start",start).add("count",count),responseListener);
    }

    public static void getTouTiaoNews(final Context context, String start, String count,ResponseListener responseListener){
        HttpRequest.DEBUGMODE = true;
        HttpRequest.GET(context, "https://www.baidu.com/", new Parameter(),responseListener);
    }
}
