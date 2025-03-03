package main.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * The user interface in which the player can log-in or create a profile.
 * 
 * @author raydin
 *
 */

public class LoginPage extends Application {

  /**
   * ErrorBox object for displaying the error when passwords don't match.
   */
  private ErrorBox error = new ErrorBox();

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
   * The StackPane object for displaying the Scene.
   */
  private StackPane root = new StackPane();

  /**
   * The GridPane to store all login-details in to display.
   */
  private GridPane pane;

  /**
   * The image that is being used for the background.
   */
  private Image background;

  /**
   * The ImageView object in which the Image object background is nested into.
   */
  private ImageView iv;

  /**
   * The rectangle that is the base for the buttons.
   */
  private Rectangle rectangle;

  /**
   * The additional line between the login- and createAccount button.
   */
  private Rectangle line;

  /**
   * The textfield in which the player enters their username.
   */
  private TextField username;

  /**
   * The password-field in which the player enters their password.
   */
  private PasswordField password;

  /**
   * The password-field in which the player can re-enter their password if they are creating a
   * profile.
   */
  private PasswordField repeatPassword;

  /**
   * The "SCRABBLE" headline at the top of the login panel.
   */
  private Label headline;

  /**
   * The button that logs the user in after their input.
   */
  private Button login;

  /**
   * Redirects player to the panel on which they can create a profile.
   */
  private Button createAccount;

  /**
   * The button to click after inserting profile data.
   */
  private Button accountCreated;

  /**
   * The button to click to go back to the login-page.
   */
  private Button back;

  /**
   * The flag to switch between login and create-Account panels.
   */
  boolean isInCreateMode = false;

  /**
   * The TextField to enter the username when the player is in create mode.
   */
  private TextField username2;

  /**
   * Returns the TextField to enter the username when the player is in create mode.
   * 
   * @return the TextField to enter the username when the player is in create mode.
   */
  public TextField getUsername2() {
    return this.username2;
  }

  /**
   * The constructor of the LoginPage class. Invokes the init method.
   */
  public LoginPage() {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    width = gd.getDisplayMode().getWidth();
    height = gd.getDisplayMode().getHeight();

    init(this.stage);
  }

  /**
   * Returns the current StackPane object.
   * 
   * @return the current StackPane object.
   */
  public StackPane getStackPane() {
    init(stage);
    return this.root;
  }

  /**
   * Returns the login button.
   * 
   * @return the login button.
   */
  public Button getLoginButton() {
    return this.login;
  }

  /**
   * Returns the accountCreated button.
   * 
   * @return the accountCreated button.
   */
  public Button getCreateAccButton() {
    return this.accountCreated;
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
   * Returns the "inCreateMode"-flag.
   * 
   * @return the "inCreateMode"-flag.
   */
  public boolean getFlag() {
    return this.isInCreateMode;
  }

  /**
   * Returns the TextField for the password.
   * 
   * @return the password TextField.
   */
  public PasswordField getPassword() {
    return this.password;
  }

  /**
   * Returns the TextField for the repeated password.
   * 
   * @return the repeated password TextField.
   */
  public PasswordField getRepeatPassword() {
    return this.repeatPassword;
  }

  /**
   * Returns the line for separation.
   * 
   * @return the line for separation.
   */
  public Rectangle getTopLine() {
    return this.line;
  }

  /**
   * Returns the ErrorBox object of this class.
   * 
   * @return the ErrorBox object of this class.
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
   * Sets the username of the player.
   * 
   * @param username the username of the player.
   */
  public void setUsername(TextField username) {
    this.username = username;
  }

  /**
   * Sets the password of the player.
   * 
   * @param password the password of the player.
   */
  public void setPassword(PasswordField password) {
    this.password = password;
  }

  /**
   * Sets the repeated password of the player.
   * 
   * @param repeatPassword the repeated password of the player.
   */
  public void setRepeatPassword(PasswordField repeatPassword) {
    this.repeatPassword = repeatPassword;
  }


  /**
   * Returns the Button to click when the new account details have been input.
   * 
   * @return the Button to click when the new account details have been input.
   */
  public Button getAccountCreated() {
    return this.accountCreated;
  }

  /**
   * Returns the TextField of the username.
   * 
   * @return the TextField of the username.
   */
  public TextField getUsername() {
    return this.username;
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
   * Sets the StackPane of this class.
   * 
   * @param pane the StackPane object of this class.
   */
  public void setStackPane(StackPane pane) {
    this.root = pane;
  }

  /**
   * Sets the in create mode flag.
   * 
   * @param value the flag to be set.
   */
  public void setFlag(boolean value) {
    this.isInCreateMode = value;
  }

  /**
   * The inherited method to show the Scene when the class is run.
   * 
   * @param stage the stage object of the scene.
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
   * @param stage The stage that is going to be closed.
   */
  public void close(Stage stage) {
    stage.close();
  }

  /**
   * Initializes all relevant objects and the stage of the login-page.
   * 
   * @param stage the stage of the login-page.
   */
  public void init(Stage stage) {
    initBackground();
    optionsBackground();
    initButtons();
    initLoginDetails();
    initLabel();
    setActionEvents();
    initStage(stage);
  }

  /**
   * Initializes the Image-object "background" by using bckgrnd.png; bckgrnd.png is a free to use,
   * copyright free image from https://pixabay.com/images/id-691842/ saved on: 31st of March, 2021
   * at 10:32 pm.
   */
  private void initBackground() {
    background = new Image("file:res/images/bckgrnd.png");
    iv = new ImageView(background);
    iv.setFitHeight(height);
    iv.setFitWidth(width);
  }

  /**
   * Initializes the rectangle behind the login options.
   */
  private void optionsBackground() {
    rectangle = new Rectangle();
    rectangle.setWidth(width * 0.3125); // 450
    rectangle.setHeight(width * 0.2778); // 400-height * 0.44445

    rectangle.setStyle("-fx-stroke-line-cap: round;");

    rectangle.setFill(Color.WHITE);
    rectangle.setStroke(GuiSettings.BLUE);
    rectangle.setArcHeight(30);
    rectangle.setArcWidth(30);

    line = new Rectangle();
    line.setWidth(width * 0.229); // 330
    line.setHeight(width * 0.00069); // 1-height * 0.0011
    line.setStyle("-fx-stroke-line-cap: round;");
    line.setFill(GuiSettings.GRAY_BORDER);
    line.setOpacity(70);
  }

  /**
   * Initializes the login- and the createAccount button and styles them accordingly; return.png is
   * a copyright free image, drawn by @/raydin.
   */
  private void initButtons() {

    login = new Button("Log In");
    createAccount = new Button("Create Profile");

    login.setStyle("-fx-background-color: #2e75b6;" + "-fx-background-radius: 17px; "
        + "-fx-text-fill: white; " + "-fx-font-family: Futura; -fx-font-weight: bold; "
        + "-fx-font-size: 20px; -fx-border-color: #2e75b6; -fx-border-radius: 17px;");

    login.setPrefWidth(width * 0.229); // 330
    login.setPrefHeight(width * 0.0347);// 50-height * 0.0556

    createAccount.setStyle("-fx-background-color: white; " + "-fx-background-radius: 17px; "
        + "-fx-text-fill: #2e75b6; " + "-fx-font-family: Futura; -fx-font-weight: bold; "
        + "-fx-font-size: 20px; -fx-border-color: #2e75b6; -fx-border-radius: 17px;");

    createAccount.setPrefWidth(width * 0.229); // 330
    createAccount.setPrefHeight(width * 0.0347);// 50-height * 0.0556

    accountCreated = new Button("Create Profile");
    accountCreated.setStyle("-fx-background-color: #2e75b6; " + "-fx-background-radius: 17px; "
        + "-fx-text-fill: white; " + "-fx-font-family: Futura; " + "-fx-font-size: 20px; "
        + "-fx-border-radius: 17px; -fx-border-color: #2e75b6;  -fx-font-weight: bold;");

    accountCreated.setPrefWidth(width * 0.229); // 330
    accountCreated.setPrefHeight(width * 0.0347);// 50-height * 0.0556

    back = new Button();
    Image im = new Image("file:res/images/return.png", width * 0.021, width * 0.021, true, true);
    // // 30/30
    ImageView imv = new ImageView(im);
    back.setGraphic(imv);
    back.setStyle("-fx-background-color: transparent;");
    back.setTranslateX(-(width * 0.132)); // -190
    back.setTranslateY(-(width * 0.118)); // -170-height * 0.1889
  }

  /**
   * Initializes the username textfield and the passwordfield and styles them accordingly.
   */
  private void initLoginDetails() {
    username = new TextField();
    username.setPromptText("Username");
    username.setFocusTraversable(false);
    username.setStyle("-fx-font-family: Calibri;" + "-fx-background-radius: 10px;"
        + "-fx-font-size: 17px;" + "-fx-background-color: white; " + "-fx-border-color: #A6A6A6;"
        + "-fx-border-radius: 10px;" + "-fx-text-fill: #7F7F7F");

    username.setPrefWidth(width * 0.229); // 330
    username.setPrefHeight(width * 0.0347);// 50-height * 0.0556

    username2 = new TextField();
    username2.setPromptText("Username");
    username2.setFocusTraversable(false);
    username2.setStyle("-fx-font-family: Calibri;" + "-fx-background-radius: 10px;"
        + "-fx-font-size: 17px;" + "-fx-background-color: white; " + "-fx-border-color: #A6A6A6;"
        + "-fx-border-radius: 10px;" + "-fx-text-fill: #7F7F7F");

    username2.setPrefWidth(width * 0.229); // 330
    username2.setPrefHeight(width * 0.0347);// 50-height * 0.0556

    password = new PasswordField();
    password.setPromptText("Password");
    password.setFocusTraversable(false);
    password.setStyle(
        "-fx-background-radius: 10px;" + "-fx-font-size: 17px;" + "-fx-background-color: white; "
            + "-fx-border-color: #A6A6A6;" + "-fx-border-radius: 10px;");

    password.setPrefWidth(width * 0.229); // 330
    password.setPrefHeight(width * 0.0347);// 50-height * 0.0556

    repeatPassword = new PasswordField();
    repeatPassword.setPromptText("Repeat Password");
    repeatPassword.setFocusTraversable(false);
    repeatPassword.setStyle(
        "-fx-background-radius: 10px;" + "-fx-font-size: 17px;" + "-fx-background-color: white; "
            + "-fx-border-color: #A6A6A6;" + "-fx-border-radius: 10px;");

    repeatPassword.setPrefWidth(width * 0.229); // 330
    repeatPassword.setPrefHeight(width * 0.0347);// 50-height * 0.0556
  }

  /**
   * Initializes the headline label and styles it.
   */
  private void initLabel() {
    headline = new Label("S C R A B U R U");
    headline.setAlignment(Pos.CENTER);
    headline.setTextAlignment(TextAlignment.CENTER);
    headline.setPrefWidth(width * 0.229); // 330
    headline.setPrefHeight(width * 0.0347);// 50-height * 0.0556
    // headline.setStyle("-fx-font-family: Futura; -fx-background-color: #fbfaff;"
    // + "-fx-text-fill: linear-gradient(to right, red, orange, #e7eb00, green, "
    // + "blue, indigo, violet); -fx-font-size: 35px;");
    headline.setStyle("-fx-font-family: Futura; -fx-background-color: transparent;"
        + "-fx-text-fill: linear-gradient(to right, #84A9E7 0%, "
        + "#DEB7EE 15%, #ee7898 30%, #e6ed8e 45%, #9ccdb4 60%, " + "#84A9E7 75%, #ed9ab0 100% );"
        + " -fx-font-size: 35px;" + "-fx-font-weight: bold");
  }

  /**
   * Initializes the stage of the login-panel.
   * 
   * @param stage the stage that presents the login options.
   */
  private void initStage(Stage stage) {

    root = new StackPane();

    pane = new GridPane();
    pane.setPadding(new Insets(width * 0.00347, width * 0.00347, 0, 0)); // 5/5/0/0-height * 0.0056
    pane.setAlignment(Pos.CENTER);
    pane.setVgap(width * 0.00694); // 10-height * 0.0111

    GridPane.setConstraints(headline, 0, 0);
    GridPane.setConstraints(username, 0, 1);
    GridPane.setConstraints(password, 0, 2);
    GridPane.setConstraints(login, 0, 3);
    GridPane.setConstraints(line, 0, 4);
    GridPane.setConstraints(createAccount, 0, 5);

    pane.getChildren().addAll(headline, username, password, login, line, createAccount);

    root.getChildren().add(iv);
    root.getChildren().add(rectangle);

    root.getChildren().add(pane);
    root.getChildren().add(back);
  }

  /**
   * Sets all ActionEvents of the buttons.
   */
  private void setActionEvents() {

    createAccount.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {

        pane.getChildren().removeAll(login, line, createAccount);

        GridPane.setConstraints(repeatPassword, 0, 3);
        GridPane.setConstraints(line, 0, 4);
        GridPane.setConstraints(accountCreated, 0, 5);
        GridPane.setConstraints(back, 0, 6);
        pane.getChildren().addAll(repeatPassword, line, accountCreated, back);

        isInCreateMode = true;

        if (username.getText().length() > 15) {
          error.displayError("Username too long.");
        }
      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {

        if (isInCreateMode) {

          pane.getChildren().removeAll(repeatPassword, accountCreated);

          GridPane.setConstraints(username, 0, 1);
          GridPane.setConstraints(login, 0, 3);
          GridPane.setConstraints(line, 0, 4);
          GridPane.setConstraints(createAccount, 0, 5);

          pane.getChildren().addAll(login, createAccount);

          isInCreateMode = false;
        }
      }
    });

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

    login.setOnMouseEntered(mouseEvent -> {
      login.setStyle("-fx-background-color: #2E8CCD;" + "-fx-background-radius: 17px; "
          + "-fx-text-fill:white; " + "-fx-font-family: Futura; -fx-font-weight: bold; "
          + "-fx-font-size: 20px; -fx-border-color: #2e75b6; -fx-border-radius: 17px;");
    });

    login.setOnMouseExited(mouseEvent -> {
      login.setStyle("-fx-background-color: #2e75b6;" + "-fx-background-radius: 17px; "
          + "-fx-text-fill: white; " + "-fx-font-family: Futura; -fx-font-weight: bold; "
          + "-fx-font-size: 20px; -fx-border-color: #2e75b6; -fx-border-radius: 17px;");
    });

    createAccount.setOnMouseEntered(mouseEvent -> {
      createAccount.setStyle("-fx-background-color: #2e75b6; " + "-fx-background-radius: 17px; "
          + "-fx-text-fill: white; " + "-fx-font-family: Futura; -fx-font-weight: bold; "
          + "-fx-font-size: 20px; -fx-border-color: #2e75b6; -fx-border-radius: 17px;");
    });

    createAccount.setOnMouseExited(mouseEvent -> {
      createAccount.setStyle("-fx-background-color: white; " + "-fx-background-radius: 17px; "
          + "-fx-text-fill: #2e75b6; " + "-fx-font-family: Futura; -fx-font-weight: bold; "
          + "-fx-font-size: 20px; -fx-border-color: #2e75b6; -fx-border-radius: 17px;");
    });

    accountCreated.setOnMouseEntered(mouseEvent -> {
      accountCreated.setStyle("-fx-background-color: #2E8CCD; " + "-fx-background-radius: 17px; "
          + "-fx-text-fill: white; " + "-fx-font-family: Futura; " + "-fx-font-size: 20px; "
          + "-fx-border-radius: 17px; -fx-border-color: #2e75b6; -fx-font-weight: bold;");
    });

    accountCreated.setOnMouseExited(mouseEvent -> {
      accountCreated.setStyle("-fx-background-color: #2e75b6; " + "-fx-background-radius: 17px; "
          + "-fx-text-fill: white; " + "-fx-font-family: Futura; " + "-fx-font-size: 20px; "
          + "-fx-border-radius: 17px; -fx-border-color: #2e75b6; -fx-font-weight: bold;");
    });

    username.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.TAB)) {
          password.requestFocus();
        }
      }
    });

    password.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.TAB)) {
          // if (isInCreateMode) {
          repeatPassword.requestFocus();
          // }
        }
      }
    });

    username.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
          Boolean newPropertyValue) {
        if (newPropertyValue) {
          // on focus
          username.setStyle(
              "-fx-font-family: Calibri;" + "-fx-background-radius: 10px;" + "-fx-font-size: 17px;"
                  + "-fx-background-color: white; " + "-fx-border-color: #2e75b6;"
                  + "-fx-border-radius: 10px;" + "-fx-text-fill: #7F7F7F");
        } else {
          // out focus
          username.setStyle(
              "-fx-font-family: Calibri;" + "-fx-background-radius: 10px;" + "-fx-font-size: 17px;"
                  + "-fx-background-color: white; " + "-fx-border-color: #A6A6A6;"
                  + "-fx-border-radius: 10px;" + "-fx-text-fill: #7F7F7F");
        }
      }
    });

    username2.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
          Boolean newPropertyValue) {
        if (newPropertyValue) {
          // on focus
          username2.setStyle(
              "-fx-font-family: Calibri;" + "-fx-background-radius: 10px;" + "-fx-font-size: 17px;"
                  + "-fx-background-color: white; " + "-fx-border-color: #2e75b6;"
                  + "-fx-border-radius: 10px;" + "-fx-text-fill: #7F7F7F");
        } else {
          // out focus
          username2.setStyle(
              "-fx-font-family: Calibri;" + "-fx-background-radius: 10px;" + "-fx-font-size: 17px;"
                  + "-fx-background-color: white; " + "-fx-border-color: #A6A6A6;"
                  + "-fx-border-radius: 10px;" + "-fx-text-fill: #7F7F7F");
        }
      }
    });

    password.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
          Boolean newPropertyValue) {
        if (newPropertyValue) {
          // on focus
          password.setStyle("-fx-background-radius: 10px;" + "-fx-font-size: 17px;"
              + "-fx-background-color: white; " + "-fx-border-color: #2e75b6;"
              + "-fx-border-radius: 10px;");
        } else {
          // out focus
          password.setStyle("-fx-background-radius: 10px;" + "-fx-font-size: 17px;"
              + "-fx-background-color: white; " + "-fx-border-color: #A6A6A6;"
              + "-fx-border-radius: 10px;");
        }
      }
    });

    repeatPassword.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
          Boolean newPropertyValue) {
        if (newPropertyValue) {
          // on focus
          repeatPassword.setStyle("-fx-background-radius: 10px;" + "-fx-font-size: 17px;"
              + "-fx-background-color: white; " + "-fx-border-color: #2e75b6;"
              + "-fx-border-radius: 10px;");
        } else {
          // out focus
          repeatPassword.setStyle("-fx-background-radius: 10px;" + "-fx-font-size: 17px;"
              + "-fx-background-color: white; " + "-fx-border-color: #A6A6A6;"
              + "-fx-border-radius: 10px;");
        }
      }
    });
  }
}
