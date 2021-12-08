package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.task1data.SudokuGenerator;
import com.example.myapplication.task1data.SudokuGrid;

public class grid extends View {

    private final int boardColor  = R.color.black;
    private final Paint paint = new Paint();
    private int cellSize = getWidth()/3;
    private String[][] grid = new String[9][9];

    public grid(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int dimension = Math.min(getMeasuredWidth(),getMeasuredHeight());
        setMeasuredDimension(dimension,dimension);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        DrawGrid(canvas);
        DrawMarkers(canvas);
    }

    public void SetGrid(SudokuGrid g)
    {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = String.valueOf(g.GetValue(i,j));
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private void DrawGrid(Canvas canvas)
    {
        paint.setColor(boardColor);
        paint.setStrokeWidth(8);
        cellSize = canvas.getWidth()/9;

        for (int i=0; i<=9 ; i++)
        {
            canvas.drawLine(cellSize*i,0,cellSize*i,canvas.getWidth(),paint);
        }
        for (int j = 0; j <=9; j++) {
            canvas.drawLine(0,cellSize*j,canvas.getWidth(),cellSize*j,paint);
        }
    }
    private void DrawS(Canvas canvas, int row, int col, String number)
    {
        paint.setStrokeWidth(8);


        cellSize = canvas.getWidth()/9;
        paint.setTextSize(cellSize/2);
        //canvas.drawText("S",cellSize/2,cellSize/2,paint);

        int mid = cellSize/3;

        canvas.drawText(number,(cellSize*col)+mid,(cellSize*row)+(mid*2),paint);
        //ClickedSymbol = 0;

    }
    private void DrawMarkers(Canvas canvas) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                        DrawS(canvas, i, j, grid[i][j]);
                }
            }
    }
}
