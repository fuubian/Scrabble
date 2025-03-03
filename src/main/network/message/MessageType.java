package main.network.message;

/**
 * All types of messages combined in one enum.
 * 
 * @author lknothe
 *
 */

public enum MessageType {

  CHAT, CHANGETILES, LOBBYDETAILS, INITIALIZE, ENDGAME, CONNECT, 
  DISCONNECT, NEWNAME, UPDATEGAME, SENDWORD, CONFIRMWORD, REJECTWORD, 
  PASSMOVE, REQUESTMOVE, READY, LEAVEGAME, STARTGAME, BLOCKSOCKET, BROADCASTRESPONSE;

}
