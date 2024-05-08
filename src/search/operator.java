/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import static search.inverted.displayInvertedIndex;
import static search.inverted.generateInvertedIndex;
import static search.inverted.index_search;

/**
 *
 * @author zead shalaby
 */
public class operator {

    // return result of operators 
    public static String operator(String word1, String word2, String keys) throws IOException {

        String dataDir = "targetData/inverted index"; // Index *.txt files from this directory
        File[] files = new File(dataDir).listFiles();

        List<String> dataset = setting.settings.readDataset(files);
        String[] documents = dataset.toArray(new String[dataset.size()]);
        Map<String, Set<Integer>> invertedIndex = createInvertedIndex(documents);

        Map<String, Set<Integer>> invertedResult = modifyLogic(invertedIndex, word1, word2, keys);
        if (invertedResult != null) {
            // Display inverted index
            System.out.println("\n====================================================================================");
            System.out.println("                               Display Result:");
            System.out.println("====================================================================================\n");

            System.out.println(invertedResult );
            setting.ResultSearch.setSaveoperator(invertedResult);
            
            index_search(word1);
            index_search(word2);
        }
        
        return null;

    }

    // create inverted index first 
    public static Map<String, Set<Integer>> createInvertedIndex(String[] documents) {
        Map<String, Set<Integer>> invertedIndex = new HashMap<>();

        for (int i = 0; i < documents.length; i++) {
            String[] words = documents[i].split("\\s+");
            for (String word : words) {
                if (!invertedIndex.containsKey(word)) {
                    invertedIndex.put(word, new HashSet<>());
                }
                invertedIndex.get(word).add(i + 1); // Adding 1 to convert from zero-based index to one-based index
            }
        }

        return invertedIndex;
    }

    // modify logic do calculate result and return it 
    public static Map<String, Set<Integer>> modifyLogic(Map<String, Set<Integer>> invertedIndex, String word1, String word2, String keys) {
        Map<String, Set<Integer>> invertedResult = new HashMap<>();
        int a = 0, b = 0;
        for (Map.Entry<String, Set<Integer>> entry : invertedIndex.entrySet()) {
            if (entry.getKey().equals(word1)) {
                a = 1;
            } else if (entry.getKey().equals(word2)) {
                b = 1;
            }
        }

        if (a + b != 2) {
            System.out.println("num: 500\n" + "msg: The words do not match any term.\n" + "Try adding them to the documents first. " + "\nNB : Not Use The Same Word");
            return null;
        }

        Set<Integer> word1Indexes = invertedIndex.get(word1);
        Set<Integer> word2Indexes = invertedIndex.get(word2);
        Set<Integer> intersections = new HashSet<>(word1Indexes);
        Set<Integer> unions = new HashSet<>(word2Indexes);

        if (word1Indexes == null || word2Indexes == null) {
            System.out.println("num: 500\n" + "msg: The words do not match any term.\n" + "Try adding them to the documents first.\nNB: Do not use the same word for both inputs.");
            return null;
        }

        if (!(keys.equalsIgnoreCase("AND") || keys.equalsIgnoreCase("OR") || keys.equalsIgnoreCase("XOR") || keys.equalsIgnoreCase("AND NOT"))) {
            System.out.println("num: 500\n" + "msg: The keys do not match any valid operation.\n" + "Try adding them to the code first.");
            return null;
        }

        if (keys.equalsIgnoreCase("AND")) {
            Set<Integer> intersection = new HashSet<>(word1Indexes);
            intersection.retainAll(word2Indexes);
            invertedResult.put("AND", intersection);
        } else if (keys.equalsIgnoreCase("AND NOT")) {
            unions.addAll(word2Indexes);
            intersections.retainAll(unions);
            Set<Integer> andNotResult = new HashSet<>(word1Indexes);
            andNotResult.removeAll(intersections);
            invertedResult.put("AND NOT", andNotResult);
        } else if (keys.equalsIgnoreCase("OR")) {
            Set<Integer> union = new HashSet<>(word1Indexes);
            union.addAll(word2Indexes);
            invertedResult.put("OR", union);
        } else if (keys.equalsIgnoreCase("XOR")) {
            Set<Integer> xor = new HashSet<>(word1Indexes);
            xor.addAll(word2Indexes);
            Set<Integer> intersection = new HashSet<>(word1Indexes);
            intersection.retainAll(word2Indexes);
            xor.removeAll(intersection);
            invertedResult.put("XOR", xor);
        }

        return invertedResult;
    }
    
    /////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    
    // search in index 
    public static void index_search(String Query){
        
        String indexDirectoryPath = "indexes/inverted index";

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