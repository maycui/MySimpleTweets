package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String screenName = getIntent().getStringExtra("screen_name");
        String otherScreenName = getIntent().getStringExtra("NAME");
        long userID = getIntent().getLongExtra("userID", 20);



        if (otherScreenName == null) {
            //create the user fragment
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
            //display the user timeline fragment inside the container
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, userTimelineFragment);
            ft.commit();
        } else {
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(otherScreenName);
            //display the user timeline fragment inside the container
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, userTimelineFragment);
            ft.commit();
        }

        client = TwitterApp.getRestClient();

        if (otherScreenName == null) {
            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        User user = User.fromJSON(response);
                        getSupportActionBar().setTitle(user.screenName);
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ;
                    }
                }
            });
        } else {
            client.getOtherUserInfo(String.valueOf(userID), otherScreenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        User user = User.fromJSON(response);
                        getSupportActionBar().setTitle(user.screenName);
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ;
                    }
                }
            });

        }
    }

    public void populateUserHeadline(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagLine);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.name);

        tvTagline.setText(user.getTagLine());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFollowingCount() + " Following");
        Glide.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);
    }
}
