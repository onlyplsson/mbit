package com.example.mbit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    Button btnWelcomeSignin, btnWelcomeSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    btnWelcomeSignin = findViewById(R.id.btn_Welcome_Signin);
    btnWelcomeSignup = findViewById(R.id.btn_Welcome_Signup);

    btnWelcomeSignin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WelcomeActivity.this, SignInActivity.class);

            startActivity(intent);
        }
    });
    btnWelcomeSignup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);

            startActivity(intent);
        }
    });
    }
}