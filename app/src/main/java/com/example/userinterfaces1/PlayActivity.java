package com.example.userinterfaces1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayActivity extends Activity {

    @Bind(R.id.gameBoard)
    LinearLayout container;
    private Board board;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        int[][] cardBoard = null;
        int score = 0;
        gson = new Gson();

        Bundle extra = getIntent().getExtras();
        if(extra != null && extra.containsKey("continue")){
            if(extra.getBoolean("continue")){
                SharedPreferences sharedPreferences = getSharedPreferences("2048", MODE_PRIVATE);
                String jsonBoard = sharedPreferences.getString("board", null);
                if(jsonBoard != null){
                    cardBoard = gson.fromJson(jsonBoard, int[][].class);
                }
                score = sharedPreferences.getInt("score", 0);
            }
        }

        if (savedInstanceState != null && savedInstanceState.containsKey("boad")) {
            Board board = (Board) savedInstanceState.getSerializable("board");
            assert board != null;
            cardBoard = board.getCardBoard();
            score = board.getScore();
        }

        initBoard(cardBoard, score);
        container.addView(board);
    }

    private void initBoard(int[][] cardBoard, int score) {

        board = new Board(this, cardBoard, score);

        board.setOnTouchListener(new SwipeListener(getApplicationContext()) {
            @Override
            public void onSwipeBottom() {
                board.moveDown();
            }

            @Override
            public void onSwipeLeft() {
                board.moveLeft();
            }

            @Override
            public void onSwipeRight() {
                board.moveRight();
            }

            @Override
            public void onSwipeTop() {
                board.moveUp();
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        board.setLayoutParams(params);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("board", board);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.restart) {
            board.resetBoard();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        SharedPreferences.Editor sharedPreferences = getSharedPreferences("2048", MODE_PRIVATE).edit();
        sharedPreferences.putString("board", gson.toJson(board.getCardBoard()));
        sharedPreferences.putInt("score", board.getScore());
        sharedPreferences.apply();
        super.onDestroy();
    }
}
