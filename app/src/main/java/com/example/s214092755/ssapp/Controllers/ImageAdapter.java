package com.example.s214092755.ssapp.Controllers;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.s214092755.ssapp.R;

import java.util.List;

/**
 * Created by s214092755 on 2017/05/18.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;

    public ImageAdapter(Context c, String[] web,int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
    }

    public int getCount() {
        return web.length;
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
        if (convertView == null) {

            grid = inflater.inflate(R.layout.grid_item, null);

            TextView textView = (TextView) grid.findViewById(R.id.gridText);
            ImageView imageView = (ImageView)grid.findViewById(R.id.gridImage);

            textView.setText(web[position]);


            imageView.setImageResource(Imageid[position]);
            grid.setLayoutParams(new GridView.LayoutParams(250, 250));
            //grid.setPadding(5, 5, 5, 5);
        } else {
            grid = convertView;
        }

        return grid;
    }

    // references to our images
    /*private Integer[] mThumbIds = {
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
    };*/
}
