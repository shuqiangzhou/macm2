package com.mail.myapplication.ui.glidemoudle;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.Key;

import java.security.MessageDigest;

public class DesLoaderUrl implements  Key{
    
    
    private String url;

    public DesLoaderUrl(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
