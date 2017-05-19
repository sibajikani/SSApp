package layout;

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
        GridView gridView = (GridView)view.findViewById(R.id.gridview_merch);
        ImageAdapter imageAdapter = new ImageAdapter(getContext());
        gridView.setAdapter(imageAdapter);
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
