package com.example.self.edu.twitterclient;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TwitterClientApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    //initialize realm
    RealmConfiguration config = new RealmConfiguration.Builder(this).build();
    Realm.setDefaultConfiguration(config);
  }
}
