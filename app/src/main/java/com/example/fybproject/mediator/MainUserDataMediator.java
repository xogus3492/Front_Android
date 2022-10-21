package com.example.fybproject.mediator;

public class MainUserDataMediator {
    private static int age;
    private static char gender;

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        MainUserDataMediator.age = age;
    }

    public static char getGender() {
        return gender;
    }

    public static void setGender(char gender) {
        MainUserDataMediator.gender = gender;
    }
}
