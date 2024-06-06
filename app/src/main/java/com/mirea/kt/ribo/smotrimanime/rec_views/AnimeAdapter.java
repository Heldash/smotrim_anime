package com.mirea.kt.ribo.smotrimanime.rec_views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.io.Resources;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mirea.kt.ribo.smotrimanime.R;
import com.mirea.kt.ribo.smotrimanime.utils_for_storage.DBManager;
import com.mirea.kt.ribo.smotrimanime.utils_for_storage.SQLHelper;
import com.mirea.kt.ribo.smotrimanime.utils_internet.ShikikomoriApi;
import com.mirea.kt.ribo.smotrimanime.utils_internet.TokenShiki;
import com.mirea.kt.ribo.smotrimanime.utils_internet.shikikomori_object.AnimesObj;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.ViewHolder> {

    private ArrayList<AnimeItem> animeList;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private int page;
    private Context context;
    private DBManager dbManager;
    private OnAnimeClickListener onAnimeClickListener;

    public void setPage(int page) {
        this.page = page;
    }

    public void putAnimeObject(int index, AnimeItem anime) {
        animeList.set(index, anime);
        notifyItemChanged(index);
    }

    interface OnAnimeClickListener {
        void onAnimeClick(AnimeItem anime, int position);
    }

    public AnimeAdapter(ArrayList<AnimeItem> animeList, FirebaseDatabase database, Context context, DBManager dbManager, OnAnimeClickListener onAnimeClickListener) {
        this.animeList = animeList;
        this.database = database;
        this.context = context;
        this.dbManager = dbManager;
        this.onAnimeClickListener = onAnimeClickListener;
    }

    public AnimeAdapter(ArrayList<AnimeItem> animeList, OnAnimeClickListener onAnimeClickListener,
                        DBManager dbManager) {
        this.animeList = animeList;
        this.dbManager = dbManager;
        this.onAnimeClickListener = onAnimeClickListener;
    }

    public void setAnimeList(ArrayList<AnimeItem> animeList) {
        this.animeList = animeList;
    }

    public AnimeAdapter(ArrayList<AnimeItem> animeList) {
        this.animeList = animeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anime, parent, false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        return new ViewHolder(view);
    }


    public void uploadAnimeList() {
        ShikikomoriApi shikikomoriApi = new ShikikomoriApi(mAuth, database);
        ShikikomoriApi.Api api = shikikomoriApi.getApi();
        DatabaseReference ref = database.getReference("tokens");
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    TokenShiki tokens = task.getResult().getValue(TokenShiki.class);
                    if (tokens != null) {
                        page++;
                        if (page < 100000) {
                            Call<ArrayList<AnimesObj>> call = api.getAllAnime(page, 50, "ranked", tokens.getUserAgent(), "Bearer" + tokens.getAccesToken());

                            call.enqueue(new Callback<ArrayList<AnimesObj>>() {
                                @Override
                                public void onResponse(Call<ArrayList<AnimesObj>> call, Response<ArrayList<AnimesObj>> response) {
                                    if (response.isSuccessful()) {
                                        ArrayList<AnimeItem> addedAnime = AnimeItem.convertAnimeObj(response.body(), dbManager);
                                        Log.d("animeList", "" + animeList);
                                        animeList.addAll(addedAnime);
                                        notifyDataSetChanged();
                                    }
                                    if (!response.isSuccessful()) {
                                        if (response.code() == 401) {
                                            shikikomoriApi.refreshingTokens(tokens.getClient_id(),
                                                    tokens.getClient_secret(), tokens.getRefresh_token(),
                                                    tokens.getUserAgent());
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<AnimesObj>> call, Throwable t) {

                                }
                            });
                        } else {
                            Log.d("sdaas", "done spisok");
                        }
                    }
                }
            }

        });
    }

    private void setTextCard(AnimeItem anime, @NonNull AnimeAdapter.ViewHolder holder) {
        if (anime.getTitleName()!=null && !anime.getTitleName().isEmpty()) {
            holder.tileAnime.setText(anime.getTitleName());
        }else if (anime.getTitleNameEnglish()!=null && !anime.getTitleNameEnglish().isEmpty()){
            holder.tileAnime.setText(anime.getTitleNameEnglish());
        }else{
            holder.tileAnime.setText("Без названия");
        }
        float rait = anime.getRaiting();
        if ((int) rait == 0) {
            holder.raitingAnime.setText("Отсутствует");
        } else {
            if ((int) rait == rait) {
                holder.raitingAnime.setText((int) rait + "/10");
            } else {
                holder.raitingAnime.setText(rait + "/10");
            }
        }
        if (anime.getYear() == -1) {
            holder.year.setVisibility(View.GONE);
        } else {
            holder.year.setText(String.valueOf(anime.getYear()));
        }
        if (anime.getSeasonYear() != null && !anime.getSeasonYear().equals("неизвестно")) {
            holder.season.setText(anime.getSeasonYear());
            switch (anime.getSeasonYear()) {
                case "зима":
                    holder.season.setTextColor(
                            context.getApplicationContext()
                                    .getResources()
                                    .getColor(R.color.winter_season)
                    );
                    break;
                case "весна":
                    holder.season.setTextColor(
                            context.getApplicationContext()
                                    .getResources()
                                    .getColor(R.color.spring_season)
                    );
                    break;
                case "лето":
                    holder.season.setTextColor(
                            context.getApplicationContext()
                                    .getResources()
                                    .getColor(R.color.summer_season)
                    );
                    break;
                case "осень":
                    holder.season.setTextColor(
                            context.getApplicationContext()
                                    .getResources()
                                    .getColor(R.color.automn_season)
                    );
                    break;
            }
        } else {
            holder.season.setVisibility(View.GONE);
        }
        holder.status.setText(anime.getStatus());
        Log.d("contextSADSAD", "" + context.toString());
        if (anime.getStatus() != null) {
            switch (anime.getStatus()) {
                case "анонс":
                    holder.status.getBackground().setTint(
                            context.getApplicationContext()
                                    .getResources()
                                    .getColor(R.color.anons_red)
                    );
                    break;
                case "онгоинг":
                    holder.status.getBackground().setTint(
                            context.getApplicationContext()
                                    .getResources()
                                    .getColor(R.color.ongoing_color)
                    );
                    break;
                case "завершено":
                    holder.status.getBackground().setTint(
                            context.getApplicationContext()
                                    .getResources()
                                    .getColor(R.color.realese_color)
                    );
                    break;
            }
        }
    }

    private void buttonsCardEdit(AnimeItem anime, AnimeAdapter.ViewHolder holder) {
        DBManager.StateAnime status = dbManager.statusUserAnime(anime);
        if (status.isIzbran()) {
            holder.izbranAnime.setImageDrawable(context.getApplicationContext()
                    .getResources()
                    .getDrawable(R.drawable.bookmark_full));
            Drawable img = holder.izbranAnime.getDrawable();
            img.setTint(context.getResources().getColor(R.color.color_yellow));
//            holder.favAnime.setBackground(
//                    context.getApplicationContext()
//                            .getResources()
//                            .getDrawable(R.drawable.bookmark_full));
        } else {
            holder.izbranAnime.setImageDrawable(context.getApplicationContext()
                    .getResources()
                    .getDrawable(R.drawable.bookmark_empty));
            Drawable img = holder.izbranAnime.getDrawable();
            img.setTint(context.getResources().getColor(R.color.gray_finish));
        }
        if (status.isFavorite()) {
//            holder.favAnime.setImageDrawable(img);
            holder.favAnime.setImageDrawable(context.getApplicationContext()
                    .getResources()
                    .getDrawable(R.drawable.ic_action_favorite));
            Drawable img = holder.favAnime.getDrawable();
            img.setTint(context.getResources().getColor(R.color.color_red));
        } else {
            holder.favAnime.setImageDrawable(context.getApplicationContext()
                    .getResources()
                    .getDrawable(R.drawable.empty_favorite));
            Drawable img = holder.favAnime.getDrawable();
            img.setTint(context.getResources().getColor(R.color.gray_finish));
        }
        if (status.isWatched()) {
            Drawable img = holder.watchedBtn.getDrawable();
            img.setTint(context.getResources().getColor(R.color.color_green));
//            holder.watchedBtn.setImageDrawable(img);
        } else {
            Drawable img = holder.watchedBtn.getDrawable();
            img.setTint(context.getResources().getColor(R.color.gray_finish));
        }


    }

    @Override
    public void onBindViewHolder(@NonNull AnimeAdapter.ViewHolder holder, int position) {
        AnimeItem anime = animeList.get(position);
        if (position == animeList.size()) {
            uploadAnimeList();
        }
        setTextCard(anime, holder);
        buttonsCardEdit(anime, holder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation selectItem = AnimationUtils.loadAnimation(v.getContext(), R.anim.selected_item);
                v.startAnimation(selectItem);

                onAnimeClickListener.onAnimeClick(anime, holder.getAdapterPosition());
            }
        });

        holder.favAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation select = AnimationUtils.loadAnimation(context, R.anim.selected_item);
                DBManager.StateAnime status = dbManager.statusUserAnime(anime);
                holder.favAnime.startAnimation(select);
                boolean result;
                if (status.isFavorite()) {
                    result = dbManager.diasableFavoriteAnime(anime.getId());
                    if (result) {
                        Toast.makeText(context.getApplicationContext(),
                                "Удалено из любимого", Toast.LENGTH_SHORT).show();
                        holder.favAnime.setImageDrawable(context.getApplicationContext()
                                .getResources()
                                .getDrawable(R.drawable.empty_favorite));
                        Drawable img = holder.favAnime.getDrawable();
                        img.setTint(context.getResources().getColor(R.color.gray_finish));
                    } else {
                        Toast.makeText(context.getApplicationContext(),
                                "Ошибка при удалении", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    result = dbManager.addFavoriteAnime(anime.getId());
                    if (result) {
                        Toast.makeText(context.getApplicationContext(),
                                "Добавленно в любимое", Toast.LENGTH_SHORT).show();
                        holder.favAnime.setImageDrawable(context.getApplicationContext()
                                .getResources()
                                .getDrawable(R.drawable.ic_action_favorite));
                        Drawable img = holder.favAnime.getDrawable();
                        img.setTint(context.getResources().getColor(R.color.color_red));

                    } else {
                        Toast.makeText(context.getApplicationContext(),
                                "Ошибка при добавлении", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        holder.izbranAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation select = AnimationUtils.loadAnimation(context, R.anim.selected_item);
                DBManager.StateAnime status = dbManager.statusUserAnime(anime);
                holder.izbranAnime.startAnimation(select);
                boolean result;
                if (status.isIzbran()) {
                    result = dbManager.diasableIzbranAnime(anime.getId());
                    if (result) {
                        Toast.makeText(context.getApplicationContext(),
                                "Удалено из избранного", Toast.LENGTH_SHORT).show();
                        holder.izbranAnime.setImageDrawable(context.getApplicationContext()
                                .getResources()
                                .getDrawable(R.drawable.bookmark_empty));
                        Drawable img = holder.izbranAnime.getDrawable();
                        img.setTint(context.getResources().getColor(R.color.gray_finish));
                    } else {
                        Toast.makeText(context.getApplicationContext(),
                                "Ошибка при удалении", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    result = dbManager.addIzbranAnime(anime.getId());
                    if (result) {
                        Toast.makeText(context.getApplicationContext(),
                                "Добавленно в избранное", Toast.LENGTH_SHORT).show();
                        holder.izbranAnime.setImageDrawable(
                                context.getApplicationContext()
                                        .getResources()
                                        .getDrawable(R.drawable.bookmark_full));
                        Drawable img = holder.izbranAnime.getDrawable();
                        img.setTint(context.getResources().getColor(R.color.color_yellow));
                    } else {
                        Toast.makeText(context.getApplicationContext(),
                                "Ошибка при добавлении", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        holder.watchedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation select = AnimationUtils.loadAnimation(context, R.anim.selected_item);
                holder.watchedBtn.startAnimation(select);
                DBManager.StateAnime status = dbManager.statusUserAnime(anime);
                boolean result;
                if (status.isWatched()) {
                    result = dbManager.diasableWatchedAnime(anime.getId());

                    if (result) {
                        Toast.makeText(context.getApplicationContext(),
                                "Удалено из просмотренного", Toast.LENGTH_SHORT).show();
                        Drawable img = holder.watchedBtn.getDrawable();
                        img.setTint(context.getResources().getColor(R.color.gray_finish));
                    } else {
                        Toast.makeText(context.getApplicationContext(),
                                "Ошибка при удалении", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    result = dbManager.addWatchedAnime(anime.getId());
                    if (result) {
                        Toast.makeText(context.getApplicationContext(),
                                "Добавленно в просмотренное", Toast.LENGTH_SHORT).show();
                        Drawable img = holder.watchedBtn.getDrawable();
                        img.setTint(context.getResources().getColor(R.color.color_green));
                    } else {
                        Toast.makeText(context.getApplicationContext(),
                                "Ошибка при добавлении", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        Picasso.get().load(anime.getImageUrl()).error(R.drawable.no_image).into(holder.animePoster);
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView animePoster;
        private final TextView tileAnime, raitingAnime, status, year, season;
        private final ImageButton favAnime, izbranAnime, watchedBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //text views
            animePoster = itemView.findViewById(R.id.anime_poster_card);
            tileAnime = itemView.findViewById(R.id.title_anime_card);
            raitingAnime = itemView.findViewById(R.id.raiting_anime_card);
            status = itemView.findViewById(R.id.status_anime);
            year = itemView.findViewById(R.id.year);
            season = itemView.findViewById(R.id.season);
            //ImageButton
            favAnime = itemView.findViewById(R.id.favorite_titles_btn);
            izbranAnime = itemView.findViewById(R.id.izbran);
            watchedBtn = itemView.findViewById(R.id.watched_btn);
        }
    }
}
