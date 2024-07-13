package com.mirea.kt.ribo.smotrimanime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DefaultDataSource;
import androidx.media3.exoplayer.DefaultRenderersFactory;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.hls.HlsMediaSource;
import androidx.media3.ui.PlayerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mirea.kt.ribo.smotrimanime.rec_views.AnimeItem;
import com.mirea.kt.ribo.smotrimanime.utils_for_storage.AnimePageInfo;
import com.mirea.kt.ribo.smotrimanime.utils_for_storage.DBManager;
import com.mirea.kt.ribo.smotrimanime.utils_for_storage.SQLHelper;
import com.squareup.picasso.Picasso;

public class AnimePageActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean edited_obj;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.anime_page_menu, menu);
        AnimeItem animeFromDatabase = dbManager.getAnime(animeInfo.getIdShiki());
        if (animeFromDatabase.getFavorite() == 1) {
            menu.findItem(R.id.fav_menu_btn).setIcon(R.drawable.ic_action_favorite);
            Drawable fav_icon = menu.findItem(R.id.fav_menu_btn).getIcon();
            if (fav_icon != null) {
                fav_icon.mutate();
                fav_icon.setColorFilter(getResources().getColor(R.color.color_red), PorterDuff.Mode.SRC_ATOP);
            }
        } else {
            Drawable fav_icon = menu.findItem(R.id.fav_menu_btn).getIcon();
            if (fav_icon != null) {
                fav_icon.mutate();
                fav_icon.setColorFilter(getResources().getColor(R.color.gray_finish), PorterDuff.Mode.SRC_ATOP);
            }
        }
        if (animeFromDatabase.getIzbran() == 1) {
            menu.findItem(R.id.izbran_menu_btn).setIcon(R.drawable.bookmark_full);
            Drawable icon = menu.findItem(R.id.izbran_menu_btn).getIcon();
            if (icon != null) {
                icon.mutate();
                icon.setColorFilter(getResources().getColor(R.color.color_yellow), PorterDuff.Mode.SRC_ATOP);
            }
        } else {
            Drawable icon = menu.findItem(R.id.izbran_menu_btn).getIcon();
            if (icon != null) {
                icon.mutate();
                icon.setColorFilter(getResources().getColor(R.color.gray_finish), PorterDuff.Mode.SRC_ATOP);
            }
        }
        if (animeFromDatabase.getWatched() == 1) {
            Log.d("WatchedOrNo", "true");
            Drawable icon = menu.findItem(R.id.watched_menu_btn).getIcon();
            if (icon != null) {
                icon.mutate();
                icon.setColorFilter(getResources().getColor(R.color.color_green), PorterDuff.Mode.SRC_ATOP);
            }
        } else {
            Drawable icon = menu.findItem(R.id.watched_menu_btn).getIcon();
            if (icon != null) {
                icon.mutate();
                icon.setColorFilter(getResources().getColor(R.color.gray_finish), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;
    }

    DBManager dbManager;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return super.onOptionsItemSelected(item);
        edited_obj = true;
        AnimeItem animeDatabase = dbManager.getAnime(animeInfo.getIdShiki());
        Log.d("ANIME_INFO", animeDatabase.toString());
        if (android.R.id.home == item.getItemId()) {
            onBackPressed();
            finish();
            return true;
        }
//        if (user == null) {
//            Toast.makeText(this, "Для выполнения действия требуется авторизация", Toast.LENGTH_SHORT).show();
//            return true;
//        }
        if ((item.getItemId() == R.id.fav_menu_btn || item.getItemId() == R.id.izbran_menu_btn ||
                item.getItemId() == R.id.watched_menu_btn) && user != null) {
            if (item.getItemId() == R.id.fav_menu_btn) {
                if (animeDatabase.getFavorite() == 0) {
                    boolean result = dbManager.addFavoriteAnime(animeInfo.getIdShiki());
                    if (result) {
                        item.setIcon(R.drawable.ic_action_favorite);
                        Drawable icon = item.getIcon();
                        if (icon != null) {
                            icon.mutate();
                            icon.setColorFilter(getResources().getColor(R.color.color_red), PorterDuff.Mode.SRC_ATOP);
                        }
                        Toast.makeText(this, "Добавленно в любимое", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Ошибка при добавлении", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    boolean result = dbManager.diasableFavoriteAnime(animeInfo.getIdShiki());
                    if (result) {
                        item.setIcon(R.drawable.empty_favorite);
                        Drawable icon = item.getIcon();
                        if (icon != null) {
                            icon.mutate();
                            icon.setColorFilter(getResources().getColor(R.color.gray_finish), PorterDuff.Mode.SRC_ATOP);
                        }
                        Toast.makeText(this, "Удалено из любимых", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Ошибка при удалении", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            } else if (item.getItemId() == R.id.izbran_menu_btn) {
                if (animeDatabase.getIzbran() == 0) {
                    boolean result = dbManager.addIzbranAnime(animeInfo.getIdShiki());
                    if (result) {
                        item.setIcon(R.drawable.bookmark_full);
                        Drawable icon = item.getIcon();
                        if (icon != null) {
                            icon.mutate();
                            icon.setColorFilter(getResources().getColor(R.color.color_yellow), PorterDuff.Mode.SRC_ATOP);
                        }
                        Toast.makeText(this, "Добавленно в избранное", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Ошибка при добавлении", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    boolean result = dbManager.diasableIzbranAnime(animeInfo.getIdShiki());
                    if (result) {
                        item.setIcon(R.drawable.bookmark_empty);
                        Drawable icon = item.getIcon();
                        if (icon != null) {
                            icon.mutate();
                            icon.setColorFilter(getResources().getColor(R.color.gray_finish), PorterDuff.Mode.SRC_ATOP);
                        }
                        Toast.makeText(this, "Удалено из избранных", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Ошибка при удалении", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            } else if (item.getItemId() == R.id.watched_menu_btn) {
                if (animeDatabase.getWatched() == 0) {
                    boolean result = dbManager.addWatchedAnime(animeInfo.getIdShiki());
                    if (result) {
                        Drawable icon = item.getIcon();
                        if (icon != null) {
                            icon.mutate();
                            icon.setColorFilter(getResources().getColor(R.color.color_green), PorterDuff.Mode.SRC_ATOP);
                        }
                        Toast.makeText(this, "Добавленно в просмотренные", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Ошибка при добавлении", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    boolean result = dbManager.diasableWatchedAnime(animeInfo.getIdShiki());
                    if (result) {
                        Drawable icon = item.getIcon();
                        if (icon != null) {
                            icon.mutate();
                            icon.setColorFilter(getResources().getColor(R.color.gray_finish), PorterDuff.Mode.SRC_ATOP);
                        }
                        Toast.makeText(this, "Удалено из просмотренных", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Ошибка при удалении", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        } else if (user != null) {
            Toast.makeText(this, "Для выполнения действия требуется авторизация", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //    String video = "<iframe src=https://video.sibnet.ru/shell.php?videoid=5526533&share=1></iframe>";
//    String video ="<iframe src=\"//anivod.com/seria/1304983/86e70272c7bc00553d3e0c10239851e8/720p\" width=\"607\" height=\"360\" frameborder=\"0\" AllowFullScreen allow=\"autoplay *; fullscreen *\"></iframe>"
//    String video = "<video preload=\"none\" crossorigin=\"anonymous\" src=\"blob:https://dori.farmedia.live/51ee8347-a312-4981-9b9d-d7907bf853a4\" x-webkit-airplay=\"allow\" disableremoteplayback=\"true\" pip=\"false\" controlslist=\"nodownload\" style=\"position: static; width: 100%; height: 100%; object-fit: contain; transition: filter 0.2s linear 0s; min-height: auto; max-height: none; min-width: auto; max-width: none; left: 0px; top: 0px;\"></video>";
//    String video = "<iframe height="+(width*9/16+1)/density+" width="+(width/density-(density*20+5))+" src=https://sovetromantica.com/embed/episode_1501_1-subtitles frameborder=\"0\" allowfullscreen></iframe>";
//        videoPlayer.loadData(video,"text/html","utf-8");
//    private VideoView videoPlayer;
    private PlayerView playerView;
    private ExoPlayer player;
    private ImageButton poster;
    private AnimePageInfo animeInfo;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private TextView titleAnime, descrAnime, PG, genresDescr, seriesCnt;

    @OptIn(markerClass = UnstableApi.class)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edited_obj = false;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        setContentView(R.layout.activity_anime_page);
        initElements();
        setSupportActionBar(toolbar);
        dbManager = new DBManager(new SQLHelper(this, "animeDatabase.db",
                null, 4));
        ActionBar actionBar = getSupportActionBar();
//        try {
//            MediaCodec.createByCodecName("OMX.google.aac.decoder");
//            MediaCodec.createByCodecName("c2.android.aac.decoder");
//            MediaCodec.createByCodecName("c2.android.avc.decoder");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        int width = displaymetrics.widthPixels;  // deprecated
//        int height = displaymetrics.heightPixels;
//        float density = displaymetrics.density;
//        episode_1_1080p.m3u8
        Bundle bundle = getIntent().getBundleExtra("bund");
        animeInfo = new AnimePageInfo(bundle, getApplicationContext(), this);
        setValuesInViews();
//        Log.d("Size_screen","width: "+width+"\nheight: "+height+"\ndens: "+density);
//        String video;
//        video = animeInfo.convertToUrlSovetRom(1,1);
        //playVideo();//"https://video.blender.org/download/videos/3d95fb3d-c866-42c8-9db1-fe82f48ccb95-804.mp4");//video);

//        videoPlayer.setVideoURI(Uri.parse(video));//"https://sovetromantica.com/embed/episode_1501_1-subtitles"));
    }

    public void initElements() {
        playerView = findViewById(R.id.videoPlayer);
        poster = findViewById(R.id.anime_image_prev);
        titleAnime = findViewById(R.id.title_anime);
        descrAnime = findViewById(R.id.descrition_text);
        PG = findViewById(R.id.PG_descript);
        genresDescr = findViewById(R.id.genres_descr);
        seriesCnt = findViewById(R.id.series_number);
        toolbar = findViewById(R.id.toolbar_anime_page);
    }

    public void setValuesInViews() {
        titleAnime.setText(animeInfo.getNameAnime());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            descrAnime.setText(Html.fromHtml(animeInfo.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            descrAnime.setText(Html.fromHtml(animeInfo.getDescription()));
        }
        PG.setText(animeInfo.getPG());
        genresDescr.setText(animeInfo.getGenres());
        Log.d("series", "" + animeInfo.getSeries());
        seriesCnt.setText(String.valueOf(animeInfo.getSeries()));
        poster.setOnClickListener(this);
        Picasso.get().load(animeInfo.getImageUrl()).error(R.drawable.no_image).into(poster);
    }

    //    @OptIn(markerClass = UnstableApi.class)
    @OptIn(markerClass = UnstableApi.class)
    private void playVideo(String url) {
        DefaultDataSource.Factory datasource = new DefaultDataSource.Factory(this);//.setUserAgent(Util.getUserAgent(this, getString(R.string.app_name)));

        HlsMediaSource hlsMediaSource =
                new HlsMediaSource.Factory(datasource)
                        .setAllowChunklessPreparation(false)
                        .createMediaSource(MediaItem.fromUri(url));
        DefaultRenderersFactory rf = new DefaultRenderersFactory(this.getApplicationContext())
                .setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER);
        player = new ExoPlayer.Builder(this).setRenderersFactory(rf).build();
        playerView.setPlayer(player);
        player.setMediaSource(hlsMediaSource);
//    DecoderCounters
        Log.d("HLSSSSS", "" + player.getPlayerError() + "\n" +
                "" + player.getVideoDecoderCounters());
//    player.setPlayWhenReady(true);
        player.prepare();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("edited", edited_obj);
        intent.putExtra("index", getIntent().getIntExtra("index", -1));
        intent.putExtra("id", animeInfo.getIdShiki());
        Log.d("player", "index" + intent.getIntExtra("index", -1));
        this.setResult(RESULT_OK, intent);
        super.onBackPressed();
        if (animeInfo.isHasVideo() && player != null) {
            player.pause();
        }
        Log.d("player", "" + edited_obj + "\nid: " + animeInfo.getIdShiki());
        finish();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.putExtra("edited", edited_obj);
        intent.putExtra("index", getIntent().getIntExtra("index", -1));
        intent.putExtra("id", animeInfo.getIdShiki());
        Log.d("player", "index" + intent.getIntExtra("index", -1));
        this.setResult(RESULT_OK, intent);
        super.onDestroy();

        if (animeInfo.isHasVideo() && player != null) {
            player.pause();
        }
//        Log.d("player",""+edited_obj+"\nid: "+animeInfo.getIdShiki());
//        Intent intent = new Intent();
//        intent.putExtra("edited",edited_obj);
//        intent.putExtra("index",getIntent().getIntExtra("index",-1));
//        intent.putExtra("id",animeInfo.getIdShiki());
//        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (animeInfo.getScaningVideo() == "ready" && animeInfo.isHasVideo()) {
            if (animeInfo.isHasVideo()) {
                Animation animUpIMageBtn = AnimationUtils.loadAnimation(this, R.anim.element_out);
                poster.startAnimation(animUpIMageBtn);
                poster.setVisibility(View.GONE);
                Animation animInvideo = AnimationUtils.loadAnimation(this, R.anim.inner_video);
                animInvideo.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        playVideo(AnimePageInfo.convertToUrlSovetRom(
                                1, animeInfo.getVersion(),
                                animeInfo.getSovetId(), animeInfo.getFolderInSite()
                        ));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                playerView.startAnimation(animInvideo);
                playerView.setVisibility(View.VISIBLE);
//                player.setPlayWhenReady(true);
            } else {
                Log.d("No_video","first_else");
                Animation noVideoAnimation = AnimationUtils.loadAnimation(this,
                        R.anim.no_anime_in_page);
                poster.startAnimation(noVideoAnimation);
                Toast.makeText(this, "На данной странице нет видео", Toast.LENGTH_SHORT).show();
            }
        } else if (animeInfo.isHasVideo()) {
            Animation anim = AnimationUtils.loadAnimation(this,
                    R.anim.selected_item);
            poster.startAnimation(anim);
            Toast.makeText(this, "Видео готовится", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("No_video","second_else");
            Animation noVideoAnimation = AnimationUtils.loadAnimation(this,
                    R.anim.no_anime_in_page);
            poster.startAnimation(noVideoAnimation);
            Toast.makeText(this, "На данной странице нет видео", Toast.LENGTH_SHORT).show();
        }
    }
}