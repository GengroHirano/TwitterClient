package com.example.self.edu.twitterclient.databases;

import io.realm.RealmObject;

public class History extends RealmObject{

  String searchWord;

  public String getSearchWord() {
    return searchWord;
  }

  public void setSearchWord(String searchWord) {
    this.searchWord = searchWord;
  }

}
