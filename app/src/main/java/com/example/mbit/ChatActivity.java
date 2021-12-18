package com.example.mbit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class ChatActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String username = user.getDisplayName();
    String uid = user.getUid();
    int nUserCount;
    String sChat;

    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        String getTime = dateFormat.format(date);

        return getTime;
    }


    ArrayList<Chat> list_Chat;

    TextView tvRoomName;
    ListView listviewUsers;
    Button btnQuit, btnSend;
    EditText edChat;
    RecyclerView recyclerviewChat = null;
    ChatAdapter chatAdapter = null;

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        tvRoomName = findViewById(R.id.tv_RoomName);
        listviewUsers = findViewById(R.id.listview_Users);
        btnQuit = findViewById(R.id.btn_Quit);
        btnSend = findViewById(R.id.btn_Send);
        edChat = findViewById(R.id.ed_Chat);
        recyclerviewChat = findViewById(R.id.recyclerview_Chat);

        Intent intent = getIntent();
        String room_name = intent.getStringExtra("room_name");
        String ch_name = intent.getStringExtra("ch_name");
        tvRoomName.setText(room_name);

        final ArrayList<String> list_User = new ArrayList<>();
        final ArrayAdapter<String> adapter_User = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_User);
        listviewUsers.setAdapter(adapter_User);

        list_Chat = new ArrayList<>();
        chatAdapter = new ChatAdapter(list_Chat);
        recyclerviewChat.setAdapter(chatAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        //mLayoutManager.setReverseLayout(true);
        //mLayoutManager.setStackFromEnd(true);
        recyclerviewChat.setLayoutManager(mLayoutManager);

        myRef = myRef.child(ch_name);


        myRef.child("rooms").child(room_name).child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_User.clear();

                for (DataSnapshot userData : snapshot.getChildren()) {
                    String users = userData.child("nick").getValue(String.class);

                    list_User.add(users);
                }

                adapter_User.notifyDataSetChanged();

                nUserCount = list_User.size();
                if (nUserCount == 0) {
                    myRef.child("rooms").child(room_name).child("user_count").setValue(null);
                } else {
                    myRef.child("rooms").child(room_name).child("user_count").setValue(nUserCount);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sChat = edChat.getText().toString().trim();
                myRef.child("rooms").child(room_name).child("chats").child(getTime()).setValue(username + " : " + sChat);
                edChat.setText("");
            }
        });

        myRef.child("rooms").child(room_name).child("chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_Chat.clear();

                for (DataSnapshot userData : snapshot.getChildren()) {
                    String chat = userData.getValue(String.class);

                    addItem(chat);
                }

                chatAdapter.notifyDataSetChanged();
                recyclerviewChat.scrollToPosition(list_Chat.size()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("rooms").child(room_name).child("chats").child(getTime()).setValue(username + " 님이 나갔습니다.");
                myRef.child("rooms").child(room_name).child("users").child(uid).setValue(null);
                list_User.remove(myRef.child("rooms").child(room_name).child("users").child(uid).getKey());

                if(list_User.size()-1 == 0) {

                    myRef.child("rooms").child(room_name).child("chats").setValue(null);
                }
                onBackPressed();
            }
        });

        edChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0) {
                    btnSend.setEnabled(false);
                } else {
                    btnSend.setEnabled(true);
                }
            }
        });




    }
    public  void addItem (String content) {
        Chat c = new Chat();

        c.setsChatConetent(content);

        list_Chat.add(c);
    }
}