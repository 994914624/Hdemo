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
import com.kongzue.baseokhttp.listener.ResponseListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.test.hdemo.R;
import cn.test.hdemo.activity.DetailWebviewActivity;
import cn.test.hdemo.adapter.AVAdapter;

import cn.test.hdemo.api.API;
import cn.test.hdemo.entity.AVEntity;
import cn.test.hdemo.utils.HttpUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class AVFragment extends BaseFragment {

    protected   static String TAG="AVFragment";
    private View view;
    private RecyclerView mRecyclerView;
    private AVAdapter avAdapter;
    private int start =0;
    private int count =10;
    private Context context;
    public AVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_av, container, false);
        context = view.getContext().getApplicationContext();
        initView();
        initRefresh();
        initAdapter();
        initListener();
        return view;
    }

    private void initView() {
        mRecyclerView = view.findViewById(R.id.av_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    private void initRefresh() {

        RefreshLayout refreshLayout = (RefreshLayout)view.findViewById(R.id.av_refreshLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                refreshlayout.finishRefresh(1500);//刷新1s
                //TODO 刷新数据
                start += 10;
                Log.d(TAG,"onRefresh:"+start);
                //TODO 刷新数据
                getData(String.valueOf(start),String.valueOf(count));
                //avAdapter.setNewData();
            }
        });
    }

    private void initAdapter() {

        List<AVEntity.SubjectsBean> data = new ArrayList<>();
        avAdapter = new AVAdapter(data);
        avAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(avAdapter);
        // 获取默认数据
        getData(String.valueOf(start),String.valueOf(count));
    }

    private void getData(String start,String count) {
        // 默认前 10 条数据
        HttpUtil.getAV(context, start, count,  new ResponseListener() {
            @Override
            public void onResponse(String response, Exception error) {
                if (error == null){
                    try {

                        Moshi moshi=new Moshi.Builder().build();
                        JsonAdapter<AVEntity> jsonAdapter=moshi.adapter(AVEntity.class);
                        final AVEntity obj=jsonAdapter.fromJson(response);
                        // 更新数据
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                avAdapter.getData().clear();
                                avAdapter.addData(obj.getSubjects());
                                avAdapter.notifyDataSetChanged();
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TAG,"Moshi : error");
                    }
                }else {
                    Log.d(TAG,"error: "+error.toString());
                }
            }
        });

    }

    private void initListener() {

        avAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemClick: ");
               // Toast.makeText(context, "onItemClick" + position, Toast.LENGTH_SHORT).show();
                try {
                    AVEntity.SubjectsBean bean = (AVEntity.SubjectsBean) adapter.getItem(position);
                    Intent intent = new Intent(context,DetailWebviewActivity.class);
                    intent.putExtra("title",String.format("%s",bean.getTitle()));
                    intent.putExtra("H5_url",String.format("%s",bean.getAlt()));
                    startActivity(intent);
                } catch (Exception e){
                    e.printStackTrace();
                }


            }
        });

//        avAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                Log.d(TAG, "onItemChildClick: ");
//                Toast.makeText(context, "onItemChildClick" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

}
