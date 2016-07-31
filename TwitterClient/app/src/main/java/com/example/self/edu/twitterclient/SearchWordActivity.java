package com.example.self.edu.twitterclient;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.self.edu.twitterclient.databases.History;
import com.example.self.edu.twitterclient.databinding.ActivitySearchWordBinding;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.Iterator;

import io.realm.Realm;
import io.realm.RealmResults;

public class SearchWordActivity extends AppCompatActivity {

  public static Intent getSearchWordIntent(Context context) {
    return new Intent(context, SearchWordActivity.class);
  }

  private ActivitySearchWordBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_word);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //単語履歴リストのアイテムをクリックした時の挙動
    mBinding.content.searchHistoryList.setOnItemClickListener((adapterView, view1, i, l) -> {
      //DB書き込みはしなーい
      String searchWord = (String)adapterView.getItemAtPosition(i);
      next(searchWord);
    });

    mBinding.content.search.setOnClickListener(view -> {
      String searchWord = mBinding.content.editSearchWord.getText().toString();
      if (TextUtils.isEmpty(searchWord)) {
        mBinding.content.searchWordWrap.setError("なんかしらの単語は入れてほしいなぁ");
        return;
      }

      //DBへ書き込み
      Realm realm = Realm.getDefaultInstance();
      realm.beginTransaction();
      History history = realm.createObject(History.class);
      history.setSearchWord(searchWord);
      realm.commitTransaction();
      realm.close();

      //画面遷移
      next(searchWord);

    });
  }

  @Override
  protected void onResume() {
    super.onResume();

    //DBから読み出し
    Realm realm = Realm.getDefaultInstance();
    RealmResults<History> results = realm.where(History.class).findAll();
    ArrayList<String> list = new ArrayList<>();
    for (History result : results) {
      list.add(result.getSearchWord());
    }
    realm.close();

    //履歴がないなら空っぽってことをしっかり伝えないとね
    int visibility = list.size() == 0 ? View.VISIBLE : View.GONE;
    mBinding.content.emptyHistory.setVisibility(visibility);

    //履歴の中身をセット
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
    mBinding.content.searchHistoryList.setAdapter(adapter);
  }

  /**
   * //画面遷移
   * @param word 検索文字列
   */
  private void next(String word) {
    Intent intent = SearchResultActivity.getSearchResultIntent(SearchWordActivity.this, word);
    startActivity(intent);
  }
}
