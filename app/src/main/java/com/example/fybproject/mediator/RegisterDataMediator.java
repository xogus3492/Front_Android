package com.example.fybproject.mediator;

import static android.service.controls.ControlsProviderService.TAG;

import android.util.Log;

public class RegisterDataMediator {
    private static String email;
    private static String pw;
    private static String name;
    private static int age;
    private static int height;
    private static int weight;
    private static String gender;
    private static String form;
    private static String sholder;
    private static String pelvis;
    private static String leg;

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        RegisterDataMediator.email = email;
    }

    public static String getPw() {
        return pw;
    }

    public static void setPw(String pw) {
        RegisterDataMediator.pw = pw;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        RegisterDataMediator.name = name;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        RegisterDataMediator.age = age;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        RegisterDataMediator.height = height;
    }

    public static int getWeight() {
        return weight;
    }

    public static void setWeight(int weight) {
        RegisterDataMediator.weight = weight;
    }

    public static String getGender() { return gender; }

    public static void setGender(String gender) { RegisterDataMediator.gender = gender; }

    public static String getForm() {
        return form;
    }

    public static void setForm(String form) {
        RegisterDataMediator.form = form;
    }

    public static String getSholder() {
        return sholder;
    }

    public static void setSholder(String sholder) {
        RegisterDataMediator.sholder = sholder;
    }

    public static String getPelvis() {
        return pelvis;
    }

    public static void setPelvis(String pelvis) {
        RegisterDataMediator.pelvis = pelvis;
    }

    public static String getLeg() {
        return leg;
    }

    public static void setLeg(String leg) {
        RegisterDataMediator.leg = leg;
    }
}
