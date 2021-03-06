package com.rzm.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.rzm.imageloader.cache.ICache;
import com.rzm.imageloader.display.AbstractDisplay;
import com.rzm.imageloader.request.BitmapRequest;
import com.rzm.imageloader.utils.LogUtils;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author:renzhenming
 * Time:2018/6/13 7:22
 * Description:This is AbstractLoader
 * 加载器策略不同，则不同的加载器实现方式不同，但是他们有相同的操作，比如显示
 * 占位图等，所以这些相同操作在抽象一层出来
 */
public abstract class AbstractLoader implements Loader {

    private static final String TAG = "AbstractLoader";

    private AtomicInteger integer = new AtomicInteger(0);
    /**
     * 加载器加载图片的逻辑是先缓存后网络，所以需要持有缓存对象的引用
     */
    private ICache cache = SimpleImageLoader.getInstance().getConfig().getBitmapCache();

    /**
     * 同样因为要处理显示时的逻辑，所以需要持有显示配置对象的引用
     */
    private AbstractDisplay displayConfig = SimpleImageLoader.getInstance().getConfig().getDisplayConfig();

    @Override
    public void load(BitmapRequest request) {
        //从缓存中获取Bitmap
        Bitmap bitmap = null;
        if (cache != null) {
            bitmap = cache.get(request);
        }
        if (bitmap == null) {
            //显示加载中图片
            showLoadingImg(request);
            //加载器实现，抽象
            bitmap = onLoad(request);
            if (bitmap == null) {
                while (integer.incrementAndGet() <= 3) {
                    LogUtils.d(TAG, "load error ,retry " + integer);
                    bitmap = onLoad(request);
                    if (bitmap != null) {
                        break;
                    }
                }
                integer.set(0);
            }
            if (bitmap == null) {
                LogUtils.d(TAG, "retry 3 times, quit");
            } else if (cache != null) {
                //加入缓存
                cacheBitmap(request, bitmap);
            }
        } else {
            //有缓存
        }
        deliveryToUIThread(request, bitmap);
    }

    public abstract Bitmap onLoad(BitmapRequest request);

    protected void deliveryToUIThread(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        if (imageView != null) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    updateImageView(request, bitmap);
                }
            });
        }
    }

    private void updateImageView(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        //加载正常  防止图片错位
        if (bitmap != null && imageView.getTag().equals(request.getImageUrlTag())) {
            imageView.setImageBitmap(bitmap);
        }
        //有可能加载失败
        if (bitmap == null && displayConfig != null && displayConfig.getErrorImage() != 0) {
            imageView.setImageResource(displayConfig.getErrorImage());
        }
        //回调 给圆角图片  特殊图片进行扩展
        if (request.getImageListener() != null) {
            request.getImageListener().onComplete(imageView, bitmap, request.getImageUrl());
        }
    }

    /**
     * 缓存图片
     *
     * @param request
     * @param bitmap
     */
    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        if (request != null && bitmap != null) {
            synchronized (AbstractLoader.class) {
                cache.put(request, bitmap);
            }
        }
    }

    /**
     * 显示加载中占位图,需要判断用户有没有配置
     *
     * @param request
     */
    private void showLoadingImg(BitmapRequest request) {
        if (hasLoadingPlaceHolder()) {
            final ImageView imageView = request.getImageView();
            if (imageView != null) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(displayConfig.getLoadingImage());
                    }
                });
            }
        }
    }

    /**
     * 是否设置了加载中图片
     *
     * @return
     */
    private boolean hasLoadingPlaceHolder() {
        return displayConfig != null && displayConfig.getLoadingImage() != 0;
    }
}
