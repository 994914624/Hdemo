package cn.test.hdemo.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.test.hdemo.App;
import cn.test.hdemo.R;
import cn.test.hdemo.entity.AVEntity;
import cn.test.hdemo.entity.SARecommendEntity;

/**
 * Created by yzk on 2018/12/13
 */

public class SARecommendAdapter   extends BaseMultiItemQuickAdapter<SARecommendEntity.DataBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public SARecommendAdapter(List<SARecommendEntity.DataBean> data) {
        super(data);
        addItemType(SARecommendEntity.DataBean.CLICK_ITEM_VIEW, R.layout.item_news_1);
        addItemType(SARecommendEntity.DataBean.CLICK_ITEM_VIEW_2, R.layout.item_detial_webview_ad);
    }



    @Override
    protected void convert(BaseViewHolder helper, SARecommendEntity.DataBean item) {
        switch (helper.getItemViewType()) {
            case SARecommendEntity.DataBean.CLICK_ITEM_VIEW:
                //TODO 设置数据
                ((TextView)helper.getView(R.id.tv_item_1_news_title)).setText(String.format("%s",item.getTitle()));
                ((TextView)helper.getView(R.id.tv_item_1_news_subtitle)).setText(String.format("%s",item.getSource()));

                ImageView imageView = helper.getView(R.id.img_item_1_news);
                Glide.with(App.mContext).load(item.getImg()).into(imageView);

                break;
            case SARecommendEntity.DataBean.CLICK_ITEM_VIEW_2:
                //TODO 设置数据
                ((TextView)helper.getView(R.id.tv_item_detial_webview_ad_text)).setText(String.format("%s",item.getTitle()));

                break;
        }
    }
}
