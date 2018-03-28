package com.igorternyuk.texasholdem;

import com.igorternyuk.texasholdem.model.Card;
import com.igorternyuk.texasholdem.model.Game;
import com.igorternyuk.texasholdem.model.GameStatus;
import com.igorternyuk.texasholdem.model.player.AbstractPlayer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

/**
 * Created by igor on 20.03.18.
 */
public class Renderer {
    private static final Font LARGE_FONT = new Font("Arial", 48);
    private static final Font SMALL_FONT = new Font("Arial", 18);
    private static final Color TABLE_COLOR = Color.rgb(29, 150, 80);
    private static final double TABLE_RECT_ARC_SIZE = 80;
    private static final double GAME_INFO_X = 275;
    private static final double GAME_INFO_Y = 250;
    private static ResourceManager rm = ResourceManager.getInstance();
    private static final int[] COMMUNITY_CARDS_X = {405, 480, 555, 630, 705};
    private static final int COMMUNITY_CARDS_Y = 335;
    private static final int[] PLAYER_CARDS_X = {750, 290, 20, 290, 750, 1000};
    private static final int[] PLAYER_CARDS_Y = {445, 445, 200, 20, 20, 200};
    private static final int[] PLAYER_INFO_X = {750, 290, 20, 290, 750, 1000};
    private static final int[] PLAYER_INFO_Y = {565, 565, 330, 140, 140, 330};

    static void renderTable(final GraphicsContext gc){
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.setFill(TABLE_COLOR);
        gc.fillRoundRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight(),
                TABLE_RECT_ARC_SIZE, TABLE_RECT_ARC_SIZE);
    }

    static void renderCommunityCards(final GraphicsContext gc, final Game game){
        List<Card> communityCards = game.getCommunityCards();
        for(int i = 0; i < communityCards.size(); ++i){
            gc.drawImage(rm.getCardImage(communityCards.get(i)), COMMUNITY_CARDS_X[i], COMMUNITY_CARDS_Y);
        }
    }

    static void renderPlayerCards(final GraphicsContext gc, final Game game){
        final List<AbstractPlayer> players = game.getPlayers();
        for(int i = 0; i < players.size(); ++i){
            final AbstractPlayer player = game.getPlayers().get(i);
            List<Card> holeCards = player.getHand().getHoleCards();
            for (int j = 0; j < holeCards.size(); ++j){
                if(game.getGameStatus().equals(GameStatus.SHOWDOWN)){
                    gc.drawImage(rm.getCardImage(holeCards.get(j)),
                            PLAYER_CARDS_X[i] + j * (ResourceManager.CARD_WIDTH + 2), PLAYER_CARDS_Y[i]);
                } else {
                    if(player.getPlayerType().isHuman()){
                        gc.drawImage(rm.getCardImage(holeCards.get(j)),
                                PLAYER_CARDS_X[i] + j * (ResourceManager.CARD_WIDTH + 2), PLAYER_CARDS_Y[i]);
                    } else {
                        gc.drawImage(rm.getCardBack(), PLAYER_CARDS_X[i] + j * (ResourceManager.CARD_WIDTH + 2),
                                PLAYER_CARDS_Y[i]);
                    }
                }
            }
        }
    }

    static void renderPlayersInfo(final GraphicsContext gc, final Game game){
        final List<AbstractPlayer> players = game.getPlayers();
        for(int i = 0; i < players.size(); ++i){
            gc.setFill(Color.BLUE);
            gc.setFont(SMALL_FONT);
            gc.fillText(players.get(i).toString(), PLAYER_INFO_X[i], PLAYER_INFO_Y[i]);
            switch (players.get(i).getPlayerRole()){
                case DEALER:
                    gc.setFill(Color.GREEN);
                    gc.fillOval(PLAYER_INFO_X[i] + 155, PLAYER_INFO_Y[i] - 90, 60, 60);
                    gc.setFill(Color.RED);
                    gc.setFont(LARGE_FONT);
                    gc.fillText("D", PLAYER_INFO_X[i] + 170, PLAYER_INFO_Y[i] - 44);
                    break;
                case SMALL_BLIND:
                case BIG_BLIND:
                    gc.setFill(Color.RED);
                    gc.fillText(players.get(i).getPlayerRole().toString(), PLAYER_INFO_X[i] + 150, PLAYER_INFO_Y[i]);
                    break;
                case REGULAR:
                    break;
            }
        }
    }

    static void renderGameInfo(final GraphicsContext gc, final Game game){
        gc.setFill(Color.YELLOW);
        gc.setFont(LARGE_FONT);
        gc.fillText(String.format("Round %d Stage - %s\nPot - %8.1f$ Min bet - %8.1f$", game.getRoundNumber(),
                game.getGameStatus(), game.getPot(), game.getCurrentMaxBet()), GAME_INFO_X, GAME_INFO_Y);
        if(game.isWaitingForHumanBet()) {
            gc.setFill(Color.RED);
            gc.setFont(SMALL_FONT);
            gc.fillText("It is your turn to bet", GAME_INFO_X, GAME_INFO_Y + 75);
        }
    }

}
