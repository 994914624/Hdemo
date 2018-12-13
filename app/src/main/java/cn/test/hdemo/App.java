package cn.test.hdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by yzk on 2018/12/10
 */

public class App extends Application {

    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
    }
}
