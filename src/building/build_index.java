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
  
  
  
  
  public static void build_indx(List<File> listfiles ,String type_indx ,String[] option) throws IOException, ParseException{

  boolean analyzer = setting.settings.analyzer(option);
      System.out.println(analyzer);
      setting.settings.CheckDirectory("indexes/" + type_indx);
      setting.settings.copy_dataset(type_indx, listfiles);
      Directory indexDictory = FSDirectory.open(new File("indexes/" + type_indx));
      
      if(analyzer == false){sa = new SimpleAnalyzer(Version.LUCENE_42);analyzerConfig = new IndexWriterConfig(Version.LUCENE_42, sa);writer = new IndexWriter(indexDictory, analyzerConfig);

       }else{   STOA = new StopAnalyzer (Version.LUCENE_42);analyzerConfig = new IndexWriterConfig(Version.LUCENE_42, STOA); writer = new IndexWriter(indexDictory, analyzerConfig);}

           for (File f: listfiles) {   // for each file in the directory
         

            if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead() ) {
           System.out.println("Indexing " + f.getCanonicalPath());  
           
            // Read file content and normalize it
                    String content = setting.settings.readFileContent(f);
                    String pre_processing = setting.settings.pre(content , option);

           
           
              Document doc = new Document(); // 
                doc.add(new Field("contents", pre_processing, Field.Store.YES, Field.Index.ANALYZED));
                 //Index file name
                doc.add(new Field("filename", f.getName(),Field.Store.YES, Field.Index.NOT_ANALYZED));                
                 // Index file full path
                doc.add(new Field("fullpath", f.getCanonicalPath(),Field.Store.YES, Field.Index.NOT_ANALYZED)); 
                
                
                 
                writer.addDocument(doc); // Add document to Lucene index
            }
    }
        System.out.println("# of Docs indexed = " + writer.numDocs());
        System.out.println("Lucene Index Built Successfully.");
    writer.close();  // close the writer
}


}
       