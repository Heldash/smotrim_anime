package com.mirea.kt.ribo.smotrimanime.rec_views;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mirea.kt.ribo.smotrimanime.AnimePageActivity;
import com.mirea.kt.ribo.smotrimanime.R;
import com.mirea.kt.ribo.smotrimanime.utils_for_storage.DBManager;
import com.mirea.kt.ribo.smotrimanime.utils_for_storage.SQLHelper;
import com.mirea.kt.ribo.smotrimanime.utils_internet.ShikikomoriApi;
import com.mirea.kt.ribo.smotrimanime.utils_internet.SovetRomanticaApi;
import com.mirea.kt.ribo.smotrimanime.utils_internet.TokenShiki;
import com.mirea.kt.ribo.smotrimanime.utils_internet.shikikomori_object.AnimePodrob;
import com.mirea.kt.ribo.smotrimanime.utils_internet.shikikomori_object.AnimesObj;
import com.mirea.kt.ribo.smotrimanime.utils_internet.sovetRomObj.SovetRomanResSearch;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IzbranFragment extends Fragment implements AnimeAdapter.OnAnimeClickListener{


    public IzbranFragment() {
        // Required empty public constructor
    }


    private ArrayList<AnimeItem> animeList;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    DBManager dbManager;
    private AnimeAdapter adapter;
    private ShikikomoriApi shikikomoriApi;
    private FirebaseDatabase database;
    private SovetRomanticaApi.Api romanApi;
    private SovetRomanticaApi sovetRomanticaApi;
    private ShikikomoriApi.Api shikiApi;
    private TokenShiki tokens;

    public void inicialParametres() {
        mAuth = FirebaseAuth.getInstance();
        dbManager = new DBManager(new SQLHelper(getContext(), "animeDatabase.db",
                null, 4));
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
    }

    public void inicializeAdapter() {
        animeList = dbManager.loadAllIzbran();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inicialParametres();
        View view = inflater.inflate(R.layout.fragment_izbran, container, false);
        RecyclerView recAnime = view.findViewById(R.id.anime_top_izbran);
        recAnime.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,
                false));
        inicializeAdapter();
        adapter = new AnimeAdapter(animeList, database, getContext(), dbManager, this);
        recAnime.setAdapter(adapter);

        shikikomoriApi = new ShikikomoriApi(mAuth, database);
        shikiApi = shikikomoriApi.getApi();
        sovetRomanticaApi = new SovetRomanticaApi(mAuth, database);
        romanApi = sovetRomanticaApi.getApi();


        return view;
    }

    public Bundle addedNeedParametres(AnimeItem anime, SovetRomanResSearch sovRomRes,
                                      AnimePodrob animeShikiInfo) {
        Bundle bundle = new Bundle();
        bundle.putInt("idShiki", anime.getId());
        bundle.putString("nameAnime", anime.getTitleName());
        bundle.putString("shikiDescriptionHtml", animeShikiInfo.getDescriptionHtml());
        bundle.putString("sovetDescription", sovRomRes.getAnimeDescription());
        bundle.putBoolean("hasSovetRoman", true);
        bundle.putInt("sovet_id", sovRomRes.getAnimeId());
        bundle.putString("folderInSite", sovRomRes.getAnimeFolder());
        if (animeShikiInfo.getDescriptionHtml() != null) {
            bundle.putString("descrHtml", animeShikiInfo.getDescriptionHtml());
        } else {
            bundle.putString("descrHtml", "");
        }
        bundle.putString("image_url", animeShikiInfo.getImage().getOriginal());
        bundle.putString("PG", animeShikiInfo.getRating());
        bundle.putInt("series", animeShikiInfo.getEpisodes());
        bundle.putStringArrayList("genres",
                animeShikiInfo.getGenres());
        String status = animeShikiInfo.getStatus();
        switch (status) {
            case "realesed":
                bundle.putString("status", "Вышло");
                break;
            case "anonsed":
                bundle.putString("status", "Анонс");
                break;
            case "ongoing":
                bundle.putString("status", "Выходит");
                break;
            default:
                bundle.putString("status", status);
                break;
        }

        return bundle;
    }

    @Override
    public void onAnimeClick(AnimeItem anime, int position) {
        Bundle bundle = new Bundle();
//        startActivity(new Intent(getContext(), AnimePageActivity.class),bundle);
        shikiSendInfo(bundle, anime);
        Call<ArrayList<SovetRomanResSearch>> call = romanApi.getAnimeForName(anime.getTitleName());
        bundle.putInt("idShiki", anime.getId());
        bundle.putString("nameAnime", anime.getTitleName());

        call.enqueue(new Callback<ArrayList<SovetRomanResSearch>>() {
            @Override
            public void onResponse(Call<ArrayList<SovetRomanResSearch>> call, Response<ArrayList<SovetRomanResSearch>> response) {
                if (response.isSuccessful()) {
                    Log.d("succes", "" + response.code());
                    ArrayList<SovetRomanResSearch> result = response.body();
                    for (SovetRomanResSearch animeRes : result) {
                        Log.d("SovetRoman", "succes" + anime.getId() + "\n" +
                                animeRes.getAnimeShikimori());
                        if (animeRes.getAnimeShikimori() == anime.getId()) {
                            Log.d("SovetRoman", "succes" + animeRes.getAnimeId());
                            bundle.putBoolean("hasSovetRoman", true);
                            bundle.putBoolean("has_video", true);
                            bundle.putInt("sovet_id", animeRes.getAnimeId());
                            bundle.putString("folderInSite", animeRes.getAnimeFolder());
                            Log.d("tokens_state", "" + tokens.getUserAgent() + "\n" +
                                    tokens.getAccesToken());
                            Call<AnimePodrob> callShiki = shikiApi.
                                    getAnimeForId(anime.getId(), tokens.getUserAgent(), "Bearer " + tokens.getAccesToken());
                            callShiki.enqueue(new Callback<AnimePodrob>() {
                                @Override
                                public void onResponse(Call<AnimePodrob> call, Response<AnimePodrob> response) {
                                    Log.d("sdasdsaadasdadasd", "swadaswqqwqdsdadswa");
                                    if (response.isSuccessful()) {
                                        Log.d("Succes_zapros", response.body().toString());
                                        AnimePodrob animeInfo = response.body();
                                        Log.d("descrHas", "" + animeInfo.getDescription());
                                        if (animeInfo.getDescriptionHtml() != null) {
                                            bundle.putString("descrHtml", animeInfo.getDescriptionHtml());
                                        } else {
                                            bundle.putString("descrHtml", "");
                                        }
                                        bundle.putString("image_url", animeInfo.getImage().getOriginal());
                                        bundle.putString("PG", animeInfo.getRating());
                                        bundle.putInt("series", animeInfo.getEpisodes());
                                        bundle.putStringArrayList("genres",
                                                animeInfo.getGenres());

                                        Log.d("bundleForAnimePage", "" + bundle.toString());
                                        Intent intent = new Intent(getContext(), AnimePageActivity.class);
                                        intent.putExtra("bund", bundle);
                                        intent.putExtra("index", position);
                                        intent.putExtra("id", anime.getId());
                                        activityResultLaunch.launch(intent);
                                    } else {
                                        Log.d("error_request", "" + response.code() + "\n" +
                                                response.errorBody());
                                    }
                                }

                                @Override
                                public void onFailure(Call<AnimePodrob> call, Throwable t) {
                                    Log.e("sasadsadsadsd", t.getMessage());
                                }
                            });
                            break;
                        }
                    }
                } else {
                    if (response.code() == 503) {
                        Log.d("error_code", "sraboralo");
                        bundle.putBoolean("has_video", false);
                        Call<AnimePodrob> callShiki = shikiApi.
                                getAnimeForId(anime.getId(), tokens.getUserAgent(), "Bearer " + tokens.getAccesToken());
                        callShiki.enqueue(new Callback<AnimePodrob>() {
                            @Override
                            public void onResponse(Call<AnimePodrob> call, Response<AnimePodrob> response) {
                                if (response.isSuccessful()) {
                                    AnimePodrob animeInfo = response.body();
                                    Log.d("descrHas", "" + animeInfo.getDescription());
                                    if (animeInfo.getDescription() != null) {
                                        bundle.putString("descr", animeInfo.getDescriptionHtml());
                                    } else {
                                        bundle.putString("descr", "");
                                    }
                                    bundle.putString("image_url", animeInfo.getImage().getOriginal());
                                    bundle.putString("PG", animeInfo.getRating());
                                    bundle.putInt("series", animeInfo.getEpisodes());
                                    bundle.putStringArrayList("genres",
                                            animeInfo.getGenres());
                                    Intent intent = new Intent(getContext(), AnimePageActivity.class);
                                    intent.putExtra("bund", bundle);
                                    intent.putExtra("index", position);
                                    intent.putExtra("id", anime.getId());
                                    activityResultLaunch.launch(intent);
                                } else {
                                    Log.d("error_request_no_has_video", "" + response.code() + "\n" +
                                            response.errorBody());
                                }
                            }

                            @Override
                            public void onFailure(Call<AnimePodrob> call, Throwable t) {
                                Log.e("sasadsadsadsd", t.getMessage());
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SovetRomanResSearch>> call, Throwable t) {
                Log.d("kak ono tut poyavilos", "" + t.getMessage());
                Log.d("error_code", "sraboralo");
                bundle.putBoolean("has_video", false);
                Call<AnimePodrob> callShiki = shikiApi.
                        getAnimeForId(anime.getId(), tokens.getUserAgent(), "Bearer " + tokens.getAccesToken());
                callShiki.enqueue(new Callback<AnimePodrob>() {
                    @Override
                    public void onResponse(Call<AnimePodrob> call, Response<AnimePodrob> response) {
                        if (response.isSuccessful()) {
                            AnimePodrob animeInfo = response.body();
                            Log.d("descrHas", "" + animeInfo.getDescription());
                            if (animeInfo.getDescription() != null) {
                                bundle.putString("descr", animeInfo.getDescriptionHtml());
                            } else {
                                bundle.putString("descr", "");
                            }
                            bundle.putString("image_url", animeInfo.getImage().getOriginal());
                            bundle.putString("PG", animeInfo.getRating());
                            bundle.putInt("series", animeInfo.getEpisodes());
                            bundle.putStringArrayList("genres",
                                    animeInfo.getGenres());
                            Intent intent = new Intent(getContext(), AnimePageActivity.class);
                            intent.putExtra("bund", bundle);
                            intent.putExtra("index", position);
                            intent.putExtra("id", anime.getId());
                            activityResultLaunch.launch(intent);
                        } else {
                            Log.d("error_request_no_has_video", "" + response.code() + "\n" +
                                    response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<AnimePodrob> call, Throwable t) {
                        Log.e("sasadsadsadsd", t.getMessage());
                    }
                });
            }
        });


    }


    public void shikiSendInfo(Bundle bundle, AnimeItem anime) {
        DatabaseReference ref = database.getReference("tokens");
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    tokens = task.getResult().getValue(TokenShiki.class);
                    if (tokens != null) {
                        adapter.setPage(1);
                        Call<ArrayList<AnimesObj>> call = shikiApi.getAllAnime(2, 50, "popularity", tokens.getUserAgent(), "Bearer " + tokens.getAccesToken());

                        call.enqueue(new Callback<ArrayList<AnimesObj>>() {
                            @Override
                            public void onResponse(Call<ArrayList<AnimesObj>> call, Response<ArrayList<AnimesObj>> response) {
                                if (response.isSuccessful()) {
                                    animeList = AnimeItem.convertAnimeObj(response.body(), dbManager);
                                    Log.d("animeList", "" + animeList);
                                    adapter.setAnimeList(animeList);
                                    adapter.notifyDataSetChanged();
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
                    }
                }
            }
        });

    }

    ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getData() != null) {
                        boolean edited = result.getData().getBooleanExtra("edited", false);
                        Log.d("chagedWathedIzFav", "edited: " + edited);
                        if (edited) {
                            int index = result.getData().getIntExtra("index", -1);
                            int id = result.getData().getIntExtra("id", -1);
                            Log.d("chagedWathedIzFav", "index:" + index + "\nid: " + id);
                            if (index != -1 && id != -1) {
                                adapter.putAnimeObject(index, dbManager.getAnime(id));
                            }
                        }
                    } else {
                        Log.d("dachtoEtoTakie", "ssadsadasdadsa" + result.getData() +
                                "\n" + result.getResultCode());
                    }
                }
            }
    );
}