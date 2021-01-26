package alsid.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.media.*;
import javafx.util.Duration;
import java.io.File;

public class MainScreen extends Application
{
    private static Stage    stage;

    // MediaPlayer for playing music
    private MediaPlayer     mp;

    @Override
    public void start (Stage stage) throws Exception
    {
        MainScreen.stage = stage;

        // Load FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/alsid/view/MainScreen.fxml"));
        Scene scene = new Scene(root, 1250, 650);

        // Load the background image
        root.setStyle("-fx-background-image: url('/alsid/assets/mainscreen-background.png'); " +
                    "-fx-background-size: cover;");

        // Load the background music
        Media sound = new Media(new File("cityintheclouds.mp3").toURI().toString());
        mp = new MediaPlayer(sound);
        mp.setOnEndOfMedia(new Runnable()
        {
            public void run() {
                mp.seek(Duration.ZERO);
            }
        });
        mp.play();

        //Load the custom font
        Font.loadFont(
                MainScreen.class.getResource("/alsid/assets/DTM-Mono.ttf").toExternalForm(),
                10
        );

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Around La Salle in 80 days");
        stage.show();
    }

    public static void main (String[] args)
    {
        launch(args);
    }
}