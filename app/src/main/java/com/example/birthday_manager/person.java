package com.example.birthday_manager;


import android.graphics.Bitmap;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class person {
    //id
    private int id;
    //年
    private int year;
    //性别
    private String sex;

    //姓名
    private String name;
    //头像
    private Bitmap face = null;
    //备注
    private String remarkinfo;
    //月
    private int month;
    //日
    private int day;
    //年龄
    private int age;
    private int remind_time;
    private int restdays;
    private Calendar calendar;

    //private static final int[] leapyear={31,29,31,30,31,30,31,31,30,31,30,31};
    //private static final int[] notleapyear={31,28,31,30,31,30,31,31,30,31,30,31};
    person(int id, String name, int year, int month, int day, int age, String sex, String remarkinfo, Bitmap face) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.age = age;
        this.sex = sex;
        this.remarkinfo = remarkinfo;
        this.face = face;
    }

    person(String name, Bitmap face, String remarkinfo, int year, int month, int day) {
        this.name = name;
        this.face = face;
        this.remarkinfo = remarkinfo;
        this.month = month;
        this.day = day;
        calendar = Calendar.getInstance();
        this.age = calendar.get(Calendar.YEAR) - year;
        String birthday = year + "-" + month + "-" + day;
        this.restdays = getBirthday(birthday);

    }

    person() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFace(Bitmap face) {
        this.face = face;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setremarkinfo(String remarkinfo) {
        this.remarkinfo = remarkinfo;
    }


    public Bitmap getFace() {
        return face;
    }

    public String getName() {
        return name;
    }

    public String getRemarkinfo() {
        return remarkinfo;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getAge() {
        return age;
    }

    public int getRestdays() {
        return restdays;
    }

    public int getBirthday(String birthday) {
        //String birthday = "2013-03-18";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);// 获得当前年份
        try {
            cal.setTime(formatter.parse(birthday));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int birthyear = cal.get(Calendar.YEAR);
        while (birthyear < yearNow) {
            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
            birthyear = cal.get(Calendar.YEAR);
        }
        Date ed = new Date();
        Date sd = cal.getTime();
        long days = 0;
        if ((ed.getTime() - sd.getTime()) / (3600 * 24 * 1000) < 0) {//>
            days = -((ed.getTime() - sd.getTime()) / (3600 * 24 * 1000)) + 1;
            return (int) days;
        } else {
            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
            sd = cal.getTime();
            days = -((ed.getTime() - sd.getTime()) / (3600 * 24 * 1000)) + 1;
            return (int) days;
        }
    }
}
