/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package book_store;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author biyen
 */
public class AdminBooksController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button backButton;
    @FXML
    private Button add;
    @FXML
    private TableView<Book> table;
    @FXML
    private TableColumn<Book, String> bookname;
    @FXML
    private TableColumn<Book, Integer> price;
    @FXML
    private Button delete;
    @FXML 
    private TextField newName;
    @FXML
    private TextField newPrice;
    
    // observable list for temp data
    private ObservableList<Book> data = FXCollections.observableArrayList();
    
    

    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { // populates the TableView as page is made
        // TODO
        bookname.setCellValueFactory(new PropertyValueFactory<>("BookName")); //corresponds to instance var
        price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        // lets multiple items to be selected at same time
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loadData(); // load data from books.txt to data
        table.setItems(data);
    }    
    
    // load the temp data with the contents of the file
    public void loadData(){
        try{
            // file and scanner to extract data from file then use that to create a Book Object
            // and then add to the Obseravble list of type Book
            File text = new File("books.txt");
            Scanner read = new Scanner(text);
            // loop to continue going to next line
            while(read.hasNextLine()){
                String[] s = read.nextLine().split("-");
                int price = Integer.parseInt(s[1]);
                
                Book b = new Book(s[0], price); // bookname,price
                data.add(b);
            }
        } catch(IOException e){
            System.out.println("Error Occured");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void clickDelete(ActionEvent event) throws IOException{
        // dummy data to differentiate from deleted and non deleted books
        ObservableList<Book> allBooks, selectData;
        
        allBooks = table.getItems(); // table get all the items
        selectData = table.getSelectionModel().getSelectedItems(); // gets all the selected data
        selectData.forEach(allBooks::remove); // remove selected data from allBooks data
        
        
        new FileWriter("books.txt", false).close(); // this time it overwites
        for(Book b : allBooks){
            // loop to add the new list of books after deleting
            writeToFile(b.getBookName() +"-"+b.getPrice(), true); // writes new books to the file and deletes the others
        }
    }
    
    @FXML
    private void clickAdd(ActionEvent event) throws IOException{
        String new_bookname = newName.getText();
        int p = Integer.parseInt(newPrice.getText());
        
        data.add(new Book(new_bookname, p)); //
        
        newPrice.setText("");
        newName.setText(""); // reset the textfields after adding
        writeToFile(new_bookname+"-"+p, true); // write into books.txt
        //true means append new data to exist , false or none means overwrite
    }
    
    public void writeToFile(String details, boolean append) throws IOException{ // adds data to the file line by line
        String file = "books.txt";
        FileWriter text = new FileWriter(file, append); //true means append new data to exist , false or none means overwrite
            text.write(details + "\n"); // goes to next line
            text.close();
    }

    @FXML
    private void clickBack(ActionEvent event) throws IOException {
        // change page and apply css stuff
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminStartScreen.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        String css = this.getClass().getResource("app.css").toExternalForm();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
        System.out.println("You switched Scenes ");
    }
    
}
