package com.igorternyuk.texasholdem.model.player;

/**
 * Created by igor on 21.03.18.
 */
public enum PlayerStatus {
    IN_PLAY(""),
    WON("WON"),
    TIE("TIED"),
    LOST("LOST"),
    FOLDED("FOLDED");

    private String text;

    PlayerStatus(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
