package com.example.userinterfaces1;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.Serializable;

public class Card extends FrameLayout implements Serializable{

    private transient TextView txtNumber;
    private int number;

    public Card(Context context) {
        super(context);
        txtNumber = new TextView(getContext());
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10,10,0,0);
        setBackgroundColor(Color.LTGRAY);
        txtNumber.setGravity(Gravity.CENTER);
        addView(txtNumber, lp);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        if(number <= 0){
            txtNumber.setText("");
        } else{
            txtNumber.setText(String.valueOf(number));
        }
    }
}
