package main.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * The main menu in which the player can choose between the different game-modes.
 * 
 * @author raydin
 *
 */

public class MainMenu extends Application {
  /**
   * Stage object to set the Scene.
   */
  private Stage stage = new Stage();

  /**
   * Individual width of the screen the application is being displayed on.
   */
  private int width;

  /**
   * Individual height of the screen the application is being displayed on.
   */
  private int height;

  /**
   * The background image of the user-interface.
   */
  private Image background;

  /**
   * The ImageView object that stores the Image object to display it.
   */
  private ImageView iv;

  /**
   * The rectangle base for the main menu options.
   */
  private Rectangle rectangle;

  /**
   * The rectangle base for the line for seperation.
   */
  private Rectangle line;

  /**
   * The Button to enter a host-mode game.
   */
  private Button hostGame;

  /**
   * The Button to enter a join-mode game.
   */
  private Button joinGame;

  /**
   * The Button to enter a tutorial-mode game.
   */
  private Button playTutorial;

  /**
   * The Button to click after entering the connection details.
   */
  private Button joinedGame;

  /**
   * The Button to click to go back to the game-mode selection.
   */
  private Button back;

  /**
   * The Button to logout from your profile.
   */
  private Button logout;

  /**
   * The StackPane object to store game-mode-options on.
   */
  private StackPane root;

  /**
   * The grid to store the buttons in.
   */
  private GridPane pane;

  /**
   * The "SCRABBLE" headline on top of the option-buttons.
   */
  private Label headline;

  /**
   * The button to see the player details.
   */
  private Button profile;

  /**
   * The TextField to enter the ipAdress in.
   */
  private TextField ipAddress;

  /**
   * The ComboBox to choose an IP address from.
   */
  private ComboBox<String> availableIp;

  private ErrorBox error = new ErrorBox();

  private Button help;


  /**
   * The constructor of this class. Invokes the init method.
   */
  public MainMenu() {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    width = gd.getDisplayMode().getWidth();
    height = gd.getDisplayMode().getHeight();

    init(this.stage);
  }

  /**
   * Returns the hostGame Button.
   * 
   * @return the hostGame Button.
   */
  public Button getHostButton() {
    return this.hostGame;
  }

  /**
   * Returns the joinGame Button.
   * 
   * @return the joinGame Button.
   */
  public Button getJoinButton() {
    return this.joinGame;
  }

  /**
   * Returns the joinedGame Button.
   * 
   * @return the joinedGame Button.
   */
  public Button getJoinedButton() {
    return this.joinedGame;
  }

  /**
   * Returns the playTutorial Button.
   * 
   * @return the playTutorial Button.
   */
  public Button getTutorialButton() {
    return this.playTutorial;
  }

  /**
   * Returns the profile Button.
   * 
   * @return the profile Button.
   */
  public Button getProfile() {
    return this.profile;
  }

  /**
   * Returns the StackPane of this class.
   *
   * @return the StackPane of this class.
   */
  public StackPane getStackPane() {
    return this.root;
  }

  /**
   * Returns the TextField for the IP address.
   *
   * @return the TextField for the IP address.
   */
  public TextField getIp() {
    return this.ipAddress;
  }

  /**
   * Returns the ErrorBox object for this class.
   *
   * @return the ErrorBox object for this class.
   */
  public ErrorBox getErrorBox() {
    return this.error;
  }

  /**
   * Returns the "Scraburu" headline.
   *
   * @return the "Scraburu" headline.
   */
  public Label getHeadline() {
    return this.headline;
  }

  /**
   * Returns the line for separation.
   *
   * @return the line for separation.
   */
  public Rectangle getLine() {
    return this.line;
  }

  /**
   * Returns the back Button.
   *
   * @return the back Button.
   */
  public Button getBackButton() {
    return this.back;
  }

  /**
   * Returns the ComboBox for the available IPs.
   *
   * @return the ComboBox for the available IPs.
   */
  public ComboBox<String> getAvailableIp() {
    return this.availableIp;
  }

  public Button getHelpButton() {
    return this.help;
  }

  /**
   * The start method that shows the scene.
   * 
   * @param stage the stage object to initialize.
   */
  @Override
  public void start(Stage stage) {
    Scene scene = new Scene(root, width, height);

    stage.setScene(scene);
    stage.show();
  }

  /**
   * Closes the window.
   * 
   * @param stage the stage object of the window that should be closed.
   */
  public void close(Stage stage) {
    init(stage);
    stage.close();
  }

  /**
   * Initializes all components.
   * 
   * @param stage the Stage-object to be initialized.
   */
  private void init(Stage stage) {
    initBackground();
    optionsBackground();
    initButtons();
    initLabel();
    connectToGame();
    setActionEvents();
    initStage(stage);
  }

  /**
   * Initializes the Image-object "background" by using bg.png and blends it; bckgrnd.png is a free
   * to use, copyright free image from https://pixabay.com/images/id-691842/ saved on: 31st of
   * March, 2021 at 10:32 pm.
   */
  private void initBackground() {
    background = new Image("file:res/images/bckgrnd.png");
    iv = new ImageView(background);
    iv.setFitHeight(height);
    iv.setFitWidth(width);
  }

  /**
   * Initializes the rectangle behind the login options and the line between the buttons.
   */
  private void optionsBackground() {
    rectangle = new Rectangle();
    rectangle.setWidth(width * 0.3125); // 450
    rectangle.setHeight(width * 0.2778); // 400-height * 0.44445

    rectangle.setArcHeight(30);
    rectangle.setArcWidth(30);

    rectangle.setFill(Color.WHITE);
    rectangle.setStroke(GuiSettings.BLUE);

    line = new Rectangle();
    line.setWidth(width * 0.229); // 330
    line.setHeight(width * 0.00069); // 1-height * 0.0011
    line.setStyle("-fx-stroke-line-cap: round;");
    line.setFill(GuiSettings.GRAY_BORDER);
    line.setOpacity(70);
  }

  /**
   * Initializes the Button objects.
   */
  private void initButtons() {
    back = new Button();
    back.setStyle("-fx-background-color: transparent;");
    Image image2 =
        new Image("file:res/images/return.PNG", width * 0.021, width * 0.021, true, true); // 30/30
    ImageView iv2 = new ImageView(image2);
    back.setGraphic(iv2);

    back.setOnMousePressed(mouseEvent -> {
      back.setStyle("-fx-background-color: transparent;");
      Image image =
          new Image("file:res/images/return.PNG", width * 0.01736, width * 0.01736, true, true); // 25/25
      ImageView iv = new ImageView(image);
      back.setGraphic(iv);
    });

    back.setOnMouseReleased(mouseEvent -> {
      back.setStyle("-fx-background-color: transparent;");
      Image image =
          new Image("file:res/images/return.PNG", width * 0.021, width * 0.021, true, true); // 30/30
      ImageView iv = new ImageView(image);
      back.setGraphic(iv);
    });

    logout = new Button();
    logout.setStyle("-fx-background-color: transparent;");
    Image image3 =
        new Image("file:res/images/logout.png", width * 0.0243, width * 0.0243, true, true); // 35/35
    ImageView iv3 = new ImageView(image3);
    logout.setGraphic(iv3);

    logout.setOnMouseEntered(mouseEvent -> {
      Image image32 =
          new Image("file:res/images/logout.png", width * 0.0223, width * 0.0223, true, true); // 35/35
      ImageView iv32 = new ImageView(image32);
      logout.setGraphic(iv32);
    });

    logout.setOnMouseExited(mouseEvent -> {
      Image image32 =
          new Image("file:res/images/logout.png", width * 0.0243, width * 0.0243, true, true); // 35/35
      ImageView iv32 = new ImageView(image32);
      logout.setGraphic(iv32);
    });

    hostGame = new Button("HOST GAME");
    joinGame = new Button("JOIN GAME");
    playTutorial = new Button("PLAY TUTORIAL");

    hostGame
        .setStyle("-fx-background-color: #518FD2; -fx-text-fill: white; -fx-border-radius: 17px; "
            + "-fx-font-family: Futura; -fx-font-size: 20px; -fx-background-radius: 17px; "
            + "-fx-border-color: #5671bb; -fx-font-weight: bold;");
    joinGame
        .setStyle("-fx-background-color: #71AAF0; -fx-text-fill: white; -fx-border-radius: 17px; "
            + "-fx-font-family: Futura; -fx-font-size: 20px; -fx-background-radius: 19px; "
            + "-fx-border-color: #5671bb; -fx-font-weight: bold;");
    playTutorial
        .setStyle("-fx-background-color: #8FC7FF; -fx-text-fill: white; -fx-border-radius: 17px; "
            + "-fx-font-family: Futura; -fx-font-size: 20px; -fx-background-radius: 17px; "
            + "-fx-border-color: #5671bb; -fx-font-weight: bold;");

    hostGame.setPrefWidth(width * 0.229); // 330
    hostGame.setPrefHeight(width * 0.04167); // 60-height * 0.0667
    joinGame.setPrefWidth(width * 0.229); // 330
    joinGame.setPrefHeight(width * 0.04167); // 60-height * 0.0667
    playTutorial.setPrefWidth(width * 0.229); // 330
    playTutorial.setPrefHeight(width * 0.04167); // 60-height * 0.0667

    profile = new Button();
    profile.setStyle("-fx-background-color: transparent;");

    Image icon =
        new Image("file:res/images/icon.PNG", width * 0.03472, width * 0.03472, true, true); // 50/50-height;
                                                                                             // 0.0556

    ImageView iv = new ImageView(icon);
    profile.setGraphic(iv);

    help = new Button();
    help.setStyle("-fx-background-color: transparent;");
    Image helpIm = new Image("file:res/images/help.png", 87, 87, true, true);
    ImageView helpView = new ImageView(helpIm);
    help.setGraphic(helpView);

    help.setOnMouseEntered(mouseEvent -> {
      help.setStyle("-fx-background-color: transparent;");
      Image helpIm2 = new Image("file:res/images/help.png", 77, 77, true, true);
      ImageView helpView2 = new ImageView(helpIm2);
      help.setGraphic(helpView2);
    });

    help.setOnMouseExited(mouseEvent -> {
      help.setStyle("-fx-background-color: transparent;");
      Image helpIm2 = new Image("file:res/images/help.png", 87, 87, true, true);
      ImageView helpView2 = new ImageView(helpIm2);
      help.setGraphic(helpView2);
    });

  }

  /**
   * Initializes the "Scrabble"-headline.
   */
  private void initLabel() {
    headline = new Label("S C R A B U R U");
    headline.setAlignment(Pos.CENTER);
    headline.setTextAlignment(TextAlignment.CENTER);
    headline.setPrefWidth(width * 0.229); // 330
    headline.setPrefHeight(width * 0.0347);// 50-height * 0.0556
    headline.setStyle("-fx-font-family: Futura; -fx-background-color: #fbfaff;"
        + "-fx-text-fill: linear-gradient(to right, red, orange, #e7eb00, green, "
        + "blue, indigo, violet); -fx-font-size: 35px;");
  }

  /**
   * Sets the Button objects' events.
   */
  private void setActionEvents() {
    hostGame.setOnMouseEntered(mouseEvent -> {
      hostGame.setStyle("-fx-background-color: #5199D2; -fx-text-fill: white; "
          + "-fx-border-radius: 17px; -fx-font-family: Futura; "
          + "-fx-font-size: 21px; -fx-background-radius: 17px; "
          + "-fx-border-color: #5671bb; -fx-font-weight: bold;");
    });
    hostGame.setOnMouseExited(mouseEvent -> {
      hostGame.setStyle("-fx-background-color: #51A0D2; -fx-text-fill: white; "
          + "-fx-border-radius: 17px; -fx-font-family: Futura; "
          + "-fx-font-size: 20px; -fx-background-radius: 17px; "
          + "-fx-border-color: #5671bb; -fx-font-weight: bold;");
    });

    joinGame.setOnMouseEntered(mouseEvent -> {
      joinGame.setStyle("-fx-background-color: #71B4F0; -fx-text-fill: white; "
          + "-fx-border-radius: 17px; -fx-font-family: Futura; "
          + "-fx-font-size: 21px; -fx-background-radius: 17px; "
          + "-fx-border-color: #5671bb; -fx-font-weight: bold;");
    });
    joinGame.setOnMouseExited(mouseEvent -> {
      joinGame.setStyle("-fx-background-color: #71AAF0; -fx-text-fill: white; "
          + "-fx-border-radius: 17px; -fx-font-family: Futura; "
          + "-fx-font-size: 20px; -fx-background-radius: 17px; "
          + "-fx-border-color: #5671bb; -fx-font-weight: bold;");
    });

    playTutorial.setOnMouseEntered(mouseEvent -> {
      playTutorial.setStyle("-fx-background-color: #8FCFFF; -fx-text-fill: white; "
          + "-fx-border-radius: 17px; -fx-font-family: Futura; "
          + "-fx-font-size: 21px; -fx-background-radius: 17px; "
          + "-fx-border-color: #5671bb; -fx-font-weight: bold;");
    });
    playTutorial.setOnMouseExited(mouseEvent -> {
      playTutorial.setStyle("-fx-background-color: #8FC7FF; -fx-text-fill: white; "
          + "-fx-border-radius: 17px; -fx-font-family: Futura; "
          + "-fx-font-size: 20px; -fx-background-radius: 17px; "
          + "-fx-border-color: #5671bb; -fx-font-weight: bold;");
    });
  }

  /**
   * Initializes the stage.
   * 
   * @param stage the Stage object to be initialized.
   */
  private void initStage(Stage stage) {
    root = new StackPane();

    pane = new GridPane();
    pane.setPadding(new Insets(width * 0.00347, width * 0.00347, 0, 0)); // 5/5/0/0-height * 0.0056
    pane.setAlignment(Pos.CENTER);
    pane.setVgap(width * 0.01649); // 23.75-height * 0.02639

    GridPane.setConstraints(headline, 0, 0);
    GridPane.setConstraints(line, 0, 1);
    GridPane.setConstraints(hostGame, 0, 2);
    GridPane.setConstraints(joinGame, 0, 3);
    GridPane.setConstraints(playTutorial, 0, 4);

    pane.getChildren().addAll(headline, line, hostGame, joinGame, playTutorial);

    profile.setTranslateX(width * 0.1354); // 195
    profile.setTranslateY(-(width * 0.1181)); // -170-height * 0.1889

    logout.setTranslateX(-(width * 0.1354)); // 195
    logout.setTranslateY(-(width * 0.1181)); // -170-height * 0.1889

    line.setTranslateY(-20);
    root.getChildren().addAll(iv);
    root.getChildren().add(rectangle);
    root.getChildren().add(pane);
    root.getChildren().add(profile);
    root.getChildren().add(logout);
  }

  /**
   * Sets the actionEvent of the joinGame Button.
   */
  private void connectToGame() {
    joinedGame = new Button("JOIN GAME");
    joinedGame
        .setStyle("-fx-background-color: #6ea9ee; -fx-text-fill: black; -fx-border-radius: 17px; "
            + "-fx-font-family: Futura; -fx-font-size: 15px; -fx-background-radius: 19px; "
            + "-fx-border-color: #5671bb; -fx-font-weight: bold;");
    joinedGame.setPrefWidth(width * 0.229); // 330
    joinedGame.setPrefHeight(width * 0.04167); // 60-height * 0.0667

    joinedGame.setOnMouseEntered(mouseEvent -> {
      joinedGame.setStyle("-fx-background-color: #6ea9ee; -fx-text-fill: black; "
          + "-fx-border-radius: 17px; -fx-font-family: Futura; "
          + "-fx-font-size: 17px; -fx-background-radius: 19px; "
          + "-fx-border-color: #5671bb; -fx-font-weight: bold;");
    });

    joinedGame.setOnMouseExited(mouseEvent -> {
      joinedGame.setStyle(
          "-fx-background-color: #6ea9ee; " + "-fx-text-fill: black; -fx-border-radius: 17px; "
              + "-fx-font-family: Futura; -fx-font-size: 15px; "
              + "-fx-background-radius: 19px; -fx-border-color: #5671bb; -fx-font-weight: bold;");
    });

    ipAddress = new TextField();
    ipAddress.setPromptText("IP-address");
    ipAddress.setFocusTraversable(false);
    ipAddress.setStyle(
        "-fx-background-color: #F2F2F2; -fx-border-color: #A6A6A6; -fx-background-radius: 15px; "
            + "-fx-border-radius: 15px; -fx-text-fill: black; "
            + "-fx-font-size: 16px; -fx-font-family: Calibri;");
    ipAddress.setPrefWidth(width * 0.229); // 330
    ipAddress.setPrefHeight(width * 0.04167); // 60-height * 0.0667

    availableIp = new ComboBox<String>();
    availableIp.setPromptText("Available IP-addresses");
    availableIp.setStyle(
        "-fx-background-color: #F2F2F2; -fx-border-color: #A6A6A6; -fx-background-radius: 15px; "
            + "-fx-border-radius: 15px; -fx-text-fill: black; "
            + "-fx-font-size: 16px; -fx-font-family: Calibri;");
    availableIp.setPrefWidth(width * 0.229); // 330
    availableIp.setPrefHeight(width * 0.04167); // 60-height * 0.0667

    joinGame.setOnAction(actionEvent -> {
      pane.getChildren().removeAll(hostGame, joinGame, playTutorial);
      root.getChildren().removeAll(profile, logout);
      GridPane.setConstraints(availableIp, 0, 2);
      GridPane.setConstraints(ipAddress, 0, 3);
      GridPane.setConstraints(joinedGame, 0, 4);
      pane.getChildren().addAll(ipAddress, availableIp, joinedGame);
      root.getChildren().add(back);
      back.setTranslateX(-(width * 0.132)); // -190
      back.setTranslateY(-(width * 0.04167)); // 60-height * 0.0667
    });

    back.setOnAction(actionEvent -> {
      pane.getChildren().removeAll(ipAddress, availableIp, joinedGame);
      GridPane.setConstraints(hostGame, 0, 2);
      GridPane.setConstraints(joinGame, 0, 3);
      GridPane.setConstraints(playTutorial, 0, 4);
      pane.getChildren().addAll(hostGame, joinGame, playTutorial);
      root.getChildren().remove(back);
      root.getChildren().addAll(profile, logout);
      profile.setTranslateX(width * 0.1354); // 195
      profile.setTranslateY(-(width * 0.1181)); // -170-height * 0.1889
      logout.setTranslateX(-(width * 0.1354)); // 195
      logout.setTranslateY(-(width * 0.1181)); // -170-height * 0.1889
    });
  }

  /**
   * Adds a new ipAddress to the available IP addresses ComboBox.
   * 
   * @param ipAddress the ipAddress to be added.
   */
  public void setAvailableIp(String ipAddress) {
    this.availableIp.getItems().add(ipAddress);
  }

  /**
   * Removes all available IP addresses in the availableIp ComboBox.
   */
  public void resetAvailableIp() {
    this.availableIp.getItems().removeAll(availableIp.getItems());
  }
}
