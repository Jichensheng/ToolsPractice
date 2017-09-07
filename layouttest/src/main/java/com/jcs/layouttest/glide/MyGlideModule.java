package com.jcs.layouttest.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.jcs.layouttest.BaseApplication;

import java.io.File;

@GlideModule
public class MyGlideModule extends AppGlideModule  {
  private static final int MAX_CACHE=100*1024*1024;


  @Override
  public void applyOptions(Context context, GlideBuilder builder) {
    builder.setDiskCache(new InternalCacheDiskCacheFactory(context,MAX_CACHE ));
    builder .setDiskCache(new DiskCache.Factory() {
      @Override
      public DiskCache build() {
        File cacheLocation = new File(BaseApplication.getInstance().getExternalCacheDir(), "gggglid4");
        cacheLocation.mkdirs();
        return DiskLruCacheWrapper.get(cacheLocation, MAX_CACHE);
      }
    });
    super.applyOptions(context, builder);
  }

  @Override
  public void registerComponents(Context context, Glide glide, Registry registry) {
    super.registerComponents(context, glide, registry);
  }
}