package com.example.android_studio_pr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Study extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study);

        Button topButton[] = new Button[5];
        topButton[0] = (Button) findViewById(R.id.mainBtn);
        topButton[1] = (Button) findViewById(R.id.calender);
        topButton[2] = (Button) findViewById(R.id.schedule);
        topButton[3] = (Button) findViewById(R.id.studyManage);
        topButton[4] = (Button) findViewById(R.id.community);

        Button timerStart, timerPause;
        timerStart = findViewById(R.id.countDownStart);
        timerPause = findViewById(R.id.countDownPause);

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
        topButton[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        timerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerPause.setClickable(true);
            }
        });
        timerPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerPause.setClickable(false);
            }
        });



        Timer timer;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

            }
        };


    }
}
