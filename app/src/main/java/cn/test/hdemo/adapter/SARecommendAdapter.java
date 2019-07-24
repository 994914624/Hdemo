package cn.test.hdemo.adapter;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Random;

import cn.test.hdemo.App;
import cn.test.hdemo.R;
import cn.test.hdemo.entity.NFeedEntity2;
import cn.test.hdemo.utils.ImageLoadUtil;

/**
 * Created by yzk on 2018/12/13
 */

public class SARecommendAdapter extends BaseMultiItemQuickAdapter<NFeedEntity2.DataBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public SARecommendAdapter(List<NFeedEntity2.DataBean> data) {
        super(data);
        addItemType(NFeedEntity2.DataBean.CLICK_ITEM_VIEW, R.layout.item_news_1);
        addItemType(NFeedEntity2.DataBean.CLICK_ITEM_VIEW_2, R.layout.item_detial_webview_ad);
        addItemType(NFeedEntity2.DataBean.FEED_ITEM_1, R.layout.item_feed_1);
    }


    @Override
    protected void convert(BaseViewHolder helper, NFeedEntity2.DataBean item) {
        switch (helper.getItemViewType()) {
            case NFeedEntity2.DataBean.CLICK_ITEM_VIEW:
                try {
                    if (item != null) {
                        //TODO 设置数据
                        ((TextView) helper.getView(R.id.tv_item_1_news_title)).setText(String.format("%s", item.getName() + ""));
                        ((TextView) helper.getView(R.id.tv_item_1_news_subtitle)).setText(String.format("¥%s", item.getPrice() + ""));

                        ImageView imageView = helper.getView(R.id.img_item_1_news);
                        Glide.with(App.getApp()).load(item.getImg()).into(imageView);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case NFeedEntity2.DataBean.CLICK_ITEM_VIEW_2:
                //TODO 设置数据
                ((TextView) helper.getView(R.id.tv_item_detial_webview_ad_text)).setText(String.format("%s", item.getName() + ""));
                break;
            case NFeedEntity2.DataBean.FEED_ITEM_1:
                try {
                    if (item != null) {
                        //TODO Feed
                        try {
                            ((TextView) helper.getView(R.id.item_feed_1_tv_name)).setText(String.format("%s", item.getName() + ""));
                            ((TextView) helper.getView(R.id.item_feed_1_tv_price)).setText(String.format("¥%s", item.getPrice() + ""));
                            TextView tv_origin_price = ((TextView) helper.getView(R.id.item_feed_1_tv_origin_price));
                            if (item.getPrice() != item.getOrigin_price()) {
                                tv_origin_price.setText(String.format("价格 ¥%s", item.getOrigin_price() + ""));
                                tv_origin_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                tv_origin_price.setVisibility(View.VISIBLE);
                            } else {
                                tv_origin_price.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(item.getTagColor() == 0){
                            // tags 随机颜色标签
                            Random random = new Random();
                            int color = Color.argb(255, random.nextInt(126), random.nextInt(256), random.nextInt(256));
                            item.setTagColor(color);
                        }
                        TextView tv_tags = ((TextView) helper.getView(R.id.item_feed_1_tv_tags));
                        tv_tags.setBackgroundColor(item.getTagColor());
                        tv_tags.setText(String.format("%s", item.getTags().toString().replace(',', '/') + ""));
                        ImageView imageView = helper.getView(R.id.item_feed_1_img);
                        // Glide
                        //Glide.with(App.getApp()).load(item.getImg()).into(imageView);
                        ImageLoadUtil.loadAndCacheImg(item.getImg(),imageView);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
