package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.fragments.HomeTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TimelineActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;
    MenuItem miActionProgressItem;
    TweetsPagerAdapter adapterViewPager;
    Tweet updateTweet;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        v.setIndeterminate(true);
        v.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);


        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent qu = new Intent(TimelineActivity.this, SearchActivity.class);
                qu.putExtra("query", query);
                startActivity(qu);
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miCompose:
                Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
                startActivityForResult(i, 20);
                return true;
            case R.id.miProfile:
                //launch profile view
                Intent it = new Intent(this, ProfileActivity.class);
                startActivity(it);
        }
        return true;
    }

    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }
//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Tweet newTweet = Parcels.unwrap(data.getParcelableExtra("newTweet"));
            HomeTimelineFragment fragmentHomeTweets =
                    (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);
            fragmentHomeTweets.appendTweet(newTweet);
            // Toast the name to display temporarily on screen
            Toast.makeText(this, newTweet.body, Toast.LENGTH_LONG).show();
        }
    }


    public void newContentUpdate(Tweet t) {
        HomeTimelineFragment fragmentHomeTweets =
                (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);
        fragmentHomeTweets.appendTweet(t);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set the adapter for the pager
        //vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), this));
        adapterViewPager = new TweetsPagerAdapter(getSupportFragmentManager(), this);
        vpPager.setAdapter(adapterViewPager);

        //setup the TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

        updateTweet =  Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        if (updateTweet != null) {
            newContentUpdate(updateTweet);
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
//        //set the adapter for the pager
//        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), this));
//        //setup the TabLayout
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
//        tabLayout.setupWithViewPager(vpPager);
//    }


}
