package cn.test.hdemo.utils;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import cn.test.hdemo.entity.AbstractEntity;
import cn.test.hdemo.entity.NFeedEntity2;

/**
 * Created by yzk on 2019/7/25
 */

public class JsonUtil {

    public static AbstractEntity convertFromJson(String jsonStr){
        try {
            // 解析 json 到具体实体类
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<NFeedEntity2> jsonAdapter = moshi.adapter(NFeedEntity2.class);
            return  jsonAdapter.fromJson(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
