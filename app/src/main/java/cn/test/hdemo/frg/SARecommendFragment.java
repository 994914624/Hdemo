package cn.test.hdemo.frg;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.test.hdemo.R;
import cn.test.hdemo.activity.DetailActivity;
import cn.test.hdemo.adapter.SARecommendAdapter;
import cn.test.hdemo.api.API;
import cn.test.hdemo.entity.NFeedEntity2;
import cn.test.hdemo.utils.HttpPOST;
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
public class SARecommendFragment extends BaseFragment {

    protected static String TAG = "SARecommendFragment";
    private View view;
    private RecyclerView mRecyclerView;
    private static SARecommendAdapter recommendAdapter;
    private int start = 0;
    private int count = 6;
    private static final int up = 0;
    private static final int down = 1;
    private Context context;

    public static SARecommendAdapter getSARecommendAdapter(){
        return recommendAdapter;
    }

    public SARecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recommend, container, false);
        context = view.getContext().getApplicationContext();
        initView();
        initRefresh();
        initAdapter();
        initListener();
        return view;
    }


    private void initView() {
        mRecyclerView = view.findViewById(R.id.sa_rec_recyclerView);
        //设置 2 列
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //解决item跳动
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.setLayoutManager(manager);

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    RefreshLayout refreshLayout;

    private void initRefresh() {
        try {
            refreshLayout = (RefreshLayout) view.findViewById(R.id.sa_rec_refreshLayout);

            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {

                    refreshlayout.finishRefresh(100);//刷新 100ms
                    //TODO 刷新数据
                    //start += 10;
                    Log.d(TAG, "onRefresh:" + start);
                    //TODO 刷新数据
                    //getData(String.valueOf(start),String.valueOf(count));
                    //avAdapter.setNewData();
                    // 获取默认数据
                    getData(String.valueOf(start), String.valueOf(count), up);
                }


            });

            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshLayout) {

                    //TODO 加载更多
                    Log.d(TAG, "onLoadMore:");
                    refreshLayout.finishLoadMore(50);
                    start += 10;
                    getData(String.valueOf(start), String.valueOf(count), down);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {

        List<NFeedEntity2.DataBean> data = new ArrayList<>();
        recommendAdapter = new SARecommendAdapter(data);
        recommendAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(recommendAdapter);
        // 获取默认数据
        getData(String.valueOf(start), String.valueOf(count), down);
    }


    private void getData(final String start, final String count, final int upDown) {
        try {
            // 默认前 10 条数据
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {

                    // 获取 json
                    e.onNext(HttpUtil.getNFeed(start, count));

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
                                Moshi moshi = new Moshi.Builder().build();
                                JsonAdapter<NFeedEntity2> jsonAdapter = moshi.adapter(NFeedEntity2.class);
                                NFeedEntity2 obj = jsonAdapter.fromJson(response);
                                if(obj==null)return;
                                if (upDown == up) {
                                    recommendAdapter.addData(0, obj.getData());
                                } else {
                                    recommendAdapter.addData(obj.getData());
                                }

                                recommendAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(),"数据走丢了！",Toast.LENGTH_SHORT).show();
                            }

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

    private void initListener() {
        recommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemClick: ");
                // Toast.makeText(context, "onItemClick" + position, Toast.LENGTH_SHORT).show();
                try {
                    NFeedEntity2.DataBean bean = (NFeedEntity2.DataBean) adapter.getItem(position);
                    if(bean==null)return;
                    // 告诉服务端点击信息
                    requestInfo(bean);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("title", String.format("%s", bean.getName()));
                    intent.putExtra("itemId", String.format("%s", bean.getItem_id()));
                    intent.putExtra("img", String.format("%s", bean.getImg()));
                    intent.putExtra("type", "shoes");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }

    /*
     *  请求 info
     *  {"scene_id":"info",
     *  "log_id":"1",
     *  "distinct_id":"test",
     *  "limit":"1",
     *  "exp_id":"base",
     *  "item_id":"24840",
     *  "item_type":"shoes"}
     */
    private void requestInfo(final NFeedEntity2.DataBean bean) {
        try {
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                    // 获取 json
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("scene_id","info");
                        jsonObject.put("distinct_id", SensorsDataAPI.sharedInstance().getAnonymousId());
                        jsonObject.put("log_id","1");
                        jsonObject.put("limit","1");
                        jsonObject.put("exp_id","base");
                        jsonObject.put("item_id",bean.getItem_id()+"");
                        jsonObject.put("item_type",bean.getItem_type());

                        Log.i("qqqq: requestInfo --> ",jsonObject.toString());
                        e.onNext(HttpPOST.submitPostData(API.N_INFO,jsonObject));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull String response) {
                            Log.i(TAG, "onNext requestInfo= " + response);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(TAG, "onError = " + e.getMessage());
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
