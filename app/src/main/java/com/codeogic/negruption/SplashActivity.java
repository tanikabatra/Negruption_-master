package com.codeogic.negruption;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        sharedPreferences = getSharedPreferences(Util.PREFS_NAME, MODE_PRIVATE);

        boolean isLoginOrRegistered = sharedPreferences.contains(Util.PREFS_KEYUSERNAME);

        if (isLoginOrRegistered) {
            handler.sendEmptyMessageDelayed(100, 2500);
        } else {
             handler.sendEmptyMessageDelayed(200,2500);
        }
    }

    

     Handler handler=new Handler(){
        public void handleMessage(Message message){

            if (message.what==100){

                Intent i =new Intent(SplashActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
            else if (message.what==200){

                Intent i =new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
           
        }


    };
}
