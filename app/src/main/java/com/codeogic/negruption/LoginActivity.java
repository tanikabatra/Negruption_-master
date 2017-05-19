package com.codeogic.negruption;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    EditText uname,password;
    Button login,register;
    RequestQueue requestQueue;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    User user;

    public void init(){

        uname=(EditText)findViewById(R.id.loginUsername);
        password=(EditText)findViewById(R.id.loginPassword);
        login=(Button)findViewById(R.id.btnLogin);
        register=(Button)findViewById(R.id.btnRegister);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        user=new User();


        requestQueue= Volley.newRequestQueue(this);

        sharedPreferences=getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnLogin){

           user.setUsername(uname.getText().toString().trim());
           user.setPassword(password.getText().toString().trim());
            loginIntoCloud();

        }


           else if (id==R.id.btnRegister){

            Intent i =new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(i);
            finish();
        }
    }


    public void loginIntoCloud(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.LOGIN_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    int success = jsonObject.getInt("success");
                    int userID = jsonObject.getInt("userID");

                    if(success==1){
                        editor.putInt(Util.PREFS_KEYUSERID,userID);
                        editor.putString(Util.PREFS_KEYUSERNAME,user.getUsername());
                        editor.putString(Util.PREFS_KEYPASSWORD,user.getPassword());
                        editor.commit();
                        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(i);
                        finish();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                Toast.makeText(LoginActivity.this,"Response: "+response,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this,"Some Error"+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("username",user.getUsername());
                map.put("password",user.getPassword());
                Log.i("userName",user.getUsername() + user.getPassword());
                return map;
            }
        };
        requestQueue.add(stringRequest);


    }

    }

