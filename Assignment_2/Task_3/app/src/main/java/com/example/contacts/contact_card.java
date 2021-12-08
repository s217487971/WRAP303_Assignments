package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class contact_card extends AppCompatActivity {

    int STORAGE_WRITE = 114;
    private static final int S = 272;
    contact contact;
    private String pictureFilePath = "drawable://" + R.drawable.avatar_01;
    ImageView imageView ;
    ArrayList<contact> list;
    int[] resc = new int[10];
    int pos = 0;
    String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_card);
        Bundle bundle = getIntent().getExtras();
        imageView = findViewById(R.id.imageView2);
        EditText text = findViewById(R.id.editTextTextPersonName);
        EditText text1 = findViewById(R.id.editTextPhone);
        contact = null;
        list = null;
        fillResc();


        if(bundle!=null) {
            contact = bundle.getParcelable("contact");
            list = bundle.getParcelableArrayList("list");
            if(contact!=null)
            {
                text.setText(contact.getName());
                text1.setText(contact.getNumber());
                pos = getPos(contact.getImgResource());
                temp = contact.getId();
                Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), contact.getImgResource());
                //imageTemp.setImageBitmap(icon);
                imageView.setImageBitmap(icon);
            }
        }
        if(contact==null)
        {
            contact = new contact("name","number",R.drawable.avatar_01);
            text.setHint(contact.getName());
            text1.setHint(contact.getNumber());
            temp = contact.getId();
            pos = 0;
            Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), contact.getImgResource());
            //imageTemp.setImageBitmap(icon);
            imageView.setImageBitmap(icon);
        }




        Button button = findViewById(R.id.button);

        imageView.setClickable(true);


        FloatingActionButton button1 = findViewById(R.id.floatingActionButton2);

        View.OnClickListener call = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = contact.getNumber();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        };
        View.OnClickListener cycle = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = (pos+1)%10;
                imageView.setImageDrawable(getDrawable(resc[pos]));
            }
        };
        View.OnClickListener save = new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                String numner = text1.getText().toString();
                String name = text.getText().toString();
                int drawable = resc[pos];
                if (name.length()>0 && numner.length()==10) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    if (list != null) {
                        UpdateInList(numner, list, resc[pos], name);
                        intent.putExtra("list", list);
                        try {
                            WriteListToFile(list);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        intent.putExtra("contact", (Parcelable) new contact(name, numner, drawable));
                    }
                    startActivity(intent);
                }
            }
        };
        imageView.setOnClickListener(cycle);
        /**View.OnClickListener save = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED )
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_WRITE);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    if(list==null) {
                        try {
                            WriteToFile(new contact(text.getText().toString(), text1.getText().toString(), imgrecource));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(list!=null)
                    {
                        try {
                            list.add(new contact(text.getText().toString(),text1.getText().toString(),imgrecource));
                            WriteListToFile(list);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    startActivity(intent);
                }
            }
        };*/
        button.setOnClickListener(save);
        button1.setOnClickListener(call);
    }
   /** public void WriteListToFile(ArrayList<contact> list) throws IOException {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return;
            }
        }
        // Create a media file name
        //String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File ListFile;
        String ListFileName="file.txt";
        pictureFilePath = mediaStorageDir.getPath() + File.separator + ListFileName;
        ListFile = new File(mediaStorageDir.getPath() + File.separator + ListFileName);

        //Save the list file
        FileOutputStream fos = new FileOutputStream(ListFile);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        for (contact contact2:list
        ) {
            bw.write(contact2.getId()+";"+contact2.getName()+";"+contact2.getNumber()+";"+contact2.getImgResource());
            bw.newLine();
        }
        bw.close();
    }*/
    public void WriteListToFile(ArrayList<contact> list) throws IOException {

        // Create a media file name
        //String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        String ListFileName="file.txt";
        File ListFile = new File(getFilesDir(),ListFileName);

        //Save the list file
        //FileOutputStream fos = new FileOutputStream(ListFile);
        FileOutputStream fos = getApplicationContext().openFileOutput(ListFileName, Context.MODE_PRIVATE);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        for (contact contact2:list
        ) {
            bw.write(contact2.getId()+";"+contact2.getName()+";"+contact2.getNumber()+";"+contact2.getImgResource());
            bw.newLine();
        }
        bw.close();
    }

    public void UpdateInList(String number, ArrayList<contact> list, int img, String Name)
    {
        for (contact c:list
             ) {
            if(c.getId().equals(temp))
            {
                c.setImgResource(img);
                c.setName(Name);
                c.setNumber(number);
                return;
            }
        }
        list.add(new contact(Name,number,img));
    }
    public void fillResc()
    {
        resc[0] = R.drawable.avatar_01;
        resc[1] = R.drawable.avatar_02;
        resc[2] = R.drawable.avatar_03;
        resc[3] = R.drawable.avatar_04;
        resc[4] = R.drawable.avatar_05;
        resc[5] = R.drawable.avatar_06;
        resc[6] = R.drawable.avatar_07;
        resc[7] = R.drawable.avatar_08;
        resc[8] = R.drawable.avatar_09;
        resc[9] = R.drawable.avatar_pokemon;

    }

    protected int getPos(int drawable)
    {
        for (int i = 0; i < 10; i++) {
            if(drawable==resc[i])
            {
                return i;
            }
        }
        return 0;
    }

}