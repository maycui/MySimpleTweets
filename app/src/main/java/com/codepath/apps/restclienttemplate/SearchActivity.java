package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.restclienttemplate.fragments.SearchPagerAdapter;
import com.codepath.apps.restclienttemplate.fragments.SearchTweetsFragment;

public class SearchActivity extends AppCompatActivity {

    String query;
    SearchPagerAdapter adapterViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        query = getIntent().getStringExtra("query");
        getSupportActionBar().setTitle(query);


//        Bundle bundle = new Bundle();
//        bundle.putString("query", query);
//        SearchTweetsFragment hello = new SearchTweetsFragment();
//        hello.setArguments(bundle);


        SearchTweetsFragment search = SearchTweetsFragment.newInstance(query);
//        fragmentTransaction.add(R.id.FragmentContainer, fragment);
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        adapterViewPager = new SearchPagerAdapter(search, getSupportFragmentManager(), this);
        vpPager.setAdapter(adapterViewPager);

    }


}
