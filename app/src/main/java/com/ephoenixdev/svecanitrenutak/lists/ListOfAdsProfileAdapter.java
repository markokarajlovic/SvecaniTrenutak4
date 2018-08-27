package com.ephoenixdev.svecanitrenutak.lists;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ephoenixdev.svecanitrenutak.R;
import com.ephoenixdev.svecanitrenutak.ViewAdActivity;
import com.ephoenixdev.svecanitrenutak.models.AdModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListOfAdsProfileAdapter extends RecyclerView.Adapter<ListOfAdsProfileAdapter.AdViewHolder>{

    private Context mCtx;
    private List<AdModel> adModelList;

    public ListOfAdsProfileAdapter(Context mCtx, List<AdModel> adModelList) {
        this.mCtx = mCtx;
        this.adModelList = adModelList;
    }

    @NonNull
    @Override
    public ListOfAdsProfileAdapter.AdViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_ads2, viewGroup, false);
        return new ListOfAdsProfileAdapter.AdViewHolder(view,mCtx,adModelList);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfAdsProfileAdapter.AdViewHolder adViewHolder, int i) {

        AdModel adModel = adModelList.get(i);

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("AdImages/" + adModel.getAdId() + "/" +adModel.getImageOfTheAd());

        adViewHolder.textViewTitle.setText(adModel.getTitle());
        adViewHolder.textViewCity.setText(adModel.getCityOfAd());
        final ImageView imageView = adViewHolder.imageView;

        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return adModelList.size();
    }

    public class AdViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle, textViewCity;
        ImageView imageView;
        List<AdModel> adModelList = new ArrayList<AdModel>();
        Context ctx;

        public AdViewHolder(@NonNull View itemView, Context ctx, List<AdModel> adModelList) {

            super(itemView);
            this.adModelList = adModelList;
            this.ctx=ctx;
            itemView.setOnClickListener(this);

            imageView = itemView.findViewById(R.id.imageViewListItemAds2);
            textViewTitle = itemView.findViewById(R.id.textViewListItemAdsTitle2);
            textViewCity = itemView.findViewById(R.id.textViewListItemAdsCity2);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            AdModel adModel = this.adModelList.get(position);
            Intent intent = new Intent(this.ctx, ViewAdActivity.class);
            intent.putExtra("userId",adModel.getUserID());
            intent.putExtra("adId",adModel.getAdId());
            intent.putExtra("title",adModel.getTitle());
            intent.putExtra("city",adModel.getAddress());
            intent.putExtra("discription",adModel.getDescription());
            intent.putExtra("fbURL",adModel.getFbURL());
            intent.putExtra("instagramURL",adModel.getInstagramURL());
            intent.putExtra("youtubeURL",adModel.getYouTubeURL());
            intent.putExtra("webSite",adModel.getPersonalWebSite());
            intent.putExtra("adress",adModel.getAddress());
            intent.putExtra("category", adModel.getCategory());
            intent.putExtra("imageOfAd",adModel.getImageOfTheAd());
            this.ctx.startActivity(intent);
        }
    }
}
