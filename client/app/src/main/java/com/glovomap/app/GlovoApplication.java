package com.glovomap.app;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.graphics.drawable.BuildConfig;
import android.support.multidex.MultiDexApplication;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.glovomap.di.ApplicationDependency;
import com.glovomap.di.components.ApplicationComponent;
import com.glovomap.di.components.ComponentHolder;
import com.glovomap.utils.SharedPreferencesUtils;
import io.fabric.sdk.android.Fabric;

public class GlovoApplication extends MultiDexApplication {

    private static Context context = null;
    public static String TAG = GlovoApplication.class.getSimpleName();
    public static ApplicationComponent graph;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        ComponentHolder holder = ComponentHolder.INSTANCE;
        graph.inject(holder);
//        if (!BuildConfig.DEBUG) {
//            Log.e(GlovoApplication.class.getSimpleName(), "Crashlytics ON");
            Fabric.with(this, new Crashlytics());
//        } else {
//            Log.e(GlovoApplication.class.getSimpleName(), "Crashlytics OFF");
//        }

        this.TAG = this.getClass().getSimpleName();

        //Global context of app
        GlovoApplication.context = getApplicationContext();
        SharedPreferencesUtils.init(this);
    }

    private void initializeInjector() {
        graph = new ApplicationDependency().getApplicationComponent(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return graph;
    }

    public static Context getAppContext() {
        return GlovoApplication.context;
    }

    public static boolean isAccessFineLocationPermissionGranted() {
        int permission = ContextCompat.checkSelfPermission(
                GlovoApplication.getAppContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);

        return permission == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isAccessCoarseLocationPermissionGranted() {
        int permission = ContextCompat.checkSelfPermission(
                GlovoApplication.getAppContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return permission == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isReadExternalStoragePermissionGranted() {
        int permission = ContextCompat.checkSelfPermission(
                GlovoApplication.getAppContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        return permission == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isWriteExternalStoragePermissionGranted() {
        int permission = ContextCompat.checkSelfPermission(
                GlovoApplication.getAppContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return permission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        switch (level) {
            case TRIM_MEMORY_COMPLETE:
                this.tryToCleanMemory();
                break;

            case TRIM_MEMORY_UI_HIDDEN:
                this.tryToCleanMemory();
                break;

            case TRIM_MEMORY_RUNNING_CRITICAL:
                this.cleanMemoryCache();
                break;
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory");
        this.cleanMemoryCache();
    }

    private void tryToCleanMemory() {
        this.cleanMemoryCache();
        System.gc();
    }

    private void cleanMemoryCache() {
        //TODO: Clean memory
    }
}
