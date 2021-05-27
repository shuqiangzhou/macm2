package com.mail.myapplication.ui.glidemoudle;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.nio.ByteBuffer;


public class DesUrlModelLoaderFactory implements ModelLoaderFactory<DesLoaderUrl, ByteBuffer> {

    private Context context;

    public DesUrlModelLoaderFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ModelLoader<DesLoaderUrl, ByteBuffer> build(@NonNull MultiModelLoaderFactory multiFactory) {
        return new DESUrlModelLoader(context);
    }


    @Override
    public void teardown() {

    }
}
