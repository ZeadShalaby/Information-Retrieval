/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author zead shalaby
 */


public class ResultSearch {
//    private static List<Object> listInList = new ArrayList<>();
    private static  int[][] termDocumentMatrixs ;
    private static Map<String, List<Integer>> invertedIndexs ;
    private static Map<String, Set<Integer>> Operatorinverted;
    private static String[] Bi_words,positions,documents,path_list,name_list,terms,operators;

    
//   public static void setSaveChoices(List<Object> resultSearch) {
//        List<Object> resultSearch1 = new ArrayList<>(resultSearch);
//   }   
//   // return result search 
//   public static List<Object> getChoices() {
//        return listInList;
//    }
    
    
    // Method of saved info //
    public static void setSaveChoices(String[] term, int[][] termDocumentMatrix, String[] namelist, String[] pathlist , String[] document) {
        terms = term;
        termDocumentMatrixs = termDocumentMatrix;
        name_list = namelist;
        path_list = pathlist;
        documents = document;
        
    }
    
     // Method of saved info //
    public static void setSaveChoicesinverted(String[] term, String[] namelist, String[] pathlist, Map<String, List<Integer>> invertedIndex , String[] document) {
        terms = term;
        name_list = namelist;
        path_list = pathlist;
        documents = document;
        invertedIndexs = invertedIndex;
        
    }
    
    // Method of saved info //
    public static void setSavebiWard(String[] biWard) {
        Bi_words = biWard;
    }
    
    // Method of saved biWard //
    public static void setSaveoperator(Map<String, Set<Integer>> invertedResult) {
      Operatorinverted   = invertedResult;
    }
    
     // Method of saved position //
    public static void setSavepostion(String[] position) {
        positions = position;
    }
    
    
   // return result operator 
    public static Map<String, Set<Integer>>  getoperator() {
        return Operatorinverted;
    }
    
    // return result term 
    public static String[] getterms() {
        return terms;
    }
    
    // return result NameList 
    public static String[] getNameList() {
        return name_list;
    }
    
    // return result athList 
    public static String[] getPathList() {
        return path_list;
    }
     // return result athList 
    public static String[] getdocument() {
        return documents;
    }
    
    // return result TermMatrix 
    public static int[][] getTermMatrix() {
        return termDocumentMatrixs;
    }
    
    // return result invertedIndex 
    public static Map<String, List<Integer>> getInvertedIndex() {
        return invertedIndexs;
    }
    
    // return result Bi-Word 
    public static String[] getBi_Word () {
        return Bi_words;
    }
    
    // return result postion index 
    public static String[] getpostion () {
        return positions;
    }

    

    
}
