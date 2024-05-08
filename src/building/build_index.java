/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package building;

/**
 *
 * @author zead shalaby
 */


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
//import static setting.settings.normalizeText;

public class build_index {
    
  private static IndexWriterConfig analyzerConfig;
  private static SimpleAnalyzer sa;
  private static IndexWriter writer;
  private static StopAnalyzer STOA;
  
  
  
  
  public static void buildIndex(List<File> listfiles, String typeIndex, String[] option) throws IOException {

        // return content after preprocessing 
        File[] new_listfiles = setting.settings.newListfile(listfiles , option , typeIndex);
        System.out.println(new_listfiles);
        // check directory of index
        setting.settings.CheckDirectory("indexes/" + typeIndex);

        // Create or open the directory for the index
        Directory indexDirectory = FSDirectory.open(new File("indexes/" + typeIndex));
        IndexWriter writer = null;
        
        // Configure the analyzer
        writer = new IndexWriter(indexDirectory, new IndexWriterConfig(Version.LUCENE_42, new SimpleAnalyzer(Version.LUCENE_42)));
        
        // Indexing each file
        for (File file : new_listfiles) {
            if (!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead()) {
                System.out.println("Indexing " + file.getCanonicalPath());

                // Create a Lucene document
                Document doc = new Document();
                doc.add(new Field("contents", new FileReader(file))); //Index file content              
                doc.add(new Field("filename", file.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED)); // Index file name
                doc.add(new Field("fullpath", file.getCanonicalPath(), Field.Store.YES, Field.Index.NOT_ANALYZED)); // Index file full path
                
                // Add document to Lucene index
                writer.addDocument(doc);
            }
        }

        // Print the number of documents indexed
        System.out.println("# of Docs indexed = " + writer.numDocs());
        System.out.println("Lucene Index Built Successfully.");
        
        // Close the writer
        writer.close();
    }
}
       