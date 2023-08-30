package com.example.birthday_manager;

import android.graphics.Bitmap;

//记录用户设置及用户信息，已弃用
public class user {

    private int voice, vibrate, bar;
    private String name, birthday;
    private Bitmap face;

    user(String name, String birthday, Bitmap face, int voice, int vibrate, int bar) {
        this.name = name;
        this.birthday = birthday;
        this.face = face;
        this.voice = voice;
        this.vibrate = vibrate;
        this.bar = bar;
    }

}