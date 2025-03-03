package main.model;

/**
 * The GameException class represents a game exception.
 * 
 * @author sekeller
 */
public class GameException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a game exception with the specified message.
   * 
   * @param message the specified message
   */
  GameException(String message) {
    super(message);
  }

  /**
   * Constructs a copy of the specified game exception.
   * 
   * @param original the specified game exception
   */
  GameException(GameException original) {
    super(original == null ? "" : original.getMessage());
  }

}
