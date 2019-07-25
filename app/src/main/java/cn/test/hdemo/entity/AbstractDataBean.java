package cn.test.hdemo.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by yzk on 2019/7/18
 */

public abstract class AbstractDataBean implements MultiItemEntity {


    /**
     * item 布局
     */
    public static final int  FEED_ITEM_1 =1;
    private int type =1; // 默认1 item 的布局
    @Override
    public int getItemType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    /**
     * tag 的颜色
     */
    private int tagColor;
    public int getTagColor() {
        return tagColor;
    }
    public void setTagColor(int tagColor) {
        this.tagColor = tagColor;
    }

    /**
     * 实体的抽象方法
     */
    public abstract int getPrice();
    public abstract int getOrigin_price();
    public abstract List<String> getTags();
    public abstract String getImg();
    public abstract String getName();
    public abstract String getItem_id();
    public abstract String getItem_type();

}
