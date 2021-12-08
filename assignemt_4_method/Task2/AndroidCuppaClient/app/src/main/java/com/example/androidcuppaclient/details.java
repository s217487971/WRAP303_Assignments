package com.example.androidcuppaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String handle = getIntent().getStringExtra("handle");
        EditText number = findViewById(R.id.editTextPhone);
        number.setOnKeyListener((view, i, keyEvent) -> {
            // Did not press the enter key, do nothing.
            if ((keyEvent.getKeyCode() != KeyEvent.KEYCODE_ENTER)
                    || (keyEvent.getAction() != KeyEvent.ACTION_DOWN))
                return false;
            if(number.getText().length()==10)
            {
                try {
                    SaveDataToFile(handle, number.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("telephone", number.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        });
    }

    protected void SaveDataToFile(String handle, String telephone) throws IOException {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return;
            }
        }
        // Create a media file name
        //String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File ListFile;
        String ListFileName = "profile.txt";
        ListFile = new File(mediaStorageDir.getPath() + File.separator + ListFileName);

        //Save the list file
        FileOutputStream fos = new FileOutputStream(ListFile);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(handle + ":" + telephone);
        bw.close();
    }
}
