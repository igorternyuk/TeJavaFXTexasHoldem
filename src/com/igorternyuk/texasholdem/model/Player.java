package com.igorternyuk.texasholdem.model;

/**
 * Created by igor on 18.03.18.
 */
public class Player {
    private Hand hand = new Hand();
    public void addCard(final Card card){
        this.hand.addCard(card);
    }
}
