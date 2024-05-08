/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import org.tartarus.snowball.ext.EnglishStemmer;

/**
 *
 * @author zead shalaby
 */
public class setting_searcch {
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////    
    
                                       // Preprocessing //
    
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////    
      

    private static String []option;
    private static String normalize = null;
    private static String stem_process = null;
    private static String lemtize = null;
    private static String stop_word = null;

    
    ///////////////////////////
    // method pre_processing //
    //////////////////////////
    public static String pre(String content, String [] options) {
        option = options;
        stop_word = stop_words(content);
        normalize = normalization(content);
        stem_process = stemming(toknize(content));
        lemtize = lemmatize(toknize(content));
        String result = check_retrun_result();
        if(result != null){return result;}
        return content;
    }

    
    /////////////////////
    //// toknization ////
    /////////////////////
    public static String [] toknize(String text){
     // Tokenize the text into words
        StringTokenizer tokenizer = new StringTokenizer(text);

        // Create an array to hold the tokenized words
        String[] words = new String[tokenizer.countTokens()];
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            words[i++] = tokenizer.nextToken();
        }

        // Perform stemming on the words and get the stemmed text
        return words;
    }
    
    
     /////////////////////
    //List of stop words//   
    /////////////////////
    public static String stop_words(String content){
        // Read stop words from file
        Set<String> stopWords = readStopWordsFromFile("Alll Dataset\\stopWords\\stopwords.txt");
 
        // check exist option 
        boolean op = check_option(option , "stop words");
        if(op != true){return null;}
        
        // Remove stop words and get the processed text
        String processedText = removeStopWords(content, stopWords);
        return processedText;
   
    }
    // Method to read stop words from file
    private static Set<String> readStopWordsFromFile(String filePath) {
        Set<String> stopWords = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error reading stop words file: " + e.getMessage());
        }
        return stopWords;
    }
   
    // Method to remove stop words from text
    private static String removeStopWords(String text, Set<String> stopWords) {
        String[] words = text.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!stopWords.contains(word.toLowerCase())) {
                result.append(word).append(" ");
            }
        }
        return result.toString().trim();
    }

    
    
    ////////////////////////
    /// normalize text  ///
    ////////////////////////
    public static String normalization(String text) {
        // check exist option 
        boolean op = check_option(option , "normalization");
        if(op != true){return null;}
        // check stop word
        if(stop_word != null){text = stop_word;}
        
        // Remove punctuation - Convert text to lowercase
        text = text.replaceAll("[^a-zA-Z\\s]", "").toLowerCase();
        // Remove diacritics (accents)
        text = removeDiacritics(text);

        return text;
    }

    // Method to remove diacritics (accents) from text
    public static String removeDiacritics(String text) {
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(text).replaceAll("");
    }
    
    
    /////////////////////
    /// steaming text ///
    ////////////////////
    public static String stemming(String[] words) {
        // check exist option 
    // check exist option 
        boolean op = check_option(option , "steming");
        if(op != true){return null;}
        // check if do normalze processing 
        if(normalize != null){words = toknize(normalize);}
        // check stop word
        else if(stop_word != null){words = toknize(stop_word);}
        
        // Initialize the stemmer
       EnglishStemmer stemmer = new EnglishStemmer();

        // StringBuilder to store the stemmed words
        StringBuilder stemmedText = new StringBuilder();

        // Stem each word and append to the StringBuilder
        for (int i = 0; i < words.length; i++) {
            stemmer.setCurrent(words[i]);
            if (stemmer.stem()) {
                stemmedText.append(stemmer.getCurrent()).append(" "); // Append the stemmed word with a space
            } else {
                System.out.println("Stemming failed for word: " + words[i]);
            }
        }

        // Trim the trailing space and return the stemmed text
        return stemmedText.toString().trim();
    
    }

 
    
    
    /////////////////////
    /// lemmatization ///
    /////////////////////
    private static final Map<String, String> lemmatizationMap;

    static {
        lemmatizationMap = new HashMap<>();
        // Add lemmatization rules or mappings
        lemmatizationMap.put("running", "run");
        lemmatizationMap.put("jumps", "jump");
        lemmatizationMap.put("eating", "eat");
        lemmatizationMap.put("swimming", "swim");
        // Add more mappings as needed
    }

    public static String lemmatize(String[] words) {
        // check exist option 
        boolean op = check_option(option , "lemmatization");
        if(op != true){return null;}
        // check if do stem processing
        if(stem_process != null){words =toknize( stem_process);}
        // check if do  normalze processing 
        else if(normalize != null){words = toknize(normalize);}
        // check stop word
        else if(stop_word != null){words = toknize(stop_word);}
        
        
        // StringBuilder to store the lemmatized words
        StringBuilder lemmatizedText = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            lemmatizedText.append(lemmatizeWord(words[i])).append(" "); // Append the lemmatized word with a space
        }
        
        // Trim the trailing space and return the lemmatized text
        return lemmatizedText.toString().trim();
    }
    
    // Helper method to lemmatize a single word
    private static String lemmatizeWord(String word) {
        // Check if the word exists in the lemmatization map
        if (lemmatizationMap.containsKey(word)) {
            return lemmatizationMap.get(word);
        }
        // If the word is not found in the map, return the original word
        return word;
    }

     // check if option of preprocessing exist //
    private static boolean check_option(String[] options , String test) {
        for(String op:option){
            if (op.equals(test)){
                return true;
            }
        }   
        // No match found, return false
        return false;
    }
    
     
    
 // check content i return it after preprocessing 
    private static String check_retrun_result() {
       
        if(lemtize != null){return lemtize;}
        else if(stem_process != null){return stem_process;}
        else if(normalize != null){return normalize;}
        else if(stop_word != null){return stop_word;}

        return null;
    }

 ////////////
    
}
