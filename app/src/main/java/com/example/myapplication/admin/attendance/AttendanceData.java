package com.example.myapplication.admin.attendance;

public class AttendanceData {

    private String key, enroll, name;

    public AttendanceData() {
    }

    public AttendanceData(String key, String enroll, String name) {
        this.key = key;
        this.enroll = enroll;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEnroll() {
        return enroll;
    }

    public void setEnroll(String enroll) {
        this.enroll = enroll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
