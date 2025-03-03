package main.gui;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.controller.NetworkGameController;
import main.database.Profile;
import main.model.Scrabble;

/**
 * Represents the InGame session of a network game.
 * 
 * @author ceho
 *
 */

public class InGameNetwork extends InGame {
  // chat
  /**
   * StackPane object for the chat.
   */
  private StackPane chatPane;

  /**
   * TextField to type a new message.
   */
  TextField messageInput;

  /**
   * GridPane object to order the chat messages.
   */
  private GridPane msgGrid;

  /**
   * ScrollPane object for the chat window.
   */
  private ScrollPane scrollPane;

  /**
   * ArrayList of Labels to store the message labels.
   */
  private ArrayList<Label> messages;

  /**
   * Counter for the messages.
   */
  private int msgCount = 0;

  /**
   * Button to send a new message.
   */
  private Button send;

  // letters, time
  /**
   * StackPane object for the timer and bag of tiles.
   */
  private StackPane timePane;

  /**
   * Label to display "TIMER".
   */
  private Label timer;

  // action
  /**
   * StackPane object for the action buttons (pass and change tiles).
   */
  private StackPane actionPane;

  /**
   * NetworkGameController
   */
  private NetworkGameController netController;

  /**
   * Calls the super setGame method and sets the timer.
   */
  public void setGame(Scrabble game) {
    super.setGame(game);
    countdownTimer.setGame(game);
    countdownTimer.start();
    // if (!msgGrid.getChildren().isEmpty()) {
    // msgGrid.getChildren().removeAll(msgGrid);
    // messages.clear();
    // }
  }

  /**
   * Sets the NetworkGameController for the chat.
   * 
   * @param netController.
   */
  public void setController(NetworkGameController netController) {
    this.netController = netController;
  }

  /**
   * Calls the super method and initializes all methods in this class.
   */
  void init(Stage stage) {
    super.init(stage);
    initChatPanel();
    initTimeLetters();
    initAction();
    setActionChat();
    initStage(stage);
  }

  /**
   * Initializes and sets the panel on which the chat is going to be displayed.
   */
  private void initChatPanel() {
    Rectangle chatRect = new Rectangle();
    chatRect.setWidth(width * 0.20833); // 300
    chatRect.setHeight(height * 0.66111); // 595
    chatRect.setArcWidth(30);
    chatRect.setArcHeight(30);
    chatRect.setFill(GuiSettings.GRAY_FILL);
    chatRect.setStroke(GuiSettings.GRAY_BORDER);

    messageInput = new TextField();
    messageInput.setStyle("-fx-background-color: white;" + "-fx-border-color: #A6A6A6;"
        + "-fx-background-radius: 20px;" + "-fx-border-radius: 20px;" + "-fx-text-fill: #7f7f7f;"
        + "-fx-font-family: Calibri;" + "-fx-font-size: 15px;"); // radius-20px-1.538em;
    // font-15px-1.154em

    messageInput.setPromptText("Enter your message...");
    messageInput.setMaxHeight(height * 0.04); // 40-height * 0.04443 / 36
    messageInput.setMinWidth(width * 0.16667); // 240

    Image sendIcon =
        new Image("file:res/images/send.PNG", width * 0.0368, width * 0.0368, true, true);
    // 50/50-width * 0.03472 / 53/53
    ImageView ivSend = new ImageView(sendIcon);

    send = new Button();
    send.setGraphic(ivSend);
    send.setStyle("-fx-background-color: transparent;");

    HBox sendingBox = new HBox();
    sendingBox.getChildren().add(messageInput);
    sendingBox.getChildren().add(send);
    sendingBox.setMaxHeight(height * 0.05); // 45
    sendingBox.setMaxWidth(width * 0.20833); // 300
    sendingBox.setAlignment(Pos.CENTER_LEFT);

    Label chatHeadline = new Label("C H A T");
    chatHeadline.setTextAlignment(TextAlignment.CENTER);
    chatHeadline.setAlignment(Pos.CENTER);
    chatHeadline.setPrefWidth(width * 0.19444); // 280
    chatHeadline.setStyle("-fx-font-family: Futura;" + "-fx-font-size: 15px;"
        + "-fx-background-color: white; " + "-fx-text-fill: #EABED7;"
        + "-fx-background-radius: 20px; -fx-border-radius: 20px; -fx-font-weight: bold;");
    msgGrid = new GridPane();
    msgGrid.setPadding(new Insets(width * 0.00694, width * 0.00694, 0, width * 0.00694));
    // 10/10/0/10
    msgGrid.setVgap(height * 0.005556); // 5
    msgGrid.add(chatHeadline, 0, 0);

    scrollPane = new ScrollPane();
    scrollPane.setContent(msgGrid);
    scrollPane.pannableProperty().set(true);
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setMaxHeight(height * 0.6); // 540
    scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");

    chatPane = new StackPane();
    chatPane.setAlignment(Pos.BASELINE_LEFT);
    chatPane.getChildren().add(chatRect);
    chatPane.getChildren().add(sendingBox);
    messageInput.setTranslateY((width * 0.00069)); // 1-height * 0.0011
    messageInput.setTranslateX(width * 0.00694); // 10

    messages = new ArrayList<Label>();
  }

  /**
   * Sets a new message and displays it in the chat window.
   * 
   * @param sentBy String of the sender's name.
   * @param msg String that contains the message.
   */
  public void receiveChat(String sentBy, String msg) {
    Label sender = new Label(sentBy);
    sender.setStyle("-fx-font-family: Calibri; -fx-font-size: 13px; -fx-text-fill: #7F7F7F;"
        + "-fx-padding: 2px;");
    Label message = new Label(msg);
    message.setWrapText(true);

    if (msg.length() > 28) {
      message.setMaxWidth(width * 0.17361); // 250
    }

    messages.add(message);

    VBox msgBox = new VBox();
    msgBox.getChildren().add(sender);
    msgBox.getChildren().add(messages.get(msgCount));

    msgGrid.add(msgBox, 0, msgCount + 1);

    int exception = -1;
    int playerCount = 0;
    for (int i = 0; i < players.length; i++) {
      if (players[i].getText().equals(Profile.getOnlineName())) {
        exception = i;
      }
      if (!players[i].getText().equals("-")) {
        playerCount++;
      }
    }


    if (sentBy.equals(Profile.getOnlineName())) {
      message.setStyle("-fx-font-family: Calibri;" + "-fx-background-radius: 13px;"
          + "-fx-font-size: 15px;" + "-fx-background-color: #F7E899; " + "-fx-text-fill: black;"
          + "-fx-border-radius: 13px;" + "-fx-padding: 10px;");
      msgBox.setAlignment(Pos.CENTER_RIGHT);
    } else if (players[(exception + 1) % playerCount] != null
        && players[(exception + 1) % playerCount].getText().equals(sentBy)) {
      message.setStyle("-fx-font-family: Calibri;" + "-fx-background-radius: 13px;"
          + "-fx-font-size: 15px;" + "-fx-background-color: #D3ABEC; " + "-fx-text-fill: white;"
          + "-fx-border-radius: 13px;" + "-fx-padding: 10px;");
      msgBox.setAlignment(Pos.CENTER_LEFT);
    } else if (players[(exception + 2) % playerCount] != null
        && players[(exception + 2) % playerCount].getText().equals(sentBy)) {
      message.setStyle("-fx-font-family: Calibri;" + "-fx-background-radius: 13px;"
          + "-fx-font-size: 15px;" + "-fx-background-color: #79B5EA; " + "-fx-text-fill: white;"
          + "-fx-border-radius: 13px;" + "-fx-padding: 10px;");
      msgBox.setAlignment(Pos.CENTER_LEFT);
    } else if (players[(exception + 3) % playerCount] != null
        && players[(exception + 3) % playerCount].getText().equals(sentBy)) {
      message.setStyle("-fx-font-family: Calibri;" + "-fx-background-radius: 13px;"
          + "-fx-font-size: 15px;" + "-fx-background-color: #F7B5DE; " + "-fx-text-fill: white;"
          + "-fx-border-radius: 13px;" + "-fx-padding: 10px;");
      msgBox.setAlignment(Pos.CENTER_LEFT);
    }

    msgCount++;

    scrollPane.applyCss();
    scrollPane.layout();
    scrollPane.setVvalue(scrollPane.getVmax());
  }

  /**
   * Initializes and sets the panel on which the timer and bag of tiles are going to be displayed.
   */
  private void initTimeLetters() {
    Rectangle timeRect = new Rectangle();
    timeRect.setWidth(width * 0.20833); // 300
    timeRect.setHeight(height * 0.16111); // 145
    timeRect.setArcWidth(30);
    timeRect.setArcHeight(30);
    timeRect.setFill(GuiSettings.GRAY_FILL);
    timeRect.setStroke(GuiSettings.GRAY_BORDER);

    Label timeLeft = new Label("TIME LEFT");
    timeLeft.setPrefSize(width * 0.0833, height * 0.05556); // 120/50
    timeLeft.setAlignment(Pos.CENTER);
    timeLeft.setStyle("-fx-font-family: Futura;" + "-fx-background-radius: 10px;"
        + "-fx-font-size: 20px; -fx-background-color: #2e75b6; -fx-border-color: #A6A6A6;"
        + "-fx-text-fill: white; -fx-border-radius: 10px;"); // radius-10px-0.769em;
    // font-20px-1.538em

    timer = new Label();
    timer.setPrefSize(width * 0.0833, height * 0.05556); // 120/50
    timer.setAlignment(Pos.CENTER);
    timer.setStyle("-fx-font-family: Futura;" + "-fx-background-radius: 10px;"
        + "-fx-font-size: 20px;" + "-fx-background-color: white; " + "-fx-border-color: #2e75b6;"
        + "-fx-text-fill: #2e75b6;" + "-fx-border-radius: 10px;");
    countdownTimer = new CountdownTimer(timer);

    GridPane timeGrid = new GridPane();
    timeGrid.setAlignment(Pos.CENTER);
    timeGrid.setVgap(height * 0.016667); // 15
    timeGrid.setHgap(height * 0.016667);// 15
    timeGrid.add(timeLeft, 0, 0);
    timeGrid.add(letters, 0, 1);
    timeGrid.add(timer, 1, 0);
    timeGrid.add(bagOfTiles, 1, 1);

    timePane = new StackPane();
    timePane.getChildren().add(timeRect);
    timePane.getChildren().add(timeGrid);
  }

  /**
   * Initializes and sets the panel on which the buttons pass and change are going to be displayed.
   */
  private void initAction() {
    Rectangle actionRect = new Rectangle();
    actionRect.setWidth(width * 0.20833); // 300
    actionRect.setHeight(height * 0.08889); // 80
    actionRect.setArcWidth(30);
    actionRect.setArcHeight(30);
    actionRect.setFill(GuiSettings.GRAY_FILL);
    actionRect.setStroke(GuiSettings.GRAY_BORDER);

    pass.setPrefSize(width * 0.0833, height * 0.05556); // 120/50

    change.setPrefSize(width * 0.0833, height * 0.05556); // 120/50

    GridPane actionGrid = new GridPane();
    actionGrid.setAlignment(Pos.CENTER);
    actionGrid.setVgap(height * 0.016667); // 15
    actionGrid.setHgap(height * 0.016667);// 15
    actionGrid.add(pass, 0, 0);
    actionGrid.add(change, 1, 0);

    actionPane = new StackPane();
    actionPane.getChildren().add(actionRect);
    actionPane.getChildren().add(actionGrid);
  }

  /**
   * Initializes the stage and arranges the object on the stackpane.
   */
  @Override
  void initStage(Stage stage) {
    root = new StackPane();

    root.getChildren().add(ivBackground);
    root.getChildren().add(rectangle);

    GridPane gamePane = new GridPane();
    gamePane.setAlignment(Pos.CENTER);
    gamePane.setVgap(height * 0.016667); // 15
    gamePane.setHgap(width * 0.01015976); // 23
    gamePane.add(timePane, 0, 0);
    gamePane.add(leadPane, 0, 1);
    gamePane.add(actionPane, 0, 1, 1, 2);
    gamePane.add(gameGui.getGameGui(), 1, 0, 1, 2); // 600x600
    gamePane.add(rackPane, 1, 2);
    gamePane.add(logout, 2, 0);
    gamePane.add(headline, 2, 0);
    gamePane.add(chatPane, 2, 0, 1, 3);
    gamePane.add(scrollPane, 2, 0, 1, 3);

    leadPane.setTranslateY(-(height * 0.01)); // -9
    actionPane.setTranslateY(height * 0.242221); // 218
    logout.setTranslateY(-(height * 0.05556)); // -50
    logout.setTranslateX(width * 0.16875); // 243
    headline.setTranslateY(-(height * 0.05556)); // -50
    chatPane.setTranslateY(height * 0.08889); // 80
    scrollPane.setTranslateY(height * 0.01333); // 12

    root.getChildren().add(gamePane);
  }

  /**
   * Sets ActionEvents for the chat.
   */
  private void setActionChat() {
    messageInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
          String message = messageInput.getText();

          if (!message.isBlank()) {
            netController.sendChatGame(message);
            receiveChat(Profile.getOnlineName(), message);
            messageInput.setText(null);
            // scrollPane.applyCss();
            // scrollPane.layout();
            // scrollPane.setVvalue(scrollPane.getVmax());
          }
        }
      }
    });

    send.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        String message = messageInput.getText();

        if (!message.isBlank()) {
          netController.sendChatGame(message);
          receiveChat(Profile.getOnlineName(), message);
          messageInput.setText(null);
          // scrollPane.applyCss();
          // scrollPane.layout();
          // scrollPane.setVvalue(scrollPane.getVmax());
        }
      }
    });

    send.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        send.setCursor(Cursor.HAND);
      }
    });

    messageInput.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
          Boolean newPropertyValue) {
        if (newPropertyValue) {
          // on focus
          if (messageInput.getText().isBlank()) {
            messageInput.clear();
          }
          messageInput.setStyle("-fx-background-color: white; -fx-border-color: #2e75b6; "
              + "-fx-background-radius: 20px; -fx-border-radius: 20px; "
              + "-fx-text-fill: #7f7f7f; -fx-font-family: Calibri; -fx-font-size: 15px;");
        } else {
          // out focus
          messageInput.setStyle("-fx-background-color: white; -fx-border-color: #A6A6A6; "
              + "-fx-background-radius: 20px; -fx-border-radius: 20px; "
              + "-fx-text-fill: #7f7f7f; -fx-font-family: Calibri; -fx-font-size: 15px;");
        }
      }
    });
  }
}
