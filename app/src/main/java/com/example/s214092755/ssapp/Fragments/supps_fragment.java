package com.example.s214092755.ssapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.s214092755.ssapp.Controllers.ImageAdapter;
import com.example.s214092755.ssapp.Models.Transaction;
import com.example.s214092755.ssapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class supps_fragment extends Fragment {

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
        GridView gridView = (GridView)view.findViewById(R.id.gridview);

        int[] mThumbIds = {
            R.drawable.images, R.drawable.n_tech_1kg_american_870x1110,
            R.drawable.new_usn_nl_towel, R.drawable.tornado_shaker_front_b_1,
            R.drawable.nutritech_anabolicmass5_2, R.drawable.nutritechfit_hunter_cap_product_page,
            R.drawable.nutritechfit_lifting_gloves_product_page1_250x317, R.drawable.nutritechfit_nylon_lifting_belt_product_page1_250x317,
            R.drawable.nutritechfit_pullover_250x317, R.drawable.usn_pure_protein1,
            R.drawable.usn_b4_bomb, R.drawable.usn_100_premium_whey_protein,
            R.drawable.nutritechfit_mens_summer_swolestice_singlet_pink,
            R.drawable.nutritechfit_amino_pre_250x317, R.drawable.nutritech_shakepro400_2,
            R.drawable.nutritechfit_2_2_litre_colossus_bottle_1_250x317,
            R.drawable.nutribot_tee_front_1_11
    };
        //Get list of supplements and add to list
        String[] strings = new String[mThumbIds.length];
        for(int x = 0;x<strings.length;x++){
            strings[x]= "Supp "+x;
        }

        //Get list of supplement text


        ImageAdapter imageAdapter = new ImageAdapter(getContext(),strings,mThumbIds);
        //gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //go to product page with information about product
            }
        });


        return view;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_supps_fragment, container, false);
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
