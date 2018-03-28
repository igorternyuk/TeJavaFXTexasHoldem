package com.igorternyuk.texasholdem.model;

import java.util.Objects;

/**
 * Created by igor on 18.03.18.
 */
public class Card {
    public enum Suit{
        CLUBS("C"), HEARTS("H"), SPADES("S"), DIAMONDS("D");

        private String text;

        Suit(final String text){
            this.text = text;
        }

        @Override
        public String toString(){
            return this.text;
        }
    }

    public enum Rank{
        TWO(2, false), THREE(3, false), FOUR(4, false), FIVE(5, false), SIX(6, false), SEVEN(7, false),
        EIGHT(8, false), NINE(9, false), TEN(10, false), JACK(11, true), QUEEN(12, true), KING(13, true),
        ACE(14, true);
        private int value;
        private boolean figure;

        Rank(final int value, final boolean isFigure){
            this.value = value;
            this.figure = isFigure;
        }

        public int getValue() {
            return this.value;
        }

        public boolean isFigure() {
            return this.figure;
        }
    }

    public static final int LOW_ACE_VALUE = 1;
    private Suit suit;
    private Rank rank;
    private boolean faceUp = false;

    public static Rank getRankByValue(final int value) throws IllegalArgumentException {
        if(value == LOW_ACE_VALUE) return Rank.ACE;
        for(final Rank rank: Rank.values()){
            if(rank.value == value){
                return rank;
            }
        }
        throw new IllegalArgumentException("Invalid card value");
    }

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public Rank getRank() {
        return this.rank;
    }

    public int getValue(){
        return this.rank.getValue();
    }

    public boolean isFigure(){
        return this.rank.isFigure();
    }

    public boolean isFaceUp(){
        return this.faceUp;
    }

    public void flip(){
        this.faceUp = !this.faceUp;
    }

    public boolean isSuit(final Suit suit){
        return this.suit.equals(suit);
    }

    public boolean isRank(final Rank rank){
        return this.rank.equals(rank);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (!(other instanceof Card)) return false;
        final Card otherCard = (Card) other;
        return Objects.equals(this.suit, otherCard.getSuit())
                && Objects.equals(this.rank, otherCard.getRank())
                && faceUp == otherCard.faceUp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = getSuit().hashCode();
        result = prime * result + getRank().hashCode();
        result = prime * result + (this.faceUp ? 1 : 0);
        return result;
    }

    @Override
    public String toString(){
        if(this.rank.isFigure()){
            switch (this.rank){
                case JACK:
                    return "J" + this.suit.toString();
                case QUEEN:
                    return "Q" + this.suit.toString();
                case KING:
                    return "K" + this.suit.toString();
                default:
                    return "A" + this.suit.toString();
            }
        } else {
            return String.valueOf(this.getValue()) + this.suit.toString();
        }
    }
}
