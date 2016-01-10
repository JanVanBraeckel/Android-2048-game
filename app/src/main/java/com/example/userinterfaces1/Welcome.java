package com.example.userinterfaces1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnContinue)
    public void onContinueClicked(){
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("continue", true);
        startActivity(intent);
    }

    @OnClick(R.id.btnAbout)
    public void onAboutClick(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Welcome.this);
        alertDialogBuilder.setTitle(R.string.aboutTitle);
        alertDialogBuilder.setMessage(R.string.aboutContent);
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    @OnClick(R.id.btnExit)
    public void onExitClick(){
        finish();
    }

    @OnClick(R.id.btnNewGame)
    public void onNewGameClick(View v){
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("continue", false);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }
}
