package com.github.silverAndroid.gallery;

import android.app.Application;
import android.content.AsyncQueryHandler;
import android.database.Cursor;
import android.net.Uri;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by silver_android on 26/10/16.
 */

public class MyApplication extends Application {

    private NetworkService networkService;

    @Override
    public void onCreate() {
        super.onCreate();
        networkService = new NetworkService();

        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .setRequestListeners(requestListeners)
                .build();
        Fresco.initialize(this, config);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
    }

    public NetworkService getNetworkService() {
        return networkService;
    }
}
