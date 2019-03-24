package com.example.ryan.ign_code_foo_2019_android_app;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"Articles", "Videos"};


    public FragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ArticleFragment();
        } else if (position == 1) {
            return new VideoFragment();
        } else {
            return new VideoFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
