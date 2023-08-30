package com.example.birthday_manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class FragmentMine extends Fragment implements OnClickListener {
    private RelativeLayout supportus;
    private RelativeLayout share;
    private RelativeLayout send;
    private RelativeLayout setting;
    private RelativeLayout manage;
    private Button modify_user, myface;
    private TextView myname, mybirthday;


    private String name, birthday;
    private Bitmap bitmap;
    private Drawable drawable_bitmap;
    private int status;

    Context context;
    SQLiteDatabase db;
    Cursor cursor = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        supportus = (RelativeLayout) view.findViewById(R.id.suportus);
        supportus.setOnClickListener(this);
        share = (RelativeLayout) view.findViewById(R.id.share);
        share.setOnClickListener(this);
        manage = (RelativeLayout) view.findViewById(R.id.manage);
        manage.setOnClickListener(this);
        send = (RelativeLayout) view.findViewById(R.id.send);
        send.setOnClickListener(this);
        setting = (RelativeLayout) view.findViewById(R.id.setting);
        setting.setOnClickListener(this);
        modify_user = (Button) view.findViewById(R.id.modify_user);
        modify_user.setOnClickListener(this);

        myname = (TextView) view.findViewById(R.id.mine_name);
        mybirthday = (TextView) view.findViewById(R.id.mine_birthday);
        myface = (Button) view.findViewById(R.id.mine_face);

        //读取数据库信息进行显示
        context = getContext().getApplicationContext();
        db = new DBhelper(context).getWritableDatabase();
        cursor = db.rawQuery("select * from user_info", null);
        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex("name"));
            birthday = cursor.getString(cursor.getColumnIndex("birthday"));
            status = cursor.getInt(cursor.getColumnIndex("status"));

            if (status != 1) {
                byte[] in = cursor.getBlob(cursor.getColumnIndex("face"));
                bitmap = BitmapFactory.decodeByteArray(in, 0, in.length);
            }

        }
        db.close();

        myname.setText(name);
        mybirthday.setText(birthday);
        if (status != 1)
            myface.setBackground(new BitmapDrawable(bitmap));

        return view;
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        switch (v.getId()) {
            case R.id.suportus:
                mainActivity.start_supportus();
                break;
            case R.id.share:
                mainActivity.start_share();
                break;
            case R.id.send:
                mainActivity.start_send();
                break;
            case R.id.setting:
                mainActivity.start_setting();
                break;
            case R.id.modify_user:
                mainActivity.start_edituser();
                break;
            case R.id.manage:
                mainActivity.start_manage();
                break;

        }
    }


}