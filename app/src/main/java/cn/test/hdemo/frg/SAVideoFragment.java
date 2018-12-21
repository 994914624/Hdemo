package cn.test.hdemo.frg;


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
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.test.hdemo.R;
import cn.test.hdemo.activity.DetailActivity;
import cn.test.hdemo.adapter.SARecommendAdapter;
import cn.test.hdemo.entity.SARecommendEntity;
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
public class SAVideoFragment extends BaseFragment {

    protected static String TAG = "SARecommendFragment";
    private View view;
    private RecyclerView mRecyclerView;
    private SARecommendAdapter recommendAdapter;
    private int start = 0;
    private int count = 10;
    private Context context;


    public SAVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        context = view.getContext().getApplicationContext();
        initView();
        initRefresh();
        initAdapter();
        initListener();
        return view;
    }

    private void initView() {
        mRecyclerView = view.findViewById(R.id.sa_video_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    private void initRefresh() {

        RefreshLayout refreshLayout = (RefreshLayout) view.findViewById(R.id.sa_video_refreshLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                refreshlayout.finishRefresh(1500);//刷新1s
                //TODO 刷新数据
                //start += 10;
                Log.d(TAG, "onRefresh:" + start);
                //TODO 刷新数据
                //getData(String.valueOf(start),String.valueOf(count));
            }


        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {

                //TODO 加载更多
                Log.d(TAG, "onLoadMore:");
                refreshLayout.finishLoadMore(1500);
                start += 10;
                getData(String.valueOf(start), String.valueOf(count));
            }
        });
    }

    private void initAdapter() {

        List<SARecommendEntity.DataBean> data = new ArrayList<>();
        recommendAdapter = new SARecommendAdapter(data);
        recommendAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(recommendAdapter);
        // 获取默认数据
        getData(String.valueOf(start), String.valueOf(count));
    }

    private void getData(final String start, final String count) {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {

                // 获取 json
                e.onNext(HttpUtil.getH_Video(start, count));

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
                            final SARecommendEntity obj = jsonAdapter.fromJson(response);
                            // 更新数据
                            //recommendAdapter.getData().clear();
                            recommendAdapter.addData(obj.getData());
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
    }

    private void initListener() {

        recommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemClick: ");
                //Toast.makeText(context, "onItemClick" + position, Toast.LENGTH_SHORT).show();
                try {
                    SARecommendEntity.DataBean bean = (SARecommendEntity.DataBean) adapter.getItem(position);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("title", String.format("%s", bean.getTitle()));
                    intent.putExtra("itemId", String.format("%s", bean.getItem_id()));
                    intent.putExtra("img", String.format("%s", bean.getImg()));
                    intent.putExtra("source", String.format("%s", bean.getSource()));
                    intent.putExtra("type","video");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }

}
