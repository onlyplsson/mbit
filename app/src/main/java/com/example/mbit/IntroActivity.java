package com.example.mbit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startWelcomeActivity();
                } else {
                    startPickChannelActivity();
                }
                finish();
            }
        }, 3000);

    }

    private void startWelcomeActivity() {
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
    }
    private void startPickChannelActivity() {
        Intent intent = new Intent(getApplicationContext(), PickChannelActivity.class);
        startActivity(intent);
    }
}