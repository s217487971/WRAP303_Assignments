package com.example.androidcuppaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class collection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        TextView title = findViewById(R.id.textView);
        TextView invoice = findViewById(R.id.textView2);
        Button collectButton = findViewById(R.id.button);

        Bundle data = getIntent().getExtras();
        String incvoiceCode = data.getString("code");
        String invoicec = data.getString("invoice");

        invoice.setText(invoicec);
        title.setText("Order Complete");




        collectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("quantities", "DONE");
                intent.putExtra("code", incvoiceCode);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}