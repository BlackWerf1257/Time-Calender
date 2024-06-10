package com.example.android_studio_pr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainFragment extends Fragment {
    TextView dateTxt;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        //개인정보 입력
        TextView nameTxt, deptText;
        dateTxt = view.findViewById(R.id.todayDateText);
        nameTxt = view.findViewById(R.id.userNameTxt);
        deptText = view.findViewById(R.id.userDeptNameTxt);

        GetTime();
        String name = Dept_Announce.nameValue;
        String dept = Dept_Announce.deptValue;

        nameTxt.setText(name);
        deptText.setText(dept);

        Button toSchoolSite = view.findViewById(R.id.moveToSchoolSite);
        Button toDeptSite = view.findViewById(R.id.moveToDeptSite);
        Button toCyberCampus = view.findViewById(R.id.moveToCybercampus);
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
                nameTxt.setText(name);
                String[] deptArr = {"기독교학부", "어문학부", "사회복지학부", "경찰학부", "경상학부", "관광학부", "사범학부",
                        "유아교육과", "특수교육과", "유아특수교육과", "특수체육교육과", "컴퓨터공학부", "보건학부", "물리치료학과",
                        "안경광학과", "응급구조학과", "간호학과", "치위생학과", "작업치료학과", "디자인영상학부", "스포츠과학부",
                        "문화예술학부", "첨단IT학부", "외식산업학부"};

                for(int i=0; i<deptArr.length; i++) {
                    if (Dept_Announce.deptValue.equals(deptArr[i]))
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

        return view;
    }

    void GetTime(){
        Date dateInst = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        String str_week = "";
        cal.setTime(dateInst);
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

        String date = new SimpleDateFormat("yyyy-MM-dd").format(dateInst);
        dateTxt.setText(date + "(" + str_week + ")");
    }
}