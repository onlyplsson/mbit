package com.example.mbit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SetNickActivity extends AppCompatActivity {
    Button btnSetNickOK;
    EditText edSetNickNick;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setnick);

        btnSetNickOK = findViewById(R.id.btn_SetNick_OK);
        edSetNickNick = findViewById(R.id.ed_SetNick_Nick);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                String nick = profile.getDisplayName();
                if (nick != null) {
                    if (nick.length() > 0) {
                        startPickChannelActivity();
                    }
                }
            }
        }


        btnSetNickOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nick = edSetNickNick.getText().toString();

                if (nick.length() > 0) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nick)
                            .build();

                    if (user != null) {
                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    startPickChannelActivity();
                                }
                            }
                        });
                    }
                }



            }
        });

    }
    private void startSignInActivity() {
        Intent intent = new Intent(SetNickActivity.this, SignInActivity.class);
        startActivity(intent);
    }
    private void startPickChannelActivity() {
        Intent intent = new Intent(SetNickActivity.this, PickChannelActivity.class);
        startActivity(intent);
    }
}