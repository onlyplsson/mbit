package com.example.mbit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private ArrayList<Chat> mData = null;

    public ChatAdapter(ArrayList<Chat> data) {
        mData = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;

        ViewHolder(View itemView) {
            super(itemView);

            content = itemView.findViewById(R.id.tv_ChatContent);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_chat, parent, false);
        ChatAdapter.ViewHolder vh = new ChatAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = mData.get(position);

        holder.content.setText(chat.getsChatConetent());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }





}
