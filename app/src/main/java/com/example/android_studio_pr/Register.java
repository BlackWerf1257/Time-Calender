package com.example.android_studio_pr;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.rpc.context.AttributeContext;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    static int dropdownSelectedIdx;
    static EditText idField, pwField, userName;
    static ProgressDialog progresssBar;
    static String[] deptArr = {"기독교학부", "어문학부", "사회복지학부", "경찰학부", "경상학부", "관광학부", "사범학부",
            "유아교육과", "특수교육과", "유아특수교육과", "특수체육교육과", "컴퓨터공학부", "보건학부", "물리치료학과",
            "안경광학과", "응급구조학과", "간호학과", "치위생학과", "작업치료학과", "디자인영상학부", "스포츠과학부",
            "문화예술학부", "첨단IT학부", "외식산업학부"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.register);
        super.onCreate(savedInstanceState);

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
                RegisterEvent();
                progresssBar.show();
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


    private void RegisterEvent() {
        StringRequest request = new StringRequest(Request.Method.POST, Url.RegisterUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progresssBar.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progresssBar.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", idField.getText().toString().trim());
                params.put("pw", pwField.getText().toString().trim());
                params.put("userName", userName.getText().toString().trim());
                params.put("deptName", deptArr[dropdownSelectedIdx]);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(Register.this);
        queue.add(request);
    }
}
