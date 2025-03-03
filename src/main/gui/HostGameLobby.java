package main.gui;

import java.io.File;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.controller.ServerController;
import main.model.TileSet;

/**
 * The class HostGameLobby displays lobby the user sees when they host a game.
 * 
 * @author raydin
 *
 */
public class HostGameLobby {

  /**
   * The ServerController for this class.
   */
  private ServerController serverController;

  /**
   * Stage object to set the Scene.
   */
  private Stage stage = new Stage();

  /**
   * The BaseLobby object with which the base elements of the user-interface can be accessed.
   */
  private BaseLobby base = new BaseLobby();

  /**
   * The StackPane object to which all further needed components are added.
   */
  private StackPane pane;

  /**
   * The GridPane object for the chat messages.
   */
  private GridPane gridPane;

  /**
   * The ScrollPane object for the chat messages.
   */
  public static ScrollPane scrollPane;

  /**
   * The error box that is displayed when points over 10 are entered.
   */
  private ErrorBox error = new ErrorBox();

  /**
   * The Button to edit the lobby name.
   */
  private Button edit;

  /**
   * The Button to enter the inputs of the letter distribution.
   */
  private Button choices;

  /**
   * The Button to start the game.
   */
  private Button startGame;

  /**
   * The send button for the chat messages.
   */
  private Button sendButton;

  /**
   * Button to kick player 1.
   */
  private Button kickPlayer1 = new Button();

  /**
   * Button to kick player 2.
   */
  private Button kickPlayer2 = new Button();

  /**
   * Button to kick player 3.
   */
  private Button kickPlayer3 = new Button();

  /**
   * The Label that displays the connection details.
   */
  private Label connectionDetails;

  /**
   * The flag to determine if the user requests to change the lobby name.
   */
  private boolean toEdit = false;

  /**
   * The Label that shows the lobby name.
   */
  private Label lobbyName = new Label("Lobby of <username>");

  /**
   * The "Statistics" headline in the statistics table.
   */
  private Label statsHeadline = new Label("Statistics");

  /**
   * The P1 headline in the statistics table.
   */
  private Label p1 = new Label("Player 1");


  /**
   * The P2 headline in the statistics table.
   */
  private Label p2 = new Label("Player 2");

  /**
   * The P3 headline in the statistics table.
   */
  private Label p3 = new Label("Player 3");

  /**
   * The P4 headline in the statistics table.
   */
  private Label p4 = new Label("Player 4");

  /**
   * The player name of the first player in the player panel.
   */
  private Label player1 = new Label();

  /**
   * The player name of the second player in the player panel.
   */
  private Label player2 = new Label();

  /**
   * The player name of the third player in the player panel.
   */
  private Label player3 = new Label();

  /**
   * The player name of the fourth player in the player panel.
   */
  private Label player4 = new Label();

  /**
   * The "Player stats1" label in the statistics table.
   */
  private Label stats1 = new Label("Games Played");

  /**
   * The "Player stats2" label in the statistics table.
   */
  private Label stats2 = new Label("Wins");

  /**
   * The "Player stats3" label in the statistics table.
   */
  private Label stats3 = new Label("Win %");

  /**
   * The "Player stats4" label in the statistics table.
   */
  private Label stats4 = new Label("Last Place");

  /**
   * The values of the statistics in the statistics table, not set by default.
   */
  private Label[][] values = new Label[4][4];

  /**
   * The saved messages.
   */
  private Label[] messages = new Label[500];

  /**
   * The labels that represent the easy AI players' names.
   */
  private Label[] easyBotLabel = new Label[3];

  /**
   * The labels that represent the hard AI players' names.
   */
  private Label[] hardBotLabel = new Label[3];

  /**
   * The TextField for chat input.
   */
  private TextField input;

  /**
   * The text-field to enter points for a specific letter in.
   */
  private ComboBox<Integer> valueChoice;

  /**
   * The additional line in the statistics table to create four rows.
   */
  private Line lastLine;

  /**
   * The ImageView object to dispaly the Image of the number of the first player in the player
   * panel.
   */
  private ImageView oneIv;

  /**
   * The ImageView object to display the Image of the number of the second player in the player
   * panel.
   */
  private ImageView twoIv;

  /**
   * The ImageView object to display the Image of the number of the third player in the player
   * panel.
   */
  private ImageView threeIv;

  /**
   * The ImageView object to display the Image of the number of the fourth player in the player
   * panel.
   */
  private ImageView fourIv;

  /**
   * The buttons to add easy AI players.
   */
  private Button[] easyBots = new Button[3];

  /**
   * The buttons to add hard AI players.
   */
  private Button[] hardBots = new Button[3];

  /**
   * The buttons to remove the AI players.
   */
  private Button[] removeBot = new Button[3];

  /**
   * Flag to determine if the mesage is from the host.
   */
  private boolean msgFromHost = false;

  /**
   * Flag to determine if the message is from the client.
   */
  private boolean msgFromClient = false;

  /**
   * The index of the client who sent the message.
   */
  private int indexOfClient;

  /**
   * The TileSet to store the letter distribution in.
   */
  public TileSet tileSet;

  /**
   * The ComboBox to store the different dictionary choices.
   */
  private ComboBox<String> dictionaryChoice;

  /**
   * The ComboBox to choose the letters in the letter distributions.
   */
  private ComboBox<Character> letterChoice;

  /**
   * The ComboBox to determine what number of letters the bag of tiles should contain of a specific
   * letter.
   */
  private ComboBox<Integer> amountChoice;

  /**
   * The style properties of the bot buttons.
   */
  private final static String BOTBUTTONSTYLE =
      "-fx-background-color: #2e75b6; -fx-text-fill: white; "
          + "-fx-font-family: Futura; -fx-font-size: 13px; -fx-border-radius: 15px; "
          + "-fx-background-radius: 15px; -fx-font-weight: bold; -fx-font-style: italic;";

  /**
   * The style properties of the bot labels.
   */
  private final static String BOTLABELSTYLE =
      "-fx-text-fill: #7f7f7f; -fx-font-family: Futura; -fx-font-size: 24px; "
          + "-fx-font-style: italic; -fx-font-weight: bold;";


  /**
   * The color yellow of the chat messages.
   */
  private final static String YELLOW = "-fx-background-color: #ffd976; ";

  /**
   * The color purple of the chat messages.
   */
  private final static String PURPLE = "-fx-background-color: #d3abec; ";

  /**
   * The color purple of the chat messages.
   */
  private final static String BLUE = "-fx-background-color: #339be0; ";

  /**
   * The color pink of the chat messages.
   */
  private final static String PINK = "-fx-background-color: #ffa8dc; ";

  /**
   * The style properties of the text messages.
   */
  private final static String MESSAGES_STYLE =
      "-fx-font-size: 15px; -fx-background-radius: 13px; -fx-border-radius: 16px; -fx-font-family: Calibri; "
          + "-fx-text-fill: white; -fx-padding: 10px; -fx-font-weight: bold;";

  /**
   * The style properties of the names in the stats table.
   */
  private final static String NAMES_IN_STATS =
      "-fx-text-fill: #2e75b6; -fx-font-family: Futura; -fx-font-size: 18px;";

  /**
   * The constructor of the HostGamelobby class. Invokes the init method and sets the
   * serverController object.
   */
  public HostGameLobby(ServerController serverController) {
    init(this.stage);
    this.serverController = serverController;
  }

  /**
   * The alternative constructor of the HostGamelobby class. Invokes the init method.
   */
  public HostGameLobby() {
    init(this.stage);
  }

  /**
   * Returns the current lobbyName object.
   * 
   * @return the current lobbyName object.
   */
  public Label getLobbyName() {
    return lobbyName;
  }

  /**
   * Returns the startGame Button.
   * 
   * @return the startGame Button.
   */
  public Button getStartButton() {
    return this.startGame;
  }

  /**
   * Returns the Label object of player 1.
   * 
   * @return the Label object of player 1.
   */
  public Label getPlayer1() {
    return player1;
  }

  /**
   * Returns the Label object of player 2.
   * 
   * @return the Label object of player 2.
   */
  public Label getPlayer2() {
    return player2;
  }

  /**
   * Returns the Label object of player 3.
   * 
   * @return the Label object of player 3.
   */
  public Label getPlayer3() {
    return player3;
  }

  /**
   * Returns the Label object of player 4.
   * 
   * @return the Label object of player 4.
   */
  public Label getPlayer4() {
    return player4;
  }

  /**
   * Returns the StackPane object of this class.
   * 
   * @return the StackPane object of this class.
   */
  public StackPane getStackPane() {
    return this.pane;
  }

  /**
   * Returns the Label of the easy bot 1.
   * 
   * @return the Label of the easy bot 1.
   */
  public Label getEasyBotLabel1() {
    return this.easyBotLabel[0];
  }

  /**
   * Returns the Label of the easy bot 2.
   * 
   * @return the Label of the easy bot 2.
   */
  public Label getEasyBotLabel2() {
    return this.easyBotLabel[1];
  }

  /**
   * Returns the Label of the easy bot 3.
   * 
   * @return the Label of the easy bot 3.
   */
  public Label getEasyBotLabel3() {
    return this.easyBotLabel[2];
  }

  /**
   * Returns the Label of the hard bot 1.
   * 
   * @return the Label of the hard bot 1.
   */
  public Label getHardBotLabel1() {
    return this.hardBotLabel[0];
  }

  /**
   * Returns the Label of the hard bot 2.
   * 
   * @return the Label of the hard bot 2.
   */
  public Label getHardBotLabel2() {
    return this.hardBotLabel[1];
  }

  /**
   * Returns the Label of the hard bot 2.
   * 
   * @return the Label of the hard bot 2.
   */
  public Label getHardBotLabel3() {
    return this.hardBotLabel[2];
  }

  /**
   * Returns the kickPlayer1 Button.
   * 
   * @return the kickPlayer1 Button.
   */
  public Button getKickPlayer1() {
    return this.kickPlayer1;
  }

  /**
   * Returns the kickPlayer2 Button.
   * 
   * @return the kickPlayer2 Button.
   */
  public Button getKickPlayer2() {
    return this.kickPlayer2;
  }

  /**
   * Returns the kickPlayer3 Button.
   * 
   * @return the kickPlayer3 Button.
   */
  public Button getKickPlayer3() {
    return this.kickPlayer3;
  }

  /**
   * Returns the values Label Array for the statistics.
   * 
   * @return the StackPane object of this class.
   */
  public Label[][] getStatValues() {
    return this.values;
  }

  /**
   * Returns the ComboBox for the dictionary choice.
   * 
   * @return the ComboBox for the dictionary choice.
   */
  public ComboBox<String> getDictChoice() {
    return this.dictionaryChoice;
  }

  /**
   * Returns the ComboBox for the letter choice.
   * 
   * @return the ComboBox for the letter choice.
   */
  public ComboBox<Character> getLetterChoice() {
    return this.letterChoice;
  }

  /**
   * Returns the ComboBox for the amount choice.
   * 
   * @return the ComboBox for the amount choice.
   */
  public ComboBox<Integer> getAmountChoice() {
    return this.amountChoice;
  }

  /**
   * Returns the ComboBox for the value choice.
   * 
   * @return the ComboBox for the value choice.
   */
  public ComboBox<Integer> getValueChoice() {
    return this.valueChoice;
  }

  /**
   * Sets the value of the statistics.
   * 
   * @param row the row of the value to be set.
   * @param column the column of the value to be set.
   * @param value the value to be set.
   */
  public void setStatValue(int row, int column, String value) {
    values[row][column].setText(value);
  }

  /**
   * Sets the statistics name for [1,0].
   * 
   * @param statName the statistics name for [1,0].
   */
  public void setStatName1(String statName) {
    this.stats1.setText(statName);
  }

  /**
   * Sets the statistics name for [2,0].
   * 
   * @param statName the statistics name for [2,0].
   */
  public void setStatName2(String statName) {
    this.stats2.setText(statName);
  }

  /**
   * Sets the statistics name for [3,0].
   * 
   * @param statName the statistics name for [3,0].
   */
  public void setStatName3(String statName) {
    this.stats3.setText(statName);
  }

  /**
   * Sets the statistics name for [4,0].
   * 
   * @param statName the statistics name for [4,0].
   */
  public void setStatName4(String statName) {
    this.stats4.setText(statName);
  }

  /**
   * Sets the name of the player in the stats table for [0,1].
   * 
   * @param statName the statistics name for [0,1].
   */
  public void setPlayerName1(String name) {
    this.p1.setText(name);
  }

  /**
   * Sets the name of the player in the stats table for [0,2].
   * 
   * @param statName the statistics name for [0,2].
   */
  public void setPlayerName2(String name) {
    this.p2.setText(name);
  }

  /**
   * Sets the name of the player in the stats table for [0,3].
   * 
   * @param statName the statistics name for [0,3].
   */
  public void setPlayerName3(String name) {
    this.p3.setText(name);
  }

  /**
   * Sets the name of the player in the stats table for [0,4].
   * 
   * @param statName the statistics name for [0,4].
   */
  public void setPlayerName4(String name) {
    this.p4.setText(name);
  }

  /**
   * Calls all methods to initialize all components that are needed for the Stage object and finally
   * initializes the Stage object.
   * 
   * @param stage the stage object to initialize.
   */
  public void init(Stage stage) {
    initLobbyName();
    initEditButton();
    initChoicesButton();
    initButtonsLeft();
    initComboBoxes();
    initStatisticsLabels();
    initPlayerNumbers();
    initPlayerLabels();
    initBotButtons();
    initKickButtons();
    setActionEvents();
    initValues();
    initChatPane();
    setActionChat();
    setActionLetters();
    initStage(this.stage);

  }

  public Stage getStage() {
    return this.stage;
  }

  /**
   * Initializes the components needed for the statistics table.
   */
  private void initStatisticsLabels() {

    statsHeadline.setStyle("-fx-text-fill: #fa8fb3; -fx-font-family: Futura; -fx-font-size: 30px;");
    statsHeadline.setTranslateY(-240);
    statsHeadline.setTranslateX(-48);

    Label[] l2 = {p1, p2, p3, p4};
    for (Label labels : l2) {
      labels.setStyle("-fx-text-fill: #7F7F7F; -fx-font-family: Futura; -fx-font-size: 16px;");
      labels.setTranslateY(-240);
    }

    p1.setTranslateX(110);
    p2.setTranslateX(264);
    p3.setTranslateX(417);
    p4.setTranslateX(557);

    lastLine = new Line(800, 60, 1496, 60);
    lastLine.setStroke(Color.WHITE);
    lastLine.setStrokeWidth(3.0);

    lastLine.setTranslateX(233);
    lastLine.setTranslateY(-105);

    Label[] l3 = {stats1, stats2, stats3, stats4};
    for (Label labels : l3) {
      labels.setStyle("-fx-text-fill: #7F7F7F; -fx-font-family: Futura; -fx-font-size: 17px;");
      labels.setTranslateX(-45);
    }
    stats1.setTranslateY(-200);
    stats2.setTranslateY(-160);
    stats3.setTranslateY(-125);
    stats4.setTranslateY(-90);
  }

  /**
   * Initializes the ImageView objects of the numbers next to the player names.
   */
  private void initPlayerNumbers() {
    Image one = new Image("file:res/images/one.PNG", 75, 75, true, true);
    oneIv = new ImageView(one);
    Image two = new Image("file:res/images/two.PNG", 70, 70, true, true);
    twoIv = new ImageView(two);
    Image three = new Image("file:res/images/three.PNG", 78, 78, true, true);
    threeIv = new ImageView(three);
    Image four = new Image("file:res/images/four.PNG", 70, 70, true, true);
    fourIv = new ImageView(four);

    oneIv.setTranslateY(-80);
    oneIv.setTranslateX(-580);
    threeIv.setTranslateY(75);
    threeIv.setTranslateX(-580);
    twoIv.setTranslateX(-580);
    fourIv.setTranslateY(160);
    fourIv.setTranslateX(-573);
  }

  /**
   * Initializes the startGame and connectionDetails buttons.
   */
  private void initButtonsLeft() {

    Tooltip startGameTip = new Tooltip("Start the game.");
    startGameTip.setStyle("-fx-font-family: Futura; -fx-font-size: 15px; "
        + "-fx-background-color: #c7efff; -fx-border-radius: 22px; "
        + "-fx-background-radius: 22px; -fx-text-fill: black; -fx-set-stroke: black;");

    startGame = new Button("START GAME");
    startGame
        .setStyle("-fx-background-color: #004e8b; -fx-text-fill: white; -fx-font-style: italic; "
            + "-fx-font-size: 20px; -fx-border-radius: 22px; -fx-font-family: Futura; "
            + "-fx-background-radius: 22px; -fx-border-color: #3e4756; -fx-font-weight: bold;");

    startGame.setTooltip(startGameTip);

    Tooltip connectionDetTip = new Tooltip("Connection details to send them to your friends.");
    connectionDetTip.setStyle("-fx-font-family: Futura; -fx-font-size: 15px; "
        + "-fx-background-color: #c7efff; -fx-border-radius: 22px; "
        + "-fx-background-radius: 22px; -fx-text-fill: black; -fx-set-stroke: black;");

    connectionDetails = new Label("Connection Details");
    connectionDetails
        .setStyle("-fx-background-color: #8ec7ff; -fx-text-fill: black; -fx-font-style: italic; "
            + "-fx-font-size: 20px; -fx-border-radius: 22px; -fx-font-family: Calibri; "
            + "-fx-background-radius: 22px; -fx-border-color: #3e4756; -fx-font-weight: bold;");

    connectionDetails.setPrefHeight(60);
    connectionDetails.setPrefWidth(400);

    connectionDetails.setTooltip(connectionDetTip);

    startGame.setPrefHeight(60);
    startGame.setPrefWidth(400);

    startGame.setTranslateX(-416);
    startGame.setTranslateY(265);

    connectionDetails.setTranslateX(-416);
    connectionDetails.setTranslateY(-185);
  }

  /**
   * Initializes the chat pane.
   */
  private void initChatPane() {
    input = new TextField();
    input.setStyle("-fx-background-color: white; -fx-border-color: #2e75b6; "
        + "-fx-background-radius: 15px; -fx-border-radius: 15px; "
        + "-fx-text-fill: #7f7f7f; -fx-font-family: Futura; -fx-font-size: 15px;");

    input.setPromptText("Enter your message...");
    input.setFocusTraversable(false);
    input.setMaxHeight(50);
    input.setMaxWidth(270);

    Image send = new Image("file:res/images/send.PNG", 60, 60, true, true);
    ImageView sendView = new ImageView(send);

    sendButton = new Button();
    sendButton.setStyle("-fx-background-color: transparent;");
    sendButton.setGraphic(sendView);

    sendButton.setOnMousePressed(actionEvent -> {
      Image send2 = new Image("file:res/images/send.PNG", 75, 75, true, true);
      ImageView sendView2 = new ImageView(send2);
      sendButton.setGraphic(sendView2);
    });

    sendButton.setOnMouseReleased(actionEvent -> {
      Image send2 = new Image("file:res/images/send.PNG", 60, 60, true, true);
      ImageView sendView2 = new ImageView(send2);
      sendButton.setGraphic(sendView2);
    });

  }

  /**
   * Initializes the lobbyName object.
   */
  private void initLobbyName() {
    lobbyName.setStyle("-fx-text-fill: #2e75b6; -fx-font-family: Futura; "
        + "-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-style: italic;");
    lobbyName.setTranslateX(-430);
    lobbyName.setTranslateY(-275);
  }

  /**
   * Initializes the ComboBox objects needed for the dictionary choice and letter distribution.
   */
  private void initComboBoxes() {
    dictionaryChoice = new ComboBox<String>();
    dictionaryChoice.setStyle(
        "-fx-background-color: #F2F2F2; -fx-border-color: #A6A6A6; -fx-background-radius: 15px; "
            + "-fx-border-radius: 15px; -fx-text-fill: black; "
            + "-fx-font-size: 16px; -fx-font-family: Calibri;");

    dictionaryChoice.setPrefHeight(50);
    dictionaryChoice.setPrefWidth(280);
    dictionaryChoice.setPromptText("Dictionary Choice");
    dictionaryChoice.setTooltip(new Tooltip("Choose a dictionary you want to use"));

    dictionaryChoice.setTranslateX(-5);
    dictionaryChoice.setTranslateY(35);

    dictionaryChoice.getItems().addAll(new File("res/dict").list());
    dictionaryChoice.setValue(new File("res/dict").list()[0]);

    letterChoice = new ComboBox<Character>();
    letterChoice.setStyle(
        "-fx-background-color: #F2F2F2; -fx-border-color: #A6A6A6; -fx-background-radius: 15px; "
            + "-fx-border-radius: 15px; -fx-text-fill: black; "
            + "-fx-font-family: Calibri; -fx-font-size: 16px;");

    letterChoice.setPrefHeight(50);
    letterChoice.setPrefWidth(120);
    letterChoice.setTooltip(new Tooltip("Choose a letter to determine it's points"));
    letterChoice.setPromptText("Letter");

    for (int i = 65; i < 91; i++) {
      Character c = Character.valueOf((char) i);
      letterChoice.getItems().add(c);
    }

    letterChoice.setTranslateX(-80);
    letterChoice.setTranslateY(210);

    amountChoice = new ComboBox<Integer>();
    amountChoice.setStyle(
        "-fx-background-color: #F2F2F2; -fx-border-color: #A6A6A6; -fx-background-radius: 15px; "
            + "-fx-border-radius: 15px; -fx-text-fill: black; "
            + "-fx-font-family: Calibri; -fx-font-size: 16px;");

    for (int i = 1; i < 100; i++) {
      Integer amount = Integer.valueOf(i);
      amountChoice.getItems().add(amount);
    }

    amountChoice.setPrefWidth(120);
    amountChoice.setMaxHeight(50);
    amountChoice.setPromptText("Amount");
    amountChoice.setTooltip(new Tooltip("Choose the amount of the letter you prefer"));


    amountChoice.setTranslateX(-80);
    amountChoice.setTranslateY(270);


    valueChoice = new ComboBox<Integer>();
    valueChoice.setPromptText("value");
    valueChoice.setFocusTraversable(false);
    valueChoice.setStyle("-fx-background-color: #F2F2F2; -fx-border-color: #A6A6A6; "
        + "-fx-background-radius: 15px; -fx-border-radius: 15px; "
        + "-fx-text-fill: black; -fx-font-family: Calibri; -fx-font-size: 16px;");

    valueChoice.setMaxWidth(100);
    valueChoice.setMaxHeight(50);

    for (int i = 1; i < 100; i++) {
      Integer value = Integer.valueOf(i);
      valueChoice.getItems().add(value);
    }

    valueChoice.setTooltip(new Tooltip("Enter the number of points you want this letter to have"));
    valueChoice.setTranslateX(80);
    valueChoice.setTranslateY(210);
  }

  /**
   * Initializes the edit-button and sets their EventHandler.
   */
  private void initEditButton() {
    edit = new Button();
    Image im = new Image("file:res/images/edit.PNG", 80, 80, true, true);
    ImageView iv = new ImageView(im);
    edit.setStyle("-fx-background-color: transparent;");
    edit.setGraphic(iv);

    edit.setOnAction(actionEvent -> {
      toEdit = true;
      changeLobbyName();
    });

    Tooltip editTip = new Tooltip("Edit the Lobby Name");
    editTip.setStyle("-fx-font-family: Futura; -fx-font-size: 13px; "
        + "-fx-background-color: #e0f8c4; -fx-border-radius: 15px; "
        + "-fx-background-radius: 15px; -fx-text-fill: black; -fx-set-stroke: black;");
    edit.setTooltip(editTip);
    edit.setTranslateY(-270);
    edit.setTranslateX(-235);

    edit.setOnMouseEntered(actionEvent -> {
      Image send2 = new Image("file:res/images/edit.PNG", 85, 85, true, true);
      ImageView sendView2 = new ImageView(send2);
      edit.setGraphic(sendView2);
    });

    edit.setOnMouseExited(actionEvent -> {
      Image send2 = new Image("file:res/images/edit.PNG", 80, 80, true, true);
      ImageView sendView2 = new ImageView(send2);
      edit.setGraphic(sendView2);
    });
  }

  /**
   * Sets the Labels of the players on the left side of the panel.
   */
  private void initPlayerLabels() {
    Label[] names = {player2, player3, player4};

    for (Label l : names) {
      l.setStyle("-fx-text-fill: #ff605c; -fx-font-family: Futura; -fx-font-size: 24px; "
          + "-fx-font-weight: bold; -fx-font-style: italic;");
      l.setTranslateX(-460);
    }
    player1.setStyle("-fx-text-fill: #7F7F7F; -fx-font-family: Futura; -fx-font-size: 24px; "
        + "-fx-font-weight: bold; -fx-font-style: italic;");
    player1.setTranslateX(-450);

    player1.setTranslateY(-80);
    player2.setTranslateY(5);
    player3.setTranslateY(80);
    player4.setTranslateY(165);

  }

  /**
   * Initializes the Button objects to add and remove the Bots and the Label objects that appear
   * when a Bot is added.
   */
  private void initBotButtons() {
    Tooltip easyBotsTip = new Tooltip("Add an easy bot player.");
    easyBotsTip.setStyle("-fx-font-family: Futura; -fx-font-size: 13px; "
        + "-fx-background-color: #dfc7ff; -fx-border-radius: 15px; "
        + "-fx-background-radius: 15px; -fx-text-fill: black; -fx-set-stroke: black;");

    Tooltip hardBotsTip = new Tooltip("Add a hard bot player.");
    hardBotsTip.setStyle("-fx-font-family: Futura; -fx-font-size: 13px; "
        + "-fx-background-color: #c7d3ff; -fx-border-radius: 15px; "
        + "-fx-background-radius: 15px; -fx-text-fill: black; -fx-set-stroke: black;");

    for (int i = 0; i < easyBots.length; i++) {
      easyBots[i] = new Button("Easy Bot");
      easyBots[i].setPrefHeight(40);
      easyBots[i].setPrefWidth(80);
      easyBots[i].setStyle(BOTBUTTONSTYLE);
      easyBots[i].setTooltip(easyBotsTip);
    }

    for (int i = 0; i < hardBots.length; i++) {
      hardBots[i] = new Button("Hard Bot");
      hardBots[i].setPrefHeight(40);
      hardBots[i].setPrefWidth(80);
      hardBots[i].setStyle(BOTBUTTONSTYLE);
      hardBots[i].setTooltip(hardBotsTip);
    }

    for (Button b : easyBots) {
      b.setTranslateX(-390);

    }

    easyBots[1].setTranslateY(80);
    easyBots[2].setTranslateY(160);

    for (Button b : hardBots) {
      b.setTranslateX(-300);
    }

    hardBots[1].setTranslateY(80);
    hardBots[2].setTranslateY(160);

    Tooltip removeBotTip = new Tooltip("Remove bot player.");
    removeBotTip.setStyle("-fx-font-family: Futura; -fx-font-size: 13px; "
        + "-fx-background-color: #c7e6ff; -fx-border-radius: 15px; "
        + "-fx-background-radius: 15px; -fx-text-fill: black; -fx-set-stroke: black;");

    for (int i = 0; i < removeBot.length; i++) {
      removeBot[i] = new Button("x");
      removeBot[i].setStyle("-fx-background-color: transparent; -fx-text-fill: #A6A6A6; "
          + "-fx-font-size: 36px; -fx-font-weight: bold;");
      removeBot[i].setTranslateX(-260);
      removeBot[i].setTooltip(removeBotTip);
    }
    removeBot[0].setTranslateY(-5);
    removeBot[1].setTranslateY(70);
    removeBot[2].setTranslateY(160);

    for (int i = 0; i < easyBotLabel.length; i++) {
      easyBotLabel[i] = new Label();
      easyBotLabel[i].setStyle(BOTLABELSTYLE);
      easyBotLabel[i].setTranslateX(-460);
    }

    easyBotLabel[0].setTranslateY(5);
    easyBotLabel[1].setTranslateY(80);
    easyBotLabel[2].setTranslateY(165);

    for (int i = 0; i < hardBotLabel.length; i++) {
      hardBotLabel[i] = new Label();
      hardBotLabel[i].setStyle(BOTLABELSTYLE);
      hardBotLabel[i].setTranslateX(-460);
    }

    hardBotLabel[0].setTranslateY(5);
    hardBotLabel[1].setTranslateY(80);
    hardBotLabel[2].setTranslateY(165);
  }

  /**
   * Initializes the choice Button-object that saves the user inputs in the letter distribution.
   */
  private void initChoicesButton() {
    choices = new Button("ENTER");
    choices
        .setStyle("-fx-background-color: #2e75b6; -fx-text-fill: white; -fx-font-family: Futura; "
            + "-fx-font-size: 15px; -fx-font-weight: bold; "
            + "-fx-border-radius: 15px; -fx-background-radius: 15px;");

    choices.setPrefHeight(40);
    choices.setPrefWidth(80);

    choices.setTranslateX(90);
    choices.setTranslateY(270);
  }

  /**
   * Initializes the stage and adds all the components on the StackPane.
   * 
   * @param stage the stage object to be initialized.
   */
  private void initStage(Stage stage) {
    base.init(stage);
    pane = base.getStackPane();

    pane.getChildren().addAll(edit, lobbyName, choices, startGame, connectionDetails,
        dictionaryChoice, letterChoice, amountChoice, valueChoice, statsHeadline, p1, p2, p3, p4,
        lastLine);

    pane.getChildren().addAll(stats1, stats2, stats3, stats4);

    pane.getChildren().addAll(oneIv, twoIv, threeIv, fourIv);

    pane.getChildren().addAll(player1, player2, player3, player4);

    pane.getChildren().addAll(easyBots[0], easyBots[1], easyBots[2]);
    pane.getChildren().addAll(hardBots[0], hardBots[1], hardBots[2]);


    for (int i = 0; i < values.length; i++) {
      pane.getChildren().add(values[0][i]);
      values[0][i].setTranslateX(110);
    }
    values[0][0].setTranslateY(-200);
    values[0][1].setTranslateY(-160);
    values[0][2].setTranslateY(-125);
    values[0][3].setTranslateY(-90);

    for (int i = 0; i < values.length; i++) {
      pane.getChildren().add(values[1][i]);
      values[1][i].setTranslateX(270);
    }
    values[1][0].setTranslateY(-200);
    values[1][1].setTranslateY(-160);
    values[1][2].setTranslateY(-125);
    values[1][3].setTranslateY(-90);

    for (int i = 0; i < values.length; i++) {
      pane.getChildren().add(values[2][i]);
      values[2][i].setTranslateX(410);
    }
    values[2][0].setTranslateY(-200);
    values[2][1].setTranslateY(-160);
    values[2][2].setTranslateY(-125);
    values[2][3].setTranslateY(-90);

    for (int i = 0; i < values.length; i++) {
      pane.getChildren().add(values[3][i]);
      values[3][i].setTranslateX(550);
    }
    values[3][0].setTranslateY(-200);
    values[3][1].setTranslateY(-160);
    values[3][2].setTranslateY(-125);
    values[3][3].setTranslateY(-90);

    pane.getChildren().add(input);
    input.setTranslateX(430);
    input.setTranslateY(274);

    pane.getChildren().add(sendButton);
    sendButton.setTranslateY(274);
    sendButton.setTranslateX(600);

    scrollPane = new ScrollPane();
    scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
    scrollPane.setMaxWidth(335);
    scrollPane.setMaxHeight(250);
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setTranslateX(460);
    scrollPane.setTranslateY(120);


    gridPane = new GridPane();
    gridPane.setMaxWidth(335);
    gridPane.setMaxHeight(250);
    gridPane.setStyle("-fx-background-color: transparent;");

    Label chat = new Label("");
    chat.setStyle("-fx-font-family: Futura; -fx-font-size: 15px; -fx-text-fill: black; "
        + "-fx-background-color: transparent; -fx-font-weight: bold;");
    chat.setPrefWidth(325);
    chat.setAlignment(Pos.CENTER);
    chat.setTextAlignment(TextAlignment.CENTER);

    gridPane.getChildren().add(chat);
    scrollPane.setContent(gridPane);
    pane.getChildren().add(scrollPane);
    Label headlineName = new Label("S C R A B U R U");
    headlineName.setStyle("-fx-font-family: Futura; -fx-background-color: transparent;"
        + "-fx-text-fill: linear-gradient(to right, #84A9E7 0%, "
        + "#DEB7EE 15%, #ee7898 30%, #e6ed8e 45%, #9ccdb4 60%, " + "#84A9E7 75%, #ed9ab0 100% );"
        + "-fx-font-size: 30px;" + "-fx-font-weight: bold;");

    pane.getChildren().add(headlineName);

    headlineName.setTranslateX(250);
    headlineName.setTranslateY(-305);
  }

  /**
   * Handles the letter distribution events.
   */
  private void setActionLetters() {
    choices.setOnMouseEntered(styleEvent -> {
      choices.setStyle("-fx-background-color: #5094d3; -fx-text-fill: white; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-font-weight: bold; -fx-border-radius: 15px; -fx-background-radius: 15px;");
    });

    choices.setOnMouseExited(styleEvent -> {
      choices
          .setStyle("-fx-background-color: #2e75b6; -fx-text-fill: white; -fx-font-family: Futura;"
              + " -fx-font-size: 15px; -fx-font-weight: bold; "
              + "-fx-border-radius: 15px; -fx-background-radius: 15px;");
    });
  }

  /**
   * Sets the event handles for all components in the chat pane.
   */
  private void setActionChat() {
    input.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
          serverController.sendChatLobby(input.getText());
          input.setText(null);
        }

      }
    });

    sendButton.setOnAction(sendEvent -> {
      serverController.sendChatLobby(input.getText());
      input.setText(null);
    });

  }

  /**
   * The method to change the lobby name, is being called when the edit-Button is clicked.
   */
  private void changeLobbyName() {
    TextField tf = new TextField();
    tf.setStyle("-fx-background-color: #ebeaea; -fx-text-fill: #2e75b6; -fx-font-size: 17px; "
        + "-fx-font-family: Futura; -fx-font-weight: bold; -fx-font-style: italic; "
        + "-fx-border-radius: 15px; -fx-background-radius: 15px;");
    tf.setPrefHeight(50);
    tf.setMaxWidth(280);
    tf.setTooltip(new Tooltip("Enter new name"));

    tf.setTranslateX(-445);
    tf.setTranslateY(-275);

    Button done = new Button("ENTER");
    done.setStyle("-fx-background-color: #2e75b6; -fx-text-fill: white; -fx-font-family: Futura; "
        + "-fx-font-size: 15px; -fx-font-weight: bold;");

    done.setTranslateX(-265);
    done.setTranslateY(-275);

    done.setOnAction(actionEvent -> {
      String newName = "";

      if (!(tf.getText().equals(""))) {
        if (tf.getText().length() > 25) {
          error.displayError("Lobby name too long.");
        } else {
          newName = tf.getText();
        }
      } else {
        newName = "Lobby of " + player1.getText();
      }

      lobbyName.setText(newName);
      pane.getChildren().add(lobbyName);
      pane.getChildren().add(edit);
      pane.getChildren().remove(done);
      pane.getChildren().remove(tf);
      lobbyName.setTranslateX(-430);
      lobbyName.setTranslateY(-270);
      serverController.sendLobbyDetails();
    });

    if (toEdit) {
      pane.getChildren().remove(lobbyName);
      pane.getChildren().remove(edit);
      pane.getChildren().add(tf);
      pane.getChildren().add(done);
      toEdit = false;
    }
  }

  /**
   * Initializes the statistics values of each player.
   */
  private void initValues() {
    for (int i = 0; i < values.length; i++) {
      for (int j = 0; j < values[0].length; j++) {
        values[i][j] = new Label();
        values[i][j].setText("");
        values[i][j]
            .setStyle("-fx-text-fill: #7F7F7F; -fx-font-family: Futura; -fx-font-size: 17px;");
      }
    }
  }

  /**
   * Sets all Action- and MouseEvents of this stage.
   */
  private void setActionEvents() {

    startGame.setOnMouseEntered(mouseEvent -> {
      startGame
          .setStyle("-fx-background-color: #5094d3; -fx-text-fill: white; -fx-font-style: italic; "
              + "-fx-font-size: 20px; -fx-border-radius: 22px; -fx-font-family: Futura; "
              + "-fx-background-radius: 22px; -fx-border-color: #3e4756; -fx-font-weight: bold;");
    });

    startGame.setOnMouseExited(mouseEvent -> {
      startGame
          .setStyle("-fx-background-color: #004e8b; -fx-text-fill: white; -fx-font-style: italic; "
              + "-fx-font-size: 20px; -fx-border-radius: 22px; -fx-font-family: Futura; "
              + "-fx-background-radius: 22px; -fx-border-color: #3e4756; -fx-font-weight: bold;");
    });

    for (Button b : easyBots) {
      b.setOnMouseEntered(mouseEvent -> {
        b.setStyle("-fx-background-color: white; -fx-text-fill: #2e75b6; "
            + "-fx-font-family: Futura; -fx-font-size: 13px; "
            + "-fx-border-radius: 15px; -fx-background-radius: 15px; "
            + "-fx-font-weight: bold; -fx-font-style: italic; " + "-fx-border-color: #2e75b6;");
      });

      b.setOnMouseExited(mouseEvent -> {
        b.setStyle("-fx-background-color: #2e75b6; -fx-text-fill: white; -fx-font-family: Futura; "
            + "-fx-font-size: 13px; -fx-border-radius: 15px; "
            + "-fx-background-radius: 15px; -fx-font-weight: bold; -fx-font-style: italic;");
      });
    }

    for (Button b : hardBots) {
      b.setOnMouseEntered(mouseEvent -> {
        b.setStyle("-fx-background-color: white; -fx-text-fill: #2e75b6; -fx-font-family: Futura; "
            + "-fx-font-size: 13px; -fx-border-radius: 15px; -fx-background-radius: 15px; "
            + "-fx-font-weight: bold; -fx-font-style: italic; " + "-fx-border-color: #2e75b6;");
      });

      b.setOnMouseExited(mouseEvent -> {
        b.setStyle("-fx-background-color: #2e75b6; -fx-text-fill: white; -fx-font-family: Futura; "
            + "-fx-font-size: 13px; -fx-border-radius: 15px; -fx-background-radius: 15px; "
            + "-fx-font-weight: bold; -fx-font-style: italic;");
      });
    }

    easyBots[0].setOnAction(actionEvent -> {
      pane.getChildren().removeAll(player2, easyBots[0], hardBots[0]);
      easyBotLabel[0].setText("Easy Bot 1");
      pane.getChildren().addAll(easyBotLabel[0], removeBot[0]);
      serverController.addAi(1);
    });

    easyBots[1].setOnAction(actionEvent -> {
      pane.getChildren().removeAll(player3, easyBots[1], hardBots[1]);
      easyBotLabel[1].setText("Easy Bot 2");
      pane.getChildren().addAll(easyBotLabel[1], removeBot[1]);
      serverController.addAi(2);
    });

    easyBots[2].setOnAction(actionEvent -> {
      pane.getChildren().removeAll(player4, easyBots[2], hardBots[2]);
      easyBotLabel[2].setText("Easy Bot 3");
      pane.getChildren().addAll(easyBotLabel[2], removeBot[2]);
      serverController.addAi(3);
    });

    hardBots[0].setOnAction(actionEvent -> {
      pane.getChildren().removeAll(player2, easyBots[0], hardBots[0]);
      hardBotLabel[0].setText("Hard Bot 1");
      pane.getChildren().addAll(hardBotLabel[0], removeBot[0]);
      serverController.addAi(1);
    });

    hardBots[1].setOnAction(actionEvent -> {
      pane.getChildren().removeAll(player3, easyBots[1], hardBots[1]);
      hardBotLabel[1].setText("Hard Bot 2");
      pane.getChildren().addAll(hardBotLabel[1], removeBot[1]);
      serverController.addAi(2);
    });

    hardBots[2].setOnAction(actionEvent -> {
      pane.getChildren().removeAll(player4, easyBots[2], hardBots[2]);
      hardBotLabel[2].setText("Hard Bot 3");
      pane.getChildren().addAll(hardBotLabel[2], removeBot[2]);
      serverController.addAi(3);
    });

    removeBot[0].setOnAction(actionEvent -> {
      if (pane.getChildren().contains(easyBotLabel[0])) {
        pane.getChildren().removeAll(easyBotLabel[0], removeBot[0]);
        easyBotLabel[0].setText("");
      } else if (pane.getChildren().contains(hardBotLabel[0])) {
        hardBotLabel[0].setText("");
        pane.getChildren().removeAll(hardBotLabel[0], removeBot[0]);
      }

      pane.getChildren().addAll(player2, easyBots[0], hardBots[0]);
      serverController.removeAi(1);
    });

    removeBot[1].setOnAction(actionEvent -> {
      if (pane.getChildren().contains(easyBotLabel[1])) {
        pane.getChildren().removeAll(easyBotLabel[1], removeBot[1]);
        easyBotLabel[1].setText("");
      } else if (pane.getChildren().contains(hardBotLabel[1])) {
        hardBotLabel[1].setText("");
        pane.getChildren().removeAll(hardBotLabel[1], removeBot[1]);
      }

      pane.getChildren().addAll(player3, easyBots[1], hardBots[1]);
      serverController.removeAi(2);
    });

    removeBot[2].setOnAction(actionEvent -> {
      if (pane.getChildren().contains(easyBotLabel[2])) {
        pane.getChildren().removeAll(easyBotLabel[2], removeBot[2]);
        easyBotLabel[2].setText("");
      } else if (pane.getChildren().contains(hardBotLabel[2])) {
        hardBotLabel[2].setText("");
        pane.getChildren().removeAll(hardBotLabel[2], removeBot[2]);
      }

      pane.getChildren().addAll(player4, easyBots[2], hardBots[2]);
      serverController.removeAi(3);
    });
  }



  /**
   * Sets the index for the client who has sent a message.
   * 
   * @param name the name of the client who has sent a message.
   */
  public void msgIsFrom(String name) {
    if (name == player1.getText()) {
      msgFromHost = true;
    } else {
      msgFromClient = true;
    }
  }

  /**
   * Sets the chat message of the host and displays it on the chat rectangle.
   */
  public void setChatMessage(String name, String message, boolean fromHost) {
    int i = 0;
    while (messages[i] != null) {
      i++;
    }

    Label msgLabel = new Label();
    msgLabel.setStyle("-fx-background-color: #2e75b6; -fx-font-size: 15px; "
        + "-fx-background-radius: 13px; -fx-border-radius: 16px; -fx-font-family: Calibri; "
        + "-fx-text-fill: white; -fx-padding: 10px;");

    if (message.length() > 28) {
      message = message.substring(0, 28);
    }
    msgLabel.setText(message);

    Label nameOfSender = new Label(name);
    nameOfSender.setStyle("-fx-background-color: transparent; -fx-text-fill: gray; "
        + "-fx-font-size: 14px; -fx-padding: 5px; -fx-font-family: Calibri;");

    msgLabel.setTextAlignment(TextAlignment.CENTER);
    msgLabel.setAlignment(Pos.CENTER);
    messages[i] = msgLabel;


    VBox msgBox = new VBox();
    msgBox.getChildren().add(nameOfSender);
    msgBox.getChildren().add(messages[i]);

    gridPane.add(msgBox, 0, i);

    if (fromHost) {
      msgLabel.setStyle(YELLOW + MESSAGES_STYLE + "-fx-text-fill: black;");
      msgBox.setAlignment(Pos.CENTER_RIGHT);

    } else {
      if ((player2.getText() != null) && (name.equals(player2.getText()))) {
        msgLabel.setStyle(PURPLE + MESSAGES_STYLE);

      } else if ((player2.getText() != null) && (name.equals(player2.getText()))) {
        msgLabel.setStyle(BLUE + MESSAGES_STYLE);

      } else {
        msgLabel.setStyle(PINK + MESSAGES_STYLE);
      }
      msgBox.setAlignment(Pos.CENTER_LEFT);
    }
    scrollDown();
  }


  /**
   * Scrolls down to the bottom of the pane.
   */
  private void scrollDown() {

    scrollPane.vvalueProperty().bind(gridPane.heightProperty());

    scrollPane.applyCss();
    scrollPane.layout();
    scrollPane.setVvalue(scrollPane.getVmax());

  }

  /**
   * Removes the Bot-Button when a player is added.
   * 
   * @param index the index of the player that is added.
   */
  public void removeBotButtons(int index) {
    pane.getChildren().removeAll(easyBots[index], hardBots[index]);
  }

  /**
   * Adds the Bot-Button when a player is removed.
   * 
   * @param index the index of the player that is added.
   */
  public void addBotButtons(int index) {
    pane.getChildren().addAll(easyBots[index], hardBots[index]);
  }

  /**
   * Sets the connection Details for the lobby.
   * 
   * @param ipAddress the ipAddress of this lobby.
   */
  public void setConnectionDetails(String ipAddress) {
    this.connectionDetails.setText("IP Address:     " + ipAddress);
    this.connectionDetails.setTextAlignment(TextAlignment.CENTER);
    this.connectionDetails.setAlignment(Pos.CENTER);
  }

  /**
   * Initializes/styles the kick-player Buttons.
   */
  private void initKickButtons() {
    kickPlayer1.setText("x");
    kickPlayer1.setStyle("-fx-background-color: transparent; -fx-text-fill: #A6A6A6; "
        + "-fx-font-size: 36px; -fx-font-weight: bold;");

    kickPlayer1.setTranslateX(-260);

    kickPlayer2.setText("x");
    kickPlayer2.setStyle("-fx-background-color: transparent; -fx-text-fill: #A6A6A6; "
        + "-fx-font-size: 36px; -fx-font-weight: bold;");

    kickPlayer2.setTranslateX(-260);

    kickPlayer3.setText("x");
    kickPlayer3.setStyle("-fx-background-color: transparent; -fx-text-fill: #A6A6A6; "
        + "-fx-font-size: 36px; -fx-font-weight: bold;");

    kickPlayer3.setTranslateX(-260);

    kickPlayer1.setTranslateY(-5);
    kickPlayer2.setTranslateY(70);
    kickPlayer3.setTranslateY(160);

    kickPlayer1.setOnAction(actionEvent -> {
      serverController.kickPlayer(player2.getText());
    });

    kickPlayer2.setOnAction(actionEvent -> {
      serverController.kickPlayer(player3.getText());
    });

    kickPlayer3.setOnAction(actionEvent -> {
      serverController.kickPlayer(player4.getText());
    });
  }

  /**
   * Makes the kick Button visible whenever players have joined.
   * 
   * @param index the index of the Button that is going to be added.
   */
  public void addKickButton(int index) {
    Button[] kickButtons = {kickPlayer1, kickPlayer2, kickPlayer3};
    pane.getChildren().add(kickButtons[index]);
  }

  /**
   * Makes the kick Button invisible whenever players have left.
   * 
   * @param index the index of the Button that is going to be removed.
   */
  public void removeKickButton(int index) {
    Button[] kickButtons = {kickPlayer1, kickPlayer2, kickPlayer3};
    pane.getChildren().remove(kickButtons[index]);
  }

  /**
   * Makes the kick Button invisible whenever bots have left.
   * 
   * @param index the index of the Button that is going to be removed.
   */
  public void removeKickButtonBot(int index) {
    Button[] kickButtons = {removeBot[0], removeBot[1], removeBot[2]};
    pane.getChildren().remove(kickButtons[index]);
  }


  public void removeBots(int index) {

    if (pane.getChildren().contains(easyBotLabel[index])) {
      pane.getChildren().removeAll(easyBotLabel[index], removeBot[index]);
      easyBotLabel[index].setText("");
    } else if (pane.getChildren().contains(hardBotLabel[index])) {
      hardBotLabel[index].setText("");
      pane.getChildren().removeAll(hardBotLabel[index], removeBot[index]);
    }
    Label player = new Label();
    switch (index) {
      case 0:
        player = player2;
        break;
      case 1:
        player = player3;
        break;
      case 2:
        player = player4;
        break;
      default:
        break;
    }
    pane.getChildren().addAll(player, easyBots[index], hardBots[index]);
    serverController.removeAi((index + 1));


  }

  /**
   * Colors the labels of the players that have clicked on the ready Button.
   * 
   * @param name the name of the player that has clicked on ready.
   */
  public void colorReadyPlayers(String name) {
    if (name.equals(player2.getText())) {
      player2.setTextFill(GuiSettings.GREEN);
    } else if (name.equals(player3.getText())) {
      player3.setTextFill(GuiSettings.GREEN);
    } else if (name.equals(player4.getText())) {
      player4.setTextFill(GuiSettings.GREEN);
    }
  }

  /**
   * Colors the labels of the players that have clicked on the ready Button.
   * 
   * @param name the name of the player that has clicked on ready.
   */
  public void colorNotReadyPlayers(String name) {
    if (name.equals(player2.getText())) {
      player2.setTextFill(GuiSettings.RED);
    } else if (name.equals(player3.getText())) {
      player3.setTextFill(GuiSettings.RED);
    } else if (name.equals(player4.getText())) {
      player4.setTextFill(GuiSettings.RED);
    }
  }

  /**
   * Sets the names of the joined players in the stats table.
   * 
   * @param index index of the player that has joined.
   * @param name name of the player that has joined.
   */
  public void setPlayerName(int index, String name) {
    if (index == 0) {
      p1.setText(name);
      p1.setStyle(NAMES_IN_STATS);
    }
    if (index == 1) {
      p2.setText(name);
      p2.setStyle(NAMES_IN_STATS);
    }
    if (index == 2) {
      p3.setText(name);
      p3.setStyle(NAMES_IN_STATS);
    }
    if (index == 3) {
      p4.setText(name);
      p4.setStyle(NAMES_IN_STATS);
    }
  }

  /**
   * The method closes the stage.
   * 
   * @param stage The stage that is going to be closed.
   */
  public void close(Stage stage) {
    stage.close();
  }
}
