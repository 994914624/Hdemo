package cn.test.hdemo.adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.test.hdemo.App;
import cn.test.hdemo.R;
import cn.test.hdemo.entity.NewsEntity;
import cn.test.hdemo.entity.TouTiaoNewsEntity;

/**
 * Created by yzk on 2018/12/10
 */

public class NewsAdapter extends BaseMultiItemQuickAdapter<TouTiaoNewsEntity.T1348647853363Bean, BaseViewHolder>  {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NewsAdapter(List<TouTiaoNewsEntity.T1348647853363Bean> data) {
        super(data);
        addItemType(TouTiaoNewsEntity.T1348647853363Bean.CLICK_ITEM_VIEW, R.layout.item_news_1);
        //addItemType(NewsEntity.CLICK_ITEM_CHILD_VIEW, R.layout.item_news_2);


    }

    @Override
    protected void convert(BaseViewHolder helper, TouTiaoNewsEntity.T1348647853363Bean item) {

        switch (helper.getItemViewType()) {
            case TouTiaoNewsEntity.T1348647853363Bean.CLICK_ITEM_VIEW:
                //helper.addOnClickListener(R.id.btn_news_item_1);
                //TODO 设置数据
                ((TextView)helper.getView(R.id.tv_item_1_news_title)).setText(String.format("%s",item.getTitle()));
                ((TextView)helper.getView(R.id.tv_item_1_news_subtitle)).setText(String.format("%s . %s跟帖",item.getSource(),item.getReplyCount()));

                ImageView imageView = helper.getView(R.id.img_item_1_news);
                Glide.with(App.getApp()).load(item.getImgsrc()).into(imageView);
                break;
//            case NewsEntity.CLICK_ITEM_CHILD_VIEW:
//                helper.addOnClickListener(R.id.img_item_2_num_reduce).addOnClickListener(R.id.img_item_2_num_add);
//
//                // set img data
//                break;

        }

    }

}

