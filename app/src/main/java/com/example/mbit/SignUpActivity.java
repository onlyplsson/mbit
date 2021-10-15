package com.example.mbit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {

    EditText edSignupEmail, edSignupPW, edSignupPWcheck;
    Button btnSignupOK;
    TextView tvSignupPWerror;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edSignupEmail = findViewById(R.id.ed_Signup_Email);
        edSignupPW = findViewById(R.id.ed_Signup_PW);
        edSignupPWcheck = findViewById(R.id.ed_Signup_PWcheck);
        btnSignupOK = findViewById(R.id.btn_Signup_OK);
        tvSignupPWerror = findViewById(R.id.tv_Signup_PWerror);

        firebaseAuth = FirebaseAuth.getInstance();


        edSignupPWcheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pw = edSignupPW.getText().toString().trim();
                String pwcheck = edSignupPWcheck.getText().toString().trim();

                if (!pw.equals(pwcheck)) {
                    tvSignupPWerror.setVisibility(View.VISIBLE);
                }
                else {
                    tvSignupPWerror.setVisibility(View.INVISIBLE);
                }
            }
        });




        btnSignupOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edSignupEmail.getText().toString().trim();
                String pw = edSignupPW.getText().toString().trim();
                String pwcheck = edSignupPWcheck.getText().toString().trim();

                if (!pw.equals(pwcheck)) {
                    return;
                }
                if (email.length() > 0 && pw.length() > 0 && pwcheck.length() > 0) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                startActivity(intent);
                            } else {
                                if(task.getException() != null) {
                                    Toast.makeText(SignUpActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
                } else {
                    Toast.makeText(SignUpActivity.this, "이메일 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}