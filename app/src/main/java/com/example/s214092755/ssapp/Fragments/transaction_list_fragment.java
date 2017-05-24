package com.example.s214092755.ssapp.Fragments;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.s214092755.ssapp.Controllers.transactionAdapter;
import com.example.s214092755.ssapp.DatabaseHelper;
import com.example.s214092755.ssapp.MainActivity;
import com.example.s214092755.ssapp.Models.Merchandise;
import com.example.s214092755.ssapp.Models.Product;
import com.example.s214092755.ssapp.Models.Supplement;
import com.example.s214092755.ssapp.Models.Transaction;
import com.example.s214092755.ssapp.Controllers.transactionController;
import com.example.s214092755.ssapp.Models.User;
import com.example.s214092755.ssapp.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class transaction_list_fragment extends Fragment {

    transactionController transactionController;
    private DatabaseHelper dbh;
    public transaction_list_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_list_fragment, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Transaction History");

        //Get transaction list populated from previous fragment
        Bundle bundle = getArguments();
        ArrayList<Pair<Product,Transaction>> pairs = new ArrayList<>();
        if(bundle!=null){
            pairs = (ArrayList<Pair<Product, Transaction>>) bundle.getSerializable("transPackage");
        }

        /*transactionAdapter transactionAdapter = new transactionAdapter(getContext(),pairs,"trans", (MainActivity) getActivity());

        ListView listView = (ListView)view.findViewById(R.id.trans_list);
        listView.setAdapter(transactionAdapter);
*/

        //current user
        User curUser = ((MainActivity)getActivity()).getCurUser();
        if(curUser == null){
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frag_container,new login_fragment()).commit();
            Toast.makeText(getContext(),"Log in first", Toast.LENGTH_SHORT).show();

        }

        //Add to database
        dbh = new DatabaseHelper(getContext());
        try {
            dbh.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        transactionController = new transactionController(dbh,getContext());
        for(Pair pair: pairs){
            transactionController.addTransaction((Transaction) pair.second);
        }


        dbh.close();
        dbh = new DatabaseHelper(getContext());



        //clear pairs, transactionAdapter
        pairs.clear();

        //supplement list
        ArrayList<Supplement> Supplements = ((MainActivity)getActivity()).getSupplements();

        //merchandise list
        ArrayList<Merchandise> Merchandise = ((MainActivity)getActivity()).getMerchandise();
        if(curUser!=null) {

            //get all transactions for current user
            MainActivity main = (MainActivity) getActivity();
            ArrayList<Transaction> transactions = transactionController.getTransactions(main.getCurUser());
            Product curProduct = null;
            for (Transaction t : transactions) {
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
                Pair<Product, Transaction> pair = new Pair<>(curProduct, t);
                pairs.add(pair);
            }

            transactionAdapter transactionAdapter = new transactionAdapter(getContext(), pairs, "trans", (MainActivity) getActivity());

            ListView listView = (ListView) view.findViewById(R.id.trans_list);
            listView.setAdapter(transactionAdapter);

            transactionAdapter.notifyDataSetChanged();
        }


        return view;
    }

}
