package cn.test.hdemo.frg;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kongzue.baseokhttp.listener.ResponseListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.test.hdemo.R;
import cn.test.hdemo.activity.DetailWebviewActivity;
import cn.test.hdemo.adapter.AVAdapter;

import cn.test.hdemo.entity.AVEntity;
import cn.test.hdemo.utils.HttpUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseFragment {

    protected   static String TAG="UserFragment";
    private View view;
    private RecyclerView mRecyclerView;
    private AVAdapter avAdapter;
    private int start =0;
    private int count =10;
    private Context context;
    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        context = view.getContext().getApplicationContext();
        initView();
//        initRefresh();
//        initAdapter();
//        initListener();
        return view;
    }
    TextView tvUserId;
    TextView btnUserLogout;
    private void initView() {
//        mRecyclerView = view.findViewById(R.id.user_recyclerView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        tvUserId = view.findViewById(R.id.tv_user_id);
        tvUserId.setText(String.format("当前用户：%s", SensorsDataAPI.sharedInstance().getAnonymousId()));

        btnUserLogout = view.findViewById(R.id.btn_user_logout);
        btnUserLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 退出
                logoutUser();
            }
        });

    }

//    private void initRefresh() {
//
//        RefreshLayout refreshLayout = (RefreshLayout)view.findViewById(R.id.user_refreshLayout);
//
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//
//                refreshlayout.finishRefresh(1500);//刷新1s
//                //TODO 刷新数据
//                start += 10;
//                Log.d(TAG,"onRefresh:"+start);
//                //TODO 刷新数据
//                getData(String.valueOf(start),String.valueOf(count));
//                //avAdapter.setNewData();
//            }
//        });
//    }
//
//    private void initAdapter() {
//
//        List<AVEntity.SubjectsBean> data = new ArrayList<>();
//        avAdapter = new AVAdapter(data);
//        avAdapter.openLoadAnimation();
//        mRecyclerView.setAdapter(avAdapter);
//        // 获取默认数据
//        getData(String.valueOf(start),String.valueOf(count));
//    }
//
//    private void getData(String start,String count) {
//        // 默认前 10 条数据
//        HttpUtil.getAV(context, start, count,  new ResponseListener() {
//            @Override
//            public void onResponse(String response, Exception error) {
//                if (error == null){
//                    try {
//
//                        Moshi moshi=new Moshi.Builder().build();
//                        JsonAdapter<AVEntity> jsonAdapter=moshi.adapter(AVEntity.class);
//                        final AVEntity obj=jsonAdapter.fromJson(response);
//                        // 更新数据
//                        view.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                avAdapter.getData().clear();
//                                if (obj != null&&obj.getSubjects()!=null) {
//                                    avAdapter.addData(obj.getSubjects());
//                                    avAdapter.notifyDataSetChanged();
//                                }
//
//                            }
//                        });
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Log.d(TAG,"Moshi : error");
//                    }
//                }else {
//                    Log.d(TAG,"error: "+error.toString());
//                }
//            }
//        });
//
//    }
//
//    private void initListener() {
//
//        avAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Log.d(TAG, "onItemClick: ");
//               // Toast.makeText(context, "onItemClick" + position, Toast.LENGTH_SHORT).show();
//                try {
//                    AVEntity.SubjectsBean bean = (AVEntity.SubjectsBean) adapter.getItem(position);
//                    Intent intent = new Intent(context,DetailWebviewActivity.class);
//                    intent.putExtra("title",String.format("%s",bean.getTitle()));
//                    intent.putExtra("H5_url",String.format("%s",bean.getAlt()));
//                    startActivity(intent);
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//
//
//            }
//        });
//
////        avAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
////            @Override
////            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
////                Log.d(TAG, "onItemChildClick: ");
////                Toast.makeText(context, "onItemChildClick" + position, Toast.LENGTH_SHORT).show();
////            }
////        });
//    }


    private void logoutUser(){
        try {
            // 默认前 10 条数据
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {

                    // 获取 json
                    e.onNext(HttpUtil.resetUser());

                }
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull String response) {
                            Log.d(TAG, "onNext = " + response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.optInt("code") ==200){
                                    Toast.makeText(getContext(),"账号已退出",Toast.LENGTH_SHORT).show();
                                    tvUserId.setText("");
                                    btnUserLogout.setText("账号已退出");
                                    btnUserLogout.setBackgroundColor(Color.GRAY);
                                    btnUserLogout.setEnabled(false);
                                    return;
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                            Toast.makeText(getContext(),"账号退出失败！！！",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError = " + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
