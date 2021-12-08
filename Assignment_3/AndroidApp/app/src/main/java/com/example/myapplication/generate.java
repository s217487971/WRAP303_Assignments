package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class generate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        TextView textView  = findViewById(R.id.textView4);
        textView.setText("Number of Unique Grids");
        EditText limitHolder = findViewById(R.id.editTextNumber);


        Button generatenow = findViewById(R.id.button4);
        generatenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int limit = Integer.valueOf(limitHolder.getText().toString());
                Intent intent = new Intent(getApplicationContext(), result.class);
                intent.putExtra("limit",limit);
                startActivity(intent);
            }
        });
    }
}