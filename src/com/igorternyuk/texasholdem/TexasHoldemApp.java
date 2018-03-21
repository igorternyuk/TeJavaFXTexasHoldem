package com.igorternyuk.texasholdem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TexasHoldemApp extends Application {
    private static final String TITLE_OF_PROGRAM = "JTexasHoldem";
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGTH = 700;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle(TITLE_OF_PROGRAM);
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGTH);
        Controller controller = fxmlLoader.getController();
        scene.setOnScroll(event -> {
            if(event.getDeltaY() > 0){
                controller.increaseHumanPlayerBet();
            } else if(event.getDeltaY() < 0){
                controller.decreaseHumanPlayerBet();
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
