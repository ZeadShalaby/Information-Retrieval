/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author zead shalaby
 */
public class HomeController implements Initializable {

     private Stage primaryStage;
     private Scene scane;
     private Parent root;
     
    @FXML
    private Button index;
    @FXML
    private Button choise;
    @FXML
    private Button search;
    @FXML
    private Button option;

    boolean flag = true;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            // TODO
            fadeOutButton(index,240,1,1);
            fadeOutButton(search,-210,1,1);
            fadeOutButton(option,4,200,1);
    } 
    
    
    @FXML
    private void choise(ActionEvent event) {
        
       visable();
        
    }

    @FXML
    private void open_index(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/test/IndexerDashFX.fxml"));
        
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
    private void open_option(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/test/option.fxml"));
        
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scane = new Scene(root);
        primaryStage.setScene(scane);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    // change visable of choise //
    private void visable(){
        if(flag != false){
            fadeInButton(index,-36,1);
            fadeInButton(search,50,1);
            fadeInButton(option,-2,2);
            flag = false;
            }
            else{
            fadeOutButton(index,240,1,1400);
            fadeOutButton(search,-210,1,1400);
            fadeOutButton(option,4,200,1400);
            flag = true;
            }
    }
    
    // Method to fade in the buttons
    private void fadeInButton(Button button , int x , int y) {
                // Create a TranslateTransition animation
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), button);
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1400), button);
        fadeInTransition.setFromValue(0);
        fadeInTransition.setToValue(1);
        transition.setToX(x);
        transition.setToY(y); 
        fadeInTransition.play();
        transition.play();
    }
    
    // Method to fade out the buttons
    private void fadeOutButton(Button button , int x , int y ,int time) {
        FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(time), button);
        fadeOutTransition.setFromValue(1);
        fadeOutTransition.setToValue(0);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), button);
        transition.setToX(x);
        transition.setToY(y);
        fadeOutTransition.play();
        transition.play();

    }
    
    
}
