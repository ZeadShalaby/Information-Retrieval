/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author zead shalaby
 */
public class Bi_ward {
  
public static void search_Bi_ward(String type_indx, String Query) throws IOException {
            String[] words = Query.split(" ");

            if (words.length == 1) {
                readAndSearchFiles(words[0]);
            } else if (words.length == 2) {
                String targetWord = words[0] + " " + words[1];
                readAndSearchFiles(targetWord);
            } else if (words.length == 3) {
                String targetWord = words[0] + " " + words[1] + " " + words[2];
                readAndSearchFiles(targetWord);
            } else {
                System.out.println("Please enter one, two, or three words.");
            }  

       }
   
   
     private static void readAndSearchFiles(String targetWord) throws IOException {
        String dataDir = "targetData/Bi-ward index"; // Index *.txt files from this directory
        File[] files = new File(dataDir).listFiles();

        for (File fileName : files) {
            List<Integer> positions = findWordPositionsInFile(fileName.getCanonicalPath(), targetWord);
            if (!positions.isEmpty()) {
                System.out.println("Occurrences of '" + targetWord + "' in " + fileName + ": " + positions);
            }
        }
        index_search(targetWord);
    }

    private static List<Integer> findWordPositionsInFile(String fileName, String targetWord) {
        List<Integer> positions = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine();
                String[] words = line.split(" ");
                for (int i = 0; i < words.length - targetWord.split(" ").length + 1; i++) {
                    StringBuilder wordSeq = new StringBuilder();
                    for (int j = 0; j < targetWord.split(" ").length; j++) {
                        wordSeq.append(words[i + j]).append(" ");
                    }
                    String wordSequence = wordSeq.toString().trim();
                    if (wordSequence.equals(targetWord)) {
                        positions.add(lineNumber); // Add line number instead of word position
                    }
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return positions;
    }
    
    
    
     // search in index 
    public static void index_search(String Query){
        
        String indexDirectoryPath = "indexes/Bi-ward index";

         try (Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));
             IndexReader reader = DirectoryReader.open(indexDirectory)) {
        QueryParser parser = new QueryParser(Version.LUCENE_41,"contents",new SimpleAnalyzer(Version.LUCENE_41)); 

            IndexSearcher searcher = new IndexSearcher(reader);
            Query query = parser.parse(Query);
            TopDocs hits = searcher.search(query, 100);

            System.out.println("========================================================");
            System.out.println("                Display Search Results:");
            System.out.println("========================================================\n");

            if (hits.totalHits == 0) {
                System.out.println("No documents found matching the query: " + Query);
            } else {
                System.out.println("Found " + hits.totalHits + " document(s) that matched query '" + Query + "':\n");

                for (ScoreDoc scoreDoc : hits.scoreDocs) {
                    Document doc = searcher.doc(scoreDoc.doc);
                    System.out.println("Document: " + doc.get("filename"));
                    System.out.println("fullpath: " + doc.get("fullpath"));
                    System.out.println();
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
       } 
    }
    
    
}
