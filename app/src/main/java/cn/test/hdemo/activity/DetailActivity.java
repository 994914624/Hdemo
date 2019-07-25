package cn.test.hdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.util.ArrayList;
import java.util.List;

import cn.test.hdemo.App;
import cn.test.hdemo.R;
import cn.test.hdemo.adapter.SARecommendAdapter;

import cn.test.hdemo.entity.AbstractDataBean;

public class DetailActivity extends BaseActivity {


    private ImageView imageView;
    private RecyclerView mRecyclerView;
    private TextView textView;
    private TextView tv_source;
    private SARecommendAdapter recommendAdapter;
    private int start = 0;
    private int count = 3;
    private String itemId;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        //initAdapter();
    }

    private void initView() {
        imageView = findViewById(R.id.img_detail_activity);
        mRecyclerView = findViewById(R.id.ll_detail_activity_recyclerView);
        textView = findViewById(R.id.tv_detail_activity);
        tv_source = findViewById(R.id.tv_detail_source_activity);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.this.finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setData();
    }

    private void setData() {
        try {
            Intent intent = getIntent();
            setTitle("详情");
            if(intent == null)return;
            String title = intent.getStringExtra("title");
            textView.setText(title);
            // img
            String img = intent.getStringExtra("img");
            Glide.with(this).load(img).into(imageView);
            String source = intent.getStringExtra("source");
            tv_source.setText(source);
            itemId = intent.getStringExtra("itemId");
            type = intent.getStringExtra("type");
            // 获取默认数据
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private void initAdapter() {
        List<AbstractDataBean> data = new ArrayList<>();
        recommendAdapter = new SARecommendAdapter(data);
        recommendAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Toast.makeText(App.getApp(),String.format("点击了神策推荐：%s",position),Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * 数据
     */
    private void getData(final String start, final String count, final String type) {

    }
}
