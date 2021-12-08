package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class view extends AppCompatActivity {

    String directory;
    int start = 0;
    int count = 0;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Button button = findViewById(R.id.button8);
        Button button1 = findViewById(R.id.button7);
        textView = findViewById(R.id.textView7);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start++;
                start = start%count;
                show(start);
            }
        };

        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start--;
                start = start%count;
                show(start);
            }
        };


        int count;

        //String gridtype = getIntent().getStringExtra("list");
        //if(gridtype=="all")
            directory = Environment.getExternalStorageDirectory()
                    + "/Android/data/"
                    + getApplicationContext().getPackageName()
                    + "/Files";
            show(start);

    }
    public void show(int star){
        String filename = directory + "\\" + String.valueOf(star) + ".txt";
        File mediaStorageDir = new File(directory);
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return;
            }
        }
        File ListFile = new File(mediaStorageDir.getPath() + File.separator + filename);
        if(ListFile.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(ListFile));
                String line = reader.readLine();
                while (line != null) {
                    String string = line;
                    /**String[] parts = string.split(" ");
                    String ID = parts[0]; // Phone Number
                    String name = parts[1]; // Name
                    String number = parts[2] ;//Number*/
                    line = reader.readLine();
                }
                reader.close();
                textView.setText(line);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}