package com.example.userinterfaces1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameDone extends Activity {

    @Bind(R.id.finalScore)
    TextView finalScore;
    @Bind(R.id.gameOver)
    TextView gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_done);
        ButterKnife.bind(this);
        if(getIntent().getExtras().getBoolean("win")){
            gameOver.setText("Congratulations, you won!");
        }else{
            gameOver.setText("Game over, no more moves!");
        }
        finalScore.setText("Your score: " + getIntent().getExtras().getInt("score"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_done, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent =new Intent(this, Welcome.class);
        finish();
        startActivity(intent);
    }

    @OnClick(R.id.menuButton)
    public void onReturnToMenuPressed(){
        Intent intent =new Intent(this, Welcome.class);
        startActivity(intent);
    }
}
