package cn.test.hdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.ArrayList;
import java.util.List;

import cn.test.hdemo.R;
import cn.test.hdemo.adapter.SARecommendAdapter;
import cn.test.hdemo.entity.SARecommendEntity;
import cn.test.hdemo.utils.HttpUtil;

public class DetailActivity extends BaseActivity {


    private ImageView imageView;
    private RecyclerView mRecyclerView;
    private TextView textView;
    private TextView tv_source;
    private SARecommendAdapter recommendAdapter;
    private int start = 0;
    private int count = 3;
    private String itemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        initAdapter();
    }

    private void initView() {
        imageView = findViewById(R.id.img_detail_activity);
        mRecyclerView = findViewById(R.id.ll_detail_activity_recyclerView);
        textView =findViewById(R.id.tv_detail_activity);
        tv_source=findViewById(R.id.tv_detail_source_activity);

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
        Intent intent = getIntent();
        setTitle("详情");
        String title = intent.getStringExtra("title");
        textView.setText(title);
        // img
        String img = intent.getStringExtra("img");
        Glide.with(this).load(img).into(imageView);
        String source = intent.getStringExtra("source");
        tv_source.setText(source);
        itemId = intent.getStringExtra("itemId");



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
        // 默认前 3 条数据

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 获取 article
                    String response = HttpUtil.getH_Article_relevant(start, count, itemId);
                    Moshi moshi = new Moshi.Builder().build();
                    JsonAdapter<SARecommendEntity> jsonAdapter = moshi.adapter(SARecommendEntity.class);


                    final SARecommendEntity obj = jsonAdapter.fromJson(response);
                    final List<SARecommendEntity.DataBean> data= obj.getData();
                    for(int i=0;i<data.size();i++){
                        data.get(i).setType(2);
                    }
                    // 更新数据
                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recommendAdapter.getData().clear();
                            recommendAdapter.addData(data);
                            recommendAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
}
