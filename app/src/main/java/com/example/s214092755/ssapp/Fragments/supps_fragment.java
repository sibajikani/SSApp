package com.example.s214092755.ssapp.Fragments;

import android.app.Activity;
import android.content.Context;
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
import com.example.s214092755.ssapp.Models.Supplement;
import com.example.s214092755.ssapp.R;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;


public class supps_fragment extends Fragment {

    private DatabaseHelper dbh;
    private ProductController productController;
    public supps_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supps_fragment, container, false);
        final GridView gridView = (GridView)view.findViewById(R.id.gridview);


        dbh = new DatabaseHelper(getContext());
        try {
            dbh.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        productController = new ProductController(dbh,getContext());

        final ArrayList<Supplement> supplements = new ArrayList<>();

        final Map<Integer,ArrayList<Supplement>> mappedSupps = productController.getAllSupplements();

        //set supplement list in main activity
        productController.setSupList((MainActivity)getActivity());

        for (Map.Entry<Integer,ArrayList<Supplement>> entry : mappedSupps.entrySet())
        {
            supplements.add(entry.getValue().get(0));
        }

        int[] mThumbIds = new int[supplements.size()];

        for(int x= 0;x<mThumbIds.length;x++)
        {
            int drawableResourceId = this.getResources().getIdentifier(supplements.get(x).getPicLink(), "drawable", this.getContext().getPackageName());
            mThumbIds[x] = drawableResourceId;
        }
        //Get list of supplements and add to list
        String[] strings = new String[mThumbIds.length];
        for(int x = 0;x<strings.length;x++){
            strings[x]= supplements.get(x).getName();
        }
        ArrayList<Duo> duos = new ArrayList<>();

        //Get list of supplement text
        for(int x=0;x<mThumbIds.length;x++){
            duos.add(new Duo(strings[x],mThumbIds[x]));
        }



        ImageAdapter imageAdapter = new ImageAdapter(getContext(),duos);
        gridView.setAdapter(imageAdapter);
        gridView.setNumColumns(2);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //go to product page with information about product
                Bundle bundle = new Bundle();
                bundle.putSerializable("curSup",supplements.get(position));
                bundle.putString("type","supp");

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new product_fragment();
                fragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                View view1 = getActivity().findViewById(R.id.frag_container);
                int idview = view1.getId();
                transaction.replace(idview,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                fragmentManager.executePendingTransactions();
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
