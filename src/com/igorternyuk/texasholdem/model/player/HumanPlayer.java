package com.igorternyuk.texasholdem.model.player;

import com.igorternyuk.texasholdem.model.Game;
import com.igorternyuk.texasholdem.model.GameStatus;

/**
 * Created by igor on 21.03.18.
 */
public class HumanPlayer extends AbstractPlayer{
    private static final int BET_INCREMENT = 10;
    public HumanPlayer(final Game game, final String name, final double money) {
        super(game, name, money, PlayerType.HUMAN);
    }

    public void increaseBet(){
        this.betAmount += BET_INCREMENT;
    }

    public void decreaseBet(){
        this.betAmount -= BET_INCREMENT;
    }

    @Override
    public void bet() {
        if(game.getGameStatus().equals(GameStatus.SHOWDOWN)) return;
        if(game.getGameStatus().equals(GameStatus.PREFLOP)){
            if(this.playerRole.equals(PlayerRole.SMALL_BLIND)){
                if(this.betAmount < this.game.getMinBet() / 2){
                    this.betAmount = this.game.getMinBet() / 2;
                }
            } else if(this.playerRole.equals(PlayerRole.BIG_BLIND)){
                if(this.betAmount < this.game.getMinBet()){
                    this.betAmount = this.game.getMinBet();
                }
            }
        }
        if(this.betAmount < this.game.getMinBet()){
            this.betAmount = this.game.getMinBet();
        }
        payToPot();
    }
}
