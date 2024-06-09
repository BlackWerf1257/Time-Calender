package com.example.android_studio_pr;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CalendarFragment extends Fragment {

    private GridLayout timetableGrid;
    private Map<String, String> timetableMap = new HashMap<>();
    private Map<String, Integer> colorMap = new HashMap<>();
    private Random random = new Random();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        timetableGrid = view.findViewById(R.id.timetableGrid);

        populateTimetable();

        return view;
    }

    private void populateTimetable() {
        // 샘플 시간표 데이터
        String[][] timetable = {
                {"", "안드로이드", "데이터통신", "컴퓨터구조", "웹프로그래밍", ""},
                {"", "안드로이드", "데이터통신", "컴퓨터구조", "웹프로그래밍", ""},
                {"", "안드로이드", "데이터통신", "컴퓨터구조", "웹프로그래밍", ""},
                {"", "안드로이드", "데이터통신", "컴퓨터구조", "웹프로그래밍", ""},
                {"", "안드로이드", "데이터통신", "컴퓨터구조", "웹프로그래밍", ""},
                {"", "데이터베이스", "공업수학", "운영체제", "소프트웨어공학", ""},
                {"", "데이터베이스", "공업수학", "운영체제", "소프트웨어공학", ""},
                {"", "데이터베이스", "공업수학", "운영체제", "소프트웨어공학", ""},
                {"", "데이터베이스", "공업수학", "운영체제", "소프트웨어공학", ""},
                {"", "데이터베이스", "공업수학", "운영체제", "소프트웨어공학", ""},
        };

        String[] days = {"", "월", "화", "수", "목", "금"};

        for (int col = 0; col < timetable[0].length; col++) {
            TextView dayTextView = new TextView(getActivity());
            dayTextView.setText(days[col]);
            dayTextView.setPadding(8, 8, 8, 8);
            dayTextView.setBackgroundResource(R.color.colorPrimary);
            dayTextView.setTextColor(getResources().getColor(android.R.color.white));
            dayTextView.setGravity(Gravity.CENTER);

            GridLayout.LayoutParams dayParams = new GridLayout.LayoutParams();
            dayParams.rowSpec = GridLayout.spec(0);
            dayParams.columnSpec = GridLayout.spec(col);
            dayTextView.setLayoutParams(dayParams);

            timetableGrid.addView(dayTextView);

            for (int row = 1; row < timetable.length; row++) {
                if (col == 0) {
                    String timeSlot = (8 + row) + ":00" ; // 시간대
                    TextView timeTextView = new TextView(getActivity());
                    timeTextView.setText(timeSlot);
                    timeTextView.setPadding(8, 8, 8, 8);
                    timeTextView.setBackgroundResource(R.color.colorAccent);
                    timeTextView.setTextColor(getResources().getColor(android.R.color.white));
                    timeTextView.setGravity(Gravity.CENTER);

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.rowSpec = GridLayout.spec(row);
                    params.columnSpec = GridLayout.spec(col);
                    timeTextView.setLayoutParams(params);

                    timetableGrid.addView(timeTextView);
                } else {
                    String text = timetable[row][col];
                    TextView textView = new TextView(getActivity());
                    textView.setText(text);
                    textView.setPadding(8, 8, 8, 8);
                    textView.setTextColor(getResources().getColor(android.R.color.white));
                    textView.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL); // 텍스트를 아래로 정렬

                    if (!colorMap.containsKey(text)) {
                        int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                        colorMap.put(text, color);
                    }

                    textView.setBackgroundColor(colorMap.get(text));

                    int finalRow = row;
                    int finalCol = col;
                    textView.setOnClickListener(v -> showEditDialog(textView, finalRow, finalCol));

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.rowSpec = GridLayout.spec(row);
                    params.columnSpec = GridLayout.spec(col);
                    params.setGravity(Gravity.FILL); // TextView가 셀을 채우도록 설정
                    textView.setLayoutParams(params);
                    timetableGrid.addView(textView);
                }
            }
        }
    }

    private void showEditDialog(TextView textView, int row, int col) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("수업 수정");

        final EditText input = new EditText(getActivity());
        input.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
        input.setText(textView.getText().toString());
        builder.setView(input);

        builder.setPositiveButton("저장", (dialog, which) -> {
            String newClass = input.getText().toString();
            textView.setText(newClass);

            int color;
            if (colorMap.containsKey(newClass)) {
                color = colorMap.get(newClass);
            } else {
                color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                colorMap.put(newClass, color);
            }

            textView.setBackgroundColor(color);

            String key = row + "-" + col;
            timetableMap.put(key, newClass);  // 시간표 데이터 업데이트
            colorMap.put(newClass, color);  // 새로운 색상을 맵에 추가
            Toast.makeText(getActivity(), "수업이 저장되었습니다", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("취소", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
