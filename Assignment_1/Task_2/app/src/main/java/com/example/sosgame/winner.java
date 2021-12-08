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
        int score = data.getInt("score");
        String player = data.getString("winner");
        String winner = "Winner\n"+player+"\nScore:"+score;
        TextView textView = findViewById(R.id.textView4);
        textView.setText(winner);
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
}