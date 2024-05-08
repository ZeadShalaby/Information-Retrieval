package setting;

import static java.awt.SystemColor.text;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import static jdk.nashorn.internal.objects.NativeArray.push;
import org.tartarus.snowball.ext.EnglishStemmer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zead shalaby
 */
public class settings {
    
    private static String[] choices;
    private static List<File> listFiles; // Assuming this is a class-level variable
    private static boolean successImg = false;
    
    // Method of choices pre-processing //
    public static void setSaveChoices(String chosenOptions) {
        if (choices == null) {
            choices = new String[1];
            choices[0] = chosenOptions;
        }else {
            String[] newArray = new String[choices.length + 1];
            System.arraycopy(choices, 0, newArray, 0, choices.length);
            newArray[choices.length] = chosenOptions;
            choices = newArray;
        }
    }

    public static String[] getChoices() {
        return choices;
       // return null;
    }
    
   
    // Method to save files
    public static void setSaveFiles(List<File> listfile) {
        if (listfile != null ) {
            listFiles = listfile;
        }
    }

    // Method to get files
    public static List<File> getFiles() {
     return listFiles;
    }

    public static boolean setSuccessImg(boolean img) {
    successImg = img;
    return true;
    }
        
    public static boolean getimageupload(){
        if(successImg == true){
        return true ;}
        return false;
    }

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////    
    
    // Method to calculate unique terms from the documents
    public static Set<String> calculateTerms(String[] documents) {
        Set<String> terms = new HashSet<>();
        for (String document : documents) {
            String[] words = document.split("\\s+");
            terms.addAll(Arrays.asList(words));
        }
        return terms;
    }
    
    
    // Method to read dataset from a file
    public static List<String> readDataset(File[] files) throws IOException {
        List<String> dataset = new ArrayList<>();
         for (File f: files) {   // for each file in the directory
            if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead() ) {
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                dataset.add(line);
            }
        }}}
        return dataset;
    }
    
    
    public static String readFileContent(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (FileReader reader = new FileReader(file)) {
            int c;
            while ((c = reader.read()) != -1) {
                content.append((char) c);
            }
        }
        return content.toString();
    }
    
    
    
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

    
     
    

 ///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////    
       
    // check if directory exist or not (delete old index )
     public static void CheckDirectory(String indexPath)
    {
        try {
            File indexDir = new File(indexPath);
            if (indexDir.exists()) {
                String[] entries = indexDir.list();
                if (entries != null) {
                    for (String entry : entries) {
                        File currentFile = new File(indexDir.getPath(), entry);
                        currentFile.delete();
                    }
                }
                indexDir.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
             

 
     // Copy the file to the destination directory
     public static File[] copy_dataset(String type_indx, String content ,  String Name) throws IOException {
        // Specify the destination directory
        Path destinationDirectory = Paths.get("D:\\my projects\\last year project\\Semester 2\\IR\\project\\IR_Projects_Gui\\targetData\\" + type_indx);
                
        // Specify the destination file path
        Path destinationFile = destinationDirectory.resolve(Name + "_pre.txt");

        try {
            // Create a FileWriter object
            FileWriter fileWriter = new FileWriter(destinationFile.toFile());

            // Create a BufferedWriter object to write data to the file efficiently
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the content to the file
            if(content != null){
            bufferedWriter.write(content);}
            

            // Close the BufferedWriter to flush the buffer and close the file
            bufferedWriter.close();
         
            System.out.println("Dataset has been copied successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    
      File[] files = new File(destinationDirectory.toString()).listFiles();
      return files;
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

    public static void check_datasetCopy(String typeIndex) throws IOException{
        Path destinationDirectory = Paths.get("D:\\my projects\\last year project\\Semester 2\\IR\\project\\IR_Projects_Gui\\targetData\\" + typeIndex);
        if (Files.exists(destinationDirectory)) {

           try {
               Files.walk(destinationDirectory)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
               System.out.println("Directory deleted successfully.");
               Files.createDirectories(destinationDirectory);
           } catch (Exception e) {
               System.out.println("Failed to delete directory: " + e.getMessage());
           }
            } else {  Files.createDirectories(destinationDirectory); }
        
        
    }
    // files after all thing prepeocessing and all 
    public static File[] newListfile(List<File> listfiles , String [] option , String typeIndex) throws IOException {
        check_datasetCopy(typeIndex);
        System.out.println(stop_word);
        File[] new_listFiles = null ;
        for(File file : listfiles){
            String content = readFileContent(file);
            String contPre =   pre(content, option);
            File[] filePath = copy_dataset(typeIndex,  contPre , file.getName());
            new_listFiles = filePath;       
        }
       return new_listFiles;  
    }
 

}












//
//
//
//
//public static void copy_dataset(String type_indx ,List<File> file) {
//       if (file != null) {
//            try {
//                for(File f:file){
//                // Get source file path
//                Path sourcePath = f.toPath();
//
//                // Specify the destination directory
//                Path destinationDirectory = Paths.get("D:\\my projects\\last year project\\Semester 2\\IR\\project\\IR_Projects_Gui\\targetData\\"+type_indx);
//
//                 if (Files.exists(destinationDirectory)) {
//                    // If yes, delete all files inside the directory
//                    File[] files = destinationDirectory.toFile().listFiles();
//                    if (files != null) {
//                        for (File fi : files) {
//                            Files.deleteIfExists(fi.toPath());
//                        }
//                    }
//                } else {
//                    // If directory does not exist, create it
//                    Files.createDirectories(destinationDirectory);
//                }
//                // Copy the file to the destination directory
//                Files.copy(sourcePath, destinationDirectory.resolve(sourcePath.getFileName()));
//
//                System.out.println("File copied successfully!");
//                }
//            } catch (IOException e) {
//                System.out.println("Error copying file: " + e.getMessage());
//            }
//        }
//    }




