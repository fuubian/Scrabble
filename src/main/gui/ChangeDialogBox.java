package main.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Represents a dialog popup window, in which the player can enter his new username or password.
 * 
 * @author ceho
 *
 */

public class ChangeDialogBox {

  public enum Type {
    USERNAME, PASSWORD;
  }

  /**
   * Individual width of the screen the application is being displayed on.
   */
  private int width;

  /**
   * Individual height of the screen the application is being displayed on.
   */
  // private int height;

  /**
   * Stage object to display the scene.
   */
  private Stage dialog;

  /**
   * Base/background.
   */
  private Rectangle rect;

  /**
   * Label that displays information for player.
   */
  private Label infoLabel;

  /**
   * TextField where player can enter his new username.
   */
  private TextField newUn;

  /**
   * String to store the new username.
   */
  private String username;

  /**
   * PasswordField where player can enter his new password.
   */
  private PasswordField newPw;

  /**
   * PasswordField where player can re-enter his new password.
   */
  private PasswordField repeatPw;

  /**
   * String to store the new password.
   */
  private String password;

  /**
   * Button to close the popup window.
   */
  private Button cancel;

  /**
   * Button to save the entered data and close the popup window.
   */
  private Button save;

  /**
   * HBox which stores cancel and save in a row.
   */
  private HBox buttons;

  /**
   * ErrorAlert pops up when the entered new passwords do not match.
   */
  private ErrorBox error = new ErrorBox();

  /**
   * Flag to indicate whether the player wants to change his username oder change his password.
   */
  // private boolean un = false;

  private Type type;

  /**
   * Stores the info message that is being displayed.
   */
  private String info;

  /**
   * Flag to indicate whether user clicked on save or on cancel button.
   */
  private boolean answer = false;

  /**
   * Returns the new set username.
   * 
   * @return username.
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Return the new set password.
   * 
   * @return password.
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Return whether user clicked on cancel or save.
   * 
   * @return flag.
   */
  public boolean getAnswer() {
    return this.answer;
  }

  /**
   * Initializes the base/background.
   */
  private void initRectangle() {
    rect = new Rectangle();

    switch (type) {
      case USERNAME:
        rect.setHeight(width * 0.104167); // 150
        break;
      case PASSWORD:
        rect.setHeight(width * 0.13889); // 200
        break;
      default:
    }

    rect.setWidth(width * 0.2431); // 350-(width * 0.27778)
    rect.setFill(Color.WHITE);
    rect.setStroke(GuiSettings.BLUE);
  }

  /**
   * Initializes the Label and sets the info that is going the be displayed.
   * 
   * @param message that is going to be displayed.
   */
  private void setInfo() {
    switch (type) {
      case USERNAME:
        info = "Enter your new username.";
        break;
      case PASSWORD:
        info = "Enter your new password.";
        break;
      default:
    }

    infoLabel = new Label(info);
    infoLabel
        .setStyle("-fx-font-family: Futura;" + "-fx-font-size: 18px;" + "-fx-text-fill: #2e75b6");
    infoLabel.setPrefSize(width * 0.19444, width * 0.020833); // 280/30
  }

  /**
   * Initializes and styles the TextField.
   */
  private void initText() {
    newUn = new TextField();
    newUn.setPromptText("New username");
    newUn.setFocusTraversable(false);
    // newUn.setStyle("-fx-font-family: Calibri; -fx-font-size: 12px;");
    newUn.setStyle("-fx-font-family: Calibri; -fx-background-radius: 10px;" + "-fx-font-size: 15px;"
        + "-fx-background-color: white; " + "-fx-border-color: #A6A6A6;"
        + "-fx-border-radius: 10px;" + "-fx-text-fill: #7F7F7F");
    newUn.setPrefSize(width * 0.19444, width * 0.027778); // 280/40
  }

  /**
   * Initializes and styles the PasswordFields.
   */
  private void initPassword() {
    newPw = new PasswordField();
    newPw.setPromptText("New password");
    newPw.setFocusTraversable(false);
    // newPw.setStyle("-fx-font-family: Calibri; -fx-font-size: 12px;");
    newPw.setStyle(
        "-fx-background-radius: 10px;" + "-fx-font-size: 15px;" + "-fx-background-color: white; "
            + "-fx-border-color: #A6A6A6;" + "-fx-border-radius: 10px;");
    newPw.setPrefSize(width * 0.19444, width * 0.027778);// 280/40

    repeatPw = new PasswordField();
    repeatPw.setPromptText("Repeat new password");
    repeatPw.setFocusTraversable(false);
    // repeatPw.setStyle("-fx-font-family: Calibri; -fx-font-size: 12px;");
    repeatPw.setStyle(
        "-fx-background-radius: 10px;" + "-fx-font-size: 15px;" + "-fx-background-color: white;"
            + "-fx-border-color: #A6A6A6;" + "-fx-border-radius: 10px;");
    repeatPw.setPrefSize(width * 0.19444, width * 0.027778);// 280/40
  }

  /**
   * Initializes and styles the buttons.
   */
  private void initButtons() {
    cancel = new Button("Cancel");
    cancel.setStyle("-fx-background-color: #ff605c;" + "-fx-text-fill: white;"
        + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
        + "-fx-background-radius: 15px;" + "-fx-font-size: 13px;");
    cancel.setPrefSize(width * 0.069443, width * 0.020833); // 100/30

    save = new Button("Save");

    save.setStyle("-fx-background-color: #00ca4e;" + "-fx-text-fill: white;"
        + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
        + "-fx-background-radius: 15px;" + "-fx-font-size: 13px;");
    save.setPrefSize(width * 0.069443, width * 0.020833); // 100/30

    buttons = new HBox();
    buttons.setSpacing(width * 0.05556); // 80
    buttons.getChildren().add(cancel);
    buttons.getChildren().add(save);
  }

  /**
   * Sets all ActionEvents of the buttons.
   */
  private void setActionEvents() {
    newUn.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
          if (!newUn.getText().isBlank()) {
            username = newUn.getText();
            answer = true;
            dialog.close();
          } else {
            // answer = false;
            error.displayError("Username must not be empty.");
          }
        }
      }
    });

    newPw.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
          if (newPw.getText().equals(repeatPw.getText())) {
            if (!newPw.getText().isBlank()) {
              password = newPw.getText();
              answer = true;
              dialog.close();
            } else {
              // answer = false;
              error.displayError("Password must not be empty.");
            }
          } else {
            error.displayError("Passwords do not match.");
          }
        }
      }
    });

    repeatPw.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
          if (newPw.getText().equals(repeatPw.getText())) {
            if (!newPw.getText().isBlank()) {
              password = newPw.getText();
              answer = true;
              dialog.close();
            } else {
              // answer = false;
              error.displayError("Password must not be empty.");
            }
          } else {
            error.displayError("Passwords do not match.");
          }
        }
      }
    });

    save.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        switch (type) {
          case USERNAME:
            if (!newUn.getText().isBlank()) {
              username = newUn.getText();
              answer = true;
              dialog.close();
            } else {
              // answer = false;
              error.displayError("Username must not be empty.");
            }
            break;
          case PASSWORD:
            if (newPw.getText().equals(repeatPw.getText())) {
              if (!newPw.getText().isBlank()) {
                password = newPw.getText();
                answer = true;
                dialog.close();
              } else {
                // answer = false;
                error.displayError("Password must not be empty.");
              }
            } else {
              error.displayError("Passwords do not match.");
            }
            break;
          default:
        }
      }
    });

    save.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        save.setStyle("-fx-background-color: #1AD75F;" + "-fx-text-fill: white;"
            + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
            + "-fx-background-radius: 15px;" + "-fx-font-size: 14px;");
      }
    });

    save.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        save.setStyle("-fx-background-color: #00ca4e;" + "-fx-text-fill: white;"
            + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
            + "-fx-background-radius: 15px;" + "-fx-font-size: 13px;");
      }
    });

    cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        answer = false;
        dialog.close();
      }
    });

    cancel.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        cancel.setStyle("-fx-background-color: #FF7369;" + "-fx-text-fill: white;"
            + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
            + "-fx-background-radius: 15px;" + "-fx-font-size: 14px;");
      }
    });

    cancel.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        cancel.setStyle("-fx-background-color: #ff605c;" + "-fx-text-fill: white;"
            + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
            + "-fx-background-radius: 15px;" + "-fx-font-size: 13px;");
      }
    });

    newUn.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
          Boolean newPropertyValue) {
        if (newPropertyValue) {
          // on focus
          newUn.setStyle(
              "-fx-font-family: Calibri;" + "-fx-background-radius: 10px;" + "-fx-font-size: 15px;"
                  + "-fx-background-color: white; " + "-fx-border-color: #2e75b6;"
                  + "-fx-border-radius: 10px;" + "-fx-text-fill: #7F7F7F");
        } else {
          // out focus
          newUn.setStyle(
              "-fx-font-family: Calibri;" + "-fx-background-radius: 10px;" + "-fx-font-size: 15px;"
                  + "-fx-background-color: white; " + "-fx-border-color: #A6A6A6;"
                  + "-fx-border-radius: 10px;" + "-fx-text-fill: #7F7F7F");
        }
      }
    });

    newPw.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
          Boolean newPropertyValue) {
        if (newPropertyValue) {
          // on focus
          newPw.setStyle("-fx-background-radius: 10px;" + "-fx-font-size: 15px;"
              + "-fx-background-color: white; " + "-fx-border-color: #2e75b6;"
              + "-fx-border-radius: 10px;");
        } else {
          // out focus
          newPw.setStyle("-fx-background-radius: 10px;" + "-fx-font-size: 15px;"
              + "-fx-background-color: white; " + "-fx-border-color: #A6A6A6;"
              + "-fx-border-radius: 10px;");
        }
      }
    });

    repeatPw.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
          Boolean newPropertyValue) {
        if (newPropertyValue) {
          // on focus
          repeatPw.setStyle("-fx-background-radius: 10px;" + "-fx-font-size: 15px;"
              + "-fx-background-color: white; " + "-fx-border-color: #2e75b6;"
              + "-fx-border-radius: 10px;");
        } else {
          // out focus
          repeatPw.setStyle("-fx-background-radius: 10px;" + "-fx-font-size: 15px;"
              + "-fx-background-color: white; " + "-fx-border-color: #A6A6A6;"
              + "-fx-border-radius: 10px;");
        }
      }
    });
  }

  /**
   * Initializes the Stage and sets the Scene.
   */
  private void initStage() {
    dialog = new Stage();
    dialog.setTitle("Edit");
    dialog.initModality(Modality.APPLICATION_MODAL);

    // setInfo(info);

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setVgap(width * 0.006944); // 10
    grid.add(infoLabel, 0, 0);

    switch (type) {
      case USERNAME:
        grid.add(newUn, 0, 1);
        grid.add(buttons, 0, 2);
        break;
      case PASSWORD:
        grid.add(newPw, 0, 1);
        grid.add(repeatPw, 0, 2);
        grid.add(buttons, 0, 3);
        break;
      default:
    }

    StackPane root = new StackPane();

    root.getChildren().add(rect);
    root.getChildren().add(grid);

    Scene dialogScene;

    switch (type) {
      case USERNAME:
        dialogScene = new Scene(root, width * 0.25, width * 0.11111); // 360/160-(width * 0.28472)
        break;
      case PASSWORD:
        dialogScene = new Scene(root, width * 0.25, width * 0.14583); // 360/210
        break;
      default:
        dialogScene = new Scene(root, width * 0.25, width * 0.11111); // 360/160-(width * 0.28472)
    }
    dialog.setScene(dialogScene);
    dialog.showAndWait();
  }

  /**
   * Displays the popup-window.
   * 
   * @param type indicates whether the player wants to change his username or his password.
   */
  public void displayChange(Type type) {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    width = gd.getDisplayMode().getWidth();
    // height = gd.getDisplayMode().getHeight();

    this.type = type;

    answer = false;

    initRectangle();
    setInfo();
    initText();
    initPassword();
    initButtons();
    setActionEvents();
    initStage();
  }
}
