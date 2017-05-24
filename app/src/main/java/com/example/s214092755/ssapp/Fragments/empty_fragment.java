package com.example.s214092755.ssapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.s214092755.ssapp.MainActivity;
import com.example.s214092755.ssapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class empty_fragment extends Fragment {


    public empty_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Current Order List");
        return inflater.inflate(R.layout.fragment_empty_fragment, container, false);
    }

}
