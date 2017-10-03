/* Name: Alex Hale
 * ID: 260672475
 * Date: April 12, 2016 */

import java.util.*;
import java.io.*;

public class DocumentFrequency {
  public static void main(String[] args) {
    String dir = "";
    if (args.length == 1) {
      // get the name of the directory where the input files are located
      dir = args[0];
    } else {
      // set up a scanner to get a file name input
      Scanner scan = new Scanner(System.in);
      dir = "";
      
      // request a new file name input until the user enters one
      while (dir.equals("")) {
      System.out.println("Please enter the directory where the documents are located.");
      dir = scan.nextLine();
      }
    }
    
    // create a HashMap to store the document frequencies
    HashMap<String, Integer> dfs = extractDocumentFrequencies(dir, 40);

    // call the writeDocumentFrequencies method to save the document frequencies to a file
    writeDocumentFrequencies(dfs, "freqs.txt");
  }
  
  /* This method runs through each file in a folder and computes the document frequency of each word.
   * we don't need a try-catch block because we're not reading from any documents here, we do that through 
   * the extractWordsFromDocument() method.  */
  public static HashMap<String, Integer> extractDocumentFrequencies(String directory, int nDocs) {
    // a HashMap in which we'll store the words and their frequencies
    HashMap<String, Integer> multiDocWords = new HashMap<String, Integer>();
    
    // iterate through all the docs, the number of which is specified by the nDocs parameter
    for (int i = 1; i <= nDocs; i++) {
      // use the extractWordsFromDocument() method to put all of the words from one document into a HashSet
         // we are assuming that the file names are 1.txt, 2.txt, ...
      HashSet<String> singleDocWords = extractWordsFromDocument(directory + "/" + i + ".txt");
      
      // if there was an exception caught in extractWordsFromDocument method, have this method return null as well.
      if (singleDocWords == null) {
        return null;
      }
      
      // iterate through each element of this document's HashSet
      for (String s : singleDocWords) {
        // if the key is already in the HashMap, replace it with the same key and a value one higher
        if (multiDocWords.containsKey(s)) {
          multiDocWords.put(s, multiDocWords.get(s) + 1);
        } else {
          // otherwise, add it as a new key with a value of 1
          multiDocWords.put(s, 1);
        }
      }
    }
    // return the HashMap of document frequencies
    return multiDocWords;
  }
  
  // this method takes a document and returns a HashSet of all the words in that document
  public static HashSet<String> extractWordsFromDocument(String filename) {
    try {
      // a HashSet in which we'll store the words
      HashSet<String> words = new HashSet<String>();
      
      // a scanner to read from the file
      Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)));
      
      // continue to read while there are still lines left in the document
      while(sc.hasNextLine()) {
        // store the line from the document in a String
        String read = sc.nextLine();
        
        // use DocumentFrequency.normalize() to remove punctuation and capital letters from the line
        read = normalize(read);
        
        // split the line into an array of Strings, with each word in the line becoming its own element
        String[] individual = read.split(" ");
        
        // iterate through each word in the line
        for (int i = 0; i < individual.length; i++) {
          // make sure that none of the words are nothing
          if (!individual[i].equals("")) {
            // add each word individually to the HashSet
            // If the word is already there, that's ok! The HashSet will only store one.
            words.add(individual[i]);
          }
        }
      }
      // return the HashSet full of words from that document
      return words;
    } 
    // make sure that the filename requested can be found before trying to pull words from it
    catch (FileNotFoundException fnfe) {
      System.err.println(filename + " cannot be found.");
      return null;
    }
    // catch any other exceptions without crashing the program
    catch (Exception e) {
      System.err.println(e.getMessage());
      return null;
    }
  }
  
  // this method puts the document frequencies we've calculated into a text file for later use
  public static void writeDocumentFrequencies(HashMap<String, Integer> dfs, String filename) {
    try {
      // create a new output at the filename specified with the filename parameter
      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
      
      // create an ArrayList where we'll store a String of each word with its value
      ArrayList<String> listOfWordsWithValues = new ArrayList<String>();
      
      // iterate through, turning each HashMap key + value into a String
      for (Map.Entry<String, Integer> entry : dfs.entrySet()) {
        String s = entry.getKey() + " " + entry.getValue();
        listOfWordsWithValues.add(s);
      }
      
      // sort based on ASCII order (alphabetical, since everything is in lowercase)
      Collections.sort(listOfWordsWithValues);
      
      // add each String in the ArrayList to its own line in the file
      for (int i = 0; i < listOfWordsWithValues.size(); i++) {
        out.println(listOfWordsWithValues.get(i));
      }
      
      // close the file
      out.close();
    }
    // catch any exceptions that occur and display a message to the user.
    catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
  
  /*
   * This method "normalizes" a word, stripping extra whitespace and punctuation.
   * Do not modify.
   */
  public static String normalize(String word) {
    return word.replaceAll("[^a-zA-Z ']", "").toLowerCase();
  }
  
}