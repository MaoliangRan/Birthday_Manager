package com.example.birthday_manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class FragmentBirthday extends Fragment {

    private ListView mlv;
    private ArrayList<person> itemlist;
    private MyListViewAdapter myadapter;
    private TextView tip;

    Context context;
    SQLiteDatabase db;
    private Cursor cursor;

    private String name, remarkinfo, birthday;
    private int remind_time, sex, year, month, day, age, restdays;
    private byte[] in;
    private Bitmap face;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_birthday, container, false);

        mlv = (ListView) view.findViewById(R.id.list_view);
        tip = (TextView) view.findViewById(R.id.tip);
        itemlist = new ArrayList<person>();

        MainActivity mainActivity = (MainActivity) getActivity();
        context = getActivity().getApplicationContext();
        db = new DBhelper(context).getWritableDatabase();
        cursor = db.rawQuery("select * from person", null);

        int count = 0;
        String tempname = null;
        int tempyear = 0;
        int tempmonth = 0;
        int tempday = 0;
        int tempremindtime = 0;

        if (cursor.getCount() == 0)
            tip.setText("点击右上角即可添加生日哦~");
        while (cursor.moveToNext()) {
            remind_time = cursor.getInt(cursor.getColumnIndex("remind_time"));
            name = cursor.getString(cursor.getColumnIndex("name"));
            sex = cursor.getInt(cursor.getColumnIndex("sex"));
            remarkinfo = cursor.getString(cursor.getColumnIndex("remarkinfo"));
            year = cursor.getInt(cursor.getColumnIndex("year"));
            month = cursor.getInt(cursor.getColumnIndex("month"));
            day = cursor.getInt(cursor.getColumnIndex("day"));
            birthday = month + "月" + day + "日";
            if (cursor.getBlob(cursor.getColumnIndex("face")) != null) {
                in = cursor.getBlob(cursor.getColumnIndex("face"));
                face = BitmapFactory.decodeByteArray(in, 0, in.length);
            } else {
                if (sex == 0)
                    face = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.head_male);
                else
                    face = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.head_female);
            }
            person p = new person(name, face, remarkinfo, year, month, day);
            if (count == 0) {
                restdays = p.getRestdays();
                tempname = name;
                tempyear = year;
                tempmonth = month;
                tempday = day;
                tempremindtime = remind_time;
            }
            //将最近生日的人进行通知
            if (restdays <= p.getRestdays())
                mainActivity.setnotify(tempname, restdays, tempyear, tempmonth, tempday, tempremindtime);
            else
                mainActivity.setnotify(name, p.getRestdays(), year, month, day, remind_time);

            itemlist.add(p);
            count = 1;
        }
        db.close();

        //itemlist排序
        Collections.sort(itemlist, new Comparator<person>() {
            @Override
            public int compare(person p1, person p2) {
                Integer r1 = p1.getRestdays();
                Integer r2 = p2.getRestdays();
                //可以按User对象的其他属性排序，只要属性支持compareTo方法
                return r1.compareTo(r2);
            }
        });
        //显示listview
        myadapter = new MyListViewAdapter(itemlist, R.layout.list_item_style, this.getContext());
        mlv.setAdapter(myadapter);

        //itemonclicklistenner
//        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                new android.support.v7.app.AlertDialog.Builder(getContext())
//                        .setTitle("标题")
//                        .setMessage("请选择操作")
//                        .setPositiveButton("确定", null)
//                        .setNegativeButton("删除",null)
//                        .show();
//
//            }
//        });

        return view;
    }

}
