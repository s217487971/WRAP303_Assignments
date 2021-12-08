package com.example.sosgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class winner extends AppCompatActivity implements View.OnClickListener {

    String player1;
    String player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        Bundle data = getIntent().getExtras();
        Player player01 = data.getParcelable("player1");
        Player player02 = data.getParcelable("player2");
        String stat01 = StringBuilder(player01);
        String stat02 = StringBuilder(player02);

        int score = data.getInt("score");
        String player = data.getString("winner");
        String winner = "Winner\n"+player+"\nScore:"+score;
        TextView textView = findViewById(R.id.textView4);
        TextView textView1 = findViewById(R.id.textView12);
        TextView textView2 = findViewById(R.id.textView13);
        textView.setText(winner);
        textView1.setText(stat01);
        textView2.setText(stat02);

        /**View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
            }
        };*/
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra("player1",player1);
        intent.putExtra("player2",player2);
        startActivity(intent);
    }
    public String StringBuilder(Player player)
    {
        String data = "Name: "+player.getName()+"\nScore: "+player.getScore()+"\nO's clicked: "+player.getNumberOS()+"\nS's clicked: "+player.getNumberS()+"\nHighest SOS's: "+player.getMAXnumberSOS()+"\nTotal Turns: "+player.getNumberTurns()+"\nNumber of SOS:"+player.getScore();
        return data;
    }
}