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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dept_Announce extends AppCompatActivity {
    Intent intent;
    String deptValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dept_announce);

        //개인정보 입력
        TextView dateTxt, nameTxt, deptText;
        dateTxt = findViewById(R.id.todayDateText);
        nameTxt = findViewById(R.id.userNameTxt);
        deptText = findViewById(R.id.userDeptNameTxt);

        Intent intent = getIntent();
        String nameValue = intent.getStringExtra("userName");
        deptValue = intent.getStringExtra("deptName");

        Date dateInst = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        String str_week = "";
        switch (cal.get(Calendar.DAY_OF_WEEK))
        {
            case 1:
                str_week = "일";
                break;
            case 2:
                str_week = "월";
                break;
            case 3:
                str_week = "화";
                break;
            case 4:
                str_week = "수";
                break;
            case 5:
                str_week = "목";
                break;
            case 6:
                str_week = "금";
                break;
            case 7:
                str_week = "토";
                break;
        }

        cal.setTime(dateInst);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(dateInst);
        dateTxt.setText(date + "(" + str_week + ")");
        nameTxt.setText(nameValue);
        deptText.setText(deptValue);


        Button topButton[] = new Button[4];
        topButton[0] = (Button) findViewById(R.id.mainBtn);
        topButton[1] = (Button) findViewById(R.id.calender);
        topButton[2] = (Button) findViewById(R.id.schedule);
        topButton[3] = (Button) findViewById(R.id.studyManage);

    
        //상단 버튼
        topButton[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                Intent intent = new Intent(Dept_Announce.this, Study.class);
                startActivity(intent);
            }
        });


        Button toSchoolSite = findViewById(R.id.moveToSchoolSite);
        Button toDeptSite = findViewById(R.id.moveToDeptSite);
        Button toCyberCampus = findViewById(R.id.moveToCybercampus);
        toSchoolSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SiteUrl.schoolMainUrl));
                startActivity(intent);
            }
        });
        toDeptSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] deptArr = {"기독교학부", "어문학부", "사회복지학부", "경찰학부", "경상학부", "관광학부", "사범학부",
                        "유아교육과", "특수교육과", "유아특수교육과", "특수체육교육과", "컴퓨터공학부", "보건학부", "물리치료학과",
                        "안경광학과", "응급구조학과", "간호학과", "치위생학과", "작업치료학과", "디자인영상학부", "스포츠과학부",
                        "문화예술학부", "첨단IT학부", "외식산업학부"};

                for(int i=0; i<deptArr.length; i++) {
                    if (deptValue.equals(deptArr[i]))
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SiteUrl.deptSiteUrl[i]));
                        startActivity(intent);
                    }
                }
            }
        });

        toCyberCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SiteUrl.cyberCampusUrl));
                startActivity(intent);
            }
        });
    }
}
