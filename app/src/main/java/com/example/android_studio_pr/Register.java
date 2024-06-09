package com.example.android_studio_pr;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    static int dropdownSelectedIdx = 0;
    static EditText idField, pwField, userName;
    static ProgressDialog progresssBar;

    AlertDialog.Builder alertDialog;
    static String[] deptArr = {"기독교학부", "어문학부", "사회복지학부", "경찰학부", "경상학부", "관광학부", "사범학부",
            "유아교육과", "특수교육과", "유아특수교육과", "특수체육교육과", "컴퓨터공학부", "보건학부", "물리치료학과",
            "안경광학과", "응급구조학과", "간호학과", "치위생학과", "작업치료학과", "디자인영상학부", "스포츠과학부",
            "문화예술학부", "첨단IT학부", "외식산업학부"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.register);
        super.onCreate(savedInstanceState);

        alertDialog = new AlertDialog.Builder(Register.this);
        alertDialog.setTitle(R.string.error);
        alertDialog.setIcon(android.R.drawable.ic_lock_idle_alarm);


        Button registerBtn, undoBtn;
        Spinner spinner;


        idField = findViewById(R.id.idField);
        pwField = findViewById(R.id.pwField);
        userName = findViewById(R.id.userNameField);

        registerBtn = findViewById(R.id.registerBtn);
        undoBtn = findViewById(R.id.registerUndoBtn);
        spinner = findViewById(R.id.deptSpinner);



        ArrayAdapter apapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, deptArr);
        spinner.setAdapter(apapter);

        progresssBar = new ProgressDialog(this);
        progresssBar.setMessage("로딩 중");
        progresssBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String id = idField.getText().toString().trim();
                    String pw = pwField.getText().toString().trim();
                    String userNameValue = userName.getText().toString().trim();

                    if (id.isEmpty() || pw.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "값을 입력해주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        progresssBar.show();
                        ContentValues values = new ContentValues();
                        values.put("id", id);
                        values.put("pw", pw);
                        values.put("userName", userNameValue);
                        values.put("deptName", deptArr[dropdownSelectedIdx].toString());
                        Register.HttpUtil netTask = new Register.HttpUtil(SiteUrl.RegisterUrl, values);
                        netTask.execute();
                    }
            }
        });
        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dropdownSelectedIdx = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                progresssBar.dismiss();
            }
        });
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
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            // 결과에 따른 UI 수정
            progresssBar.dismiss();
            if(!result.isEmpty())
            {

                alertDialog.setMessage(result);
                alertDialog.show();
            }
        }
    }
}
