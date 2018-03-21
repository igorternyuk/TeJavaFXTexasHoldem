package com.igorternyuk.texasholdem.model;

import com.igorternyuk.texasholdem.model.player.AbstractPlayer;
import com.igorternyuk.texasholdem.model.player.ComputerPlayer;
import com.igorternyuk.texasholdem.model.player.HumanPlayer;
import com.igorternyuk.texasholdem.model.player.PlayerRole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by igor on 20.03.18.
 */
public class Game {
    public static final double ANTE = 13;
    public static final double MIN_BET = 20;
    private static final Map<GameStatus, Double> MIN_BET_MAP = createMinBetMap();
    private static final int MAX_NUMBER_OF_PLAYERS = 6;
    private static final int INITIAL_PLAYER_CARDS_NUMBER = 2;
    private static final double PLAYER_INITIAL_MONEY = 1000;
    private GameStatus gameStatus;
    private int roundNumber = 0;
    private HumanPlayer humanPlayer;
    private List<AbstractPlayer> players = new ArrayList<>(MAX_NUMBER_OF_PLAYERS);
    private final Deck deck = new Deck();
    private final List<Card> communityCards = new ArrayList<>(5);
    private int dealerIndex = 0, smallBlindIndex = 1, bigBlindIndex = 2;
    int[] playerOrders = new int[MAX_NUMBER_OF_PLAYERS];
    private double pot = 0;
    private List<AbstractPlayer> afterGameRanking = new ArrayList<>(MAX_NUMBER_OF_PLAYERS);
    private double gain;
    private int nextBetPlayerIndex = 0;
    private boolean isWaitingForHumanBet = false;
    private double maxBet = 0;

    public Game(){
        prepareNewGame();
        this.humanPlayer = new HumanPlayer(this, "You", PLAYER_INITIAL_MONEY);
        this.players.add(this.humanPlayer);
        for(int i = 1; i < MAX_NUMBER_OF_PLAYERS; ++i){
            this.players.add(new ComputerPlayer(this, "Computer" + i, PLAYER_INITIAL_MONEY));
        }
        prepareNewGame();
    }

    private static Map<GameStatus, Double> createMinBetMap() {
        Map<GameStatus, Double> map = new HashMap<>();
        map.put(GameStatus.PREFLOP, 40.0);
        map.put(GameStatus.FLOP, 80.0);
        map.put(GameStatus.TURN, 80.0);
        map.put(GameStatus.RIVER, 80.0);
        return map;
    }

    public void prepareNewGame(){
        this.roundNumber = 0;
        this.players.forEach(AbstractPlayer::resetMoney);
        startNewRound();
    }

    public void startNewRound(){
        this.gameStatus = GameStatus.PREFLOP;
        this.deck.clear();
        this.deck.populate();
        this.deck.shuffle();
        this.communityCards.clear();
        dealCardsToPlayers();
        this.players.forEach(AbstractPlayer::ante);
        preflop();
        ++this.roundNumber;
    }

    private void updateDealerAndBlinds(){
        if(this.roundNumber == 0){
            dealerIndex = 0;
            smallBlindIndex = 1;
            bigBlindIndex = 2;
            this.humanPlayer.setPlayerRole(PlayerRole.DEALER);
            this.players.get(smallBlindIndex).setPlayerRole(PlayerRole.SMALL_BLIND);
            this.players.get(bigBlindIndex).setPlayerRole(PlayerRole.BIG_BLIND);
        } else {
            ++this.dealerIndex;
            this.dealerIndex %= MAX_NUMBER_OF_PLAYERS;
            this.smallBlindIndex = this.dealerIndex + 1;
            this.smallBlindIndex %= MAX_NUMBER_OF_PLAYERS;
            this.bigBlindIndex = this.smallBlindIndex + 1;
            this.bigBlindIndex %= MAX_NUMBER_OF_PLAYERS;
            this.players.forEach(player -> player.setPlayerRole(PlayerRole.REGULAR));
            this.players.get(this.dealerIndex).setPlayerRole(PlayerRole.DEALER);
            this.players.get(this.smallBlindIndex).setPlayerRole(PlayerRole.SMALL_BLIND);
            this.players.get(this.bigBlindIndex).setPlayerRole(PlayerRole.BIG_BLIND);
            rotateOrderIndices();
        }
    }

    private void rotateOrderIndices(){
        int last = playerOrders[playerOrders.length - 1];
        for(int i = playerOrders.length - 1; i > 0; --i){
            playerOrders[i] = playerOrders[i - 1];
        }
        playerOrders[0] = last;
    }


    private void dealCardsToPlayers(){
        this.players.forEach(player -> {
            for(int i = 0; i < INITIAL_PLAYER_CARDS_NUMBER; ++i) {
                this.deck.deal(player);
            }
        });
    }

    public void preflop(){
        updateDealerAndBlinds();
        this.players.get(smallBlindIndex).bet();
        this.players.get(bigBlindIndex).bet();
        int nextAfterBigBlind = this.bigBlindIndex + 1;
        nextAfterBigBlind %= MAX_NUMBER_OF_PLAYERS;
        double maxBet = 0;


        for(int i = 0; i < playerOrders.length; ++i){
            if(this.players.get(i).getPlayerType().isHuman()){
                this.isWaitingForHumanBet = true;
                if((i + 1) < playerOrders.length){
                    this.nextBetPlayerIndex = i + 1;
                }
            }
            this.players.get(i).bet();
            if(this.players.get(i).getBetAmount() > maxBet){
                maxBet = this.players.get(i).getBetAmount();
            }
        }
    }

    public void humanBet(){
        this.isWaitingForHumanBet = false;
    }

    public void flop(){
        for(int i = 0; i < 3; ++i){
            communityCards.add(this.deck.top());
        }
    }

    public void turn(){

    }

    public void river(){

    }

    public void payToPot(final double money){
        this.pot += money;
    }

    public double getMinBet(){
        return !this.gameStatus.equals(GameStatus.SHOWDOWN) ? MIN_BET_MAP.get(this.gameStatus) : 0.0;
    }

    public GameStatus getGameStatus() {
        return this.gameStatus;
    }

    public int getRoundNumber() {
        return this.roundNumber;
    }

    public List<AbstractPlayer> getPlayers() {
        return this.players;
    }

    public List<Card> getCommunityCards() {
        return Collections.unmodifiableList(this.communityCards);
    }

    public int getDealerIndex() {
        return this.dealerIndex;
    }

    public int getBigBlindIndex() {
        return this.bigBlindIndex;
    }

    public int getSmallBlindIndex() {
        return this.smallBlindIndex;
    }

    public double getPot() {
        return this.pot;
    }

    private void nextRound(){

    }

    public void showDown(){

    }

    public double getGain() {
        return this.gain;
    }

    public void fold(){

    }
}
