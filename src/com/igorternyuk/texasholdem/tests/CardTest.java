package com.igorternyuk.texasholdem.tests;

/**
 * Created by igor on 19.03.18.
 */
import com.igorternyuk.texasholdem.model.Card;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CardTest {
        @Test
        public void testAceValues(){
            final Card aceOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.ACE);
            final Card aceOfClubs = new Card(Card.Suit.CLUBS, Card.Rank.ACE);
            final Card aceOfDiamonds = new Card(Card.Suit.DIAMONDS, Card.Rank.ACE);
            final Card aceOfSpades = new Card(Card.Suit.SPADES, Card.Rank.ACE);
            assertEquals(14, aceOfHearts.getValue());
            assertEquals(14, aceOfClubs.getValue());
            assertEquals(14, aceOfDiamonds.getValue());
            assertEquals(14, aceOfSpades.getValue());
        }

        @Test
        public void testKingValues(){
            final Card kingOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.KING);
            final Card kingOfClubs = new Card(Card.Suit.CLUBS, Card.Rank.KING);
            final Card kingOfDiamonds = new Card(Card.Suit.DIAMONDS, Card.Rank.KING);
            final Card kingOfSpades = new Card(Card.Suit.SPADES, Card.Rank.KING);
            assertEquals(13, kingOfHearts.getValue());
            assertEquals(13, kingOfClubs.getValue());
            assertEquals(13, kingOfDiamonds.getValue());
            assertEquals(13, kingOfSpades.getValue());
        }

        @Test
        public void testQueenValues(){
            final Card queenOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.QUEEN);
            final Card queenOfClubs = new Card(Card.Suit.CLUBS, Card.Rank.QUEEN);
            final Card queenOfDiamonds = new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN);
            final Card queenOfSpades = new Card(Card.Suit.SPADES, Card.Rank.QUEEN);
            assertEquals(12, queenOfHearts.getValue());
            assertEquals(12, queenOfClubs.getValue());
            assertEquals(12, queenOfDiamonds.getValue());
            assertEquals(12, queenOfSpades.getValue());
        }

        @Test
        public void testJackValues(){
            final Card jackOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.JACK);
            final Card jackOfClubs = new Card(Card.Suit.CLUBS, Card.Rank.JACK);
            final Card jackOfDiamonds = new Card(Card.Suit.DIAMONDS, Card.Rank.JACK);
            final Card jackOfSpades = new Card(Card.Suit.SPADES, Card.Rank.JACK);
            assertEquals(11, jackOfHearts.getValue());
            assertEquals(11, jackOfClubs.getValue());
            assertEquals(11, jackOfDiamonds.getValue());
            assertEquals(11, jackOfSpades.getValue());
        }

        @Test
        public void testNumberValues(){
            final Card twoOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.TWO);
            final Card threeOfClubs = new Card(Card.Suit.CLUBS, Card.Rank.THREE);
            final Card fourOfDiamonds = new Card(Card.Suit.DIAMONDS, Card.Rank.FOUR);
            final Card fiveOfSpades = new Card(Card.Suit.SPADES, Card.Rank.FIVE);
            final Card sixOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.SIX);
            final Card sevenOfClubs = new Card(Card.Suit.CLUBS, Card.Rank.SEVEN);
            final Card eightOfDiamonds = new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT);
            final Card nineOfSpades = new Card(Card.Suit.SPADES, Card.Rank.NINE);
            final Card tenOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.TEN);
            assertEquals(2, twoOfHearts.getValue());
            assertEquals(3, threeOfClubs.getValue());
            assertEquals(4, fourOfDiamonds.getValue());
            assertEquals(5, fiveOfSpades.getValue());
            assertEquals(6, sixOfHearts.getValue());
            assertEquals(7, sevenOfClubs.getValue());
            assertEquals(8, eightOfDiamonds.getValue());
            assertEquals(9, nineOfSpades.getValue());
            assertEquals(10, tenOfHearts.getValue());
        }

        @Test
        public void testAceStringRepresentation(){
            final Card aceOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.ACE);
            assertEquals("AH", aceOfHearts.toString());
            final Card aceOfClubs = new Card(Card.Suit.CLUBS, Card.Rank.ACE);
            assertEquals("AC", aceOfClubs.toString());
            final Card aceOfDiamonds = new Card(Card.Suit.DIAMONDS, Card.Rank.ACE);
            assertEquals("AD", aceOfDiamonds.toString() );
            final Card aceOfSpades = new Card(Card.Suit.SPADES, Card.Rank.ACE);
            assertEquals("AS", aceOfSpades.toString() );
        }

        @Test
        public void testJackStringRepresentation(){
            final Card jackOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.JACK);
            assertEquals("JH", jackOfHearts.toString());
            final Card jackOfClubs = new Card(Card.Suit.CLUBS, Card.Rank.JACK);
            assertEquals("JC", jackOfClubs.toString());
            final Card jackOfDiamonds = new Card(Card.Suit.DIAMONDS, Card.Rank.JACK);
            assertEquals("JD", jackOfDiamonds.toString());
            final Card jackOfSpades = new Card(Card.Suit.SPADES, Card.Rank.JACK);
            assertEquals("JS", jackOfSpades.toString());
        }

        @Test
        public void testQueenStringRepresentation(){
            final Card queenOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.QUEEN);
            assertEquals("QH", queenOfHearts.toString());
            final Card queenOfClubs = new Card(Card.Suit.CLUBS, Card.Rank.QUEEN);
            assertEquals("QC", queenOfClubs.toString());
            final Card queenOfDiamonds = new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN);
            assertEquals("QD", queenOfDiamonds.toString());
            final Card queenOfSpades = new Card(Card.Suit.SPADES, Card.Rank.QUEEN);
            assertEquals("QS", queenOfSpades.toString());
        }

        @Test
        public void testKingStringRepresentation(){
            final Card kingOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.KING);
            assertEquals("KH", kingOfHearts.toString());
            final Card kingOfClubs = new Card(Card.Suit.CLUBS, Card.Rank.KING);
            assertEquals("KC", kingOfClubs.toString());
            final Card kingOfDiamonds = new Card(Card.Suit.DIAMONDS, Card.Rank.KING);
            assertEquals("KD", kingOfDiamonds.toString());
            final Card kingOfSpades = new Card(Card.Suit.SPADES, Card.Rank.KING);
            assertEquals("KS", kingOfSpades.toString());
        }

        @Test
        public void testNumbersStringRepresentation(){
            final Card twoOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.TWO);
            final Card threeOfClubs = new Card(Card.Suit.CLUBS, Card.Rank.THREE);
            final Card fourOfDiamonds = new Card(Card.Suit.DIAMONDS, Card.Rank.FOUR);
            final Card fiveOfSpades = new Card(Card.Suit.SPADES, Card.Rank.FIVE);
            final Card sixOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.SIX);
            final Card sevenOfClubs = new Card(Card.Suit.CLUBS, Card.Rank.SEVEN);
            final Card eightOfDiamonds = new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT);
            final Card nineOfSpades = new Card(Card.Suit.SPADES, Card.Rank.NINE);
            final Card tenOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.TEN);
            assertEquals("2H", twoOfHearts.toString());
            assertEquals("3C", threeOfClubs.toString());
            assertEquals("4D", fourOfDiamonds.toString());
            assertEquals("5S", fiveOfSpades.toString());
            assertEquals("6H", sixOfHearts.toString());
            assertEquals("7C", sevenOfClubs.toString());
            assertEquals("8D", eightOfDiamonds.toString());
            assertEquals("9S", nineOfSpades.toString());
            assertEquals("10H", tenOfHearts.toString());
        }
}
