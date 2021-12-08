package com.example.sosgame;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class SOSGameBoard extends View {

    private final int boardColor;
    private final int SColor;
    private final int OColor;
    private final int Player1LineColor;
    private final int Player2LineColor;
    /**
     * Clicking S Will result in 1
     * Clicking O Will result in 2
     * Unclicked default is 0
     */
    public int ClickedSymbol = 0;
    private int row;
    private int col;
    int score;
    boolean sos;
    ChangeListener listener;
    int player;
    ArrayList<line> lines;


    private final Paint paint = new Paint();

    public final GameLogic game;

    private int cellSize = getWidth()/3;

    public SOSGameBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game = new GameLogic();
        lines = new ArrayList<>();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.SOSGameBoard,0,0);

        try{
            boardColor = a.getInteger(R.styleable.SOSGameBoard_boardColor,0);
            SColor = a.getInteger(R.styleable.SOSGameBoard_SColor,0);
            OColor = a.getInteger(R.styleable.SOSGameBoard_OColor,0);
            Player1LineColor = a.getInteger(R.styleable.SOSGameBoard_Player1LineColor,0);
            Player2LineColor = a.getInteger(R.styleable.SOSGameBoard_Player2LineColor,0);

    }finally {
            a.recycle();
        }
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

        DrawGameBoard(canvas);
        /**DrawS(canvas,0,0);
        DrawO(canvas,3,3);
        DrawPlayerOneLine(canvas,0,0,3,3);*/
        DrawMarkers(canvas);
        ClickedSymbol = 0;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float X = event.getX();
        float Y = event.getY();

        int action = event.getAction();

        if(action== MotionEvent.ACTION_DOWN && ClickedSymbol!=0)
        {

            row = (int) Math.ceil(Y/cellSize) - 1;
            col = (int) Math.ceil(X/cellSize) - 1;

            invalidate();
            return true;
        }
        else
        return false;
    }

    private void DrawMarkers(Canvas canvas)
    {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <5 ; j++) {
                if(game.getSOS()[i][j] !=0)
                {
                    if(game.getSOS()[i][j]==1)
                    {
                        DrawS(canvas,i,j);
                    }
                    else if(game.getSOS()[i][j]==2)
                    {
                        DrawO(canvas,i,j);
                    }
                }
            }
        }
        for (int i = 0; i < lines.size(); i++) {
            line a = lines.get(i);
            DrawPlayerOneLine(canvas,a.row1,a.col1,a.row2,a.col2,a.player);
        }
                if(game.getSOS()[row][col] == 0 && ClickedSymbol!=0)
                {

                    if(game.insertSymbol(ClickedSymbol,row,col))
                    {

                        if(ClickedSymbol==1)
                        {
                            DrawS(canvas,row,col);
                            CheckMatchForS(canvas, row,col,player);
                        }
                        else if(ClickedSymbol==2)
                        {
                            DrawO(canvas,row,col);
                            CheckMatchForO(canvas,row,col,player);
                        }
                        listener.somethingChanged();
                    }
        }

    }
    public void CheckMatchForS(Canvas canvas, int row, int col, int player)
    {
        score = 0;
            if(row+2<5)
            {
                if(game.getSOS()[row+1][col]==2 && game.getSOS()[row+2][col]==1)
                {
                    score = score +1;
                    lines.add(new line(row,col,row+2,col,player));
                    DrawPlayerOneLine(canvas,row,col,row+2,col,player);
                }
            }
            if(row-2>=0)
            {
                if(game.getSOS()[row-1][col]==2 && game.getSOS()[row-2][col]==1)
                {
                    score = score +1;
                    lines.add(new line(row,col,row-2,col,player));
                    DrawPlayerOneLine(canvas,row,col,row-2,col,player);
                }
            }
            if(col+2<5)
            {
                if(game.getSOS()[row][col+1]==2 && game.getSOS()[row][col+2]==1)
                {
                    score = score +1;
                    lines.add(new line(row,col,row,col+2,player));
                    DrawPlayerOneLine(canvas,row,col,row,col+2,player);
                }
            }
            if(col-2>=0)
            {
                if(game.getSOS()[row][col-1]==2 && game.getSOS()[row][col-2]==1)
                {
                    score = score +1;
                    lines.add(new line(row,col,row,col-2,player));
                    DrawPlayerOneLine(canvas,row,col,row,col-2,player);
                }
            }
            if(col+2<5 && row+2<5)
            {
                if(game.getSOS()[row+1][col+1]==2 && game.getSOS()[row+2][col+2]==1)
                {
                    score = score +1;
                    lines.add(new line(row,col,row+2,col+2,player));
                    DrawPlayerOneLine(canvas,row,col,row+2,col+2,player);
                }
            }
            if(col-2>=0 && row-2>=0)
            {
                if(game.getSOS()[row-1][col-1]==2 && game.getSOS()[row-2][col-2]==1)
                {
                    score = score +1;
                    lines.add(new line(row,col,row-2,col-2,player));
                    DrawPlayerOneLine(canvas,row,col,row-2,col-2,player);
                }
            }
            if(col-2>=0 && row+2<5)
            {
                if(game.getSOS()[row+1][col-1]==2 && game.getSOS()[row+2][col-2]==1)
                {
                    score = score +1;
                    lines.add(new line(row,col,row+2,col-2,player));
                    DrawPlayerOneLine(canvas,row,col,row+2,col-2,player);
                }
            }
            if(col+2<5 && row-2>=0)
            {
                if(game.getSOS()[row-1][col+1]==2 && game.getSOS()[row-2][col+2]==1)
                {
                    score = score +1;
                    lines.add(new line(row,col,row-2,col+2,player));
                    DrawPlayerOneLine(canvas,row,col,row-2,col+2,player);
                }
            }
    }
    public void CheckMatchForO(Canvas canvas, int row, int col, int player)
    {
        score = 0;
            if(row-1>=0 && row+1<5)
            {
                if(game.getSOS()[row-1][col]==1 && game.getSOS()[row+1][col]==1)
                {
                    score = score +1;
                    lines.add(new line(row-1,col,row+1,col,player));
                    DrawPlayerOneLine(canvas,row-1,col,row+1,col,player);
                }
            }
            if(col-1>=0 && col+1<5)
            {
                if(game.getSOS()[row][col-1]==1 && game.getSOS()[row][col+1]==1)
                {
                    score = score +1;
                    lines.add(new line(row,col-1,row,col+1,player));
                    DrawPlayerOneLine(canvas,row,col-1,row,col+1,player);
                }
            }
            if(row-1>=0 && col-1>=0 && row+1<5 && col+1<5)
            {
                if(game.getSOS()[row-1][col-1]==1 && game.getSOS()[row+1][col+1]==1)
                {
                    score = score +1;
                    lines.add(new line(row-1,col-1,row+1,col+1,player));
                    DrawPlayerOneLine(canvas,row-1,col-1,row+1,col+1,player);
                }
                if(game.getSOS()[row-1][col+1]==1 && game.getSOS()[row+1][col-1]==1)
                {
                    score = score +1;
                    lines.add(new line(row-1,col+1,row+1,col-1,player));
                    DrawPlayerOneLine(canvas,row-1,col+1,row+1,col-1,player);
                }
            }
    }

    private void DrawGameBoard(Canvas canvas)
    {
        paint.setColor(boardColor);
        paint.setStrokeWidth(8);

        cellSize = canvas.getWidth()/5;

        for (int i=0; i<=5 ; i++)
        {
            canvas.drawLine(cellSize*i,0,cellSize*i,canvas.getWidth(),paint);
        }
        for (int j = 0; j <=5; j++) {
            canvas.drawLine(0,cellSize*j,canvas.getWidth(),cellSize*j,paint);
        }
    }
    private void DrawO(Canvas canvas, int row, int col)
    {
        paint.setColor(SColor);
        paint.setStrokeWidth(8);


        cellSize = canvas.getWidth()/5;
        paint.setTextSize(cellSize/2);
        //canvas.drawText("S",cellSize/2,cellSize/2,paint);

        int mid = cellSize/3;

        canvas.drawText("0",(cellSize*col)+mid,(cellSize*row)+(mid*2),paint);
        //ClickedSymbol = 0;
    }
    private void DrawS(Canvas canvas, int row, int col)
    {
        paint.setColor(SColor);
        paint.setStrokeWidth(8);


        cellSize = canvas.getWidth()/5;
        paint.setTextSize(cellSize/2);
        //canvas.drawText("S",cellSize/2,cellSize/2,paint);

        int mid = cellSize/3;

        canvas.drawText("S",(cellSize*col)+mid,(cellSize*row)+(mid*2),paint);
        //ClickedSymbol = 0;

    }
    private void DrawPlayerOneLine(Canvas canvas,int row1, int col1, int row2, int col2, int player)
    {
        if(player==1)
        {
        paint.setColor(Player1LineColor);}
        if(player==2)
        {
            paint.setColor(Player2LineColor);
        }
        paint.setStrokeWidth(4);
        cellSize = canvas.getWidth()/5;

        int mid = cellSize/2;

        canvas.drawLine((cellSize*col1)+mid,(cellSize*row1)+mid,(cellSize*col2)+mid,(cellSize*row2)+mid,paint);

    }
    private void DrawPlayerTwoLine(Canvas canvas, int row1, int col1, int row2, int col2)
    {
        paint.setColor(Player2LineColor);
        paint.setStrokeWidth(6);
        cellSize = canvas.getWidth()/5;

        int mid = cellSize/2;

        canvas.drawLine((cellSize*col1)+mid,(cellSize*row1)+mid,(cellSize*col2)+mid,(cellSize*row2)+mid,paint);

    }
    public void setClickedSymbol(int value)
    {
        ClickedSymbol = value;
    }
    public void setGameLogic(GameLogic game)
    {

    }
    public boolean getMatch()
    {
        return sos;
    }
    public void SetSOS(boolean va)
    {
        sos = va;
    }
    public int getScore()
    {
        return score;
    }
    public void SetScore(int score)
    {
        this.score = score;
    }
    public void setPlayer(int player)
    {
        this.player = player;
    }
    public int getPlays(){return  game.getPlays();}
    public int getClickedSymbol()
    {
        return getClickedSymbol();
    }
    public void setListener(ChangeListener listener)
    {
        this.listener = listener;
    }
}
