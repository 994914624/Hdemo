package cn.test.hdemo.frg;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import cn.test.hdemo.R;
import cn.test.hdemo.adapter.SARecommendAdapter;
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
    private static final int up = 0;
    private static final int down = 1;
    private Context context;


    public SAVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vedio, container, false);
        context = view.getContext().getApplicationContext();
//        initView();
//        initRefresh();
//        initAdapter();
//        initListener();
        return view;
    }

    private void initView() {
        mRecyclerView = view.findViewById(R.id.sa_video_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    RefreshLayout refreshLayout;

    private void initRefresh() {

        try {
            refreshLayout = (RefreshLayout) view.findViewById(R.id.sa_video_refreshLayout);
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {

                    refreshlayout.finishRefresh(1000);//刷新1s
                    //TODO 刷新数据
                    //start += 10;
                    Log.d(TAG, "onRefresh:" + start);
                    //TODO 刷新数据
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
                    getData(String.valueOf(start), String.valueOf(count),down);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
//
//        List<NFeedEntity.DataBean> data = new ArrayList<>();
//        recommendAdapter = new SARecommendAdapter(data);
//        recommendAdapter.openLoadAnimation();
//        mRecyclerView.setAdapter(recommendAdapter);
//        // 获取默认数据
//        getData(String.valueOf(start), String.valueOf(count),down);
    }

    private void getData(final String start, final String count,final int upDown) {
        try {
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

//                            Moshi moshi = new Moshi.Builder().build();
//                            JsonAdapter<NFeedEntity> jsonAdapter = moshi.adapter(NFeedEntity.class);
//                            final NFeedEntity obj = jsonAdapter.fromJson(response);
//                            // 更新数据
//                            //recommendAdapter.getData().clear();
//                            //
//                            // recommendAdapter.addData(obj.getData());
//                            if (upDown == up) {
//                                recommendAdapter.addData(0, obj.getData());
//                            } else {
//                                recommendAdapter.addData(obj.getData());
//                            }
//                            recommendAdapter.notifyDataSetChanged();

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
                //Toast.makeText(context, "onItemClick" + position, Toast.LENGTH_SHORT).show();
//                try {
//                    NFeedEntity.DataBean bean = (NFeedEntity.DataBean) adapter.getItem(position);
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    intent.putExtra("title", String.format("%s", bean.getName()));
//                    intent.putExtra("itemId", String.format("%s", bean.getItem_id()));
//                    intent.putExtra("img", String.format("%s", bean.getImg()));
//                    intent.putExtra("type", "video");
//                    startActivity(intent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


            }
        });

    }

}
