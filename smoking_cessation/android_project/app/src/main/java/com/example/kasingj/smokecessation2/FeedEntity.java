package com.example.kasingj.smokecessation2;

/**
 * Created by kasingj on 11/6/16.
 */
public class FeedEntity {
    public FeedEntity(int id, String date, String description, int likes, int dislikes) {
        this.id = id;
        this.description = description;
        this.likes = likes;
        this.dislikes = dislikes;
    }


    private int id;
    private int dislikes;
    private int likes;
    private int feedId;
    private String description;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserServerId() {
        return userServerId;
    }

    public void setUserServerId(int userServerId) {
        this.userServerId = userServerId;
    }

    private int userServerId;
    private int date;


}
