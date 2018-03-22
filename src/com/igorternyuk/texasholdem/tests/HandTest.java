package com.igorternyuk.texasholdem.tests;

import com.igorternyuk.texasholdem.model.Card;
import com.igorternyuk.texasholdem.model.Hand;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.igorternyuk.texasholdem.model.Card.Rank;
import static com.igorternyuk.texasholdem.model.Card.Suit;
import static com.igorternyuk.texasholdem.model.Hand.Combination;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by igor on 19.03.18.
 */
public class HandTest {
    @Test
    public void testOfRoyalFlush(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.CLUBS, Rank.JACK));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.SIX));
        communityCards.add(new Card(Suit.CLUBS, Rank.QUEEN));
        communityCards.add(new Card(Suit.SPADES, Rank.SEVEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.KING));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.CLUBS, Rank.ACE));
        hand.addCard(new Card(Suit.CLUBS, Rank.TEN));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(2, hand.getHoleCards().size());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
        assertEquals(Combination.ROYAL_FLESH, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
    }

    @Test
    public void testOfStraightFlushWithLowAce(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.TWO));
        communityCards.add(new Card(Suit.SPADES, Rank.SEVEN));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.FOUR));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.FIVE));
        communityCards.add(new Card(Suit.CLUBS, Rank.KING));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.DIAMONDS, Rank.ACE));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.THREE));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(Combination.STRAIGHT_FLUSH_WITH_LOW_ACE, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
    }

    @Test
    public void testOfStraightFlushWithLowAce2(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.HEARTS, Rank.TWO));
        communityCards.add(new Card(Suit.HEARTS, Rank.THREE));
        communityCards.add(new Card(Suit.HEARTS, Rank.FOUR));
        communityCards.add(new Card(Suit.HEARTS, Rank.FIVE));
        communityCards.add(new Card(Suit.HEARTS, Rank.TEN));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.KING));
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(Combination.STRAIGHT_FLUSH_WITH_LOW_ACE, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
    }

    @Test
    public void testOfStraightFlush(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.SPADES, Rank.TWO));
        communityCards.add(new Card(Suit.SPADES, Rank.THREE));
        communityCards.add(new Card(Suit.SPADES, Rank.FOUR));
        communityCards.add(new Card(Suit.SPADES, Rank.FIVE));
        communityCards.add(new Card(Suit.SPADES, Rank.SIX));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.SEVEN));
        hand.addCard(new Card(Suit.SPADES, Rank.ACE));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(Combination.STRAIGHT_FLUSH, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
    }

    @Test
    public void testOfStraightWithLowAce(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.SPADES, Rank.TWO));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.THREE));
        communityCards.add(new Card(Suit.HEARTS, Rank.FOUR));
        communityCards.add(new Card(Suit.CLUBS, Rank.FIVE));
        communityCards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.SEVEN));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.ACE));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(Combination.STRAIGHT_WITH_LOW_ACE, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
    }

    @Test
    public void testOfStraight(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.SEVEN));
        communityCards.add(new Card(Suit.SPADES, Rank.NINE));
        communityCards.add(new Card(Suit.HEARTS, Rank.JACK));
        communityCards.add(new Card(Suit.CLUBS, Rank.ACE));
        communityCards.add(new Card(Suit.CLUBS, Rank.TEN));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.EIGHT));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.TWO));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(Combination.STRAIGHT, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
    }

    @Test
    public void testOfNotStraight2(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.SPADES, Rank.FIVE));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.SEVEN));
        communityCards.add(new Card(Suit.SPADES, Rank.NINE));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.ACE));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.DIAMONDS, Rank.KING));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.THREE));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertNotEquals(Combination.STRAIGHT, hand.getCombination());
        assertEquals(Combination.HIGH_CARD, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
    }

    @Test
    public void testOfNotStraight3(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.SPADES, Rank.FIVE));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.SEVEN));
        communityCards.add(new Card(Suit.SPADES, Rank.NINE));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.ACE));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.EIGHT));
        hand.addCard(new Card(Suit.CLUBS, Rank.QUEEN));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertNotEquals(Combination.STRAIGHT, hand.getCombination());
        assertEquals(Combination.HIGH_CARD, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
    }

    @Test
    public void testOfNotStraight4(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.SPADES, Rank.FIVE));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.SEVEN));
        communityCards.add(new Card(Suit.SPADES, Rank.NINE));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.ACE));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.JACK));
        hand.addCard(new Card(Suit.CLUBS, Rank.KING));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertNotEquals(Combination.STRAIGHT, hand.getCombination());
        assertEquals(Combination.HIGH_CARD, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
    }

    @Test
    public void testOfNoStraight(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.SIX));
        communityCards.add(new Card(Suit.SPADES, Rank.EIGHT));
        communityCards.add(new Card(Suit.HEARTS, Rank.JACK));
        communityCards.add(new Card(Suit.CLUBS, Rank.QUEEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.KING));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.TWO));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertNotEquals(Combination.STRAIGHT, hand.getCombination());
        assertEquals(Combination.HIGH_CARD, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
    }

    @Test
    public void testOfQuads(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        communityCards.add(new Card(Suit.SPADES, Rank.NINE));
        communityCards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.ACE));
        communityCards.add(new Card(Suit.CLUBS, Rank.TEN));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.QUEEN));
        hand.addCard(new Card(Suit.CLUBS, Rank.QUEEN));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(Combination.FOUR_OF_A_KIND, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
        System.out.println(hand);
    }

    @Test
    public void testOfFullHouseFromTwoTriples(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        communityCards.add(new Card(Suit.SPADES, Rank.QUEEN));
        communityCards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.KING));
        communityCards.add(new Card(Suit.CLUBS, Rank.TEN));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.DIAMONDS, Rank.KING));
        hand.addCard(new Card(Suit.HEARTS, Rank.KING));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(Combination.FULL_HOUSE, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
        assertEquals(Rank.KING, hand.getFinalFiveCombinationCards().get(0).getRank());
        assertEquals(Rank.QUEEN, hand.getFinalFiveCombinationCards().get(3).getRank());
        System.out.println(hand);
    }

    @Test
    public void testOfFullHouseFromOneTripleAndTwoPairs(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        communityCards.add(new Card(Suit.SPADES, Rank.QUEEN));
        communityCards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.KING));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.KING));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.DIAMONDS, Rank.JACK));
        hand.addCard(new Card(Suit.HEARTS, Rank.JACK));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(Combination.FULL_HOUSE, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
        assertEquals(Rank.QUEEN, hand.getFinalFiveCombinationCards().get(0).getRank());
        assertEquals(Rank.KING, hand.getFinalFiveCombinationCards().get(3).getRank());
        System.out.println(hand);
    }

    @Test
    public void testOfFullHouseFromOneTripleAndOnePair(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.THREE));
        communityCards.add(new Card(Suit.SPADES, Rank.THREE));
        communityCards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.JACK));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.KING));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.DIAMONDS, Rank.JACK));
        hand.addCard(new Card(Suit.HEARTS, Rank.THREE));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(Combination.FULL_HOUSE, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
        assertEquals(Rank.THREE, hand.getFinalFiveCombinationCards().get(0).getRank());
        assertEquals(Rank.JACK, hand.getFinalFiveCombinationCards().get(3).getRank());
        System.out.println(hand);
    }

    @Test
    public void testOfFlush(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.TWO));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.FOUR));
        communityCards.add(new Card(Suit.HEARTS, Rank.SEVEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.FIVE));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.KING));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.DIAMONDS, Rank.NINE));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.JACK));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(Combination.FLUSH, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
        assertEquals(Rank.KING, hand.getFinalFiveCombinationCards().get(0).getRank());
        assertEquals(Rank.JACK, hand.getFinalFiveCombinationCards().get(1).getRank());
        assertEquals(Rank.NINE, hand.getFinalFiveCombinationCards().get(2).getRank());
        assertEquals(Rank.FOUR, hand.getFinalFiveCombinationCards().get(3).getRank());
        assertEquals(Rank.TWO, hand.getFinalFiveCombinationCards().get(4).getRank());
        System.out.println(hand);
    }

    @Test
    public void testOfTriple(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.FOUR));
        communityCards.add(new Card(Suit.SPADES, Rank.FOUR));
        communityCards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.JACK));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.KING));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.DIAMONDS, Rank.THREE));
        hand.addCard(new Card(Suit.HEARTS, Rank.FOUR));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(Combination.THREE_OF_A_KIND, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
        assertEquals(Rank.FOUR, hand.getFinalFiveCombinationCards().get(0).getRank());
        assertEquals(Rank.KING, hand.getFinalFiveCombinationCards().get(3).getRank());
        assertEquals(Rank.QUEEN, hand.getFinalFiveCombinationCards().get(4).getRank());
        System.out.println(hand);
    }

    @Test
    public void testOfSet(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.TWO));
        communityCards.add(new Card(Suit.SPADES, Rank.FOUR));
        communityCards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.EIGHT));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.KING));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));
        hand.addCard(new Card(Suit.HEARTS, Rank.EIGHT));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(Combination.THREE_OF_A_KIND, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
        assertEquals(Rank.EIGHT, hand.getFinalFiveCombinationCards().get(0).getRank());
        assertEquals(Rank.KING, hand.getFinalFiveCombinationCards().get(3).getRank());
        assertEquals(Rank.QUEEN, hand.getFinalFiveCombinationCards().get(4).getRank());
        System.out.println(hand);
    }

    @Test
    public void testOfTwoPairs(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.TWO));
        communityCards.add(new Card(Suit.SPADES, Rank.FOUR));
        communityCards.add(new Card(Suit.HEARTS, Rank.FOUR));
        communityCards.add(new Card(Suit.CLUBS, Rank.SEVEN));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.KING));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));
        hand.addCard(new Card(Suit.HEARTS, Rank.EIGHT));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(Combination.TWO_PAIRS, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
        assertEquals(Rank.EIGHT, hand.getFinalFiveCombinationCards().get(0).getRank());
        assertEquals(Rank.FOUR, hand.getFinalFiveCombinationCards().get(2).getRank());
        //Kicker
        assertEquals(Rank.KING, hand.getFinalFiveCombinationCards().get(4).getRank());
        System.out.println(hand);
    }

    @Test
    public void testOfPair(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.TWO));
        communityCards.add(new Card(Suit.SPADES, Rank.NINE));
        communityCards.add(new Card(Suit.HEARTS, Rank.JACK));
        communityCards.add(new Card(Suit.CLUBS, Rank.SEVEN));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.KING));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.DIAMONDS, Rank.NINE));
        hand.addCard(new Card(Suit.HEARTS, Rank.TEN));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(Combination.PAIR, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
        assertEquals(Rank.NINE, hand.getFinalFiveCombinationCards().get(0).getRank());
        assertEquals(Rank.NINE, hand.getFinalFiveCombinationCards().get(1).getRank());
        //Kickers
        assertEquals(Rank.KING, hand.getFinalFiveCombinationCards().get(2).getRank());
        assertEquals(Rank.JACK, hand.getFinalFiveCombinationCards().get(3).getRank());
        assertEquals(Rank.TEN, hand.getFinalFiveCombinationCards().get(4).getRank());
        System.out.println(hand);
    }

    @Test
    public void testOfHighCard(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.THREE));
        communityCards.add(new Card(Suit.SPADES, Rank.SIX));
        communityCards.add(new Card(Suit.HEARTS, Rank.SEVEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.JACK));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.KING));
        final Hand hand = new Hand();
        hand.addCard(new Card(Suit.DIAMONDS, Rank.NINE));
        hand.addCard(new Card(Suit.HEARTS, Rank.TEN));
        for(final Card card: communityCards){
            hand.addCard(card);
        }
        assertEquals(Combination.HIGH_CARD, hand.getCombination());
        assertEquals(5, hand.getFinalFiveCombinationCards().size());
        assertEquals(Rank.KING, hand.getFinalFiveCombinationCards().get(0).getRank());
        assertEquals(Rank.JACK, hand.getFinalFiveCombinationCards().get(1).getRank());
        assertEquals(Rank.TEN, hand.getFinalFiveCombinationCards().get(2).getRank());
        assertEquals(Rank.NINE, hand.getFinalFiveCombinationCards().get(3).getRank());
        assertEquals(Rank.SEVEN, hand.getFinalFiveCombinationCards().get(4).getRank());
        System.out.println(hand);
    }
}
