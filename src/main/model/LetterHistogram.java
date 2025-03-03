package main.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The LetterHistogram class represents a letter histogram.
 * 
 * @author sekeller
 */
class LetterHistogram {

  /**
   * The entries of the histogram.
   */
  private Map<Character, Integer> entries;

  /**
   * Constructs a letter histogram for the specified letters.
   * 
   * @param letters the specified letters
   */
  public LetterHistogram(String letters) {
    this.entries = new HashMap<>();

    for (char letter : letters.toCharArray()) {
      if (this.entries.containsKey(letter)) {
        this.entries.put(letter, this.entries.get(letter) + 1);
      } else {
        this.entries.put(letter, 1);
      }
    }
  }

  /**
   * Returns the count of the specified letter.
   * 
   * @param letter the specified letter
   * @return the count of the specified letter
   */
  public int getCount(char letter) {
    if (this.entries.containsKey(letter)) {
      return this.entries.get(letter);
    } else {
      return 0;
    }
  }

  /**
   * Returns true if the count of each letter of this letter histogram is less or equal than the
   * count of the specified letter histogram.
   * 
   * @param other the specified letter histogram
   * @return true if the count of each letter of this letter histogram is less or equal than the
   *         count of the specified letter histogram
   */
  public boolean isLessOrEqualTo(LetterHistogram other) {
    int joker = other.getCount('*');
    for (char letter : this.entries.keySet()) {
      int diff = this.getCount(letter) - other.getCount(letter);

      if (diff > 0) {
        joker = joker - diff;
      }

      if (joker < 0) {
        return false;
      }
    }

    return true;
  }

}
