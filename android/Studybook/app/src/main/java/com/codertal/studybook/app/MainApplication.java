/*
 * Created by Talab Omar on 10/26/17 1:39 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/26/17 1:18 PM
 */

package com.codertal.studybook.app;

import android.app.Activity;
import android.app.Application;

import com.codertal.studybook.BuildConfig;
import com.codertal.studybook.di.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import timber.log.Timber;

public class MainApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();

        //Set up Dagger
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);

        //Set up Timber and ObjectBox data browser
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());

            new AndroidObjectBrowser(boxStore).start(this);
        }


        //Set up LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
