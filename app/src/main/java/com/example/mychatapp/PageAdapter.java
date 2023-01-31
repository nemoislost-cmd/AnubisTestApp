package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
//this class will manage all the fragments
public class PageAdapter extends FragmentPagerAdapter {

    int tab_count;

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tab_count=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                return new chatFragment();
            case 1:
                return new statusFragment();
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return tab_count;
    }
}
