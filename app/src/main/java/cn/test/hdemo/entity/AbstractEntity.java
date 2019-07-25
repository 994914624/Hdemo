package cn.test.hdemo.entity;

import java.util.List;

/**
 * Created by yzk on 2019/7/25
 */

public abstract class AbstractEntity {
    public abstract List<? extends AbstractDataBean> getData();
}
