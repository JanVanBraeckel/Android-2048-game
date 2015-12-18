package com.example.userinterfaces1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayActivity extends Activity {

    @Bind(R.id.board)
    Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        board.setOnTouchListener(new SwipeListener(getApplicationContext()){
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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("board", board);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        board = (Board) savedInstanceState.getSerializable("board");
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
}
