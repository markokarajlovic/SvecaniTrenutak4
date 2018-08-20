package com.ephoenixdev.svecanitrenutak.lists;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ephoenixdev.svecanitrenutak.R;
import com.ephoenixdev.svecanitrenutak.ViewAdActivity;
import com.ephoenixdev.svecanitrenutak.models.AdModel;

import java.util.ArrayList;
import java.util.List;

public class ListOfAdsAdapter extends RecyclerView.Adapter<ListOfAdsAdapter.AdViewHolder>{

    private Context mCtx;
    private List<AdModel> adModelList;

    public ListOfAdsAdapter(Context mCtx, List<AdModel> adModelList) {
        this.mCtx = mCtx;
        this.adModelList = adModelList;
    }

    @NonNull
    @Override
    public AdViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_ads, viewGroup, false);
        return new AdViewHolder(view,mCtx,adModelList);
    }

    @Override
    public void onBindViewHolder(@NonNull AdViewHolder adViewHolder, int i) {

        AdModel adModel = adModelList.get(i);

        adViewHolder.textViewTitle.setText(adModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return adModelList.size();
    }

    public class AdViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle;
        List<AdModel> adModelList = new ArrayList<AdModel>();
        Context ctx;

        public AdViewHolder(@NonNull View itemView, Context ctx, List<AdModel> adModelList) {

            super(itemView);
            this.adModelList=adModelList;
            this.ctx=ctx;
            itemView.setOnClickListener(this);

            textViewTitle = itemView.findViewById(R.id.textViewListItemAds);
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            AdModel adModel = this.adModelList.get(position);
            Intent intent = new Intent(this.ctx, ViewAdActivity.class);
            intent.putExtra("Title",adModel.getTitle());
            this.ctx.startActivity(intent);
        }
    }
}
