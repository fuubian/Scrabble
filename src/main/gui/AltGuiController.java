package main.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Collection;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.controller.ClientController;
import main.controller.DatabaseController;
import main.controller.ServerController;
import main.database.Profile;
import main.model.ComputerPlayer;
import main.model.Dictionary;
import main.model.Difficulty;
import main.model.GameException;
import main.model.GameState;
import main.model.HumanPlayer;
import main.model.Player;
import main.model.Scrabble;
import main.model.Tile;
import main.model.TileSet;

/**
 * An alternative GUI controller class.
 * 
 * @author raydin
 *
 */
public class AltGuiController extends Application {

  /**
   * The stage object of this class.
   */
  private Stage window = new Stage();

  /**
   * The StackPane object of this class.
   */
  public static StackPane root = new StackPane();

  /**
   * The object to call the alert boxes with.
   */
  private AlertBox ab = new AlertBox();

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
  private TutorialGameLobby tutLobby;

  /**
   * InGame object to display the InGame session from a network game.
   */
  private InGameNetwork inGameNet;

  /**
   * InGameTutorial object to display the InGame session from a tutorial game.
   */
  private InGameTutorial inGameTut;

  /**
   * The TileSet object to set the tales.
   */
  private TileSet tileSet;

  /**
   * Stage object to display the scene.
   */
  private Stage stage = new Stage();

  /**
   * The ServerController object.
   */
  public ServerController serverController = new ServerController();

  /**
   * AlertBox to alert the player when he presses the deleteProfile button.
   */
  private AlertBox deleteAlert = new AlertBox();

  /**
   * AlertBox to alert the player when he presses the door icon.
   */
  private AlertBox leaveAlert = new AlertBox();

  public ClientController clientController = new ClientController(this);

  private ErrorBox error = new ErrorBox();

  private PlayerOrderBox playerOrderBox;

  private TutorialScript tutorialScript;

  /**
   * Individual width of the screen the application is being displayed on.
   */
  private int width;

  /**
   * Individual height of the screen the application is being displayed on.
   */
  private int height;

  /**
   * The constructor of the GuiController class. Invokes the init method.
   * 
   * @param primaryStage the primary stage of this class.
   */
  public AltGuiController(Stage primaryStage) {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    width = gd.getDisplayMode().getWidth();
    height = gd.getDisplayMode().getHeight();
    stage = primaryStage;
    init();
  }

  /**
   * Returns the ServerController object of this class.
   * 
   * @return the ServerController object of this class.
   */
  public ServerController getServerController() {
    return serverController;
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
   * Initializes all relevant objects.
   */
  public void init() {
    start = new StartGui();
    login = new LoginPage();
    profile = new PlayerProfile();
    main = new MainMenu();
    hostLobby = new HostGameLobby(this.serverController);
    joinLobby = new JoinGameLobby(this.clientController);
    tutLobby = new TutorialGameLobby();
    inGameNet = new InGameNetwork();
    inGameTut = new InGameTutorial();
    start.getCloseButton().setOnAction(actionEvent -> closeApp());
    root = start.getStackPane();

    setStartActions();
    whenDeletedAccount();
  }

  /**
   * Determines, based on the user interaction, whether the application should be closed or not.
   */
  private void closeApp() {
    ab.displayAlert("Are you sure you want to close SCRABURU?");
    boolean answer = ab.getAnswer();

    if (answer) {
      if (serverController.isServerActive()) {

        serverController.closeServer();
      }
      if (clientController.isClientActive()) {

        clientController.disconnect();
      }
      stage.close();
    }
  }

  public void enterInGame(Scrabble scrabble) {
    // From join game lobby to in game session
    pane6 = (StackPane) root.getChildren().get(0);
    // Button ready = (Button) pane6.getChildren().get(4);
    // ready.setOnAction(actionEvent4 -> {
    root.getChildren().removeAll(root.getChildren());
    root.getChildren().add(inGameNet.getStackPane());
    root.getChildren().add(start.getCloseButton());
    start.getCloseButton().setTranslateX(width * 0.4688); // 900
    start.getCloseButton().setTranslateY(width * -0.2604); // -500
    // Scrabble game = new Scrabble(TileSet.get(Language.ENGLISH).create(), Dictionary.ENGLISH,
    // true);
    // game.addPlayer(new HumanPlayer(joinLobby.getPlayer1().getText()));
    inGameNet.setGame(scrabble);

    // From in game session back to join game lobby
    StackPane paneInPane4 = (StackPane) root.getChildren().get(0);
    GridPane pane2 = (GridPane) paneInPane4.getChildren().get(2);

    Button logout = (Button) pane2.getChildren().get(5);
    logout.setOnAction(actionEvent5 -> {
      root.getChildren().removeAll(root.getChildren());
      root.getChildren().add(joinLobby.getStackPane());
      // TODO insert alertbox that asks if they really want to close the game
      // and if it is allowed to leave the game at this state
      if (this.inGameNet.getGameController().getGame().getGameState() != GameState.GAME_OVER) {
        this.inGameNet.getGameController().getGame().stopGame();
      }
      this.clientController.sendBackToLobby();
      root.getChildren().add(start.getCloseButton());
      start.getCloseButton().setTranslateX(width * 0.3333); // 640
      start.getCloseButton().setTranslateY(width * -0.1563); // -300
      // });
    });

    this.clientController.setInGame(inGameNet);
    this.clientController.setGameController(inGameNet.getGameController());
  }

  public void backToMainMenu() {
    root.getChildren().removeAll(root.getChildren());
    root.getChildren().add(main.getStackPane());
    root.getChildren().add(start.getCloseButton());
    start.getCloseButton().setTranslateX(width * 0.1745); // 335
    start.getCloseButton().setTranslateY(width * -0.1484); // -285
    clientController.disconnect();
  }

  private static StackPane pane6;

  private static GridPane gp2;

  /**
   * Sets the ActionEvents for all pages.
   */
  public void setStartActions() {
    // From initial start page to login page
    start.getStartButton().setOnMouseClicked(mouseEvent -> {
      root.getChildren().removeAll(root.getChildren());
      root.getChildren().addAll(login.getStackPane());
      root.getChildren().add(start.getCloseButton());
      start.getCloseButton().setTranslateX(width * 0.1745); // 335
      start.getCloseButton().setTranslateY(width * -0.1484); // -285

      StackPane firstRoot = (StackPane) root.getChildren().get(0);
      Button back = (Button) firstRoot.getChildren().get(3);
      back.setDisable(true);

      // when user creates a new account
      GridPane em = (GridPane) firstRoot.getChildren().get(2);
      Button createAccountButton = (Button) em.getChildren().get(5);

      createAccountButton.setOnAction(firstEvent -> {
        back.setDisable(false);
        em.getChildren().removeAll(em.getChildren());

        GridPane.setConstraints(login.getHeadline(), 0, 0);
        GridPane.setConstraints(login.getUsername(), 0, 1);
        GridPane.setConstraints(login.getPassword(), 0, 2);
        GridPane.setConstraints(login.getRepeatPassword(), 0, 3);
        GridPane.setConstraints(login.getLine(), 0, 4);
        GridPane.setConstraints(login.getAccountCreated(), 0, 5);

        em.getChildren().addAll(login.getHeadline(), login.getUsername(), login.getPassword(),
            login.getRepeatPassword(), login.getLine(), login.getAccountCreated());

        TextField username = (TextField) em.getChildren().get(1);
        login.setUsername(username);

        PasswordField password = (PasswordField) em.getChildren().get(2);
        login.setPassword(password);

        PasswordField repeatPassword = (PasswordField) em.getChildren().get(3);
        login.setRepeatPassword(repeatPassword);

        Button accountCreatedButton = (Button) em.getChildren().get(5);

        accountCreatedButton.setOnAction(nextEvent -> {
          DatabaseController.createProfile(login);
          if (DatabaseController.login) {
            root.getChildren().removeAll(root.getChildren());
            root.getChildren().add(main.getStackPane());
            root.getChildren().add(start.getCloseButton());
            start.getCloseButton().setTranslateX(width * 0.1745); // 335
            start.getCloseButton().setTranslateY(width * -0.1484); // -285
            fromMainMenu();
          }
        });

        // Button back = (Button) firstRoot.getChildren().get(3);
        back.setOnAction(actionEvent -> {

          em.getChildren().removeAll(repeatPassword, accountCreatedButton);

          GridPane.setConstraints(username, 0, 1);
          GridPane.setConstraints(login.getLoginButton(), 0, 3);
          GridPane.setConstraints(login.getLine(), 0, 4);
          GridPane.setConstraints(createAccountButton, 0, 5);

          em.getChildren().addAll(login.getLoginButton(), createAccountButton);
          back.setDisable(true);

        });
      });

      // From login page to main menu.
      GridPane gp = (GridPane) firstRoot.getChildren().get(2);
      Button loginButton = (Button) gp.getChildren().get(3);
      loginButton.setOnAction(actionEvent -> {
        DatabaseController.login(login);
        if (DatabaseController.login) {
          root.getChildren().removeAll(root.getChildren());
          root.getChildren().add(main.getStackPane());
          root.getChildren().add(start.getCloseButton());
          start.getCloseButton().setTranslateX(width * 0.1745); // 335
          start.getCloseButton().setTranslateY(width * -0.1484); // -285
          fromMainMenu();
        }
      });

    });

  }

  private void whenDeletedAccount() {
    profile.getDeleteButton().setOnAction(ae -> {
      AlertBox alert = new AlertBox();
      alert.displayAlert("Are you sure you want to delete your profile?");
      if (alert.getAnswer()) {
        DatabaseController.deleteProfile();
        root.getChildren().removeAll(root.getChildren());
        root.getChildren().add(login.getStackPane());
        root.getChildren().add(start.getCloseButton());
        start.getCloseButton().setTranslateX(width * 0.1745); // 335
        start.getCloseButton().setTranslateY(width * -0.1484); // -285
      }
      StackPane firstRoot = (StackPane) root.getChildren().get(0);

      // when user creates a new account
      GridPane em = (GridPane) firstRoot.getChildren().get(2);
      Button createAccountButton = (Button) em.getChildren().get(5);

      createAccountButton.setOnAction(firstEvent -> {

        em.getChildren().removeAll(em.getChildren());

        GridPane.setConstraints(login.getHeadline(), 0, 0);
        GridPane.setConstraints(login.getUsername(), 0, 1);
        GridPane.setConstraints(login.getPassword(), 0, 2);
        GridPane.setConstraints(login.getRepeatPassword(), 0, 3);
        GridPane.setConstraints(login.getLine(), 0, 4);
        GridPane.setConstraints(login.getAccountCreated(), 0, 5);

        em.getChildren().addAll(login.getHeadline(), login.getUsername(), login.getPassword(),
            login.getRepeatPassword(), login.getLine(), login.getAccountCreated());

        TextField username = (TextField) em.getChildren().get(1);
        login.setUsername(username);

        PasswordField password = (PasswordField) em.getChildren().get(2);
        login.setPassword(password);

        PasswordField repeatPassword = (PasswordField) em.getChildren().get(3);
        login.setRepeatPassword(repeatPassword);

        Button accountCreatedButton = (Button) em.getChildren().get(5);

        accountCreatedButton.setOnAction(nextEvent -> {
          DatabaseController.createProfile(login);
          if (DatabaseController.login) {
            root.getChildren().removeAll(root.getChildren());
            root.getChildren().add(main.getStackPane());
            root.getChildren().add(start.getCloseButton());
            start.getCloseButton().setTranslateX(width * 0.1745); // 335
            start.getCloseButton().setTranslateY(width * -0.1484); // -285
            fromMainMenu();
          }
        });
      });

      // From login page to main menu.
      GridPane gp = (GridPane) firstRoot.getChildren().get(2);
      Button loginButton = (Button) gp.getChildren().get(3);
      loginButton.setOnAction(actionEvent -> {
        DatabaseController.login(login);
        if (DatabaseController.login) {
          root.getChildren().removeAll(root.getChildren());
          root.getChildren().add(main.getStackPane());
          root.getChildren().add(start.getCloseButton());
          start.getCloseButton().setTranslateX(width * 0.1745); // 335
          start.getCloseButton().setTranslateY(width * -0.1484); // -285
          fromMainMenu();
        }
      });
    });


  }

  private void fromMainMenu() {
    // From main menu to player profile
    StackPane pane14 = (StackPane) root.getChildren().get(0);
    Button playerProfile = (Button) pane14.getChildren().get(3);
    playerProfile.setOnAction(actionEvent3 -> {

      profile.setUn(Profile.getName());
      profile.setPw(DatabaseController.getPassword(Profile.getName()));
      root.getChildren().removeAll(root.getChildren());
      root.getChildren().add(profile.getStackPane());
      root.getChildren().add(start.getCloseButton());
      start.getCloseButton().setTranslateX(width * 0.1745); // 335
      start.getCloseButton().setTranslateY(width * -0.1484); // -285
      Button back = profile.getBackButton();
      root.getChildren().add(back);


      StackPane newOne = (StackPane) root.getChildren().get(0);
      GridPane statPane = (GridPane) newOne.getChildren().get(2);

      back.setOnAction(actionEvent4 -> {
        if (profile.getFlag()) {
          statPane.getChildren().removeAll(profile.getHeadline2(), profile.getTable());

          GridPane.setConstraints(profile.getHeadline1(), 0, 0);
          GridPane.setConstraints(profile.getStackUn(), 0, 1);
          GridPane.setConstraints(profile.getStackPw(), 0, 2);
          GridPane.setConstraints(profile.getStatistics(), 0, 3);
          GridPane.setConstraints(profile.getLine(), 0, 4);
          GridPane.setConstraints(profile.getDeleteButton(), 0, 5);

          statPane.getChildren().add(profile.getHeadline1());
          statPane.getChildren().add(profile.getStackUn());
          statPane.getChildren().add(profile.getStackPw());
          statPane.getChildren().add(profile.getStatistics());
          statPane.getChildren().add(profile.getLine());
          statPane.getChildren().add(profile.getDeleteButton());

          profile.setFlag(false);
        } else {
          root.getChildren().removeAll(root.getChildren());
          root.getChildren().add(main.getStackPane());
          root.getChildren().add(start.getCloseButton());
          start.getCloseButton().setTranslateX(width * 0.1745); // 335
          start.getCloseButton().setTranslateY(width * -0.1484); // -285
        }
      });
    });

    // From main menu to host game lobby
    StackPane pane = (StackPane) root.getChildren().get(0);
    // GridPane gp2 = (GridPane) pane.getChildren().get(2);
    gp2 = (GridPane) pane.getChildren().get(2);
    Button hostGame = (Button) gp2.getChildren().get(2);
    hostGame.setOnAction(actionEvent2 -> {
      serverController.setHostGame(hostLobby);
      hostLobby.getLobbyName().setText("Lobby of " + Profile.getName());
      root.getChildren().removeAll(root.getChildren());
      root.getChildren().add(hostLobby.getStackPane());
      root.getChildren().add(start.getCloseButton());
      root.getChildren().add(main.getHelpButton());
      main.getHelpButton().setTranslateX(width * 0.2865); // 550
      main.getHelpButton().setTranslateY(width * -0.1615); // -310
      start.getCloseButton().setTranslateX(width * 0.3333); // 640
      start.getCloseButton().setTranslateY(width * -0.1615); // -310

      tutorialScript = new TutorialScript(ScriptType.HOST_LOBBY);
      main.getHelpButton().setOnAction(actionEvent -> {
        try {
          tutorialScript.start(window);
        } catch (Exception e) {
          e.printStackTrace();
        }
      });

      serverController.openServer();

      ArrayList<Player> joinedPlayers = new ArrayList<Player>();

      if (hostLobby.getDictChoice() != null) {
        this.tileSet = TileSet.getStandard();
      }

      hostLobby.getAmountChoice().setOnAction(ae -> {
        Character letter = hostLobby.getLetterChoice().getValue();
        Integer amount = hostLobby.getAmountChoice().getValue();
        if (letter != null) {
          this.tileSet.setCount(letter, amount);
        }
      });

      hostLobby.getValueChoice().setOnAction(ae2 -> {
        Character letter = hostLobby.getLetterChoice().getValue();
        Integer value = hostLobby.getValueChoice().getValue();
        if (letter != null) {
          this.tileSet.setScore(letter, value);
        }
      });

      // count = amount, value = score

      hostLobby.getLetterChoice().setOnAction(ae3 -> {

        Character letter = hostLobby.getLetterChoice().getValue();
        hostLobby.getAmountChoice().setValue(this.tileSet.getCount(letter));
        hostLobby.getValueChoice().setValue(this.tileSet.getScore(letter));
      });

      hostLobby.getDictChoice().setOnAction(ae0 -> {
        Character letter = hostLobby.getLetterChoice().getValue();
        this.tileSet = TileSet.getStandard();
        if (letter != null) {
          hostLobby.getAmountChoice().setValue(this.tileSet.getCount(letter)); // amount =
                                                                               // count
          hostLobby.getValueChoice().setValue(this.tileSet.getScore(letter)); // value = score
        }
      });


      // From host game lobby to in game session
      StackPane paneInPane2 = (StackPane) root.getChildren().get(0);
      Button startGameButton = (Button) paneInPane2.getChildren().get(7);

      startGameButton.setOnAction(actionEvent3 -> {
        Label[] easyBotLabels = {hostLobby.getEasyBotLabel1(), hostLobby.getEasyBotLabel2(),
            hostLobby.getEasyBotLabel3()};
        Label[] hardBotLabels = {hostLobby.getHardBotLabel1(), hostLobby.getHardBotLabel2(),
            hostLobby.getHardBotLabel3()};

        joinedPlayers.clear();

        if (serverController.isReady()) {
          // TODO
          Collection<Tile> tiles = this.tileSet.create();
          String filename = hostLobby.getDictChoice().getValue();
          Dictionary dict = Dictionary.readFile(filename);
          Scrabble game = new Scrabble(tiles, dict, true);
          // try {
          // game.addPlayer(new HumanPlayer(hostLobby.getPlayer1().getText()));
          joinedPlayers.add(new HumanPlayer(hostLobby.getPlayer1().getText()));
          // } catch (GameException e) {
          // error.displayError(e.getMessage());
          // }

          // Label[] easyBotLabels = {hostLobby.getEasyBotLabel1(), hostLobby.getEasyBotLabel2(),
          // hostLobby.getEasyBotLabel3()};
          // Label[] hardBotLabels = {hostLobby.getHardBotLabel1(), hostLobby.getHardBotLabel2(),
          // hostLobby.getHardBotLabel3()};

          for (int i = 0; i < easyBotLabels.length; i++) {
            if (!easyBotLabels[i].getText().isEmpty()) {
              // try {
              // game.addPlayer(new ComputerPlayer("Easy Bot Nr. " + (i + 1), Difficulty.EASY));
              joinedPlayers.add(new ComputerPlayer("Easy Bot Nr. " + (i + 1), Difficulty.EASY));
              // } catch (GameException e) {
              // error.displayError(e.getMessage());
              // }
            }
            if (!hardBotLabels[i].getText().isEmpty()) {
              // try {
              // game.addPlayer(new ComputerPlayer("Hard Bot Nr. " + (i + 1), Difficulty.HARD));
              joinedPlayers.add(new ComputerPlayer("Hard Bot Nr. " + (i + 1), Difficulty.HARD));

              // } catch (GameException e) {
              // error.displayError(e.getMessage());
              // }
            }
          }


          if (!hostLobby.getPlayer2().getText().isEmpty()) {
            // try {
            // game.addPlayer(new HumanPlayer(hostLobby.getPlayer2().getText()));
            joinedPlayers.add(new HumanPlayer(hostLobby.getPlayer2().getText()));

            // } catch (GameException e) {
            // error.displayError(e.getMessage());
            // }
          }
          if (!hostLobby.getPlayer3().getText().isEmpty()) {
            // try {
            // game.addPlayer(new HumanPlayer(hostLobby.getPlayer3().getText()));
            joinedPlayers.add(new HumanPlayer(hostLobby.getPlayer3().getText()));

            // } catch (GameException e) {
            // error.displayError(e.getMessage());
            // }
          }
          if (!hostLobby.getPlayer4().getText().isEmpty()) {
            // try {
            // game.addPlayer(new HumanPlayer(hostLobby.getPlayer4().getText()));
            joinedPlayers.add(new HumanPlayer(hostLobby.getPlayer4().getText()));

            // } catch (GameException e) {
            // error.displayError(e.getMessage());
            // }
          }

          // playerOrderBox = new PlayerOrderBox(serverController.getPlayers());

          playerOrderBox = new PlayerOrderBox(joinedPlayers.size());
          // for (int i = 0; i < game.getPlayerCount(); i++) {
          for (int i = 0; i < joinedPlayers.size(); i++) {
            // playerOrderBox.setPlayerName(game.getPlayer(i).getName());
            playerOrderBox.setPlayerName(joinedPlayers.get(i).getName());
          }

          playerOrderBox.displayAlert("Set the order of players that are going to play.");

          for (int i = 0; i < playerOrderBox.getAddedNames().length; i++) {
            try {
              if (playerOrderBox.getAddedNames()[i].startsWith("Easy Bot Nr.")) {

                game.addPlayer(
                    new ComputerPlayer(playerOrderBox.getAddedNames()[i], Difficulty.EASY));

              } else if (playerOrderBox.getAddedNames()[i].startsWith("Hard Bot Nr.")) {

                game.addPlayer(
                    new ComputerPlayer(playerOrderBox.getAddedNames()[i], Difficulty.HARD));

              } else {
                game.addPlayer(new HumanPlayer(playerOrderBox.getAddedNames()[i]));
              }
            } catch (GameException e) {
              error.displayError(e.getMessage());
            }
          }
          System.out.println(joinedPlayers);
          try {
            serverController.startGame(game);
            game.startGame();
            inGameNet.setGame(game);
            serverController.sendStartMessage(game);

            root.getChildren().removeAll(root.getChildren());
            root.getChildren().add(inGameNet.getStackPane());
            root.getChildren().add(start.getCloseButton());
            start.getCloseButton().setTranslateX(width * 0.4688); // 900
            start.getCloseButton().setTranslateY(width * -0.2604); // -500


            // From in game session back to host game lobby
            StackPane paneInPane3 = (StackPane) root.getChildren().get(0);
            GridPane pane12 = (GridPane) paneInPane3.getChildren().get(2);
            Button logout = (Button) pane12.getChildren().get(5);
            logout.setOnAction(actionEvent4 -> {
              root.getChildren().removeAll(root.getChildren());
              root.getChildren().add(hostLobby.getStackPane());
              // TODO insert alertbox that asks if they really want to close the game
              // and if it is allowed to leave the game at this state
              if (this.inGameNet.getGameController().getGame()
                  .getGameState() != GameState.GAME_OVER) {
                this.inGameNet.getGameController().getGame().stopGame();
              }
              this.serverController.sendBackToLobby();
              this.serverController.updateLobbyAfterGame();
              root.getChildren().add(start.getCloseButton());
              start.getCloseButton().setTranslateX(width * 0.3333); // 640
              start.getCloseButton().setTranslateY(width * -0.1563); // -300
              playerOrderBox.reverse();
              joinedPlayers.clear();
              System.out.println(joinedPlayers);
            });

          } catch (GameException e) {
            error.displayError(e.getMessage());
          }
          this.serverController.setInGame(inGameNet);
          this.serverController.setGameController(inGameNet.getGameController());
        } else {
          error.displayError("Not all players are ready.");
        }

      });

      // From host game lobby back to main menu
      StackPane rootPane = (StackPane) root.getChildren().get(0);
      Button logoutButton = (Button) rootPane.getChildren().get(3);
      logoutButton.setOnAction(actionEvent3 -> {
        root.getChildren().removeAll(root.getChildren());
        root.getChildren().add(main.getStackPane());
        root.getChildren().add(start.getCloseButton());
        start.getCloseButton().setTranslateX(width * 0.1745); // 335
        start.getCloseButton().setTranslateY(width * -0.1484); // -285
        serverController.closeServer();
        hostLobby.init(hostLobby.getStage());
        hostLobby.getLobbyName().setText("Lobby of " + Profile.getName());

      });
    });

    // From main menu to join game lobby
    GridPane newgp2 = (GridPane) pane.getChildren().get(2);
    Button joinGame = (Button) gp2.getChildren().get(3);
    System.out.println(joinGame);
    joinGame.setOnAction(actionEvent6 -> {

      newgp2.getChildren().removeAll(newgp2.getChildren());

      newgp2.setPadding(new Insets(0.2, 0.2, 0, 0));
      newgp2.setVgap(15);
      Label headline = main.getHeadline();
      ComboBox<String> availableIp = main.getAvailableIp();
      TextField ip = main.getIp();
      Button joinedGame = main.getJoinedButton();
      Rectangle line = main.getLine();
      GridPane.setConstraints(headline, 0, 0);
      GridPane.setConstraints(line, 0, 1);
      GridPane.setConstraints(availableIp, 0, 2);
      GridPane.setConstraints(ip, 0, 3);
      GridPane.setConstraints(joinedGame, 0, 4);
      clientController.setMenu(main);
      clientController.refreshServer();

      newgp2.getChildren().addAll(headline, line, availableIp, ip, joinedGame);
      headline.setTranslateY(-40);
      newgp2.getChildren().add(main.getBackButton());
      main.getBackButton().setTranslateX(width * -0.0365); // -70
      main.getBackButton().setTranslateY(width * -0.0286); // -55

      Button back = (Button) newgp2.getChildren().get(5);
      back.setOnAction(actEvent -> {
        newgp2.getChildren().removeAll(newgp2.getChildren());
        GridPane.setConstraints(main.getHeadline(), 0, 0);
        GridPane.setConstraints(main.getLine(), 0, 1);
        GridPane.setConstraints(main.getHostButton(), 0, 2);
        GridPane.setConstraints(main.getJoinButton(), 0, 3);
        GridPane.setConstraints(main.getTutorialButton(), 0, 4);

        newgp2.getChildren().addAll(main.getHeadline(), main.getLine(), main.getHostButton(),
            main.getJoinButton(), main.getTutorialButton());
      });

      Button joined = (Button) newgp2.getChildren().get(4);
      joined.setOnAction(aeve -> {
        root.getChildren().removeAll(root.getChildren());
        root.getChildren().add(joinLobby.getStackPane());
        root.getChildren().add(start.getCloseButton());
        start.getCloseButton().setTranslateX(width * 0.3333); // 640
        start.getCloseButton().setTranslateY(width * -0.15625); // -300

        clientController.connect(main);
        clientController.setJoinLobby(joinLobby);

        // From join game lobby back to main menu
        StackPane rootPane = (StackPane) root.getChildren().get(0);

        Button logoutButton = (Button) rootPane.getChildren().get(3);
        logoutButton.setOnAction(actionEvent3 -> {
          root.getChildren().removeAll(root.getChildren());
          root.getChildren().add(main.getStackPane());
          root.getChildren().add(start.getCloseButton());
          start.getCloseButton().setTranslateX(width * 0.1745); // 335
          start.getCloseButton().setTranslateY(width * -0.1484); // -285
          clientController.disconnect();
        });

      });
    });

    // From main menu to tutorial game lobby
    Button tutorialGame = (Button) gp2.getChildren().get(4);
    tutorialGame.setOnAction(actionEvent2 -> {
      tutLobby.getPlayerName().setText(Profile.getName());
      root.getChildren().removeAll(root.getChildren());
      root.getChildren().add(tutLobby.getStackPane());
      root.getChildren().add(start.getCloseButton());
      start.getCloseButton().setTranslateX(width * 0.3333); // 640
      start.getCloseButton().setTranslateY(width * -0.15625); // -300

      if (tutLobby.getDictChoice() != null) {
        this.tileSet = TileSet.getStandard();
      }

      tutLobby.getAmountChoice().setOnAction(ae -> {
        Character letter = tutLobby.getLetterChoice().getValue();
        Integer amount = tutLobby.getAmountChoice().getValue();
        if (letter != null) {
          this.tileSet.setCount(letter, amount);
        }
      });

      tutLobby.getValueChoice().setOnAction(ae2 -> {
        Character letter = tutLobby.getLetterChoice().getValue();
        Integer value = tutLobby.getValueChoice().getValue();
        if (letter != null) {
          this.tileSet.setScore(letter, value);
        }
      });

      tutLobby.getLetterChoice().setOnAction(ae3 -> {

        Character letter = tutLobby.getLetterChoice().getValue();
        tutLobby.getAmountChoice().setValue(this.tileSet.getCount(letter));
        tutLobby.getValueChoice().setValue(this.tileSet.getScore(letter));
      });

      tutLobby.getDictChoice().setOnAction(ae0 -> {
        Character letter = tutLobby.getLetterChoice().getValue();
        if (letter != null) {
          tutLobby.getAmountChoice().setValue(this.tileSet.getCount(letter)); // amount =
                                                                              // count
          tutLobby.getValueChoice().setValue(this.tileSet.getScore(letter)); // value = score
        }
      });

      // From tutorial game lobby back to main menu
      StackPane rootPane = (StackPane) root.getChildren().get(0);
      GridPane testPane = (GridPane) rootPane.getChildren().get(8);
      GridPane gridInGrid = (GridPane) testPane.getChildren().get(1);
      Button leaveGame = (Button) gridInGrid.getChildren().get(4);
      leaveGame.setOnAction(actionEvent4 -> {

        Label[] easyBotLabels = tutLobby.getEasyBotLabels();
        Label[] hardBotLabels = tutLobby.getHardBotLabels();

        for (int i = 0; i < easyBotLabels.length; i++) {
          if (testPane.getChildren().contains(easyBotLabels[i])) {
            tutLobby.removeBots(i);
          }
          if (testPane.getChildren().contains(hardBotLabels[i])) {
            tutLobby.removeBots(i);
          }
        }

        root.getChildren().removeAll(root.getChildren());
        root.getChildren().add(main.getStackPane());
        root.getChildren().add(start.getCloseButton());
        start.getCloseButton().setTranslateX(width * 0.1745); // 335
        start.getCloseButton().setTranslateY(width * -0.1484); // -285

      });

      // From tutorial game lobby to tutorial in game session.
      StackPane pane13 = (StackPane) root.getChildren().get(0);
      GridPane grid = (GridPane) pane13.getChildren().get(8);
      GridPane gridInGrid2 = (GridPane) grid.getChildren().get(0);
      Button startGame = (Button) gridInGrid2.getChildren().get(7);
      startGame.setOnAction(actionEvent3 -> {


        Collection<Tile> tiles = this.tileSet.create();
        String filename = tutLobby.getDictChoice().getValue();
        Dictionary dict = Dictionary.readFile(filename);
        Scrabble game = new Scrabble(tiles, dict, false);
        try {
          game.addPlayer(new HumanPlayer(tutLobby.getPlayerName().getText()));
        } catch (GameException e) {
          error.displayError(e.getMessage());
        }

        Label[] easyBotLabels = tutLobby.getEasyBotLabels();
        Label[] hardBotLabels = tutLobby.getHardBotLabels();

        for (int i = 0; i < easyBotLabels.length; i++) {
          if (!easyBotLabels[i].getText().isEmpty()) {
            try {
              game.addPlayer(new ComputerPlayer("Easy Bot Nr. " + (i + 1), Difficulty.EASY));
            } catch (GameException e) {
              error.displayError(e.getMessage());
            }
          }
          if (!hardBotLabels[i].getText().isEmpty()) {
            try {
              game.addPlayer(new ComputerPlayer("Hard Bot Nr. " + (i + 1), Difficulty.HARD));
            } catch (GameException e) {
              error.displayError(e.getMessage());
            }
          }
        }

        try {
          game.startGame();
          inGameTut.setGame(game);
          root.getChildren().removeAll(root.getChildren());
          root.getChildren().add(inGameTut.getStackPane());
          root.getChildren().add(start.getCloseButton());
          start.getCloseButton().setTranslateX(width * 0.4688); // 900
          start.getCloseButton().setTranslateY(width * -0.2604); // -500
        } catch (GameException e) {
          error.displayError(e.getMessage());
        }


        // From in game session back to tutorial game lobby
        StackPane paneInPane4 = (StackPane) root.getChildren().get(0);
        GridPane pane2 = (GridPane) paneInPane4.getChildren().get(2);
        Button logout = (Button) pane2.getChildren().get(4);
        logout.setOnAction(actionEvent5 -> {
          AlertBox box = new AlertBox();
          box.displayAlert("Are you sure you want to leave the game?\nAll progress will be lost.");
          if (box.getAnswer()) {
            game.stopGame();
            root.getChildren().removeAll(root.getChildren());
            root.getChildren().add(tutLobby.getStackPane());
            root.getChildren().add(start.getCloseButton());
            start.getCloseButton().setTranslateX(width * 0.3333); // 640
            start.getCloseButton().setTranslateY(width * -0.15625); // -300
          }
        });
      });
    });
  }


  /**
   * Sets the new lobby name if the changeLobbyName method is called.
   * 
   * @param newName the new lobby name if the changeLobbyName method is called.
   */
  public void setNewLobbyName(String newName) {
    this.hostLobby.getLobbyName().setText(newName);
  }

  /**
   * Starts the application.
   * 
   * @param primaryStage a stage to be set.
   */
  @Override
  public void start(Stage primaryStage) {
    stage.setOnCloseRequest(actionEvent -> {
      actionEvent.consume();
      closeApp();
    });

    // GraphicsDevice gd =
    // GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    // int width = gd.getDisplayMode().getWidth();
    // int height = gd.getDisplayMode().getHeight();

    Scene scene = new Scene(root, width, height);
    stage.setScene(scene);
    stage.show();
  }
}

