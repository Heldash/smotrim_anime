package com.mirea.kt.ribo.smotrimanime.rec_views;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirea.kt.ribo.smotrimanime.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.ViewHolder> {

    private ArrayList<AnimeItem> animeList;

    public AnimeAdapter(ArrayList<AnimeItem> animeList) {
        this.animeList = animeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anime,parent,false);
        return new ViewHolder(view);
    }
    public void correctList(float reating){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Stream<AnimeItem> an = animeList.stream();
            animeList = an.filter(x->x.getRaiting()>reating).collect(Collectors.toCollection(ArrayList::new));
            notifyDataSetChanged();
        }
    }
    @Override
    public void onBindViewHolder(@NonNull AnimeAdapter.ViewHolder holder, int position) {
        AnimeItem anime = animeList.get(position);
        holder.tileAnime.setText(anime.getTitleName());
//        int length_max = holder.tileAnime.getMaxEms();
//        Log.d("view_holder_anime",""+length_max);
        holder.descrAnime.setText(anime.getDescription());
        float rait = anime.getRaiting();
        if ((int)rait ==rait ){
            holder.raitingAnime.setText((int)rait+"/5");
        }else{
            holder.raitingAnime.setText(rait+"/5");
        }
        Picasso.get().load(anime.getImageUrl()).error(R.drawable.no_image).into(holder.animePoster);
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView animePoster;
        private final TextView tileAnime,descrAnime,raitingAnime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            animePoster = itemView.findViewById(R.id.anime_poster_card);
            tileAnime = itemView.findViewById(R.id.title_anime_card);
            descrAnime = itemView.findViewById(R.id.description_anime_card);
            raitingAnime = itemView.findViewById(R.id.raiting_anime_card);;
        }
    }
}
