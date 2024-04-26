/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import setting.settings;
import sun.awt.DesktopBrowse;

/**
 * FXML Controller class
 *
 * @author zead shalaby
 */
public class IndexerDashFX implements Initializable {
    
    
     private Stage primaryStage;
     private Scene scane;
     private Parent root;

    @FXML
    private Button home;
    @FXML
    private Button search;
    @FXML
    private Button option;
    @FXML
    private Button mentors;
    @FXML
    private ImageView  upload_file , upload_success;
    @FXML
    private ComboBox index_type ,option_type;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fadeout(upload_success , 1);
        String [] items = {"term-documents incidence matric","inverted index ","Bi-ward index","positional index"};
        index_type.getItems().addAll(items);
        
    }    

    //// method of dashboard ////
    @FXML
    private void open_home(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/test/home.fxml"));
        
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scane = new Scene(root);
        primaryStage.setScene(scane);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    @FXML
    private void open_search(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/test/SearcherDashFX.fxml"));
        
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scane = new Scene(root);
        primaryStage.setScene(scane);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    @FXML
     void open_option(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/test/option.fxml"));
        
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scane = new Scene(root);
        primaryStage.setScene(scane);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.setResizable(false);
    }
    

    // action methot of button //
    @FXML
    private void open_mentors(ActionEvent event) {
    }
    
    // upload file to build indexer //
    @FXML
    private void upload_file(ActionEvent event) throws IOException{
       
        ExtensionFilter ex1 = new ExtensionFilter("Text Files","*.txt");
        ExtensionFilter ex2 = new ExtensionFilter("all Files","*.*");
        FileChooser filechooser = new FileChooser();
       
        filechooser.getExtensionFilters().addAll(ex1,ex2);
        filechooser.setTitle("Open My Files");
        filechooser.setInitialDirectory(new File("D:"));
        List<File> listfile =  filechooser.showOpenMultipleDialog(primaryStage) ;
       
        if(listfile != null){
            for(File selectedFile : listfile ){System.out.println("Open File : " + selectedFile.getPath());}
            fadeout(upload_file , 1400);
            fadein(upload_success);
            
            // open file you choise it //
            // Desktop desktop =  Desktop.getDesktop();
            // desktop.open(selectedFile);
        }
    }
   
    // todo build your indexer
    @FXML
    private void build_index(ActionEvent event) throws IOException{
        
    String[] savedChoices = settings.getChoices(); // Assuming the method is getChoices() not getChoice()
    
    // try type of indexer i use it to build indexer
    try {
        // Try to get the selected item from index_type
        String data = index_type.getSelectionModel().getSelectedItem().toString();

        // Check if you chose preprocessing or not
        for (String choice : savedChoices) {
            System.out.println(choice);
        }
    } catch (NullPointerException e) {
        // If index_type.getSelectionModel().getSelectedItem() returns null, show a message
        error(event);
    } catch (Exception e) {
        // If any other exception occurs, show a general error message
        System.out.println("An error occurred: " + e.getMessage());
    }

    // Check if savedChoices is null
    if (savedChoices == null) {
        // If savedChoices is null, call open_option(event)
        open_option(event);
    }

    
    }
    
   // Method to fade in the ImageView 
    private void fadein(ImageView image){
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1400), image);
        fadeInTransition.setFromValue(0);
        fadeInTransition.setToValue(1);
        fadeInTransition.play();
    }
    
    // Method to fade out the ImageView 
    private void fadeout(ImageView image , int time){
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(time), image);
        fadeInTransition.setFromValue(1);
        fadeInTransition.setToValue(0);
        fadeInTransition.play();
    }  
    
    // error page 
    private void error(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/test/ErrorPage.fxml"));
        
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scane = new Scene(root);
        primaryStage.setScene(scane);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.setResizable(false);
    }
    
}
   