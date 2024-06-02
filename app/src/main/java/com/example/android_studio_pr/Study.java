package com.example.android_studio_pr;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Study extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study);

        Timer timer;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

            }
        };


    }
}
