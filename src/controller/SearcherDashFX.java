/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.lucene.queryparser.classic.ParseException;
import search.inverted;
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
    private Text error, error_selected;
    @FXML
    private Label first_words, second_words, result_doc;
    @FXML
    private ComboBox index_types, search_type;
    @FXML
    private TextField search_field, txf_fir, txf_sec;
    @FXML
    private HBox operator;
    @FXML
    private ToggleGroup groub;

    @FXML
    private RadioButton and, and_not, or, xor;

    @FXML
    private TableView<String> table;
    @FXML
    private TableColumn<String, String> name_col;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] items = {"term-documents", "inverted index", "Bi-ward index", "positional index"};
        index_types.getItems().addAll(items);
        String[] item_search = {"Term", "2_phaces", "phaces", "operator"};
        search_type.getItems().addAll(item_search);
        operator_Invisible();

        name_col.setCellValueFactory(cellData -> cellData.getValue().isEmpty() ? null : new SimpleStringProperty(cellData.getValue()));

        // Add data to the TableView
        for (int i = 0; i < 10; i++) {
            table.getItems().add("result Search in table view : .......()");
        }

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
    private static String operatorss = "";

    ///////////////////
    //selected Search//
    ///////////////////
    @FXML
    private void selected_search(ActionEvent event) {
        String select_search = search_type.getSelectionModel().getSelectedItem().toString();
        String[] items = {"term-documents", "inverted index", "positional index"};
        String[] invertedItem = {"inverted index"};
        String[] BI_POSItem = {"Bi-ward index", "positional index"};
        String[] POSItem = {"positional index"};

        if (select_search.equals("operator")) {
            index_types.getItems().clear();
            index_types.getItems().addAll(invertedItem);
            operatorss = "operator";
            

        } else if (select_search.equals("2_phaces")) {
            index_types.getItems().clear();
            index_types.getItems().addAll(BI_POSItem);
        } else if (select_search.equals("phaces")) {
            index_types.getItems().clear();
            index_types.getItems().addAll(POSItem);
        } else {
            index_types.getItems().clear();
            index_types.getItems().addAll(items);
        }
    }

    //////////////////
    //selected index//
    //////////////////
    @FXML
    private void selected_index(ActionEvent event) {
        String select_index = index_types.getSelectionModel().getSelectedItem().toString();
        String select_search = search_type.getSelectionModel().getSelectedItem().toString();
        if (select_index.equals("inverted index") && select_search.equals("operator")) {
            operator_visible();
            System.out.println("other is visable");
        } else {
            operator_Invisible();
        }
    }

    @FXML
    private void searchProcess(ActionEvent event) throws IOException, ParseException {
        String[] savedChoices = settings.getChoices();
        String type_indx = "";
        String type_search = "";
        String Query = search_field.getText().toString();
        String word1 = txf_fir.getText().toString();
        String word2 = txf_sec.getText().toString();
        String key = selected_radioButton();

        // handle some error
        if (savedChoices == null) {
            open_option(event);
        } else {
            if (search_type.getSelectionModel().getSelectedItem() != null) {
                type_search = search_type.getSelectionModel().getSelectedItem().toString();
            } else {
                error_selected.setText("Error Search Type Not Selected");
                return; // Exit the method if search type is not selected
            }
            if (index_types.getSelectionModel().getSelectedItem() != null) {
                type_indx = index_types.getSelectionModel().getSelectedItem().toString();
            } else {
                error_selected.setText("Error index Type Not Selected");
                return; // Exit the method if index type is not selected
            }

            // Check if the operator UI component is visible
            if (!operator.isVisible()) {
                // Get selected items from ComboBoxes if they are not null
                if (Query.length() <= 0) {
                    error.setText("Error this field is required");
                    return; // Exit the method if search query is empty
                } else {
                    error.setVisible(false);
                }
                // Perform search based on query
                error_selected.setVisible(false);
                String query = setting.setting_searcch.pre(Query, savedChoices);
                search.searcher.search_query(type_indx, query, savedChoices);
            } else {
                // Operator UI component is visible
                if (word1.length() <= 0 || word2.length() <= 0) {
                    if (word1.length() <= 0) {
                        error.setText("Error this First Word is required");
                    } else if (word2.length() <= 0) {
                        error.setText("Error this Second Word is required");
                    } else {
                        error.setVisible(false);
                        error_selected.setVisible(false);
                    }
                    error_selected.setVisible(false);
                    return; // Exit the method if first or second word is missing
                }
                // Perform search based on operator and words
                String first_W = setting.setting_searcch.pre(word1, savedChoices);
                String second_W = setting.setting_searcch.pre(word2, savedChoices);
                System.out.println(first_W + " " + key + " " + second_W);
                search.searcher.search_operator(first_W, second_W, key, type_indx);
            }
        }
        /////////////////////////////
        ////////////////////////////
        ///////////////////////////
        //////////////////////////
        String[] terms;
        String[] documents;
        Map<String, List<Integer>> invertedIndex;

        if (type_indx.endsWith("inverted index") && operatorss != "operator") {
            invertedIndex = setting.ResultSearch.getInvertedIndex();
            // added in text
            displayInvertedIndex(invertedIndex);
            result_doc.setText(invertedString.toString());
        } else if (type_indx.equals("term-documents")) {

            int[][] matrix = setting.ResultSearch.getTermMatrix();

            terms = setting.ResultSearch.getterms();
            documents = setting.ResultSearch.getdocument();
            // added matrix in text 
            display_matrix(matrix, terms, documents);
            result_doc.setText(matrixString.toString());
            // clear table
            table.getItems().clear();
            String[] nameList = setting.ResultSearch.getNameList();
            String[] pathList = setting.ResultSearch.getPathList();

            //        
            name_col.setCellValueFactory(cellData -> cellData.getValue().isEmpty() ? null : new SimpleStringProperty(cellData.getValue()));
            // Add data to the TableView
            for (String name : nameList) {
                System.out.println(name);
                // table.getItems().add(name);
            }
            // Add data to the TableView
            for (String path : pathList) {
                table.getItems().add(path);
            }

        } else if (type_indx.endsWith("Bi-ward index")) {

            // clear table
            table.getItems().clear();
            System.out.println("the result of bi ward is ");
            String[] biWard = setting.ResultSearch.getBi_Word();
            name_col.setCellValueFactory(cellData -> cellData.getValue().isEmpty() ? null : new SimpleStringProperty(cellData.getValue()));
            // Add data to the TableView
            for (String name : biWard) {
                if (name != null) {
                    table.getItems().add(name);
                }
            }

        } else if (type_indx.endsWith("positional index")) {

            // clear table
            table.getItems().clear();
            String[] postions = setting.ResultSearch.getpostion();
//            for(String pos : postions){
//                if(pos != null){
//                System.out.println(pos);
//            }}
//            
            name_col.setCellValueFactory(cellData -> cellData.getValue().isEmpty() ? null : new SimpleStringProperty(cellData.getValue()));
            // Add data to the TableView
            for (String name : postions) {
                if (name != null) {
                    table.getItems().add(name);
                }
            }
        } else if ((type_indx.endsWith("inverted index")) &&( operatorss =="operator")) {
            // clear table
            table.getItems().clear();
            Map<String, Set<Integer>> operators = setting.ResultSearch.getoperator();
            String result_operator = operator_int(operators);
//            System.out.println(result_operator);
//
            // Set cell value factories for name column
            name_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

            // Add data to the TableView
                if (result_operator != null) {
                    table.getItems().add(result_operator);
                   // result_doc.setText(result_operator);
                }
            }

        }
        ////////////////////////////////
        /// visable ///
    public void operator_visible() {

        operator.setVisible(true);
        first_words.setVisible(true);
        second_words.setVisible(true);
        txf_fir.setVisible(true);
        txf_sec.setVisible(true);

        search_field.setVisible(false);

    }

    /// Invisable ///
    public void operator_Invisible() {

        search_field.setVisible(true);

        operator.setVisible(false);
        first_words.setVisible(false);
        second_words.setVisible(false);
        txf_fir.setVisible(false);
        txf_sec.setVisible(false);
    }

    // return radio button is selected  
    public String selected_radioButton() {
        String key;
        if (or.isSelected()) {
            key = "OR";
        } else if (xor.isSelected()) {
            key = "XOR";
        } else if (and.isSelected()) {
            key = "AND";
        } else if (and_not.isSelected()) {
            key = "AND NOT";
        } else {
            key = "OR";
        }

        return key;
    }

    private static StringBuilder matrixString = new StringBuilder();

    // Method to display term-document incidence matrix 0 1
    public static void display_matrix(int[][] matrix, String[] terms, String[] documents) {

        System.out.println("\n");

        // Display matrix
        for (int i = 0; i < terms.length; i++) {
            System.out.printf("\n%s\t", terms[i] + " : "); // Print the current term

            for (int j = 0; j < documents.length; j++) {
                // Print the corresponding value from the matrix
                System.out.printf("%d\t", matrix[i][j]);
                matrixString.append(matrix[i][j]).append(" ");

                // result_doc.setText(terms[i] + " : "+matrix[i][j]);
            }

            System.out.println(); // Move to the next line for the next term
        }
    }

    private static StringBuilder invertedString = new StringBuilder();

// Method to display inverted index
    public static void displayInvertedIndex(Map<String, List<Integer>> invertedIndex) {
        // Clear the StringBuilder before appending new content
        invertedString.setLength(0);

        for (Map.Entry<String, List<Integer>> entry : invertedIndex.entrySet()) {
            invertedString.append(entry.getKey()).append(": ");
            List<Integer> docList = entry.getValue();
            for (int i = 0; i < docList.size(); i++) {
                invertedString.append("\tDoc ").append(docList.get(i));
                if (i < docList.size() - 1) {
                    invertedString.append(", ");
                }
            }
            invertedString.append("\n");
        }
    }

    
    // return value in map operator .to string
    private String operator_int(Map<String, Set<Integer>> operators) {

        StringBuilder textBuilder = new StringBuilder();

        for (Map.Entry<String, Set<Integer>> entry : operators.entrySet()) {
            String key = entry.getKey();
            Set<Integer> values = entry.getValue();

            // Append the key to the StringBuilder
            textBuilder.append(key).append(": ");

            // Append the values to the StringBuilder
            for (Integer value : values) {
                textBuilder.append(value).append(", ");
            }

            // Remove the last comma and space
            if (!values.isEmpty()) {
                textBuilder.delete(textBuilder.length() - 2, textBuilder.length());
            }

            // Append a newline character
            textBuilder.append("\n");
        }

        String allText = textBuilder.toString();

         return allText;
    }
}
