package main.controller;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import javafx.application.Platform;
import main.database.NetworkStatistics;
import main.database.Profile;
import main.gui.AltGuiController;
import main.gui.ErrorBox;
import main.gui.InGameNetwork;
import main.gui.JoinGameLobby;
import main.gui.MainMenu;
import main.model.GameException;
import main.model.GameState;
import main.model.Scrabble;
import main.model.TurnState;
import main.network.BroadcastSender;
import main.network.ClientProtocol;
import main.network.message.ChatMessage;
import main.network.message.LeaveGameMessage;
import main.network.message.LobbyDetailsMessage;
import main.network.message.Message;
import main.network.message.MessageType;
import main.network.message.ReadyMessage;
import main.network.message.UpdateGameMessage;

/**
 * Controller for the Client.
 * 
 * @author lknothe
 *
 */

public class ClientController extends NetworkGameController {

  /**
   * Client object.
   */

  private ClientProtocol cp;

  /**
   * JoinGameLobby object.
   */

  private JoinGameLobby joinLobby;



  /**
   * Flag that shows if the client is connected.
   */

  private boolean connected = false;


  /**
   * Kicked flag.
   */

  private boolean kicked = false;

  /**
   * Flag if server active.
   */

  private boolean clientActive = false;

  /**
   * Main menu object.
   */

  private MainMenu menu;


  /**
   * AltGuiController object.
   */

  private AltGuiController gui;

  /**
   * ClientController constructor.
   * 
   * @param agc AltGuiController object.
   */

  public ClientController(AltGuiController agc) {
    this.gui = agc;
  }

  /**
   * Connects a client to a server.
   * 
   * @param mm MainMenu object
   */

  public void connect(MainMenu mm) {
    this.menu = mm;
    try {
      this.cp = new ClientProtocol(mm.getIp().getText(), this);
      this.cp.start();

      Thread.sleep(500);

      if (cp.getAccepted()) {
        connected = true;
        clientActive = true;
      } else {
        mm.getErrorBox().displayError("Connection refused by host.");
      }
    } catch (IOException e) {
      mm.getErrorBox().displayError("Server couldn't be found.");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Disconnect.
   */

  public void disconnect() {
    clientActive = false;
    if(this.gameControl != null && this.gameControl.getGame().getGameState() == GameState.PLAY) {
      this.sendBackToLobby();
    }
    cp.disconnect();
    Profile.setOnlineName(Profile.getName());
  }

  /**
   * Method if the connection gets lost.
   * 
   * @param reason String object
   */

  public void lostConnection(String reason) {
    kicked = true;
    clientActive = false;
    Platform.runLater(() -> {
      joinLobby.getError().displayError(reason);
      gui.backToMainMenu();
    });
  }

  /**
   * Updates the field of available IP addresses in the GUI.
   */
  public void refreshServer() {
    menu.resetAvailableIp();
    BroadcastSender sender;
    try {
      sender = new BroadcastSender();

      sender.searchHosts();
      sender.start();

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      sender.closeSockets();
      ArrayList<String> addresses = sender.getAddressList();

      for (String address : addresses) {
        this.menu.setAvailableIp(address);
      }
    } catch (SocketException e1) {
    }

  }

  /**
   * Updates the lobby.
   * 
   * @param lobbyDet LobbyDetailsMessage object
   */

  public void updateLobby(LobbyDetailsMessage lobbyDet) {
    Platform.runLater(() -> {
      joinLobby.getPlayer1().setText(lobbyDet.getNames()[0]);
      joinLobby.getPlayer2().setText(lobbyDet.getNames()[1]);
      joinLobby.getPlayer3().setText(lobbyDet.getNames()[2]);
      joinLobby.getPlayer4().setText(lobbyDet.getNames()[3]);

      joinLobby.getLobbyName().setText(lobbyDet.getLobbyName());
      joinLobby.setConnectionDetails(lobbyDet.getConDet());

      String[][] stats = lobbyDet.getStats();
      for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
          joinLobby.setValue(i, j, stats[i][j]);
        }
      }

      if (lobbyDet.getReady()[0]) {
        joinLobby.colorReadyPlayers(lobbyDet.getNames()[1]);
      } else {
        joinLobby.colorNotReadyPlayers(lobbyDet.getNames()[1]);
      }

      if (lobbyDet.getReady()[1]) {
        joinLobby.colorReadyPlayers(lobbyDet.getNames()[2]);
      } else {
        joinLobby.colorNotReadyPlayers(lobbyDet.getNames()[2]);
      }

      if (lobbyDet.getReady()[2]) {
        joinLobby.colorReadyPlayers(lobbyDet.getNames()[3]);
      } else {
        joinLobby.colorNotReadyPlayers(lobbyDet.getNames()[3]);
      }
      
      if (lobbyDet.getGamePlayers() != null) {
        for (int i = 0; i < lobbyDet.getGamePlayers().length; i++) {
          this.joinLobby.setPlayerName(i, lobbyDet.getGamePlayers()[i]);
        }
        this.joinLobby.setStatName1("Score");
        this.joinLobby.setStatName2("Highscore");
        this.joinLobby.setStatName3("Avg. Score");
        this.joinLobby.setStatName4("Avg. Turn Time");
      } else {
        for (int i = 0; i < lobbyDet.getNames().length; i++) {
          this.joinLobby.setPlayerName(i, lobbyDet.getNames()[i]);
        }
      }

    });
  }

  /**
   * Receives chat messages.
   * 
   * @param cm ChatMessage object
   */

  public void receiveChatLobby(ChatMessage cm) {
    Platform.runLater(() -> {
      joinLobby.msgIsFrom(cm.getFrom());
      joinLobby.setChatMessage(cm.getFrom(), cm.getText(), true);
    });
  }

  /**
   * Sends chat messages.
   * 
   * @param s String for the message.
   */

  public void sendChatLobby(String s) {
    cp.sendChat(s, "lobby");

    Platform.runLater(() -> {
      joinLobby.msgIsFrom(Profile.getOnlineName());
      joinLobby.setChatMessage(Profile.getOnlineName(), s, false);
    });
  }

  /**
   * Receives a chat message in game.
   * 
   * @param cm ChatMessage object
   */

  /**
   * sends chat message in game.
   * 
   * @param text String object
   */

  public void sendChatGame(String text) {
    cp.sendChat(text, "game");
    ((NetworkStatistics) Profile.getNetworkStats()).increaseMessagesSent();
    Profile.getNetworkStats().updateStats();
  }

  /**
   * Informs all players that the game is starting.
   * 
   * @param hostName name of the host
   */

  public void informPlayers(String hostName) {
    Platform.runLater(() -> {
      joinLobby.msgIsFrom(hostName);
      joinLobby.setChatMessage(hostName, "Game is starting now.", false);
    });
  }

  /**
   * Starts the game.
   */

  public void startGame(Scrabble scrabble) {
    Platform.runLater(() -> {
      gui.enterInGame(scrabble);
    });
  }

  /**
   * This method starts the game after the game object was sent to every player.
   * 
   * @param game start state of the game
   */

  public void startGame(TurnState game) {
    while (this.gameControl == null) {}
    while (this.gameControl.getGame() == null) {}
    try {
      this.gameControl.getGame().startGame();
      this.gameControl.getGame().setTurnState(game);
      Platform.runLater(() -> this.gameControl.updateAll());
    } catch (GameException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sends the new move to all players.
   * 
   * @param game current state of the game
   */
  public void sendMove(TurnState game) {
    Message mgMove = new UpdateGameMessage(MessageType.UPDATEGAME, Profile.getOnlineName(), game);
    try {
      this.cp.sendMessage(mgMove);
    } catch (IOException e) {
      ErrorBox error = new ErrorBox();
      error.displayError("Connection error!");
    }
  }

  /**
   * This method is called if the client leaves a running game.
   * The game will be closed for all players.
   */
  public void sendBackToLobby() {
    StatisticController.updateStats(this.gameControl.getGame(), true);
    if (this.gameControl.getGame().getGameState() != GameState.GAME_OVER) {
      this.gameControl.getGame().stopGame();
      Message msg = new LeaveGameMessage(MessageType.LEAVEGAME, Profile.getOnlineName());
      try {
        this.cp.sendMessage(msg);
      } catch (IOException e) {
        Platform.runLater(() -> {
          ErrorBox error = new ErrorBox();
          error.displayError("Connection error.");
        });
      }
    }
  }

  /**
   * Sets client ready.
   */

  public void setReady() {
    Message readyMg = new ReadyMessage(MessageType.READY, Profile.getOnlineName());
    try {
      cp.sendMessage(readyMg);
    } catch (IOException e) {
      this.joinLobby.getError().displayError("Connection error.");
    }
  }

  /**
   * Returns JoinGameLobby object.
   * 
   * @return JoinGameLobby object
   */

  public JoinGameLobby getJoinLobby() {
    return joinLobby;
  }

  /**
   * Sets the JoinGameLobby.
   * 
   * @param joinLobby JoinGameLobby object
   */

  public void setJoinLobby(JoinGameLobby joinLobby) {
    this.joinLobby = joinLobby;
  }

  /**
   * Returns if the server is connected or not.
   * 
   * @return attribute connected
   */
  public boolean isConnected() {
    return connected;
  }

  /**
   * Sets InGame gui.
   * 
   * @param InGameNetwork object
   */

  public void setInGame(InGameNetwork inGame) {
    this.inGame = inGame;
    this.inGame.setController(this);
  }

  /**
   * Returns if kicked.
   * 
   * @return boolean kicked
   */
  public boolean isKicked() {
    return kicked;
  }

  /**
   * Returns if client active.
   * 
   * @return cliebtActive boolean
   */

  public boolean isClientActive() {
    return clientActive;
  }

  /**
   * Set the MainMenu.
   * 
   * @param menu MainMenu object
   */

  public void setMenu(MainMenu menu) {
    this.menu = menu;
  }

  public void setGameController(GameController gameControl) {
    this.gameControl = gameControl;
    this.gameControl.setNetwork(this, false);
  }
}
