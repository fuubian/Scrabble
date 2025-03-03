package main.gui;

import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * This class represents the lobby before the game starts when a user plays the tutorial mode.
 * 
 * @author raydin
 *
 */
public class TutorialGameLobby {
  /**
   * Stage object to set the Scene.
   */
  private Stage stage = new Stage();

  /**
   * The ErrorBox-object that appears when user inputs more than 10 points.
   */
  private ErrorBox error;

  /**
   * StackPane object for displaying the Scene.
   */
  private StackPane root;

  /**
   * The background image of the Scene.
   */
  private Image background;

  /**
   * The ImageView object to store the image for the background in.
   */
  private ImageView iv;

  /**
   * The base rectangle behind all components and on top of the background image.
   */
  private Rectangle rectangle;

  /**
   * The rectangle for the player details table.
   */
  private Rectangle playerRect;

  /**
   * The rectangle for the dictionary details.
   */
  private Rectangle dictRect;

  /**
   * The rectangle for the letter distribution details.
   */
  private Rectangle letterRect;

  /**
   * The rectangle for the statistics table.
   */
  private Rectangle statsRect;

  /**
   * The "Dictionary" label.
   */
  private Label dictionary;

  /**
   * The "Letter Distribution" label.
   */
  private Label letterDist;

  /**
   * The label that represents the player name.
   */
  private Label playerName;

  /**
   * The button to start the game.
   */
  private Button startGame;

  /**
   * The button to log-out of the game.
   */
  private Button lo;

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
   * The labels that represent the easy AI players' names.
   */
  private Label[] easyBotLabel = new Label[3];

  /**
   * The labels that represent the hard AI players' names.
   */
  private Label[] hardBotLabel = new Label[3];

  /**
   * The headline on top of the statistics table.
   */
  private Label statistics = new Label("Statistics");

  /**
   * The player statistics headlines.
   */
  private Label[] playerStats = new Label[6];

  /**
   * The values of the statistics.
   */
  private Label[] values = new Label[6];

  /**
   * The lines for the statistics table.
   */
  private Line[] lines = new Line[6];

  /**
   * The vertical line in the statistics table.
   */
  private Line vertLine;

  /**
   * The GridPane for the panel on the left side.
   */
  private GridPane paneLeft;

  /**
   * The GridPane for the panel on the right side.
   */
  private GridPane paneRight;

  /**
   * The GridPane that stores all other GridPane objects (left and right).
   */
  private GridPane firstPane;

  /**
   * The ImageView object to display the Image of the number of the first player in the player
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
   * The ComboBox for the dictionary choice.
   */
  private ComboBox<String> dictChoice;

  /**
   * The ComboBox for the letter choice.
   */
  private ComboBox<Character> letterChoice;

  /**
   * The ComboBox for the amount choice.
   */
  private ComboBox<Integer> amountChoice;

  /**
   * The ComboBox for the value choice.
   */
  private ComboBox<Integer> valueChoice;

  /**
   * The flag to determine if points are being set right now.
   */
  @SuppressWarnings("unused")
  private boolean toSetPoints = false;

  /**
   * The letter in the letterChoice-ComboBox that has been chosen.
   */
  @SuppressWarnings("unused")
  private char chosenLetter;

  /**
   * The Button to enter one letter-distribution choice.
   */
  private Button choices;

  /**
   * The number of points that have been entered into the value-TextField.
   */
  @SuppressWarnings("unused")
  private int inputPoints;

  /**
   * The constructor of the TutorialGameLobby class. Invokes the init method.
   */
  public TutorialGameLobby() {
    init(this.stage);
  }

  /**
   * Returns the startGame button.
   * 
   * @return the startGame button.
   */
  public Button getStartButton() {
    return this.startGame;
  }

  /**
   * Returns the logout/leave game Button.
   * 
   * @return the logout/leave Button.
   */
  public Button getLogOut() {
    return this.lo;
  }

  /**
   * Returns the StackPane object of this class.
   * 
   * @return the StackPane object of this class.
   */
  public StackPane getStackPane() {
    return this.root;
  }

  /**
   * Returns the name of the host player.
   * 
   * @return the name of the host player.
   */
  public Label getPlayerName() {
    return this.playerName;
  }

  /**
   * Returns the Label of easyBot 1.
   * 
   * @return the Label of easyBot 1.
   */
  public Label getEasyBot1() {
    return this.easyBotLabel[0];
  }

  /**
   * Returns the Label of easyBot 2.
   * 
   * @return the Label of easyBot 2.
   */
  public Label getEasyBot2() {
    return this.easyBotLabel[1];
  }

  /**
   * Returns the Label of easyBot 3.
   * 
   * @return the Label of easyBot 3.
   */
  public Label getEasyBot3() {
    return this.easyBotLabel[2];
  }

  /**
   * Returns the Label of hardBot 1.
   * 
   * @return the Label of hardBot 1.
   */
  public Label getHardBot1() {
    return this.hardBotLabel[0];
  }

  /**
   * Returns the Label of hardBot 2.
   * 
   * @return the Label of hardBot 2.
   */
  public Label getHardBot2() {
    return this.hardBotLabel[1];
  }

  /**
   * Returns the Label of hardBot 3.
   * 
   * @return the Label of hardBot 3.
   */
  public Label getHardBot3() {
    return this.hardBotLabel[2];
  }

  /**
   * Returns the easy bot labels.
   * 
   * @return the array of the easy bot labels.
   */
  public Label[] getEasyBotLabels() {
    return this.easyBotLabel;
  }

  /**
   * Returns the hard bot labels.
   * 
   * @return the array of the hard bot labels.
   */
  public Label[] getHardBotLabels() {
    return this.hardBotLabel;
  }

  /**
   * Returns the dictionary choice ComboBox.
   * 
   * @return the dictionary choice ComboBox.
   */
  public ComboBox<String> getDictChoice() {
    return this.dictChoice;
  }

  /**
   * Returns the letter choice ComboBox.
   * 
   * @return the letter choice ComboBox.
   */
  public ComboBox<Character> getLetterChoice() {
    return this.letterChoice;
  }

  /**
   * Returns the value choice ComboBox.
   * 
   * @return the value choice ComboBox.
   */
  public ComboBox<Integer> getValueChoice() {
    return this.valueChoice;
  }

  /**
   * Returns the amount choice ComboBox.
   * 
   * @return the amount choice ComboBox.
   */
  public ComboBox<Integer> getAmountChoice() {
    return this.amountChoice;
  }

  /**
   * Initializes all components.
   * 
   * @param stage the Stage object to be initialized
   */
  private void init(Stage stage) {
    initBackground();
    optionsBackground();
    initRectangles();
    initPanelLeft();
    initPanelRight();
    initBots();
    setActionEvents();
    initStatsLabels();
    initChoicesButton();
    initStage(stage);
  }

  /**
   * Initializes the background image; bckgrnd.png is a free to use, copyright free image from
   * https://pixabay.com/images/id-691842/ saved on: 31st of March, 2021 at 10:32 pm.
   */
  private void initBackground() {
    background = new Image("file:res/images/bckgrnd.png");
    iv = new ImageView(background);
    iv.setFitHeight(1080);
    iv.setFitWidth(1920);
  }

  /**
   * Initializes and sets the base rectangle in the background of this stage.
   */
  private void optionsBackground() {
    rectangle = new Rectangle();
    rectangle.setWidth(1350);
    rectangle.setHeight(680);
    rectangle.setArcWidth(20);
    rectangle.setArcHeight(20);

    rectangle.setFill(Color.WHITE);
    rectangle.setStroke(GuiSettings.BLUE);
  }

  /**
   * Initializes the Rectangles behind all components.
   */
  private void initRectangles() {
    playerRect = new Rectangle();
    playerRect.setWidth(440);
    playerRect.setHeight(320);
    playerRect.setArcWidth(20);
    playerRect.setArcHeight(20);
    playerRect.setFill(GuiSettings.GRAY_FILL);
    playerRect.setStroke(GuiSettings.GRAY_BORDER);

    playerRect.setTranslateX(-380);

    statsRect = new Rectangle();
    statsRect.setWidth(640);
    statsRect.setHeight(320);
    statsRect.setArcWidth(20);
    statsRect.setArcHeight(20);
    statsRect.setFill(GuiSettings.GRAY_FILL);
    statsRect.setStroke(GuiSettings.GRAY_BORDER);

    statsRect.setTranslateX(280);
    statsRect.setTranslateY(-60);

    dictRect = new Rectangle();
    dictRect.setWidth(300);
    dictRect.setHeight(130);
    dictRect.setArcWidth(20);
    dictRect.setArcHeight(20);
    dictRect.setFill(GuiSettings.GRAY_FILL);
    dictRect.setStroke(GuiSettings.GRAY_BORDER);

    dictRect.setTranslateX(110);
    dictRect.setTranslateY(200);

    letterRect = new Rectangle();
    letterRect.setWidth(300);
    letterRect.setHeight(150);
    letterRect.setArcWidth(20);
    letterRect.setArcHeight(20);
    letterRect.setFill(GuiSettings.GRAY_FILL);
    letterRect.setStroke(GuiSettings.GRAY_BORDER);

    letterRect.setTranslateX(450);
    letterRect.setTranslateY(200);

    dictionary = new Label("Dictionary");
    dictionary.setStyle("-fx-text-fill: #7f7f7f; -fx-font-family: Futura; -fx-font-size: 25px;");

    dictionary.setTranslateX(35);
    dictionary.setTranslateY(170);

    letterDist = new Label("Letter Distribution");
    letterDist.setStyle("-fx-text-fill: #7f7f7f; -fx-font-family: Futura; -fx-font-size: 25px;");

    letterDist.setTranslateY(150);
    letterDist.setTranslateX(430);
  }

  /**
   * Initializes the panel on the left side.
   */
  private void initPanelLeft() {
    paneLeft = new GridPane();

    Image one = new Image("file:res/images/one.PNG", 75, 75, true, true);
    Image two = new Image("file:res/images/two.PNG", 70, 70, true, true);
    Image three = new Image("file:res/images/three.PNG", 78, 78, true, true);
    Image four = new Image("file:res/images/four.PNG", 70, 70, true, true);

    oneIv = new ImageView(one);
    twoIv = new ImageView(two);
    threeIv = new ImageView(three);
    fourIv = new ImageView(four);

    GridPane.setConstraints(oneIv, 0, 0);
    GridPane.setConstraints(twoIv, 1, 0);
    GridPane.setConstraints(threeIv, 2, 0);
    GridPane.setConstraints(fourIv, 3, 0);

    paneLeft.getChildren().addAll(oneIv, twoIv, threeIv, fourIv);
    oneIv.setTranslateY(-110);
    oneIv.setTranslateX(-270);

    twoIv.setTranslateY(-43);
    twoIv.setTranslateX(-710);

    threeIv.setTranslateY(28);
    threeIv.setTranslateX(-784);

    fourIv.setTranslateY(110);
    fourIv.setTranslateX(-853);

    Line[] lines = new Line[3];

    lines[0] = new Line(800, -350, 1235, -350);
    lines[0].setStroke(Color.WHITE);
    lines[1] = new Line(800, -250, 1235, -250);
    lines[1].setStroke(Color.WHITE);
    lines[2] = new Line(800, -250, 1235, -250);
    lines[2].setStroke(Color.WHITE);


    for (Line l : lines) {
      l.setStrokeWidth(3.0);
      paneLeft.getChildren().add(l);
      l.setTranslateX(-268);
    }

    lines[0].setTranslateY(-80);
    lines[1].setTranslateY(-5);
    lines[2].setTranslateY(80);

    Tooltip startGameTip = new Tooltip("Start the game.");
    startGameTip.setStyle("-fx-font-family: Futura; -fx-font-size: 15px; "
        + "-fx-background-color: #c7efff; -fx-border-radius: 22px; "
        + "-fx-background-radius: 22px; -fx-text-fill: black; -fx-set-stroke: black;");

    startGame = new Button("START GAME");
    startGame
        .setStyle("-fx-background-color: #2e75b6; -fx-text-fill: white; -fx-font-style: italic; "
            + "-fx-font-size: 20px; -fx-border-radius: 22px; -fx-font-family: Futura; "
            + "-fx-background-radius: 22px; -fx-border-color: #3e4756; -fx-font-weight: bold;");
    startGame.setPrefHeight(60);
    startGame.setPrefWidth(400);
    startGame.setTooltip(startGameTip);

    paneLeft.getChildren().add(startGame);

    startGame.setTranslateX(-250);
    startGame.setTranslateY(280);

    Line headline = new Line(800, 50, 1200, 50);
    headline.setStroke(GuiSettings.GRAY_BORDER);
    headline.setStrokeWidth(1.0);

    paneLeft.getChildren().add(headline);
    headline.setTranslateX(-250);
    headline.setTranslateY(-205);

    Label title = new Label("TUTORIAL MODE");
    title.setStyle("-fx-text-fill: #2e75b6; -fx-font-family: Futura; -fx-font-size: 35px; "
        + "-fx-font-style: italic; -fx-font-weight: bold;");

    paneLeft.getChildren().add(title);
    title.setTranslateX(-200);
    title.setTranslateY(-225);

    playerName = new Label("Player 1");
    playerName.setStyle("-fx-text-fill: #7f7f7f; -fx-font-family: Futura; "
        + "-fx-font-size: 30px; -fx-font-style: italic;");
    paneLeft.getChildren().add(playerName);

    playerName.setTranslateX(-190);
    playerName.setTranslateY(-115);

  }

  /**
   * Sets the ActionEvents/MouseEvents and EventHandlers of the buttons.
   */
  private void setActionEvents() {
    easyBots[0].setOnAction(actionEvent -> {
      firstPane.getChildren().removeAll(easyBots[0], hardBots[0]);
      easyBotLabel[0].setText("Easy Bot 1");
      firstPane.getChildren().addAll(easyBotLabel[0], removeBot[0]);
    });
    easyBots[1].setOnAction(actionEvent -> {
      firstPane.getChildren().removeAll(easyBots[1], hardBots[1]);
      easyBotLabel[1].setText("Easy Bot 2");

      firstPane.getChildren().addAll(easyBotLabel[1], removeBot[1]);
    });
    easyBots[2].setOnAction(actionEvent -> {
      firstPane.getChildren().removeAll(easyBots[2], hardBots[2]);
      easyBotLabel[2].setText("Easy Bot 3");

      firstPane.getChildren().addAll(easyBotLabel[2], removeBot[2]);
    });
    hardBots[0].setOnAction(actionEvent -> {
      firstPane.getChildren().removeAll(easyBots[0], hardBots[0]);
      hardBotLabel[0].setText("Hard Bot 1");

      firstPane.getChildren().addAll(hardBotLabel[0], removeBot[0]);
    });
    hardBots[1].setOnAction(actionEvent -> {
      firstPane.getChildren().removeAll(easyBots[1], hardBots[1]);
      hardBotLabel[1].setText("Hard Bot 2");
      firstPane.getChildren().addAll(hardBotLabel[1], removeBot[1]);
    });
    hardBots[2].setOnAction(actionEvent -> {
      firstPane.getChildren().removeAll(easyBots[2], hardBots[2]);
      hardBotLabel[2].setText("Hard Bot 3");
      firstPane.getChildren().addAll(hardBotLabel[2], removeBot[2]);
    });

    removeBot[0].setOnAction(actionEvent -> {
      if (firstPane.getChildren().contains(easyBotLabel[0])) {
        easyBotLabel[0].setText("");
        firstPane.getChildren().removeAll(easyBotLabel[0], removeBot[0]);
      } else if (firstPane.getChildren().contains(hardBotLabel[0])) {
        hardBotLabel[0].setText("");
        firstPane.getChildren().removeAll(hardBotLabel[0], removeBot[0]);
      }
      firstPane.getChildren().addAll(easyBots[0], hardBots[0]);
    });

    removeBot[1].setOnAction(actionEvent -> {
      if (firstPane.getChildren().contains(easyBotLabel[1])) {
        easyBotLabel[1].setText("");
        firstPane.getChildren().removeAll(easyBotLabel[1], removeBot[1]);
      } else {
        hardBotLabel[1].setText("");
        firstPane.getChildren().removeAll(hardBotLabel[1], removeBot[1]);
      }
      firstPane.getChildren().addAll(easyBots[1], hardBots[1]);
    });

    removeBot[2].setOnAction(actionEvent -> {
      if (firstPane.getChildren().contains(easyBotLabel[2])) {
        easyBotLabel[2].setText("");
        firstPane.getChildren().removeAll(easyBotLabel[2], removeBot[2]);
      } else if (firstPane.getChildren().contains(hardBotLabel[2])) {
        hardBotLabel[2].setText("");
        firstPane.getChildren().removeAll(hardBotLabel[2], removeBot[2]);
      }
      firstPane.getChildren().addAll(easyBots[2], hardBots[2]);
    });

    startGame.setOnMouseEntered(mouseEvent -> {
      startGame
          .setStyle("-fx-background-color: #5094d3; -fx-text-fill: white; -fx-font-style: italic; "
              + "-fx-font-size: 20px; -fx-border-radius: 22px; -fx-font-family: Futura; "
              + "-fx-background-radius: 22px; "
              + "-fx-border-color: #3e4756; -fx-font-weight: bold;");
    });

    startGame.setOnMouseExited(mouseEvent -> {
      startGame
          .setStyle("-fx-background-color: #2e75b6; -fx-text-fill: white; -fx-font-style: italic; "
              + "-fx-font-size: 20px; -fx-border-radius: 22px; -fx-font-family: Futura; "
              + "-fx-background-radius: 22px; -fx-border-color: #3e4756; -fx-font-weight: bold;");
    });

    easyBots[0].setOnMouseEntered(mouseEvent -> {
      easyBots[0].setStyle("-fx-background-color: white; -fx-text-fill: #2e75b6; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-border-radius: 15px; -fx-background-radius: 15px; "
          + "-fx-font-weight: bold; -fx-font-style: italic; " + "-fx-border-color: #2e75b6;");
    });
    easyBots[1].setOnMouseEntered(mouseEvent -> {
      easyBots[1].setStyle("-fx-background-color: white; -fx-text-fill: #2e75b6; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-border-radius: 15px; -fx-background-radius: 15px; "
          + "-fx-font-weight: bold; -fx-font-style: italic; " + "-fx-border-color: #2e75b6;");
    });
    easyBots[2].setOnMouseEntered(mouseEvent -> {
      easyBots[2].setStyle("-fx-background-color: white; -fx-text-fill: #2e75b6; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-border-radius: 15px; -fx-background-radius: 15px; "
          + "-fx-font-weight: bold; -fx-font-style: italic; " + "-fx-border-color: #2e75b6;");
    });
    hardBots[0].setOnMouseEntered(mouseEvent -> {
      hardBots[0].setStyle("-fx-background-color: white; -fx-text-fill: #2e75b6; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-border-radius: 15px; -fx-background-radius: 15px; "
          + "-fx-font-weight: bold; -fx-font-style: italic; " + "-fx-border-color: #2e75b6;");
    });
    hardBots[1].setOnMouseEntered(mouseEvent -> {
      hardBots[1].setStyle("-fx-background-color: white; -fx-text-fill: #2e75b6; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-border-radius: 15px; -fx-background-radius: 15px; "
          + "-fx-font-weight: bold; -fx-font-style: italic; " + "-fx-border-color: #2e75b6;");
    });
    hardBots[2].setOnMouseEntered(mouseEvent -> {
      hardBots[2].setStyle("-fx-background-color: white; -fx-text-fill: #2e75b6; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-border-radius: 15px; -fx-background-radius: 15px; "
          + "-fx-font-weight: bold; -fx-font-style: italic; " + "-fx-border-color: #2e75b6;");
    });

    easyBots[0].setOnMouseExited(mouseEvent -> {
      easyBots[0].setStyle("-fx-background-color: #2e75b6; -fx-text-fill: white; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-border-radius: 15px; -fx-background-radius: 15px; "
          + "-fx-font-weight: bold; -fx-font-style: italic;");
    });
    easyBots[1].setOnMouseExited(mouseEvent -> {
      easyBots[1].setStyle("-fx-background-color: #2e75b6; -fx-text-fill: white; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-border-radius: 15px; -fx-background-radius: 15px; "
          + "-fx-font-weight: bold; -fx-font-style: italic;");
    });
    easyBots[2].setOnMouseExited(mouseEvent -> {
      easyBots[2].setStyle("-fx-background-color: #2e75b6; -fx-text-fill: white; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-border-radius: 15px; -fx-background-radius: 15px; "
          + "-fx-font-weight: bold; -fx-font-style: italic;");
    });
    hardBots[0].setOnMouseExited(mouseEvent -> {
      hardBots[0].setStyle("-fx-background-color: #2e75b6; -fx-text-fill: white; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-border-radius: 15px; -fx-background-radius: 15px; "
          + "-fx-font-weight: bold; -fx-font-style: italic;");
    });
    hardBots[1].setOnMouseExited(mouseEvent -> {
      hardBots[1].setStyle("-fx-background-color: #2e75b6; -fx-text-fill: white; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-border-radius: 15px; -fx-background-radius: 15px; "
          + "-fx-font-weight: bold; -fx-font-style: italic;");
    });
    hardBots[2].setOnMouseExited(mouseEvent -> {
      hardBots[2].setStyle("-fx-background-color: #2e75b6; -fx-text-fill: white; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-border-radius: 15px; -fx-background-radius: 15px; "
          + "-fx-font-weight: bold; -fx-font-style: italic;");
    });
  }

  /**
   * Initializes the panel on the right side. doorOpen.jpg and doorClosed.jpg are copyright free
   * images, drawn and resized by @/ceho and @/raydin.
   */
  private void initPanelRight() {
    paneRight = new GridPane();

    dictChoice = new ComboBox<String>();
    dictChoice.setPromptText("Language Choice");
    dictChoice.setStyle("-fx-background-color: #F2F2F2; -fx-border-color: #A6A6A6; "
        + "-fx-background-radius: 15px; " + "-fx-border-radius: 15px; -fx-text-fill: black; "
        + "-fx-font-family: Calibri; -fx-font-size: 16px;");
    dictChoice.setPrefHeight(50);
    dictChoice.setPrefWidth(200);

    // dictChoice.getItems().addAll(Language.values());
    // dictChoice.setValue(Language.ENGLISH);
    dictChoice.getItems().addAll(new File("res/dict").list());
    dictChoice.setValue(new File("res/dict").list()[0]);

    paneRight.getChildren().add(dictChoice);
    dictChoice.setTranslateX(300);
    dictChoice.setTranslateY(230);

    letterChoice = new ComboBox<Character>();
    letterChoice.setStyle("-fx-background-color: #F2F2F2; -fx-border-color: #A6A6A6; "
        + "-fx-background-radius: 15px; " + "-fx-border-radius: 15px; -fx-text-fill: black; "
        + "-fx-font-family: Calibri; -fx-font-size: 16px;");
    letterChoice.setPrefHeight(50);
    letterChoice.setPrefWidth(115);
    letterChoice.setPromptText("Letter");

    for (int i = 65; i < 91; i++) {
      Character c = Character.valueOf((char) i);
      letterChoice.getItems().add(c);
    }

    paneRight.getChildren().add(letterChoice);
    letterChoice.setTranslateX(648);
    letterChoice.setTranslateY(190);

    amountChoice = new ComboBox<Integer>();
    amountChoice.setStyle("-fx-background-color: #F2F2F2; -fx-border-color: #A6A6A6; "
        + "-fx-background-radius: 15px; " + "-fx-border-radius: 15px; -fx-text-fill: black; "
        + "-fx-font-family: Calibri; -fx-font-size: 16px; " + "-fx-font-style: italic;");
    amountChoice.setPrefHeight(50);
    amountChoice.setPrefWidth(115);
    amountChoice.setPromptText("Amount");

    for (int i = 1; i < 100; i++) {
      Integer amount = Integer.valueOf(i);
      amountChoice.getItems().add(amount);
    }

    paneRight.getChildren().add(amountChoice);
    amountChoice.setTranslateX(650);
    amountChoice.setTranslateY(244);

    valueChoice = new ComboBox<Integer>();
    valueChoice.setPromptText("value");
    valueChoice.setFocusTraversable(false);
    valueChoice.setStyle("-fx-background-color: #F2F2F2; -fx-border-color: #A6A6A6; "
        + "-fx-background-radius: 15px; " + "-fx-border-radius: 15px; -fx-text-fill: black; "
        + "-fx-font-family: Calibri; -fx-font-size: 16px; " + "-fx-font-style: italic;");
    valueChoice.setPrefHeight(50);
    valueChoice.setPrefWidth(115);
    valueChoice.setPromptText("Value");

    for (int i = 1; i < 100; i++) {
      Integer value = Integer.valueOf(i);
      valueChoice.getItems().add(value);
    }
    paneRight.getChildren().add(valueChoice);
    valueChoice.setTranslateX(790);
    valueChoice.setTranslateY(240);

    Image logout = new Image("file:res/images/doorClosed.jpg", 60, 60, true, true);
    ImageView iv2 = new ImageView(logout);
    lo = new Button();
    lo.setGraphic(iv2);
    lo.setStyle("-fx-background-color: transparent; -fx-font-size: 25px;");
    lo.setTooltip(new Tooltip("Exit Game"));

    lo.setOnMouseEntered(actionEvent -> {
      Image logout2 = new Image("file:res/images/doorOpen.jpg", 60, 60, true, true);
      ImageView iv3 = new ImageView(logout2);
      lo.setGraphic(iv3);
    });

    lo.setOnMouseExited(actionEvent -> {
      Image logout2 = new Image("file:res/images/doorClosed.jpg", 60, 60, true, true);
      ImageView iv3 = new ImageView(logout2);
      lo.setGraphic(iv3);
    });

    paneRight.getChildren().add(lo);
    lo.setTranslateY(-300);
    lo.setTranslateX(-353);
  }


  /**
   * Initializes the Buttons to add and remove AI players and the labels that appear when a Bot is
   * added.
   */
  private void initBots() {

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
      easyBots[i].setPrefWidth(100);
      easyBots[i].setStyle("-fx-background-color: #2e75b6; -fx-text-fill: white; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-border-radius: 15px; -fx-background-radius: 15px; "
          + "-fx-font-weight: bold; -fx-font-style: italic;");
      easyBots[i].setTooltip(easyBotsTip);
    }

    for (int i = 0; i < hardBots.length; i++) {
      hardBots[i] = new Button("Hard Bot");
      hardBots[i].setPrefHeight(40);
      hardBots[i].setPrefWidth(100);
      hardBots[i].setStyle("-fx-background-color: #2e75b6; -fx-text-fill: white; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-border-radius: 15px; -fx-background-radius: 15px; "
          + "-fx-font-weight: bold; -fx-font-style: italic;");
      hardBots[i].setTooltip(hardBotsTip);
    }

    for (Button b : easyBots) {
      b.setTranslateX(-190);
    }
    easyBots[0].setTranslateY(-50);
    easyBots[1].setTranslateY(30);
    easyBots[2].setTranslateY(120);

    for (Button b : hardBots) {
      b.setTranslateX(-10);
    }

    hardBots[0].setTranslateY(-50);
    hardBots[1].setTranslateY(30);
    hardBots[2].setTranslateY(120);

    Tooltip removeBotTip = new Tooltip("Remove bot player.");
    removeBotTip.setStyle("-fx-font-family: Futura; -fx-font-size: 13px; "
        + "-fx-background-color: #c7e6ff; -fx-border-radius: 15px; "
        + "-fx-background-radius: 15px; -fx-text-fill: black; -fx-set-stroke: black;");

    for (int i = 0; i < removeBot.length; i++) {
      removeBot[i] = new Button("x");
      removeBot[i].setStyle("-fx-background-color: transparent; -fx-text-fill: #A6A6A6; "
          + "-fx-font-size: 36px; -fx-font-weight: bold;");
      removeBot[i].setTranslateX(100);
      removeBot[i].setTooltip(removeBotTip);
    }
    removeBot[0].setTranslateY(-55);
    removeBot[1].setTranslateY(30);
    removeBot[2].setTranslateY(120);

    for (int i = 0; i < easyBotLabel.length; i++) {
      easyBotLabel[i] = new Label();
      easyBotLabel[i].setStyle("-fx-text-fill: #7f7f7f; -fx-font-family: Futura; "
          + "-fx-font-size: 30px; -fx-font-style: italic;");
      easyBotLabel[i].setTranslateX(-190);
    }

    easyBotLabel[0].setTranslateY(-50);
    easyBotLabel[1].setTranslateY(30);
    easyBotLabel[2].setTranslateY(120);

    for (int i = 0; i < hardBotLabel.length; i++) {
      hardBotLabel[i] = new Label();
      hardBotLabel[i].setStyle("-fx-text-fill: #7f7f7f; -fx-font-family: Futura; "
          + "-fx-font-size: 30px; -fx-font-style: italic;");

      hardBotLabel[i].setTranslateX(-190);
    }
    hardBotLabel[0].setTranslateY(-50);
    hardBotLabel[1].setTranslateY(30);
    hardBotLabel[2].setTranslateY(120);
  }

  /**
   * Initializes the statistics labels and lines.
   */
  private void initStatsLabels() {
    statistics.setStyle("-fx-text-fill: #2e75b6; -fx-font-size: 33px; -fx-font-family: Futura;");

    for (int i = 0; i < playerStats.length; i++) {
      playerStats[i] = new Label("Player Stats" + (i + 1));
      playerStats[i]
          .setStyle("-fx-text-fill: #7F7F7F; -fx-font-family: Futura; -fx-font-size: 22px;");
    }

    for (int i = 0; i < values.length; i++) {
      values[i] = new Label("value" + (i + 1));
      values[i].setStyle("-fx-text-fill: #7F7F7F; -fx-font-family: Futura; -fx-font-size: 22px;");
    }

    for (int i = 0; i < lines.length; i++) {
      lines[i] = new Line(800, -350, 1430, -350);
      lines[i].setStroke(Color.WHITE);
      lines[i].setStrokeWidth(3.0);
    }

    vertLine = new Line(800, -350, 800, -120);
    vertLine.setStroke(Color.WHITE);
    vertLine.setStrokeWidth(3.0);
  }

  /**
   * Initializes the Stage object of this class that sets the Scene.
   * 
   * @param stage the Stage object of this class.
   */
  private void initStage(Stage stage) {
    root = new StackPane();

    root.getChildren().add(iv);
    root.getChildren().add(rectangle);
    root.getChildren().addAll(playerRect, statsRect, dictRect, letterRect, dictionary, letterDist);

    firstPane = new GridPane();
    firstPane.setPadding(new Insets(5, 5, 0, 0));
    firstPane.setAlignment(Pos.CENTER);
    firstPane.setVgap(10);


    firstPane.getChildren().add(paneLeft);
    firstPane.getChildren().add(paneRight);

    firstPane.getChildren().add(easyBots[0]);
    firstPane.getChildren().add(easyBots[1]);
    firstPane.getChildren().add(easyBots[2]);

    firstPane.getChildren().add(hardBots[0]);
    firstPane.getChildren().add(hardBots[1]);
    firstPane.getChildren().add(hardBots[2]);

    root.getChildren().add(firstPane);

    root.getChildren().add(statistics);
    statistics.setTranslateX(280);
    statistics.setTranslateY(-180);

    for (Line l : lines) {
      root.getChildren().add(l);
      l.setTranslateX(280);
    }
    lines[0].setTranslateY(-140);
    lines[1].setTranslateY(-100);
    lines[2].setTranslateY(-60);
    lines[3].setTranslateY(-20);
    lines[4].setTranslateY(20);
    lines[5].setTranslateY(60);

    root.getChildren().add(vertLine);
    vertLine.setTranslateY(-20);
    vertLine.setTranslateX(160);

    for (Label l : playerStats) {
      root.getChildren().add(l);
      l.setTranslateX(58);
    }
    playerStats[0].setTranslateY(-120);
    playerStats[1].setTranslateY(-80);
    playerStats[2].setTranslateY(-40);
    playerStats[4].setTranslateY(40);
    playerStats[5].setTranslateY(80);

    for (Label l : values) {
      root.getChildren().add(l);
      l.setTranslateX(220);
    }
    values[0].setTranslateY(-120);
    values[1].setTranslateY(-80);
    values[2].setTranslateY(-40);
    values[4].setTranslateY(40);
    values[5].setTranslateY(80);

    root.getChildren().add(choices);
    choices.setTranslateX(215);

    Label headlineName = new Label("S C R A B U R U");
    headlineName.setStyle("-fx-font-family: Futura; -fx-background-color: transparent;"
        + "-fx-text-fill: linear-gradient(to right, #84A9E7 0%, "
        + "#DEB7EE 15%, #ee7898 30%, #e6ed8e 45%, #9ccdb4 60%, " + "#84A9E7 75%, #ed9ab0 100% );"
        + "-fx-font-size: 40px;" + "-fx-font-weight: bold;");

    headlineName.setTranslateX(270);
    headlineName.setTranslateY(-270);

    root.getChildren().add(headlineName);

    Line divide = new Line(800, 50, 1200, 50);
    divide.setStroke(GuiSettings.GRAY_BORDER);
    divide.setStrokeWidth(1.0);

    root.getChildren().add(divide);
    divide.setTranslateX(-380);
    divide.setTranslateY(205);

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
    choices.setTranslateY(230);

    choices.setOnMouseEntered(styleEvent -> {
      choices.setStyle("-fx-background-color: #5094d3; -fx-text-fill: white; "
          + "-fx-font-family: Futura; -fx-font-size: 15px; "
          + "-fx-font-weight: bold; -fx-border-radius: 15px; -fx-background-radius: 15px;");
    });

    choices.setOnMouseExited(styleEvent -> {
      choices.setStyle("-fx-background-color: #2e75b6; "
          + "-fx-text-fill: white; -fx-font-family: Futura; " + "-fx-font-size: 15px; "
          + "-fx-font-weight: bold; -fx-border-radius: 15px; " + "-fx-background-radius: 15px;");
    });
  }


  /**
   * Handles the letter distribution.
   */
  @SuppressWarnings("unused")
  private void handleLetterDist() {

    letterChoice.setOnAction(actionEvent -> {
      toSetPoints = true;
      if (letterChoice.getValue() != 0) {
        chosenLetter = letterChoice.getValue();
      }
    });
  }

  public void removeBots(int index) {

    if (firstPane.getChildren().contains(easyBotLabel[index])) {
      firstPane.getChildren().removeAll(easyBotLabel[index], removeBot[index]);
      easyBotLabel[index].setText("");
    } else if (firstPane.getChildren().contains(hardBotLabel[index])) {
      hardBotLabel[index].setText("");
      firstPane.getChildren().removeAll(hardBotLabel[index], removeBot[index]);
    }

    firstPane.getChildren().addAll(easyBots[index], hardBots[index]);
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
