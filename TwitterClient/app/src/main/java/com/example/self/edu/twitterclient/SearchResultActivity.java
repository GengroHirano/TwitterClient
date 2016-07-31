package com.example.self.edu.twitterclient;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.self.edu.twitterclient.databinding.ActivitySearchResultBinding;
import com.example.self.edu.twitterclient.fragment.ResultViewPager;

public class SearchResultActivity extends AppCompatActivity {

  public static final String SEARCH_KEY = "searchKey";

  public static Intent getSearchResultIntent(Context context, String searchWord) {
    Intent intent = new Intent(context, SearchResultActivity.class);
    intent.putExtra(SEARCH_KEY, searchWord);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivitySearchResultBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search_result);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //initialize tab
    ResultViewPager adapter = new ResultViewPager(getSupportFragmentManager());
    ViewPager pager = binding.content.viewPager;
    TabLayout tabLayout = binding.content.tabLayout;
    pager.setAdapter(adapter);
    tabLayout.setupWithViewPager(pager);
  }

}
