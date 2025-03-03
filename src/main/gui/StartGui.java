package main.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * The start page, shows the initial window whenever the application is being started; also sets
 * frequently shown components of the application, such as the background-image and the
 * close-button.
 * 
 * @author raydin
 *
 */
public class StartGui extends Application {
  /**
   * Individual width of the screen the application is being displayed on.
   */
  private int width;

  /**
   * Individual height of the screen the application is being displayed on.
   */
  private int height;

  /**
   * The alert box that is displayed when user wants to exit the application.
   */
  private AlertBox ab = new AlertBox();

  /**
   * The close button that the user clicks to close the application.
   */
  private Button close;

  /**
   * The start button to launch the game.
   */
  private Button start;

  /**
   * HBox to display the start button.
   */
  private HBox startBox;

  /**
   * The Stage object that should be shown/closed.
   */
  private Stage window;

  /**
   * The Image object to store the background picture in.
   */
  private Image background;

  /**
   * The ImageView object to store the Image-object in.
   */
  private ImageView iv;

  /**
   * The "SCRABBLE" headline at the beginning of the game.
   */
  private Label headline;

  /**
   * The GridPane object for the headline and the start button.
   */
  private GridPane grid;

  /**
   * The primary StackPane object.
   */
  private StackPane root;

  /**
   * Constructor of the MainGUI class, initializes all components and sets the first StackPane.
   */
  public StartGui() {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    width = gd.getDisplayMode().getWidth();
    height = gd.getDisplayMode().getHeight();

    initComponents();
    initBackground();
    setCloseButton();
    firstStackPane();
  }

  /**
   * Returns the start HBox.
   * 
   * @return the start HBox object.
   */
  public HBox getStartBox() {
    return this.startBox;
  }

  public Button getStartButton() {
    return this.start;
  }

  /**
   * Returns the close Button.
   * 
   * @return the close Button.
   */
  public Button getCloseButton() {
    return this.close;
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
   * Sets the root StackPane-object to the root-Object that is given as parameter.
   *
   * @param root the StackPane object to set root
   */
  public void setStackPane(StackPane root) {
    this.root = root;
  }

  /**
   * Initializes the background Object and sets the application's background image bckgrnd.png is a
   * free to use, copyright free image from https://pixabay.com/images/id-691842/ saved on: 31st of
   * March, 2021 at 10:32 pm.
   */
  private void initBackground() {
    // background = new Image("file:classes/images/bckgrnd.png");
    background = new Image("file:res/images/bckgrnd.png");
    iv = new ImageView(background);
    iv.setFitHeight(height);
    iv.setFitWidth(width);
  }

  /**
   * Initializes the components (headline-Label, start-Button) in the first window.
   */
  private void initComponents() {
    headline = new Label("S C R A B U R U");
    headline.setAlignment(Pos.CENTER);
    headline.setTextAlignment(TextAlignment.CENTER);
    // headline.setPrefWidth(width);
    // headline.setMinWidth(Region.USE_PREF_SIZE);

    headline.setStyle("-fx-text-fill: linear-gradient(to right, #84A9E7 0%, "
        + "#DEB7EE 15%, #ee7898 30%, #e6ed8e 45%, #9ccdb4 60%, " + "#84A9E7 75%, #ed9ab0 100% );"
        + "-fx-font-family: Futura; -fx-font-size: 100px; -fx-font-weight: bold");

    start = new Button("S T A R T");

    start.setStyle(
        "-fx-background-color: #2e75b6; -fx-text-fill: white; " + "-fx-background-radius: 20px;"
            + "-fx-font-family: Futura; " + "-fx-font-size: 25px; -fx-font-weight: bold");

    Tooltip startGameTip = new Tooltip("Start the game.");
    startGameTip.setStyle("-fx-font-family: Futura; -fx-font-size: 18px; "
        + "-fx-background-color: #f8c4c4; -fx-border-radius: 22px; "
        + "-fx-background-radius: 22px; -fx-text-fill: black; -fx-set-stroke: black;");

    // start.setPrefWidth(headline.getMinWidth()); // 625-width * 0.43403
    start.setPrefHeight(width * 0.05556); // 80
    start.setAlignment(Pos.CENTER);
    start.setTextAlignment(TextAlignment.CENTER);
    startBox = new HBox();
    startBox.setStyle(
        "-fx-background-color: #2e75b6; -fx-text-fill: white; " + "-fx-background-radius: 20px;"
            + "-fx-font-family: Futura; " + "-fx-font-size: 25px; -fx-font-weight: bold");
    startBox.setAlignment(Pos.CENTER);
    startBox.getChildren().add(start);

    start.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        startBox.setStyle(
            "-fx-background-color: #2E8CCD; -fx-text-fill: white; -fx-background-radius: 20px;"
                + "-fx-font-family: Futura; -fx-font-size: 27px; -fx-font-weight: bold");
        // 2E82C3
        start.setStyle(
            "-fx-background-color: #2E8CCD; -fx-text-fill: white; -fx-background-radius: 20px;"
                + "-fx-font-family: Futura; -fx-font-size: 27px; -fx-font-weight: bold");
      }
    });

    start.setTooltip(startGameTip);

    start.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        startBox.setStyle(
            "-fx-background-color: #2e75b6; -fx-text-fill: white; -fx-background-radius: 20px;"
                + "-fx-font-family: Futura; -fx-font-size: 25px; -fx-font-weight: bold");
        start.setStyle(
            "-fx-background-color: #2e75b6; -fx-text-fill: white; -fx-background-radius: 20px;"
                + "-fx-font-family: Futura; -fx-font-size: 25px; -fx-font-weight: bold");
      }
    });

    grid = new GridPane();
    grid.setVgap(width * 0.05556); // 80
    grid.setAlignment(Pos.CENTER);
    grid.add(headline, 0, 0);
    grid.add(startBox, 0, 1);
  }

  /**
   * The start method to run the application; sets the window (closing) event of the close-Button
   * and sets the scene.
   */
  @Override
  public void start(Stage stage) {
    window = stage;

    window.setOnCloseRequest(actionEvent -> {
      actionEvent.consume();
      closeApp();
    });

    Scene scene = new Scene(root, width, height);
    window.setScene(scene);
    window.show();
  }

  /**
   * The method closes the stage.
   * 
   * @param stage The stage that is going to be closed.
   */
  public void close(Stage stage) {
    window.close();
  }

  /**
   * Sets the root StackPane object as the initial StackPane.
   */
  public void firstStackPane() {
    root = new StackPane();
    root.getChildren().add(iv);
    root.getChildren().add(grid);
    root.getChildren().add(close);
  }

  /**
   * Initializes the close-button and sets their EventHandler; exit.png is a copyright free image,
   * drawn by @/raydin.
   */
  private void setCloseButton() {
    close = new Button();

    Image exit =
        new Image("file:res/images/exit.png", width * 0.04167, width * 0.04167, true, true);
    // 60/60
    ImageView iv = new ImageView(exit);
    close.setGraphic(iv);

    close.setStyle("-fx-background-color: transparent;");

    close.setPrefHeight(width * 0.01042); // 15
    close.setPrefWidth(width * 0.01389); // 20

    close.setTranslateX(width * 0.24306); // 350
    close.setTranslateY(-(width * 0.132)); // -190

    close.setOnAction(actionEvent -> closeApp());

    close.setOnMouseEntered(mouseEvent -> {
      Image exitNew =
          new Image("file:res/images/exit.png", width * 0.0486, width * 0.0486, true, true);
      // 70/70
      ImageView ivNew = new ImageView(exitNew);
      close.setGraphic(ivNew);
    });

    close.setOnMouseExited(mouseEvent -> {
      Image exitNew =
          new Image("file:res/images/exit.png", width * 0.04167, width * 0.04167, true, true);
      // 60/60
      ImageView ivNew = new ImageView(exitNew);
      close.setGraphic(ivNew);
    });
  }

  /**
   * Determines, based on the user interaction, whether the application should be closed or not.
   */
  private void closeApp() {
    ab.displayAlert("Are you sure you want to close SCRABBLE?");
    boolean answer = ab.getAnswer();

    if (answer) {
      window.close();
      System.exit(0);
    }
  }

  /**
   * The main method that launches the application.
   * 
   * @param args the path and modules needed to run the app.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
