package com.nguyen.flickster;

import android.app.Application;

public class MyApplication extends Application {

    AppComponent appComponent = DaggerAppComponent.create();
}
