package setting;

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
    
    public static void saveChoices(String chosenOptions) {
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




    
}
