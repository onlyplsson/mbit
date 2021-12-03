package com.example.mbit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ISFPActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String username = user.getDisplayName();
    String uid = user.getUid();
    ListView listviewISFP;
    Button btnMakeRoom, btnChangeNick;
    TextView tvRoomList;
    String name;
    private DatabaseReference reference_ISFP = FirebaseDatabase.getInstance().getReference().getRoot();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isfpactivity);

        listviewISFP = findViewById(R.id.listview_ISFP);
        btnMakeRoom = findViewById(R.id.btn_MakeRoom);
        btnChangeNick = findViewById(R.id.btn_ChangeNick);
        tvRoomList = findViewById(R.id.tv_RoomList);

        RoomList roomList = new RoomList(ISFPActivity.this);
        listviewISFP.setAdapter(roomList);

        btnChangeNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeNick();
            }
        });

        reference_ISFP.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {

                roomList.rooms.clear();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    String sTitle = ((DataSnapshot)i.next()).getKey();
                    int nCount = 0;
                    if(dataSnapshot.child(sTitle).child("user_count").getValue(int.class) == null) {

                    } else {
                        nCount = dataSnapshot.child(sTitle).child("user_count").getValue(int.class);
                    }
                    roomList.addRoom(sTitle, nCount);
                }
                roomList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        listviewISFP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Room r = (Room)roomList.getItem(i);
                if(r.getsCurrCount() >= 4) {
                    Toast.makeText(getApplicationContext(), "방이 꽉 찼습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else{
                    
                }
                reference_ISFP.child(r.getsRoomTitle()).child("users").child(username).setValue(uid);
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("room_name", r.getsRoomTitle());
                startActivity(intent);
            }
        });
        btnMakeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUserName();
            }
        });
    }
    private  void changeNick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("닉네임 변경");

        final EditText builder_input = new EditText(this);

        builder.setView(builder_input);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                String nick = builder_input.getText().toString().trim();
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
                                    username = nick;
                                }
                            }
                        });
                    }
                }
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
    private void createUserName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("방 제목");

        final EditText builder_input = new EditText(this);

        builder.setView(builder_input);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                name = builder_input.getText().toString().trim();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put(name, "");
                reference_ISFP.updateChildren(map);


                reference_ISFP.child(name).child("users").child(username).setValue(uid);

                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("room_name", name);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                // 취소를 누르면 이름을 입력할 때 까지 요청

            }
        });
        builder.show();
    }

}
