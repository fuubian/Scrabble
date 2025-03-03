package main.controller;

import javafx.application.Platform;
import main.database.Profile;
import main.gui.HostGameLobby;
import main.gui.InGameNetwork;
import main.model.GameState;
import main.model.Scrabble;
import main.model.TurnState;
import main.network.Server;
import main.network.ServerSettings;
import main.network.message.BlockSocketMessage;
import main.network.message.ChatMessage;
import main.network.message.DisconnectMessage;
import main.network.message.InitializeMessage;
import main.network.message.LeaveGameMessage;
import main.network.message.LobbyDetailsMessage;
import main.network.message.Message;
import main.network.message.MessageType;
import main.network.message.StartGameMessage;
import main.network.message.UpdateGameMessage;

/**
 * Controller for the Server.
 * 
 * @author lknothe
 *
 */

public class ServerController extends NetworkGameController {

  /**
   * Server object.
   */

  private Server server;

  /**
   * HostGameLobby object.
   */

  private HostGameLobby hostGame;

  /**
   * Flag if server active.
   */

  private boolean serverActive = false;

  /**
   * boolean array if ready.
   */
  
  private boolean[] ready;

  /**
   * Indicates if a game was already played.
   */
  
  private boolean newLobby;
  
  /**
   * Players who took place in the last game.
   */
  
  private String[] gamePlayer;


  /**
   * Opens the server and resets the lobby.
   */

  public void openServer() {
    this.server = new Server(this);
    this.server.start();
    serverActive = true;
    hostGame.getPlayer2().setText("");
    hostGame.getPlayer3().setText("");
    hostGame.getPlayer4().setText("");
    this.hostGame.setStatName1("Games Played");
    this.hostGame.setStatName2("Wins");
    this.hostGame.setStatName3("Win %");
    this.hostGame.setStatName4("Last Place");
    for (int i = 1; i < 4; i++) {
      this.hostGame.setPlayerName(i, "");
    }
    this.newLobby = true;
    this.ready = new boolean[3];
    for (int i = 0; i < this.ready.length; i++) {
      this.ready[i] = true;
    }
  }

  /**
   * Closes the server.
   */

  public void closeServer() {
    if (this.gameControl != null && this.gameControl.getGame()!= null && 
        this.gameControl.getGame().getGameState() == GameState.PLAY) {
      Message msg = new LeaveGameMessage(MessageType.LEAVEGAME, Profile.getOnlineName());
      this.server.sendToAll(msg);
    }
    serverActive = false;

    if (!hostGame.getEasyBotLabel1().getText().isEmpty()
        || !hostGame.getHardBotLabel1().getText().isEmpty()) {

      hostGame.removeBots(0);
    } else if (!hostGame.getPlayer2().getText().isEmpty()) {
      removePlayer(hostGame.getPlayer2().getText());
    }

    if (!hostGame.getEasyBotLabel2().getText().isEmpty()
        || !hostGame.getHardBotLabel2().getText().isEmpty()) {

      hostGame.removeBots(1);
    } else if (!hostGame.getPlayer3().getText().isEmpty()) {
      removePlayer(hostGame.getPlayer3().getText());
    }

    if (!hostGame.getEasyBotLabel3().getText().isEmpty()
        || !hostGame.getHardBotLabel3().getText().isEmpty()) {

      hostGame.removeBots(2);
    } else if (!hostGame.getPlayer4().getText().isEmpty()) {
      removePlayer(hostGame.getPlayer4().getText());
    }

    this.server.closeServer();
  }

  /**
   * Adds a Player to the GUI of the lobby.
   * 
   * @param name String object, name of player
   */

  public void addPlayer(String name, String[] stats) {
    if (hostGame.getPlayer2().getText().isEmpty() && hostGame.getEasyBotLabel1().getText().isEmpty()
        && hostGame.getHardBotLabel1().getText().isEmpty()) {
      Platform.runLater(() -> {
        hostGame.getPlayer2().setText(name);
        hostGame.addKickButton(0);
        hostGame.removeBotButtons(0);
        ready[0] = false;
        if (this.newLobby) {
          this.hostGame.setPlayerName(1, name);
          for (int i = 0; i < 4; i++) {
            hostGame.setStatValue(1, i, stats[i]);
          }
        }
      });
    } else if (hostGame.getPlayer3().getText().isEmpty()
        && hostGame.getEasyBotLabel2().getText().isEmpty()
        && hostGame.getHardBotLabel2().getText().isEmpty()) {

      Platform.runLater(() -> {
        hostGame.getPlayer3().setText(name);
        hostGame.addKickButton(1);
        hostGame.removeBotButtons(1);
        ready[1] = false;
        if (this.newLobby) {
          this.hostGame.setPlayerName(2, name);
          for (int i = 0; i < 4; i++) {
            hostGame.setStatValue(2, i, stats[i]);
          }
        }
      });

    } else if (hostGame.getPlayer4().getText().isEmpty()
        && hostGame.getEasyBotLabel3().getText().isEmpty()
        && hostGame.getHardBotLabel3().getText().isEmpty()) {

      Platform.runLater(() -> {
        hostGame.getPlayer4().setText(name);
        hostGame.addKickButton(2);
        hostGame.removeBotButtons(2);
        ready[2] = false;
        if (this.newLobby) {
          this.hostGame.setPlayerName(3, name);
          this.hostGame.setPlayerName4(name);
          for (int i = 0; i < 4; i++) {
            hostGame.setStatValue(3, i, stats[i]);
          }
        }
      });

    }
    this.sendLobbyDetails();
  }

  /**
   * Removes a player from the GUI.
   * 
   * @param name String object, name of player
   */
  
  public void removePlayer(String name) {
    if (hostGame.getPlayer2().getText().equals(name)) {
      Platform.runLater(() -> {
        hostGame.addBotButtons(0);
        hostGame.removeKickButton(0);
        hostGame.getPlayer2().setText("");
        this.hostGame.setPlayerName2("");
        ready[0] = true;
        if (this.newLobby) {
          this.hostGame.setPlayerName2("");
          for (int i = 0; i < 4; i++) {
            hostGame.setStatValue(1, i, "");
          }
        }
        this.sendLobbyDetails();
      });
    } else if (hostGame.getPlayer3().getText().equals(name)) {
      Platform.runLater(() -> {
        hostGame.addBotButtons(1);
        hostGame.removeKickButton(1);
        hostGame.getPlayer3().setText("");
        ready[1] = true;
        this.hostGame.setPlayerName3("");
        if (this.newLobby) {
          for (int i = 0; i < 4; i++) {
            hostGame.setStatValue(2, i, "");
          }
        }
        this.sendLobbyDetails();
      });
    } else if (hostGame.getPlayer4().getText().equals(name)) {
      Platform.runLater(() -> {
        hostGame.addBotButtons(2);
        hostGame.removeKickButton(2);
        hostGame.getPlayer4().setText("");
        ready[2] = true;
        if (this.newLobby) {
          this.hostGame.setPlayerName4("");
          for (int i = 0; i < 4; i++) {
            hostGame.setStatValue(3, i, "");
          }
        }
        this.sendLobbyDetails();
      });
    }
  }

  /**
   * Kicks a player.
   * 
   * @param name String object, name of player
   */

  public void kickPlayer(String name) {
    Message kickMessage =
        new DisconnectMessage(MessageType.DISCONNECT, Profile.getName(), "You were kicked.");
    server.sendToOne(kickMessage, name);
    server.removeClient(name);
  }

  /**
   * Adds AI.
   */

  public void addAi(int index) {
    server.addAi();

    Platform.runLater(() -> {
      if (this.newLobby) {
        this.hostGame.setPlayerName(index, "Bot");
        for (int i = 0; i < 4; i++) {
          hostGame.setStatValue(index, i, "-");
        }
      }
      this.sendLobbyDetails();
    });
  }

  /**
   * Remove AI.
   */

  public void removeAi(int index) {
    server.removeAi();

    Platform.runLater(() -> {
      if (this.newLobby) {
        this.hostGame.setPlayerName(index, "");
        for (int i = 0; i < 4; i++) {
          hostGame.setStatValue(index, i, "");      
        }
      }
      this.sendLobbyDetails();
    });
  }

  /**
   * Sends the LobbyDetails.
   */

  public void sendLobbyDetails() {
    String[] names = {hostGame.getPlayer1().getText(), hostGame.getPlayer2().getText(),
        hostGame.getPlayer3().getText(), hostGame.getPlayer4().getText()};

    names[0] = hostGame.getPlayer1().getText();
    if (!hostGame.getPlayer2().getText().isEmpty()) {
      names[1] = hostGame.getPlayer2().getText();
    } else if (!hostGame.getEasyBotLabel1().getText().isEmpty()) {
      names[1] = hostGame.getEasyBotLabel1().getText();
    } else {
      names[1] = hostGame.getHardBotLabel1().getText();
    }
    if (!hostGame.getPlayer3().getText().isEmpty()) {
      names[2] = hostGame.getPlayer3().getText();
    } else if (!hostGame.getEasyBotLabel2().getText().isEmpty()) {
      names[2] = hostGame.getEasyBotLabel2().getText();
    } else {
      names[2] = hostGame.getHardBotLabel2().getText();
    }
    if (!hostGame.getPlayer4().getText().isEmpty()) {
      names[3] = hostGame.getPlayer4().getText();
    } else if (!hostGame.getEasyBotLabel3().getText().isEmpty()) {
      names[3] = hostGame.getEasyBotLabel3().getText();
    } else {
      names[3] = hostGame.getHardBotLabel3().getText();
    }

    String[][] table = new String[4][4];

    for (int i = 0; i < table.length; i++) {
      for (int j = 0; j < table[i].length; j++) {
        table[i][j] = (hostGame.getStatValues()[i][j]).getText();
      }
    }

    boolean[] sentReady = new boolean[3];
    sentReady[0] = this.ready[0];
    sentReady[1] = this.ready[1];
    sentReady[2] = this.ready[2];

    String[] gamePlayers = null;
    if (!this.newLobby) {
      gamePlayers = this.gamePlayer.clone();
    }
    
    LobbyDetailsMessage ldm = new LobbyDetailsMessage(MessageType.LOBBYDETAILS, Profile.getName(),
        names, hostGame.getLobbyName().getText(), ServerSettings.getIPAdress(), table, sentReady, 
        gamePlayers);
    server.sendToAll(ldm);
  }

  /**
   * Sends chat message in game.
   * 
   * @param text String object
   */

  public void sendChatGame(String text) {
    ChatMessage cm = new ChatMessage(MessageType.CHAT, Profile.getName(), text, "game");
    server.sendToAll(cm);
  }

  /**
   * Receives chat messages.
   * 
   * @param cm ChatMessage object
   */

  public void receiveChatLobby(ChatMessage cm) {
    Platform.runLater(() -> {
      hostGame.msgIsFrom(cm.getFrom());
      hostGame.setChatMessage(cm.getFrom(), cm.getText(), false);
    });
  }

  /**
   * Sends chat messages.
   * 
   * @param s String for the message
   */

  public void sendChatLobby(String text) {
    ChatMessage cm = new ChatMessage(MessageType.CHAT, Profile.getName(), text, "lobby");
    server.sendToAll(cm);

    Platform.runLater(() -> {
      hostGame.msgIsFrom(cm.getFrom());
      hostGame.setChatMessage(cm.getFrom(), cm.getText(), true);
    });
  }

  /**
   * Starts the game. 
   * The socket for the client will be blocked beforehand and an announcement is sent.
   * 
   * @param game game object
   */

  public void startGame(Scrabble scrabble) {
    Message msgBlock = new BlockSocketMessage(MessageType.BLOCKSOCKET, Profile.getOnlineName());
    this.server.sendToAll(msgBlock);
    Scrabble copyGame = new Scrabble(scrabble);
    Message initMessage =
        new InitializeMessage(MessageType.INITIALIZE, Profile.getName(), copyGame);
    server.sendToAll(initMessage);
  }

  /**
   * Sends a message to all clients that they can start their game.
   * 
   * @param game game object
   */
  
  public void sendStartMessage(Scrabble game) {
    Scrabble copy = new Scrabble(game);
    Message msgStart = new StartGameMessage(MessageType.STARTGAME, Profile.getName(), copy.getTurnState());
    this.server.sendToAll(msgStart);
  }

  /**
   * Sends the current game move to all players.
   * 
   * @param game state of the game
   */
  
  public void sendMove(TurnState game) {
    Message msg = new UpdateGameMessage(MessageType.UPDATEGAME, Profile.getName(), game);
    this.server.sendToAll(msg);
  }

  /**
   * Updates lobby after a Scrabble game was played.
   * The ready array and the statistics table will be updated.
   */
  
  public void updateLobbyAfterGame() {
    if(!this.hostGame.getPlayer2().getText().isEmpty()) {
      ready[0] = false; 
    }
    if(!this.hostGame.getPlayer3().getText().isEmpty()) {
      ready[1] = false; 
    }
    if(!this.hostGame.getPlayer4().getText().isEmpty()) {
      ready[2] = false; 
    }

    if (ready[0]) {
      hostGame.colorReadyPlayers(hostGame.getPlayer2().getText());
    } else {
      hostGame.colorNotReadyPlayers(hostGame.getPlayer2().getText());
    }
    if (ready[1]) {
      hostGame.colorReadyPlayers(hostGame.getPlayer3().getText());
    } else {
      hostGame.colorNotReadyPlayers(hostGame.getPlayer3().getText());
    }
    if (ready[2]) {
      hostGame.colorReadyPlayers(hostGame.getPlayer4().getText());
    } else {
      hostGame.colorNotReadyPlayers(hostGame.getPlayer4().getText());
    }

    if (this.gameControl.getGame().getMoveCount() >= 5) {
      this.newLobby = false;
      this.gamePlayer = new String[4];
      if (this.gameControl.getGame().getGameState() == GameState.GAME_OVER) {
        Platform.runLater(() -> {
          for (int i = 0; i < this.gameControl.getGame().getPlayerCount(); i++) {
            String[] stats = StatisticController.getGameStats(this.gameControl.getGame(), i);
            for (int x = 0; x < stats.length; x++) {
              this.hostGame.setStatValue(i, x, stats[x]);
              this.gamePlayer[i] = this.gameControl.getGame().getPlayer(i).getName();
              this.hostGame.setPlayerName(i, this.gamePlayer[i]);
            }
            this.hostGame.setStatName1("Score");
            this.hostGame.setStatName2("Highscore");
            this.hostGame.setStatName3("Avg. Score");
            this.hostGame.setStatName4("Avg. Turn Time");
          }
        });
      }
    }
    this.sendLobbyDetails();
  }

  /**
   * Changes if ready.
   * 
   * @param name String object
   */

  public void changeReady(String name) {
    if (hostGame.getPlayer2().getText().equals(name)) {
      if (ready[0]) {
        ready[0] = false;
        hostGame.colorNotReadyPlayers(name);
      } else {
        ready[0] = true;
        hostGame.colorReadyPlayers(name);
      }
    } else if (hostGame.getPlayer3().getText().equals(name)) {
      if (ready[1]) {
        ready[1] = false;
        hostGame.colorNotReadyPlayers(name);
      } else {
        ready[1] = true;
        hostGame.colorReadyPlayers(name);
      }
    } else if (hostGame.getPlayer4().getText().equals(name)) {
      if (ready[2]) {
        ready[2] = false;
        hostGame.colorNotReadyPlayers(name);
      } else {
        ready[2] = true;
        hostGame.colorReadyPlayers(name);
      }
    }
    this.sendLobbyDetails();
  }

  /**
   * Returns true if all players ready.
   * 
   * @return boolean object
   */

  public boolean isReady() {
    return ready[0] && ready[1] && ready[2];
  }

  /**
   * Returns amount of players.
   * 
   * @return amount of players
   */

  public int getPlayers() {
    return server.getAmountPlayer();
  }


  /**
   * Returns HostGameLobby object.
   * 
   * @return HostGameLobby object
   */

  public HostGameLobby getHostGame() {
    return hostGame;
  }

  /**
   * Sets the HostGameLobby.
   * 
   * @param hostGame HostGameLobby object
   */

  public void setHostGame(HostGameLobby hostGame) {
    this.hostGame = hostGame;
    this.hostGame.getPlayer1().setText(Profile.getName());
    this.hostGame.setPlayerName(0, Profile.getName());
    this.hostGame.setConnectionDetails(ServerSettings.getIPAdress());

    String[] stats = Profile.getNetworkStats().getStats();
    for (int i = 0; i < 4; i++) {
      this.hostGame.setStatValue(0, i, stats[i]);
    }
  }

  /**
   * Sets InGame gui.
   * 
   * @param InGameNetwork Ingame object
   */

  public void setInGame(InGameNetwork inGame) {
    this.inGame = inGame;
    this.inGame.setController(this);
  }

  /**
   * Returns if server active.
   * 
   * @return serverActive boolean
   */

  public boolean isServerActive() {
    return serverActive;
  }

  /**
   * Sets the GameController and sets up the network in the GameController.
   * 
   * @param gameControl GameController
   */
  
  public void setGameController(GameController gameControl) {
    this.gameControl = gameControl;
    this.gameControl.setNetwork(this, true);
  }



  /**
   * This method is called when the host leaves a running game.
   * Every client will return to their lobby.
   */
  
  public void sendBackToLobby() {
    StatisticController.updateStats(this.gameControl.getGame(), true);
    if (this.gameControl.getGame().getGameState() != GameState.GAME_OVER) {
      Message msg = new LeaveGameMessage(MessageType.LEAVEGAME, Profile.getOnlineName());
      this.server.sendToAll(msg);
    }
    this.updateLobbyAfterGame();
  }
}
