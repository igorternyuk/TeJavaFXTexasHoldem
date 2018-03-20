package com.igorternyuk.texasholdem.tests;

import com.igorternyuk.texasholdem.model.Card;
import com.igorternyuk.texasholdem.model.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
    @Before
    public void setUp(){

    }

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
}
