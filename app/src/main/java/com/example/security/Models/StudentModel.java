package com.example.security.Models;

public class StudentModel {
    String name, qr_code, group, phone_number;

    public StudentModel() {

    }

    public StudentModel(String name, String qr_code, String group, String phone_number) {
        this.name = name;
        this.qr_code = qr_code;
        this.group = group;
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public String getQr_code() {
        return qr_code;
    }

//    public void setQr_code(String qr_code) {
//        this.qr_code = qr_code;
//    }

    public String getGroup() {
        return group;
    }

//    public void setGroup(String group) {
//        this.group = group;
//    }

    public String getPhone_number() {
        return phone_number;
    }

//    public void setPhone_number(String phone_number) {
//        this.phone_number = phone_number;
//    }
}
