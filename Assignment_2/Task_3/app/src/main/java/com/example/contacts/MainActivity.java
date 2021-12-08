package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnSendEvent {

    ArrayList<contact> list;
    int STORAGE_READ = 111;
    int STORAGE_WRITE = 112;
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 177;
    public static final int My_Permission_Request_call_phone = 171;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RequestPermissions();

        Bundle bundle = getIntent().getExtras();
        list = ReadListFromFile();

        if (bundle == null){
            list = ReadListFromFile();
            if(list!=null) {
                Toast.makeText(getApplicationContext(), "List Size" + list.size() , Toast.LENGTH_SHORT).show();
            }
            else{
                list = new ArrayList<contact>();
                contact contact1 = new contact("Martha","0781234545",R.drawable.avatar_09);
                contact contact2 = new contact("Adele","0853451212",R.drawable.avatar_pokemon);
                contact contact3 = new contact("Marcus","0781234545",R.drawable.avatar_01);
                contact contact4 = new contact("Stephany","0114242878",R.drawable.avatar_02);
                contact contact5 = new contact("jackie","0766694343",R.drawable.avatar_03);
                contact contact6 = new contact("Sonya","0817682323",R.drawable.avatar_04);
                contact contact7 = new contact("Michelle", "0823215456",R.drawable.avatar_05);
                contact contact8 = new contact("Kate","0875641212", R.drawable.avatar_06);
                contact contact9 = new contact("Ronald", "0745455555",R.drawable.avatar_07);
                contact contact10 = new contact("Xavier","0834567878", R.drawable.avatar_08);
                list.add(contact1);
                list.add(contact2);
                list.add(contact3);
                list.add(contact4);
                list.add(contact5);
                list.add(contact6);
                list.add(contact7);
                list.add(contact8);
                list.add(contact9);
                list.add(contact10);
            }
        }
        else if(bundle!=null)
        {
            contact newone = null;
            if(list==null)
            {
            list = bundle.getParcelableArrayList("list");
            }
            newone = bundle.getParcelable("contact");
            if(newone!=null)
            {
                list.add(newone);
            }
        }




        contactAdapter adapter = new contactAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(adapter);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), contact_card.class);
                intent.putParcelableArrayListExtra("list",list);
                startActivity(intent);
            }
        };
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(onClickListener);


    }

    public void RequestPermissions()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, My_Permission_Request_call_phone);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_READ);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_WRITE);
            }
        }
    }

   /** public ArrayList<contact> ReadListFromFile()
    {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        File ListFile;
        String ListFileName="file.txt";
        String pictureFilePath;
        pictureFilePath = mediaStorageDir.getPath() + File.separator + ListFileName;
        ListFile = new File(mediaStorageDir.getPath() + File.separator + ListFileName);
        //list = (ArrayList) fileIn.readObject()  ileOut.writeChars(x.ID+";"+x.lastText+";"+x.imgPath);

    reader = new BufferedReader(new FileReader(ListFile));
        if(ListFile.exists()) {
            list = new ArrayList<contact>();
            BufferedReader reader;
            try {
                String line = reader.readLine();
                while (line != null) {
                    //System.out.println(line);
                    // read next line
                    String string = line;
                    String[] parts = string.split(";");
                    String ID = parts[0]; // Phone Number
                    String name = parts[1]; // Name
                    String number = parts[2] ;//Number
                    int imgPath = Integer.valueOf(parts[2]); // Picture
                    contact contact2 = new contact(ID,name,number,imgPath);
                    list.add(contact2);
                    line = reader.readLine();
                }
                reader.close();
                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    */
    public ArrayList<contact> ReadListFromFile()
    {
        FileInputStream fis = null;
        try {
            fis = getApplicationContext().openFileInput("file.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(fis!=null) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();

            list = new ArrayList<contact>();
            BufferedReader reader;
            try {
                reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    //System.out.println(line);
                    // read next line
                    stringBuilder.append(line).append('\n');
                    String string = line;
                    String[] parts = string.split(";");
                    String id = parts[0]; //Unique ID
                    String name = parts[1]; // Name
                    String number = parts[2]; // Number
                    String imgPath = parts[3]; // Picture
                    contact contact2 = new contact(id, name, number, Integer.valueOf(imgPath));
                    list.add(contact2);
                    line = reader.readLine();
                }
                reader.close();
                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_READ) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Storage Read permission denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == STORAGE_WRITE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Write permission granted", Toast.LENGTH_LONG).show();
            } else {
               // Toast.makeText(this, "Storage Write permission denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS SEND permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "SMS SEND permission denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == My_Permission_Request_call_phone) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Phone call granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Phone call permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void SendSms(String number)
    {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , number);
        smsIntent.putExtra("sms_body"  , "Test ");

        try {
            startActivity(smsIntent);
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void SendListner(String number) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else if(ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED)
        {
            SendSms(number);
        }
    }
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(getApplicationContext(),MainActivity.class);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}