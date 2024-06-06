package com.example.android_studio_pr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText idField, pwField;

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
                    Toast.makeText(getApplicationContext(), "값을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    StringRequest request= new StringRequest(Request.Method.POST, Url.LoginUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObj = new JSONObject(response);
                                String result = jsonObj.getString("status");
                                idField.setText(jsonObj.toString());
                                if(result.equals("success"))
                                    Toast.makeText(getApplicationContext(), "Login succeed", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("id", id);
                            params.put("pw", pw);
                            return params;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(request);

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
}