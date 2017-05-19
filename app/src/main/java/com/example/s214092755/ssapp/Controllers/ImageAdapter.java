package com.example.s214092755.ssapp.Controllers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.s214092755.ssapp.R;

import java.util.List;

/**
 * Created by s214092755 on 2017/05/18.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
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
}
