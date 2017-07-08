package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mayc on 7/6/17.
 */

public class SearchTweetsFragment extends TweetsListFragment {

    private TwitterClient client;
    String query;
    String encodedurl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //client = TwitterApp.getRestClient();
        query = getArguments().getString("query");
        getSearch(query);
    }


   // function that will take search and fetch stuff
    public void getSearch(String q) {
        client = TwitterApp.getRestClient();
        try {
            encodedurl = URLEncoder.encode(q,"UTF-8");
            Log.d("TEST", encodedurl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.getsearchTweets(encodedurl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                JSONArray response = null;
                try {
                    response = object.getJSONArray("statuses");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                addItems(response);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }

    public static SearchTweetsFragment newInstance(String q) {
        SearchTweetsFragment f = new SearchTweetsFragment();
        Bundle args = new Bundle();
        args.putString("query", q);
        f.setArguments(args);
        f.getSearch(q);
        return f;
    }
}




