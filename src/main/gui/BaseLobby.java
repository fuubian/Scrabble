package main.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * The base class for the host and join-game lobby. Sets the base components that are being used in
 * the HostGameLobby- and JoinGameLobby classes.
 * 
 * @author raydin.
 *
 */
public class BaseLobby extends Application {

  /**
   * The root StackPane object used for the scene.
   */
  private StackPane root;

  /**
   * Individual width of the screen the application is being displayed on.
   */
  private int width;

  /**
   * The background image of the application.
   */
  private Image background;

  /**
   * The ImageView object to store the background image in.
   */
  private ImageView iv;

  /**
   * The base rectangle behind the components.
   */
  private Rectangle rectangle;

  /**
   * The rectangle to draw the player table.
   */
  private Rectangle playerRect;

  /**
   * The rectangle to draw the statistics table.
   */
  private Rectangle statsRect;

  /**
   * The rectangle to display the dictionary ComboBox on.
   */
  private Rectangle dictRect;

  /**
   * The rectangle to display the chat on.
   */
  private Rectangle chatRect;

  /**
   * The lines to draw the statistics table.
   */
  private Line[] statLines = new Line[8];

  /**
   * The lines to draw the player table.
   */
  private Line[] lines = new Line[4];

  /**
   * The line on top of the player table that underlines the lobby name.
   */
  private Line firstLine;

  /**
   * The button to leave the lobby.
   */
  private Button lo;

  /**
   * The rectangle to display the letter distribution on.
   */
  private Rectangle letterRect;

  /**
   * The constructor of the BaseLobby class. Sets the background.
   */
  public BaseLobby() {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    width = gd.getDisplayMode().getWidth();
    // height = gd.getDisplayMode().getHeight();

    optionsBackground();
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
   * Returns the logout button.
   * 
   * @return the logout button.
   */
  public Button getLogout() {
    return this.lo;
  }

  /**
   * Initializes all components and finally initializes the stage object of this class.
   * 
   * @param stage the stage object of this class.
   */
  public void init(Stage stage) {
    initBackground();
    optionsBackground();
    initPanelRight();
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
   * Initializes the table that will contain player-names and the line that underlines the lobby
   * name.
   * 
   * @return HBox object that contains all components for the panel.
   */
  private HBox initPlayerPanel() {
    firstLine = new Line(800, 10, 1200, 10);
    firstLine.setStroke(Color.BLACK);

    playerRect = new Rectangle();
    playerRect.setHeight(330);
    playerRect.setWidth(400);

    playerRect.setArcHeight(20);
    playerRect.setArcWidth(20);

    playerRect.setFill(GuiSettings.GRAY_FILL);
    playerRect.setStroke(GuiSettings.GRAY_BORDER);


    lines[0] = new Line(800, -350, 1200, -350);
    lines[0].setStroke(GuiSettings.GRAY_BORDER);
    lines[1] = new Line(800, -250, 1200, -250);
    lines[1].setStroke(GuiSettings.GRAY_BORDER);
    lines[2] = new Line(800, -250, 1200, -250);
    lines[2].setStroke(GuiSettings.GRAY_BORDER);

    HBox pane = new HBox();
    pane.getChildren().add(playerRect);
    pane.getChildren().add(firstLine);
    firstLine.setTranslateY(-130);
    firstLine.setTranslateX(-400);

    playerRect.setTranslateY(-20);
    playerRect.setTranslateY(-20);

    pane.getChildren().addAll(lines[0], lines[1], lines[2]);
    lines[0].setTranslateY(70);
    lines[0].setTranslateX(-801);
    lines[1].setTranslateY(150);
    lines[1].setTranslateX(-1203);
    lines[2].setTranslateY(230);
    lines[2].setTranslateX(-1603);

    pane.setTranslateX(1180);
    pane.setTranslateY(325);

    return pane;
  }

  /**
   * Initializes and sets the base rectangle in the background of this stage. doorOpen.jpg and
   * doorClosed.jpg are copyright free images, drawn and resized by @/raydin and @/ceho.
   */
  private void optionsBackground() {
    rectangle = new Rectangle();
    rectangle.setWidth(1350);
    rectangle.setHeight(680);
    rectangle.setArcWidth(30);
    rectangle.setArcHeight(30);

    rectangle.setFill(Color.WHITE);
    rectangle.setStroke(GuiSettings.BLUE);

    Image logout =
        new Image("file:res/images/doorClosed.jpg", width * 0.035, width * 0.035, true, true);
    ImageView iv2 = new ImageView(logout);
    lo = new Button();
    lo.setGraphic(iv2);
    lo.setStyle("-fx-background-color: transparent; -fx-font-size: 25px;");
    lo.setTooltip(new Tooltip("Exit Game"));

    lo.setOnMouseEntered(actionEvent -> {
      Image logout2 =
          new Image("file:res/images/doorOpen.jpg", width * 0.035, width * 0.035, true, true);
      ImageView iv3 = new ImageView(logout2);
      lo.setGraphic(iv3);
    });

    lo.setOnMouseExited(actionEvent -> {
      Image logout2 =
          new Image("file:res/images/doorClosed.jpg", width * 0.035, width * 0.035, true, true);
      ImageView iv3 = new ImageView(logout2);
      lo.setGraphic(iv3);
    });
  }

  /**
   * Initializes the components needed for the statistics table.
   * 
   * @return HBox object that contains all components for panel.
   */
  public HBox initStatistics() {
    statsRect = new Rectangle();
    statsRect.setWidth(780);
    statsRect.setHeight(200);
    statsRect.setArcWidth(20);
    statsRect.setArcHeight(20);
    statsRect.setFill(GuiSettings.GRAY_FILL);
    statsRect.setStroke(GuiSettings.GRAY_BORDER);

    HBox pane = new HBox();
    pane.getChildren().add(statsRect);
    statsRect.setTranslateX(-60);
    statsRect.setTranslateY(-30);

    for (int i = 0; i < 4; i++) {
      statLines[i] = new Line(800, 60, 1496, 60);
      statLines[i].setStroke(Color.WHITE);
      statLines[i].setStrokeWidth(3.0);
      pane.getChildren().add(statLines[i]);
    }

    statLines[0].setTranslateX(-800);
    statLines[0].setTranslateY(20);

    statLines[1].setTranslateX(-1498);
    statLines[1].setTranslateY(60);

    statLines[2].setTranslateX(-2200);
    statLines[2].setTranslateY(100);


    for (int i = 4; i < statLines.length; i++) {
      statLines[i] = new Line(800, 50, 800, 245);
      statLines[i].setStroke(Color.WHITE);
      statLines[i].setStrokeWidth(3.0);
      pane.getChildren().add(statLines[i]);
      statLines[i].setTranslateY(-28);
    }

    statLines[4].setTranslateX(-3442);
    statLines[5].setTranslateX(-3300);
    statLines[6].setTranslateX(-3140);
    statLines[7].setTranslateX(-3005);


    pane.setTranslateY(-150);
    pane.setTranslateX(1700);
    return pane;
  }

  /**
   * Initializes the rectangles/panel that display the dictionary choice and the chat panel.
   * 
   * @return HBox object that contains all components for panel on the right.
   */
  private HBox initPanelRight() {
    // Dictionary Choice Panel:

    dictRect = new Rectangle();
    dictRect.setWidth(300);
    dictRect.setHeight(120);
    dictRect.setArcWidth(20);
    dictRect.setArcHeight(20);
    dictRect.setFill(GuiSettings.GRAY_FILL);
    dictRect.setStroke(GuiSettings.GRAY_BORDER);

    HBox pane = new HBox();
    pane.getChildren().add(dictRect);

    dictRect.setTranslateX(1640);
    dictRect.setTranslateY(332);

    Label dict = new Label("Dictionary");
    dict.setStyle("-fx-text-fill: #7f7f7f; -fx-font-family: Futura; -fx-font-size: 25px;");

    pane.getChildren().add(dict);
    dict.setTranslateX(1430);
    dict.setTranslateY(340);

    // Letter Distribution Choice:

    letterRect = new Rectangle();
    letterRect.setWidth(300);
    letterRect.setHeight(180);
    letterRect.setArcWidth(20);
    letterRect.setArcHeight(20);
    letterRect.setFill(GuiSettings.GRAY_FILL);
    letterRect.setStroke(GuiSettings.GRAY_BORDER);

    pane.getChildren().add(letterRect);
    letterRect.setTranslateX(1227);
    letterRect.setTranslateY(510);

    Label letterDist = new Label("Letter Distribution");
    letterDist.setStyle("-fx-text-fill: #7f7f7f; -fx-font-family: Futura; -fx-font-size: 25px;");

    pane.getChildren().add(letterDist);
    letterDist.setTranslateX(981);
    letterDist.setTranslateY(520);

    // Chat Panel:

    chatRect = new Rectangle();
    chatRect.setWidth(350);
    chatRect.setHeight(320);
    chatRect.setArcWidth(20);
    chatRect.setArcHeight(20);
    chatRect.setFill(GuiSettings.GRAY_FILL);
    chatRect.setStroke(GuiSettings.GRAY_BORDER);

    pane.getChildren().add(chatRect);
    chatRect.setTranslateX(1165); // -50
    chatRect.setTranslateY(370); // +40

    return pane;
  }

  /**
   * Initializes the Stackpane object of this class and the primary stage that sets the scene.
   * 
   * @param stage the stage to initialize.
   */
  public void initStage(Stage stage) {
    root = new StackPane();
    root.setAlignment(Pos.CENTER);


    root.getChildren().add(iv);
    root.getChildren().add(rectangle);

    GridPane pane = new GridPane();
    pane.setPadding(new Insets(5, 5, 0, 0));
    pane.setAlignment(Pos.CENTER);
    pane.setVgap(10);

    HBox boxLeft = initPlayerPanel();
    HBox boxTopRight = initStatistics();
    HBox boxBottomRight = initPanelRight();

    GridPane.setConstraints(boxLeft, 0, 0);
    GridPane.setConstraints(boxTopRight, 0, 1);
    GridPane.setConstraints(boxBottomRight, 0, 2);

    pane.getChildren().addAll(boxLeft, boxTopRight, boxBottomRight);
    boxBottomRight.setTranslateY(-500);

    root.getChildren().add(pane);

    root.getChildren().add(lo);
    lo.setTranslateX(-625);
    lo.setTranslateY(-300);

    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();
    Scene scene = new Scene(root, width, height);

    stage.setTitle("Title");
    stage.setScene(scene);
  }

  /**
   * The inherited start method that launches the application.
   * 
   * @param primaryStage the Stage object to be inititialized.
   */
  @Override
  public void start(Stage primaryStage) {
    init(primaryStage);
  }
}
