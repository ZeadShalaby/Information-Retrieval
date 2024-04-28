/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import setting.settings;

/**
 * FXML Controller class
 *
 * @author zead shalaby
 */
public class OptionController implements Initializable {
    
    
     private Stage primaryStage;
     private Scene scane;
     private Parent root;

    @FXML
    private Button home;
    @FXML
    private Button index;
    @FXML
    private Button search;
    @FXML
    private CheckBox tokenization ,stopwords,lemmatization,steming,normalization;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    // this action method of dashboard //

    @FXML
    private void open_home(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Fxml/home.fxml"));
        
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scane = new Scene(root);
        primaryStage.setScene(scane);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    @FXML
    private void open_index(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Fxml/IndexerDashFX.fxml"));
        
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scane = new Scene(root);
        primaryStage.setScene(scane);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.setResizable(false);


    }

    @FXML
    private void open_search(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Fxml/SearcherDashFX.fxml"));
        
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scane = new Scene(root);
        primaryStage.setScene(scane);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.setResizable(false);

    }
    
    
    ///// this action of button  /////
    
  /*@FXML
    private void TokenizationProcess(ActionEvent event) throws IOException {
        tokenization.setSelected(!tokenization.isSelected());  
    }*/
    @FXML
    private void StopWordsProcess(ActionEvent event) throws IOException {
        stopwords.setSelected(!stopwords.isSelected());
    }
    @FXML
    private void NormalizationProcess(ActionEvent event) throws IOException {
        normalization.setSelected(!normalization.isSelected());
    }
    @FXML
    private void StemingProcess(ActionEvent event) throws IOException {
        steming.setSelected(!steming.isSelected());
    }
    @FXML
    private void LemmatizationProcess(ActionEvent event) throws IOException {
        lemmatization.setSelected(!lemmatization.isSelected());
    }
     
    @FXML
    private void Save_Settings(ActionEvent event) throws IOException {
        // Add event listeners to checkboxes
        handleCheckboxSelection(tokenization);
        handleCheckboxSelection(stopwords);
        handleCheckboxSelection(normalization);
        handleCheckboxSelection(steming);
        handleCheckboxSelection(lemmatization);
        
       open_index(event); // Call the open_index() method passing the event
    }
    
    @FXML
    private void handleCheckboxSelection(CheckBox checkBox) {
        if (checkBox.isSelected()) {
            settings.saveChoices(checkBox.getText());
            System.out.println("Selected Checkbox: " + checkBox.getText());
            // Perform your desired action here
        }
    }

}
