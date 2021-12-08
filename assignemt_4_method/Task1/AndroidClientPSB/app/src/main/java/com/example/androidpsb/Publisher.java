package com.example.androidpsb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Networking.SubscriberClient;
import Networking.messages.Message;
import Networking.messages.MessageReceiver;
import Networking.messages.client.Subscribe;
import Networking.messages.server.ChatMessageReceived;
import Networking.messages.server.Subscribed;
import Networking.messages.server.TopicsListed;
import Networking.messages.server.Unsubscribed;

public class Publisher extends AppCompatActivity {

    SubscriberClient client;
    Map<String,ArrayList<String>> publications = new HashMap<>();
    ArrayList<String> topicViewList;
    TopicListAdapter adapter;
    CustomClickListener AnotherclickListener;
    CustomClickListener clickListener;

    TextView welcome;
    TextView topicList;
    RecyclerView topicRecyclerView;
    EditText EditNewTopic;
    Button createTopicButton;
    TextView selectedTopics;
    TextView selectedTopicsLabel;
    Button disconnect;
    Button SubscribeButton;

    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;
    private RecyclerView chats;
    private EditText sendmessage;
    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);
        String handler = getIntent().getStringExtra("handler");
        String server = getIntent().getStringExtra("server");
        client = new SubscriberClient(
                message -> runOnUiThread(
                        () -> messageReceived(message)
                ));
        client.connect(server,handler);
        try {
            Thread.sleep(1000);
        }
        catch (Exception e)
        {
            System.out.println("e"+e);
        }

        chats = findViewById(R.id.recyclerView3);
        sendmessage = findViewById(R.id.editTextTextPersonName2);
        spinner = findViewById(R.id.spinner);
        spinner.setBackgroundColor(Color.parseColor("#2E7D32"));
        welcome = findViewById(R.id.welcomeTextView);
        topicList = findViewById(R.id.topicListLabel);
        topicRecyclerView  = findViewById(R.id.recyclerView);
        EditNewTopic = findViewById(R.id.editTextTopicName);
        createTopicButton = findViewById(R.id.createTopic);
        selectedTopicsLabel = findViewById(R.id.subscribedTopicsLabel);
        selectedTopics = findViewById(R.id.textView11);
        disconnect = findViewById(R.id.button3);
        SubscribeButton = findViewById(R.id.button4);
        welcome.setText("Welcome, "+handler);
        createTopicButton.setBackgroundColor(Color.parseColor("#00897B"));
        disconnect.setBackgroundColor(Color.parseColor("#D84315"));
        SubscribeButton.setBackgroundColor(Color.parseColor("#2E7D32"));

        //Retrieves Data from RecyclerView And returns it to the Parent Activity
        clickListener = new CustomClickListener();
        AnotherclickListener = new CustomClickListener();
        CustomClickListener.listener listener = new CustomClickListener.listener() {
            @Override
            public void onChange(Object listenToMe) {
                String Topic = String.valueOf(listenToMe);
                publications.put(Topic, new ArrayList<>());
                client.joinGroup(Topic);
            }
        };
        CustomClickListener.listener listener2 = new CustomClickListener.listener() {
            @Override
            public void onChange(Object listenToMe) {
                String Topic = String.valueOf(listenToMe);
                publications.remove(Topic);
                client.leaveTopic(Topic);

                StringBuilder stringBuilder = new StringBuilder();
                int i = 1;
                for (String topic:publications.keySet()
                ) {
                    stringBuilder.append(i);
                    stringBuilder.append(".");
                    stringBuilder.append(topic);
                    stringBuilder.append("\n");
                    i++;
                }
                selectedTopics.setText(stringBuilder.toString());
            }
        };

        clickListener.setClickListener(listener);
        AnotherclickListener.setClickListener(listener2);

        View.OnClickListener addTopicLister = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EditNewTopic.toString().length()>0)
                {
                    client.AddTopic(EditNewTopic.getText().toString());
                    EditNewTopic.setText("");
                    EditNewTopic.setVisibility(View.GONE);
                    createTopicButton.setVisibility(View.GONE);
                    TextView textViewt = findViewById(R.id.textView8);
                    textViewt.setVisibility(View.GONE);
                }
            }
        };
        createTopicButton.setOnClickListener(addTopicLister);
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.disconnect();
            }
        });
        SubscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubscribeButton.setVisibility(View.GONE);
                topicList.setVisibility(View.GONE);
                topicRecyclerView.setVisibility(View.GONE);
                EditNewTopic.setVisibility(View.GONE);
                createTopicButton.setVisibility(View.GONE);
                selectedTopicsLabel.setText("Recieving Notifications Below");
                selectedTopics.setVisibility(View.GONE);
                SubscribeButton.setVisibility(View.GONE);
                TextView textViewt = findViewById(R.id.textView8);
                textViewt.setVisibility(View.GONE);

                topicViewList = new ArrayList<>();
                for (String current:publications.keySet()
                     ) {
                    topicViewList.add(current);
                }
                groupName = topicViewList.get(0);
                spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, // a pre-define layout with text views
                        android.R.id.text1, // the id of the text view in which to display the toString value
                        topicViewList); // the group name collection to be displayed
                spinner.setAdapter(spinnerAdapter);
                spinner.setVisibility(View.VISIBLE);
            }
        });

        topicViewList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new TopicListAdapter(topicViewList,clickListener, AnotherclickListener);
        topicRecyclerView.setAdapter(adapter);
        topicRecyclerView.setLayoutManager(layoutManager);


        // Get an adapter to display a group name in the Spinner.
        // Going to use one with a predefined layout that uses the toString method
        // of the data item.
        spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, // a pre-define layout with text views
                android.R.id.text1, // the id of the text view in which to display the toString value
                topicViewList); // the group name collection to be displayed
        spinner.setAdapter(spinnerAdapter);

        RecyclerView.LayoutManager layoutManager4;
        layoutManager4 = new LinearLayoutManager(getApplicationContext());
        chats.setLayoutManager(layoutManager4);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                groupName = spinnerAdapter.getItem(index);
                chatAdapter adapter4 = new chatAdapter(publications.get(groupName));
                chats.setAdapter(adapter4);
                chats.setVisibility(View.VISIBLE);
                sendmessage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        sendmessage.setOnKeyListener((view, i, keyEvent) -> {
            // Did not press the enter key, do nothing.
            if ((keyEvent.getKeyCode() != KeyEvent.KEYCODE_ENTER)
                    || (keyEvent.getAction() != KeyEvent.ACTION_DOWN))
                return false;
            // Send message to group.
            String chatMessage = sendmessage.getText().toString();
            client.sendChatMessage(chatMessage,groupName);
            // Clear text in edit view.
            sendmessage.setText("");
            return true;
        });



    }


    public void messageReceived(Message message) {

        if(message instanceof TopicsListed)
        {
            topicViewList = new ArrayList<>();
            for (String word:((TopicsListed) message).topicNames
                 ) {
                topicViewList.add(word);
            }
            adapter.SetList(topicViewList);
            topicRecyclerView.setAdapter(adapter);
        }
        else if(message instanceof ChatMessageReceived)
        {
            String topic = ((ChatMessageReceived) message).topicName;
            String handler = ((ChatMessageReceived) message).handle;
            String content = ((ChatMessageReceived) message).chatMessage;
            String time = ((ChatMessageReceived) message).timeStamp.toString();
            StringBuilder str = new StringBuilder(time);
            str.append("\n");str.append(handler);str.append(":");str.append(content);
            publications.get(topic).add(str.toString());
            chatAdapter adapter4 = new chatAdapter(publications.get(groupName));
            chats.setAdapter(adapter4);
        }
        else if(message instanceof Subscribed)
        {
            publications.put(((Subscribed) message).topicName, new ArrayList<>());
            StringBuilder stringBuilder = new StringBuilder();
            int i = 1;
            for (String topic:publications.keySet()
            ) {
                stringBuilder.append(i);
                stringBuilder.append(".");
                stringBuilder.append(topic);
                stringBuilder.append("\n");
                i++;
            }
            selectedTopics.setText(stringBuilder.toString());
        }
        else if(message instanceof Unsubscribed)
        {
            publications.remove(((Unsubscribed) message).topicName);
            StringBuilder stringBuilder = new StringBuilder();
            int i = 1;
            for (String topic:publications.keySet()
            ) {
                stringBuilder.append(i);
                stringBuilder.append(".");
                stringBuilder.append(topic);
                stringBuilder.append("\n");
                i++;
            }
            selectedTopics.setText(stringBuilder.toString());
        }
    }
}