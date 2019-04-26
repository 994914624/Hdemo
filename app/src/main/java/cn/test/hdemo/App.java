package cn.test.hdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.ihidea.multilinechooselib.MultiLineChooseLayout;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzk on 2018/12/10
 */

public class App extends Application {

    private static App mContext;
    // debug 模式的数据接收地址 （测试，测试项目）
    final static String SA_SERVER_URL_DEBUG = "http://sdk-test.datasink.sensorsdata.cn/sa?project=yangzhankun&token=95c73ae661f85aa0";

    // release 模式的数据接收地址（发版，正式项目）
    final static String SA_SERVER_URL_RELEASE = "http://sdk-test.datasink.sensorsdata.cn/sa?project=yangzhankun&token=95c73ae661f85aa0";

    // SDK Debug 模式选项
    //   SensorsDataAPI.DebugMode.DEBUG_OFF - 关闭 Debug 模式
    //   SensorsDataAPI.DebugMode.DEBUG_ONLY - 打开 Debug 模式，校验数据，但不进行数据导入
    //   SensorsDataAPI.DebugMode.DEBUG_AND_TRACK - 打开 Debug 模式，校验数据，并将数据导入到 Sensors Analytics 中
    // TODO 注意！请不要在正式发布的 App 中使用 DEBUG_AND_TRACK /DEBUG_ONLY 模式！ 请使用 DEBUG_OFF 模式！！！

    // debug 时，初始化 SDK 使用测试项目数据接收 URL 、使用 DEBUG_AND_TRACK 模式；release 时，初始化 SDK 使用正式项目数据接收 URL 、使用 DEBUG_OFF 模式。
    private boolean isDebugMode;

    public static App getApp (){
        return mContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        if(null == mContext) mContext = this;
        initSensorsDataSDK(this);
        chooseDialog();
    }

    private void chooseDialog() {
        mColorData.add("美食");
        mColorData.add("历史");
        mColorData.add("体育");
        mColorData.add("娱乐");
        mColorData.add("股票");
        mColorData.add("科技");
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                // 弹出提示
                if(activity!=null){
                    Log.d("qqq"," intent:-->"+activity.getIntent().toString());
                   if(activity.getClass().getSimpleName().equals("MainActivity")){
                       //showChooseDialog(activity);
                   }
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    //设置数据源
    private List<String> mColorData = new ArrayList<String>();
    private MultiLineChooseLayout multiChoose;
    private Button okBtn;
    private AlertDialog dialog = null;
    private void showChooseDialog(Activity activity) {
        // AlertDialog
        if(dialog!=null){
            dialog.dismiss();
            dialog=null;
        }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setCancelable(false);
            dialog = builder.create();
            View view = LayoutInflater.from(activity).inflate(R.layout.dialog_choose, null);


            multiChoose = (MultiLineChooseLayout)view.findViewById(R.id.dialog_choose_flowLayout);
            multiChoose.setList(mColorData);
            multiChoose.setOnItemClickListener(new MultiLineChooseLayout.onItemClickListener() {
                @Override
                public void onItemClick(int i, String s) {
                    Log.d("qqq",":"+i+" s:-->"+s);
                    if(multiChoose.getAllItemSelectedTextWithListArray().size()>0){
                        okBtn.setTextColor(Color.BLACK);
                    } else {
                        okBtn.setTextColor(Color.WHITE);
                    }

                }
            });


            okBtn = view.findViewById(R.id.dialog_choose_btn);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                    ArrayList arrayList = multiChoose.getAllItemSelectedTextWithListArray();
                    Log.d("qqq",":"+arrayList.toString());
                    if(arrayList.size()>0){
                        Toast.makeText(v.getContext(),"已为您推荐您感兴趣的数据！",Toast.LENGTH_LONG).show();
                        if(dialog!=null){
                            dialog.dismiss();
                            dialog=null;
                        }
                    }else {
                        Toast.makeText(v.getContext(),"请选择您的兴趣！",Toast.LENGTH_SHORT).show();
                    }
                }
            });

//            // 设置 Dialog 宽，圆角
//            Window window = dialog.getWindow();
//            if(window !=null){
//                WindowManager.LayoutParams layoutParams = window.getAttributes();
//                layoutParams.width = getResources().getDisplayMetrics().widthPixels*4/5;
//                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                window.setAttributes(layoutParams);
//                //window.setBackgroundDrawableResource(R.drawable.dialog_corner);
//                window.setBackgroundDrawable(roundCornerShape());
//            }
            dialog.show();
            dialog.setContentView(view);

    }


    /**
     * 初始化 SDK 、设置公共属性、开启自动采集
     */
    private void initSensorsDataSDK(Context context) {
        try {
            // 初始化 SDK
            SensorsDataAPI.sharedInstance(
                    context,                                                                                  // 传入 Context
                    (isDebugMode = isDebugMode(context)) ? SA_SERVER_URL_DEBUG : SA_SERVER_URL_RELEASE       // 数据接收的 URL
                    ); // Debug 模式选项

            // 初始化SDK后，获取应用名称设置为公共属性
            JSONObject properties = new JSONObject();
            properties.put("app_name", getAppName(context));
            SensorsDataAPI.sharedInstance().registerSuperProperties(properties);

            SensorsDataAPI.sharedInstance().trackInstallation("AppInstall",new JSONObject().put("DownloadChannel","sensors"));

            // 打开自动采集, 并指定追踪哪些 AutoTrack 事件
            List<SensorsDataAPI.AutoTrackEventType> eventTypeList = new ArrayList<>();
            // $AppStart
            eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_START);
            // $AppEnd
            eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_END);
            // $AppViewScreen
            eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_VIEW_SCREEN);
            // $AppClick
            eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_CLICK);
            SensorsDataAPI.sharedInstance().enableAutoTrack(eventTypeList);

            SensorsDataAPI.sharedInstance().trackFragmentAppViewScreen();

            //SensorsDataAPI.sharedInstance().enableLog(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context App 的 Context
     *                获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param context App 的 Context
     * @return debug return true,release return false
     * 用于判断是 debug 包，还是 relase 包
     */
    public static boolean isDebugMode(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 圆角 shape
     */
    private GradientDrawable roundCornerShape(){
        GradientDrawable roundShape =new GradientDrawable();
        roundShape.setShape(GradientDrawable.RECTANGLE);//圆角矩形
        roundShape.setColor(Color.WHITE);
        roundShape.setCornerRadius(30);
        return roundShape;
    }


}
