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
        STRAIGHT_WITH_LOW_ACE("Straight with low ace"),
        STRAIGHT("Straight"),
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

    private static final int MAX_NUMBER_OF_CARDS = 7;
    private static final int MIN_NUMBER_OF_CARDS = 5;
    private List<Card> cards = new ArrayList<>();
    private List<Card> holeCards = new ArrayList<>();
    private Combination combination = Combination.NO_HAND;
    private final List<Card> finalFiveCardCombination = new ArrayList<>(MIN_NUMBER_OF_CARDS);
    private boolean isHandChanged = true;

    public Hand(){
        this.combination = Combination.NO_HAND;
    }

    public List<Card> getHoleCards(){
        return this.holeCards;
    }

    public Combination getCombination(){
        if(this.isHandChanged){
            evaluate();
        }
        return this.combination;
    }
    public List<Card> getFinalFiveCombinationCards(){
        if(this.isHandChanged){
            evaluate();
        }
        return Collections.unmodifiableList(this.finalFiveCardCombination);
    }

    public void addCard(final Card card){
        if(this.cards.size() < MAX_NUMBER_OF_CARDS) {
            this.cards.add(card);
            if(this.cards.size() <= 2){
                this.holeCards.add(card);
            }
            this.isHandChanged = true;
        }
    }

    public void clearCards(){
        this.cards.clear();
        this.holeCards.clear();
        this.finalFiveCardCombination.clear();
        this.combination = Combination.NO_HAND;
    }

    private void evaluate() {
        if (this.isHandChanged) {
            this.finalFiveCardCombination.clear();
            this.combination = Combination.NO_HAND;
            if (this.cards.size() >= MIN_NUMBER_OF_CARDS && this.cards.size() <= MAX_NUMBER_OF_CARDS) {

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
                for (Map.Entry<Suit, List<Card>> entry : suitOccurences.entrySet()) {
                    if (entry.getValue().size() >= MIN_NUMBER_OF_CARDS) {
                        isFlush = true;
                        cardsOfTheSameFlush = entry.getValue();
                        cardsOfTheSameFlush.sort(Comparator.comparing(Card::getValue));
                        break;
                    }
                }

                int numberOfPairs = 0, numberOfTriples = 0, numberOfQuads = 0, numberInARow = 0, maxNumberInARow = 0,
                        maxValueInARow = 0;
                List<Card> longestCardSequence = new ArrayList<>(MAX_NUMBER_OF_CARDS);
                final List<Card> currentCardSquence = new ArrayList<>(MAX_NUMBER_OF_CARDS);
                final Map<Integer, List<Card>> pairs = new HashMap<>();
                final Map<Integer, List<Card>> triples = new HashMap<>();
                final Map<Integer, List<Card>> quads = new HashMap<>();
                for (Map.Entry<Integer, List<Card>> entry : rankOccurences.entrySet()) {
                    final List<Card> currCardList = entry.getValue();
                    switch (currCardList.size()) {
                        case 0:
                            if (numberInARow > longestCardSequence.size()) {
                                maxValueInARow = currentCardSquence.get(currentCardSquence.size() - 1).getValue();
                                longestCardSequence.clear();
                                longestCardSequence.addAll(currentCardSquence);
                                maxNumberInARow = longestCardSequence.size();
                            }
                            numberInARow = 0;
                            currentCardSquence.clear();
                            break;
                        case 1:
                            ++numberInARow;
                            currentCardSquence.add(currCardList.get(0));
                            break;
                        case 2:
                            if(entry.getKey() != Card.LOW_ACE_VALUE) {
                                pairs.put(++numberOfPairs, currCardList);
                            }
                            break;
                        case 3:
                            if(entry.getKey() != Card.LOW_ACE_VALUE) {
                                triples.put(++numberOfTriples, currCardList);
                            }
                            break;
                        default:
                            if(entry.getKey() != Card.LOW_ACE_VALUE) {
                                quads.put(++numberOfQuads, currCardList);
                            }
                            break;
                    }
                }

                if(numberInARow >= MIN_NUMBER_OF_CARDS || maxNumberInARow >= MIN_NUMBER_OF_CARDS){
                    if(numberInARow > MAX_NUMBER_OF_CARDS) {
                        numberInARow = MAX_NUMBER_OF_CARDS;
                    }
                    if(numberInARow > maxNumberInARow){
                        longestCardSequence.clear();
                        longestCardSequence.addAll(currentCardSquence);
                        maxNumberInARow = numberInARow;
                    }
                    longestCardSequence = longestCardSequence.subList(maxNumberInARow - MIN_NUMBER_OF_CARDS,
                            longestCardSequence.size());
                    maxValueInARow = longestCardSequence.get(longestCardSequence.size() - 1).getValue();
                    maxNumberInARow = MIN_NUMBER_OF_CARDS;
                    currentCardSquence.clear();
                }

                if (maxNumberInARow == MIN_NUMBER_OF_CARDS) {
                    if (isFlush) {
                        if (maxValueInARow == Rank.ACE.getValue()) {
                            this.combination = Combination.ROYAL_FLESH;
                        } else if (maxValueInARow == Rank.FIVE.getValue()) {
                            this.combination = Combination.STRAIGHT_FLUSH_WITH_LOW_ACE;
                        } else {
                            this.combination = Combination.STRAIGHT_FLUSH;
                        }
                    } else {
                        if (maxValueInARow == Rank.FIVE.getValue()) {
                            this.combination = Combination.STRAIGHT_WITH_LOW_ACE;
                        } else {
                            this.combination = Combination.STRAIGHT;
                        }
                    }
                    this.finalFiveCardCombination.addAll(longestCardSequence);
                } else if (numberOfQuads > 0) {
                    this.combination = Combination.FOUR_OF_A_KIND;
                    this.finalFiveCardCombination.addAll(quads.get(1));
                    final int quadsRank = quads.get(1).get(0).getValue();
                    List<Card> otherCards = this.cards.stream().filter(card -> card.getValue() != quadsRank)
                            .collect(Collectors.toList());
                    otherCards.sort(Comparator.comparing(Card::getValue).reversed());
                    finalFiveCardCombination.add(otherCards.get(0));
                } else if (numberOfTriples == 2) {
                    this.combination = Combination.FULL_HOUSE;
                    this.finalFiveCardCombination.addAll(triples.get(2));
                    this.finalFiveCardCombination.add(triples.get(1).get(0));
                    this.finalFiveCardCombination.add(triples.get(1).get(1));
                } else if (numberOfTriples == 1) {
                    List<Card> triple = triples.get(1);
                    if (numberOfPairs == 2) {
                        this.combination = Combination.FULL_HOUSE;
                        this.finalFiveCardCombination.addAll(triples.get(1));
                        this.finalFiveCardCombination.addAll(pairs.get(2));
                    } else if (numberOfPairs == 1) {
                        this.combination = Combination.FULL_HOUSE;
                        this.finalFiveCardCombination.addAll(triple);
                        this.finalFiveCardCombination.addAll(pairs.get(1));
                    } else if(isFlush) {
                        this.combination = Combination.FLUSH;
                        this.finalFiveCardCombination.addAll(cardsOfTheSameFlush);
                        this.finalFiveCardCombination.sort(Comparator.comparing(Card::getValue).reversed());
                    } else {
                        this.combination = Combination.THREE_OF_A_KIND;
                        this.finalFiveCardCombination.addAll(triple);
                        final int tripleRank = triple.get(0).getValue();
                        final List<Card> otherCards = this.cards.stream().filter(card -> card.getValue() != tripleRank)
                                .collect(Collectors.toList());
                        otherCards.sort(Comparator.comparing(Card::getValue).reversed());
                        this.finalFiveCardCombination.addAll(otherCards.subList(0,2));
                    }
                } else if(isFlush) {
                    this.combination = Combination.FLUSH;
                    this.finalFiveCardCombination.addAll(cardsOfTheSameFlush);
                    this.finalFiveCardCombination.sort(Comparator.comparing(Card::getValue).reversed());
                }  else if(numberOfPairs == 3){
                    this.combination = Combination.TWO_PAIRS;
                    this.finalFiveCardCombination.addAll(pairs.get(3));
                    this.finalFiveCardCombination.addAll(pairs.get(2));
                    this.finalFiveCardCombination.add(pairs.get(1).get(0));
                }  else if(numberOfPairs == 2){
                    final int firstPairRank = pairs.get(1).get(0).getValue();
                    final int secondPairRank = pairs.get(2).get(0).getValue();
                    this.combination = Combination.TWO_PAIRS;
                    this.finalFiveCardCombination.addAll(pairs.get(2));
                    this.finalFiveCardCombination.addAll(pairs.get(1));
                    final List<Card> otherCards = this.cards.stream()
                            .filter(card -> card.getValue() != firstPairRank
                                    && card.getValue() != secondPairRank).collect(Collectors.toList());
                    otherCards.sort(Comparator.comparing(Card::getValue).reversed());
                    this.finalFiveCardCombination.add(otherCards.get(0));
                } else if(numberOfPairs == 1){
                    this.combination = Combination.PAIR;
                    final List<Card> pair = pairs.get(1);
                    final int pairRank = pair.get(0).getValue();
                    this.finalFiveCardCombination.addAll(pair);
                    final List<Card> otherCards = this.cards.stream().filter(card -> card.getValue() != pairRank)
                            .collect(Collectors.toList());
                    otherCards.sort(Comparator.comparing(Card::getValue).reversed());
                    this.finalFiveCardCombination.addAll(otherCards.subList(0,3));
                } else {
                    this.combination = Combination.HIGH_CARD;
                    this.cards.sort(Comparator.comparing(Card::getValue).reversed());
                    this.finalFiveCardCombination.addAll(this.cards.subList(0,MIN_NUMBER_OF_CARDS));
                }
            }
            this.isHandChanged = false;
        }
    }

    @Override
    public int compareTo(final Hand other) {
        if(this.getCombination().ordinal() > other.getCombination().ordinal()){
            return 1;
        } else if(this.getCombination().ordinal() < other.getCombination().ordinal()){
            return -1;
        } else {
            for(int i = 0; i < MIN_NUMBER_OF_CARDS; ++i){
                final int thisCurrCardValue = this.getFinalFiveCombinationCards().get(i).getValue();
                final int otherCurrCardValue = other.getFinalFiveCombinationCards().get(i).getValue();
                if(thisCurrCardValue == otherCurrCardValue)
                    continue;
                if(thisCurrCardValue > otherCurrCardValue)
                    return 1;
                else if(thisCurrCardValue < otherCurrCardValue)
                    return -1;
            }
        }
        return 0;
    }

    @Override
    public String toString(){
        final StringBuilder stringBuilder = new StringBuilder();
        if(!this.finalFiveCardCombination.isEmpty()){
            this.finalFiveCardCombination.forEach(card -> stringBuilder.append(card).append("-"));
            final String result = stringBuilder.toString();
            return String.format("%s\n%s", result.substring(0, result.length() - 1), this.combination);
        } else {
            this.cards.forEach(card -> stringBuilder.append(card).append("-"));
            final String result = stringBuilder.toString();
            return String.format("%s\n%s", result.substring(0, result.length() - 1), this.combination);
        }
    }
}
