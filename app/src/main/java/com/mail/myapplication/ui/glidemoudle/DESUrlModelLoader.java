package com.mail.myapplication.ui.glidemoudle;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Map;

public class DESUrlModelLoader implements ModelLoader<DesLoaderUrl, ByteBuffer> {


    private Context context;

    public DESUrlModelLoader(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public LoadData<ByteBuffer> buildLoadData(@NonNull DesLoaderUrl model, int width, int height, @NonNull Options options) {

        LoadData loadData = new LoadData((Key) model, new DESHttpUrlFetcher(model.getUrl(), 2500, context) {
            @Override
            public ByteBuffer loadDataWithRedirects(URL url, int redirects, URL lastUrl, Map<String, String> headers) throws IOException {
                return super.loadDataWithRedirects(url, redirects, lastUrl, headers);
            }
        });
        return loadData;
    }

    @Override
    public boolean handles(@NonNull DesLoaderUrl desLoaderUrl) {
        return true;
    }
}
