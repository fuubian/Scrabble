package main.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.controller.DatabaseController;
import main.database.Profile;

/**
 * The player can change his username or password in his profile. The individual player statistics
 * are being displayed in the profile. The player can delete his profile.
 * 
 * @author ceho
 *
 */

public class PlayerProfile extends Application {
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
   * StackPane object for displaying the Scene.
   */
  private StackPane root;

  /**
   * GridPane to sort the objects on the rectangle.
   */
  private GridPane pane;

  /**
   * ImageView object in which the Image object background is nested into.
   */
  private ImageView iv;

  /**
   * Rectangle that is the base for the components.
   */
  private Rectangle rectangle;

  /**
   * Additional line between the statistics- and deleteProfile button.
   */
  private Rectangle line;

  /**
   * Label which displays the players username.
   */
  private Label usernameLabel;

  /**
   * Passwordfield which displays the encrypted password of the player.
   */
  private PasswordField passwordField;

  /**
   * "PROFILE" headline at the top of the profile panel.
   */
  private Label headline1;

  /**
   * "STATISTICS" headline at the top of the statistics panel.
   */
  private Label headline2;

  /**
   * Redirects player to the individual player statistics.
   */
  private Button statistics;

  /**
   * Deletes the players profile and redirects the player back to the login page.
   */
  private Button deleteProfile;

  /**
   * Redirects to the previous page.
   */
  private Button back;

  /**
   * Opens up a window, where the player can change his username.
   */
  private Button changeUn;

  /**
   * Opens up a window, where the player can change his password.
   */
  private Button changePw;

  /**
   * Stores usernameTF and changeUN in a row.
   */
  private StackPane stackUn;

  /**
   * Stores passwordPF and changePW in a row.
   */
  private StackPane stackPw;

  /**
   * ChangeDialogBox that is being displayed when the player wants to change his username.
   */
  private ChangeDialogBox dialogUn = new ChangeDialogBox();

  /**
   * ChangeDialogBox that is being displayed when the player wants to change his password.
   */
  private ChangeDialogBox dialogPw = new ChangeDialogBox();

  /**
   * Flag to switch between profile and statistics panel.
   */
  boolean isInStatisticsMode = false;

  /**
   * Stores the player's username.
   */
  private String username = "USERNAME";

  /**
   * Stores the player's password.
   */
  private String password = "secretpassword";

  /**
   * Labels for the different statistics categories.
   */
  private Label[] categoryStats = new Label[10];

  /**
   * Labels for the network statistics value.
   */
  private Label[] networkStats = new Label[10];

  /**
   * Labels for the tutorial statistics value.
   */
  private Label[] tutStats = new Label[10];

  /**
   * GridPane to display the statistics table.
   */
  private GridPane tableGrid;

  /**
   * ErrorBox object to display errors.
   */
  private ErrorBox error = new ErrorBox();

  /**
   * The constructor of the PlayerProfile class. Invokes the init method.
   */
  public PlayerProfile() {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    width = gd.getDisplayMode().getWidth();
    height = gd.getDisplayMode().getHeight();

    init(this.stage);
  }

  /**
   * Sets "un" as the new username and changes the username Label accordingly.
   * 
   * @param un new username.
   */
  public void setUn(String un) {
    username = un;
    usernameLabel.setText(un);
  }

  /**
   * Return the username.
   * 
   * @return username.
   */
  public String getUn() {
    return username;
  }

  /**
   * Sets "pw" as the new password and changes the PasswordField accordingly.
   * 
   * @param pw new password.
   */
  public void setPw(String pw) {
    password = pw;
    passwordField.setText(pw);
  }

  /**
   * Return the password.
   * 
   * @return password.
   */
  public String getPw() {
    return password;
  }

  /**
   * Returns the root StackPane.
   * 
   * @return root StackPane.
   */
  public StackPane getStackPane() {
    return this.root;
  }

  /**
   * Returns the back button.
   * 
   * @return the back button.
   */
  public Button getBackButton() {
    return this.back;
  }

  /**
   * Returns the deleteProfile button.
   * 
   * @return the deleteProfile button.
   */
  public Button getDeleteButton() {
    return this.deleteProfile;
  }

  /**
   * Returns the "Statistics" headline.
   * 
   * @return the "Statistics" headline.
   */
  public Label getHeadline2() {
    return this.headline2;
  }

  /**
   * Returns the "Profile" headline.
   * 
   * @return the "Profile" headline.
   */
  public Label getHeadline1() {
    return this.headline1;
  }

  /**
   * Returns the Statistics table.
   * 
   * @return the Statistics table.
   */
  public GridPane getTable() {
    return this.tableGrid;
  }

  /**
   * Returns the StackPane for the username handling.
   * 
   * @return the StackPane for the username handling.
   */
  public StackPane getStackUn() {
    return this.stackUn;
  }

  /**
   * Returns the StackPane for the password handling.
   * 
   * @return the StackPane for the password handling.
   */
  public StackPane getStackPw() {
    return this.stackPw;
  }

  /**
   * Returns the Statistics Button.
   * 
   * @return the Statistics Button.
   */
  public Button getStatistics() {
    return this.statistics;
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
   * Returns the delete profile Button.
   * 
   * @return the delete profile Button.
   */
  public Button getDeleteProfile() {
    return this.deleteProfile;
  }

  /**
   * Returns the statistics mode flag.
   * 
   * @return the statistics mode flag.
   */
  public boolean getFlag() {
    return this.isInStatisticsMode;
  }

  /**
   * Sets the flag.
   * 
   * @param value flag value.
   */
  public void setFlag(boolean value) {
    this.isInStatisticsMode = value;
  }

  /**
   * Sets the category name for the player statistics.
   * 
   * @param indx of row.
   * @param value name of category.
   */
  public void setCategoryStats(int indx, String value) {
    this.categoryStats[indx].setText(value);
  }

  /**
   * Sets the values for the player network statistics.
   * 
   * @param indx of row.
   * @param value of statistic.
   */
  public void setNetStats(int indx, String value) {
    this.networkStats[indx].setText(value);
  }

  /**
   * Sets the values for the player tutorial statistics.
   * 
   * @param indx of row.
   * @param value of statistic.
   */
  public void setTutStats(int indx, String value) {
    this.tutStats[indx].setText(value);
  }

  /**
   * Returns the ErrorBox.
   * 
   * @return error.
   */
  public ErrorBox getError() {
    return error;
  }

  /**
   * The inherited method to show the Scene when the class is run.
   */
  @Override
  public void start(Stage stage) {
    Scene scene = new Scene(root, width, height);

    stage.setScene(scene);
    stage.show();
  }

  /**
   * The method closes the stage.
   * 
   * @param stage that is going to be closed.
   */
  public void close(Stage stage) {
    stage.close();
  }

  /**
   * Initializes all relevant objects.
   * 
   * @param stage of the playerprofile-page.
   */
  private void init(Stage stage) {
    initBackground();
    optionsBackground();
    initButtons();
    initPlayerDetails(username, password);
    initLabel();
    initMiniStack();
    initTable();
    setActionEventUn();
    setActionEventPw();
    setActionEventStat();
    setActionEventDelete();
    setActionEventBack();
    initStage(stage);
  }

  /**
   * Initializes the Image-object "background" by using bckgrnd.png; bckgrnd.png is a free to use,
   * copyright free image from https://pixabay.com/images/id-691842/ saved on: 31st of March, 2021
   * at 10:32 pm.
   */
  private void initBackground() {
    Image background = new Image("file:res/images/bckgrnd.png");
    iv = new ImageView(background);
    iv.setFitHeight(height);
    iv.setFitWidth(width);
  }

  /**
   * Initializes the rectangle behind the profile.
   */
  private void optionsBackground() {
    rectangle = new Rectangle();
    rectangle.setWidth(width * 0.3125); // 450
    rectangle.setHeight(width * 0.2778); // 400-height * 0.44445
    rectangle.setArcWidth(30);
    rectangle.setArcHeight(30);

    rectangle.setStyle("-fx-stroke-line-cap: round;");

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
   * Initializes the statistics, deleteProfile, back, changeUN and changePW button, styles them
   * accordingly.
   */
  private void initButtons() {

    statistics = new Button("Player Statistics");

    statistics.setStyle("-fx-background-color: white; " + "-fx-background-radius: 17px; "
        + "-fx-text-fill: #2E75B6; " + "-fx-font-family: Futura; " + "-fx-font-size: 20px;"
        + "-fx-font-weight: bold;" + "-fx-border-color: #2E75B6;" + "-fx-border-radius: 17px;");

    statistics.setPrefWidth(width * 0.229); // 330
    statistics.setPrefHeight(width * 0.0347);// 50-height * 0.0556

    deleteProfile = new Button("Delete Profile");

    deleteProfile.setStyle("-fx-background-color: white; " + "-fx-background-radius: 17px; "
        + "-fx-text-fill: #C00000; " + "-fx-font-family: Futura; " + "-fx-font-size: 20px;"
        + "-fx-font-weight: bold;" + "-fx-border-color: #C00000;" + "-fx-border-radius: 17px;");

    deleteProfile.setPrefWidth(width * 0.229); // 330
    deleteProfile.setPrefHeight(width * 0.0347);// 50-height * 0.0556

    back = new Button();
    Image im = new Image("file:res/images/return.png", width * 0.021, width * 0.021, true, true);
    // 30/30
    ImageView imv = new ImageView(im);

    back.setGraphic(imv);

    back.setStyle("-fx-background-color: white;");

    changeUn = new Button("change");

    changeUn.setStyle("-fx-background-color: #7592BC;" + "-fx-background-radius: 15px;"
        + "-fx-text-fill: white;" + "-fx-font-family: Futura;" + "-fx-font-size: 13px;");

    changeUn.setPrefWidth(width * 0.0521); // 75
    changeUn.setPrefHeight(width * 0.02083);// 30

    changePw = new Button("change");

    changePw.setStyle("-fx-background-color: #7592BC;" + "-fx-background-radius: 15px;"
        + "-fx-text-fill: white;" + "-fx-font-family: Futura;" + "-fx-font-size: 13px;");

    changePw.setPrefWidth(width * 0.0521); // 75
    changePw.setPrefHeight(width * 0.02083);// 30
  }

  /**
   * Initializes usernameLabel and passwordField and styles them accordingly.
   * 
   * @param un the players username.
   * @param pw the players password.
   */
  private void initPlayerDetails(String un, String pw) {
    usernameLabel = new Label();
    usernameLabel.setStyle("-fx-font-family: Futura; -fx-background-radius: 10px;"
        + "-fx-font-weight: bold;" + "-fx-font-size: 17px;" + "-fx-background-color: white; "
        + "-fx-border-color: #A6A6A6;" + "-fx-border-radius: 10px;" + "-fx-text-fill: #7F7F7F");

    usernameLabel.setPadding(new Insets(0, 0, 0, 10));

    usernameLabel.setPrefWidth(width * 0.229); // 330
    usernameLabel.setPrefHeight(width * 0.0347);// 50-height * 0.0556

    passwordField = new PasswordField();
    // passwordField.setText(pw);
    passwordField.setFocusTraversable(false);
    passwordField.setEditable(false);
    passwordField.setMouseTransparent(true);
    passwordField.setStyle(
        "-fx-background-radius: 10px;" + "-fx-font-size: 17px;" + "-fx-background-color: white; "
            + "-fx-border-color: #A6A6A6;" + "-fx-border-radius: 10px;");

    passwordField.setPrefWidth(width * 0.229); // (245?) 230
    passwordField.setPrefHeight(width * 0.0347);// 50-height * 0.0556
  }

  /**
   * Initializes hBoxUn and adds usernameLabel and changeUn and initializes hBoxPw and adds
   * passwordField and changePw.
   */
  private void initMiniStack() {
    stackUn = new StackPane();
    stackUn.getChildren().add(usernameLabel);
    stackUn.getChildren().add(changeUn);
    changeUn.setTranslateX(width * 0.0833); // 120

    stackPw = new StackPane();
    stackPw.getChildren().add(passwordField);
    stackPw.getChildren().add(changePw);
    changePw.setTranslateX(width * 0.0833); // 120
  }

  /**
   * Initializes table and styles them accordingly.
   */
  private void initTable() {
    tableGrid = new GridPane();
    tableGrid.setAlignment(Pos.CENTER);
    tableGrid.setStyle("-fx-border-color: A6A6A6;");

    for (int i = 0; i < categoryStats.length; i++) {
      categoryStats[i] = new Label();
      categoryStats[i].setAlignment(Pos.CENTER);
      categoryStats[i].setTextAlignment(TextAlignment.CENTER);
      categoryStats[i].setPrefWidth(width * 0.104167); // 150
      categoryStats[i].setPrefHeight(width * 0.02083);// 30
      categoryStats[i].setStyle(
          "-fx-font-family: Calibri;" + "-fx-font-size: 17px;" + "-fx-background-color: #F2F2F2; "
              + "-fx-border-color: white;" + "-fx-text-fill: #7F7F7F;" + "-fx-border-width: 2px;");
      tableGrid.add(categoryStats[i], 0, i);

      networkStats[i] = new Label();
      networkStats[i].setAlignment(Pos.CENTER);
      networkStats[i].setTextAlignment(TextAlignment.CENTER);
      networkStats[i].setPrefWidth(width * 0.0625); // 90
      networkStats[i].setPrefHeight(width * 0.02083);// 30
      networkStats[i].setStyle(
          "-fx-font-family: Calibri;" + "-fx-font-size: 17px;" + "-fx-background-color: #F2F2F2; "
              + "-fx-border-color: white;" + "-fx-text-fill: #7F7F7F;" + "-fx-border-width: 2px;");
      tableGrid.add(networkStats[i], 1, i);

      tutStats[i] = new Label();
      tutStats[i].setAlignment(Pos.CENTER);
      tutStats[i].setTextAlignment(TextAlignment.CENTER);
      tutStats[i].setPrefWidth(width * 0.0625); // 90
      tutStats[i].setPrefHeight(width * 0.02083);// 30
      tutStats[i].setStyle(
          "-fx-font-family: Calibri;" + "-fx-font-size: 17px;" + "-fx-background-color: #F2F2F2; "
              + "-fx-border-color: white;" + "-fx-text-fill: #7F7F7F;" + "-fx-border-width: 2px;");
      tableGrid.add(tutStats[i], 2, i);
    }

    categoryStats[0].setText("Category");
    categoryStats[0].setPrefHeight(width * 0.02431); // 35
    categoryStats[0].setStyle(
        "-fx-font-family: Futura;" + "-fx-font-size: 20px;" + "-fx-background-color: #F2F2F2; "
            + "-fx-border-color: white;" + "-fx-text-fill: #E988A6;" + "-fx-border-width: 2px;");

    networkStats[0].setText("Network");
    networkStats[0].setPrefHeight(width * 0.02431); // 35
    networkStats[0].setStyle(
        "-fx-font-family: Futura;" + "-fx-font-size: 20px;" + "-fx-background-color: #F2F2F2; "
            + "-fx-border-color: white;" + "-fx-text-fill: #E988A6;" + "-fx-border-width: 2px;");

    tutStats[0].setText("Tutorial");
    tutStats[0].setPrefHeight(width * 0.02431); // 35
    tutStats[0].setStyle(
        "-fx-font-family: Futura;" + "-fx-font-size: 20px;" + "-fx-background-color: #F2F2F2; "
            + "-fx-border-color: white;" + "-fx-text-fill: #E988A6;" + "-fx-border-width: 2px;");
  }

  /**
   * Initializes the headline labels and styles it.
   */
  private void initLabel() {
    headline1 = new Label("P R O F I L E");
    headline1.setAlignment(Pos.CENTER);
    headline1.setTextAlignment(TextAlignment.CENTER);
    headline1.setPrefWidth(width * 0.229); // 330
    headline1.setPrefHeight(width * 0.0347);// 50-height * 0.0556
    // headline1.setStyle("-fx-font-family: Futura; -fx-text-fill: #2e75b6; "
    // + "-fx-font-size: 30px; -fx-font-weight: bold;");
    headline1.setStyle(
        "-fx-font-family: Futura;" + "-fx-text-fill: linear-gradient(to right, #84A9E7 0%, "
            + "#DEB7EE 15%, #ee7898 30%, #e6ed8e 45%, #9ccdb4 60%, "
            + "#84A9E7 75%, #ed9ab0 100% );" + "-fx-font-size: 35px;" + "-fx-font-weight: bold;");

    headline2 = new Label("S T A T I S T I C S");
    headline2.setAlignment(Pos.CENTER);
    headline2.setTextAlignment(TextAlignment.CENTER);
    headline2.setPrefWidth(width * 0.229); // 330
    headline2.setPrefHeight(width * 0.0347);// 50-height * 0.0556
    // headline2.setStyle("-fx-font-family: Futura; -fx-text-fill: #2e75b6; "
    // + "-fx-font-size: 30px; -fx-font-weight: bold;");
    headline2.setStyle(
        "-fx-font-family: Futura;" + "-fx-text-fill: linear-gradient(to right, #84A9E7 0%, "
            + "#DEB7EE 15%, #ee7898 30%, #e6ed8e 45%, #9ccdb4 60%, "
            + "#84A9E7 75%, #ed9ab0 100% );" + "-fx-font-size: 30px;" + "-fx-font-weight: bold;");
  }

  /**
   * Initializes the stage of the profile-panel.
   * 
   * @param stage that presents the profile options.
   */
  private void initStage(Stage stage) {

    root = new StackPane();

    pane = new GridPane();
    pane.setPadding(new Insets(width * 0.00347, width * 0.00347, 0, 0));
    // 5/5/0/0-height * 0.0056
    pane.setAlignment(Pos.CENTER);
    pane.setVgap(width * 0.00694); // 10-height * 0.0111
    GridPane.setConstraints(headline1, 0, 0);
    GridPane.setConstraints(stackUn, 0, 1);
    GridPane.setConstraints(stackPw, 0, 2);
    GridPane.setConstraints(statistics, 0, 3);
    GridPane.setConstraints(line, 0, 4);
    GridPane.setConstraints(deleteProfile, 0, 5);

    pane.getChildren().addAll(headline1, stackUn, stackPw, statistics, line, deleteProfile);

    // headline1.setTranslateX(85);
    // headline1.setTranslateY(-5);

    root.getChildren().addAll(iv);
    root.getChildren().add(rectangle);

    back.setTranslateX(-(width * 0.132)); // -190
    back.setTranslateY(-(width * 0.118)); // -170-height * 0.1889

    root.getChildren().add(pane);

  }

  /**
   * Sets ActionEvents of the button changeUn.
   */
  private void setActionEventUn() {

    changeUn.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        dialogUn.displayChange(ChangeDialogBox.Type.USERNAME);
        if (dialogUn.getAnswer()) {
          DatabaseController.updateUsername(dialogUn);
          usernameLabel.setText(Profile.getName());
        }
      }
    });

    changeUn.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        changeUn.setStyle("-fx-background-color: #75A0C8;" + "-fx-background-radius: 15px;"
            + "-fx-text-fill: white;" + "-fx-font-family: Futura;" + "-fx-font-size: 14px;");
      }
    });

    changeUn.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        changeUn.setStyle("-fx-background-color: #7592BC;" + "-fx-background-radius: 15px;"
            + "-fx-text-fill: white;" + "-fx-font-family: Futura;" + "-fx-font-size: 13px;");
      }
    });
  }

  /**
   * Sets ActionEvents of the button changePw.
   */
  private void setActionEventPw() {
    changePw.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        dialogPw.displayChange(ChangeDialogBox.Type.PASSWORD);
        if (dialogPw.getAnswer()) {
          DatabaseController.updatePassword(dialogPw);
          passwordField.setText(DatabaseController.getPassword(Profile.getName()));
        }
      }
    });

    changePw.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        changePw.setStyle("-fx-background-color: #75A0C8;" + "-fx-background-radius: 15px;"
            + "-fx-text-fill: white;" + "-fx-font-family: Futura;" + "-fx-font-size: 14px;");
      }
    });

    changePw.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        changePw.setStyle("-fx-background-color: #7592BC;" + "-fx-background-radius: 15px;"
            + "-fx-text-fill: white;" + "-fx-font-family: Futura;" + "-fx-font-size: 13px;");
      }
    });
  }

  /**
   * Sets ActionEvents of the button statistics.
   */
  private void setActionEventStat() {
    statistics.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        DatabaseController.setStatistics(PlayerProfile.this);
        pane.getChildren().removeAll(headline1, stackUn, stackPw, statistics, line, deleteProfile);

        // headline2.setTranslateX(45);
        // headline2.setTranslateY(-5);
        GridPane.setConstraints(headline2, 0, 0);
        // GridPane.setConstraints(table, 0, 2);
        GridPane.setConstraints(tableGrid, 0, 2);

        // pane.getChildren().addAll(headline2, table);
        pane.getChildren().addAll(headline2, tableGrid);

        // headline2.setTranslateY(10);
        tableGrid.setTranslateY(-(width * 0.0104167)); // -15

        isInStatisticsMode = true;
      }
    });

    statistics.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        statistics.setStyle("-fx-background-color: white; -fx-background-radius: 17px; "
            + "-fx-text-fill: #2E75B6; -fx-font-family: Futura; -fx-font-size: 21px;"
            + "-fx-font-weight: bold; -fx-border-color: #2E75B6; -fx-border-radius: 17px;");
      }
    });

    statistics.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        statistics.setStyle("-fx-background-color: white; -fx-background-radius: 17px; "
            + "-fx-text-fill: #2E75B6; -fx-font-family: Futura; -fx-font-size: 20px;"
            + "-fx-font-weight: bold; -fx-border-color: #2E75B6; -fx-border-radius: 17px;");
      }
    });
  }

  /**
   * Sets ActionEvents of the button deleteProfile.
   */
  private void setActionEventDelete() {
    deleteProfile.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        deleteProfile.setStyle("-fx-background-color: white; -fx-background-radius: 17px; "
            + "-fx-text-fill: #C00000; -fx-font-family: Futura; -fx-font-size: 21px;"
            + "-fx-font-weight: bold; -fx-border-color: #C00000; -fx-border-radius: 17px;");
      }
    });

    deleteProfile.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        deleteProfile.setStyle("-fx-background-color: white; -fx-background-radius: 17px; "
            + "-fx-text-fill: #C00000; -fx-font-family: Futura; -fx-font-size: 20px;"
            + "-fx-font-weight: bold; -fx-border-color: #C00000; -fx-border-radius: 17px;");
      }
    });
  }

  /**
   * Sets ActionEvents of the button back.
   */
  private void setActionEventBack() {
    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {

        if (isInStatisticsMode) {

          // pane.getChildren().removeAll(headline2, table);
          pane.getChildren().removeAll(headline2, tableGrid);

          GridPane.setConstraints(headline1, 0, 0);
          GridPane.setConstraints(stackUn, 0, 1);
          GridPane.setConstraints(stackPw, 0, 2);
          GridPane.setConstraints(statistics, 0, 3);
          GridPane.setConstraints(line, 0, 4);
          GridPane.setConstraints(deleteProfile, 0, 5);
          pane.getChildren().addAll(headline1, stackUn, stackPw, statistics, line, deleteProfile);

          isInStatisticsMode = false;
        }

      }
    });

    back.setOnMousePressed(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        Image im =
            new Image("file:res/images/return.png", width * 0.01736, width * 0.01736, true, true);
        // 25/25
        ImageView imv = new ImageView(im);

        back.setGraphic(imv);

        back.setStyle("-fx-background-color: white;");
      }
    });

    back.setOnMouseReleased(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        Image im =
            new Image("file:res/images/return.png", width * 0.021, width * 0.021, true, true);
        // 30/30
        ImageView imv = new ImageView(im);

        back.setGraphic(imv);

        back.setStyle("-fx-background-color: white;");
      }
    });
  }
}
