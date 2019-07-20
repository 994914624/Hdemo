package cn.test.hdemo.utils;

import android.content.Context;
import android.util.Log;

import com.kongzue.baseokhttp.HttpRequest;
import com.kongzue.baseokhttp.listener.ResponseListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.json.JSONException;
import org.json.JSONObject;

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


    public static String getH_Article( String start, String count){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("scene_id","article_feed");
            jsonObject.put("log_id","111");
            jsonObject.put("distinct_id",SensorsDataAPI.sharedInstance().getAnonymousId());
            jsonObject.put("limit",count);
            jsonObject.put("exp_id","base");
            return HttpPOST.submitPostData(API.H_ARTICLE,jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

   // {"scene_id":"detail","log_id":"111","distinct_id":"test1","limit":"20","exp_id":"base","item_id":"6243991","item_type":"article"}
    public static String getH_Article_detail( String start, String count,String itemId){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("scene_id","detail");
            jsonObject.put("log_id","111");
            jsonObject.put("distinct_id","test1");
            jsonObject.put("limit",count);
            jsonObject.put("item_type","article");
            jsonObject.put("item_id",itemId);
            jsonObject.put("exp_id","base");
            return HttpPOST.submitPostData(API.H_ARTICLE_DETAIL,jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * {
     * "scene_id":"article_relevant",
     * "log_id":"111",
     * "distinct_id":"QQDFEFFEFDW",
     * "limit":"3",
     * "exp_id":"base",
     * "item_id":"6550620",
     * "item_type":"article"
     * }
     */
    public static String getH_Article_relevant( String start, String count,String itemId){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("scene_id","article_relevant");
            jsonObject.put("log_id","111");
            jsonObject.put("distinct_id",SensorsDataAPI.sharedInstance().getAnonymousId());
            jsonObject.put("limit",count);
            jsonObject.put("item_type","article");
            jsonObject.put("item_id",itemId);
            jsonObject.put("exp_id","base");

            Log.d("qqqq:",jsonObject.toString());
            return HttpPOST.submitPostData(API.H_ARTICLE_RELEVENT,jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //-------------------  video -------------------------
    //{"scene_id":"video_feed","log_id":"111","distinct_id":"test1","limit":"3","exp_id":"base"}
    public static String getH_Video( String start, String count){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("scene_id","video_feed");
            jsonObject.put("log_id","111");
            jsonObject.put("distinct_id",SensorsDataAPI.sharedInstance().getAnonymousId());
            jsonObject.put("limit",count);
            jsonObject.put("exp_id","base");
            return HttpPOST.submitPostData(API.H_VIDEO,jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * {
     *     "scene_id":"video_relevant",
     *     "log_id":"111",
     *     "distinct_id":"test1",
     *     "limit":"3",
     *     "exp_id":"base",
     *     "item_id":"120586",
     *     "item_type":"video"
     * }
     */

    public static String getH_Video_relevant( String start, String count,String itemId){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("scene_id","video_relevant");
            jsonObject.put("log_id","111");
            jsonObject.put("distinct_id",SensorsDataAPI.sharedInstance().getAnonymousId());
            jsonObject.put("limit",count);
            jsonObject.put("item_type","video");
            jsonObject.put("item_id",itemId);
            jsonObject.put("exp_id","base");

            Log.d("qqqq:",jsonObject.toString());
            return HttpPOST.submitPostData(API.H_ARTICLE_RELEVENT,jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *{
     *     "scene_id":"feed",
     *     "distinct_id":"test",
     *     "limit":"2"
     * }
     * ---------------------------------------------new----------------------------------------------------
     */

    public static String getNFeed( String start, String count){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("scene_id","feed");
            jsonObject.put("distinct_id",SensorsDataAPI.sharedInstance().getAnonymousId());
            jsonObject.put("limit",count);

            Log.d("qqqq:",jsonObject.toString());
            return HttpPOST.submitPostData(API.N_FEED,jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * {"scene_id":"reset_user","log_id":"1","distinct_id":"test","limit":"1","exp_id":"base"}
     *
     */
    public static String resetUser(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("scene_id","reset_user");
            jsonObject.put("distinct_id",SensorsDataAPI.sharedInstance().getAnonymousId());
            jsonObject.put("log_id","1");
            jsonObject.put("limit","1");
            jsonObject.put("exp_id","base");
            Log.d("qqqq:",jsonObject.toString());
            return HttpPOST.submitPostData(API.N_RESET_USER,jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
