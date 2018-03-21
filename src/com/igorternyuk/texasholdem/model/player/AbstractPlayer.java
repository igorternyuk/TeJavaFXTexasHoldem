package com.igorternyuk.texasholdem.model.player;

import com.igorternyuk.texasholdem.model.Card;
import com.igorternyuk.texasholdem.model.Game;
import com.igorternyuk.texasholdem.model.Hand;

/**
 * Created by igor on 18.03.18.
 */
public abstract class AbstractPlayer {
    protected Game game;
    protected String name;
    protected PlayerType playerType;
    protected PlayerRole playerRole;
    protected PlayerStatus playerStatus = PlayerStatus.IN_PLAY;
    protected final Hand hand = new Hand();
    protected double money, initMoney;
    protected double betAmount = 0;

    public AbstractPlayer(final Game game, final String name, final double money, final PlayerType playerType){
        this.game = game;
        this.name = name;
        this.playerType = playerType;
        this.money = money;
        this.initMoney = money;
    }

    public Hand getHand(){
        return this.hand;
    }

    public void addCard(final Card card){
        this.hand.addCard(card);
    }

    public void clearCards(){
        this.hand.clearCards();
    }

    public abstract void bet();

    public double getMoneyBalance(){
        return this.money;
    }

    public void resetMoney(){
        this.money = initMoney;
    }

    public PlayerType getPlayerType() {
        return this.playerType;
    }

    public PlayerRole getPlayerRole() {
        return this.playerRole;
    }

    public void setPlayerRole(PlayerRole playerRole) {
        this.playerRole = playerRole;
    }

    public double getBetAmount() {
        return this.betAmount;
    }

    protected void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }

    public void ante(){
        game.payToPot(Game.ANTE);
        this.money -= Game.ANTE;
    }

    public void payToPot(){
        game.payToPot(this.betAmount);
        this.money -= this.betAmount;
    }

    public void takeGain(){
        if(this.playerStatus.equals(PlayerStatus.WON) || this.playerStatus.equals(PlayerStatus.TIE)){
            this.money += game.getGain();
        }
    }

    public void fold(){
        this.playerStatus = PlayerStatus.FOLDED;
    }

    public String getName(){
        return this.name;
    }
}
