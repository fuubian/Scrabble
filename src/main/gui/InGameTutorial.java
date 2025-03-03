package main.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Represents the InGame session of a tutorial game.
 * 
 * @author ceho
 *
 */

public class InGameTutorial extends InGame {
  /**
   * StackPane object for letters and action buttons.
   */
  private StackPane lettersPane;

  /**
   * Calls the super method and initializes all methods in this class.
   */
  void init(Stage stage) {
    super.init(stage);
    initLetter();
    initStage(stage);
  }

  /**
   * Initializes and sets the panel on which bag of tiles and action buttons are going to be
   * displayed.
   */
  private void initLetter() {
    Rectangle letterRect = new Rectangle();
    letterRect.setWidth(width * 0.20833); // 300
    letterRect.setHeight(height * 0.23332); // 210
    letterRect.setArcWidth(30);
    letterRect.setArcHeight(30);
    letterRect.setFill(GuiSettings.GRAY_FILL);
    letterRect.setStroke(GuiSettings.GRAY_BORDER);

    change.setPrefSize(width * 0.17708, height * 0.05556); // 255/50

    pass.setPrefSize(width * 0.17708, height * 0.05556); // 255/50

    GridPane letterGrid = new GridPane();
    letterGrid.setAlignment(Pos.CENTER);
    letterGrid.setVgap(height * 0.016667); // 15
    letterGrid.setHgap(height * 0.016667); // 15
    letterGrid.add(letters, 0, 0);
    letterGrid.add(bagOfTiles, 1, 0);
    letterGrid.add(change, 0, 1, 2, 1);
    letterGrid.add(pass, 0, 2, 2, 1);

    lettersPane = new StackPane();
    lettersPane.getChildren().add(letterRect);
    lettersPane.getChildren().add(letterGrid);
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
    gamePane.add(leadPane, 0, 0);
    gamePane.add(gameGui.getGameGui(), 1, 0); // 600x600
    gamePane.add(rackPane, 1, 1);
    gamePane.add(lettersPane, 2, 0);
    gamePane.add(logout, 2, 0);
    gamePane.add(headline, 2, 0);

    logout.setTranslateY(-(height * 0.31112)); // -280
    logout.setTranslateX(width * 0.16875); // 243
    headline.setTranslateY(-(height * 0.31112)); // -280

    root.getChildren().add(gamePane);
  }
}
