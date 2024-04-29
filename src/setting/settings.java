package setting;

import java.io.File;
import static java.util.Collections.list;
import java.util.List;
import static jdk.nashorn.internal.objects.NativeArray.push;

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




}
