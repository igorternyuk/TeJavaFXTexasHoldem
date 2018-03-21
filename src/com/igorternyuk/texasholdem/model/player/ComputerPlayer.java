package com.igorternyuk.texasholdem.model.player;

import java.util.Map;
import java.util.HashMap;
import com.igorternyuk.texasholdem.model.Game;
import com.igorternyuk.texasholdem.model.GameStatus;
import com.igorternyuk.texasholdem.model.Hand;

/**
 * Created by igor on 21.03.18.
 */
public class ComputerPlayer extends AbstractPlayer {
    private static final Map<Hand.Combination, Double> betCoefficients = createBetCoefficientsMap();
    public ComputerPlayer(Game game, String name, double money) {
        super(game, name, money, PlayerType.COMPUTER);
    }

    private static Map<Hand.Combination, Double> createBetCoefficientsMap(){
        Map<Hand.Combination, Double> map = new HashMap<>();
        map.put(Hand.Combination.NO_HAND, 0.0);
        map.put(Hand.Combination.HIGH_CARD, 1.0);
        map.put(Hand.Combination.PAIR, 1.3);
        map.put(Hand.Combination.TWO_PAIRS, 1.5);
        map.put(Hand.Combination.THREE_OF_A_KIND, 1.7);
        map.put(Hand.Combination.STRAIGHT_WITH_LOW_ACE, 1.9);
        map.put(Hand.Combination.STRAIGHT, 2.0);
        map.put(Hand.Combination.FLUSH, 2.5);
        map.put(Hand.Combination.FULL_HOUSE, 3.0);
        map.put(Hand.Combination.FOUR_OF_A_KIND, 4.0);
        map.put(Hand.Combination.STRAIGHT_FLUSH_WITH_LOW_ACE, 4.5);
        map.put(Hand.Combination.STRAIGHT_FLUSH, 5.0);
        map.put(Hand.Combination.ROYAL_FLESH, 10.0);
        return map;
    }

    @Override
    public void bet() {
        if(game.getGameStatus().equals(GameStatus.SHOWDOWN)) return;
        if(game.getGameStatus().equals(GameStatus.PREFLOP)){
            if(this.playerRole.equals(PlayerRole.SMALL_BLIND)){
                setBetAmount(this.game.getMinBet() / 2);
            } else if(this.playerRole.equals(PlayerRole.BIG_BLIND)){
                setBetAmount(this.game.getMinBet());
            }
        } else {
            setBetAmount(this.game.getMinBet() * betCoefficients.get(this.hand.getCombination()));
        }
        payToPot();
    }
}
