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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.lucene.queryparser.classic.ParseException;
import setting.settings;

/**
 * FXML Controller class
 *
 * @author zead shalaby
 */
public class SearcherDashFX implements Initializable {
    
    
     private Stage primaryStage;
     private Scene scane;
     private Parent root;

     

    @FXML
    private Button home;
    @FXML
    private Button searchs;
    @FXML
    private Button option;
    @FXML
    private Button mentors;
    @FXML 
    private Text error ,error_selected;
    
    @FXML
    private ComboBox index_types , search_type ;
    @FXML
    private TextField search_field;

    /**
     * Initializes the controller class.
     */
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        String [] items = {"term-documents","inverted index","Bi-ward index","positional index"};
        index_types.getItems().addAll(items);
        String [] item_search = {"1_word","2_word","3_word","other(& || ^)"};
        search_type.getItems().addAll(item_search);
    }
  

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
        // todo return image first for file //
        setting.settings.setSuccessImg(false);
        
        root = FXMLLoader.load(getClass().getResource("/Fxml/IndexerDashFX.fxml"));
        
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scane = new Scene(root);
        primaryStage.setScene(scane);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    @FXML
    private void open_option(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Fxml/option.fxml"));
        
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scane = new Scene(root);
        primaryStage.setScene(scane);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    @FXML
    private void open_mentors(ActionEvent event) {
    }
    
    
    
    @FXML
    private void searchProcess(ActionEvent event) throws IOException, ParseException {
    String[] savedChoices = settings.getChoices();
    String type_indx = "";
    String type_search = "";
    String Query = search_field.getText().toString();

    // Get selected items from ComboBoxes if they are not null
    if (index_types.getSelectionModel().getSelectedItem() != null) {
        type_indx = index_types.getSelectionModel().getSelectedItem().toString();
    }
    if (search_type.getSelectionModel().getSelectedItem() != null) {
        type_search = search_type.getSelectionModel().getSelectedItem().toString();
    }
    if(Query.length() <= 0){error.setText("Error this faild is required");}
    else{error.setVisible(false);}
    if(search_type.getSelectionModel().getSelectedItem() == null){error_selected.setText("Error index || Search Type Not Selected");}else if(index_types.getSelectionModel().getSelectedItem() != null){error_selected.setText("Error index || Search Type Not Selected");}
    else{error_selected.setVisible(false);
     search.searcher.search_query(type_indx,Query, savedChoices );
        System.out.println(savedChoices + "\n"+type_indx+ "\n"+Query+"\n"+type_search);

    }}

    
}
