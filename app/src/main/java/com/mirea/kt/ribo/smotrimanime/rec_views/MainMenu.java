package com.mirea.kt.ribo.smotrimanime.rec_views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mirea.kt.ribo.smotrimanime.AnimePageActivity;
import com.mirea.kt.ribo.smotrimanime.MainActivity;
import com.mirea.kt.ribo.smotrimanime.R;
import com.mirea.kt.ribo.smotrimanime.utils_for_storage.Account;
import com.mirea.kt.ribo.smotrimanime.utils_for_storage.DBManager;
import com.mirea.kt.ribo.smotrimanime.utils_for_storage.SQLHelper;
import com.mirea.kt.ribo.smotrimanime.utils_internet.ShikikomoriApi;
import com.mirea.kt.ribo.smotrimanime.utils_internet.SovetRomanticaApi;
import com.mirea.kt.ribo.smotrimanime.utils_internet.TokenShiki;
import com.mirea.kt.ribo.smotrimanime.utils_internet.shikikomori_object.AnimePodrob;
import com.mirea.kt.ribo.smotrimanime.utils_internet.shikikomori_object.AnimesObj;
import com.mirea.kt.ribo.smotrimanime.utils_internet.sovetRomObj.SovetRomanResSearch;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMenu extends AppCompatActivity implements SearchAnimeAdapter.OnAnimeClickSearchListener {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    boolean loading, workThread;
    private FirebaseUser user;
    private ImageButton menuButton;
    private TextView logOutOrLogIn;
    private ShikikomoriApi shikikomoriApi;
    private Account account;
    private String currentText;
    private String workingText;

    private RecyclerView searchRec;
    private SovetRomanticaApi.Api romanApi;
    private SovetRomanticaApi sovetRomanticaApi;
    private ShikikomoriApi.Api shikiApi;
    private FirebaseAuth mAuth;
    private EditText searchEdText;
    private TokenShiki tokens;
    private DBManager dbManager;
    private FirebaseDatabase database;
    private SearchAnimeAdapter searchAdapter;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mAuth = FirebaseAuth.getInstance();
        dbManager = new DBManager(new SQLHelper(getApplicationContext(), "animeDatabase.db",
                null, 4));
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        shikikomoriApi = new ShikikomoriApi(mAuth, database);
        shikiApi = shikikomoriApi.getApi();
        sovetRomanticaApi = new SovetRomanticaApi(mAuth, database);
        romanApi = sovetRomanticaApi.getApi();
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.draw_layout);
        menuButton = findViewById(R.id.menu_btn);
        searchEdText = findViewById(R.id.search_ed_text);
        searchRec = findViewById(R.id.res_search);
        searchAdapter = new SearchAnimeAdapter(new ArrayList<AnimeItem>(),this);
        searchRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false));
        searchRec.setAdapter(searchAdapter);
        loading = false;
        currentText = "";
        workingText = "";
        Thread thread = new Thread(() -> {
            try {
                while (workThread) {
                    if (currentText.isEmpty()) {
                        Thread.sleep(500);
                    } else {
                        if (!currentText.isEmpty()&&!loading) {
                            loading = true;
                            workingText = currentText;
                            currentText = "";
                            database.getReference().child("tokens").get()
                                    .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@androidx.annotation.NonNull Task<DataSnapshot> task) {
                                            tokens = task.getResult().getValue(TokenShiki.class);

                                            Call<ArrayList<AnimesObj>> call = shikiApi.getAnimesByName(workingText, 10, tokens.getUserAgent(),
                                                    "Bearer" + tokens.getAccesToken());
                                            call.enqueue(new Callback<ArrayList<AnimesObj>>() {
                                                @Override
                                                public void onResponse(Call<ArrayList<AnimesObj>> call, Response<ArrayList<AnimesObj>> response) {
                                                    ArrayList<AnimesObj> animeList = response.body();
                                                    if (animeList != null) {
                                                        Log.d("getting_anime", "" + animeList.toString());
                                                        handler.post(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                searchAdapter.setAnimeList(AnimeItem.convertAnimeObj(animeList, dbManager));
                                                                searchAdapter.notifyDataSetChanged();
                                                                loading = false;
                                                            }
                                                        });

                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ArrayList<AnimesObj>> call, Throwable t) {
                                                    Log.d("Failure_thread",""+t.getMessage());
                                                }
                                            });
                                        }
                                    });

                        }
                    }

                }

            } catch (InterruptedException e) {

            }
        });
        workThread = true;
        thread.start();
        searchEdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    searchRec.setVisibility(View.GONE);
                    searchAdapter.setAnimeList(new ArrayList<AnimeItem>());
                    searchAdapter.notifyDataSetChanged();
                } else {
                    searchRec.setVisibility(View.VISIBLE);
                }
                currentText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        logOutOrLogIn = drawerLayout.findViewById(R.id.logInOrOut);
        menuButton.setOnClickListener(v -> {
            searchEdText.getText().clear();
            drawerLayout.open();
        });
        if (user != null) {
            DatabaseReference ref = database.getReference("Users");

            logOutOrLogIn.setOnClickListener(v -> {
                mAuth.signOut();
                user = mAuth.getCurrentUser();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            });
            Menu nav_menu = navigationView.getMenu();
            nav_menu.findItem(R.id.watched_btn).setVisible(true);
            nav_menu.findItem(R.id.favorite_titles_btn).setVisible(true);
            nav_menu.findItem(R.id.izbran).setVisible(true);
            ref.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        account = task.getResult().getValue(Account.class);
                        Log.d("get_user_data", account.toString());
                        View header = navigationView.getHeaderView(0);
                        TextView name_acc = header.findViewById(R.id.username_user);
                        name_acc.setText(account.getUserName());
                        ShapeableImageView image_avatar = header.findViewById(R.id.avatar_user);
                        if (!account.getAvatarImgUrl().isEmpty()) {
                            Picasso.get().load(account.getAvatarImgUrl()).error(R.drawable.no_image).into(image_avatar);
                        } else {
                            String default_image = "https://firebasestorage.googleapis.com/v0/b/smotrim-anime-app.appspot.com/o/avatars%2Fdefault_avatar.jpg?alt=media&token=d91f97d4-41ff-48c8-a929-b0486f1d9041";
                            Picasso.get().load(default_image).error(R.drawable.no_image).into(image_avatar);
                        }
                    }

                }
            });
            logOutOrLogIn.setText("Выход");
            logOutOrLogIn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.log_out_icon,
                    0, 0, 0);
//            Log.d("text_vievs",account.toString());


        } else {
            Menu nav_menu = navigationView.getMenu();
            nav_menu.findItem(R.id.watched_btn).setVisible(false);
            nav_menu.findItem(R.id.favorite_titles_btn).setVisible(false);
            nav_menu.findItem(R.id.izbran).setVisible(false);
            logOutOrLogIn.setText("Вход");
            logOutOrLogIn.setOnClickListener(v -> {
                mAuth.signOut();
                user = mAuth.getCurrentUser();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            });
            logOutOrLogIn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.login_icon,
                    0, 0, 0);
        }
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (menuItem.getItemId() == R.id.acc_button) {
                if (user != null) {
//                    Toast.makeText(this, "acc_button", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(this, AnimePageActivity.class));
//                    drawerLayout.close();
                    return true;
                } else {
                    Toast.makeText(this, "Зарегистрируйтесь или " +
                            "войдите для редактирования аккаунта", Toast.LENGTH_LONG).show();
                    return true;
                }
            } else if (menuItem.getItemId() == R.id.home_page) {
                AnimeMainList animeMainList = new AnimeMainList();
                ft.replace(R.id.frag_layout, animeMainList);
                ft.commit();
                drawerLayout.close();
                return true;
            } else if (menuItem.getItemId() == R.id.random_btn) {
                Toast.makeText(this, "rand_btn", Toast.LENGTH_LONG).show();
                drawerLayout.close();
                return true;
            } else if (menuItem.getItemId() == R.id.favorite_titles_btn) {
                FavoriteList favoriteList = new FavoriteList();
                ft.replace(R.id.frag_layout, favoriteList);
                ft.commit();
                drawerLayout.close();
                return true;
            } else if (menuItem.getItemId() == R.id.izbran) {
                IzbranFragment izbranFragment = new IzbranFragment();
                ft.replace(R.id.frag_layout, izbranFragment);
                ft.commit();
                drawerLayout.close();
                return true;
            } else if (menuItem.getItemId() == R.id.watched_btn) {
                FragmentWatched fragmentWatched = new FragmentWatched();
                ft.replace(R.id.frag_layout, fragmentWatched);
                ft.commit();
                drawerLayout.close();
                return true;
            } else {
                return true;
            }
        });

    }

    @Override
    public void OnAnimeSearchClick(AnimeItem anime, int position) {
        Bundle bundle = new Bundle();
        searchEdText.clearFocus();


//        startActivity(new Intent(getContext(), AnimePageActivity.class),bundle);
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
                                        Intent intent = new Intent(getApplicationContext(), AnimePageActivity.class);
                                        intent.putExtra("bund", bundle);
                                        intent.putExtra("index", position);
                                        intent.putExtra("id", anime.getId());
                                        searchEdText.getText().clear();
                                        startActivity(intent);
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
                                    Intent intent = new Intent(getApplicationContext(), AnimePageActivity.class);
                                    intent.putExtra("bund", bundle);
                                    intent.putExtra("index", position);
                                    intent.putExtra("id", anime.getId());
                                    searchEdText.getText().clear();
                                    startActivity(intent);
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
                Log.d("error_code", "Srabotal_failure");
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
                            Intent intent = new Intent(getApplicationContext(), AnimePageActivity.class);
                            intent.putExtra("bund", bundle);
                            intent.putExtra("index", position);
                            intent.putExtra("id", anime.getId());
                            searchEdText.getText();
                            startActivity(intent);
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
}