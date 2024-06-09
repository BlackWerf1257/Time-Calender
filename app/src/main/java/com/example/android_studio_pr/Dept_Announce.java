package com.example.android_studio_pr;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dept_Announce extends AppCompatActivity {
    Intent intent;
    public static String deptValue, nameValue;

    //상단 버튼
    Button topButton[];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dept_announce);

        topButton = new Button[4];
        topButton[0] = (Button) findViewById(R.id.mainBtn);
        topButton[1] = (Button) findViewById(R.id.calender);
        topButton[2] = (Button) findViewById(R.id.schedule);
        topButton[3] = (Button) findViewById(R.id.studyManage);

        intent = getIntent();
        nameValue = intent.getStringExtra("userName");
        deptValue = intent.getStringExtra("deptName");

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetButtons();
                int id = view.getId();
                if (id == R.id.mainBtn) {
                    topButton[0].setBackgroundColor(getResources().getColor(R.color.Selected_Button));
                    replaceFragment(new MainFragment());
                } else if (id == R.id.calender) {
                    topButton[1].setBackgroundColor(getResources().getColor(R.color.Selected_Button));
                    replaceFragment(new CalendarFragment());
                } else if (id == R.id.schedule) {
                    topButton[2].setBackgroundColor(getResources().getColor(R.color.Selected_Button));
                    replaceFragment(new ScheduleFragment());
                } else if (id == R.id.studyManage) {
                    topButton[3].setBackgroundColor(getResources().getColor(R.color.Selected_Button));
                    replaceFragment(new StudyManageFragment());
                }
            }
        };

        for(int i=0; i<topButton.length; i++)
            topButton[i].setOnClickListener(listener);

        topButton[0].callOnClick();
    }

    private void resetButtons() {
        for(int i=0; i<topButton.length; i++)
            topButton[i].setBackgroundColor(getResources().getColor(R.color.UnSelected_Button));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, fragment);
        fragmentTransaction.commit();
    }
}
