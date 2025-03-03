package main.gui;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.model.Scrabble;

/**
 * The window that appears whenever a game is to be started, to choose the order of the players.
 * 
 * @author raydin
 *
 */
public class PlayerOrderBox {

  /**
   * The StackPane object of this class.
   */
  private StackPane root;

  /**
   * The stage object to display the scene.
   */
  private Stage stage;

  /**
   * The base/background for alert text.
   */
  private Rectangle rect;

  /**
   * The alert message, can be edited based on alert type.
   */
  private String alert;

  /**
   * The label that constaints the message for the user.
   */
  private Label alertLabel;

  /**
   * The Box that contains the player names ComboBox and the order Buttons.
   */
  private HBox playerBox;

  /**
   * The Box that contains the chosenOrder.
   */
  private VBox orderBox;

  /**
   * The number of players that have joined.
   */
  private int numberOfPlayers;

  /**
   * The Button that the user clicks for a random order.
   */
  private Button randomOrder;

  /**
   * The ComboBox that contains the playerNames of the joined players.
   */
  private ComboBox<String> playerNames;

  /**
   * The Buttons to set the order of the players.
   */
  private Button[] orderButtons = new Button[Scrabble.MAX_PLAYER_COUNT];

  /**
   * The Labels that show the chosen order.
   */
  private Label[] chosenOrder = new Label[Scrabble.MAX_PLAYER_COUNT];

  /**
   * The Box that contains the random Button.
   */
  private HBox randomBox;

  /**
   * The player names of the joined players.
   */
  private String[] players = new String[Scrabble.MAX_PLAYER_COUNT];

  /**
   * The index that counts how many players have joined.
   */
  private int index;

  /**
   * The names of the added players.
   */
  private String[] addedNames;

  /**
   * The Button to click once an order has been decided on.
   */
  private Button enterChoice;

  /**
   * The Box that contains the chosen order.
   */
  private HBox choiceBox;

  /**
   * Style properties for when a orderButton is being entered with the mouse.
   */
  private final static String orderButtonsEntered =
      "-fx-font-family: Futura; -fx-font-size: 18px; -fx-text-fill: white; "
          + "-fx-background-color: #438cd0; -fx-background-radius: 20px; "
          + "-fx-border-radius: 20px;";

  /**
   * Style properties for when a orderButton is being exited with the mouse.
   */
  private final static String orderButtonsExited =
      "-fx-font-family: Futura; -fx-font-size: 18px; -fx-text-fill: white; "
          + "-fx-background-color: #2e75b6; -fx-background-radius: 20px; "
          + "-fx-border-radius: 20px;";

  /**
   * Style properties for when the randomOrder Button is being entered with the mouse.
   */
  private final static String randomOrderEntered =
      "-fx-font-family: Futura; -fx-font-size: 18px; -fx-text-fill: white; "
          + "-fx-background-color: #d274ab; -fx-background-radius: 20px; "
          + "-fx-border-radius: 20px;";

  /**
   * Style properties for when the randomOrder Button is being exited with the mouse.
   */
  private final static String randomOrderExited =
      "-fx-font-family: Futura; -fx-font-size: 18px; -fx-text-fill: white; "
          + "-fx-background-color: #c75296; -fx-background-radius: 20px; "
          + "-fx-border-radius: 20px;";

  /**
   * Style properties for when the enterChoice Button is being entered with the mouse.
   */
  private final static String enterChoiceEntered =
      "-fx-font-family: Futura; -fx-font-size: 18px; -fx-text-fill: white; "
          + "-fx-background-color: #9086c6; -fx-background-radius: 20px; "
          + "-fx-border-radius: 20px;";
  /**
   * Style properties for when the enterChoice Button is being exited with the mouse.
   */
  private final static String enterChoiceExited =
      "-fx-font-family: Futura; -fx-font-size: 18px; -fx-text-fill: white; "
          + "-fx-background-color: #786bba; -fx-background-radius: 20px; "
          + "-fx-border-radius: 20px;";

  /**
   * The constructor that sets the number of players and initializes the components.
   * 
   * @param numberOfPlayers the number of players that are playing.
   */
  public PlayerOrderBox(int numberOfPlayers) {
    this.numberOfPlayers = numberOfPlayers;
    init();
  }

  /**
   * Reverses all prior options.
   */
  public void reverse() {
    chosenOrder = new Label[Scrabble.MAX_PLAYER_COUNT];
    players = new String[Scrabble.MAX_PLAYER_COUNT];
    orderButtons[0].setDisable(true);
    orderButtons[1].setDisable(true);
    orderButtons[2].setDisable(true);
    orderButtons[3].setDisable(true);
  }

  /**
   * Returns the enter-Button.
   * 
   * @return the enterChoice-Button.
   */
  public Button getEnter() {
    return this.enterChoice;
  }

  /**
   * Returns the added names of the joined players.
   * 
   * @return the added names of the joined players.
   */
  public String[] getAddedNames() {
    return this.addedNames;
  }

  /**
   * Adds a joined player to the player names ComboBox.
   * 
   * @param name the name of the player that has joined.
   */
  public void setPlayerName(String name) {
    playerNames.getItems().add(name);
    chosenOrder[index].setText((index + 1) + ". Player: ");
    orderButtons[index].setDisable(false);
    players[index] = name;
    index++;
  }

  /**
   * Initializes all components.
   */
  private void init() {
    initRectangle();
    initComponents();
    initComponents2();
    initComponents3();
    initRandomButton();
    setActionRandom();
    setActionChoice();
    setDesignActions();
  }

  /**
   * Initializes the rectangle at the background.
   */
  public void initRectangle() {
    rect = new Rectangle();
    rect.setHeight(500);
    rect.setWidth(500);
    rect.setFill(Color.WHITE);
    rect.setStroke(GuiSettings.BLUE);
  }

  /**
   * Sets the message that is going to be displayed.
   */
  public void setAlert() {
    alertLabel = new Label(alert);

    alertLabel.setPrefWidth(400);
    alertLabel.setAlignment(Pos.CENTER);
    alertLabel.setStyle("-fx-text-fill: #2e75b6; -fx-font-family: Futura; "
        + "-fx-font-size: 18px; -fx-background-radius: 10px;");
    alertLabel.setWrapText(true);
  }

  /**
   * Initializes the Button-objects and sets their Event-Handlers.
   */
  private void initComponents() {
    playerBox = new HBox();
    playerBox.setSpacing(15);

    playerNames = new ComboBox<String>();

    playerNames.setMaxHeight(280);
    playerNames.setPrefWidth(400);
    playerNames.setStyle("-fx-font-family: Futura; -fx-font-size: 18px; -fx-text-fill: #7F7F7F; "
        + "-fx-font-style: italic; -fx-background-color: #F2F2F2; " + "-fx-border-color: #A6A6A6;");

    for (int i = 0; i < orderButtons.length; i++) {
      orderButtons[i] = new Button((i + 1) + ". Player");
      orderButtons[i].setDisable(true);
      orderButtons[i]
          .setStyle("-fx-font-family: Futura; -fx-font-size: 18px; -fx-text-fill: white; "
              + "-fx-background-color: #2e75b6; -fx-background-radius: 20px; "
              + "-fx-border-radius: 20px;");
      playerBox.getChildren().add(orderButtons[i]);
    }
  }

  /**
   * Initializes the random Button and adds it to the randomBox.
   */
  private void initRandomButton() {
    randomBox = new HBox();
    randomBox.setPrefWidth(400);
    randomOrder = new Button("Set random player order");
    randomOrder.setAlignment(Pos.CENTER);
    randomOrder.setTextAlignment(TextAlignment.CENTER);
    randomOrder.setStyle("-fx-font-family: Futura; -fx-font-size: 18px; -fx-text-fill: white; "
        + "-fx-background-color: #c75296; -fx-background-radius: 20px; "
        + "-fx-border-radius: 20px;");
    randomBox.getChildren().add(randomOrder);
    randomBox.setAlignment(Pos.CENTER);
  }

  /**
   * Initializes the order choices.
   */
  private void initComponents2() {
    orderBox = new VBox();
    orderBox.setSpacing(15);
    for (int i = 0; i < chosenOrder.length; i++) {
      chosenOrder[i] = new Label();

      chosenOrder[i]
          .setStyle("-fx-font-family: Futura; -fx-font-size: 18px; -fx-text-fill: #2e75b6; "
              + "-fx-background-radius: 5px; " + "-fx-border-radius: 5px;");
      orderBox.getChildren().add(chosenOrder[i]);
    }
  }

  /**
   * Initialzes the Button to click when an individual order has been decided on.
   */
  private void initComponents3() {
    choiceBox = new HBox();
    choiceBox.setPrefWidth(400);
    choiceBox.setAlignment(Pos.CENTER);

    enterChoice = new Button("Click to enter the order of  the players");
    enterChoice.setAlignment(Pos.CENTER);
    enterChoice.setTextAlignment(TextAlignment.CENTER);
    enterChoice.setStyle("-fx-font-family: Futura; -fx-font-size: 18px; -fx-text-fill: white; "
        + "-fx-background-color: #786bba; -fx-background-radius: 20px; "
        + "-fx-border-radius: 20px;");
    choiceBox.getChildren().add(enterChoice);

  }

  /**
   * Initializes the stage.
   */
  private void initStage() {
    stage = new Stage();
    stage.initModality(Modality.NONE);
    stage.setTitle("Set Player Order");

    setAlert();

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setVgap(12);
    GridPane.setConstraints(alertLabel, 0, 0);
    GridPane.setConstraints(playerNames, 0, 1);
    GridPane.setConstraints(playerBox, 0, 2);
    GridPane.setConstraints(orderBox, 0, 3);
    GridPane.setConstraints(choiceBox, 0, 4);
    GridPane.setConstraints(randomBox, 0, 5);

    grid.getChildren().addAll(alertLabel, playerNames, playerBox, orderBox, choiceBox, randomBox);


    root = new StackPane();
    root.setAlignment(Pos.CENTER);

    root.getChildren().add(rect);
    root.getChildren().add(grid);

    Scene scene = new Scene(root, 510, 510);

    stage.setOnCloseRequest(actionEvent -> {
      actionEvent.consume();
      closeApp();
    });

    stage.setScene(scene);
    stage.showAndWait();
  }

  /**
   * Sets the event when the random order Button is clicked.
   */
  private void setActionRandom() {
    randomOrder.setOnAction(actionEvent -> {
      ArrayList<Integer> previous = new ArrayList<Integer>();
      int rand = (int) (Math.random() * numberOfPlayers);
      int i = 0;
      while (i < numberOfPlayers) {
        if (!previous.contains(rand)) {
          previous.add(rand);
          i++;
        } else {
          rand = (int) (Math.random() * numberOfPlayers);
        }
      }
      for (int j = 0; j < numberOfPlayers; j++) {
        chosenOrder[j].setText((j + 1) + ". Player: " + players[previous.get(j)]);
      }

      addedNames = new String[numberOfPlayers];
      for (int j = 0; j < addedNames.length; j++) {
        addedNames[j] = chosenOrder[j].getText().substring(11);
        System.out.println(addedNames[j]);
      }

      this.stage.close();
    });
  }

  /**
   * Sets the events of the buttons when an individual player order is being decided on.
   */
  private void setActionChoice() {
    orderButtons[0].setOnAction(actionEvent -> {
      if (!playerNames.getValue().isEmpty()) {
        chosenOrder[0].setText("1. Player: " + playerNames.getValue());
      }
    });

    orderButtons[1].setOnAction(actionEvent -> {
      if (!playerNames.getValue().isEmpty()) {
        chosenOrder[1].setText("2. Player: " + playerNames.getValue());
      }
    });

    orderButtons[2].setOnAction(actionEvent -> {
      if (!playerNames.getValue().isEmpty()) {
        chosenOrder[2].setText("3. Player: " + playerNames.getValue());
      }
    });

    orderButtons[3].setOnAction(actionEvent -> {
      if (!playerNames.getValue().isEmpty()) {
        chosenOrder[3].setText("4. Player: " + playerNames.getValue());
      }
    });

    enterChoice.setOnAction(actionEvent -> {
      index = 0;
      ErrorBox error = new ErrorBox();
      int count = 0;
      addedNames = new String[playerNames.getItems().size()];
      for (int i = 0; i < addedNames.length; i++) {
        addedNames[i] = chosenOrder[i].getText().substring(11);
        System.out.println(addedNames[i]);
      }

      for (int i = 0; i < addedNames.length; i++) {
        if (addedNames[i].equals("")) {
          count++;
        }
      }

      if (count == numberOfPlayers) {
        error.displayError("Please either choose the random- or your own order.");
      } else {

        boolean multiple = false;
        boolean empty = false;
        for (int i = 0; i < addedNames.length; i++) {
          for (int j = 0; j < addedNames.length; j++) {
            if (addedNames[i].isEmpty()) {
              empty = true;
            }
            if ((i != j) && addedNames[i].equals(addedNames[j])) {
              multiple = true;
            }
          }
          if (multiple) {
            if (empty) {
              error.displayError("All players must be assigned.");
              empty = false;
            } else {
              error.displayError("Only one player can be assigned to each spot.");
              multiple = false;
            }
            break;
          } else {
            this.stage.close();
          }
        }
      }
    });
  }

  /**
   * Sets the mouse events for all buttons.
   */
  private void setDesignActions() {

    orderButtons[0].setOnMouseEntered(mouseEvent -> {
      orderButtons[0].setStyle(orderButtonsEntered);
    });
    orderButtons[0].setOnMouseExited(mouseEvent -> {
      orderButtons[0].setStyle(orderButtonsExited);
    });

    orderButtons[1].setOnMouseEntered(mouseEvent -> {
      orderButtons[1].setStyle(orderButtonsEntered);
    });
    orderButtons[1].setOnMouseExited(mouseEvent -> {
      orderButtons[1].setStyle(orderButtonsExited);
    });

    orderButtons[2].setOnMouseEntered(mouseEvent -> {
      orderButtons[2].setStyle(orderButtonsEntered);
    });
    orderButtons[2].setOnMouseExited(mouseEvent -> {
      orderButtons[2].setStyle(orderButtonsExited);
    });

    orderButtons[3].setOnMouseEntered(mouseEvent -> {
      orderButtons[3].setStyle(orderButtonsEntered);
    });
    orderButtons[3].setOnMouseExited(mouseEvent -> {
      orderButtons[3].setStyle(orderButtonsExited);
    });

    randomOrder.setOnMouseEntered(mouseEvent -> {
      randomOrder.setStyle(randomOrderEntered);
    });

    randomOrder.setOnMouseExited(mouseEvent -> {
      randomOrder.setStyle(randomOrderExited);
    });

    enterChoice.setOnMouseEntered(mouseEvent -> {
      enterChoice.setStyle(enterChoiceEntered);
    });

    enterChoice.setOnMouseExited(mouseEvent -> {
      enterChoice.setStyle(enterChoiceExited);
    });
  }

  /**
   * Determines, based on the user interaction, whether the application should be closed or not.
   */
  private void closeApp() {
    AlertBox ab = new AlertBox();

    boolean answer = ab.getAnswer();
    if (addedNames != null) {

      ab.displayAlert("Are you done with the setting of the order? If you didn't "
          + "set the order, a random order will be chosen.");
    }
    if (answer) {
      stage.close();
    }
  }

  /**
   * Displays the alert-Window.
   * 
   * @param alertMsg the alert message-text on the alert box.
   */
  public void displayAlert(String alertMsg) {
    alert = alertMsg;
    initStage();
  }
}
