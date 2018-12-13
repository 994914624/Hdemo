package cn.test.hdemo.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by yzk on 2018/12/10
 *  news 实体
 */

public class NewsEntity implements MultiItemEntity {

    public static final int CLICK_ITEM_VIEW = 0;
    public static final int CLICK_ITEM_CHILD_VIEW = 1;
    public static final int LONG_CLICK_ITEM_VIEW = 2;
    public static final int LONG_CLICK_ITEM_CHILD_VIEW = 3;

    public int Type =0; // 默认0

    public NewsEntity(final int type) {
        Type = type;
    }

    public NewsEntity() {

    }

    @Override
    public int getItemType() {
        return Type;
    }
}
