package com.example.mbit;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    Button btnMakeRoom;
    String name;
    private DatabaseReference reference_ISFP = FirebaseDatabase.getInstance().getReference().getRoot();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isfpactivity);

        listviewISFP = findViewById(R.id.listview_ISFP);
        btnMakeRoom = findViewById(R.id.btn_MakeRoom);



        final ArrayList<String> list_ISFP = new ArrayList<>();
        final ArrayAdapter<String> adapter_ISFP = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_ISFP);
        listviewISFP.setAdapter(adapter_ISFP);

        reference_ISFP.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());
                }

                list_ISFP.clear();
                list_ISFP.addAll(set);

                adapter_ISFP.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        listviewISFP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                reference_ISFP.child(((TextView) view).getText().toString()).child("users").child(username).setValue(uid);
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("room_name", ((TextView) view).getText().toString());
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
    private void createUserName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("채팅방에 사용할 이름을 입력하세요");

        final EditText builder_input = new EditText(this);

        builder.setView(builder_input);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                name = builder_input.getText().toString();

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
