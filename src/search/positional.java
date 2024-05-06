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
import static search.inverted.index_search;

/**
 *
 * @author zead shalaby
 */
public class positional {
    
    public static void search_positional(String type_indx, String Query) {
        String dataDir = "targetData/positional index"; // Index *.txt files from this directory
        File[] files = new File(dataDir).listFiles();

       for (File fileName : files) {
           List<Integer> positions = findWordPositionsInFile(fileName, Query);
            if (!positions.isEmpty()) {
                System.out.println("Occurrences of '" + Query + "' in " + fileName + ": " + positions);
            } else {
            }
        }  
       
        // search in index 
        index_search(Query);
    }
    
    // find word and position in files
    private static List<Integer> findWordPositionsInFile(File filename, String Query) {
        List<Integer> positions = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(filename);
            int lineCount = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(Query)) {
                    int index = line.indexOf(Query);
                    while (index >= 0) {
                        positions.add((lineCount - 1) * line.length() + index);
                        index = line.indexOf(Query, index + 1);
                    }
                }
                lineCount++;
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return positions;
    }
    
    
     // search in index 
    public static void index_search(String Query){
        
        String indexDirectoryPath = "indexes/positional index";

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
