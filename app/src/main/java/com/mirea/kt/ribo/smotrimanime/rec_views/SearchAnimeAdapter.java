package com.mirea.kt.ribo.smotrimanime.rec_views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.mirea.kt.ribo.smotrimanime.R;
import com.mirea.kt.ribo.smotrimanime.utils_for_storage.DBManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAnimeAdapter extends RecyclerView.Adapter<SearchAnimeAdapter.ViewHolder> {
    private ArrayList<AnimeItem> animeList;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private int page;
    private Context context;
    private DBManager dbManager;
//    interface OnAnimeClickSearchListener {
//        void onAnimeClick(AnimeItem animeItem, int position);
//    }
    public interface OnAnimeClickSearchListener {
        void OnAnimeSearchClick(AnimeItem anime,int position);
    }
    private OnAnimeClickSearchListener onAnimeClickSearchListener;


    public SearchAnimeAdapter(ArrayList<AnimeItem> animeList) {
        this.animeList = animeList;
    }

    public SearchAnimeAdapter(ArrayList<AnimeItem> animeList, OnAnimeClickSearchListener onAnimeClickSearchListener) {
        this.animeList = animeList;
        this.onAnimeClickSearchListener = onAnimeClickSearchListener;
    }

    public ArrayList<AnimeItem> getAnimeList() {
        return animeList;
    }

    public void setAnimeList(ArrayList<AnimeItem> animeList) {
        this.animeList = animeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_searched, parent, false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        return new SearchAnimeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AnimeItem anime = animeList.get(position);
        if (anime.getTitleName() != null && !anime.getTitleName().isEmpty()) {
            holder.titleName.setText(anime.getTitleName());
        } else if (anime.getTitleNameEnglish() != null && !anime.getTitleNameEnglish().isEmpty()) {
            holder.titleName.setText(anime.getTitleNameEnglish());
        } else {
            holder.titleName.setText("Неизвестно");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Anime_in_holder",anime.toString());
//                Animation selectItem = AnimationUtils.loadAnimation(v.getContext(), R.anim.selected_item);
//                v.startAnimation(selectItem);
                onAnimeClickSearchListener.OnAnimeSearchClick(anime, holder.getAdapterPosition());
            }
        });
        Picasso.get().load(anime.getImageUrl()).error(R.drawable.no_image)
                .into(holder.titleImage);
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView titleImage;
        final TextView titleName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleName = itemView.findViewById(R.id.titleName);
            titleImage = itemView.findViewById(R.id.imageAnime);
        }
    }
}
