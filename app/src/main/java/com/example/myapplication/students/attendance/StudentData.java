package com.example.myapplication.students.attendance;

public class StudentData {

    private String name, email, enroll, department, semester, phone, key, password;
    private String idPhoto, hasApplied, isApproved, isProfileComplete, isVolunteer, profilePhoto, url;

    public StudentData() {
    }

    public StudentData(String name, String email, String enroll, String department, String semester, String phone, String key, String password, String idPhoto, String hasApplied, String isApproved, String isProfileComplete, String isVolunteer, String profilePhoto, String url) {
        this.name = name;
        this.email = email;
        this.enroll = enroll;
        this.department = department;
        this.semester = semester;
        this.phone = phone;
        this.key = key;
        this.password = password;
        this.idPhoto = idPhoto;
        this.hasApplied = hasApplied;
        this.isApproved = isApproved;
        this.isProfileComplete = isProfileComplete;
        this.isVolunteer = isVolunteer;
        this.profilePhoto = profilePhoto;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnroll() {
        return enroll;
    }

    public void setEnroll(String enroll) {
        this.enroll = enroll;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getHasApplied() {
        return hasApplied;
    }

    public void setHasApplied(String hasApplied) {
        this.hasApplied = hasApplied;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public String getIsProfileComplete() {
        return isProfileComplete;
    }

    public void setIsProfileComplete(String isProfileComplete) {
        this.isProfileComplete = isProfileComplete;
    }

    public String getIsVolunteer() {
        return isVolunteer;
    }

    public void setIsVolunteer(String isVolunteer) {
        this.isVolunteer = isVolunteer;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
