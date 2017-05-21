package com.example.s214092755.ssapp.Controllers;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.s214092755.ssapp.Models.Transaction;
import com.example.s214092755.ssapp.R;

/**
 * Created by s214092755 on 2017/05/18.
 */

public class transactionAdapter extends ArrayAdapter<Transaction> {

    public transactionAdapter(@NonNull Context context, @NonNull Transaction[] objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Transaction transaction = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_transaction, parent, false);
        }
        // Lookup view for data population
        TextView detailsText = (TextView) convertView.findViewById(R.id.details_trans_text);
        TextView payText = (TextView) convertView.findViewById(R.id.pay_trans_text);
        // Populate the data into the template view using the data object
        assert transaction != null;
        /*String detailsString = "Bought "
                .concat(String.valueOf(transaction.getQuantity()))
                .concat(" Product name")
                .concat(" Product size");*/
        detailsText.setText("Details");
        int unitPrice=4;
//        String payString = "Paid R".concat(String.valueOf(transaction.getQuantity()*unitPrice))
//                .concat(" on").concat(String.valueOf(transaction.getDate()));
        payText.setText("payString");
        // Return the completed view to render on screen
        return convertView;
    }
}
