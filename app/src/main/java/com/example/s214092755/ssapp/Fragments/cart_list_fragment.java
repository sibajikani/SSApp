package com.example.s214092755.ssapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.s214092755.ssapp.Controllers.transactionAdapter;
import com.example.s214092755.ssapp.MainActivity;
import com.example.s214092755.ssapp.Models.Merchandise;
import com.example.s214092755.ssapp.Models.Product;
import com.example.s214092755.ssapp.Models.Supplement;
import com.example.s214092755.ssapp.Models.Transaction;
import com.example.s214092755.ssapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class cart_list_fragment extends Fragment
{

    //List of transactions for current order
    private ArrayList<Transaction> Currrent_Order;

    //List of supplements
    private ArrayList<Supplement> Supplements;

    //List of merchandise
    private ArrayList<Merchandise> Merchandise;


    public cart_list_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_list_fragment, container, false);


        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Current Order List");

        ListView listView = (ListView) view.findViewById(R.id.cart_list);

        MainActivity main = (MainActivity) getActivity();
        //Extract list of transactions in current order
        Currrent_Order = main.getCurrrent_Order();

        //Extract list of supplements
        Supplements = main.getSupplements();

        Merchandise = main.getMerchandise();

        //Extract list of merchandise
        Merchandise = main.getMerchandise();

        final ArrayList<Pair<Product,Transaction>> pairs = new ArrayList<>();

        Product curProduct = null;

        //runs through transactions in current order and populate list view
        for (Transaction t : Currrent_Order) {
            //Check for supplement or merchandise
            if (t.getType().equals("supp")) {
                for (Supplement supp : Supplements) {
                    if (supp.getID().equals(t.getpID())) {
                        curProduct = supp;
                    }
                }
            } else {
                for (Merchandise merchandise : Merchandise) {
                    if (merchandise.getID().equals(t.getpID())) {
                        curProduct = merchandise;
                    }
                }
            }
            Pair<Product,Transaction> pair = new Pair<>(curProduct,t);
            pairs.add(pair);
        }

        transactionAdapter transactionAdapter = new transactionAdapter(getContext(), pairs,"cart", (MainActivity) getActivity());

       listView.setAdapter(transactionAdapter);

       view.findViewById(R.id.buttonProcess).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FragmentManager fragmentManager = getFragmentManager();
               FragmentTransaction transaction = fragmentManager.beginTransaction();
               Bundle bundle = new Bundle();
               bundle.putSerializable("transPackage",pairs);
               Fragment fragment = new transaction_list_fragment();
               fragment.setArguments(bundle);
               transaction.addToBackStack(null);
               transaction.replace(R.id.frag_container,fragment).commit();
               Currrent_Order.clear();
           }
       });


        return view;

    }

}
