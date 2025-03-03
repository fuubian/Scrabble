package main.gui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
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
import main.controller.ClientController;

/**
 * This class represents the lobby before the game starts when the user joins a game.
 * 
 * @author raydin
 *
 */
public class JoinGameLobby {
  /**
   * Stage object to set the Scene.
   */
  private Stage stage = new Stage();

  /**
   * The BaseLobby instance which sets the base components such as the background image.
   */
  private BaseLobby base = new BaseLobby();

  /**
   * The ClientController of this class.
   */
  private ClientController clientController;

  /**
   * The ready Button that tells the host that the joined player is ready.
   */
  private Button ready;

  /**
   * The Label to show the connection details.
   */
  private Label connectionDetails;

  /**
   * The StackPane object of this class that sets the Scene.
   */
  private StackPane pane;

  /**
   * The GridPane object for the chat messages.
   */
  private GridPane gridPane;

  /**
   * The ScrollPane object for the chat messages.
   */
  private ScrollPane scrollPane;

  /**
   * The HostGameLobby instance to get the lobby name from.
   */
  private HostGameLobby hostGameLobby = new HostGameLobby();

  /**
   * The Label object that stores the name of the lobby.
   */
  private Label lobbyName;

  /**
   * The ImageView object to display the Image of the number of player 1 in the player panel.
   */
  private ImageView oneIv;

  /**
   * The ImageView object to display the Image of the number of player 2 in the player panel.
   */
  private ImageView twoIv;

  /**
   * The ImageView object to display the Image of the number of player 3 in the player panel.
   */
  private ImageView threeIv;

  /**
   * The ImageView object to display the Image of the number of player 4 in the player panel.
   */
  private ImageView fourIv;

  /**
   * The "Statistics" headline in the statistics table.
   */
  private Label statsHeadline = new Label("Statistics");

  /**
   * The P1 headline in the statistics table.
   */
  private Label p1 = new Label("P1");

  /**
   * The P2 headline in the statistics table.
   */
  private Label p2 = new Label("P2");

  /**
   * The P3 headline in the statistics table.
   */
  private Label p3 = new Label("P3");

  /**
   * The P4 headline in the statistics table.
   */
  private Label p4 = new Label("P4");

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
   * The additional line in the statistics table to create four rows.
   */
  private Line lastLine;

  /**
   * The labels of the statistic values.
   */
  private Label[][] values = new Label[4][4];

  /**
   * The labels of the player names.
   */
  private Label[] playerNames = new Label[4];

  /**
   * Flag to determine if the mesage is from the host.
   */
  private boolean msgFromHost = false;

  /**
   * Flag to determine if the message is from the client.
   */
  private boolean msgFromClient = false;

  /**
   * The text field for the chat messages.
   */
  private TextField input;

  /**
   * The button to send chat messages.
   */
  private Button sendButton;

  /**
   * The index of the client who sent the message.
   */
  private int indexOfClient;

  /**
   * The saved messages.
   */
  private Label[] messages = new Label[100];

  /**
   * Error Box to display any occuring errors.
   */
  private ErrorBox error = new ErrorBox();

  /**
   * The flag to determine if the ready Button has been clicked.
   */
  public static boolean isReady = false;

  /**
   * The constructor of the JoinGameLobby class. Invokes the init method.
   */
  public JoinGameLobby(ClientController clientController) {
    init(this.stage);
    this.clientController = clientController;
  }

  /**
   * Returns the ErrorBox object of this class.
   * 
   * @return the ErrorBox object of this class.
   */
  public ErrorBox getError() {
    return this.error;
  }

  /**
   * Returns the ready button.
   * 
   * @return the ready button.
   */
  public Button getReadyButton() {
    return this.ready;
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
   * Returns the lobby name.
   * 
   * @return the lobby name.
   */
  public Label getLobbyName() {
    return this.lobbyName;
  }

  /**
   * Returns the Label object of player 1.
   * 
   * @return the Label object of player 1.
   */
  public Label getPlayer1() {
    return this.playerNames[0];
  }

  /**
   * Returns the Label object of player 2.
   * 
   * @return the Label object of player 2.
   */
  public Label getPlayer2() {
    return this.playerNames[1];
  }

  /**
   * Returns the Label object of player 3.
   * 
   * @return the Label object of player 3.
   */
  public Label getPlayer3() {
    return this.playerNames[2];
  }

  /**
   * Returns the Label object of player 4.
   * 
   * @return the Label object of player 4.
   */
  public Label getPlayer4() {
    return this.playerNames[3];
  }

  public void setValue(int row, int column, String value) {
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
   * Initializes all components of this class.
   * 
   * @param stage the Stage object to be initialized
   */
  private void init(Stage stage) {
    initButtonsLeft();
    initLobbyName();
    initPlayerNumbers();
    initStatisticsLabels();
    initValues();
    setChatPane();
    setActionChat();
    initPlayerNames();
    initStage(stage);
  }

  /**
   * Sets the chat pane and initializes it's components.
   */
  private void setChatPane() {
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
   * Sets the events for the chat pane.
   */
  private void setActionChat() {
    input.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
          clientController.sendChatLobby(input.getText());
          input.setText(null);
        }
      }
    });

    sendButton.setOnAction(sendEvent -> {
      clientController.sendChatLobby(input.getText());
      input.setText(null);
    });

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
    lastLine.setOpacity(10);
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
   * Initializes the ready-Button and the connectionDetails-Button and sets their Events.
   */
  private void initButtonsLeft() {
    ready = new Button("READY");
    ready.setStyle("-fx-background-color: #004e8b; -fx-text-fill: white; "
        + "-fx-font-style: italic; -fx-font-weight: bold; -fx-font-size: 20px; "
        + "-fx-border-radius: 22px; -fx-font-family: Futura; "
        + "-fx-background-radius: 22px; -fx-border-color: #3e4756;");

    connectionDetails = new Label();
    connectionDetails
        .setStyle("-fx-background-color: #a6ddff; -fx-text-fill: black; -fx-font-style: italic; "
            + "-fx-font-size: 20px; -fx-border-radius: 22px; -fx-font-family: Calibri; "
            + "-fx-background-radius: 22px; -fx-border-color: #3e4756; -fx-font-weight: bold;");

    connectionDetails.setPrefHeight(60);
    connectionDetails.setPrefWidth(400);

    ready.setPrefHeight(60);
    ready.setPrefWidth(400);

    ready.setTranslateX(-416);
    ready.setTranslateY(265);

    connectionDetails.setTranslateX(-416);
    connectionDetails.setTranslateY(-190);

    ready.setOnMouseEntered(mouseEvent -> {
      ready.setStyle("-fx-background-color: white; -fx-text-fill: #004e8b; "
          + "-fx-font-style: italic; -fx-font-weight: bold; -fx-font-size: 20px; "
          + "-fx-border-radius: 22px; -fx-font-family: Futura; "
          + "-fx-background-radius: 22px; -fx-border-color: #3e4756;");
    });

    ready.setOnMouseExited(mouseEvent -> {
      ready.setStyle("-fx-background-color: #004e8b; -fx-text-fill: white; "
          + "-fx-font-style: italic; -fx-font-weight: bold; -fx-font-size: 20px; "
          + "-fx-border-radius: 22px; -fx-font-family: Futura; "
          + "-fx-background-radius: 22px; -fx-border-color: #3e4756;");
    });

    ready.setOnAction(actionEvent -> {
      clientController.setReady();
    });
  }

  /**
   * Colors the labels of the players that have clicked on the ready Button.
   * 
   * @param name the name of the player that has clicked on ready.
   */
  public void colorReadyPlayers(String name) {

    if (name.equals(playerNames[1].getText())) {
      playerNames[1].setTextFill(GuiSettings.GREEN);
    } else if (name.equals(playerNames[2].getText())) {
      playerNames[2].setTextFill(GuiSettings.GREEN);
    } else if (name.equals(playerNames[3].getText())) {
      playerNames[3].setTextFill(GuiSettings.GREEN);
    }

  }

  /**
   * Colors the labels of the players that have clicked on the ready Button again.
   * 
   * @param name the name of the player that has clicked on ready again.
   */
  public void colorNotReadyPlayers(String name) {

    if (name.equals(playerNames[1].getText())) {
      playerNames[1].setTextFill(GuiSettings.RED);
    } else if (name.equals(playerNames[2].getText())) {
      playerNames[2].setTextFill(GuiSettings.RED);
    } else if (name.equals(playerNames[3].getText())) {
      playerNames[3].setTextFill(GuiSettings.RED);
    }

  }

  /**
   * Initializes the lobbyName-Label.
   */
  private void initLobbyName() {
    lobbyName = hostGameLobby.getLobbyName();
    lobbyName.setStyle("-fx-background-color: transparent; -fx-text-fill: #2e75b6; "
        + "-fx-font-family: Futura; -fx-font-size: 20px; "
        + "-fx-font-weight: bold; -fx-font-style: italic;");

    lobbyName.setTranslateX(-430);
    lobbyName.setTranslateY(-260);
  }

  /**
   * Initializes the stage of this class.
   * 
   * @param stage the stage of this class.
   */
  private void initStage(Stage stage) {
    base.start(stage);
    pane = base.getStackPane();
    pane.getChildren().add(ready);
    pane.getChildren().add(connectionDetails);
    pane.getChildren().add(lobbyName);
    pane.getChildren().addAll(oneIv, twoIv, threeIv, fourIv);
    pane.getChildren().addAll(lastLine, statsHeadline, p1, p2, p3, p4);
    pane.getChildren().addAll(stats1, stats2, stats3, stats4);

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


    for (int i = 0; i < playerNames.length; i++) {
      pane.getChildren().add(playerNames[i]);
      playerNames[i].setTranslateX(-410);
    }
    playerNames[0].setTranslateY(-80);
    playerNames[1].setTranslateY(5);
    playerNames[2].setTranslateY(80);
    playerNames[3].setTranslateY(160);

    Label letterNotice = new Label("Only the host can decide\non the letter distribution.");
    Label dictNotice = new Label("Only the host can decide\non a dictionary.");

    letterNotice
        .setStyle("-fx-text-fill: #786bba; -fx-font-family: Futura; -fx-font-style: italic; "
            + "-fx-font-size: 15px; -fx-font-weight: bold;");
    dictNotice.setStyle("-fx-text-fill: #786bba; -fx-font-family: Futura; -fx-font-style: italic; "
        + "-fx-font-size: 15px; -fx-font-weight: bold;");

    letterNotice.setTextAlignment(TextAlignment.CENTER);
    dictNotice.setTextAlignment(TextAlignment.CENTER);

    pane.getChildren().add(letterNotice);
    letterNotice.setTranslateY(225);
    letterNotice.setTranslateX(-5);

    pane.getChildren().add(dictNotice);
    dictNotice.setTranslateY(23);
    dictNotice.setTranslateX(-5);

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
   * Initializes the Labels of the player names.
   */
  private void initPlayerNames() {
    for (int i = 0; i < playerNames.length; i++) {
      playerNames[i] = new Label();
      playerNames[i]
          .setStyle("-fx-text-fill: #7F7F7F; -fx-font-family: Futura; -fx-font-size: 24px; "
              + "-fx-font-weight: bold; -fx-font-style: italic;");
    }
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
   * Determines which player is sending a message.
   * 
   * @param name the name of the player that is sending a message.
   */
  public void msgIsFrom(String name) {
    if (name == playerNames[0].getText()) {
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

    if (!fromHost) {
      msgLabel.setStyle("-fx-background-color: #ffd976; -fx-font-size: 15px; "
          + "-fx-background-radius: 13px; -fx-border-radius: 16px; -fx-font-family: Calibri; "
          + "-fx-text-fill: black; -fx-padding: 10px; -fx-font-weight: bold;");
      msgBox.setAlignment(Pos.CENTER_RIGHT);

    } else {
      if ((playerNames[1].getText() != null) && (name.equals(playerNames[1].getText()))) {
        msgLabel.setStyle("-fx-background-color: #d3abec; -fx-font-size: 15px; "
            + "-fx-background-radius: 13px; -fx-border-radius: 16px; -fx-font-family: Calibri; "
            + "-fx-text-fill: white; -fx-padding: 10px; -fx-font-weight: bold;");

      } else if ((playerNames[2].getText() != null) && (name.equals(playerNames[2].getText()))) {
        msgLabel.setStyle("-fx-background-color: #339be0; -fx-font-size: 15px; "
            + "-fx-background-radius: 13px; -fx-border-radius: 16px; -fx-font-family: Calibri; "
            + "-fx-text-fill: white; -fx-padding: 10px; -fx-font-weight: bold;");

      } else {
        msgLabel.setStyle("-fx-background-color: #ffa8dc; -fx-font-size: 15px; "
            + "-fx-background-radius: 13px; -fx-border-radius: 16px; -fx-font-family: Calibri; "
            + "-fx-text-fill: white; -fx-padding: 10px; -fx-font-weight: bold;");
      }
      msgBox.setAlignment(Pos.CENTER_LEFT);
    }

    scrollPane.applyCss();
    scrollPane.layout();
    scrollPane.setVvalue(scrollPane.getVmax());
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
   * Sets the names of the joined players in the stats table.
   * 
   * @param index index of the player that has joined.
   * @param name name of the player that has joined.
   */
  public void setPlayerName(int index, String name) {
    if (index == 0) {
      p1.setText(name);
      p1.setStyle("-fx-text-fill: #2e75b6; -fx-font-family: Futura; -fx-font-size: 18px;");
    }
    if (index == 1) {
      p2.setText(name);
      p2.setStyle("-fx-text-fill: #2e75b6; -fx-font-family: Futura; -fx-font-size: 18px;");
    }
    if (index == 2) {
      p3.setText(name);
      p3.setStyle("-fx-text-fill: #2e75b6; -fx-font-family: Futura; -fx-font-size: 18px;");
    }
    if (index == 3) {
      p4.setText(name);
      p4.setStyle("-fx-text-fill: #2e75b6; -fx-font-family: Futura; -fx-font-size: 18px;");
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
