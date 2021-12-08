package com.example.sosgame;

import java.security.PublicKey;

public class Player {

    public String name;
    public int Score;

    public Player(String name)
    {
        this.name = name;
        Score = 0;
    }

    public void AddPoint(int point)
    {
        Score = Score + point;
    }

    public void Reset()
    {
        Score = 0;
    }
}
