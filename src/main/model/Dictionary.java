package main.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * The Dictionary class represents a dictionary.
 * 
 * @author sekeller
 */
public class Dictionary implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The path of the directory where the dictionary files are located.
   */
  public static final String PATH = "res/dict/";

  /**
   * The entries of the dictionary.
   */
  private final TreeMap<String, String> entries;

  /**
   * Constructs a dictionary with the specified entries.
   * 
   * @param entries the specified entries
   */
  public Dictionary(TreeMap<String, String> entries) {
    this.entries = entries;
  }

  /**
   * Constructs a copy of the specified dictionary.
   * 
   * @param original the specified dictionary
   */
  Dictionary(Dictionary original) {
    this.entries = new TreeMap<>(original.entries);
  }

  /**
   * Returns true when this dictionary contains the specified word.
   * 
   * @param word the specified word
   * @return true when this dictionary contains the specified word
   */
  public boolean contains(String word) {
    return this.entries.containsKey(word);
  }

  /**
   * Returns the description for the specified word.
   * 
   * @param word the specified word
   * @return the description for the specified word
   */
  public String getDescription(String word) {
    return this.entries.get(word);
  }

  /**
   * Returns a list with all words of this dictionary.
   * 
   * @return a list with all words of this dictionary
   */
  public List<String> getAllWords() {
    return new ArrayList<String>(this.entries.keySet());
  }

  /**
   * Returns a dictionary which contains all words and descriptions which are in the file with the
   * specified filename.
   * 
   * @param filename the specified filename
   * @return a dictionary
   */
  public static Dictionary readFile(String filename) {
    TreeMap<String, String> dictionaryEntries = new TreeMap<>();

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH + filename))) {
      String line;

      bufferedReader.readLine();
      bufferedReader.readLine();

      while ((line = bufferedReader.readLine()) != null) {
        if (!line.isBlank()) {
          String word, description;

          if (line.contains("\t")) {
            String[] tokens = line.split("\t");
            word = tokens[0].trim().toUpperCase();
            description = tokens[1].trim();
          } else {
            word = line.trim().toUpperCase();
            description = "no description available";
          }

          dictionaryEntries.put(word, description);
        }
      }
    } catch (FileNotFoundException fileNotFoundException) {
      fileNotFoundException.printStackTrace();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }

    return new Dictionary(dictionaryEntries);
  }

}
