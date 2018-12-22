package cn.test.hdemo.frg;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kongzue.baseokhttp.listener.ResponseListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.test.hdemo.R;
import cn.test.hdemo.activity.DetailActivity;
import cn.test.hdemo.activity.DetailWebviewActivity;
import cn.test.hdemo.adapter.AVAdapter;
import cn.test.hdemo.adapter.SARecommendAdapter;
import cn.test.hdemo.api.API;
import cn.test.hdemo.entity.AVEntity;
import cn.test.hdemo.entity.SARecommendEntity;
import cn.test.hdemo.utils.HttpPOST;
import cn.test.hdemo.utils.HttpUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class SARecommendFragment extends BaseFragment {

    protected static String TAG = "SARecommendFragment";
    private View view;
    private RecyclerView mRecyclerView;
    private SARecommendAdapter recommendAdapter;
    private int start = 0;
    private int count = 10;
    private static final int up = 0;
    private static final int down = 1;
    private Context context;

    public SARecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_img, container, false);
        context = view.getContext().getApplicationContext();
        initView();
        initRefresh();
        initAdapter();
        initListener();
        return view;
    }


    private void initView() {
        mRecyclerView = view.findViewById(R.id.sa_rec_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    RefreshLayout refreshLayout;

    private void initRefresh() {
        try {
            refreshLayout = (RefreshLayout) view.findViewById(R.id.sa_rec_refreshLayout);

            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {

                    refreshlayout.finishRefresh(1000);//刷新1s
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
                    refreshLayout.finishLoadMore(1500);
                    start += 10;
                    getData(String.valueOf(start), String.valueOf(count), down);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {

        List<SARecommendEntity.DataBean> data = new ArrayList<>();
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
                    e.onNext(HttpUtil.getH_Article(start, count));

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
                                JsonAdapter<SARecommendEntity> jsonAdapter = moshi.adapter(SARecommendEntity.class);
                                SARecommendEntity obj = jsonAdapter.fromJson(response);
                                if (upDown == up) {
                                    recommendAdapter.addData(0, obj.getData());
                                } else {
                                    recommendAdapter.addData(obj.getData());
                                }

                                recommendAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {

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
                    SARecommendEntity.DataBean bean = (SARecommendEntity.DataBean) adapter.getItem(position);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("title", String.format("%s", bean.getTitle()));
                    intent.putExtra("itemId", String.format("%s", bean.getItem_id()));
                    intent.putExtra("img", String.format("%s", bean.getImg()));
                    intent.putExtra("source", String.format("%s", bean.getSource()));
                    intent.putExtra("type", "article");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }

}
