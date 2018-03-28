package com.igorternyuk.texasholdem.model.player;

/**
 * Created by igor on 20.03.18.
 */
public enum PlayerRole {
    DEALER("Dealer"),
    SMALL_BLIND("SB"),
    BIG_BLIND("BB"),
    REGULAR("");

    private String text;

    PlayerRole(final String text){
        this.text = text;
    }

    @Override
    public String toString(){
        return this.text;
    }
}
