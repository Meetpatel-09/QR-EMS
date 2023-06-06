package com.example.myapplication.admin.event;

public class EventData {

    String event, fees, date, players, key;

    public EventData() {
    }

    public EventData(String event, String fees, String date, String players, String key) {
        this.event = event;
        this.fees = fees;
        this.date = date;
        this.players = players;
        this.key = key;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlayers() {
        return players;
    }

    public void setPlayers(String players) {
        this.players = players;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
