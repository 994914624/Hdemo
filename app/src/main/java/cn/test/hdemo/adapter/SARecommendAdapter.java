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

/**
 * Created by yzk on 2018/12/13
 */

public class SARecommendAdapter   extends BaseMultiItemQuickAdapter<AVEntity.SubjectsBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public SARecommendAdapter(List<AVEntity.SubjectsBean> data) {
        super(data);
        addItemType(AVEntity.SubjectsBean.ITEM_AV_1, R.layout.item_av_1);
    }



    @Override
    protected void convert(BaseViewHolder helper, AVEntity.SubjectsBean item) {
        switch (helper.getItemViewType()) {
            case AVEntity.SubjectsBean.ITEM_AV_1:
                //TODO 设置数据
                ((TextView)helper.getView(R.id.tv_item_av_1_title)).setText(String.format("%s / %s",item.getTitle(),item.getOriginal_title()));
                ((TextView)helper.getView(R.id.tv_item_av_1_sutitle)).setText(String.format("导演： %s",item.getDirectors().get(0).getName()));
                ImageView imageView = helper.getView(R.id.img_item_av_1);
                Glide.with(App.mContext).load(item.getImages().getSmall()).into(imageView);

                break;
        }
    }
}
