package com.example.mbit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RoomList extends BaseAdapter {

    private Context mContext;
    ArrayList<Room> rooms = new ArrayList<Room>();

    public RoomList(Context context) {
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int i) {
        return rooms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_room, viewGroup, false);
        }
        TextView tvRoomTitle = view.findViewById(R.id.tv_RoomTitle);
        TextView tvCurrCount = view.findViewById(R.id.tv_CurrCount);

        Room r = rooms.get(i);

        tvRoomTitle.setText(r.getsRoomTitle());
        tvCurrCount.setText(r.getsCurrCount()+"");


        return view;
    }
    public void addRoom(String sRoomTitle, int nCurrCount) {
        Room r = new Room();

        r.setsRoomTitle(sRoomTitle);
        r.setsCurrCount(nCurrCount);

        rooms.add(r);
    }
}
