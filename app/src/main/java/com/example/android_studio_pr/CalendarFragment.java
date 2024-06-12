package com.example.android_studio_pr;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CalendarFragment extends Fragment {

    private GridLayout timetableGrid;
    private Map<String, String> timetableMap = new HashMap<>();
    private Map<String, Integer> colorMap = new HashMap<>();
    private Random random = new Random();
    AlertDialog.Builder alertDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        timetableGrid = view.findViewById(R.id.timetableGrid);

        ContentValues values = new ContentValues();
        values.put("userName", Dept_Announce.nameValue);
        values.put("needFetch", "true");
        CalendarFragment.HttpUtil task = new CalendarFragment.HttpUtil(SiteUrl.CalenderUrl, values);
        task.execute();

        populateTimetable();


        alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setIcon(android.R.drawable.ic_lock_idle_alarm);

        return view;
    }

    private void populateTimetable() {
        String[][] timetable = {
                {"", "", "", "", "", ""},
                {"", "", "", "", "", ""},
                {"", "", "", "", "", ""},
                {"", "", "안드로이드 프로그래밍", "", "", ""},
                {"", "", "안드로이드 프로그래밍", "", "", ""},
                {"", "", "안드로이드 프로그래밍", "", "", ""},
                {"", "", "", "", "", ""},
                {"", "", "", "", "", ""},
                {"", "", "", "", "", ""},
        };

        String[] days = {"", "    월    ", "    화    ", "    수    ", "    목    ", "    금   "};
        String[] times = {"\n09:00\n", "\n10:00\n", "\n11:00\n", "\n12:00\n", "\n13:00\n", "\n14:00\n", "\n15:00\n", "\n16:00\n", "\n17:00\n"};

        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            for (int i = 0; i < timetable.length; i++) {
                for (int j = 0; j < timetable[i].length; j++) {
                    timetable[i][j] = timetable[i][j].replace("\n", "");
                }
            }
            for (int i = 0; i < times.length; i++) {
                times[i] = times[i].replace("\n", "");
            }
            for (int i = 0; i < days.length; i++) {
                days[i] = "         " + days[i] + "         ";
            }
        }

        for (int col = 0; col < days.length; col++) {
            TextView dayTextView = new TextView(getActivity());
            dayTextView.setText(days[col]);
            dayTextView.setPadding(8, 8, 8, 8);
            dayTextView.setBackgroundResource(R.color.colorPrimary);
            dayTextView.setTextColor(getResources().getColor(android.R.color.white));
            dayTextView.setGravity(Gravity.CENTER);

            GridLayout.LayoutParams dayParams = new GridLayout.LayoutParams();
            dayParams.rowSpec = GridLayout.spec(0, 1f);
            dayParams.columnSpec = GridLayout.spec(col, 1f);
            dayTextView.setLayoutParams(dayParams);

            timetableGrid.addView(dayTextView);
        }

        for (int row = 1; row <= times.length; row++) {
            TextView timeTextView = new TextView(getActivity());
            timeTextView.setText(times[row - 1]);
            timeTextView.setPadding(8, 8, 8, 8);
            timeTextView.setBackgroundResource(R.color.colorAccent);
            timeTextView.setTextColor(getResources().getColor(android.R.color.white));
            timeTextView.setGravity(Gravity.CENTER);

            GridLayout.LayoutParams timeParams = new GridLayout.LayoutParams();
            timeParams.rowSpec = GridLayout.spec(row, 1f);
            timeParams.columnSpec = GridLayout.spec(0, 1f);
            timeTextView.setLayoutParams(timeParams);

            timetableGrid.addView(timeTextView);

            for (int col = 1; col < days.length; col++) {
                String text = timetable[row - 1][col - 1];
                TextView textView = new TextView(getActivity());
                textView.setText(text);
                textView.setPadding(8, 8, 8, 8);
                textView.setTextColor(getResources().getColor(android.R.color.white));
                textView.setGravity(Gravity.CENTER);

                if (!colorMap.containsKey(text)) {
                    int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                    colorMap.put(text, color);
                }

                textView.setBackgroundColor(colorMap.get(text));

                int finalRow = row;
                int finalCol = col;
                textView.setOnClickListener(v -> showEditDialog(textView, finalRow, finalCol));

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(row, 1f);
                params.columnSpec = GridLayout.spec(col, 1f);
                params.setGravity(Gravity.FILL);
                textView.setLayoutParams(params);
                timetableGrid.addView(textView);
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
            timetableMap.put(key, newClass);
            colorMap.put(newClass, color);
            Toast.makeText(getActivity(), "수업이 저장되었습니다", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("취소", (dialog, which) -> dialog.cancel());

        builder.show();
    }


    public class HttpUtil extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        HttpUtil(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
        }

        @Override
        protected String doInBackground(Void... params) {
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            String result = requestHttpURLConnection.postRequest(url, values);
            return result; // 아래 onPostExecute()의 파라미터로 전달됩니다.
        }

        @Override
        protected void onPostExecute(String result) {
            // 결과에 따른 UI 수정

            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
        }
    }
}
