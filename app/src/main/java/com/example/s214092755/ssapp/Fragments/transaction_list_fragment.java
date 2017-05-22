package com.example.s214092755.ssapp.Fragments;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.s214092755.ssapp.Controllers.transactionAdapter;
import com.example.s214092755.ssapp.DatabaseHelper;
import com.example.s214092755.ssapp.Models.Transaction;
import com.example.s214092755.ssapp.Controllers.transactionController;
import com.example.s214092755.ssapp.R;

import java.io.IOException;

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
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_list_fragment, container, false);

        //Get transaction list populated from previous fragment
        Bundle bundle = getArguments();
        Transaction transaction = null;
        if(bundle!=null){
            transaction = (Transaction) bundle.getSerializable("transaction");
        }

        transactionAdapter transactionAdapter = new transactionAdapter(getContext(),new Transaction[4]);

        ListView listView = (ListView)view.findViewById(R.id.trans_list);
        listView.setAdapter(transactionAdapter);


        dbh = new DatabaseHelper(getContext());
        try {
            dbh.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        transactionController = new transactionController(dbh,getContext());
        transactionController.addTransaction(transaction);

        Toast.makeText(getContext(),"Added",Toast.LENGTH_LONG).show();


        return view;
    }

}
