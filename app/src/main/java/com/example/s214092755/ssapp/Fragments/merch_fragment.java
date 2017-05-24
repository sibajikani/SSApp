package com.example.s214092755.ssapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.s214092755.ssapp.Controllers.ImageAdapter;
import com.example.s214092755.ssapp.Controllers.ProductController;
import com.example.s214092755.ssapp.DatabaseHelper;
import com.example.s214092755.ssapp.MainActivity;
import com.example.s214092755.ssapp.Models.Duo;
import com.example.s214092755.ssapp.Models.Merchandise;
import com.example.s214092755.ssapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class merch_fragment extends Fragment
{

    private DatabaseHelper dbh;
    private ProductController productController;
    public merch_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merch_fragment, container, false);
        GridView gridView = (GridView)view.findViewById(R.id.gridview_merch);
        //ImageAdapter imageAdapter = new ImageAdapter(getContext(),)
        dbh = new DatabaseHelper(getContext());
        try {
            dbh.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        productController = new ProductController(dbh,getContext());

        final ArrayList<Merchandise> merchandise = new ArrayList<>();

        final Map<Integer,ArrayList<Merchandise>> mappedMerch = productController.getAllMerchandise();

        //set merchandise list in main activity
        productController.setMerchList((MainActivity)getActivity());

        //get sorted merch list
        ArrayList<Map.Entry<Integer,ArrayList<Merchandise>>> sortedMerch = productController.getMerchlist();

        for (Map.Entry<Integer,ArrayList<Merchandise>> entry : sortedMerch)
        {
            merchandise.add(entry.getValue().get(0));
        }

        int[] mThumbIds = new int[merchandise.size()];

        for(int x= 0;x<mThumbIds.length;x++)
        {
            int drawableResourceId = this.getResources().getIdentifier(merchandise.get(x).getPicLink(), "drawable", this.getContext().getPackageName());
            mThumbIds[x] = drawableResourceId;

        }
        //Get list of merchandise and add to list
        String[] strings = new String[mThumbIds.length];
        for(int x = 0;x<strings.length;x++)
        {
            strings[x]= merchandise.get(x).getName();
            //strings[x] = "p";
        }
        ArrayList<Duo> duos = new ArrayList<>();
        //Get list of merchandise text
        for(int x=0;x<mThumbIds.length;x++){
            duos.add(new Duo(strings[x],mThumbIds[x]));
        }

        ImageAdapter imageAdapter = new ImageAdapter(getContext(),duos);
        gridView.setAdapter(imageAdapter);
        gridView.setNumColumns(2);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //go to product page with information about product
                Bundle bundle = new Bundle();
                bundle.putSerializable("curMerch",merchandise.get(position));
                bundle.putString("type","merch");

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new product_fragment();
                fragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                View view1 = getActivity().findViewById(R.id.frag_container);
                int idview = view1.getId();
                transaction.replace(idview,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
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
