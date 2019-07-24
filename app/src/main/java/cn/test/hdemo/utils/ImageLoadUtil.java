package cn.test.hdemo.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import cn.test.hdemo.App;

/**
 * Created by yzk on 2019/7/23
 */

public class ImageLoadUtil {

    public static void loadNetImg(String imgUrl, ImageView imageView) {
        Glide.with(App.getApp())
                .load(imgUrl)
                .into(imageView);
    }

    public static void loadAndCacheImg(String imgUrl, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options = options.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(App.getApp())
                .load(imgUrl)
                .apply(options)
                .into(imageView);
    }

    public static void cleanCacheImg(String imgUrl, ImageView imageView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(App.getApp()).clearDiskCache();
            }
        }).start();
    }

}
