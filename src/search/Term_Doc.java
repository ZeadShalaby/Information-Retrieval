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
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
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
public class Term_Doc {
    
    
    // Method to generate term-document incidence matrix
    public static int[][] generateTermDocumentMatrix(String[] documents, String[] terms ) {
        int[][] matrix = new int[terms.length][documents.length];

       // stringMatrix[i][j] = String.valueOf(intMatrix[i][j]);

        for (int i = 0; i < terms.length; i++) {
            for (int j = 0; j < documents.length; j++) {
                if (documents[j].contains(terms[i])) {
                    matrix[i][j] = 1;
                 //   stringMatrix[i][j] = Name;
                }
            }
        }
        return matrix;
    }

    // Method to display term-document incidence matrix 0 1
    public static void displayMatrix(int[][] matrix, String[] terms, String[] documents) {
        // Display header
        System.out.print("         ");
        for (String doc : documents) {
            System.out.printf( doc);
        }
        System.out.println("\n");

        // Display matrix
        for (int i = 0; i < terms.length; i++) {
            System.out.printf("\n%s\t", terms[i]+" : "); // Print the current term

            for (int j = 0; j < documents.length; j++) {
                // Print the corresponding value from the matrix
                System.out.printf("%d\t", matrix[i][j]);
            }

            System.out.println(); // Move to the next line for the next term
        }
    }
    
    
    public static void search_termIndex(String type_indx , String Query) throws IOException, ParseException {
             
        String indexDirectoryPath = "indexes/term-documents";
        String dataDir = "targetData/term-documents"; // Index *.txt files from this directory
        File[] files = new File(dataDir).listFiles();
      
        List<String> dataset = setting.settings.readDataset(files);
        String[] documents = dataset.toArray(new String[dataset.size()]);

        // Calculate terms
        Set<String> termSet = Collections.singleton(Query);

        String[] terms = termSet.toArray(new String[termSet.size()]);
        Arrays.sort(terms);
        
        // Generate term-document incidence matrix
        int[][] termDocumentMatrix = generateTermDocumentMatrix(documents, terms);
        displayMatrix(termDocumentMatrix, terms, documents);

        
        
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

