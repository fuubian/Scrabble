package main.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * Controls the different user interface pages in order to click from one page to another.
 * 
 * @author ceho
 *
 */

public class GuiController {
  /**
   * StartGui object to display the start page.
   */
  private StartGui start;

  /**
   * LoginPage object to display the login page.
   */
  private LoginPage login;

  /**
   * MainMenu object to display the main menu.
   */
  private MainMenu main;

  /**
   * PlayerProfile object to display the players profile.
   */
  private PlayerProfile profile;

  // private BaseLobby base;

  /**
   * HostGameLobby object to display the Host Game Lobby.
   */
  private HostGameLobby hostLobby;

  /**
   * JoinGameLobby object to display the Join Game Lobby.
   */
  private JoinGameLobby joinLobby;

  /**
   * TutorialGameLobby object to display the Tutorial Game Lobby.
   */
  // private TutorialGameLobby tutLobby;

  /**
   * InGame object to display the InGame session from a network game.
   */
  private InGameNetwork inGame;

  /**
   * InGameTutorial object to display the InGame session from a tutorial game.
   */
  private InGameTutorial inGameTut;

  /**
   * Stage object to display the scene.
   */
  private Stage stage = new Stage();

  /**
   * Flag to indicate whether the InGame session has been opened by HostGame or by JoinGame.
   */
  private boolean inHostGame = false;

  /**
   * Flag to indicate whether the JoinGameLobby or HostGameLobby has been joined.
   */
  private boolean inHostLobby = false;

  /**
   * AlertBox to alert the player when he presses the deleteProfile button.
   */
  private AlertBox deleteAlert = new AlertBox();

  /**
   * AlertBox to alert the player when he presses the door icon.
   */
  private AlertBox leaveAlert = new AlertBox();

  /**
   * The constructor of the GuiController class. Invokes the init method.
   * 
   * @param primaryStage the primary stage.
   */
  public GuiController(Stage primaryStage) {
    stage = primaryStage;
    init();
  }

  /**
   * Initializes all relevant objects.
   */
  public void init() {
    start = new StartGui();
    login = new LoginPage();
    profile = new PlayerProfile();
    main = new MainMenu();
    hostLobby = new HostGameLobby();
    // joinLobby = new JoinGameLobby();
    // tutLobby = new TutorialGameLobby();
    inGame = new InGameNetwork();
    inGameTut = new InGameTutorial();
    // base = new BaseLobby();
    setActionStart();
    setActionLogin();
    setActionMain();
    setActionNetwork();
    setActionTut();
    setActionProfile();
  }

  /**
   * Sets the ActionEvent for the button startGame for the start page.
   */
  public void setActionStart() {
    // start.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
    // public void handle(ActionEvent e) {
    // start.close(stage);
    // login.start(stage);
    // }
    // });
  }

  /**
   * Sets the ActionEvent for the button login and accountCreated for the login page.
   */
  public void setActionLogin() {
    login.getLoginButton().setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        login.close(stage);
        main.start(stage);
      }
    });

    login.getCreateAccButton().setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        login.close(stage);
        main.start(stage);
      }
    });
  }

  /**
   * Sets the ActionEvent to get from the main menu to the different lobbies and player profile.
   */
  public void setActionMain() {
    main.getProfile().setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        main.close(stage);
        profile.start(stage);
      }
    });

    main.getHostButton().setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        inHostLobby = true;
        main.close(stage);
        // hostLobby.start(stage);
      }
    });

    main.getJoinedButton().setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        inHostLobby = false;
        main.close(stage);
        // joinLobby.start(stage);
      }
    });

    main.getTutorialButton().setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        main.close(stage);
        // tutLobby.start(stage);
      }
    });

    // base.getLogout().setOnAction(new EventHandler<ActionEvent>() {
    // public void handle(ActionEvent e) {
    // (inHostLobby) {
    // hostLobby.close(stage);
    // } else {
    // joinLobby.close(stage);
    // }
    // main.start(stage);
    // }
    // });
  }

  /**
   * Sets the ActionEvent to get from the player profile back to the main menu.
   */
  public void setActionProfile() {
    // profile.getBackButton().setOnAction(new EventHandler<ActionEvent>() {
    // public void handle(ActionEvent e) {
    // if (!profile.getFlag()) {
    // profile.close(stage);
    // main.start(stage);
    // } else {
    // profile.setActionEventBack();
    // }
    // }
    // });

    profile.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        deleteAlert.displayAlert("Are you sure you want to delete your profile?");
        boolean answer = deleteAlert.getAnswer();

        if (answer) {
          profile.close(stage);
          start.start(stage);
        }
      }
    });
  }

  /**
   * Sets the ActionEvent to start a network game and to leave a network game.
   */
  public void setActionNetwork() {
    hostLobby.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        inHostGame = true;
        // hostLobby.close(stage);
        // inGame.start(stage);
      }
    });

    joinLobby.getReadyButton().setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        inHostGame = false;
        // joinLobby.close(stage);
        // inGame.start(stage);
      }
    });

    inGame.getLogout().setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        leaveAlert.displayAlert("Are you sure you want to leave the game?");
        boolean answer = deleteAlert.getAnswer();
        if (answer) {
          System.out.println("leave");
          // inGame.close(stage);
          if (inHostGame) {
            // hostLobby.start(stage);
          } else {
            // joinLobby.start(stage);
          }
        }
      }
    });
  }

  /**
   * Sets the ActionEvent to start a Tutorial Mode game.
   */
  public void setActionTut() {
    // tutLobby.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
    // public void handle(ActionEvent e) {
    // tutLobby.close(stage);
    // inGameTut.start(stage);
    // }

    // });


    // inGameTut.getLogout().setOnAction(new EventHandler<ActionEvent>() {
    // public void handle(ActionEvent e) {
    // inGameTut.close(stage);
    // tutLobby.start(stage);
    // }
    // });
  }

  /**
   * Starts the user interface by starting the start page first.
   * 
   * @param primaryStage the fist stage.
   */
  // public void start(Stage primaryStage) {
  // start.start(primaryStage);
  // }
}
