package com.mail.myapplication.ui.glidemoudle;


import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Synthetic;
import com.zhy.base.cache.disk.DiskLruCacheHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Map;

public class DESHttpUrlFetcher implements DataFetcher<ByteBuffer> {

    private static final String TAG = "HttpUrlFetcher";
    private static final int MAXIMUM_REDIRECTS = 5;
    private DiskLruCacheHelper diskLruCacheHelper;

    @VisibleForTesting
    static final HttpUrlConnectionFactory DEFAULT_CONNECTION_FACTORY = new DefaultHttpUrlConnectionFactory();
    /**
     * Returned when a connection error prevented us from receiving an http error.
     */
    private static final int INVALID_STATUS_CODE = -1;
    private final String url;
    private final int timeout;
    private final HttpUrlConnectionFactory connectionFactory;
    private HttpURLConnection urlConnection;
    private InputStream stream;
    private volatile boolean isCancelled;
    private Context context;

    public DESHttpUrlFetcher(String glideUrl, int timeout, Context context) {
        this(glideUrl, timeout, DEFAULT_CONNECTION_FACTORY);
        this.context = context;
        try {
            diskLruCacheHelper = new DiskLruCacheHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @VisibleForTesting
    DESHttpUrlFetcher(String url, int timeout, HttpUrlConnectionFactory connectionFactory) {
        this.url = url;
        this.timeout = timeout;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void loadData(@NonNull Priority priority, @NonNull DataCallback<? super ByteBuffer> callback) {
        GlideUrl glideUrl = new GlideUrl(url);
        long startTime = LogTime.getLogTime();
        try {

//            String asString = diskLruCacheHelper.getAsString(url);
//            if (TextUtils.isEmpty(asString)){
//                ByteBuffer result = loadDataWithRedirects(glideUrl.toURL(), 0, null, glideUrl.getHeaders());
//                callback.onDataReady(result);
//            }else{
////                return getStreamForCache(asString);
//                callback.onDataReady(getStreamForCache(asString));
//            }

            ByteBuffer result = loadDataWithRedirects(glideUrl.toURL(), 0, null, glideUrl.getHeaders());
            callback.onDataReady(result);

        } catch (IOException e) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Failed to load data for url", e);
            }
            callback.onLoadFailed(e);
        } finally {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Finished http url fetcher fetch in " + LogTime.getElapsedMillis(startTime));
            }
        }
    }

    public ByteBuffer loadDataWithRedirects(URL url, int redirects, URL lastUrl, Map<String, String> headers) throws IOException {
        if (redirects >= MAXIMUM_REDIRECTS) {
            throw new HttpException("Too many (> " + MAXIMUM_REDIRECTS + ") redirects!");
        } else {
            // Comparing the URLs using .equals performs additional network I/O and is generally broken. // See http://michaelscharf.blogspot.com/2006/11/javaneturlequals-and-hashcode-make.html.
            try {
                if (lastUrl != null && url.toURI().equals(lastUrl.toURI())) {
                    throw new HttpException("In re-direct loop");
                }
            } catch (URISyntaxException e) {
                // Do nothing, this is best effort.
            }
        }
        urlConnection = connectionFactory.build(url);
        for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
            urlConnection.addRequestProperty(headerEntry.getKey(), headerEntry.getValue());
        }
        urlConnection.setConnectTimeout(timeout);
        urlConnection.setReadTimeout(timeout);
        urlConnection.setUseCaches(false);
        urlConnection.setDoInput(true);
        // Stop the urlConnection instance of HttpUrlConnection from following redirects so that
        // redirects will be handled by recursive calls to this method, loadDataWithRedirects.
        urlConnection.setInstanceFollowRedirects(false);
        // Connect explicitly to avoid errors in decoders if connection fails.
        urlConnection.connect(); // Set the stream so that it's closed in cleanup to avoid resource leaks. See #2352.
        stream = urlConnection.getInputStream();
        if (isCancelled) {
            return null;
        }
        final int statusCode = urlConnection.getResponseCode();
        if (isHttpOk(statusCode)) {
            return getStreamForSuccessfulRequest(urlConnection);
        } else if (isHttpRedirect(statusCode)) {
            String redirectUrlString = urlConnection.getHeaderField("Location");
            if (TextUtils.isEmpty(redirectUrlString)) {
                throw new HttpException("Received empty or null redirect url");
            }
            URL redirectUrl = new URL(url, redirectUrlString);
            // Closing the stream specifically is required to avoid leaking ResponseBodys in addition
            // to disconnecting the url connection below. See #2352.
            cleanup();
            return loadDataWithRedirects(redirectUrl, redirects + 1, url, headers);
        } else if (statusCode == INVALID_STATUS_CODE) {
            throw new HttpException(statusCode);
        } else {
            throw new HttpException(urlConnection.getResponseMessage(), statusCode);
        }
    }

    // Referencing constants is less clear than a simple static method.
    private static boolean isHttpOk(int statusCode) {
        return statusCode / 100 == 2;
    }

    // Referencing constants is less clear than a simple static method.
    private static boolean isHttpRedirect(int statusCode) {
        return statusCode / 100 == 3;
    }

    private ByteBuffer getStreamForSuccessfulRequest(HttpURLConnection urlConnection) throws IOException {
        if (TextUtils.isEmpty(urlConnection.getContentEncoding())) {
            int contentLength = urlConnection.getContentLength();
            stream = ContentLengthInputStream.obtain(urlConnection.getInputStream(), contentLength);
        } else {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Got non empty content encoding: " + urlConnection.getContentEncoding());
            }
            stream = urlConnection.getInputStream();
        }

        //创建一个BufferedReader，去读取结果流
        BufferedReader reader=new BufferedReader(new InputStreamReader(stream));
        String readLine;
        StringBuffer buffer=new StringBuffer();
        while ((readLine=reader.readLine())!=null) {
            buffer.append(readLine);
        }
        String result=buffer.toString();

        result= result.replaceAll("molixg:image/","data:image/");
        result=result.replaceAll("molixg","base64");

        String base64Section = getBase64SectionOfModel(result);
        diskLruCacheHelper.put(url,base64Section);

        byte[] data = Base64.decode(base64Section, Base64.DEFAULT);
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
//        callback.onDataReady(byteBuffer);
        return byteBuffer;
    }

      private  ByteBuffer getStreamForCache(String result){
          byte[] data = Base64.decode(result, Base64.DEFAULT);
          ByteBuffer byteBuffer = ByteBuffer.wrap(data);
          return byteBuffer;
      }
    //
    private String getBase64SectionOfModel(String data) {
        // See https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/Data_URIs.
        int startOfBase64Section = data.indexOf(',');
        return data.substring(startOfBase64Section + 1);
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                // Ignore
            }
        }
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        urlConnection = null;
    }

    @Override
    public void cancel() {
        // TODO: we should consider disconnecting the url connection here, but we can't do so
        // directly because cancel is often called on the main thread.
        isCancelled = true;
    }

    @NonNull
    @Override
    public Class<ByteBuffer> getDataClass() {
        return ByteBuffer.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }

    interface HttpUrlConnectionFactory {
        HttpURLConnection build(URL url) throws IOException;
    }

    private static class DefaultHttpUrlConnectionFactory implements HttpUrlConnectionFactory {
        @Synthetic
        DefaultHttpUrlConnectionFactory() {
        }

        @Override
        public HttpURLConnection build(URL url) throws IOException {
            return (HttpURLConnection) url.openConnection();
        }
    }

}
