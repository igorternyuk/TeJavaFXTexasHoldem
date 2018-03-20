package com.igorternyuk.texasholdem.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.igorternyuk.texasholdem.model.Card.LOW_ACE_VALUE;
import static com.igorternyuk.texasholdem.model.Card.Suit;
import static com.igorternyuk.texasholdem.model.Card.Rank;

/**
 * Created by igor on 18.03.18.
 */
public class Hand implements Comparable<Hand> {
    public enum Combination{
        NO_HAND("No hand"),
        HIGH_CARD("High card"),
        PAIR("Pair"),
        TWO_PAIRS("Two pairs"),
        THREE_OF_A_KIND("Three of a kind"),
        STRAIGHT("Straight"),
        STRAIGHT_WITH_LOW_ACE("Straight with low ace"),
        FLUSH("Flush"),
        FULL_HOUSE("Full house"),
        FOUR_OF_A_KIND("Quads"),
        STRAIGHT_FLUSH_WITH_LOW_ACE("Straight flush with low ace"),
        STRAIGHT_FLUSH("Straight flush"),
        ROYAL_FLESH("Royal flesh");

        private String text;

        Combination(final String text){
            this.text = text;
        }

        @Override
        public String toString(){
            return this.text;
        };
    }

    public class CardOccurence{
        private int numberOfAKind;
        private int cardValue;

        public CardOccurence(final int numberOfAKind, final int cardValue) {
            this.numberOfAKind = numberOfAKind;
            this.cardValue = cardValue;
        }
    }

    public class Value{
        private Combination combination;
        private List<CardOccurence> pattern;

        public Value(final Combination combination, final List<CardOccurence> pattern){
            this.combination = combination;
            this.pattern = pattern;
        }
    }

    private static final int MAX_NUMBER_OF_CARDS = 7;
    private static final int MIN_NUMBER_OF_CARDS = 5;
    private List<Card> cards = new ArrayList<>();
    private final List<Card> finalFiveCardCombination = new ArrayList<>(MIN_NUMBER_OF_CARDS);
    private Value value;
    private boolean isValueChanged = true;

    public Hand(){
        this.value.combination = Combination.NO_HAND;
        this.value.pattern.clear();
    }

    public Value getValue(){
        if(this.isValueChanged){
            evaluate();
        }
        return this.value;
    }

    public List<Card> getFinalFiveCardCombination(){
        if(this.isValueChanged){
            evaluate();
        }
        return Collections.unmodifiableList(this.finalFiveCardCombination);
    }

    public void addCard(final Card card){
        if(this.cards.size() < MAX_NUMBER_OF_CARDS) {
            this.cards.add(card);
            this.cards.sort(Comparator.comparing(Card::getValue));
            this.isValueChanged = true;
        }
    }

    public void clearCards(){
        this.cards.clear();
        this.value.combination = Combination.NO_HAND;
        this.value.pattern.clear();
    }

    public void setLowAceToTheFirstPlace(){

    }


    @Override
    public String toString(){
        final StringBuilder stringBuilder = new StringBuilder();
        this.cards.forEach(card -> {
            stringBuilder.append(card + "-");
        });
        final String result = stringBuilder.toString();
        return result.substring(0, result.length() - 1) + this.value.combination;
    }

    private void evaluate() {
        if (this.isValueChanged) {
            this.finalFiveCardCombination.clear();
            final Value value = new Value(Combination.NO_HAND, new ArrayList<>());
            if (this.cards.size() >= MIN_NUMBER_OF_CARDS && this.cards.size() < MAX_NUMBER_OF_CARDS) {

                final Map<Integer, List<Card>> rankOccurences = new HashMap<>();
                for (int val = LOW_ACE_VALUE; val <= Rank.ACE.getValue(); ++val) {
                    rankOccurences.put(val, new ArrayList<>(4));
                }

                final Map<Suit, List<Card>> suitOccurences = new HashMap<>();
                for (Suit suit : Suit.values()) {
                    suitOccurences.put(suit, new ArrayList<>(7));
                }

                this.cards.forEach(card -> {
                    rankOccurences.get(card.getValue()).add(card);
                    if (card.isRank(Rank.ACE)) {
                        rankOccurences.get(LOW_ACE_VALUE).add(card);
                    }
                    suitOccurences.get(card.getSuit()).add(card);
                });

                boolean isFlush = false;
                List<Card> cardsOfTheSameFlush = null;
                int highestFlushValue = 0;
                for (Map.Entry<Suit, List<Card>> entry : suitOccurences.entrySet()) {
                    if (entry.getValue().size() >= MIN_NUMBER_OF_CARDS) {
                        isFlush = true;
                        cardsOfTheSameFlush = entry.getValue();
                        cardsOfTheSameFlush.sort(Comparator.comparing(Card::getValue));
                        highestFlushValue = cardsOfTheSameFlush.get(cardsOfTheSameFlush.size() - 1).getValue();
                        break;
                    }
                }

                int numberOfPairs = 0, numberOfTriples = 0, numberOfQuads = 0, numberInARow = 0, maxValueInARow = 0;
                final List<Card> cardsInARowOld = new ArrayList<>(MAX_NUMBER_OF_CARDS);
                final List<Card> cardsInARowNew = new ArrayList<>(MAX_NUMBER_OF_CARDS);
                final Map<Integer, List<Card>> pairs = new HashMap<>();
                final Map<Integer, List<Card>> triples = new HashMap<>();
                final Map<Integer, List<Card>> quads = new HashMap<>();
                for (Map.Entry<Integer, List<Card>> entry : rankOccurences.entrySet()) {
                    final List<Card> currCardList = entry.getValue();
                    switch (currCardList.size()) {
                        case 0:
                            if (numberInARow > cardsInARowOld.size()) {
                                maxValueInARow = cardsInARowNew.get(cardsInARowNew.size() - 1).getValue();
                                cardsInARowOld.clear();
                                cardsInARowOld.addAll(cardsInARowNew);
                                cardsInARowNew.clear();
                            }
                            numberInARow = 0;
                            break;
                        case 1:
                            ++numberInARow;
                            cardsInARowNew.add(currCardList.get(0));
                        case 2:
                            pairs.put(++numberOfPairs, currCardList);
                            break;
                        case 3:
                            triples.put(++numberOfTriples, currCardList);
                            break;
                        default:
                            quads.put(++numberOfQuads, currCardList);
                            break;
                    }
                }
                if (numberInARow >= MIN_NUMBER_OF_CARDS) {
                    boolean isStraight = false;
                    if (isFlush) {
                        if (maxValueInARow == Rank.ACE.getValue()) {
                            value.combination = Combination.ROYAL_FLESH;
                            isStraight = true;
                        } else if (maxValueInARow == Rank.FIVE.getValue()) {
                            value.combination = Combination.STRAIGHT_FLUSH_WITH_LOW_ACE;
                            isStraight = true;
                        } else if (maxValueInARow == highestFlushValue) {
                            value.combination = Combination.STRAIGHT_FLUSH;
                            isStraight = true;
                        }
                    } else {
                        if (maxValueInARow == Rank.FIVE.getValue()) {
                            value.combination = Combination.STRAIGHT_WITH_LOW_ACE;
                            isStraight = true;
                        } else {
                            value.combination = Combination.STRAIGHT;
                            isStraight = true;
                        }
                    }
                    if (isStraight) {
                        for (int i = numberInARow - MIN_NUMBER_OF_CARDS; i < MIN_NUMBER_OF_CARDS; ++i) {
                            this.finalFiveCardCombination.add(cardsInARowOld.get(i));
                            value.pattern.add(new CardOccurence(1, cardsInARowOld.get(i).getValue()));
                        }
                    }
                } else if (numberOfQuads > 0) {
                    value.combination = Combination.FOUR_OF_A_KIND;
                    final int val = quads.get(numberOfQuads).get(0).getValue();
                    value.pattern.add(new CardOccurence(4, val));

                } else if (numberOfTriples == 2) {
                    value.combination = Combination.FULL_HOUSE;
                    final List<Card> firstTriple = triples.get(1);
                    final List<Card> secondTriple = triples.get(2);
                    final int firstTripleValue = firstTriple.get(0).getValue();
                    final int secondTripleValue = secondTriple.get(0).getValue();
                    final List<Card> highRankedTriple = firstTripleValue > secondTripleValue ? firstTriple : secondTriple;
                    final List<Card> lowRankedTriple = firstTripleValue > secondTripleValue ? secondTriple : firstTriple;
                    this.finalFiveCardCombination.addAll(highRankedTriple);
                    this.finalFiveCardCombination.add(lowRankedTriple.get(0));
                    this.finalFiveCardCombination.add(lowRankedTriple.get(1));
                    value.pattern.add(new CardOccurence(3, highRankedTriple.get(0).getValue()));
                    value.pattern.add(new CardOccurence(2, lowRankedTriple.get(0).getValue()));
                } else if (numberOfTriples == 1) {
                    List<Card> triple = triples.get(1);
                    if (numberOfPairs == 2) {
                        value.combination = Combination.FULL_HOUSE;
                        value.pattern.add(new CardOccurence(3, triple.get(0).getValue()));
                        this.finalFiveCardCombination.addAll(triples.get(1));
                        final List<Card> firstPair = pairs.get(1);
                        final List<Card> secondPair = pairs.get(2);
                        if(firstPair.get(0).getValue() > secondPair.get(0).getValue()){
                            this.finalFiveCardCombination.addAll(firstPair);
                            value.pattern.add(new CardOccurence(2, firstPair.get(0).getValue()));
                        } else {
                            this.finalFiveCardCombination.addAll(secondPair);
                            value.pattern.add(new CardOccurence(3, secondPair.get(0).getValue()));
                        }
                    } else if (numberOfPairs == 1) {
                        value.combination = Combination.FULL_HOUSE;
                        final List<Card> pair = pairs.get(1);
                        value.pattern.add(new CardOccurence(3, triple.get(0).getValue()));
                        value.pattern.add(new CardOccurence(2, pair.get(0).getValue()));
                        this.finalFiveCardCombination.addAll(triple);
                        this.finalFiveCardCombination.addAll(pair);
                    }
                    value.combination = Combination.THREE_OF_A_KIND;
                    value.pattern.add(new CardOccurence(3, triple.get(0).getValue()));
                    this.finalFiveCardCombination.addAll(triple);
                    this.cards.sort(Comparator.comparing(Card::getValue));
                    for(int i = this.cards.size() - 1; i >= 0; --i){
                        if(this.cards.get(i).getValue() == triple.get(0).getValue())
                            continue;
                        this.finalFiveCardCombination.add(cards.get(i));
                        if(this.finalFiveCardCombination.size() == MIN_NUMBER_OF_CARDS)
                            break;
                    }
                } else if(numberOfPairs == 2){
                    final List<Card> firstPair = pairs.get(1);
                    final int firstPairValue = firstPair.get(0).getValue();
                    final List<Card> secondPair = pairs.get(2);
                    final int secondPairValue = secondPair.get(0).getValue();
                    value.combination = Combination.TWO_PAIRS;
                    final List<Card> highRankedPair = firstPairValue > secondPairValue ? firstPair : secondPair;
                    final List<Card> lowRankedPair = firstPairValue > secondPairValue ? secondPair : firstPair;
                    value.pattern.add(new CardOccurence(2, highRankedPair.get(0).getValue()));
                    value.pattern.add(new CardOccurence(2, lowRankedPair.get(0).getValue()));
                    this.finalFiveCardCombination.addAll(firstPair);
                    this.finalFiveCardCombination.addAll(secondPair);
                    this.cards.sort(Comparator.comparing(Card::getValue).reversed());
                    for(final Card card: this.cards){
                        if(card.getValue() == firstPair.get(0).getValue()
                           || card.getValue() == secondPair.get(0).getValue())
                            continue;
                        this.finalFiveCardCombination.add(card);
                        break;
                    }
                } else if(numberOfPairs == 1){
                    value.combination = Combination.PAIR;
                    final List<Card> pair = pairs.get(1);
                    value.pattern.add(new CardOccurence(2, pair.get(0).getValue()));
                    this.finalFiveCardCombination.addAll(pair);
                    this.cards.sort(Comparator.comparing(Card::getValue).reversed());
                    for(final Card card: this.cards){
                        if(card.getValue() == pair.get(0).getValue()) continue;
                        this.finalFiveCardCombination.add(card);
                        if(this.finalFiveCardCombination.size() == MIN_NUMBER_OF_CARDS) break;
                    }
                } else if (isFlush) {
                    value.combination = Combination.FLUSH;
                    for(final Card card: cardsOfTheSameFlush){
                        value.pattern.add(new CardOccurence(1, card.getValue()));
                    }
                    this.finalFiveCardCombination.addAll(cardsOfTheSameFlush);
                    this.finalFiveCardCombination.sort(Comparator.comparing(Card::getValue).reversed());

                } else {
                    value.combination = Combination.HIGH_CARD;
                    this.cards.sort(Comparator.comparing(Card::getValue).reversed());
                    for(final Card card: this.cards){
                        this.finalFiveCardCombination.add(card);
                        value.pattern.add(new CardOccurence(1, card.getValue()));
                        if(finalFiveCardCombination.size() == MIN_NUMBER_OF_CARDS) break;
                    }
                }
            }
            this.value = value;
            this.isValueChanged = false;
        }
    }

    @Override
    public int compareTo(final Hand other) {
        if(this.getValue().combination.ordinal() > other.getValue().combination.ordinal()){
            return 1;
        } else if(this.getValue().combination.ordinal() < other.getValue().combination.ordinal()){
            return -1;
        } else {
            for(int i = 0; i < MIN_NUMBER_OF_CARDS; ++i){
                //this.getValue().pattern.get(0).cardValue
            }
        }
        return 0;
    }
}




/*

int poker::compareHandValues(const poker::Hand::Value &val1,
                                  const poker::Hand::Value &val2)
{
    int result{0};
    if(val1.combination > val2.combination)
        result = 1;
    else if(val1.combination < val2.combination)
        result = -1;
    else
    {
        for(auto i = 0u; i < Hand::NUM_CARDS_MAX; ++i)
        {
            if(val1.pattern[i].cardValue == val2.pattern[i].cardValue)
                continue;
            else
            {
                result = val1.pattern[i].cardValue > val2.pattern[i].cardValue ?
                            1 : -1;
                break;
            }
        }
    }
    return result;
}

void poker::Hand::setLowAceAtFirstPlace()
{
    Card tmp = cards_[cards_.size() - 1];
    for(int i{int(cards_.size() - 1)}; i > 0; --i)
        cards_[i] = cards_[i - 1];
    cards_[0] = tmp;
}


int poker::Hand::findIndexByValue(int val) const
{
    for(auto i = 0u; i < cards_.size(); ++i)
    {
        if(cards_[i].getValue() == val)
            return i;
    }
    return -1;
}
* */
