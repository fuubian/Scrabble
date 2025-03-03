package main.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TutorialScript extends Application {

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

  private ImageView scriptView;

  private Button okButton;

  private ScriptType type;

  private ScrollPane scrollPane;

  private GridPane grid;

  private VBox vbox;

  /**
   * Calls the methods to initialize all components.
   */
  public TutorialScript(ScriptType type) {
    this.type = type;
    initScript();
    initStage();
  }


  private void initScript() {
    scrollPane = new ScrollPane();
    scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
    scrollPane.setMaxWidth(960);
    scrollPane.setMaxHeight(550);
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);

    vbox = new VBox();
    vbox.setSpacing(10);

    if (type == ScriptType.HOST_LOBBY) {
      Image[] script = new Image[3];
      script[0] = new Image("file:res/images/hostlobby1.png", 660, 440, true, true);
      script[1] = new Image("file:res/images/hostlobby2.png", 660, 440, true, true);
      script[2] = new Image("file:res/images/hostlobby3.png", 660, 440, true, true);

      ImageView[] scriptView = new ImageView[3];
      scriptView[0] = new ImageView(script[0]);
      scriptView[1] = new ImageView(script[1]);
      scriptView[2] = new ImageView(script[2]);

      vbox.getChildren().add(scriptView[0]);
      vbox.getChildren().add(scriptView[1]);
      vbox.getChildren().add(scriptView[2]);

      scrollPane.setContent(vbox);
    } else if (type == ScriptType.IN_GAME) {
      Image[] script = new Image[4];
      script[0] = new Image("file:res/images/ingame1.PNG", 660, 440, true, true);
      script[1] = new Image("file:res/images/ingame2.PNG", 660, 440, true, true);
      script[2] = new Image("file:res/images/ingame3.PNG", 660, 440, true, true);

      ImageView[] scriptView = new ImageView[3];
      scriptView[0] = new ImageView(script[0]);
      scriptView[1] = new ImageView(script[1]);
      scriptView[2] = new ImageView(script[2]);

      vbox.getChildren().add(scriptView[0]);
      vbox.getChildren().add(scriptView[1]);
      vbox.getChildren().add(scriptView[2]);

      scrollPane.setContent(vbox);
    } else {
      int i = 0;
    }

  }

  private void initStage() {
    root = new StackPane();
    root.getChildren().add(scrollPane);

    stage = new Stage();
    stage.setScene(new Scene(root, 670, 450));
    stage.setTitle("Help");
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    stage.showAndWait();

  }

}
