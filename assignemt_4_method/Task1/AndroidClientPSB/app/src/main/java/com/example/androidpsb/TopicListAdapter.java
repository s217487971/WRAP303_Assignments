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

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.TopicViewHolder> {
    // The list of messages displayed by the recycler view.
    private ArrayList<String> messages = new ArrayList<>();
    CustomClickListener listener;
    CustomClickListener listener2;

    public TopicListAdapter(ArrayList<String> list, CustomClickListener clickListener, CustomClickListener listener2) {
        messages = list;
        this.listener = clickListener;
        this.listener2 = listener2;
    }
    public void SetList(ArrayList<String> list)
    {
        messages = list;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.topicview,
                        parent, false);
        TopicViewHolder mvh = new TopicViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        String message = messages.get(position);
        holder.setTopic(message, listener , listener2);
    }

    @Override
    public int getItemCount() {

        return messages.size();
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHandleLeft;
        public Button button0;
        public Button button1;
        public String message;
        CustomClickListener listener0;
        CustomClickListener listener1;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHandleLeft = itemView.findViewById(R.id.topicNameText);
            button0 = itemView.findViewById(R.id.joinButton);
            button1 = itemView.findViewById(R.id.leaveButton);

        }

        public void setTopic(String message,CustomClickListener listener, CustomClickListener listener2) {
            this.message = message;
            this.listener0 = listener;
            this.listener1= listener2;
            txtHandleLeft.setText(message);
            button0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener0.notifyListners(message);
                }
            });
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener1.notifyListners(message);
                }
            });
        }

    }

}
