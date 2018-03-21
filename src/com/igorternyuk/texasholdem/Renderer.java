package com.igorternyuk.texasholdem;

import com.igorternyuk.texasholdem.model.Card;
import com.igorternyuk.texasholdem.model.Game;
import com.igorternyuk.texasholdem.model.player.AbstractPlayer;
import com.igorternyuk.texasholdem.model.player.PlayerRole;
import com.sun.istack.internal.NotNull;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

/**
 * Created by igor on 20.03.18.
 */
public class Renderer {
    private static final Font LARGE_FONT = new Font("Arial", 48);
    private static final Font SMALL_FONT = new Font("Arial", 22);
    private static final Color TABLE_COLOR = Color.rgb(29, 150, 80);
    private static final double TABLE_RECT_ARC_SIZE = 80;
    private static final double GAME_INFO_X = 350;
    private static final double GAME_INFO_Y = 240;
    private static ResourceManager rm = ResourceManager.getInstance();
    private static final int[] COMMUNITY_CARDS_X = {200, 275, 350, 425, 500};
    private static final int[] PLAYER_CARDS_X = {750, 290, 20, 290, 750, 1000};
    private static final int[] PLAYER_CARDS_Y = {455, 455, 200, 20, 20, 200};
    private static final int[] PLAYER_INFO_X = {750, 290, 20, 290, 750, 1000};
    private static final int[] PLAYER_INFO_Y = {580, 580, 330, 140, 140, 330};
    public static void renderTable(@NotNull final GraphicsContext gc){
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.setFill(TABLE_COLOR);
        gc.fillRoundRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight(),
                TABLE_RECT_ARC_SIZE, TABLE_RECT_ARC_SIZE);
    }

    public static void renderCommunityCards(@NotNull final GraphicsContext gc, @NotNull final Game game){
        List<Card> communityCards = game.getCommunityCards();
        for(int i = 0; i < communityCards.size(); ++i){
            gc.drawImage(rm.getCardImage(communityCards.get(i)), COMMUNITY_CARDS_X[i], 200);
        }
    }

    public static void renderPlayerCards(@NotNull final GraphicsContext gc, @NotNull final Game game){
        final List<AbstractPlayer> players = game.getPlayers();
        for(int i = 0; i < players.size(); ++i){
            final AbstractPlayer player = game.getPlayers().get(i);
            List<Card> holeCards = player.getHand().getHoleCards();
            for (int j = 0; j < holeCards.size(); ++j){
                gc.drawImage(rm.getCardImage(holeCards.get(j)),
                        PLAYER_CARDS_X[i] + j * (ResourceManager.CARD_WIDTH + 2), PLAYER_CARDS_Y[i]);
                /*if(game.getGameStatus().equals(GameStatus.SHOWDOWN)){
                    gc.drawImage(rm.getCardImage(holeCards.get(i)),
                            PLAYER_CARDS_X[i] + j * (ResourceManager.CARD_WIDTH + 2), PLAYER_CARDS_Y[i]);
                } else {
                    if(player.getPlayerType().isHuman()){
                        gc.drawImage(rm.getCardImage(holeCards.get(i)),
                                PLAYER_CARDS_X[i] + j * (ResourceManager.CARD_WIDTH + 2), PLAYER_CARDS_Y[i]);
                    } else {
                        gc.drawImage(rm.getCardBack(), PLAYER_CARDS_X[i] + j * (ResourceManager.CARD_WIDTH + 2),
                                PLAYER_CARDS_Y[i]);
                    }
                }*/
            }
        }
    }

    public static void renderPlayersInfo(@NotNull final GraphicsContext gc, @NotNull final Game game){
        final List<AbstractPlayer> players = game.getPlayers();
        for(int i = 0; i < players.size(); ++i){
            gc.setFill(Color.BLUE);
            gc.setFont(SMALL_FONT);
            gc.fillText(players.get(i).toString(), PLAYER_INFO_X[i], PLAYER_INFO_Y[i]);
            switch (players.get(i).getPlayerRole()){
                case DEALER:
                    gc.setFill(Color.GREEN);
                    gc.fillOval(PLAYER_INFO_X[i] + 170, PLAYER_INFO_Y[i] - 90, 60, 60);
                    gc.setFill(Color.RED);
                    gc.setFont(LARGE_FONT);
                    gc.fillText("D", PLAYER_INFO_X[i] + 186, PLAYER_INFO_Y[i] - 48);
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

    public static void renderGameInfo(@NotNull final GraphicsContext gc, @NotNull final Game game){
        gc.setFill(Color.YELLOW);
        gc.setFont(LARGE_FONT);
        gc.fillText("Texas holdem round " + game.getRoundNumber() + "\nThe pot is " + game.getPot(),
                GAME_INFO_X, GAME_INFO_Y);
    }

}
