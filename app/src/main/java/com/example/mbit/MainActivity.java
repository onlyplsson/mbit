package com.example.mbit;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("리스트뷰 테스트");

        final String[] mid = {"안진표님의 대화방입니다.","박정균님의 대화방입니다.","정한별님의 대화방입니다.","이동인님의 대화방입니다.","안진표님의 대화방입니다.","박정균님의 대화방입니다."};

        ListView list = (ListView) findViewById(R.id.ListView1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mid);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(getApplicationContext(),mid[arg2],
                        Toast.LENGTH_SHORT).show();
            }
        });







    }

}