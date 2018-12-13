package cn.test.hdemo.frg;


import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
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
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.Moshi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.test.hdemo.MainActivity;
import cn.test.hdemo.R;
import cn.test.hdemo.activity.DetailWebviewActivity;
import cn.test.hdemo.adapter.NewsAdapter;
import cn.test.hdemo.api.API;
import cn.test.hdemo.entity.AVEntity;
import cn.test.hdemo.entity.NewsEntity;
import cn.test.hdemo.entity.TouTiaoNewsEntity;
import cn.test.hdemo.utils.HtmlParseUtil;
import cn.test.hdemo.utils.HttpUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {
    protected   static String TAG="NewsFragment";

    private View view;
    private RecyclerView mRecyclerView;
    private NewsAdapter newsAdapter;
    private Context context;
    private int start =0;
    private int count =10;
    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);
        context = view.getContext().getApplicationContext();
        initView();
        initRefresh();
        initAdapter();
        initListener();
        return view;
    }

    private void initListener() {

        newsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemClick: ");
                Toast.makeText(context, "onItemClick" + position, Toast.LENGTH_SHORT).show();

                try {
                    TouTiaoNewsEntity.T1348647853363Bean bean = (TouTiaoNewsEntity.T1348647853363Bean) adapter.getItem(position);
                    Intent intent = new Intent(context,DetailWebviewActivity.class);
                    //intent.putExtra("title",String.format(""));
                    intent.putExtra("H5_url",String.format("%s",bean.getUrl()));
                    startActivity(intent);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

//        newsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                Log.d(TAG, "onItemChildClick: ");
//                Toast.makeText(context, "onItemChildClick" + position, Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    private void initAdapter() {
        List<TouTiaoNewsEntity.T1348647853363Bean> data = new ArrayList<>();
        newsAdapter = new NewsAdapter(data);
        newsAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(newsAdapter);
        // 获取默认数据
        getData(String.valueOf(start),String.valueOf(count));
    }

    private void initView() {
        mRecyclerView = view.findViewById(R.id.news_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    /**
     * refresh
     */
    private void initRefresh() {
        RefreshLayout refreshLayout = (RefreshLayout)view.findViewById(R.id.news_refreshLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                refreshlayout.finishRefresh(1500);//刷新1s

                start += 10;
                Log.d(TAG,"onRefresh:"+start);
                //TODO 刷新数据
                getData(String.valueOf(start),String.valueOf(count));

                //newsAdapter.setNewData();
            }
        });
    }

    private void getData(final String start, final String count) {
        // 默认前 10 条数据
        HttpUtil.getTouTiaoNews(context, start, count,  new ResponseListener() {
            @Override
            public void onResponse(String response, Exception error) {
                if (error == null){
                    try {
                        Log.d(TAG,"resp:"+response);

                        // html str
                        String string = HtmlParseUtil.getInput(start,count);

                        Moshi moshi=new Moshi.Builder().build();
                        JsonAdapter<TouTiaoNewsEntity> jsonAdapter=moshi.adapter(TouTiaoNewsEntity.class);
                        final TouTiaoNewsEntity obj=jsonAdapter.fromJson(string);

                        // 更新数据
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                List<TouTiaoNewsEntity.T1348647853363Bean> list = obj.getT1348647853363();
                                if("0".equals(start)) {
                                    list.remove(Integer.parseInt(start));
                                    newsAdapter.addData(list);
                                }else {
                                    newsAdapter.getData().clear();
                                    newsAdapter.addData(0,list);
                                }

                                newsAdapter.notifyDataSetChanged();
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


}
