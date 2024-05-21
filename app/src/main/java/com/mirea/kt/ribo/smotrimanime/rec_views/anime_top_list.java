package com.mirea.kt.ribo.smotrimanime.rec_views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirea.kt.ribo.smotrimanime.R;

import java.util.ArrayList;

public class anime_top_list extends Fragment {
    private ArrayList<AnimeItem> animeList;
    public anime_top_list() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime_top_list, container, false);
        animeList = new ArrayList<>();
        animeList.add(new AnimeItem("Title name","descr","https://shikimori.one/assets/globals/missing_original.jpg","tv",4.0f));
        animeList.add(new AnimeItem("Title","descr","https://shikimori.one/assets/globals/missing_original.jpg","tv",4.5f));
        animeList.add(new AnimeItem("Title name","descr","https://shikimori.one/assets/globals/missing_original.jpg","tv",4.5f));
        animeList.add(new AnimeItem("Title name","descr","https://shikimori.one/assets/globals/missing_original.jpg","tv",4.5f));
        Log.d("On_create_view_Card",animeList.toString());
        RecyclerView recAnime = view.findViewById(R.id.anime_top_list);
        recAnime.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,
                false));
        AnimeAdapter adapter = new AnimeAdapter(animeList);
        recAnime.setAdapter(adapter);
        return view;
    }
}