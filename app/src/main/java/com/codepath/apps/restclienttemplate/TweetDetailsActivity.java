package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailsActivity extends AppCompatActivity {

    Context context;
    Tweet tweet;
    TextView tvUserName;
    ImageView ProfileImage;
    TextView screenName;
    TextView tvBody;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        context = this;

        tvUserName = (TextView) findViewById(R.id.user);
        tvBody = (TextView) findViewById(R.id.text);
        screenName= (TextView) findViewById(R.id.screen);
        ProfileImage = (ImageView) findViewById(R.id.pImage);

        tweet =  Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        //set the stuff
        tvUserName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        screenName.setText("@" + tweet.getUser().getScreenName());

        Glide.with(context)
                .load(tweet.getUser().getProfileImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(context, 10, 0))
                .into(ProfileImage);

    }
}
