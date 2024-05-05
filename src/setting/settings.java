package setting;

import static java.awt.SystemColor.text;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
      
//    String[] stemmedText = stemming(words);
//        return stemmedText;
//    
    public static String pre(String content, String[] option) {
        String[] steam = null,toknizes;String normalize = null ,toknizlemt = null ;
        for(String op : option){
            if(op.equals("normalization")){ normalize = normalization(content); content = normalize;}
//            else if(op.equals("steming")){ if(normalize != null){toknizes = toknize(normalize);}else{toknizes = toknize(content);} steam = stemming(toknizes); }
//            else if(op.equals("lemmatization")){if(steam != null){toknizlemt = toknize(steam);}}
        }
        return content;
    }

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

    /// steaming text ///
    public static String[] stemming(String[] words) {
        // Initialize the stemmer
        EnglishStemmer stemmer = new EnglishStemmer();

        // Stem each word and store the result in an array
        String[] stemmedWords = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            stemmer.setCurrent(words[i]);
            if (stemmer.stem()) {
                stemmedWords[i] = stemmer.getCurrent();
            } else {
                System.out.println("Stemming failed for word: " + words[i]);
            }
        }

    return stemmedWords;
 }


    
    ///  normalize text  ///
    public static String normalization(String text) {
        // Convert text to lowercase
        text = text.toLowerCase();

        // Remove punctuation
        text = text.replaceAll("[^a-zA-Z\\s]", "");

        // Remove diacritics (accents)
        text = removeDiacritics(text);

        // Remove stop words (example list)
        String[] stopWords = {"is", "a", "with", "some", "and"};
        for (String word : stopWords) {
            text = text.replaceAll("\\b" + word + "\\b", "");
        }

        return text;
    }

    // Method to remove diacritics (accents) from text
    public static String removeDiacritics(String text) {
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(text).replaceAll("");
    }
    
    
    
    // lemmatization
    private static final Map<String, String> lemmatizationMap;

    static {
        lemmatizationMap = new HashMap<>();
        // Add lemmatization rules or mappings
        lemmatizationMap.put("running", "run");
        lemmatizationMap.put("jumps", "jump");
        // Add more mappings as needed
    }

    public static String lemmatize(String word) {
        // Check if the word exists in the lemmatization map
        if (lemmatizationMap.containsKey(word)) {
            return lemmatizationMap.get(word);
        }
        // If the word is not found in the map, return the original word
        return word;
    }
    
    public static String[] lemmatizeWords(String[] words) {
            String[] lemmatizedWords = new String[words.length];
            for (int i = 0; i < words.length; i++) {
                lemmatizedWords[i] = lemmatize(words[i]);
            }
            return lemmatizedWords;
        }
    
    public static String[] lemmatize()
    {
        String[] words = null;
        String[] lemmatizedWords = lemmatizeWords(words);
        return lemmatizedWords;
    }

    
    
    
//    
//    // Method to read dataset from a file
//    public static List<String> readDataset(List<File> filePaths) {
//           List<String> dataset = new ArrayList<>();
//           for (File file : filePaths) {
//               try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//                   String line;
//                   while ((line = reader.readLine()) != null) {
//                       dataset.add(line.trim());
//                   }
//               } catch (IOException e) {
//                   e.printStackTrace();
//               }
//           }
//           return dataset;
//       }
    
    
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

    public static void copy_dataset(String type_indx ,List<File> file) {
       if (file != null) {
            try {
                for(File f:file){
                // Get source file path
                Path sourcePath = f.toPath();

                // Specify the destination directory
                Path destinationDirectory = Paths.get("D:\\my projects\\last year project\\Semester 2\\IR\\project\\IR_Projects_Gui\\targetData\\"+type_indx);

                 if (Files.exists(destinationDirectory)) {
                    // If yes, delete all files inside the directory
                    File[] files = destinationDirectory.toFile().listFiles();
                    if (files != null) {
                        for (File fi : files) {
                            Files.deleteIfExists(fi.toPath());
                        }
                    }
                } else {
                    // If directory does not exist, create it
                    Files.createDirectories(destinationDirectory);
                }
                // Copy the file to the destination directory
                Files.copy(sourcePath, destinationDirectory.resolve(sourcePath.getFileName()));

                System.out.println("File copied successfully!");
                }
            } catch (IOException e) {
                System.out.println("Error copying file: " + e.getMessage());
            }
        }
    }

    public static boolean analyzer(String[] option) {
                 for(String op :option ){
                     if(op.equals( "stop words")){
                        return true;
                     }
                 }
                 return false;
    }
    }






