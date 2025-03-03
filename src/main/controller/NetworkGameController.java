package main.controller;

import javafx.application.Platform;
import main.gui.ErrorBox;
import main.gui.InGameNetwork;
import main.model.GameState;
import main.model.TurnState;
import main.network.message.ChatMessage;

/**
 * Superclass for the ServerController and the ClientController;
 * 
 * @author frajwa
 *
 */
public abstract class NetworkGameController {
  
  /**
   * Controller of the Scrabble object.
   */
  protected GameController gameControl;
  
  /**
   * InGame object.
   */

  protected InGameNetwork inGame;
  
  public abstract void receiveChatLobby(ChatMessage cm);
  public abstract void sendChatLobby(String text);
  public abstract void sendChatGame(String text);
  public abstract void sendMove(TurnState game);
  public abstract void sendBackToLobby();
  public abstract void setInGame(InGameNetwork inGame);
  
  /**
   * Updates the game in the running network game.
   * 
   * @param game Scrabble object
   */
  public void receiveMove(TurnState game) {
    Platform.runLater(() -> {
      this.gameControl.getGame().setTurnState(game);
      this.gameControl.updateAll();
    });
  }
  
  /**
   * This method is called when a player leaves the game.
   * If the game was still running, all players will return to their lobby.
   * 
   * @param playerName player who left the game
   */
  public void receiveBackToLobby(String playerName) {
    if (this.gameControl.getGame().getGameState() == GameState.PLAY) {
      Platform.runLater(() -> {
        ErrorBox error = new ErrorBox();
        error.displayError("The game was stopped because " + playerName + " left.");
      });
      this.gameControl.getGame().stopGame();
    }
  }
  
  /**
   * Receives a chat message in game.
   * 
   * @param cm ChatMessage object
   */
  public void receiveChatGame(ChatMessage cm) {
    Platform.runLater(() -> {
      this.inGame.receiveChat(cm.getFrom(), cm.getText());
    });
  }
}
