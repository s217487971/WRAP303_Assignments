package com.example.sosgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GamePlay extends AppCompatActivity implements ChangeListener.listener{

    private ChangeListener listener;
    Player playerOne;
    Player playerTwo;
    String winner;
    int Score;
    boolean PlayerOneTurn = true;
    boolean complete = false;
    int numberOfpLAys = 0;


    public int Clicked = 0;

    TextView textView ;
    TextView textView1 ;
    TextView textView2 ;
    SOSGameBoard gameBoard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        Bundle bundle = getIntent().getExtras();
            String player1 = bundle.getString("player1");
            String player2 = bundle.getString("player2");

            playerOne = new Player(player1);
            playerTwo = new Player(player2);

        textView = findViewById(R.id.textView5);
        textView1 = findViewById(R.id.textView6);
        textView2 = findViewById(R.id.textView7);

        textView.setText(player1+" Score: "+playerOne.Score);
        textView1.setText(player2+" Score: "+playerTwo.Score);
        textView2.setText("Turn: "+player1+" Plays");

        Button buttonO = findViewById(R.id.button3);
        Button buttonS = findViewById(R.id.button4);

        gameBoard = findViewById(R.id.SOSGameBoard);


        listener = new ChangeListener(gameBoard.getPlays());
        listener.setChangeListener(this);

        gameBoard.setListener(listener);
        gameBoard.setPlayer(1);

        buttonO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gameBoard.setClickedSymbol(2);
            }
        });
        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gameBoard.setClickedSymbol(1);
            }
        });

    }

    public void ChangePlayerTurn()
    {
        PlayerOneTurn = !PlayerOneTurn;

        if(PlayerOneTurn)
        {
            textView2.setText(""+playerOne.name+"Plays Now");
            textView2.setTextColor(textView.getCurrentTextColor());
            gameBoard.setPlayer(1);
        }
        else
        {
            textView2.setText(""+playerTwo.name+"Plays Now");
            textView2.setTextColor(textView1.getCurrentTextColor());
            gameBoard.setPlayer(2);
        }
    }

    public void AddPoint(int score)
    {
        if(PlayerOneTurn)
        {
            playerOne.AddPoint(score);
            textView.setText(playerOne.name+" Score: "+playerOne.Score);
        }
        else
        {
            playerTwo.AddPoint(score);
            textView1.setText(playerTwo.name+" Score: "+playerTwo.Score);
        }
    }
    public void getWinner()
    {
        if(playerOne.Score>playerTwo.Score)
        {
            winner = playerOne.name;
            Score = playerOne.Score;
        }
        else if(playerTwo.Score> playerOne.Score)
        {
            winner = playerTwo.name;
            Score = playerTwo.Score;
        }
        else if(playerOne.Score==playerTwo.Score)
        {
            winner = "Draw";
            Score = playerTwo.Score;
        }

        Intent intent = new Intent(this, winner.class);
        intent.putExtra("winner",winner);
        intent.putExtra("score",Score);
        startActivity(intent);
    }

    @Override
    public void onChange(int b) {

        if(numberOfpLAys<gameBoard.getPlays())
        {
            numberOfpLAys = numberOfpLAys+1;
            if(gameBoard.getScore()>0)
            {
                AddPoint(gameBoard.getScore());
                gameBoard.SetScore(0);
            }
            else
            {
                ChangePlayerTurn();
            }
        }
        if(numberOfpLAys==25)
        {
            complete = true;
            getWinner();
        }
    }
}