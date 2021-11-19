package com.example.mbit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String username = user.getDisplayName();

    TextView tvRoomName;
    ListView listviewUsers;
    Button btnQuit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        tvRoomName = findViewById(R.id.tv_RoomName);
        listviewUsers = findViewById(R.id.listview_Users);
        btnQuit = findViewById(R.id.btn_Quit);

        Intent intent = getIntent();
        String room_name = intent.getExtras().get("room_name").toString();
        tvRoomName.setText(room_name);

        final ArrayList<String> list_User = new ArrayList<>();
        final ArrayAdapter<String> adapter_User = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_User);
        listviewUsers.setAdapter(adapter_User);

        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference(room_name).child("users");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_User.clear();
                for (DataSnapshot userData : snapshot.getChildren()) {
                    String users = userData.getKey();

                    list_User.add(users);
                    //adapter_User.add(users);
                }
                adapter_User.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userReference.child(username).setValue(null);
                list_User.remove(userReference.child(username).getKey());
                onBackPressed();
            }
        });






    }
}