package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by mayc on 7/7/17.
 */

public class SearchPagerAdapter  extends SmartFragmentStatePagerAdapter {
    private String tabTitles[] = new String[] {"Search"};
    private Context context;
    Fragment fragment;

    public SearchPagerAdapter(SearchTweetsFragment hello, FragmentManager fm, Context context) {
        super(fm);
        this.fragment = hello;
        this.context = context;
    }

    public int getCount() {
        return 1;
    }

    public Fragment getItem(int position) {
        if (position == 0) {
            return fragment;
        } else {
            return null;
        }
    }

    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
