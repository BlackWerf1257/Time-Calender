package com.example.android_studio_pr;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Dept_Announce extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dept_announce);

        Button topButton[] = new Button[5];
        topButton[0] = (Button) findViewById(R.id.mainBtn);
        topButton[1] = (Button) findViewById(R.id.calender);
        topButton[2] = (Button) findViewById(R.id.schedule);
        topButton[3] = (Button) findViewById(R.id.studyManage);
        topButton[4] = (Button) findViewById(R.id.community);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int topBtnWidth = size.x / 5;
        for(int i=0; i< topButton.length; i++)
            topButton[i].setWidth(5);

    }
}