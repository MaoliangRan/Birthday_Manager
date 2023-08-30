package com.example.birthday_manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ManageActivity extends AppCompatActivity implements OnClickListener {

    Button manage_button;
    TextView manage_text;
    EditText manage_name;
    Button manage_delete;

    Context context;
    SQLiteDatabase db = null;
    Cursor cursor = null;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        manage_button = (Button) findViewById(R.id.manage_back);
        manage_button.setOnClickListener(this);
        manage_delete = (Button) findViewById(R.id.manage_ok);
        manage_delete.setOnClickListener(this);
        manage_text = (TextView) findViewById(R.id.manage_backt);
        manage_text.setOnClickListener(this);
        manage_name = (EditText) findViewById(R.id.manage_name);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.manage_back:
                finish();
                break;
            case R.id.manage_backt:
                finish();
                break;
            case R.id.manage_ok:
                name = manage_name.getText().toString();
                context = this.getApplicationContext();
                db = new DBhelper(context).getWritableDatabase();
                String sql = "select * from person where name='" + name + "'";
                cursor = db.rawQuery(sql, null);
                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "没有此人！", Toast.LENGTH_SHORT).show();
                    db.close();
                } else {
                    sql = "delete from person where name='" + name + "'";
                    db.execSQL(sql);
                    Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    db.close();
                    finish();
                }

                break;

        }
    }

    public int manage() {
//        name = manage_name.getText().toString();
//        context = this.getApplicationContext();
//        db = new DBhelper(context).getWritableDatabase();
//        String sql = "select * from person where name=" + name;
//        cursor = db.rawQuery(sql, null);
//        Toast.makeText(getApplicationContext(), "没有此人！", Toast.LENGTH_SHORT).show();
//        if (cursor.getCount() == 0){
//            Toast.makeText(getApplicationContext(), "没有此人！", Toast.LENGTH_SHORT).show();
//            db.close();
//            return 0;
//        } else{
//            sql="delete from person where name="+name;
//            db.execSQL(sql);
//            Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
//            db.close();
//            return 1;
//        }
        return 0;

    }
}
