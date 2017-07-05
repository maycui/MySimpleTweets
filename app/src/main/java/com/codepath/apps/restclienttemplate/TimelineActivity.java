package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TimelineActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.miCompose:
                Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
                startActivityForResult(i, REQUEST_CODE);
                return true;
            case R.id.miProfile:
                //launch profile view
                Intent it = new Intent(this, ProfileActivity.class);
                startActivity(it);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            Tweet newTweet = Parcels.unwrap(data.getParcelableExtra("newTweet"));
            //Tweet newTweet = getIntent().getParcelableExtra("newTweet");
//            tweets.add(0, newTweet);
//            tweetAdapter.notifyItemInserted(0);
//            rvTweets.scrollToPosition(0);
            // Toast the name to display temporarily on screen
            Toast.makeText(this, newTweet.body, Toast.LENGTH_LONG).show();
        }
    }

//    public void receiveRt(Tweet rT) {
//        //Tweet newTweet = Parcels.unwrap(getIntent().getParcelableExtra("retweeted"));
//        //Tweet newTweet = getIntent().getParcelableExtra("newTweet");
//        tweets.add(0, rT);
//        tweetAdapter.notifyItemInserted(0);
//        rvTweets.scrollToPosition(0);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set the adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), this));
        //setup the TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);


        //swipe
//        swipeContainer = (SwipeRefreshLayout) findViewById(swipeContainer);
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                fetchTimelineAsync(0);
//            }
//        });
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright);

        //Tweet rT = Parcels.unwrap(getIntent().getParcelableExtra("retweeted"));
        //receiveRt(rT);

    }

//    public void fetchTimelineAsync(int page) {
//        tweetAdapter.clear();
//        populateTimeline();
//        swipeContainer.setRefreshing(false);
//    }


}
