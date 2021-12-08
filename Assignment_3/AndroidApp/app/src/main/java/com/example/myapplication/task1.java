package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.task1data.SudokuGenerator;
import com.example.myapplication.task1data.SudokuGrid;

public class task1 extends AppCompatActivity {

    Button button;
    SudokuGrid grid;
    SudokuGrid oldGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);

        button = findViewById(R.id.button3);


        final SudokuGrid[] grid = {new SudokuGrid()};
        SudokuGenerator generator = new SudokuGenerator(grid[0]);


        com.example.myapplication.grid gridview = findViewById(R.id.grid);
        gridview.SetGrid(grid[0]);
        gridview.invalidate();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid[0] = generator.WaveCollapseAlgorithm();
                gridview.SetGrid(grid[0]);
                gridview.invalidate();
            }
        };
        button.setOnClickListener(listener);
    }
}