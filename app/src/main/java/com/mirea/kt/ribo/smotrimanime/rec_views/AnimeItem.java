package com.mirea.kt.ribo.smotrimanime.rec_views;

public class AnimeItem {
    private String titleName,genres,description,imageUrl,typeAnime;
    private float raiting;
    private int favorite,watched,id;

    public AnimeItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AnimeItem(String titleName, String genres, String description, String imageUrl, String typeAnime, float raiting, int favorite, int watched, int id) {
        this.titleName = titleName;
        this.genres = genres;
        this.description = description;
        this.imageUrl = imageUrl;
        this.typeAnime = typeAnime;
        this.raiting = raiting;
        this.favorite = favorite;
        this.watched = watched;
        this.id = id;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getWatched() {
        return watched;
    }

    public void setWatched(int watched) {
        this.watched = watched;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTypeAnime() {
        return typeAnime;
    }

    public void setTypeAnime(String typeAnime) {
        this.typeAnime = typeAnime;
    }

    public float getRaiting() {
        return raiting;
    }

    public void setRaiting(float raiting) {
        this.raiting = raiting;
    }
}
