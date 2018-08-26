package com.ephoenixdev.svecanitrenutak.lists;

import android.app.Activity;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ephoenixdev.svecanitrenutak.R;

public class CategoryAdapter extends BaseAdapter {

    private Activity context;

    public CategoryAdapter(Activity context) {
        this.context = context;
    }

    int[] kategorije = {
            R.string.K_1,
            R.string.K_2,
            R.string.K_3,
            R.string.K_4,
            R.string.K_5,
            R.string.K_6,
            R.string.K_7,
            R.string.K_8,
            R.string.K_9,
            R.string.K_10,
            R.string.K_11,
            R.string.K_12,
            R.string.K_13,
            R.string.K_14,
            R.string.K_15,
            R.string.K_16,
            R.string.K_17,
            R.string.K_18,
            R.string.K_19,
            R.string.K_20,
            R.string.K_21,
            R.string.K_22
    };

    int[] ikoniceKategorija = {
            R.drawable.ic_muzika,
            R.drawable.ic_restorani,
            R.drawable.ic_vencanice,
            R.drawable.ic_dekoracije,
            R.drawable.ic_torte,
            R.drawable.ic_foto,
            R.drawable.ic_satori,
            R.drawable.ic_pozivnice,
            R.drawable.ic_lepota,
            R.drawable.ic_limuzine,
            R.drawable.ic_osoblje,
            R.drawable.ic_prenociste,
            R.drawable.ic_burme,
            R.drawable.ic_ketering,
            R.drawable.ic_pokloncici,
            R.drawable.ic_pica,
            R.drawable.ic_igraonice,
            R.drawable.ic_svecana_odela,
            R.drawable.ic_obuca,
            R.drawable.ic_animacije,
            R.drawable.ic_organizatori,
            R.drawable.ic_ostalo
    };

    @Override
    public int getCount() {
        return kategorije.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        convertView = inflater.inflate(R.layout.list_item_categories,null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewListItemCategories);
        TextView textView = (TextView) convertView.findViewById(R.id.textViewListItemCategories);

        imageView.setImageResource(ikoniceKategorija[position]);
        textView.setText(kategorije[position]);
        return convertView;
    }
}

