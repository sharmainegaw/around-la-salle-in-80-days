package alsid.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

public class MainScreenController
{
    @FXML
    GridPane rootPane;

    /**
     * This method handles the "Start" button and is executed when the button is clicked.
     *
     * @param event
     * @throws Exception
     */
    @FXML
    protected void handleStartButtonAction (ActionEvent event) throws Exception
    {
        GridPane newPane = FXMLLoader.load(getClass().getResource("/alsid/view/AddPlayers.fxml"));
        rootPane.getChildren().setAll(newPane);
    }

    /**
     * This method handles the "Exit" button and is executed when the button is clicked.
     *
     * @param event
     */
    @FXML
    protected void handleExitButtonAction(ActionEvent event)
    {
        System.exit(0);
    }
}