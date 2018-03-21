package com.igorternyuk.texasholdem.model;

import com.igorternyuk.texasholdem.model.player.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by igor on 20.03.18.
 */
public class Game {
    public static final double ANTE = 13;
    private static final int NUMBER_OF_COMMUNITY_CARDS = 5;
    private static final Map<GameStatus, Double> MIN_BET_MAP = createMinBetMap();
    private static final int MAX_NUMBER_OF_PLAYERS = 6;
    private static final int INITIAL_PLAYER_CARDS_NUMBER = 2;
    private static final double PLAYER_INITIAL_MONEY = 1000;
    private GameStatus gameStatus;
    private int roundNumber = 0;
    private HumanPlayer humanPlayer;
    private List<AbstractPlayer> players = new ArrayList<>(MAX_NUMBER_OF_PLAYERS);
    private final Deck deck = new Deck();
    private final List<Card> communityCards = new ArrayList<>(NUMBER_OF_COMMUNITY_CARDS);
    private int dealerIndex = 0, smallBlindIndex = 0, bigBlindIndex = 0, humanPlayerIndexInTheQueue = 0;
    private int[] playersQueue = new int[MAX_NUMBER_OF_PLAYERS];
    private double pot = 0;
    private boolean waitingForHumanBet = false;
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
        this.players.forEach(AbstractPlayer::clearCards);
        dealCardsToPlayers();
        updateDealerAndBlinds();
        this.pot = 0.0;
        this.players.forEach(AbstractPlayer::ante);
        this.maxBet = MIN_BET_MAP.get(this.gameStatus);
        beforeHumanPlayerBets();
        ++this.roundNumber;
    }

    private void dealCardsToPlayers(){
        this.players.forEach(player -> {
            for(int i = 0; i < INITIAL_PLAYER_CARDS_NUMBER; ++i) {
                this.deck.deal(player);
            }
        });
    }

    private void updateDealerAndBlinds(){
        if(this.roundNumber == 0){
            smallBlindIndex = dealerIndex + 1;
            bigBlindIndex = smallBlindIndex + 1;
            this.humanPlayer.setPlayerRole(PlayerRole.DEALER);
            this.players.get(smallBlindIndex).setPlayerRole(PlayerRole.SMALL_BLIND);
            this.players.get(bigBlindIndex).setPlayerRole(PlayerRole.BIG_BLIND);
            final int numberOfSpecialRoles = PlayerRole.values().length - 1;
            for(int i = 0; i < MAX_NUMBER_OF_PLAYERS; ++i){
                this.playersQueue[i] = i + (i < numberOfSpecialRoles ? numberOfSpecialRoles : -numberOfSpecialRoles);
            }
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
        this.humanPlayerIndexInTheQueue = findHumanPlayerOrder();
    }

    private void rotateOrderIndices(){
        int last = playersQueue[playersQueue.length - 1];
        for(int i = playersQueue.length - 1; i > 0; --i){
            playersQueue[i] = playersQueue[i - 1];
        }
        playersQueue[0] = last;
    }

    private int findHumanPlayerOrder(){
        for (int i : this.playersQueue) {
            final int currIndex = this.playersQueue[i];
            if(this.players.get(currIndex).getPlayerType().isHuman()){
                return i;
            }
        }
        return -1;
    }

    private void beforeHumanPlayerBets(){
        for(int i = 0; i < humanPlayerIndexInTheQueue; ++i){
            final AbstractPlayer currPlayer = this.players.get(this.playersQueue[i]);
            currPlayer.bet();
            if(this.maxBet < currPlayer.getBetAmount()){
                this.maxBet = currPlayer.getBetAmount();
            }
        }
        waitingForHumanBet = true;
    }

    public void humanPlayerBet(){
        if(waitingForHumanBet){
            this.humanPlayer.bet();
            if(this.maxBet < this.humanPlayer.getBetAmount()){
                this.maxBet = this.humanPlayer.getBetAmount();
            }
            waitingForHumanBet = false;
            afterHumanPlayerBets();
            callForAllComputerPlayers();
        }
    }

    public boolean isWaitingForHumanBet(){
        return this.waitingForHumanBet;
    }

    private void afterHumanPlayerBets(){
        for(int i = humanPlayerIndexInTheQueue + 1; i < this.playersQueue.length; ++i){
            final AbstractPlayer currPlayer = this.players.get(this.playersQueue[i]);
            currPlayer.bet();
            if(this.maxBet < currPlayer.getBetAmount()){
                this.maxBet = currPlayer.getBetAmount();
            }
        }
    }

    private void callForAllComputerPlayers(){
        this.players.stream().filter(player -> player.getPlayerType().isComputer()).forEach(player -> {
            if(player.getBetAmount() < this.maxBet){
                player.setBetAmount(this.maxBet);
                player.bet();
            }
        });
    }

    public void increaseHumanPlayerBetAmount(){
        this.humanPlayer.increaseBet();
    }

    public void decreaseHumanPlayerBetAmount(){
        this.humanPlayer.decreaseBet();
    }

    public void humanPlayerReraise(){
        if(this.humanPlayer.getBetAmount() > this.maxBet){
            this.humanPlayer.bet();
            this.maxBet = this.humanPlayer.getBetAmount();
            callForAllComputerPlayers();
        }
    }

    public void nextStep(){
        switch (this.gameStatus){
            case PREFLOP:
                for(int i = 0; i < 3; ++i){
                    communityCards.add(this.deck.top());
                }
                this.gameStatus = GameStatus.FLOP;
                beforeHumanPlayerBets();
                break;
            case FLOP:
                communityCards.add(this.deck.top());
                this.gameStatus = GameStatus.TURN;
                beforeHumanPlayerBets();
                break;
            case TURN:
                communityCards.add(this.deck.top());
                this.gameStatus = GameStatus.RIVER;
                beforeHumanPlayerBets();
                break;
            case RIVER:
                this.gameStatus = GameStatus.SHOWDOWN;
                determineWinners();
                break;
            case SHOWDOWN:
                break;
        }
        this.maxBet = MIN_BET_MAP.get(this.gameStatus);
    }

    //TODO human player fold case
    public void humanPlayerFold(){
        this.humanPlayer.fold();
        int numberOfRemainingSteps = 0;
        switch (this.gameStatus){
            case PREFLOP:
                numberOfRemainingSteps = 3;
                break;
            case FLOP:
                numberOfRemainingSteps = 2;
                break;
            case TURN:
                numberOfRemainingSteps = 1;
                break;
            case RIVER:
            case SHOWDOWN:
                default:
                numberOfRemainingSteps = 0;
                break;
        }
        for(int i = 0; i < numberOfRemainingSteps; ++i){
            List<AbstractPlayer> remainingPlayers = this.players.stream()
                    .filter(player -> player.getPlayerStatus().equals(PlayerStatus.IN_PLAY)).collect(Collectors.toList());
            remainingPlayers.forEach(player -> {
                player.bet();
                if(this.maxBet < player.getBetAmount()){
                    this.maxBet = player.getBetAmount();
                }
            });
            callForAllComputerPlayers();
            nextStep();
        }
    }

    private void determineWinners(){
        List<AbstractPlayer> remainingPlayers = this.players.stream()
                .filter(player -> player.getPlayerStatus().equals(PlayerStatus.IN_PLAY)).collect(Collectors.toList());
        remainingPlayers.sort(Comparator.comparing(AbstractPlayer::getHand).reversed());
        int numberOfTiedPlayers = 0;
        for(int i = 0; i < remainingPlayers.size() - 1; ++i){
            final Hand.Combination currPlayerCombination = remainingPlayers.get(i).getHand().getCombination();
            final Hand.Combination nextPlayerCombination = remainingPlayers.get(i + 1).getHand().getCombination();
            if(currPlayerCombination.equals(nextPlayerCombination)){
                ++numberOfTiedPlayers;
            } else {
                break;
            }
        }
        if(numberOfTiedPlayers == 0){
            final AbstractPlayer winner = remainingPlayers.get(0);
            winner.setPlayerStatus(PlayerStatus.WON);
            winner.takeGain(this.pot);
            remainingPlayers.subList(1, remainingPlayers.size())
                    .forEach(player -> player.setPlayerStatus(PlayerStatus.LOST));
        } else {
            final double splittedPot = this.pot / numberOfTiedPlayers;
            for(int i = 0; i < remainingPlayers.size(); ++i){
                if(i < numberOfTiedPlayers) {
                    remainingPlayers.get(i).setPlayerStatus(PlayerStatus.TIE);
                    remainingPlayers.get(i).takeGain(splittedPot);
                } else {
                    remainingPlayers.get(i).setPlayerStatus(PlayerStatus.LOST);
                }
            }
        }
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

    public double getGain() {
        return this.pot;
    }
}
