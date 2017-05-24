package com.example.s214092755.ssapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.s214092755.ssapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class pic_fragment extends Fragment {


    public pic_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pic_fragment, container, false);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageProductFull);
        Bundle bundle = getArguments();
        Integer picID = 0;
        if(bundle!=null)
        {
            String picLink = bundle.getString("picLink");
            picID = this.getResources().getIdentifier(picLink, "drawable", this.getContext().getPackageName());
        }
        imageView.setImageResource(picID);

        return view;
    }

}
