package com.example.mbit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class SignInActivity extends AppCompatActivity {

    Button btnSigninOK;
    EditText edSigninEmail, edSigninPW;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSigninOK = findViewById(R.id.btn_Signin_OK);
        edSigninEmail = findViewById(R.id.ed_Signin_Email);
        edSigninPW = findViewById(R.id.ed_Signin_PW);

        firebaseAuth = FirebaseAuth.getInstance();

        btnSigninOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edSigninEmail.getText().toString().trim();
                String pw = edSigninPW.getText().toString().trim();

                if (email.length() > 0 && pw.length() > 0) {
                    firebaseAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startSetNickActivity();
                            } else {
                                if (task.getException() != null) {
                                    Toast.makeText(SignInActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                } else {
                    Toast.makeText(SignInActivity.this, "이메일 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void startPickChannelActivity() {
        Intent intent = new Intent(SignInActivity.this, PickChannelActivity.class);
        startActivity(intent);
    }
    private void startSetNickActivity() {
        Intent intent = new Intent(SignInActivity.this, SetNickActivity.class);
        startActivity(intent);
    }

}