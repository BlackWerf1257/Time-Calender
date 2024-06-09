package com.example.android_studio_pr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class Study extends AppCompatActivity {

    EditText minText, secText;
    TimerTask timerTask;
    private Timer timer;
    int remainTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study);

        Button topButton[] = new Button[4];
        topButton[0] = (Button) findViewById(R.id.mainBtn);
        topButton[1] = (Button) findViewById(R.id.calender);
        topButton[2] = (Button) findViewById(R.id.schedule);
        topButton[3] = (Button) findViewById(R.id.studyManage);

        Button timerStart, timerPause;
        timerStart = findViewById(R.id.countDownStart);
        timerPause = findViewById(R.id.countDownPause);

        minText = findViewById(R.id.minText);
        secText = findViewById(R.id.secondText);

        timerPause.setClickable(false);

        Switch soundOn, vibrationOn;
        soundOn = findViewById(R.id.soundOn);
        vibrationOn = findViewById(R.id.vibrationOn);


        topButton[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Study.this, Dept_Announce.class);
                startActivity(intent);
            }
        });
        topButton[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        topButton[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        topButton[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Study.this, Study.class);
                startActivity(intent);
            }
        });



        timerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "실행됨", Toast.LENGTH_SHORT).show();
            }

        });



        timerPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerPause.setClickable(false);
            }
        });

    }
}
