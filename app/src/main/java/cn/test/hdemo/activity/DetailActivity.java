package cn.test.hdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.test.hdemo.App;
import cn.test.hdemo.R;
import cn.test.hdemo.adapter.SARecommendAdapter;
import cn.test.hdemo.entity.NFeedEntity;
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
        try {
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
            type = intent.getStringExtra("type");
            // 获取默认数据
            //getData(String.valueOf(start), String.valueOf(count), type);
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    private void initAdapter() {
        List<NFeedEntity.DataBean> data = new ArrayList<>();
        recommendAdapter = new SARecommendAdapter(data);
        recommendAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Toast.makeText(App.getApp(),String.format("点击了神策推荐：%s",position),Toast.LENGTH_SHORT).show();

                try {
                    NFeedEntity.DataBean bean = (NFeedEntity.DataBean) adapter.getItem(position);
                    Intent intent = new Intent(DetailActivity.this, DetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if(bean == null)return;
                    intent.putExtra("title", String.format("%s", bean.getName()));
                    intent.putExtra("itemId", String.format("%s", bean.getItem_id()));
                    intent.putExtra("img", String.format("%s", bean.getImg()));
                    //intent.putExtra("source", String.format("%s", bean.getSource()));
                    //intent.putExtra("type","shence");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 数据
     */
    private void getData(final String start, final String count, final String type) {
        if("shence".equals(type)){
            setTitle("推荐详情");
            return;
        }
        // 默认前 3 条数据
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {

                // 获取 json

                if ("article".equals(type)) {
                    e.onNext(HttpUtil.getH_Article_relevant(start, count, itemId));
                } else {
                    e.onNext(HttpUtil.getNFeed(start, count));
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
                        Log.d(TAG, "onNext = " + response);
                        try {
                            Moshi moshi = new Moshi.Builder().build();
                            JsonAdapter<NFeedEntity> jsonAdapter = moshi.adapter(NFeedEntity.class);


                            final NFeedEntity obj = jsonAdapter.fromJson(response);
                            if(obj == null)return;
                            final List<NFeedEntity.DataBean> data = obj.getData();
                            for (int i = 0; i < data.size(); i++) {
                                data.get(i).setType(0);
                            }
                            // 更新数据
                            recommendAdapter.getData().clear();
                            recommendAdapter.addData(data);
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
}
