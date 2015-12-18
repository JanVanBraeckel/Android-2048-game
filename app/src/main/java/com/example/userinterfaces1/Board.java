package com.example.userinterfaces1;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Gebruiker on 19/10/2015.
 */
public class Board extends GridLayout implements Serializable {
    private Card[][] cardBoard;
    private boolean hasMoved = false;
    private transient TextView txtScore;
    private int score = 0;

    public Board(Context context) {
        this(context, null);
    }

    public Board(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Board(Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs, defaultStyle);
        initBoard();
    }

    private void initBoard() {
        setColumnCount(4);
        setRowCount(5);
        cardBoard = new Card[4][4];
        setBackgroundColor(0xffbbada0);
        txtScore = new TextView(getContext());
        txtScore.setText("Score: " + score);
        LayoutParams params = new GridLayout.LayoutParams();
        params.columnSpec = spec(0, 4);
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        addView(txtScore, params);
    }

    private void addCards(int cardWidth, int cardHeight) {
        Card c;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                c = new Card(getContext());
                c.setNumber(0);
                cardBoard[i][j] = c;
                addView(c, cardWidth, cardHeight);
            }
        }
    }

    public void resetBoard() {
        for (Card[] cardRow : cardBoard) {
            for (Card card : cardRow) {
                card.setNumber(0);
            }
        }
        score = 0;
        updateScore();
        startGame();
    }

    private void updateScore() {
        txtScore.setText("Score: " + score);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w, h) - 10) / 4;
        addCards(cardWidth, cardWidth);
        cardWidth = w < h ? (w - 20) / 4 : (h - 20) / 4;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cardBoard[i][j].setMinimumWidth(cardWidth - 10);
                cardBoard[i][j].setMinimumHeight(cardWidth - 10);
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams(GridLayout.spec(i + 1), GridLayout.spec(j));
                lp.setMargins(5, 5, 5, 5);
                cardBoard[i][j].setLayoutParams(lp);
            }
        }
        startGame();
    }

    public void startGame() {
        //startLoseGame();
        startmaybebuggame();
        /*int value = Math.random() > 0.7 ? 4 : 2;
        int value2 = Math.random() > 0.7 ? 4 : 2;
        if (value == 4)
            value2 = 2;

        getRandomCard().setNumber(value);

        Card card = getRandomCard();
        while(card.getNumber() != 0){
            card = getRandomCard();
        }
        card.setNumber(value2);*/
    }

    private void startmaybebuggame() {
        cardBoard[3][0].setNumber(2);
        cardBoard[2][0].setNumber(2);
        cardBoard[1][0].setNumber(4);
    }

    private void startLoseGame() {
        int value = 1;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                if (!(row == 0 && column == 0)) {
                    if (value == 2 || value == 4)
                        value++;
                    cardBoard[row][column].setNumber(value);
                    value++;
                }
            }
        }
    }

    private Card getRandomCard() {
        int x = (int) (Math.random() * 4);
        int y = (int) (Math.random() * 4);
        return cardBoard[x][y];
    }

    public void moveUp() {
        int scoreGain = 0;
        boolean originalMerged;
        for (int column = 0; column < 4; column++) {
            for (int xUp = 0; xUp < 4; xUp++) {
                originalMerged = false;
                for (int xDelta = xUp + 1; xDelta < 4; xDelta++) {
                    Card original = cardBoard[xUp][column];
                    Card delta = cardBoard[xDelta][column];
                    if (original.getNumber() != 0 && delta.getNumber() != 0) {
                        if (original.getNumber() == delta.getNumber() && !originalMerged) {
                            originalMerged = true;
                            scoreGain = original.getNumber() * 2;
                            original.setNumber(scoreGain);
                            delta.setNumber(0);
                            hasMoved = true;
                        }
                    }
                    if (original.getNumber() == 0 && delta.getNumber() != 0) {
                        original.setNumber(delta.getNumber());
                        delta.setNumber(0);
                        hasMoved = true;
                    }
                    if (delta.getNumber() != 0 && (original.getNumber() != delta.getNumber())) {
                        break;
                    }
                }
            }
        }
        score += scoreGain;
        updateScore();
        afterMovement();
    }

    public void moveDown() {
        int scoreGain = 0;
        boolean originalMerged;
        for (int column = 0; column < 4; column++) {
            for (int xDown = 3; xDown >= 0; xDown--) {
                originalMerged = false;
                for (int xDelta = xDown - 1; xDelta >= 0; xDelta--) {
                    Card original = cardBoard[xDown][column];
                    Card delta = cardBoard[xDelta][column];
                    if (original.getNumber() != 0 && delta.getNumber() != 0) {
                        if (original.getNumber() == delta.getNumber() && !originalMerged) {
                            originalMerged = true;
                            scoreGain = original.getNumber() * 2;
                            original.setNumber(scoreGain);
                            delta.setNumber(0);
                            hasMoved = true;
                        }
                    }
                    if (original.getNumber() == 0 && delta.getNumber() != 0) {
                        original.setNumber(delta.getNumber());
                        delta.setNumber(0);
                        hasMoved = true;
                    }
                    if (delta.getNumber() != 0 && (original.getNumber() != delta.getNumber())) {
                        break;
                    }
                }
            }
        }
        score += scoreGain;
        updateScore();
        afterMovement();
    }

    public void moveLeft() {
        int scoreGain = 0;
        boolean originalMerged;
        for (int row = 0; row < 4; row++) {
            for (int xLeft = 0; xLeft < 4; xLeft++) {
                originalMerged = false;
                for (int xDelta = xLeft + 1; xDelta < 4; xDelta++) {
                    Card original = cardBoard[row][xLeft];
                    Card delta = cardBoard[row][xDelta];
                    if (original.getNumber() != 0 && delta.getNumber() != 0) {
                        if (original.getNumber() == delta.getNumber() && !originalMerged) {
                            originalMerged = true;
                            scoreGain = original.getNumber() * 2;
                            original.setNumber(scoreGain);
                            delta.setNumber(0);
                            hasMoved = true;
                        }
                    }
                    if (original.getNumber() == 0 && delta.getNumber() != 0) {
                        original.setNumber(delta.getNumber());
                        delta.setNumber(0);
                        hasMoved = true;
                    }
                    if (delta.getNumber() != 0 && (original.getNumber() != delta.getNumber())) {
                        break;
                    }
                }
            }
        }
        score += scoreGain;
        updateScore();
        afterMovement();
    }

    public void moveRight() {
        int scoreGain = 0;
        boolean originalMerged;
        for (int row = 0; row < 4; row++) {
            for (int xRight = 3; xRight >= 0; xRight--) {
                originalMerged = false;
                for (int xDelta = xRight - 1; xDelta >= 0; xDelta--) {
                    Card original = cardBoard[row][xRight];
                    Card delta = cardBoard[row][xDelta];
                    if (original.getNumber() != 0 && delta.getNumber() != 0) {
                        if (original.getNumber() == delta.getNumber() && !originalMerged) {
                            originalMerged = true;
                            scoreGain = original.getNumber() * 2;
                            original.setNumber(scoreGain);
                            delta.setNumber(0);
                            hasMoved = true;
                        }
                    }
                    if (original.getNumber() == 0 && delta.getNumber() != 0) {
                        original.setNumber(delta.getNumber());
                        delta.setNumber(0);
                        hasMoved = true;
                    }
                    if (delta.getNumber() != 0 && (original.getNumber() != delta.getNumber())) {
                        break;
                    }
                }
            }
        }
        score += scoreGain;
        updateScore();
        afterMovement();
    }

    public void afterMovement() {
        if (hasMoved) {
            hasMoved = false;
            List<Card> availableCells = new ArrayList<>();
            for (Card[] cardRow : cardBoard) {
                for (Card card : cardRow) {
                    if (card.getNumber() == 0) {
                        availableCells.add(card);
                    }
                    if (card.getNumber() == 2048) {
                        Intent intent = new Intent(getContext(), GameDone.class);
                        intent.putExtra("win", true);
                        intent.putExtra("score", score);
                        getContext().startActivity(intent);
                    }
                }
            }
            int index = new Random().nextInt(availableCells.size());
            int number = Math.random() > 0.7 ? 4 : 2;
            availableCells.get(index).setNumber(number);

            if (availableCells.size() == 1) {
                if (!movesStillPossible()) {
                    Intent intent = new Intent(getContext(), GameDone.class);
                    intent.putExtra("win", false);
                    intent.putExtra("score", score);
                    getContext().startActivity(intent);
                }
            }
        }
    }

    private boolean movesStillPossible() {
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                Card currentPosition = cardBoard[row][column];
                boolean leftPossible = (row != 0) && cardBoard[row - 1][column].getNumber() == currentPosition.getNumber();
                boolean rightPossible = (row != 3) && cardBoard[row + 1][column].getNumber() == currentPosition.getNumber();
                boolean topPossible = (column != 0) && cardBoard[row][column - 1].getNumber() == currentPosition.getNumber();
                boolean bottomPossible = (column != 3) && cardBoard[row][column + 1].getNumber() == currentPosition.getNumber();
                if (leftPossible || rightPossible || topPossible || bottomPossible) {
                    return true;
                }
            }
        }
        return false;
    }
}
