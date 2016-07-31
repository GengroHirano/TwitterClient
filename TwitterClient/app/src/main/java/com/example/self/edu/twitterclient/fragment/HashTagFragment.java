package com.example.self.edu.twitterclient.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.self.edu.twitterclient.R;
import com.example.self.edu.twitterclient.SearchResultActivity;
import com.example.self.edu.twitterclient.databinding.FragmentHashTagBinding;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;


public class HashTagFragment extends Fragment {

  public static HashTagFragment newInstance() {
    return new HashTagFragment();
  }

  public HashTagFragment() {
    // Required empty public constructor
  }

  private FragmentHashTagBinding mBinding;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_hash_tag, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mBinding = FragmentHashTagBinding.bind(getView());

    //こんなもんはLibraryに任せちまえばいいんだよ！！
    String searchWord = getActivity().getIntent().getStringExtra(SearchResultActivity.SEARCH_KEY);
    SearchTimeline timeline = new SearchTimeline.Builder()
            .query("#"+searchWord)
            .build();
    TweetTimelineListAdapter timelineListAdapter = new TweetTimelineListAdapter
            .Builder(getContext())
            .setTimeline(timeline)
            .setOnActionCallback(new Callback<Tweet>() {
              @Override
              public void success(Result<Tweet> result) {}

              @Override
              public void failure(TwitterException exception) {
                mBinding.errorText.setText("ハッシュタグでの検索に失敗しちったごめんね");
                mBinding.progress.setVisibility(View.GONE);
              }
            })
            .build();
    mBinding.hashTagList.setAdapter(timelineListAdapter);
  }
}
