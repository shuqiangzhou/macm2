package com.mail.myapplication.ui.glidemoudle;


import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import java.nio.ByteBuffer;

@GlideModule
public class MyGlideModule extends AppGlideModule {


    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.prepend(DesLoaderUrl.class, ByteBuffer.class,  new DesUrlModelLoaderFactory(context));
//        registry
    }

    /**
     * 这里不开启，避免添加相同的modules两次 * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
