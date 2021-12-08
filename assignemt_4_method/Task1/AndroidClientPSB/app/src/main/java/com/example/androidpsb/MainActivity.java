package com.example.androidpsb;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Networking.SubscriberClient;
import Networking.messages.Message;
import Networking.messages.MessageReceiver;


public class MainActivity extends AppCompatActivity {

    private final int Internet_request = 478;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText subscriberIdView = findViewById(R.id.editTextTextPersonName);
        EditText serverIPView = findViewById(R.id.editTextNumber);

        if(checkSelfPermission(Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, Internet_request);
        }

        Button connect = findViewById(R.id.button);
        connect.setBackgroundColor(Color.parseColor("#00897B"));

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subscriber = subscriberIdView.getText().toString();
                String server = serverIPView.getText().toString();
                if(server.length()<3)
                {
                    server = "192.168.43.178";
                }
                Intent intent = new Intent(getApplicationContext(), Publisher.class);
                intent.putExtra("handler", subscriber);
                intent.putExtra("server", server);
                startActivity(intent);
            }
        };
        connect.setOnClickListener(onClickListener);
    }

}