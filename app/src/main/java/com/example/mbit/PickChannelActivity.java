package com.example.mbit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class PickChannelActivity extends AppCompatActivity {

    Button btnISFP, btnISFJ, btnISTP, btnISTJ, btnINTP, btnINTJ, btnINFP, btnINFJ;
    Button btnESFP, btnESFJ, btnESTP, btnESTJ, btnENTP, btnENTJ, btnENFP, btnENFJ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickchannel);


        btnISFP = findViewById(R.id.btn_ISFP);
        btnISFJ = findViewById(R.id.btn_ISFJ);
        btnISTP = findViewById(R.id.btn_ISTP);
        btnISTJ = findViewById(R.id.btn_ISTJ);
        btnINTP = findViewById(R.id.btn_INTP);
        btnINTJ = findViewById(R.id.btn_INTJ);
        btnINFP = findViewById(R.id.btn_INFP);
        btnINFJ = findViewById(R.id.btn_INFJ);
        btnESFP = findViewById(R.id.btn_ESFP);
        btnESFJ = findViewById(R.id.btn_ESFJ);
        btnESTP = findViewById(R.id.btn_ESTP);
        btnESTJ = findViewById(R.id.btn_ESTJ);
        btnENTP = findViewById(R.id.btn_ENTP);
        btnENTJ = findViewById(R.id.btn_ENTJ);
        btnENFP = findViewById(R.id.btn_ENFP);
        btnENFJ = findViewById(R.id.btn_ENFJ);

        btnISFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, ISFPActivity.class);
                startActivity(intent);
            }
        });
        btnISFJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, ISFJActivity.class);
                startActivity(intent);
            }
        });
        btnISTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, ISTPActivity.class);
                startActivity(intent);
            }
        });
        btnISTJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, ISTJActivity.class);
                startActivity(intent);
            }
        });
        btnINTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, INTPActivity.class);
                startActivity(intent);
            }
        });
        btnINTJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, INTJActivity.class);
                startActivity(intent);
            }
        });
        btnINFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, INFPActivity.class);
                startActivity(intent);
            }
        });
        btnINFJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, INFJActivity.class);
                startActivity(intent);
            }
        });
        btnESFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, ESFPActivity.class);
                startActivity(intent);
            }
        });
        btnESFJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, ESFJActivity.class);
                startActivity(intent);
            }
        });
        btnESTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, ESTPActivity.class);
                startActivity(intent);
            }
        });
        btnESTJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, ESTJActivity.class);
                startActivity(intent);
            }
        });
        btnENTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, ENTPActivity.class);
                startActivity(intent);
            }
        });
        btnENTJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, ENTJActivity.class);
                startActivity(intent);
            }
        });
        btnENFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, ENFPActivity.class);
                startActivity(intent);
            }
        });
        btnENFJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickChannelActivity.this, ENFJActivity.class);
                startActivity(intent);
            }
        });
    }

}