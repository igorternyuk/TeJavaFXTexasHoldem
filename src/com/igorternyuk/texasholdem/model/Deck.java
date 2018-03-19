package com.igorternyuk.texasholdem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

import static com.igorternyuk.texasholdem.model.Card.Suit;
import static com.igorternyuk.texasholdem.model.Card.Rank;
/**
 * Created by igor on 18.03.18.
 */
public class Deck {
    private static final int MAX_NUMBER_OF_CARDS = 52;
    private List<Card> cards = new ArrayList<>(MAX_NUMBER_OF_CARDS);

    public Deck(){
        populate();
        shuffle();
    }

    public List<Card> getCards(){
        return Collections.unmodifiableList(this.cards);
    }

    public void populate(){
        this.cards.clear();
        for (final Suit suit : Suit.values()) {
            for (final Rank rank : Rank.values()) {
                this.cards.add(new Card(suit, rank));
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(this.cards, new Random());
    }

    public void clear(){
        this.cards.clear();
    }

    public int cardsRemains(){
        return this.cards.size();
    }

    public void deal(final Player player){
        if(!this.cards.isEmpty()){
            player.addCard(top());
        } else {
            System.out.println("There are no cards in the deck");
        }
    }

    private Card top(){
        final Card topCard = this.cards.get(this.cards.size() - 1);
        this.cards.remove(topCard);
        return topCard;
    }

}
