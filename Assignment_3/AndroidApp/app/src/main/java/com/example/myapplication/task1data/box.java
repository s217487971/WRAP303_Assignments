package com.example.myapplication.task1data;

public class box {
    int[] possibilities;
    private int size;
    private int fact;

    public box()
    {
        fact = 0;
        possibilities = new int[]{1,2,3,4,5,6,7,8,9};
        size = 9;
    }

    public void remove(int b)
    {
            if (b > 0 && b < 10 && size>0) {
                if(possibilities[b - 1] != 0)
                {
                    possibilities[b - 1] = 0;
                    size = size - 1;

                }
            }
    }
    public void checkFact()
    {
            for (int i = 0; i < 8; i++) {
                if(possibilities[i]!=0)
                {
                    fact = possibilities[i];
                    size = 0;
                    return;
                }
            }

    }
    public int getFact(
    )
    {
        return fact;
    }
    public int getPossibilityCount()
    {
        return size;
    }
    public void put(int g)
    {
        if(fact==0)
        {
            if(Insertable(g))
            {
                remove(g);
                fact = g;
            }
        }
    }
    public boolean Insertable(int g)
    {
        for (int i = 0; i <9 ; i++) {
            if(g == possibilities[i])
            {
                return true;
            }
        }
        return false;
    }

    public String getPossibilities() {


            String p = "";
            for (int i = 0; i <9 ; i++) {

                if(possibilities[i]!=0){
                    p = p + String.valueOf(possibilities[i])+" ";
                }
            }
            return p;

    }
    public int getFirstPossiblility()
    {
        for (int i = 0; i < 9; i++) {
            if(possibilities[i]!=0)
                return possibilities[i];
        }
        return 0;
    }
}
