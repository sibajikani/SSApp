package com.example.s214092755.ssapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import layout.merch_fragment;
import layout.supps_fragment;

public class MainSplash extends Fragment {
    private FragmentTabHost tabHost;

    public MainSplash() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main_splash, container, false);

        tabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        tabHost.addTab(tabHost.newTabSpec("supps_fragment").setIndicator("Supplements"), supps_fragment.class,null);
        tabHost.addTab(tabHost.newTabSpec("merch_fragment").setIndicator("Merchandise"), merch_fragment.class,null);

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
