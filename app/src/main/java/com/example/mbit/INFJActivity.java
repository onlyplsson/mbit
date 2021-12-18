package com.example.mbit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class INFJActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String username = user.getDisplayName();
    String uid = user.getUid();
    ListView listview;
    Button btnMakeRoom;
    ImageButton btnSetting;
    TextView tvRoomList, tvChName;
    String name, sChName;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        String getTime = dateFormat.format(date);

        return getTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infjactivity);

        listview = findViewById(R.id.listview);
        btnMakeRoom = findViewById(R.id.btn_MakeRoom);
        btnSetting = findViewById(R.id.btn_Setting);
        tvRoomList = findViewById(R.id.tv_RoomList);
        tvChName = findViewById(R.id.tv_ChName);

        sChName = tvChName.getText().toString();
        myRef = myRef.child(sChName);

        RoomList roomList = new RoomList(INFJActivity.this);
        listview.setAdapter(roomList);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {

                roomList.rooms.clear();
                Iterator i = dataSnapshot.child("rooms").getChildren().iterator();
                while (i.hasNext()) {
                    String sTitle = ((DataSnapshot)i.next()).getKey();
                    int nCount = 0;
                    if(dataSnapshot.child("rooms").child(sTitle).child("user_count").getValue(int.class) == null) {

                    } else {
                        nCount = dataSnapshot.child("rooms").child(sTitle).child("user_count").getValue(int.class);
                    }
                    roomList.addRoom(sTitle, nCount);
                }
                roomList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Room r = (Room)roomList.getItem(i);
                if(r.getsCurrCount() >= 4) {
                    Toast.makeText(getApplicationContext(), "방이 꽉 찼습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else{
                    
                }
                myRef.child("rooms").child(r.getsRoomTitle()).child("chats").child(getTime()).setValue(username + " 님이 들어왔습니다.");
                myRef.child("rooms").child(r.getsRoomTitle()).child("users").child(uid).child("nick").setValue(username);
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("room_name", r.getsRoomTitle());
                intent.putExtra("ch_name", sChName);
                startActivity(intent);
            }
        });
        btnMakeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRoom();
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(INFJActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createRoom() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("방 제목");

        final EditText builder_input = new EditText(this);

        builder.setView(builder_input);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                name = builder_input.getText().toString().trim();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put(name, "");
                myRef.child("rooms").updateChildren(map);
                myRef.child("rooms").child(name).child("users").child(uid).child("nick").setValue(username);

                sChName = tvChName.getText().toString();

                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("room_name", name);
                intent.putExtra("ch_name", sChName);
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
