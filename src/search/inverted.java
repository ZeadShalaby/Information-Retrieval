/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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

/**
 *
 * @author zead shalaby
 */
public class inverted {

    // Method to generate inverted index
    public static Map<String, List<Integer>> generateInvertedIndex(String[] documents, String[] terms) {
        Map<String, List<Integer>> invertedIndex = new HashMap<>();

        for (String term : terms) {
            List<Integer> docList = new ArrayList<>();
            for (int i = 0; i < documents.length; i++) {
                if (documents[i].contains(term)) {
                    docList.add(i + 1); // Adding 1 to convert from zero-based index to one-based index
                }
            }
            invertedIndex.put(term, docList);
        }

        return invertedIndex;
    }

    // Method to display inverted index
    public static void displayInvertedIndex(Map<String, List<Integer>> invertedIndex) {
        for (Map.Entry<String, List<Integer>> entry : invertedIndex.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            List<Integer> docList = entry.getValue();
            for (int i = 0; i < docList.size(); i++) {
                System.out.print("\tDoc " + docList.get(i));
                if (i < docList.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    private static String[] name_list; // Declare the arrays without initialization

    private static String[] path_list;

    // search in index 
    public static void index_search(String Query) {

        String indexDirectoryPath = "indexes/inverted index";

        try (Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));
                IndexReader reader = DirectoryReader.open(indexDirectory)) {
            QueryParser parser = new QueryParser(Version.LUCENE_41, "contents", new SimpleAnalyzer(Version.LUCENE_41));

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

                // Initialize the arrays with the size equal to the totalHits
                name_list = new String[hits.totalHits];
                path_list = new String[hits.totalHits];

                int i = 0; // Move the initialization outside of the loop

                for (ScoreDoc scoreDoc : hits.scoreDocs) {
                    Document doc = searcher.doc(scoreDoc.doc);
                    name_list[i] = doc.get("filename");
                    path_list[i] = doc.get("fullpath");
                    i++;
                    System.out.println(doc.get("filename"));
                    System.out.println(doc.get("fullpath"));

                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // main methoad
    public static void search_inverted(String type_indx, String Query) throws IOException {

        String dataDir = "targetData/inverted index"; // Index *.txt files from this directory
        File[] files = new File(dataDir).listFiles();

        List<String> dataset = setting.settings.readDataset(files);
        String[] documents = dataset.toArray(new String[dataset.size()]);

        // Calculate terms
        Set<String> termSet = Collections.singleton(Query);

        String[] terms = termSet.toArray(new String[termSet.size()]);
        Arrays.sort(terms);

        Map<String, List<Integer>> invertedIndex = generateInvertedIndex(documents, terms);

        // Display inverted index
        System.out.println("\n====================================================================================");
        System.out.println("                           Inverted Index Representation:");
        System.out.println("====================================================================================\n");

        //  method of set index
       setting.ResultSearch.setSaveChoicesinverted(terms,  name_list, path_list,invertedIndex, documents);

 
        
       
       displayInvertedIndex(invertedIndex);
        // search in index 
        index_search(Query);

    }
}
