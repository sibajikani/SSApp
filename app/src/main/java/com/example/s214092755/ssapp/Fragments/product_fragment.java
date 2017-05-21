package com.example.s214092755.ssapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.s214092755.ssapp.Models.Merchandise;
import com.example.s214092755.ssapp.Models.Supplement;
import com.example.s214092755.ssapp.Models.Transaction;
import com.example.s214092755.ssapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class product_fragment extends Fragment
{


    public product_fragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.product, container, false);


        //extract views for Product Name, Type, Size, Flavor/Color, StockOnHand, UnitPrice, Quantity, Total Price
        final TextView ProductName = (TextView)view.findViewById(R.id.txtProdName);
        TextView Type = (TextView)view.findViewById(R.id.txtType);
        TextView Size = (TextView)view.findViewById(R.id.txtSize);
        TextView FlavColor = (TextView)view.findViewById(R.id.txtFlavourColour);
        final TextView StockOnHand = (TextView)view.findViewById(R.id.txtStock);
        final TextView UnitPrice = (TextView)view.findViewById(R.id.txtUnitPrice);
        final TextView Quantity = (TextView)view.findViewById(R.id.txtQuantity);
        final TextView TotalPrice = (TextView)view.findViewById(R.id.txtTotalPrice);


        //FIll the textviews with the fields of the product passed through in this fragment's bundle


        //Extract Add, Subtract imageviews and Cancel, AddToCart buttons
        ImageView Add = (ImageView)view.findViewById(R.id.imgAdd);
        ImageView Subtract = (ImageView)view.findViewById(R.id.imgSubtract);
        Button Cancel = (Button)view.findViewById(R.id.btnpCancel);
        Button AddToCart = (Button)view.findViewById(R.id.btnpAddToCart);


        //create onclick listeners for extracted views

        //Cancel button
        Cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //Return to products fragment
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frag_container,getFragmentManager().findFragmentByTag("home_frag"));
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        //Add to cart button
        AddToCart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                //create bundle for values and switch to current_order_frag
                //record product name, unit price, quantity, total cost
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                //get product bundle
                Fragment product_fragment = getFragmentManager().findFragmentByTag("product_fragment");
                Bundle Prod_bundle = product_fragment.getArguments();

                //Fragment to send product info to and it's corresponding bundle
                Fragment cur_order_frag = getFragmentManager().findFragmentByTag("current_order_fragment");
                Bundle Order_bundle =  cur_order_frag.getArguments();

                //check if the product selected is a supplement or merchandise item

                //Supplement
                if (Prod_bundle.get("S")!=null)
                {
                    //get current bundle from the order and then add the new product to the order fragment's bundle
                    int SupplementsOrdered = 0;

                    for (String str : Order_bundle.keySet())
                    {
                        if (str.startsWith("S"))
                            SupplementsOrdered++;
                    }

                    SupplementsOrdered++;
                    Supplement newSup = (Supplement)Order_bundle.get("S");
                    Order_bundle.putSerializable("S:" + SupplementsOrdered, newSup);

                    //create new transaction to record the quantity selected
                    //Current date
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String curDate = df.format(c.getTime());
                    Transaction transaction = new Transaction(newSup.getID(),Integer.parseInt(Quantity.getText().toString()),0);
                    Order_bundle.putSerializable("TS:" + SupplementsOrdered, transaction);
                }

                //Merchandise
                else
                {
                    //get current bundle from the order and then add the new product to the order fragment's bundle
                    int MerchandiseOrdered = 0;

                    for (String str : Order_bundle.keySet())
                    {
                        if (str.startsWith("M"))
                            MerchandiseOrdered++;
                    }
                    MerchandiseOrdered++;
                    Merchandise newMerch = (Merchandise) Order_bundle.get("M");
                    Order_bundle.putSerializable("M:" + MerchandiseOrdered, newMerch);

                    //create new transaction to record the quantity selected
                    //Current date
                    assert newMerch != null;
                    Transaction transaction = new Transaction(newMerch.getID(),Integer.parseInt(Quantity.getText().toString()),0);
                    Order_bundle.putSerializable("TM:" + MerchandiseOrdered, transaction);

                }

                cur_order_frag.setArguments(Order_bundle);
                ft.replace(R.id.frag_container,getFragmentManager().findFragmentByTag("current_order_frag"));
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        //Subtract imageview
        Subtract.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //Reduce the quantity selected
                Integer cur_quantity = Integer.parseInt(Quantity.getText().toString());

                if (cur_quantity > 1)
                {
                    cur_quantity = cur_quantity -1;
                }

                Quantity.setText(cur_quantity.toString());

                //Update the total price textview
                Integer unit_price = Integer.parseInt(UnitPrice.getText().toString());
                Integer total = unit_price* cur_quantity;
                TotalPrice.setText(total.toString());

            }
        });

        //Add imageview
        Add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //Increase the quantity selected
                Integer cur_quantity = Integer.parseInt(Quantity.getText().toString());
                Integer onHand = Integer.parseInt(StockOnHand.getText().toString());

                if (cur_quantity < onHand)
                {
                    cur_quantity = cur_quantity + 1;
                }

                Quantity.setText(cur_quantity.toString());

                //Update the total price textview
                Integer unit_price = Integer.parseInt(UnitPrice.getText().toString());
                Integer total = unit_price* cur_quantity;
                TotalPrice.setText(total.toString());

            }
        });



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
