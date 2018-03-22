package com.igorternyuk.texasholdem.tests;

import com.igorternyuk.texasholdem.model.Card;
import com.igorternyuk.texasholdem.model.Hand;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static com.igorternyuk.texasholdem.model.Card.Rank;
import static com.igorternyuk.texasholdem.model.Card.Suit;
import static com.igorternyuk.texasholdem.model.Hand.Combination;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by igor on 20.03.18.
 */
public class HandsComparisonTest {

    @Test
    public void testOfHighCard(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.SIX));
        communityCards.add(new Card(Suit.CLUBS, Rank.FIVE));
        communityCards.add(new Card(Suit.SPADES, Rank.SEVEN));
        communityCards.add(new Card(Suit.SPADES, Rank.KING));
        final Hand firstHand = new Hand();
        firstHand.addCard(new Card(Suit.CLUBS, Rank.ACE));
        firstHand.addCard(new Card(Suit.CLUBS, Rank.TEN));
        final Hand secondHand = new Hand();
        secondHand.addCard(new Card(Suit.CLUBS, Rank.TWO));
        secondHand.addCard(new Card(Suit.CLUBS, Rank.THREE));
        for(final Card card: communityCards){
            firstHand.addCard(card);
            secondHand.addCard(card);
        }
        assertEquals(Combination.HIGH_CARD, firstHand.getCombination());
        assertEquals(Combination.HIGH_CARD, secondHand.getCombination());
        assertTrue(firstHand.compareTo(secondHand) > 0);
    }

    @Test
    public void testOfPairTie(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        communityCards.add(new Card(Suit.HEARTS, Rank.SIX));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.TWO));
        communityCards.add(new Card(Suit.SPADES, Rank.SEVEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.KING));
        final Hand firstHand = new Hand();
        firstHand.addCard(new Card(Suit.CLUBS, Rank.FOUR));
        firstHand.addCard(new Card(Suit.SPADES, Rank.FOUR));
        final Hand secondHand = new Hand();
        secondHand.addCard(new Card(Suit.HEARTS, Rank.FIVE));
        secondHand.addCard(new Card(Suit.CLUBS, Rank.FIVE));
        for(final Card card: communityCards){
            firstHand.addCard(card);
            secondHand.addCard(card);
        }
        assertEquals(Combination.PAIR, firstHand.getCombination());
        assertEquals(Combination.PAIR, secondHand.getCombination());
        assertTrue(firstHand.compareTo(secondHand) < 0);
    }

    @Test
    public void testOfQuadsAgainstQuads(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        communityCards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.EIGHT));
        communityCards.add(new Card(Suit.SPADES, Rank.EIGHT));
        communityCards.add(new Card(Suit.SPADES, Rank.KING));
        final Hand firstHand = new Hand();
        firstHand.addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));
        firstHand.addCard(new Card(Suit.HEARTS, Rank.EIGHT));
        final Hand secondHand = new Hand();
        secondHand.addCard(new Card(Suit.SPADES, Rank.QUEEN));
        secondHand.addCard(new Card(Suit.CLUBS, Rank.QUEEN));
        for(final Card card: communityCards){
            firstHand.addCard(card);
            secondHand.addCard(card);
        }
        assertEquals(Combination.FOUR_OF_A_KIND, firstHand.getCombination());
        assertEquals(Combination.FOUR_OF_A_KIND, secondHand.getCombination());
        assertTrue(firstHand.compareTo(secondHand) < 0);
    }

    @Test
    public void testOfFullHouseAgainstFullHouse(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        communityCards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        communityCards.add(new Card(Suit.CLUBS, Rank.TWO));
        communityCards.add(new Card(Suit.SPADES, Rank.TWO));
        communityCards.add(new Card(Suit.CLUBS, Rank.TWO));
        final Hand firstHand = new Hand();
        firstHand.addCard(new Card(Suit.DIAMONDS, Rank.ACE));
        firstHand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        final Hand secondHand = new Hand();
        secondHand.addCard(new Card(Suit.HEARTS, Rank.FIVE));
        secondHand.addCard(new Card(Suit.CLUBS, Rank.FOUR));
        for(final Card card: communityCards){
            firstHand.addCard(card);
            secondHand.addCard(card);
        }
        final Combination firstHandCombination = firstHand.getCombination();
        final Combination secondHandCombination = secondHand.getCombination();
        System.out.println(firstHand);
        System.out.println(secondHand);
        assertEquals(Combination.FULL_HOUSE, firstHandCombination);
        assertEquals(Combination.FULL_HOUSE, secondHandCombination);
        assertTrue(firstHand.compareTo(secondHand) > 0);
    }

    @Test
    public void testOfTwoPaisAgainstFullHouse(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        communityCards.add(new Card(Suit.HEARTS, Rank.KING));
        communityCards.add(new Card(Suit.HEARTS, Rank.THREE));
        communityCards.add(new Card(Suit.SPADES, Rank.TWO));
        communityCards.add(new Card(Suit.CLUBS, Rank.TWO));
        final Hand firstHand = new Hand();
        firstHand.addCard(new Card(Suit.DIAMONDS, Rank.KING));
        firstHand.addCard(new Card(Suit.HEARTS, Rank.SIX));
        final Hand secondHand = new Hand();
        secondHand.addCard(new Card(Suit.HEARTS, Rank.TWO));
        secondHand.addCard(new Card(Suit.CLUBS, Rank.THREE));
        for(final Card card: communityCards){
            firstHand.addCard(card);
            secondHand.addCard(card);
        }
        assertEquals(Combination.TWO_PAIRS, firstHand.getCombination());
        assertEquals(Combination.FULL_HOUSE, secondHand.getCombination());
        assertTrue(firstHand.compareTo(secondHand) < 0);
    }

    @Test
    public void testOfTwoPaisAgainstTwoPairs(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        communityCards.add(new Card(Suit.HEARTS, Rank.KING));
        communityCards.add(new Card(Suit.HEARTS, Rank.THREE));
        communityCards.add(new Card(Suit.SPADES, Rank.FIVE));
        communityCards.add(new Card(Suit.CLUBS, Rank.TEN));
        final Hand firstHand = new Hand();
        firstHand.addCard(new Card(Suit.DIAMONDS, Rank.KING));
        firstHand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        final Hand secondHand = new Hand();
        secondHand.addCard(new Card(Suit.HEARTS, Rank.TEN));
        secondHand.addCard(new Card(Suit.CLUBS, Rank.FIVE));
        for(final Card card: communityCards){
            firstHand.addCard(card);
            secondHand.addCard(card);
        }
        assertEquals(Combination.TWO_PAIRS, firstHand.getCombination());
        assertEquals(Combination.TWO_PAIRS, secondHand.getCombination());
        assertTrue(firstHand.compareTo(secondHand) > 0);
    }

    @Test
    public void testOfTwoPairsAgainstFlush(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.HEARTS, Rank.ACE));
        communityCards.add(new Card(Suit.SPADES, Rank.NINE));
        communityCards.add(new Card(Suit.HEARTS, Rank.NINE));
        communityCards.add(new Card(Suit.HEARTS, Rank.KING));
        communityCards.add(new Card(Suit.HEARTS, Rank.FIVE));
        final Hand firstHand = new Hand();
        firstHand.addCard(new Card(Suit.DIAMONDS, Rank.ACE));
        firstHand.addCard(new Card(Suit.DIAMONDS, Rank.FOUR));
        final Hand secondHand = new Hand();
        secondHand.addCard(new Card(Suit.SPADES, Rank.ACE));
        secondHand.addCard(new Card(Suit.HEARTS, Rank.TWO));
        for(final Card card: communityCards){
            firstHand.addCard(card);
            secondHand.addCard(card);
        }
        assertEquals(Combination.TWO_PAIRS, firstHand.getCombination());
        assertEquals(Combination.FLUSH, secondHand.getCombination());
        assertTrue(firstHand.compareTo(secondHand) < 0);
    }

    @Test
    public void testOfTwoPairsTie(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.CLUBS, Rank.FOUR));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.FOUR));
        communityCards.add(new Card(Suit.CLUBS, Rank.JACK));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        communityCards.add(new Card(Suit.CLUBS, Rank.TEN));
        final Hand firstHand = new Hand();
        firstHand.addCard(new Card(Suit.CLUBS, Rank.THREE));
        firstHand.addCard(new Card(Suit.DIAMONDS, Rank.KING));
        final Hand secondHand = new Hand();
        secondHand.addCard(new Card(Suit.DIAMONDS, Rank.QUEEN));
        secondHand.addCard(new Card(Suit.HEARTS, Rank.SIX));
        for(final Card card: communityCards){
            firstHand.addCard(card);
            secondHand.addCard(card);
        }
        assertEquals(Combination.TWO_PAIRS, firstHand.getCombination());
        System.out.println(firstHand);
        assertEquals(Combination.TWO_PAIRS, secondHand.getCombination());
        System.out.println(secondHand);
        assertTrue(firstHand.compareTo(secondHand) > 0);
    }

    @Test
    public void testOfSituationWithThreePairs(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.CLUBS, Rank.FOUR));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.FOUR));
        communityCards.add(new Card(Suit.CLUBS, Rank.JACK));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        communityCards.add(new Card(Suit.CLUBS, Rank.TEN));
        final Hand firstHand = new Hand();
        firstHand.addCard(new Card(Suit.HEARTS, Rank.TEN));
        firstHand.addCard(new Card(Suit.DIAMONDS, Rank.KING));
        final Hand secondHand = new Hand();
        secondHand.addCard(new Card(Suit.DIAMONDS, Rank.ACE));
        secondHand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        for(final Card card: communityCards){
            firstHand.addCard(card);
            secondHand.addCard(card);
        }
        assertEquals(Combination.TWO_PAIRS, firstHand.getCombination());
        System.out.println(firstHand);
        assertEquals(Combination.TWO_PAIRS, secondHand.getCombination());
        System.out.println(secondHand);
        assertTrue(firstHand.compareTo(secondHand) < 0);
    }

    @Test
    public void testOfTripleAgainstQuads(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        communityCards.add(new Card(Suit.HEARTS, Rank.KING));
        communityCards.add(new Card(Suit.HEARTS, Rank.THREE));
        communityCards.add(new Card(Suit.SPADES, Rank.FIVE));
        communityCards.add(new Card(Suit.CLUBS, Rank.FIVE));
        final Hand firstHand = new Hand();
        firstHand.addCard(new Card(Suit.DIAMONDS, Rank.KING));
        firstHand.addCard(new Card(Suit.HEARTS, Rank.KING));
        final Hand secondHand = new Hand();
        secondHand.addCard(new Card(Suit.HEARTS, Rank.FIVE));
        secondHand.addCard(new Card(Suit.CLUBS, Rank.FIVE));
        for(final Card card: communityCards){
            firstHand.addCard(card);
            secondHand.addCard(card);
        }
        assertEquals(Combination.FULL_HOUSE, firstHand.getCombination());
        assertEquals(Combination.FOUR_OF_A_KIND, secondHand.getCombination());
        assertTrue(firstHand.compareTo(secondHand) < 0);
    }

    @Test
    public void testOfQuadsAgainstQuadsWithKicker(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.THREE));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.KING));
        communityCards.add(new Card(Suit.HEARTS, Rank.KING));
        communityCards.add(new Card(Suit.SPADES, Rank.KING));
        communityCards.add(new Card(Suit.CLUBS, Rank.KING));
        final Hand firstHand = new Hand();
        firstHand.addCard(new Card(Suit.DIAMONDS, Rank.JACK));
        firstHand.addCard(new Card(Suit.HEARTS, Rank.FIVE));
        final Hand secondHand = new Hand();
        secondHand.addCard(new Card(Suit.HEARTS, Rank.QUEEN));
        secondHand.addCard(new Card(Suit.CLUBS, Rank.FOUR));
        for(final Card card: communityCards){
            firstHand.addCard(card);
            secondHand.addCard(card);
        }
        assertEquals(Combination.FOUR_OF_A_KIND, firstHand.getCombination());
        assertEquals(Combination.FOUR_OF_A_KIND, secondHand.getCombination());
        assertTrue(firstHand.compareTo(secondHand) < 0);
    }

    @Test
    public void testOfTripleAgainstTripleWithKicker(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.THREE));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.TWO));
        communityCards.add(new Card(Suit.HEARTS, Rank.KING));
        communityCards.add(new Card(Suit.SPADES, Rank.KING));
        communityCards.add(new Card(Suit.CLUBS, Rank.KING));
        final Hand firstHand = new Hand();
        firstHand.addCard(new Card(Suit.DIAMONDS, Rank.JACK));
        firstHand.addCard(new Card(Suit.HEARTS, Rank.FIVE));
        final Hand secondHand = new Hand();
        secondHand.addCard(new Card(Suit.HEARTS, Rank.SIX));
        secondHand.addCard(new Card(Suit.DIAMONDS, Rank.SEVEN));
        for(final Card card: communityCards){
            firstHand.addCard(card);
            secondHand.addCard(card);
        }
        assertEquals(Combination.THREE_OF_A_KIND, firstHand.getCombination());
        assertEquals(Combination.THREE_OF_A_KIND, secondHand.getCombination());
        assertTrue(firstHand.compareTo(secondHand) > 0);
    }

    @Test
    public void testOfStraightAgainstStraightWithLowAce(){
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Suit.DIAMONDS, Rank.THREE));
        communityCards.add(new Card(Suit.DIAMONDS, Rank.TWO));
        communityCards.add(new Card(Suit.HEARTS, Rank.NINE));
        communityCards.add(new Card(Suit.SPADES, Rank.FIVE));
        communityCards.add(new Card(Suit.CLUBS, Rank.EIGHT));
        final Hand firstHand = new Hand();
        firstHand.addCard(new Card(Suit.DIAMONDS, Rank.ACE));
        firstHand.addCard(new Card(Suit.HEARTS, Rank.FOUR));
        final Hand secondHand = new Hand();
        secondHand.addCard(new Card(Suit.HEARTS, Rank.SIX));
        secondHand.addCard(new Card(Suit.DIAMONDS, Rank.SEVEN));
        for(final Card card: communityCards){
            firstHand.addCard(card);
            secondHand.addCard(card);
        }
        assertEquals(Combination.STRAIGHT_WITH_LOW_ACE, firstHand.getCombination());
        assertEquals(Combination.STRAIGHT, secondHand.getCombination());
        assertTrue(firstHand.compareTo(secondHand) < 0);
    }
}
