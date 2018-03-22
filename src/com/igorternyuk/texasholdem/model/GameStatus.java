package com.igorternyuk.texasholdem.model;

/**
 * Created by igor on 20.03.18.
 */
public enum GameStatus {
    PREFLOP("Preflop"),
    FLOP("Flop"),
    TURN("Turn"),
    RIVER("River"),
    SHOWDOWN("Showdown");
    private String text;

    GameStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString(){
        return this.text;
    }
}
