package com.example.myapplication.admin.event;

public class EventData {

    String eventname, image, date, url, key;

    public EventData() {
    }

    public EventData(String eventname, String image, String date, String url, String key) {
        this.eventname = eventname;
        this.image = image;
        this.date = date;
        this.url = url;
        this.key = key;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
