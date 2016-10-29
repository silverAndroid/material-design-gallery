package com.github.silverAndroid.gallery;

import android.app.Application;

/**
 * Created by silver_android on 26/10/16.
 */

public class MyApplication extends Application {

    private NetworkService networkService;

    @Override
    public void onCreate() {
        super.onCreate();
        networkService = new NetworkService();
    }

    public NetworkService getNetworkService() {
        return networkService;
    }
}
