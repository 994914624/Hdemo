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
import java.util.ArrayList;
import java.util.List;

import cn.test.hdemo.R;
import cn.test.hdemo.activity.DetailActivity;
import cn.test.hdemo.adapter.SARecommendAdapter;
import cn.test.hdemo.custom.CustomObserver;
import cn.test.hdemo.custom.IDataSuccess;
import cn.test.hdemo.entity.AbstractDataBean;
import cn.test.hdemo.entity.AbstractEntity;
import cn.test.hdemo.utils.JsonUtil;
import cn.test.hdemo.utils.RxUtil;


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
    private IDataSuccess mDataSuccess;

    public static SARecommendAdapter getSARecommendAdapter() {
        return recommendAdapter;
    }

    public SARecommendFragment() {
        // Required empty public constructor
    }

    public void setIDataSuccess(IDataSuccess iDataSuccess){
        this.mDataSuccess = iDataSuccess;
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
                    refreshLayout.finishLoadMore(100);
                    //start += 10;
                    getData(String.valueOf(start), String.valueOf(count), down);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
        List<AbstractDataBean> data = new ArrayList<>();
        recommendAdapter = new SARecommendAdapter(data);
        recommendAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(recommendAdapter);
        // 获取默认数据
        getData(String.valueOf(start), String.valueOf(count), down);
    }


    private void getData(final String start, final String count, final int upDown) {
        try {
            RxUtil.getFeedData(start, count, new CustomObserver(new IDataSuccess() {
                @Override
                public void onDataSuccess(String response) {
                    try {
                        // 拿到实体数据
                        AbstractEntity obj =JsonUtil.convertFromJson(response);
                        if (obj == null) return;
                        if (upDown == up) {
                            recommendAdapter.addData(0, obj.getData());
                        } else {
                            recommendAdapter.addData(obj.getData());
                        }
                        recommendAdapter.notifyDataSetChanged();
                        // 回调给 UserFragment
                        mDataSuccess.onDataSuccess("");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "数据走丢了！", Toast.LENGTH_SHORT).show();
                    }
                }
            }));
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
                    AbstractDataBean bean = (AbstractDataBean) adapter.getItem(position);
                    if (bean == null) return;
                    // 告诉服务端点击信息
                    RxUtil.requestInfo(bean);
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

}
