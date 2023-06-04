package com.example.myapplication.students.attendance;

public class StudentData {

    private String name, email, enroll, department, semester, phone, key;

    public StudentData() {
    }

    public StudentData(String name, String email, String enroll, String department, String semester, String phone, String key) {
        this.name = name;
        this.email = email;
        this.enroll = enroll;
        this.department = department;
        this.semester = semester;
        this.phone = phone;
        this.key = key;
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
}
