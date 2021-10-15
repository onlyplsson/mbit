package com.example.mbit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SelectActivity extends AppCompatActivity {

    Button btnLogout, btnShowNick;
    TextView tvUserNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        btnLogout = findViewById(R.id.btn_Logout);
        btnShowNick = findViewById(R.id.btn_ShowNick);
        tvUserNick = findViewById(R.id.tv_UserNick);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startWelcomeActivity();
            }
        });

        btnShowNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                tvUserNick.setText(user.getDisplayName());

            }
        });
    }
    private void startWelcomeActivity() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
    private void startSetNickActivity() {
        Intent intent = new Intent(this, SetNickActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() { }

}