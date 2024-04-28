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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author zead shalaby
 */
public class ErrorController implements Initializable {

     private Stage primaryStage;
     private Scene scane;
     private Parent root;
     
     
    @FXML
    private Text error_textr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void error_close(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Fxml/IndexerDashFX.fxml"));
        
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scane = new Scene(root);
        primaryStage.setScene(scane);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    @FXML
    private void error_okay(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Fxml/IndexerDashFX.fxml"));
        
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scane = new Scene(root);
        primaryStage.setScene(scane);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.setResizable(false);
    }
    
}
