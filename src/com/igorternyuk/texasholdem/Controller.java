package com.igorternyuk.texasholdem;

import com.igorternyuk.texasholdem.model.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    private Game game = new Game();
    @FXML
    private Canvas canvas;
    private GraphicsContext gc;

    public void increaseHumanPlayerBet(){
        this.game.increaseHumanPlayerBetAmount();
    }

    public void decreaseHumanPlayerBet(){
        this.game.decreaseHumanPlayerBetAmount();
    }

    public void onBtnNewGameClicked(ActionEvent event) {
        this.game.prepareNewGame();
    }

    public void onBtnNewRoundClicked(ActionEvent event) {
        this.game.startNewRound();
    }

    public void onBtnBetClicked(ActionEvent event) {
        this.game.humanPlayerBet();
    }

    public void onBtnReraiseClicked(ActionEvent event) {
        this.game.humanPlayerReraise();
    }

    public void onBtnFoldClicked(ActionEvent event) {
    }

    public void onBtnNextStepClicked(ActionEvent event) {
        this.game.nextStep();
    }

    public void onBtnExitClicked(ActionEvent event) {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to exit?",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirm exit, please");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            Platform.exit();
            System.exit(0);
        } else {
            alert.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.gc = canvas.getGraphicsContext2D();
    }
}
