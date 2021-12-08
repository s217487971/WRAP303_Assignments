package com.example.androidpsb;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.chatViewHolder> {
    // The list of messages displayed by the recycler view.
    private ArrayList<String> messages = new ArrayList<>();
    public chatAdapter(ArrayList<String> list) {
        messages = list;
    }

    @NonNull
    @Override
    public chatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.chatview,
                        parent, false);
        chatViewHolder mvh = new chatViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull chatViewHolder holder, int position) {
        String message = messages.get(position);
        holder.setChat(message);
    }

    @Override
    public int getItemCount() {

        return messages.size();
    }

    public class chatViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHandleLeft;
        public String message;

        public chatViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHandleLeft = itemView.findViewById(R.id.textView5);

        }
        public void setChat(String message) {
            this.message = message;
            txtHandleLeft.setText(message);
        }

    }

}
