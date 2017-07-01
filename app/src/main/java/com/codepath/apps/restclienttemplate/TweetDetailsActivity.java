package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    ImageView mediaD;
    TextView retweet;
    TextView likes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        context = this;

        tvUserName = (TextView) findViewById(R.id.user);
        tvBody = (TextView) findViewById(R.id.text);
        screenName= (TextView) findViewById(R.id.screen);
        ProfileImage = (ImageView) findViewById(R.id.pImage);
        mediaD = (ImageView) findViewById(R.id.mediaD);
        retweet = (TextView) findViewById(R.id.rtCount);
        likes = (TextView) findViewById(R.id.likeCount);


        tweet =  Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        //set the stuff
        tvUserName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        screenName.setText("@" + tweet.getUser().getScreenName());
        retweet.setText(String.valueOf(tweet.getRetweetCount()));
        likes.setText(String.valueOf(tweet.getFavoritesCount()));

        Glide.with(context)
                .load(tweet.getUser().getProfileImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(context, 10, 0))
                .into(ProfileImage);

        //set media image
        if (tweet.getLmediaURL() != null) {
            Glide.with(context)
                    .load(tweet.getLmediaURL())
                    .bitmapTransform(new RoundedCornersTransformation(context, 15, 0))
                    .into(mediaD);
        } else {
            mediaD.setVisibility(View.GONE);
        }


    }
}
