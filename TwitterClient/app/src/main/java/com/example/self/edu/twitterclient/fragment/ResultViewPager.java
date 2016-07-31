package com.example.self.edu.twitterclient.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * タブに使うフラグメントとかの管理はViewPagerで行う
 */
public class ResultViewPager extends FragmentPagerAdapter {

  public ResultViewPager(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0: return WordFragment.newInstance();
      default: return HashTagFragment.newInstance();
    }
  }

  @Override
  public int getCount() {
    return 2;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    switch (position) {
      case 0: return "単語検索";
      default: return "ハッシュタグ";
    }
  }
}
