package alsid.controller;

import alsid.model.game.Game;
import alsid.model.game.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;

public class GameEndController
{
    ArrayList<Player>       rankedList;
    ArrayList<ImageView>    boxes;
    ArrayList<ImageView>    sprites;
    ArrayList<Label>        playerDisplays;

    @FXML
    GridPane board;

    @FXML
    ImageView box1, box2, box3, box4;

    @FXML
    ImageView sprite1, sprite2, sprite3, sprite4;

    @FXML
    Label playerDisplay1, playerDisplay2, playerDisplay3, playerDisplay4;

    @FXML

    private Game game;

    public GameEndController (Game game)
    {
        this.game = game;
    }

    @FXML
    public void initialize()
    {
        rankedList      = new ArrayList<>();
        boxes           = new ArrayList<>();
        sprites         = new ArrayList<>();
        playerDisplays  = new ArrayList<>();

        rankedList = game.getPlayers();

        boxes.addAll(Arrays.asList(box1, box2, box3, box4));
        sprites.addAll(Arrays.asList(sprite1, sprite2, sprite3, sprite4));
        playerDisplays.addAll(Arrays.asList(playerDisplay1, playerDisplay2, playerDisplay3, playerDisplay4));

        for(int i = 0; i < rankedList.size(); i++)
        {
            switch(i)
            {
                case 0: playerDisplays.get(i).setText("1ST PLACE"); break;
                case 1: playerDisplays.get(i).setText("2ND PLACE"); break;
                case 2: playerDisplays.get(i).setText("3RD PLACE"); break;
                case 3: playerDisplays.get(i).setText("4TH PLACE"); break;
            }

            playerDisplays.get(i).setText(playerDisplays.get(i).getText() + "\n" + rankedList.get(i).getName());

            switch(rankedList.get(i).getNumber())
            {
                case 0: sprites.get(i).setImage(new Image("/alsid/assets/sprite-lasalle-green.png")); break;
                case 1: sprites.get(i).setImage(new Image("/alsid/assets/sprite-lasalle-purple.png")); break;
                case 2: sprites.get(i).setImage(new Image("/alsid/assets/sprite-lasalle-red.png")); break;
                case 3: sprites.get(i).setImage(new Image("/alsid/assets/sprite-lasalle-yellow.png")); break;
            }

            playerDisplays.get(i).setVisible(true);
            sprites.get(i).setVisible(true);
            sprites.get(i).setTranslateX(25);
            boxes.get(i).setVisible(true);
        }
    }
}
