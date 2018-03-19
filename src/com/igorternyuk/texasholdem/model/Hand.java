package com.igorternyuk.texasholdem.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
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
    public int compareTo(Hand o) {
        return 0;
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
        final Value value = new Value(Combination.NO_HAND, new ArrayList<>());
        if(this.cards.size() >= MIN_NUMBER_OF_CARDS && this.cards.size() < MAX_NUMBER_OF_CARDS){

            final Map<Integer, Integer> rankOccurences = new HashMap<>();
            for(int val = LOW_ACE_VALUE; val <= Rank.ACE.getValue(); ++val){
                rankOccurences.put(val, 0);
            }

            final Map<Suit, Integer> suitOccurences = new HashMap<>();
            for (Suit suit: Suit.values()){
                suitOccurences.put(suit, 0);
            }

            this.cards.forEach(card -> {
                rankOccurences.put(card.getValue(), rankOccurences.get(card.getValue()) + 1);
                if(card.isRank(Rank.ACE)){
                    rankOccurences.put(LOW_ACE_VALUE, rankOccurences.get(LOW_ACE_VALUE));
                }
                suitOccurences.put(card.getSuit(), suitOccurences.get(card.getSuit()) + 1);
            });

            boolean isFlush = false;
            List<Card> cardsOfTheSameFlush= null;
            int highestFlushValue = 0;
            for(Map.Entry<Suit, Integer> entry: suitOccurences.entrySet()){
                if(entry.getValue() >= MIN_NUMBER_OF_CARDS){
                    isFlush = true;
                    cardsOfTheSameFlush = this.cards.stream().filter(card -> card.isSuit(entry.getKey()))
                            .collect(Collectors.toList());
                    for (final Card card : cardsOfTheSameFlush) {
                        if(card.getValue() > highestFlushValue){
                            highestFlushValue = card.getValue();
                        }
                    }
                    break;
                }
            }

            int numberOfPairs = 0, numberOfTriples = 0, numberOfFours = 0, numberInARow = 0,
                maxNumberInARow = 0, maxValueInARow = 0;
            for(Map.Entry<Integer, Integer> entry: rankOccurences.entrySet()){
                switch (entry.getValue()){
                    case 0:
                        numberInARow = 0;
                        break;
                    case 1:
                        ++numberInARow;
                        if(entry.getKey() > maxNumberInARow){
                            maxNumberInARow = entry.getKey();
                        }
                        if(entry.getKey() > maxValueInARow){
                            maxValueInARow = entry.getValue();
                        }
                        value.pattern.add(new CardOccurence(entry.getKey(), entry.getValue()));
                    case 2:
                        ++numberOfPairs;
                        value.pattern.add(new CardOccurence(entry.getKey(), entry.getValue()));
                        break;
                    case 3:
                        ++numberOfTriples;
                        value.pattern.add(new CardOccurence(entry.getKey(), entry.getValue()));
                        break;
                    case 4:
                        ++numberOfFours;
                        value.pattern.add(new CardOccurence(entry.getKey(), entry.getValue()));
                        break;
                    default:
                        break;
                }
            }

            if(isFlush){
                if(numberInARow >= MIN_NUMBER_OF_CARDS){
                    if(maxValueInARow == Rank.ACE.getValue()){
                        value.combination = Combination.ROYAL_FLESH;
                    }
                }
            }
            if(maxNumberInARow == 4 && maxValueInARow == Rank.FIVE.getValue()){
                value.combination = Combination.STRAIGHT_WITH_LOW_ACE;
            }
        }
    }
}

/*

void poker::Hand::evaluate() const
{
    Value value;

    if(cards_.size() < NUM_CARDS_MAX)
    {
        value.combination = CombinationType::NO_HAND;
    }
    else
    {
        std::map<int, int> occurRank;
        for(int r{Card::LOWEST_CARD_VALUE}; r <= Card::HIGHEST_CARD_VALUE; ++r)
            occurRank[r] = 0;

        std::map<Card::Suit, int> occurSuit;
        for(int s{0}; s < Card::NUM_SUITS; ++s)
            occurSuit[static_cast<Card::Suit>(s)] = 0;

        for(const auto &card: cards_)
        {
            ++occurRank[card.getValue()];
            ++occurSuit[card.getSuit()];
        }

        bool isFlush{false};
        for(auto it = occurSuit.cbegin(); it != occurSuit.end(); ++it)
        {
            if(it->second == NUM_CARDS_MAX)
            {
                isFlush = true;
                break;
            }
        }

        int numPairs{0}, numTrips{0}, numFours{0}, numInARow{0},
            maxInARow{0}, maxValInARow{0};
        for(auto it = occurRank.cbegin(); it != occurRank.cend(); ++it)
        {
            switch (it->second)
            {
                case 0:
                    numInARow = 0;
                    break;
                case 1:
                    ++numInARow;
                    if(it->first > maxValInARow)
                        maxValInARow = it->first;
                    if(numInARow > maxInARow)
                        maxInARow = numInARow;
                    value.pattern.emplace_back(it->first,it->second);
                    break;
                case 2:
                    ++numPairs;
                    value.pattern.emplace_back(it->first,it->second);
                    break;
                case 3:
                    ++numTrips;
                    value.pattern.emplace_back(it->first,it->second);
                    break;
                case 4:
                    ++numFours;
                    value.pattern.emplace_back(it->first,it->second);
                    break;
                default:
                    break;
            }
        }

        if(maxInARow == NUM_CARDS_MAX - 1 &&
           maxValInARow == static_cast<int>(Card::Rank::FIVE))
        {
            value.combination = isFlush ?
                        CombinationType::STRAIGHT_FLUSH_WITH_LOW_ACE :
                        CombinationType::STRAIGHT_WITH_LOW_ACE;
        }
        else if(maxInARow == NUM_CARDS_MAX)
        {
            bool isAceMax = maxValInARow == Card::HIGHEST_CARD_VALUE;
            if(isFlush)
                value.combination = isAceMax ? CombinationType::ROYAL_FLASH :
                                               CombinationType::STRAIGHT_FLUSH;
            else
                value.combination = CombinationType::STRAIGHT;
        }
        else if(numFours > 0)
            value.combination = CombinationType::FOUR_OF_A_KIND;
        else if(numTrips > 0)
            value.combination = numPairs > 0 ? CombinationType::FULL_HAUS :
                                               CombinationType::THREE_OF_A_KIND;
        else if(numPairs == 2)
            value.combination = CombinationType::TWO_PAIR;
        else if(numPairs == 1)
            value.combination = CombinationType::PAIR;
        else if(isFlush)
            value.combination = CombinationType::FLUSH;
        else
            value.combination = CombinationType::HIGH_CARD;

        std::sort(value.pattern.begin(), value.pattern.end(),
                  [](const auto &occur1, const auto &occur2)
        {
            return occur1.numberOfAKind == occur2.numberOfAKind ?
                   occur1.cardValue > occur2.cardValue :
                   occur1.numberOfAKind > occur2.numberOfAKind;
        });
    }
    value_ = value;
}

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


////////////////////////////////////

/*
* class Hand
    {
    public:
        enum CombinationType
        {
            NO_HAND,
            HIGH_CARD,
            PAIR,
            TWO_PAIR,
            THREE_OF_A_KIND,
            STRAIGHT_WITH_LOW_ACE,
            STRAIGHT,
            FLUSH,
            FULL_HAUS,
            FOUR_OF_A_KIND,
            STRAIGHT_FLUSH_WITH_LOW_ACE,
            STRAIGHT_FLUSH,
            ROYAL_FLASH
        };

        struct Occurence
        {
            Occurence(int val, int num);
            int cardValue, numberOfAKind;
        };

        struct Value
        {
            CombinationType combination;
            std::vector<Occurence> pattern;
        };

        enum { NUM_CARDS_MAX = 5 };
        explicit Hand();
        inline auto getCardCount() const noexcept { return cards_.size(); }
        void changeCard(Card card, int pos);
        inline const Card& getGard(int pos) const { return cards_.at(pos); }
        inline const Card& operator[](int pos) const { return cards_.at(pos); }
        Value getValue() const;
        std::string toString() const;
        int findIndexByValue(int val) const;
        void addCard(const Card& card);
        void clear();
        void setLowAceAtFirstPlace();

    private:
        std::vector<Card> cards_;
        mutable Value value_;
        mutable bool isValueChanged_{true};
        void evaluate() const;
    };
    int compareHandValues(const Hand::Value &val1, const Hand::Value &val2);
    std::ostream &operator<<(std::ostream &os, const Hand &hand);
* */
