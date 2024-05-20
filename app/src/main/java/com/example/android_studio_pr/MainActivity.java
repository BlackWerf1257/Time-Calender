package com.example.android_studio_pr;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button[] topButtonList = new Button[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        float density = getResources().getDisplayMetrics().density;
        int width = (int)(metrics.widthPixels / density) / 5;

        topButtonList[0] = (Button)findViewById(R.id.mainBtn);
        topButtonList[1] = (Button)findViewById(R.id.calender);
        topButtonList[2] = (Button)findViewById(R.id.schedule);
        topButtonList[3] = (Button)findViewById(R.id.studyManage);
        topButtonList[4] = (Button)findViewById(R.id.community);

        for(int i=0; i< topButtonList.length; i++)
            topButtonList[i].setWidth(width);
    }
}