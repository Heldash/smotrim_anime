
package com.mirea.kt.ribo.smotrimanime.utils_internet.sovetRomObj;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SovetRomanResSearch {

    @SerializedName("anime_description")
    @Expose
    private String animeDescription;
    @SerializedName("anime_episodes")
    @Expose
    private Integer animeEpisodes;
    @SerializedName("anime_folder")
    @Expose
    private String animeFolder;
    @SerializedName("anime_id")
    @Expose
    private Integer animeId;
    @SerializedName("anime_keywords")
    @Expose
    private String animeKeywords;
    @SerializedName("anime_name")
    @Expose
    private String animeName;
    @SerializedName("anime_name_russian")
    @Expose
    private String animeNameRussian;
    @SerializedName("anime_paused")
    @Expose
    private Integer animePaused;
    @SerializedName("anime_shikimori")
    @Expose
    private Integer animeShikimori;
    @SerializedName("anime_studio")
    @Expose
    private Integer animeStudio;
    @SerializedName("anime_year")
    @Expose
    private Integer animeYear;

    public String getAnimeDescription() {
        return animeDescription;
    }

    public void setAnimeDescription(String animeDescription) {
        this.animeDescription = animeDescription;
    }

    public Integer getAnimeEpisodes() {
        return animeEpisodes;
    }

    public void setAnimeEpisodes(Integer animeEpisodes) {
        this.animeEpisodes = animeEpisodes;
    }

    public String getAnimeFolder() {
        return animeFolder;
    }

    public void setAnimeFolder(String animeFolder) {
        this.animeFolder = animeFolder;
    }

    public int getAnimeId() {
        return animeId;
    }

    public void setAnimeId(Integer animeId) {
        this.animeId = animeId;
    }

    public String getAnimeKeywords() {
        return animeKeywords;
    }

    public void setAnimeKeywords(String animeKeywords) {
        this.animeKeywords = animeKeywords;
    }

    public String getAnimeName() {
        return animeName;
    }

    public void setAnimeName(String animeName) {
        this.animeName = animeName;
    }

    public String getAnimeNameRussian() {
        return animeNameRussian;
    }

    public void setAnimeNameRussian(String animeNameRussian) {
        this.animeNameRussian = animeNameRussian;
    }

    public Integer getAnimePaused() {
        return animePaused;
    }

    public void setAnimePaused(Integer animePaused) {
        this.animePaused = animePaused;
    }

    public Integer getAnimeShikimori() {
        return animeShikimori;
    }

    public void setAnimeShikimori(Integer animeShikimori) {
        this.animeShikimori = animeShikimori;
    }

    public Integer getAnimeStudio() {
        return animeStudio;
    }

    public void setAnimeStudio(Integer animeStudio) {
        this.animeStudio = animeStudio;
    }

    public Integer getAnimeYear() {
        return animeYear;
    }

    public void setAnimeYear(Integer animeYear) {
        this.animeYear = animeYear;
    }

}
