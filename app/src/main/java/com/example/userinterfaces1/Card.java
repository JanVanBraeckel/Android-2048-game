package com.example.userinterfaces1;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

public class Card extends FrameLayout implements Serializable{

    private transient TextView txtNumber;
    private int number = 0;

    public Card(Context context) {
        super(context);
        txtNumber = new TextView(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        txtNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        addView(txtNumber, params);
        updateColor();
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
        updateColor();
    }

    private void updateColor(){
        int id = getResources().getIdentifier("number" + number, "color", getContext().getPackageName());
        try{
            setBackgroundColor(getContext().getResources().getColor(id));
        }catch (Resources.NotFoundException e){
            setBackgroundColor(getContext().getResources().getColor(R.color.number2048));
        }
    }
}
