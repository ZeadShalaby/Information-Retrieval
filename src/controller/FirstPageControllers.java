/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
/**
 * FXML Controller class
 *
 * @author zead shalaby
 */
public class FirstPageControllers implements Initializable {

     private Stage primaryStage;
     private Scene scane;
     private Parent root;
     
     @FXML
    private Button button_nose , button_nose1;

    private Timeline colorTimeline;

    
    /**
     * Initializes the controller class.
     */
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Create a Timeline to change the color hint every second
        colorTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), this::changeButtonColor)
        );
        colorTimeline.setCycleCount(Timeline.INDEFINITE);
        colorTimeline.play();
    } 

    @FXML
    private void Welcome_Panda_Search(ActionEvent event) throws IOException {
        
            root = FXMLLoader.load(getClass().getResource("/Fxml/home.fxml"));

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
            primaryStage.setResizable(false);

    }

     private void changeButtonColor(ActionEvent event) {
        // Generate a random color
Color randomColor = (Math.random() < 0.5) ? Color.rgb(108, 0, 208) : Color.BLACK;

        // Set the button's background color
         button_nose.setStyle("-fx-background-color: #" + randomColor.toString().substring(2) +
                    "; -fx-background-radius: 100px;");
         button_nose1.setStyle("-fx-background-color: #" + randomColor.toString().substring(2) +
                    "; -fx-background-radius: 100px;");
}


}
