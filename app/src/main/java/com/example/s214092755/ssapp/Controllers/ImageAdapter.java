package com.example.s214092755.ssapp.Controllers;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.s214092755.ssapp.Models.Duo;
import com.example.s214092755.ssapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by s214092755 on 2017/05/18.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Duo> data;

    public ImageAdapter(Context c, ArrayList<Duo> data) {
        mContext = c;
        this.data = data;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //if (convertView == null) {

            //grid = inflater.inflate(R.layout.grid_item, null);

            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            //TextView textView = (TextView) grid.findViewById(R.id.gridText);
            //ImageView imageView = (ImageView)grid.findViewById(R.id.gridImage);
            TextView textView = new TextView(mContext);
            ImageView imageView = new ImageView(mContext);

            textView.setText(data.get(position).getName());
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            textView.setMaxHeight(50);
            textView.setMaxWidth(50);

            imageView.setMaxHeight(50);
            imageView.setMaxWidth(50);

            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            imageView.setImageResource(data.get(position).getRes());
            //grid.setLayoutParams(new GridView.LayoutParams(400, 400));
            imageView.setLayoutParams(new GridView.LayoutParams(270, 270));

            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            //grid.setPadding(5, 5, 5, 5);
        //} else {
            //grid = convertView;
        //}

        return linearLayout;
    }

}
