package com.example.androidcuppaclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

import Networking.messages.product;


public class menu extends AppCompatActivity {

    public int[] quantities = new int[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle data = getIntent().getExtras();
        ArrayList<product> menu = data.getParcelableArrayList("products");
        quantities = new int[menu.size()];
        for (int i = 0; i <menu.size() ; i++) {
            quantities[i] = 0;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        productListAdapter adapter = new productListAdapter(menu, quantities, width,"");
        recyclerView.setAdapter(adapter);

        width = width/6;

        ImageButton confirmButton = findViewById(R.id.imageButton);

        ViewGroup.LayoutParams params1 = confirmButton.getLayoutParams();
        params1.width = width;
        params1.height = width;
        confirmButton.setLayoutParams(params1);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantities = adapter.getQuantities();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("quantities", quantities);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}