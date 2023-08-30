package com.example.birthday_manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private FragmentHome fragmentHome;
    private FragmentBirthday fragmentBirthday;
    private FragmentMine fragmentMine;
    public FragmentWebView webView;

    public String url;
    private boolean debug = true;
    private long exitTime = 0;

    private String name;
    private int resttime;

    Cursor cursor = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    home();
                    return true;
                case R.id.birthday:
                    birthday();
                    return true;
                case R.id.mine:
                    mine();
                    return true;
            }
            return false;
        }

    };


    public String sql;

    Toolbar toolbar;
    Handler handler;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//保持竖屏
        WelcomeActivity.instance.finish();//结束欢迎页activity

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        home();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        //需要执行的代码放这里
                        shownotify(name, "" + resttime);
                        break;
                }
            }
        };


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                newitem();
                break;
        }
        return true;
    }

    public void setnotify(String name, int restdays, int year, int month, int day, int remind_time) {

        this.name = name;
        this.resttime = restdays;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
        String t = null;
        Timer timer = new Timer(true);
        if (remind_time == 0) {
            t = year + "-" + month + "-" + (day - 1) + " " + "08:00:00";
            timer.schedule(task, strToDateLong(t));
        } else if (remind_time == 1) {
            t = year + "-" + month + "-" + (day - 1) + " " + "12:00:00";
            timer.schedule(task, strToDateLong(t));
        } else if (remind_time == 2) {
            t = year + "-" + month + "-" + day + " " + "08:00:00";
            timer.schedule(task, strToDateLong(t));
        } else if (remind_time == 3) {
            t = year + "-" + month + "-" + day + " " + "12:00:00";
            timer.schedule(task, strToDateLong(t));
        }
        shownotify(name, "" + resttime);
    }

    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);

        return strtodate;
    }

    public void home() {
        fragmentHome = new FragmentHome();
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, fragmentHome);
        fragmentTransaction.commit();
    }

    public void birthday() {
        fragmentBirthday = new FragmentBirthday();
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, fragmentBirthday);
        fragmentTransaction.commit();
    }

    public void mine() {
        fragmentMine = new FragmentMine();
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, fragmentMine);
        fragmentTransaction.commit();
    }

    //再按一次退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                //moveTaskToBack(true);
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //启动内置浏览器页面
    public void creatwebview(String s) {
        webView = new FragmentWebView();
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, webView);
        fragmentTransaction.commit();
        if (s.equals("essay1"))
            url = "https://mp.weixin.qq.com/s/Q4_emC2yZ_92ZCcgq032aQ\n";
        else
            url = "https://mp.weixin.qq.com/s/GYdVWlfpwrJAJXdI8dJ0gw\n";
        fragmentTransaction.addToBackStack(null);
    }

    //启动支持我们页面
    public void start_supportus() {
        Intent intent = new Intent(); //创建一个Intent对象
        intent.setClass(this, SupportusActivity.class); //描述起点和目标
        startActivity(intent);
    }

    //启动分享页面
    public void start_share() {
        shareMsg("分享到", "分享生日管家 ", "我正在使用《生日管家》APP，还不错哟，分享给大家~", null);
    }

    //调用系统分享

    public void shareMsg(String activityTitle, String msgTitle, String msgText,
                         String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, activityTitle));
    }

    //反馈
    public void start_send() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822"); // 真机上使用
        i.putExtra(Intent.EXTRA_EMAIL,
                new String[]{"154926287@qq.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "反馈建议（生日管家）");
        i.putExtra(Intent.EXTRA_TEXT, "您的建议：\n");
        startActivity(Intent.createChooser(i,
                "请选择邮箱应用"));
    }

    //启动设置页面
    public void start_setting() {
        Intent intent = new Intent(); //创建一个Intent对象
        intent.setClass(this, SettingActivity.class); //描述起点和目标
        startActivity(intent);
    }

    //编辑
    public void start_manage() {
        Intent intent = new Intent(); //创建一个Intent对象
        intent.setClass(this, ManageActivity.class); //描述起点和目标
        startActivity(intent);
    }

    //添加生日
    public void newitem() {
        Intent intent = new Intent(); //创建一个Intent对象
        intent.setClass(this, NewItemActivity.class); //描述起点和目标
        startActivity(intent);
    }

    //修改用户信息
    public void start_edituser() {
        Intent intent = new Intent(); //创建一个Intent对象
        intent.setClass(this, EdituserActivity.class); //描述起点和目标
        startActivity(intent);
    }

    public void shownotify(String name, String restdays) {
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this);
        notifyBuilder.setContentTitle(name);
        notifyBuilder.setContentText(restdays + "天后");
        notifyBuilder.setSmallIcon(R.drawable.icon);
        notifyBuilder.setWhen(System.currentTimeMillis());
        notifyBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

        Context context;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        context = this.getApplicationContext();
        db = new DBhelper(context).getWritableDatabase();
        cursor = db.rawQuery("select * from user_info", null);
        cursor.moveToFirst();
        int voice_status = cursor.getInt(cursor.getColumnIndex("voice"));
        int vibrate_status = cursor.getInt(cursor.getColumnIndex("vibrate"));
        int bar_status = cursor.getInt(cursor.getColumnIndex("bar"));
        if (voice_status == 1 && count == 0)
            notifyBuilder.setDefaults(Notification.DEFAULT_SOUND);
        if (vibrate_status == 1 && count == 0)
            notifyBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
        if (bar_status == 1)
            notifyBuilder.setOngoing(true);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(666666, notifyBuilder.build());
        count = 1;
    }
}
