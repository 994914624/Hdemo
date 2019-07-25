package cn.test.hdemo.utils;

import android.util.Log;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import org.json.JSONObject;

import cn.test.hdemo.api.API;
import cn.test.hdemo.custom.CustomObserver;
import cn.test.hdemo.custom.IDataSuccess;
import cn.test.hdemo.entity.AbstractDataBean;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yzk on 2019/7/25
 */

public class RxUtil {
    private static final String TAG = "RxUtil";

    /**
     * 获取 Feed
     */
    public static void getFeedData(final String start, final String count, Observer<String> observer) {
        try {
            // 默认前 6 条数据
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                    // 获取 feed 流 json
                    e.onNext(HttpUtil.getNFeed("", count));
                }
            }).subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求点击的信息
     */
    public static void requestInfo(final AbstractDataBean bean) {
        try {
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                    // 获取 json
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("scene_id", "info");
                        jsonObject.put("distinct_id", SensorsDataAPI.sharedInstance().getAnonymousId());
                        jsonObject.put("log_id", "1");
                        jsonObject.put("limit", "1");
                        jsonObject.put("exp_id", "base");
                        jsonObject.put("item_id", bean.getItem_id() + "");
                        jsonObject.put("item_type", bean.getItem_type());
                        Log.i("qqqq: requestInfo --> ", jsonObject.toString());
                        e.onNext(HttpPOST.submitPostData(API.N_INFO, jsonObject));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CustomObserver(new IDataSuccess() {
                        @Override
                        public void onDataSuccess(String response) {

                        }
                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 重置用户
     */
    public static void logoutUser(Observer<String> observer){
        try {
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                    // 获取 json
                    e.onNext(HttpUtil.resetUser());

                }
            }).subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
