package com.example.birthday_manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SettingActivity extends AppCompatActivity implements OnClickListener {

    Button backbtn;
    TextView backview;
    Button voice_setting_btn;
    Button vibrate_setting_btn;
    Button bar_setting_btn;
    RelativeLayout ring_setting;
    TextView default_ring;
    int voice_status, vibrate_status, bar_status;

    Context context;
    SQLiteDatabase db = null;
    Cursor cursor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        backbtn = (Button) findViewById(R.id.settingback);
        backview = (TextView) findViewById(R.id.settingbackt);
        backbtn.setOnClickListener(this);
        backview.setOnClickListener(this);

        voice_setting_btn = (Button) findViewById(R.id.voice_setting_btn);
        vibrate_setting_btn = (Button) findViewById(R.id.vibrate_setting_btn);
        bar_setting_btn = (Button) findViewById(R.id.bar_setting_btn);

        voice_setting_btn.setOnClickListener(this);
        vibrate_setting_btn.setOnClickListener(this);
        bar_setting_btn.setOnClickListener(this);

        ring_setting = (RelativeLayout) findViewById(R.id.ring_setting);
        default_ring = (TextView) findViewById(R.id.default_ring);
        ring_setting.setOnClickListener(this);

        context = this.getApplicationContext();
        db = new DBhelper(context).getWritableDatabase();
        cursor = db.rawQuery("select * from user_info", null);
        cursor.moveToFirst();
        voice_status = cursor.getInt(cursor.getColumnIndex("voice"));
        vibrate_status = cursor.getInt(cursor.getColumnIndex("vibrate"));
        bar_status = cursor.getInt(cursor.getColumnIndex("bar"));
        if (voice_status == 1)
            voice_setting_btn.setBackgroundResource(R.drawable.open_color);
        else
            voice_setting_btn.setBackgroundResource(R.drawable.closed_color);

        if (vibrate_status == 1)
            vibrate_setting_btn.setBackgroundResource(R.drawable.open_color);
        else
            vibrate_setting_btn.setBackgroundResource(R.drawable.closed_color);

        if (bar_status == 1)
            bar_setting_btn.setBackgroundResource(R.drawable.open_color);
        else
            bar_setting_btn.setBackgroundResource(R.drawable.closed_color);
        db.close();


        //chmod -R 755 /data/user/0/cn.studyjams.s2.sj0178.ranml_birthday_manager/files/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settingback:
                finish();
                break;
            case R.id.settingbackt:
                finish();
                break;
            case R.id.voice_setting_btn:
                db = new DBhelper(context).getWritableDatabase();
                cursor = db.rawQuery("select * from user_info", null);
                cursor.moveToFirst();
                voice_status = cursor.getInt(cursor.getColumnIndex("voice"));
                if (voice_status == 1) {
                    voice_setting_btn.setBackgroundResource(R.drawable.closed_color);
                    db.execSQL("update user_info set voice=0 where voice=1");
                } else {
                    voice_setting_btn.setBackgroundResource(R.drawable.open_color);
                    db.execSQL("update user_info set voice=1 where voice=0");
                }
                db.close();
                break;
            case R.id.vibrate_setting_btn:
                db = new DBhelper(context).getWritableDatabase();
                cursor = db.rawQuery("select * from user_info", null);
                cursor.moveToFirst();
                vibrate_status = cursor.getInt(cursor.getColumnIndex("vibrate"));
                if (vibrate_status == 1) {
                    vibrate_setting_btn.setBackgroundResource(R.drawable.closed_color);
                    db.execSQL("update user_info set vibrate=0 where vibrate=1");
                } else {
                    vibrate_setting_btn.setBackgroundResource(R.drawable.open_color);
                    db.execSQL("update user_info set vibrate=1 where vibrate=0");
                }
                db.close();
                break;
            case R.id.bar_setting_btn:
                db = new DBhelper(context).getWritableDatabase();
                cursor = db.rawQuery("select * from user_info", null);
                cursor.moveToFirst();
                bar_status = cursor.getInt(cursor.getColumnIndex("bar"));
                if (bar_status == 1) {
                    bar_setting_btn.setBackgroundResource(R.drawable.closed_color);
                    db.execSQL("update user_info set bar=0 where bar=1");
                } else {
                    bar_setting_btn.setBackgroundResource(R.drawable.open_color);
                    db.execSQL("update user_info set bar=1 where bar=0");
                }
                db.close();
                break;
            case R.id.ring_setting:
                //暂时不做这个
                break;
        }
    }
}
