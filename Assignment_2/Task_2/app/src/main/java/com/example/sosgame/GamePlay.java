package com.example.sosgame;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

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
    TextView textView3;
    TextView textView4;
    SOSGameBoard gameBoard;
    Controller controller;

    Map<String, Object> publications;


    @RequiresApi(api = Build.VERSION_CODES.N)
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
        textView3 = findViewById(R.id.textView8);
        textView4 = findViewById(R.id.textView9);

        textView.setText(player1+" Score: "+playerOne.Score);
        textView1.setText(player2+" Score: "+playerTwo.Score);
        textView2.setText("Turn: "+player1+" Plays");

        Button buttonO = findViewById(R.id.button3);
        Button buttonS = findViewById(R.id.button4);

        gameBoard = findViewById(R.id.SOSGameBoard);
        controller = Controller.getInstance();


        publications = new HashMap<>();

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

        calculator game = new calculator() {

            @Override
            public void onPublished(Publisher publisher, String topic, Map<String, Object> params) {
                MakeTextShow(publisher.getMessage());
            }
        };
        calculator pointScore = new calculator() {
            @Override
            public void onPublished(Publisher publisher, String topic, Map<String, Object> params) {
                if(publisher.getPublisher().equals("1"))
                {
                    MakeTextShow(publisher.getMessage());
                }
                else if(publisher.getPublisher().equals("2"))
                {
                    MakeTextShow(publisher.getMessage());
                }
            }
        };
        calculator playerTurnCount = new calculator() {
            @Override
            public void onPublished(Publisher publisher, String topic, Map<String, Object> params) {
                if(publisher.getPublisher().equals("1"))
                {
                    playerOne.setNumberTurns();
                    //MakeTextShow(publisher.getMessage());
                }
                else if(publisher.getPublisher().equals("2"))
                {
                    playerTwo.setNumberTurns();
                    //MakeTextShow(publisher.getMessage());

                }
            }
        };
        calculator numberOfOs = new calculator() {
            @Override
            public void onPublished(Publisher publisher, String topic, Map<String, Object> params) {
                if(publisher.getPublisher().equals("1"))
                {
                    playerOne.setNumberOS();
                    MakeTextShow(publisher.getMessage());
                }
                else if(publisher.getPublisher().equals("2"))
                {
                    playerTwo.setNumberOS();
                    MakeTextShow(publisher.getMessage());
                }
            }
        };
        calculator numberOfS = new calculator() {
            @Override
            public void onPublished(Publisher publisher, String topic, Map<String, Object> params) {
                if(publisher.getPublisher().equals("1"))
                {
                    playerOne.setNumberS();
                    MakeTextShow(publisher.getMessage());
                }
                else if(publisher.getPublisher().equals("2"))
                {
                    playerTwo.setNumberS();
                    MakeTextShow(publisher.getMessage());
                }
            }
        };
        calculator numberOfSequences = new calculator() {
            @Override
            public void onPublished(Publisher publisher, String topic, Map<String, Object> params) {
                int scorevalue = Integer.valueOf(publisher.getMessage());
                if(publisher.getPublisher().equals("1"))
                {
                    playerOne.setNumberSOS(scorevalue);
                    playerOne.AddPoint(scorevalue);
                }
                else if(publisher.getPublisher().equals("2"))
                {
                    playerTwo.setNumberSOS(scorevalue);
                    playerTwo.AddPoint(scorevalue);
                }
                MakeTextShow(publisher.getMessage());
            }
        };
        calculator MaxSOSs = new calculator() {
            @Override
            public void onPublished(Publisher publisher, String topic, Map<String, Object> params) {
                if(publisher.getPublisher().equals("1"))
                {
                    int max = Integer.valueOf(publisher.message);
                    if(max>playerOne.getMAXnumberSOS()) {
                        playerOne.setMAXnumberSOS(Integer.valueOf(publisher.message));
                        //MakeTextShow(publisher.getMessage());
                    }
                }
                else if(publisher.getPublisher().equals("2"))
                {
                    int max = Integer.valueOf(publisher.message);
                    if(max>playerTwo.getMAXnumberSOS()) {
                        playerTwo.setMAXnumberSOS(Integer.valueOf(publisher.message));
                        //MakeTextShow(publisher.getMessage());
                    }
                }
            }
        };
        calculator statsCalculator = new calculator() {
            @Override
            public void onPublished(Publisher publisher, String topic, Map<String, Object> params) {
                    String text = StringBuilder(playerOne);
                    textView3.setText(text);
                    String text2 = StringBuilder(playerTwo);
                    textView4.setText(text2);
            }
            };

        Score = 0;
        controller.subscribe("Game",game);
        controller.subscribe("Turn",pointScore);
        controller.subscribe("SOS",pointScore);
        controller.subscribe("S",numberOfS);
        controller.subscribe("O",numberOfOs);
        controller.subscribe("SOS",numberOfSequences);
        controller.subscribe("S",statsCalculator);
        controller.subscribe("O",statsCalculator);
        controller.subscribe("Turn",statsCalculator);
        controller.subscribe("Game",statsCalculator);
        controller.subscribe("SOS",statsCalculator);
        controller.subscribe("MaxSOS",statsCalculator);
        controller.subscribe("MaxSOS",MaxSOSs);
        controller.subscribe("Turn",playerTurnCount);
        //controller.subscribe("Turn",MaxSOSs);

        gameBoard.setController(controller);
        controller.publish(new calculator.Publisher("1","New Game Starts"),"Game",null);
        controller.publish( new calculator.Publisher("1",playerOne.getName()+"'s Turn Starts"),"Turn",null);
        String player2w = StringBuilder(playerTwo);
        textView4.setText(player2w);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void ChangePlayerTurn()
    {
        PlayerOneTurn = !PlayerOneTurn;

        if(PlayerOneTurn)
        {
            textView2.setText(""+playerOne.name+" Plays Now");
            textView2.setTextColor(textView.getCurrentTextColor());
            gameBoard.setPlayer(1);
            controller.publish(new calculator.Publisher("1",playerTwo.getName()+"'s Turn Ends\n"+playerOne.getName()+"'s Turn Starts"),"Turn",publications);
            //controller.publish(new calculator.Publisher("1","Player 2 Turn Ends"),"Turn",publications);
        }
        else
        {
            textView2.setText(""+playerTwo.name+" Plays Now");
            textView2.setTextColor(textView1.getCurrentTextColor());
            gameBoard.setPlayer(2);
            controller.publish(new calculator.Publisher("2",playerOne.getName()+"'s Turn Ends\n"+playerTwo.getName()+"'s Turn Starts"),"Turn",publications);
            //controller.publish(new calculator.Publisher("2","Player 1 Turn Ends"),"Turn",publications);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void AddPoint(int score)
    {
        if(PlayerOneTurn)
        {
            controller.publish(new calculator.Publisher("1",String.valueOf(score)),"SOS",null);
            textView.setText(playerOne.name+" Score: "+playerOne.Score);

        }
        else
        {
            controller.publish(new calculator.Publisher("2",String.valueOf(score)),"SOS",null);
            textView1.setText(playerTwo.name+" Score: "+playerTwo.Score);
            //controller.publish(playerOne,"SOS",publications);
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
        intent.putExtra("player1",playerOne);
        intent.putExtra("player2",playerTwo);
        startActivity(intent);
    }
    public String getPlayer()
    {
        if(PlayerOneTurn)
            return "1";
        else
            return "2";
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onChange(int b) {

        if(numberOfpLAys<gameBoard.getPlays())
        {
            numberOfpLAys = numberOfpLAys+1;
            if(gameBoard.getScore()>0)
            {
                /**
                 * We Add Turn to current player
                 * We update the number of S OR O
                 */
                String publisher = getPlayer();
                String name = GetPlayerName();
                //controller.publish( new calculator.Publisher(publisher,name+ "Completed SOS"+""),"SOS",null);
                MakeTextShow(name+" completed SOS");
                controller.publish( new calculator.Publisher(publisher,String.valueOf(gameBoard.getScore())),"MaxSOS",null);
                controller.publish(new calculator.Publisher(publisher, name+" plays Again"),"Turn",publications);
                Score = Score + gameBoard.getScore();
                AddPoint(gameBoard.getScore());
                gameBoard.SetScore(0);
            }
            else
            {
                String publisher = getPlayer();
                //controller.publish( new calculator.Publisher(publisher,Score+""),"MaxSOS",null);
                Score = 0;
                ChangePlayerTurn();
            }
        }
        if(numberOfpLAys==25)
        {
            complete = true;
            controller.publish(new calculator.Publisher("1","Game Has Ended"),"Game",null);
            getWinner();
        }
    }

    public void MakeTextShow(String message)
    {

        if(message.length()>1)
        {
            Context context = getApplicationContext();
            Toast toast = new Toast(getApplicationContext());
            int duration = Toast.LENGTH_SHORT;
            //textView3.setText(message);
            if (message.equals("Game Has Ended")) {
                toast.cancel();
            }
            toast = Toast.makeText(context, message, duration);
            toast.show();
        }
        //textView3.setText("");
        //textView3.invalidate();
    }
    public String GetPlayerName()
    {
        if(PlayerOneTurn)
            return playerOne.getName();
        return playerTwo.getName();
    }
    public String StringBuilder(Player player)
    {
        String data = "O's clicked: "+player.getNumberOS()+"\nS's clicked: "+player.getNumberS()+"\nHighest SOS's: "+player.getMAXnumberSOS()+"\nTotal Turns: "+player.getNumberTurns()+"\nNumber of SOS:"+player.getScore();
        return data;
    }

}