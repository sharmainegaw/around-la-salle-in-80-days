package alsid.controller;

import alsid.model.game.Game;
import alsid.model.game.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddPlayersController
{
    private Game game = new Game();

    @FXML
    GridPane rootPane;

    @FXML
    ImageView imgView1, imgView2, imgView3, imgView4;

    @FXML
    TextField nameField1, nameField2, nameField3, nameField4;

    @FXML
    Player player1, player2, player3, player4;

    @FXML
    Text warning;


    /**
     * This method handles the "Start Game" button and is executed when the button is clicked.
     *
     * @param event
     * @throws Exception
     */
    @FXML
    protected void handleStartGameButtonAction (ActionEvent event) throws Exception
    {
        reset();

        // Check if any of the fields are left empty
        if((nameField1.isVisible() && nameField1.getText().isEmpty()) ||
                (nameField2.isVisible() && nameField2.getText().isEmpty()) ||
                (nameField3.isVisible() && nameField3.getText().isEmpty()) ||
                (nameField4.isVisible() && nameField4.getText().isEmpty()))
        {
            warning.setText("Please do not leave \nany fields empty!");
            warning.setVisible(true);
        }

        // Check character length of string, limit is set to 10
        else if((nameField1.isVisible() && nameField1.getText().length() > 10) ||
                (nameField2.isVisible() && nameField2.getText().length() > 10) ||
                (nameField3.isVisible() && nameField3.getText().length() > 10) ||
                (nameField4.isVisible() && nameField4.getText().length() > 10))
        {
            warning.setText("Names must not \nexceed 10 characters!");
            warning.setVisible(true);
        }

        // Check if any of the fields are equal to each other
        else if((nameField1.isVisible() && nameField1.getText().equals(nameField2.getText())) ||
                (nameField1.isVisible() && nameField1.getText().equals(nameField3.getText())) ||
                (nameField1.isVisible() && nameField1.getText().equals(nameField4.getText())) ||
                (nameField2.isVisible() && nameField2.getText().equals(nameField3.getText())) ||
                (nameField2.isVisible() && nameField2.getText().equals(nameField4.getText())) ||
                (nameField3.isVisible() && nameField3.getText().equals(nameField4.getText())))
        {
            warning.setText("Please use unique \nnames!");
            warning.setVisible(true);
        }

        // Proceed if all inputs are all valid
        else
        {
            if(!nameField1.getText().isEmpty())
            {
                player1 = new Player(nameField1.getText().trim(), 0);
            }

            if(!nameField2.getText().isEmpty())
            {
                player2 = new Player(nameField2.getText().trim(), 1);
            }

            if(!nameField3.getText().isEmpty())
            {
                player3 = new Player(nameField3.getText().trim(), 2);
            }

            if(!nameField4.getText().isEmpty())
            {
                player4 = new Player(nameField4.getText().trim(), 3);
            }

            if(player1 != null) game.addPlayer(player1);
            if(player2 != null) game.addPlayer(player2);
            if(player3 != null) game.addPlayer(player3);
            if(player4 != null) game.addPlayer(player4);

            // Check if there are more than 1 players
            // if there's only one player, clear players ArrayList
            if(game.getPlayers().size() < 2)
            {
                warning.setText("You need at least \ntwo players to play!");
                warning.setVisible(true);
                game.getPlayers().clear();
            }

            // If game is valid,
            // change controller to Game Screen Controller
            // and load the Game Screen FXML file
            else
            {
                //game.initBoard();
                //game.getChanceDeck().initChance(game);
                game.initGame();
                GameScreenController nextController = new GameScreenController(game);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/alsid/view/GameScreen.fxml"));
                loader.setController(nextController);
                HBox newPane = loader.load();
                rootPane.getChildren().setAll(newPane);
            }
        }

    }

    /**
     * This method handles the "+" button (add player button) and is executed when the button is clicked.
     * The next ImageView and TextField that aren't visible becomes visible.
     *
     * @param event
     */
    @FXML
    protected void handleAddPlayerButtonAction (ActionEvent event)
    {
        if(!nameField1.isVisible())
        {
            imgView1.setVisible(true);
            nameField1.setVisible(true);
        }
        else if(!nameField2.isVisible())
        {
            imgView2.setVisible(true);
            nameField2.setVisible(true);
        }
        else if(!nameField3.isVisible())
        {
            imgView3.setVisible(true);
            nameField3.setVisible(true);
        }
        else if(!nameField4.isVisible())
        {
            imgView4.setVisible(true);
            nameField4.setVisible(true);
        }
    }

    /**
     * This method handles the "-" button (remove player button) and is executed when the button is clicked.
     * The last ImageView and TextField that was not visible becomes visible.
     *
     * @param event
     */
    @FXML
    protected void handleRemovePlayerButtonAction (ActionEvent event)
    {
        if(nameField4.isVisible())
        {
            nameField4.clear();
            imgView4.setVisible(false);
            nameField4.setVisible(false);
        }
        else if(nameField3.isVisible())
        {
            nameField3.clear();
            imgView3.setVisible(false);
            nameField3.setVisible(false);
        }
        else if(nameField2.isVisible())
        {
            nameField2.clear();
            imgView2.setVisible(false);
            nameField2.setVisible(false);
        }
        else if(nameField1.isVisible())
        {
            nameField1.clear();
            imgView1.setVisible(false);
            nameField1.setVisible(false);
        }
    }

    /**
     * This method resets the data whenever there is a user input error.
     */
    private void reset()
    {
        warning.setText("");
        game.getPlayers().clear();
        player1 = null;
        player2 = null;
        player3 = null;
        player4 = null;
    }
}