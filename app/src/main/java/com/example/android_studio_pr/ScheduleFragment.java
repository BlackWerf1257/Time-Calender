package com.example.android_studio_pr;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
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

public class ScheduleFragment extends Fragment {
    private CalendarView calendarView;
    private TextView memoTextView;
    private Button viewMemoButton;
    private Button editMemoButton;
    String queryDate;
    private String selectedDate;
    String tmpTask; //해당 날짜의 작업 임시 저장용
    AlertDialog.Builder alertDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        memoTextView = view.findViewById(R.id.memoTextView);
        viewMemoButton = view.findViewById(R.id.viewMemoButton);
        editMemoButton = view.findViewById(R.id.editMemoButton);


        alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setIcon(android.R.drawable.ic_lock_idle_alarm);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일 메모 내용 ★";
                queryDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                Toast.makeText(getContext(), queryDate, Toast.LENGTH_SHORT).show();
            }
        });

        viewMemoButton.setOnClickListener(v -> {
            if (selectedDate != null)
                MemoTextViewEvent("false");
            else {
                alertDialog.setTitle(R.string.error);
                alertDialog.setMessage("날짜를 선택하세요");
                alertDialog.show();
            }
        });

        editMemoButton.setOnClickListener(v -> {
            if (selectedDate != null)
            {
                AlertDialog.Builder editAlert = new AlertDialog.Builder(getContext());
                editAlert.setTitle("일정 추가");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                editAlert.setView(input);
                editAlert.setPositiveButton("추가", ((dialog, which) -> {
                    tmpTask = input.getText().toString();
                    MemoTextViewEvent("true");
                }));
                editAlert.setNegativeButton("취소", ((dialog, which) -> {}));
                editAlert.show();
            }
            else {
                alertDialog.setTitle(R.string.error);
                alertDialog.setMessage("날짜를 선택하세요");
                alertDialog.show();
            }
        });

        return view;
    }


    //true: 데이터설정 false: 데이터 가져오기
    private void MemoTextViewEvent(String isSet) {
        ContentValues values = new ContentValues();
        values.put("userName", Dept_Announce.nameValue);
        values.put("date", queryDate);
        values.put("isSendMode", isSet);
        if(tmpTask == null)
            values.put("task", "");
        else
            values.put("task", tmpTask);
        ScheduleFragment.HttpUtil netTask = new ScheduleFragment.HttpUtil(SiteUrl.ScheduleUrl, values);
        netTask.execute();
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
            String date = null;
            String task = null;
            // 결과에 따른 UI 수정
            if(!result.isEmpty())
            {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    result = jsonObject.getString("result");
                    date = jsonObject.getString("date");
                    task = jsonObject.getString("task");

                    // 이제 'name' 변수를 사용할 수 있습니다.
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(result.equals("성공"))
                    alertDialog.setTitle(R.string.succeed);
                else if(result.equals("실패"))
                    alertDialog.setTitle(R.string.error);

                String data = date + "\n" + task;
                memoTextView.setText(data);
                alertDialog.setMessage(result);
                alertDialog.show();
            }
            else{
                alertDialog.setTitle(R.string.error);
                alertDialog.setMessage(result);
                alertDialog.show();
            }
        }
    }
}