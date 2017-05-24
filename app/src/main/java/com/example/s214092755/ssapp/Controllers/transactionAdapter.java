package com.example.s214092755.ssapp.Controllers;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s214092755.ssapp.MainActivity;
import com.example.s214092755.ssapp.Models.Merchandise;
import com.example.s214092755.ssapp.Models.Product;
import com.example.s214092755.ssapp.Models.Supplement;
import com.example.s214092755.ssapp.Models.Transaction;
import com.example.s214092755.ssapp.R;

import java.util.ArrayList;

/**
 * Created by s214092755 on 2017/05/18.
 */

public class transactionAdapter extends ArrayAdapter<Pair<Product,Transaction>> {
    private ArrayList<Pair<Product,Transaction>> pairs;
    private String type;
    private MainActivity activity;

    public transactionAdapter(@NonNull Context context, @NonNull ArrayList<Pair<Product,Transaction>> pairs, String type, MainActivity activity) {
        super(context, 0, pairs);
        this.pairs = pairs;
        this.type = type;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pair curPair = getItem(position);
        assert curPair != null;
        Transaction transaction = (Transaction) curPair.second;
        Product product = (Product)curPair.first;

        if (convertView == null) {
            if(type.equals("cart"))
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_cart, parent, false);
            else
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_transaction, parent, false);
        }

        // Lookup view for data population
        TextView detailsText = (TextView) convertView.findViewById(R.id.details_trans_text);
        TextView payText = (TextView) convertView.findViewById(R.id.pay_trans_text);
        String detailsString = "Details";
        // Populate the data into the template view using the data object
        if(product.getType().equals("supplement")) {
            product = (Supplement) curPair.first;

            detailsString = "Bought "
                    .concat(String.valueOf(transaction.getQuantity()))
                    .concat(" ")
                    .concat(product.getName())
                    .concat(" ")
                    .concat(((Supplement)product).getSize());
        }
        if(product.getType().equals("merchandise")) {
            product = (Merchandise) curPair.first;

            detailsString = "Bought "
                    .concat(String.valueOf(transaction.getQuantity()))
                    .concat(" ")
                    .concat(product.getName())
                    .concat(" ")
                    .concat(((Merchandise)product).getSize());

        }

        detailsText.setText(detailsString);
        String payString = "Paid R".concat(String.valueOf(transaction.getQuantity()*product.getUnitPrice()))
               .concat(" on ").concat(String.valueOf(transaction.getDate()));
        payText.setText(payString);
        // Return the completed view to render on screen
        if(type.equals("cart")) {
            convertView.findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pairs.remove(position);
                    notifyDataSetChanged();
                    activity.getCurrrent_Order().remove(position);
                }
            });
        }
        return convertView;
    }
}
