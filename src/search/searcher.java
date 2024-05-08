/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.lucene.queryparser.classic.ParseException;

/**
 *
 * @author zead shalaby
 */
public class searcher {
       
  public static void search_query(String type_indx ,String Query,String[] option) throws IOException, ParseException{
           

           switch(type_indx){
               case "inverted index":
                   search.inverted.search_inverted (type_indx,Query);
                   break;
               case "term-documents":
                   search.Term_Doc.search_termIndex(type_indx,Query);
                   break;
               case "Bi-ward index":
                   search.Bi_ward.search_Bi_ward(type_indx,Query);
                   break;
               case "positional index":
                   search.positional.search_positional(type_indx,Query);
                     System.out.println("positional index");
                   break;   

           }
        
     }
  
  
   public static void search_operator(String word1 ,String word2,String key,String type_indx) throws IOException, ParseException{
           

           switch(type_indx){
              
               case "inverted index":
                   System.out.println("operator search");
                   search.operator.operator(word1 , word2 , key);
                   break;  

           }
        
     }

    private static void ward(String type_indx, String Query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
