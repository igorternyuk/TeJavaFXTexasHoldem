package com.igorternyuk.texasholdem.model.player;

import java.util.Map;
import java.util.HashMap;
import com.igorternyuk.texasholdem.model.Game;
import com.igorternyuk.texasholdem.model.GameStatus;
import com.igorternyuk.texasholdem.model.Hand;
import static com.igorternyuk.texasholdem.model.Hand.Combination;

/**
 * Created by igor on 21.03.18.
 */
public class ComputerPlayer extends AbstractPlayer {
    private static final Map<Combination, Double> betCoefficients = createBetCoefficientsMap();
    public ComputerPlayer(Game game, String name, double money) {
        super(game, name, money, PlayerType.COMPUTER);
    }

    private static Map<Combination, Double> createBetCoefficientsMap(){
        Map<Combination, Double> map = new HashMap<>();
        map.put(Combination.NO_HAND, 0.0);
        map.put(Combination.HIGH_CARD, 1.0);
        map.put(Combination.PAIR, 1.3);
        map.put(Combination.TWO_PAIRS, 1.5);
        map.put(Combination.THREE_OF_A_KIND, 1.7);
        map.put(Combination.STRAIGHT_WITH_LOW_ACE, 1.9);
        map.put(Combination.STRAIGHT, 2.0);
        map.put(Combination.FLUSH, 2.5);
        map.put(Combination.FULL_HOUSE, 3.0);
        map.put(Combination.FOUR_OF_A_KIND, 4.0);
        map.put(Combination.STRAIGHT_FLUSH_WITH_LOW_ACE, 4.5);
        map.put(Combination.STRAIGHT_FLUSH, 5.0);
        map.put(Combination.ROYAL_FLESH, 10.0);
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
        if(this.betAmount < this.game.getMinBet()){
            this.betAmount = this.game.getMinBet();
        }
        payToPot();
    }

    @Override
    public String toString(){
        if(this.game.getGameStatus().equals(GameStatus.SHOWDOWN)) {
            return String.format("%s\n%s\nMoney:%8.3f$ %s", this.getName(), this.getHand(), this.getMoneyBalance(),
                    this.getPlayerStatus());
        } else {
            return String.format("%s\nMoney:%8.3f$", this.getName(), this.getMoneyBalance());
        }
    }
}
