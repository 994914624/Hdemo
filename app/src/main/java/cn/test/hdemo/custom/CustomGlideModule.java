package cn.test.hdemo.custom;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by yzk on 2019/7/23
 */

@GlideModule
public class CustomGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {

        // 内部存储缓存 20M
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "GlideImages", 1024 * 1024 * 20));

    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}