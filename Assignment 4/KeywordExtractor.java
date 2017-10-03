/* Name: Alex Hale
 * ID: 260672475
 * Date: April 12, 2016 */

import java.util.*;
import java.io.*;

public class KeywordExtractor {
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
    
    try {
      // create a folder object of the directory provided in the input
      File folder = new File(dir);
      
      // store all the names of the documents in an ArrayList
      ArrayList<String> fileNames = new ArrayList<String>(Arrays.asList(folder.list()));
      
      // create a TreeMap which will be used for sorting the word-value pairs
      TreeMap<Integer, String> tempForSorting = new TreeMap<Integer, String>();
      
      // iterate through all the files in the folder
      for (int i = 0; i < fileNames.size(); i++) {
        // separate the file name into the sections before and after the period
        String[] separated = fileNames.get(i).split("\\.");
        
        // Add the sections before and after the period to the TreeMap.
        // The TreeMap will sort the values based on the number before the period, instead of alphabetically.
        tempForSorting.put(Integer.parseInt(separated[0]), separated[1]);
      }
      
      // empty the ArrayList of filenames
      fileNames.clear();
      
      // add each filename, now sorted in numerical order, back into the ArrayList (inserting the period back in as well)
      for (Map.Entry<Integer, String> entry : tempForSorting.entrySet()) {
        fileNames.add(entry.getKey() + "." + entry.getValue());
      }
      
      // get the HashMap of document frequencies from the file created in the DocumentFrequency class
      HashMap<String, Integer> dfs = readDocumentFrequencies("freqs.txt");
      
      // iterate through each file in the folder
      for (int i = 1; i <= fileNames.size(); i++) {
        // create a HashMap of term frequencies for the current document
        HashMap<String, Integer> tfs = computeTermFrequencies(dir + "/" + fileNames.get(i-1));
        
        // create a HashMap of TFIDF scores for the words in the current document
        HashMap<String, Double> tfidf = computeTFIDF(tfs, dfs, fileNames.size());
        
        // print the file name
        System.out.println(fileNames.get(i-1));
        
        // print the top 5 keywords in the current document, based on their TFIDF scores
        printTopKeywords(tfidf, 5);
        
        // add a space
        System.out.println();
      }
    }
    catch (Exception e) {
      System.err.println("There was an error with your directory name!");
      System.err.println(e.getMessage());
    }
  }
  
  // this method turns a file into a HashMap of term frequencies
  public static HashMap<String, Integer> computeTermFrequencies(String filename) {
    try {
      // create a HashMap in which we'll store the words and their term frequencies
      HashMap<String, Integer> termFrequencies = new HashMap<String, Integer>();
      
      // create a scanner at the file specified by the filename parameter
      Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)));
      
      // iterate through each line of the text file
      while(sc.hasNextLine()) {
        // store the line from the document in a String
        String read = sc.nextLine();
        
        // use DocumentFrequency.normalize() to remove punctuation and capital letters from the line
        read = DocumentFrequency.normalize(read);
        
        // split the line into an array of Strings, with each word in the line becoming its own element
        String[] words = read.split(" ");
        
        // iterate through each word in the line
        for (int i = 0; i < words.length; i++) {
          // make sure that none of the words are nothing
          if (!words[i].equals("")) {
            // if the key is already in the HashMap, replace it with the same key and a value one higher
            if (termFrequencies.containsKey(words[i])) {
              termFrequencies.put(words[i], termFrequencies.get(words[i]) + 1);
            } else {
              // otherwise, add it as a new key with a value of 1
              termFrequencies.put(words[i], 1);
            }
          }
        }
      }
      // return the HashMap of term frequencies for the individual document
      return termFrequencies;
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
  
  // this method turns a file generated by DocumentFrequency into a HashMap of document frequencies
  public static HashMap<String, Integer> readDocumentFrequencies(String filename) {
    try {
      // create a HashMap in which to store the document frequencies
      HashMap<String, Integer> wordFrequencies = new HashMap<String, Integer>();
      
      // create a scanner at the file specified by the filename parameter
      Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)));
      
      // iterate through each line of the text file
      while(sc.hasNextLine()) {
        // split the line into the word and its frequency
        String[] wordAndNumber = sc.nextLine().split(" ");
        
        // add the word and frequency pair to the HashMap
        wordFrequencies.put(wordAndNumber[0], Integer.parseInt(wordAndNumber[1]));
      }
      // return the HashMap of document frequencies for this document
      return wordFrequencies;
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
  
  // this method computes the TFIDF of all the words in the HashMap of term frequencies
  public static HashMap<String, Double> computeTFIDF(HashMap<String, Integer> tfs, HashMap<String, Integer> dfs, 
                                                     double nDocs) {
    // create a HashMap in which we'll store the TFIDF of each word
    HashMap<String, Double> tfidf = new HashMap<String, Double>();
    
    // iterate through each element in the HashMap of term frequencies
    for(Map.Entry<String, Integer> entry: tfs.entrySet()) {
      // get String
      String key = entry.getKey();
      
      // search dfs for String, get corresponding df
      int df = dfs.get(key);
      
      // calculate idf = ln(nDocs/df)
      double idf = Math.log(nDocs/df);
      
      // calculate tfidf = entry.get(String)*idf;
      double tfidfScore = entry.getValue()*idf;
      
      // put key and value into tfidf HashMap
      tfidf.put(key, tfidfScore);
    }
    // return the HashMap of term frequencies
    return tfidf;
  }
  
  /**
   * This method prints the top K keywords by TF-IDF in descending order. Do not modify.
   */
  public static void printTopKeywords(HashMap<String, Double> tfidfs, int k) {
    ValueComparator vc =  new ValueComparator(tfidfs);
    TreeMap<String, Double> sortedMap = new TreeMap<String, Double>(vc);
    sortedMap.putAll(tfidfs);
    
    int i = 0;
    for(Map.Entry<String, Double> entry: sortedMap.entrySet()) {
      String key = entry.getKey();
      Double value = entry.getValue();
      
      System.out.println(key + " " + value);
      i++;
      if (i >= k) {
        break;
      }
    }
  }  
}

/*
 * This class makes printTopKeywords work. Do not modify.
 */
class ValueComparator implements Comparator<String> {
    
    Map<String, Double> map;
    
    public ValueComparator(Map<String, Double> base) {
      this.map = base;
    }
    
    public int compare(String a, String b) {
      if (map.get(a) >= map.get(b)) {
        return -1;
      } else {
        return 1;
      } // returning 0 would merge keys 
    }
  }


/* It's impossible to be certain without actually computing the TFIDF score of every word in each document,
 * but it seems to me that my algorithm worked well. Each word in the top 5 TFIDF scores of the document
 * seems unique (in that it is unlikely to be common in another document) and the scores are sorted in 
 * descending order. */ 