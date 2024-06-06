
package com.mirea.kt.ribo.smotrimanime.utils_internet.shikikomori_object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AnimesObj {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("russian")
    @Expose
    private String russian;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("episodes")
    @Expose
    private int episodes;
    @SerializedName("episodes_aired")
    @Expose
    private int episodesAired;
    @SerializedName("aired_on")
    @Expose
    private String airedOn;
    @SerializedName("released_on")
    @Expose
    private Object releasedOn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRussian() {
        return russian;
    }

    public void setRussian(String russian) {
        this.russian = russian;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public float getScore() {
        return Float.parseFloat(score);
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public int getEpisodesAired() {
        return episodesAired;
    }

    public void setEpisodesAired(int episodesAired) {
        this.episodesAired = episodesAired;
    }

    public String getAiredOn() {
        return airedOn;
    }

    public void setAiredOn(String airedOn) {
        this.airedOn = airedOn;
    }

    public Object getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(Object releasedOn) {
        this.releasedOn = releasedOn;
    }

    @Override
    public String toString() {
        return "AnimesObj{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", russian='" + russian + '\'' +
                ", image=" + image +
                ", url='" + url + '\'' +
                ", kind='" + kind + '\'' +
                ", score='" + score + '\'' +
                ", status='" + status + '\'' +
                ", episodes=" + episodes +
                ", episodesAired=" + episodesAired +
                ", airedOn='" + airedOn + '\'' +
                ", releasedOn=" + releasedOn +
                '}';
    }
}
