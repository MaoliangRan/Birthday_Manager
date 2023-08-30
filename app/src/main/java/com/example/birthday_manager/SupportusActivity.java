package com.example.birthday_manager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SupportusActivity extends AppCompatActivity implements OnClickListener {

    Button backbtn;
    TextView backview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supportus);

        backbtn = (Button) findViewById(R.id.suportusback);
        backview = (TextView) findViewById(R.id.suportusbackt);
        backbtn.setOnClickListener(this);
        backview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.suportusback:
                finish();
                break;
            case R.id.suportusbackt:
                finish();
                break;
        }
    }
}
