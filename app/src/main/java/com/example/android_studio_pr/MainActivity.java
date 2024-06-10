package com.example.android_studio_pr;

import android.content.ContentValues;
import android.content.Intent;
import android.icu.util.Output;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    static String loginResult, userName, deptName;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText idField, pwField;

        alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle(R.string.error);
        alertDialog.setIcon(android.R.drawable.ic_lock_idle_alarm);

        idField = findViewById(R.id.idField);
        pwField = findViewById(R.id.pwField);

        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        Button registerBtn = (Button)findViewById(R.id.registerBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            UserLoginEvent();
           }

            private void UserLoginEvent() {
                String id = idField.getText().toString().trim();
                String pw = pwField.getText().toString().trim();

                if (id.isEmpty() || pw.isEmpty()) {
                    alertDialog.setMessage(R.string.noInfo);
                    alertDialog.show();
                    return;
                }
                else
                {
                    ContentValues values = new ContentValues();
                    values.put("id", id);
                    values.put("pw", pw);
                    HttpUtil netTask = new HttpUtil(SiteUrl.LoginUrl, values);
                    netTask.execute();
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
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
            // 결과에 따른 UI 수정
            if(!result.isEmpty())
            {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    userName = jsonObject.getString("User_Name");
                    deptName = jsonObject.getString("Dept_Name");
                    // 이제 'name' 변수를 사용할 수 있습니다.
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), Dept_Announce.class);
                intent.putExtra("userName", userName);
                intent.putExtra("deptName", deptName);
                startActivity(intent);
            }
            else {

                alertDialog.setMessage(R.string.noIdorPw);
                alertDialog.show();
            }
        }
    }
}

