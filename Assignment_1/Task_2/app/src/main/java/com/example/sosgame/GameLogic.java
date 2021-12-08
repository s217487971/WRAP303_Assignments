package com.example.sosgame;

public class GameLogic {

    public int[][] SOS;
    int ButtonClicked = 0;
    int score = 0;
    int plays = 0;
    ChangeListener listener = new ChangeListener(plays);

    public GameLogic()
    {
        SOS = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                SOS[i][j] = 0;
            }
        }

    }

    public int[][] getSOS() {
        return SOS;
    }
    public boolean insertSymbol(int symbol, int row, int col)
    {
        if(SOS[row][col]==0)
        {
            SOS[row][col] = symbol;
            plays = plays +1;
            listener.somethingChanged();
            return true;
        }
        else
            return false;
    }
    public int getPlays()
    {
        return plays;
    }
    public void CheckSOS(int row,int col)
    {
        int value = SOS[row][col];
        //CHeck for O
        if(value==2)
        {
            if(row-1>=0 && row+1<5)
            {
                if(SOS[row-1][col]==1 && SOS[row+1][col]==1)
                {
                    score = score +1;
                }
            }
            if(col-1>=0 && col+1<5)
            {
                if(SOS[row-1][col]==1 && SOS[row+1][col]==1)
                {
                    score = score +1;
                }
            }
            if(row-1>=0 && col-1>=0 && row+1<5 && col+1<5)
            {
                if(SOS[row-1][col-1]==1 && SOS[row+1][col+1]==1)
                {
                    score = score +1;
                }
                if(SOS[row-1][col+1]==1 && SOS[row+1][col-1]==1)
                {
                    score = score +1;
                }
            }
        }
        // CHeck for S
        if(value==1)
        {
            if(row+2<5)
            {
                if(SOS[row+1][col]==2 && SOS[row+2][col]==1)
                {
                    score = score +1;
                }
            }
            if(row-2>=0)
            {
                if(SOS[row-1][col]==2 && SOS[row-2][col]==1)
                {
                    score = score +1;
                }
            }
            if(col+2<5)
            {
                if(SOS[row][col+1]==2 && SOS[row][col+2]==1)
                {
                    score = score +1;
                }
            }
            if(col-2>=0)
            {
                if(SOS[row][col-1]==2 && SOS[row][col-2]==1)
                {
                    score = score +1;
                }
            }
            if(col+2<5 && row+2<5)
            {
                if(SOS[row+1][col+1]==2 && SOS[row+2][col+2]==1)
                {
                    score = score +1;
                }
            }
            if(col-2>=0 && row-2>=0)
            {
                if(SOS[row-1][col-1]==2 && SOS[row-2][col-2]==1)
                {
                    score = score +1;
                }
            }
            if(col-2>=0 && row+2<5)
            {
                if(SOS[row+1][col-1]==2 && SOS[row+2][col-2]==1)
                {
                    score = score +1;
                }
            }
            if(col+2<5 && row-2>=0)
            {
                if(SOS[row-1][col+1]==2 && SOS[row-2][col+2]==1)
                {
                    score = score +1;
                }
            }
        }

    }
    public void UpdateClick(int value)
    {
        ButtonClicked = value;
    }
    public void setScore(int value)
    {
        score = value;
    }
    public int getScore()
    {
        return score;
    }
}
