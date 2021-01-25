package com.example.watchroom;

public class Video {

    private String name;
    private String time;
    private String views;
    private String description;

    public Video(String name, String time, String views, String description) {
        this.name = name;
        this.time = time;
        this.views = views;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
