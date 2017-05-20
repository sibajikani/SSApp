package com.example.s214092755.ssapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.s214092755.ssapp.Controllers.transactionAdapter;
import com.example.s214092755.ssapp.Models.Transaction;
import com.example.s214092755.ssapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class transaction_list_fragment extends Fragment {


    public transaction_list_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_list_fragment, container, false);

        //Get transaction list populated from previous fragment

        transactionAdapter transactionAdapter = new transactionAdapter(getContext(),new Transaction[4]);

        ListView listView = (ListView)view.findViewWithTag("transList");
        listView.setAdapter(transactionAdapter);


        return view;
    }

}
