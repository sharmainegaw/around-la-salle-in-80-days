package alsid.controller;

import alsid.model.asset.*;
import alsid.model.chance.*;
import alsid.model.game.*;
import alsid.model.space.*;

import java.io.IOException;
import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class GameScreenController
{
    private Game game;
    private int[][] index;

    //ArrayLists to group the elements in the GUI
    private ArrayList<Label>        spaceLabels;
    private ArrayList<Button>       spaceButtons;
    private ArrayList<ImageView>    sprites;
    private ArrayList<Label>        playerDisplays;
    private MenuBar[][]             menus;
    private ArrayList<ImageView>    boxes;

    private Chance  currChance;
    private Player  currPlayer;
    private Space   currSpace;
    private Space   offeredSpace;
    private int     currTurn;
    private int     oldPosition;
    private int     newPosition;
    private int     diceRoll;

    private int     swapposition1   = -1;
    private int     swapposition2   = -1;
    private int     offerPosition   = 0;
    private boolean isSwapping      = true;
    private boolean isTrading       = false;
    private boolean isChoosingProp  = false;
    private boolean ChanceEvent     = false;

    @FXML
    private GridPane board;

    @FXML
    private HBox mainHBox;

    //Labels used to display the board spaces.
    @FXML
    private Label str0, str1, str2, str3, str4, str5, str6, str7,
            str8, str9, str10, str11, str12, str13, str14, str15,
            str16, str17, str18, str19, str20, str21, str22, str23,
            str24, str25, str26, str27, str28, str29, str30, str31;

    @FXML
    private Button space0, space1, space2, space3, space4, space5, space6, space7,
            space8, space9, space10, space11, space12, space13, space14, space15,
            space16, space17, space18, space19, space20, space21, space22, space23,
            space24, space25, space26, space27, space28, space29, space30, space31;

    //ImageView for displaying the player sprites.
    @FXML
    private ImageView sprite1, sprite2, sprite3, sprite4;

    //Label for displaying messages/narrations in-game.
    @FXML
    private Label message, spaceInfoDisplay, bankMoney;

    @FXML
    private ImageView spaceInfoBox;

    //Label for displaying info about the players (name and amount of money left).
    @FXML
    private Label player1display, player2display, player3display, player4display;

    @FXML
    private MenuBar menu1top, menu1bottom, menu2top, menu2bottom,
            menu3top, menu3bottom, menu4top, menu4bottom;

    @FXML
    private ImageView box1, box2, box3, box4;

    //Button for rolling the dice and starting the game (after swapping is finished).
    @FXML
    private Button rollDice, startGame, endGame, shuffle;

    //Button for purchasing an asset, paying rent, developing a property, and ending the turn.
    @FXML
    private Button purchase, payRent, trade, develop, cancelTrade,
            agree, pass, drawChance, payTax;



    //for demo testing
    @FXML
    private TextField diceNumber;

    @FXML
    private Button bankrupt;



    public GameScreenController(Game game)
    {
        this.game = game;
    }

    @FXML
    public void initialize()
    {
        spaceLabels     = new ArrayList<>();
        spaceButtons    = new ArrayList<>();
        sprites         = new ArrayList<>();
        playerDisplays  = new ArrayList<>();
        menus           = new MenuBar[4][2];
        boxes           = new ArrayList<>();

        spaceLabels.addAll(Arrays.asList(str0, str1, str2, str3, str4, str5, str6, str7,
                str8, str9, str10, str11, str12, str13, str14, str15,
                str16, str17, str18, str19, str20, str21, str22, str23,
                str24, str25, str26, str27, str28, str29, str30, str31));

        spaceButtons.addAll(Arrays.asList(space0, space1, space2, space3, space4, space5, space6, space7,
                space8, space9, space10, space11, space12, space13, space14, space15,
                space16, space17, space18, space19, space20, space21, space22, space23,
                space24, space25, space26, space27, space28, space29, space30, space31));

        sprites.addAll(Arrays.asList(sprite1, sprite2, sprite3, sprite4));

        playerDisplays.addAll(Arrays.asList(player1display, player2display, player3display, player4display));

        menus[0][0] = menu1top;
        menus[0][1] = menu1bottom;
        menus[1][0] = menu2top;
        menus[1][1] = menu2bottom;
        menus[2][0] = menu3top;
        menus[2][1] = menu3bottom;
        menus[3][0] = menu4top;
        menus[3][1] = menu4bottom;

        boxes.addAll(Arrays.asList(box1, box2, box3, box4));

        for(int i = 0; i <= 31; i++)
        {
            ImageView imgView = game.getBoard().getSpaces().get(i).getImage();
            imgView.setFitWidth(70.0);
            imgView.setFitHeight(70.0);
            imgView.setPickOnBounds(true);
            imgView.setPreserveRatio(true);

            spaceButtons.get(i).setPadding(Insets.EMPTY);
            spaceButtons.get(i).setGraphic(imgView);
            spaceLabels.get(i).setText(game.getBoard().getSpaces().get(i).toString());

            GridPane.setHalignment(spaceLabels.get(i), HPos.CENTER);

            game.getBoard().getSpaces().get(i).setPosition(i);

            if(i == 0 || i == 8 || i == 16 || i == 24)
            {
                spaceButtons.get(i).setDisable(true);
            }
        }

        index = new int[32][2];
        for(int j = 0; j < 8; j++)
        {
            index[j] = new int[]{0, j};
            index[j + 9] = new int[]{j + 1, 8};
            index[j + 16] = new int[]{8, 8 - j};
            index[j + 24] = new int[]{8 - j, 0};
        }
        index[8] = new int[]{0, 8};

        message.setText("Welcome to Around La Salle in 80 Days! Please set up the board. Click on two spaces to swap them, or shuffle.");

        updatePlayers();

        sprite1.setImage(new Image("/alsid/assets/sprite-lasalle-green.png"));
        sprite2.setImage(new Image("/alsid/assets/sprite-lasalle-purple.png"));
        sprite3.setImage(new Image("/alsid/assets/sprite-lasalle-red.png"));
        sprite4.setImage(new Image("/alsid/assets/sprite-lasalle-yellow.png"));

        sprite1.setTranslateX(10);
        sprite2.setTranslateX(40);
        sprite3.setTranslateX(10);
        sprite4.setTranslateX(40);

        sprite1.setTranslateY(-10);
        sprite2.setTranslateY(-10);
        sprite3.setTranslateY(20);
        sprite4.setTranslateY(20);

        //game.initBank();
        updateBank();
    }

    @FXML
    protected void handleStartGameButtonAction(ActionEvent event)
    {
        if(swapposition1 != -1 && swapposition2 == -1)
            spaceButtons.get(swapposition1).setDisable(false);

        isSwapping = false;

        rollDice.setVisible(true);
        pass.setVisible(false);
        startGame.setVisible(false);
        shuffle.setVisible(false);

        for(int i = 0; i < 4; i++)
            spaceButtons.get(i * 8).setDisable(false);

        for(int i = 0; i < game.getPlayers().size(); i++)
            sprites.get(i).setVisible(true);

        message.setText("Alright! Start rolling the dice, " + game.getPlayers().get(game.getCurrentPlayer()).getName() + "!");
    }

    @FXML
    protected void handleShuffleButtonAction (ActionEvent event)
    {
        game.getBoard().shuffle();

        for(int i = 0; i <= 31; i++)
        {
            ImageView imgView = game.getBoard().getSpaces().get(i).getImage();
            spaceButtons.get(i).setGraphic(imgView);
            spaceLabels.get(i).setText(game.getBoard().getSpaces().get(i).toString());
        }
    }

    @FXML
    protected void handleRollDiceButtonAction(ActionEvent event)
    {
        currTurn = game.getCurrentPlayer();
        currPlayer = game.getPlayers().get(currTurn);
        oldPosition = currPlayer.getPosition();

        rollDice.setVisible(false);
        pass.setVisible(false);

        int number = new Random().nextInt(6) + 1;
        diceRoll = number;

        /**int number = Integer.parseInt(diceNumber.getText());
        diceRoll = number;*/

        newPosition = (oldPosition + number) % 32;

        currSpace = game.getBoard().getSpaces().get(newPosition);



        Timeline move = new Timeline(new KeyFrame(Duration.millis(200), event1 -> {
            board.getChildren().remove(sprites.get(currTurn));
            if (currPlayer.getPosition() == 31)
            {
                board.add(sprites.get(currTurn), index[0][1], index[0][0]);
                currPlayer.setPosition(0);
            }
            else
            {
                board.add(sprites.get(currTurn), index[currPlayer.getPosition() + 1][1], index[currPlayer.getPosition() + 1][0]);
                currPlayer.setPosition(currPlayer.getPosition() + 1);
            }
        }));

        move.setCycleCount(number);
        move.play();

        message.setText(currPlayer.getName() + " rolled a " + number + "! ");

        move.setOnFinished(e ->
        {
            message.setText(currSpace.onLand(currPlayer));

            onLand();
        });
    }

    @FXML
    protected void onLand()
    {
        //previously was in Jail space
        if (oldPosition == 24)
        {
            boolean hasGOJF = false;

            for(int i = 0; i < currPlayer.getChanceCards().size(); i++)
                if (currPlayer.getChanceCards().get(i) instanceof GetOutOfJailChance) {
                    hasGOJF = true;
                    break;
                }

            if(!hasGOJF)
            {
                currPlayer.add(-50);
                message.setText(message.getText() + "Paid $50 to get out of jail. ");
            }
            else
            {
                message.setText(message.getText() + "Used Get Out Of Jail Card. ");

                int i = 0;
                while(!(currPlayer.getChanceCards().get(i) instanceof GetOutOfJailChance))
                    i++;

                currPlayer.getChanceCards().remove(i);
            }
            updatePlayers();
        }

        //landed on START space
        if (newPosition == 0)
        {
            game.getBank().payTo(currPlayer, 200);
            updateBank();
            message.setText(message.getText() + "Collected $200. ");

            nextTurn(true);
        }

        //landed on Free Parking space
        else if (newPosition == 8)
        {
            message.setText(message.getText() + "For now, you may rest. ");

            nextTurn(true);
        }

        //landed on Community Service (CHURCH) space
        else if (newPosition == 16)
        {
            currPlayer.add(-50);
            message.setText(message.getText() + "Donated $50 to the bank. ");

            nextTurn(true);
        }

        //landed on Jail (SDFO) space
        else if (newPosition == 24)
        {
            message.setText(message.getText() + "Pay a fine of $50 later. ");
            nextTurn(true);
        }



        //landed on a Property
        else if (currSpace instanceof Property)
        {
            if(((Property) currSpace).isOwned())
            {
                ((Property) currSpace).incFootCount();
            }

            //landed on own Property
            if (((Property) currSpace).getOwner() == currPlayer)
            {
                if (((Property) currSpace).canDevelop())
                {
                    develop.setVisible(true);
                }

                else pass.setVisible(true);
            }

            //landed on unowned Property
            else if (!((Property) currSpace).isOwned())
            {
                if(((Property) currSpace).getPrice() <= currPlayer.getMoney())
                {
                    purchase.setVisible(true);
                }

                pass.setVisible(true);
            }

            //landed on someone else's Property
            else if(((Property) currSpace).getOwner() != currPlayer)
            {
                if (currPlayer.getProperties().size() > 0)
                {
                    trade.setVisible(true);
                }

                payRent.setVisible(true);
            }

            spaceInfoDisplay.setText(((Asset) currSpace).getInfo());
            spaceInfoBox.setVisible(true);
            spaceInfoDisplay.setVisible(true);
        }

        //landed on a Railroad
        else if (currSpace instanceof Railroad)
        {
            //landed on own Railroad
            if (((Railroad) currSpace).getOwner() == currPlayer)
            {
                pass.setVisible(true);
            }

            //landed on unowned Railroad
            else if (!((Railroad) currSpace).isOwned())
            {
                if(((Railroad) currSpace).getPrice() <= currPlayer.getMoney())
                {
                    purchase.setVisible(true);
                }
                pass.setVisible(true);
            }

            //landed on someone else's Railroad
            else if(((Railroad) currSpace).getOwner() != currPlayer)
            {
                payRent.setVisible(true);
            }

            spaceInfoDisplay.setText(((Asset) currSpace).getInfo());
            spaceInfoBox.setVisible(true);
            spaceInfoDisplay.setVisible(true);
        }

        //landed on a Utility
        else if (currSpace instanceof Utility)
        {
            ((Utility) currSpace).setDiceRoll(diceRoll);

            //landed on own Railroad
            if (((Utility) currSpace).getOwner() == currPlayer)
            {
                pass.setVisible(true);
            }

            //landed on unowned Railroad
            else if (!((Utility) currSpace).isOwned())
            {
                if(((Utility) currSpace).getPrice() <= currPlayer.getMoney())
                {
                    purchase.setVisible(true);
                }

                pass.setVisible(true);
            }

            //landed on someone else's Railroad
            else if(((Utility) currSpace).getOwner() != currPlayer)
            {
                payRent.setVisible(true);
            }

            spaceInfoDisplay.setText(((Asset) currSpace).getInfo());
            spaceInfoBox.setVisible(true);
            spaceInfoDisplay.setVisible(true);
        }

        //landed on the Income Tax Space or Luxury Tax Space
        else if (currSpace instanceof IncomeTaxSpace || currSpace instanceof LuxuryTaxSpace)
            payTax.setVisible(true);

        //landed on the Chance Space
        else if (currSpace instanceof ChanceSpace)
            drawChance.setVisible(true);

        if (newPosition != 0 && newPosition < oldPosition)
        {
            if(!ChanceEvent)
            {
                game.getBank().payTo(currPlayer, 200);
                message.setText(message.getText() + " Collected $200. ");
                updateBank();
            }
            updatePlayers();
        }
    }



    @FXML
    protected void handleClickSpaceButtonAction (ActionEvent event)
    {
        //if player is still currently swapping spaces

        if(isSwapping)
        {
            if(swapposition1 == -1)
            {
                int i = 0;

                do
                {
                    i++;
                } while (spaceButtons.get(i) != event.getSource());

                swapposition1 = i;
                spaceButtons.get(swapposition1).setDisable(true);
            }
            else if(swapposition2 == -1)
            {
                int i = 0;

                do
                {
                    i++;
                } while (spaceButtons.get(i) != event.getSource());

                swapposition2 = i;

                ImageView imgTemp1 = game.getBoard().getSpaces().get(swapposition1).getImage();
                ImageView imgTemp2 = game.getBoard().getSpaces().get(swapposition2).getImage();
                String strTemp1 = game.getBoard().getSpaces().get(swapposition1).toString();
                String strTemp2 = game.getBoard().getSpaces().get(swapposition2).toString();

                game.getBoard().getSpaces().get(swapposition2).setPosition(swapposition1);
                game.getBoard().getSpaces().get(swapposition1).setPosition(swapposition2);
                spaceButtons.get(swapposition1).setGraphic(imgTemp2);
                spaceButtons.get(swapposition2).setGraphic(imgTemp1);
                spaceLabels.get(swapposition1).setText(strTemp2);
                spaceLabels.get(swapposition2).setText(strTemp1);

                spaceButtons.get(swapposition1).setDisable(false);

                Collections.swap(game.getBoard().getSpaces(), swapposition1, swapposition2);

                swapposition1 = -1;
                swapposition2 = -1;
            }
        }

        //if player is choosing which property to trade

        else if (isTrading)
        {
            offerPosition = 0;

            do
            {
                offerPosition++;
            } while(spaceButtons.get(offerPosition) != event.getSource());

            Space offeredSpace = game.getBoard().getSpaces().get(offerPosition);

            message.setText(((Property)currSpace).getOwner().getName() + ", " + currPlayer.getName() +
                    " offered " + ((Property) offeredSpace).getName() + ". Please click agree or cancel.");

            spaceButtons.get(this.offerPosition).setDisable(true);
            agree.setVisible(true);
            isTrading = false;
        }

        //if player is choosing which property to use chance card on

        else if (isChoosingProp)
        {
            int i = 0;
            do
            {
                i++;
            } while(spaceButtons.get(i) != event.getSource());

            message.setText(((RentModifierChance)currChance).useEffect((Asset) game.getBoard().getSpaces().get(i)));

            for(int j = 0; j <= 31; j++)
                spaceButtons.get(j).setDisable(false);

            isChoosingProp = false;
            ChanceEvent = false;
            nextTurn(true);
        }
    }

    @FXML
    protected void handlePassButtonAction(ActionEvent event)
    {
        nextTurn(false);
    }

    @FXML
    protected void handlePurchaseButtonAction (ActionEvent e)
    {

        if(ChanceEvent && (currSpace instanceof Utility || currSpace instanceof Railroad))
        {
            currPlayer.add((Asset) currSpace);
            message.setText("Purchased " + ((Asset) currSpace).getName() + ". " +
                    (currPlayer.payTo(game.getBank(), ((Asset)currSpace).getPrice())));
            updateBank();
        }

        else
        {
            currPlayer.purchase((Asset) currSpace);
            message.setText(currPlayer.getName() + " purchased " + ((Asset) currSpace).getName() + ". ");
        }

        ChanceEvent = false;
        nextTurn(true);
    }

    @FXML
    protected void handlePayRentButtonAction (ActionEvent e)
    {
        if(currSpace instanceof Property)
            message.setText(currPlayer.payRent((Property) currSpace));

        else if(currSpace instanceof Railroad)
            message.setText(currPlayer.payRent((Railroad) currSpace));

        else if(currSpace instanceof Utility)
            message.setText(currPlayer.payRent(((Utility) currSpace), diceRoll, ChanceEvent));

        ChanceEvent = false;
        nextTurn(true);
    }

    @FXML
    protected void handlePayTaxButtonAction (ActionEvent e)
    {

        if(currSpace instanceof IncomeTaxSpace)
            message.setText(currPlayer.payTo(game.getBank(), ((IncomeTaxSpace) currSpace).getTax(currPlayer)));

        else if(currSpace instanceof LuxuryTaxSpace)
            message.setText(currPlayer.payTo(game.getBank(),((LuxuryTaxSpace) currSpace).getTax()));

        updateBank();

        nextTurn(true);
    }

    @FXML
    protected void handleDevelopButtonAction (ActionEvent e)
    {

        currPlayer.develop((Property) currSpace);
        spaceLabels.get(currPlayer.getPosition()).setText(currSpace.toString());

        nextTurn(true);
    }

    @FXML
    protected void handleTradeButtonAction (ActionEvent e)
    {
        payRent.setVisible(false);

        cancelTrade.setVisible(true);

        isTrading = true;

        for(int i = 0; i <= 31; i++)
        {
            if(game.getBoard().getSpaces().get(i) instanceof Property && ((Property) game.getBoard().getSpaces().get(i)).getOwner() == currPlayer)
                spaceButtons.get(i).setDisable(false);

            else spaceButtons.get(i).setDisable(true);
        }

        message.setText(currPlayer.getName() + ", please choose which property to offer, or click cancel to stop trading.");
    }

    @FXML
    protected void handleCancelTradeButtonAction (ActionEvent e)
    {
        trade.setVisible(true);
        payRent.setVisible(true);

        cancelTrade.setVisible(false);
        agree.setVisible(false);

        for(int i = 0; i <= 31; i++)
            spaceButtons.get(i).setDisable(false);

        message.setText("Cancelled trading.");

        isTrading = false;
    }

    @FXML
    protected void handleAgreeButtonAction (ActionEvent e)
    {
        isTrading = false;

        offeredSpace = game.getBoard().getSpaces().get(offerPosition);

        message.setText("Traded " + ((Asset) offeredSpace).getName() +
                " for " + ((Asset) currSpace).getName() +
                " with " + ((Asset) currSpace).getOwner().getName() + ". ");

        currPlayer.trade(((Asset)currSpace).getOwner(), (Asset) currSpace, (Asset) offeredSpace);

        for(int j = 0; j <= 31; j++)
            spaceButtons.get(j).setDisable(false);

        updatePlayers();

        nextTurn(true);
    }

    @FXML
    protected void handleDrawChanceButtonAction (ActionEvent e)
    {
        drawChance.setVisible(false);
        ChanceEvent = true;

        Chance chanceDrawn;
        if (game.getChanceDeck().isEmpty())
            game.getChanceDeck().resetUnowned();

        chanceDrawn = game.getChanceDeck().draw();

        /**do {
            chanceDrawn = game.getChanceDeck().draw();
        } while(!(chanceDrawn instanceof MoveToSpaceChance));*/

        if(chanceDrawn instanceof ChangeMoneyChance)
        {
            message.setText(((ChangeMoneyChance)chanceDrawn).useEffect(currPlayer));
            updateBank();
            nextTurn(true);
        }

        else if(chanceDrawn instanceof GetOutOfJailChance)
        {
            message.setText(((GetOutOfJailChance)chanceDrawn).useEffect(currPlayer) + chanceDrawn.getEffectDialogue() + " ");
            nextTurn(true);
        }

        else if(chanceDrawn instanceof MoveToSpaceChance)
        {
            oldPosition = currPlayer.getPosition();

            message.setText(((MoveToSpaceChance)chanceDrawn).useEffect(currPlayer));

            newPosition = currPlayer.getPosition();
            currSpace = game.getBoard().getSpaces().get(newPosition);

            board.getChildren().remove(sprites.get(currTurn));
            board.add(sprites.get(currTurn), index[newPosition][1], index[newPosition][0]);

            if (((MoveToSpaceChance)chanceDrawn).playerCollectedStart())
            {
                game.getBank().add(-200);
                updateBank();
            }

            message.setText(message.getText() + currSpace.onLand(currPlayer));

            if (currSpace instanceof Property || currSpace instanceof Utility || currSpace instanceof Railroad)
            {
                onLand();
            }

            else nextTurn(true);

            updatePlayers();
        }

        else if(chanceDrawn instanceof RentModifierChance)
        {
            currChance = chanceDrawn;
            isChoosingProp = true;

            for(int i = 0; i <= 31; i++)
                spaceButtons.get(i).setDisable(true);

            if((chanceDrawn.getEffect() == Chance.DOUBLE_RENT || chanceDrawn.getEffect() == Chance.RENOVATION || chanceDrawn.getEffect() == Chance.DILAPIDATED) &&
                    currPlayer.getProperties().size() != 0)
            {
                message.setText(chanceDrawn.getEffectDialogue() + "\nClick on the property you want to apply this card on.");
                for (int i = 0; i <= 31; i++)
                    if (game.getBoard().getSpaces().get(i) instanceof Property && ((Property) game.getBoard().getSpaces().get(i)).getOwner() == currPlayer)
                        spaceButtons.get(i).setDisable(false);
            }
            else if ((chanceDrawn.getEffect() == Chance.UTIL_RAIL_INC || chanceDrawn.getEffect() == Chance.UTIL_RAIL_DEC) &&
                    (currPlayer.getUtilities().size() != 0 || currPlayer.getRailroads().size() != 0))
            {
                message.setText(chanceDrawn.getEffectDialogue() + "\nClick on the utility or railroad you want to apply this card on.");
                for(int i = 0; i <= 31; i++)
                    if((game.getBoard().getSpaces().get(i) instanceof Utility && ((Utility) game.getBoard().getSpaces().get(i)).getOwner() == currPlayer)||
                            (game.getBoard().getSpaces().get(i) instanceof Railroad && ((Railroad) game.getBoard().getSpaces().get(i)).getOwner() == currPlayer))
                        spaceButtons.get(i).setDisable(false);
            }
            else
            {
                for(int i = 0; i <= 31; i++)
                    spaceButtons.get(i).setDisable(false);

                message.setText(chanceDrawn.getEffectDialogue() + "\nChance card discarded. ");
                nextTurn(true);
            }
        }
    }

    /**
     * This method updates the GUI.
     */
    private void updatePlayers()
    {
        int i, j;

        //Update player info for each player

        for(i = 0; i < game.getPlayers().size(); i++)
        {
            //Display player's info (name and amount of money)

            boxes.get(i).setVisible(true);
            menus[i][0].setVisible(true);
            menus[i][1].setVisible(true);

            menus[i][0].getMenus().get(0).getItems().clear();
            menus[i][0].getMenus().get(1).getItems().clear();
            menus[i][1].getMenus().get(0).getItems().clear();
            menus[i][1].getMenus().get(1).getItems().clear();

            playerDisplays.get(i).setText(game.getPlayers().get(i).getName() +
                    "\n\uD83D\uDCB0 $" + game.getPlayers().get(i).getMoney());
            playerDisplays.get(i).setVisible(true);

            //Update player's assets

            if(game.getPlayers().get(i).getProperties().size() == 0)
                menus[i][0].getMenus().get(0).getItems().add(new MenuItem("No Properties"));
            else
            {
                for (j = 0; j < game.getPlayers().get(i).getProperties().size(); j++)
                {
                    MenuItem text = new MenuItem(game.getPlayers().get(i).getProperties().get(j).getName());
                    text.setStyle("-fx-text-fill: " + game.getPlayers().get(i).getProperties().get(j).getHexColor());
                    menus[i][0].getMenus().get(0).getItems().add(text);
                }
            }

            if(game.getPlayers().get(i).getUtilities().size() == 0)
                menus[i][0].getMenus().get(1).getItems().add(new MenuItem("No Utilities"));
            else
            {
                for (j = 0; j < game.getPlayers().get(i).getUtilities().size(); j++)
                {
                    MenuItem text = new MenuItem(game.getPlayers().get(i).getUtilities().get(j).getName());
                    menus[i][0].getMenus().get(1).getItems().add(text);
                }
            }

            if(game.getPlayers().get(i).getRailroads().size() == 0)
                menus[i][1].getMenus().get(0).getItems().add(new MenuItem("No Railroads"));
            else
            {
                for (j = 0; j < game.getPlayers().get(i).getRailroads().size(); j++)
                {
                    MenuItem text = new MenuItem(game.getPlayers().get(i).getRailroads().get(j).getName());
                    menus[i][1].getMenus().get(0).getItems().add(text);
                }
            }

            if(game.getPlayers().get(i).getChanceCards().size() == 0)
                menus[i][1].getMenus().get(1).getItems().add(new MenuItem("No Chance Cards"));
            else
            {
                for (j = 0; j < game.getPlayers().get(i).getChanceCards().size(); j++)
                {
                    MenuItem text = new MenuItem("");
                    if(game.getPlayers().get(i).getChanceCards().get(j) instanceof GetOutOfJailChance)
                        text = new MenuItem("Get Out Of Jail");

                    menus[i][1].getMenus().get(1).getItems().add(text);
                }
            }
        }
    }

    private void nextTurn(boolean actionPerformed)
    {
        updatePlayers();

        if (game.checkGameEnd() != 0)
        {
            message.setText("Game has ended.\n");

            startGame.setVisible(false);
            shuffle.setVisible(false);
            endGame.setVisible(true);

            if(game.checkGameEnd() == Game.PLAYER_BANKRUPT)
            {
                for(int i = 0; i < game.getPlayers().size(); i++)
                {
                    if(game.getPlayers().get(i).isBankrupt())
                        message.setText(message.getText() +
                                game.getPlayers().get(i).getName() + " is bankrupt.");
                }
            }

            else if(game.checkGameEnd() == Game.TWO_FULL_SETS)
            {
                for(int i = 0; i < game.getPlayers().size(); i++)
                {
                    if(game.getPlayers().get(i).getFullSetCount() >= 2)
                        message.setText(message.getText() +
                                game.getPlayers().get(i).getName() + " has two full sets.");
                }
            }

            else if(game.checkGameEnd() == Game.BANK_EMPTY)
            {
                message.setText(message.getText() + "Bank is empty.");
            }
        }
        else
        {
            game.nextTurn();

            if(actionPerformed)
                message.setText(message.getText() +  "It's now " + game.getPlayers().get(game.getCurrentPlayer()).getName() +
                "'s turn. Roll the dice!");
            else message.setText("It's now " + game.getPlayers().get(game.getCurrentPlayer()).getName() +
                "'s turn. Roll the dice!");

            purchase.setVisible(false);
            payRent.setVisible(false);
            trade.setVisible(false);
            agree.setVisible(false);
            cancelTrade.setVisible(false);
            develop.setVisible(false);
            pass.setVisible(false);
            drawChance.setVisible(false);
            payTax.setVisible(false);

            spaceInfoDisplay.setVisible(false);
            spaceInfoBox.setVisible(false);

            isTrading       = false;
            isChoosingProp  = false;
            ChanceEvent = false;

            rollDice.setVisible(true);
        }
    }

    @FXML
    protected void handleBankruptButtonAction()
    {
        //game.getBank().setMoney(-1);
        game.getPlayers().get(0).add(-5000);

        nextTurn(true);
    }

    @FXML
    protected void handleEndGameButtonAction() throws IOException
    {
        rollDice.setVisible(false);

        game.rankPlayers();

        GameEndController endController = new GameEndController(game);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alsid/view/GameEnd.fxml"));
        loader.setController(endController);
        GridPane newPane = loader.load();
        mainHBox.getChildren().setAll(newPane);
    }

    private void updateBank()
    {
        bankMoney.setText("\uD83C\uDFE6 The Bank currently has $" + game.getBank().getMoney() + ".");
    }
}