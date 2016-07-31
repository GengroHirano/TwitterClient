package com.example.self.edu.twitterclient;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.self.edu.twitterclient.databinding.ActivityMainBinding;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

  // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
  private static final String TWITTER_KEY = "xxx";
  private static final String TWITTER_SECRET = "xxx";
  private ActivityMainBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
    Fabric.with(this, new Twitter(authConfig));
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    TwitterSession session = Twitter.getSessionManager().getActiveSession();
    if (session != null) {
      TwitterAuthToken authToken = session.getAuthToken();
      //ToKenが存在し、なおかつ有効期限が切れていなければ次の画面に遷移する。
      if (authToken != null && !authToken.isExpired()) {
        next();
      }
    }

    mBinding.twitterLoginButton.setCallback(new Callback<TwitterSession>() {
      @Override
      public void success(Result<TwitterSession> result) {
        next();
      }

      @Override
      public void failure(TwitterException exception) {
        Toast.makeText(MainActivity.this, "Login Filed", Toast.LENGTH_SHORT).show();
      }
    });

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    mBinding.twitterLoginButton.onActivityResult(requestCode, resultCode, data);
  }

  private void next() {
    Intent intent = SearchWordActivity.getSearchWordIntent(MainActivity.this);
    startActivity(intent);
    finish();
  }
}
