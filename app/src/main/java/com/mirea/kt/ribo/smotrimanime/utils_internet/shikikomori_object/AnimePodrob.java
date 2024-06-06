
package com.mirea.kt.ribo.smotrimanime.utils_internet.shikikomori_object;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AnimePodrob {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    private Integer episodes;
    @SerializedName("episodes_aired")
    @Expose
    private Integer episodesAired;
    @SerializedName("aired_on")
    @Expose
    private Object airedOn;
    @SerializedName("released_on")
    @Expose
    private Object releasedOn;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("english")
    @Expose
    private List<Object> english;
    @SerializedName("japanese")
    @Expose
    private List<Object> japanese;
    @SerializedName("synonyms")
    @Expose
    private List<Object> synonyms;
    @SerializedName("license_name_ru")
    @Expose
    private String licenseNameRu;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("description_html")
    @Expose
    private String descriptionHtml;
    @SerializedName("description_source")
    @Expose
    private String descriptionSource;
    @SerializedName("franchise")
    @Expose
    private String franchise;
    @SerializedName("favoured")
    @Expose
    private Boolean favoured;
    @SerializedName("anons")
    @Expose
    private Boolean anons;
    @SerializedName("ongoing")
    @Expose
    private Boolean ongoing;
    @SerializedName("thread_id")
    @Expose
    private Integer threadId;
    @SerializedName("topic_id")
    @Expose
    private Integer topicId;
    @SerializedName("myanimelist_id")
    @Expose
    private Integer myanimelistId;
    @SerializedName("rates_scores_stats")
    @Expose
    private List<Object> ratesScoresStats;
    @SerializedName("rates_statuses_stats")
    @Expose
    private List<Object> ratesStatusesStats;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("next_episode_at")
    @Expose
    private String nextEpisodeAt;
    @SerializedName("fansubbers")
    @Expose
    private List<Object> fansubbers;
    @SerializedName("fandubbers")
    @Expose
    private List<Object> fandubbers;
    @SerializedName("licensors")
    @Expose
    private List<Object> licensors;
    @SerializedName("genres")
    @Expose
    private List<HashMap<String,String>> genres;
    @SerializedName("studios")
    @Expose
    private List<Object> studios;
    @SerializedName("videos")
    @Expose
    private List<Object> videos;
    @SerializedName("screenshots")
    @Expose
    private List<Object> screenshots;
    @SerializedName("user_rate")
    @Expose
    private Object userRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getScore() {
        return score;
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

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public Integer getEpisodesAired() {
        return episodesAired;
    }

    public void setEpisodesAired(Integer episodesAired) {
        this.episodesAired = episodesAired;
    }

    public Object getAiredOn() {
        return airedOn;
    }

    public void setAiredOn(Object airedOn) {
        this.airedOn = airedOn;
    }

    public Object getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(Object releasedOn) {
        this.releasedOn = releasedOn;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<Object> getEnglish() {
        return english;
    }

    public void setEnglish(List<Object> english) {
        this.english = english;
    }

    public List<Object> getJapanese() {
        return japanese;
    }

    public void setJapanese(List<Object> japanese) {
        this.japanese = japanese;
    }

    public List<Object> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<Object> synonyms) {
        this.synonyms = synonyms;
    }

    public String getLicenseNameRu() {
        return licenseNameRu;
    }

    public void setLicenseNameRu(String licenseNameRu) {
        this.licenseNameRu = licenseNameRu;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    public void setDescriptionHtml(String descriptionHtml) {
        this.descriptionHtml = descriptionHtml;
    }

    public String getDescriptionSource() {
        return descriptionSource;
    }

    public void setDescriptionSource(String descriptionSource) {
        this.descriptionSource = descriptionSource;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    public Boolean getFavoured() {
        return favoured;
    }

    public void setFavoured(Boolean favoured) {
        this.favoured = favoured;
    }

    public Boolean getAnons() {
        return anons;
    }

    public void setAnons(Boolean anons) {
        this.anons = anons;
    }

    public Boolean getOngoing() {
        return ongoing;
    }

    public void setOngoing(Boolean ongoing) {
        this.ongoing = ongoing;
    }

    public Integer getThreadId() {
        return threadId;
    }

    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getMyanimelistId() {
        return myanimelistId;
    }

    public void setMyanimelistId(Integer myanimelistId) {
        this.myanimelistId = myanimelistId;
    }

    public List<Object> getRatesScoresStats() {
        return ratesScoresStats;
    }

    public void setRatesScoresStats(List<Object> ratesScoresStats) {
        this.ratesScoresStats = ratesScoresStats;
    }

    public List<Object> getRatesStatusesStats() {
        return ratesStatusesStats;
    }

    public void setRatesStatusesStats(List<Object> ratesStatusesStats) {
        this.ratesStatusesStats = ratesStatusesStats;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNextEpisodeAt() {
        return nextEpisodeAt;
    }

    public void setNextEpisodeAt(String nextEpisodeAt) {
        this.nextEpisodeAt = nextEpisodeAt;
    }

    public List<Object> getFansubbers() {
        return fansubbers;
    }

    public void setFansubbers(List<Object> fansubbers) {
        this.fansubbers = fansubbers;
    }

    public List<Object> getFandubbers() {
        return fandubbers;
    }

    public void setFandubbers(List<Object> fandubbers) {
        this.fandubbers = fandubbers;
    }

    public List<Object> getLicensors() {
        return licensors;
    }

    public void setLicensors(List<Object> licensors) {
        this.licensors = licensors;
    }

    public ArrayList<String> getGenres() {
        ArrayList<String> genr = new ArrayList<>();
        for (HashMap<String,String> g:genres){
            genr.add(g.get("russian"));
        }
//        Log.d("fesadsad",genres.toString());
        return genr;
    }

    public void setGenres(List<HashMap<String,String>> genres) {
        this.genres = genres;
    }

    public List<Object> getStudios() {
        return studios;
    }

    public void setStudios(List<Object> studios) {
        this.studios = studios;
    }

    public List<Object> getVideos() {
        return videos;
    }

    public void setVideos(List<Object> videos) {
        this.videos = videos;
    }

    public List<Object> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Object> screenshots) {
        this.screenshots = screenshots;
    }

    public Object getUserRate() {
        return userRate;
    }

    public void setUserRate(Object userRate) {
        this.userRate = userRate;
    }

}
