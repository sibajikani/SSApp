package com.example.s214092755.ssapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import layout.merch_fragment;
import layout.supps_fragment;

/**
 * Created by s214092755 on 2017/05/11.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return  new supps_fragment();
            case 1: return new merch_fragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 2;
    }
}
