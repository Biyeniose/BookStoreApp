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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AdminCustomersController implements Initializable {
    @FXML
    private Button add;

    @FXML
    private Button backButton;
    
    @FXML
    private Button delete;

    @FXML
    private TextField newPassword;

    @FXML
    private TextField newUsername;

    @FXML
    private TableColumn<Customer, String> password;

    @FXML
    private TableColumn<Customer, Integer> points;
    
    @FXML
    private TableColumn<Customer, String> username;
    
    @FXML
    private TableView<Customer> table;

    
    ObservableList<Customer> data = FXCollections.observableArrayList();
    private Stage stage;
    private Scene scene;
    private Parent root;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // set the table with Observable list of type Customer
            username.setCellValueFactory(new PropertyValueFactory<>("Username"));
            password.setCellValueFactory(new PropertyValueFactory<>("Password"));
            points.setCellValueFactory(new PropertyValueFactory<>("Points"));
            
            createCustsFromFile(); // loads the data from file into temp data 
            table.setItems(data);
        
    }    

    @FXML
    public void addNew(ActionEvent event) throws IOException {
        // save the entered text into variables
        String name = newUsername.getText();
        String pass = newPassword.getText();
        
        data = table.getItems(); // put the typed in data into
        data.add(new Customer(name, pass)); // add new Customer object to temp data
        
        // after creating a new Customer and adding it to the Observable list we then
        // write that data to the file (true means append new data)
        
        writeToFile(name+"-"+pass+"-"+ 0, true);
                
        newUsername.setText(""); // resets both of the text fields
        newPassword.setText("");
    }
    
    @FXML
    public void deleteOne(ActionEvent event) throws IOException {
        // create temporary observable lists to differentiate between 
        ObservableList<Customer> selecRows, AllBooks;
        AllBooks = table.getItems();
        selecRows = table.getSelectionModel().getSelectedItems(); // gets selected items in the TableColumn
        selecRows.forEach(AllBooks::remove); // remove all the selected Customers from the list of all Customers
        
        new FileWriter("customers.txt", false).close();// 
        for(Customer c: AllBooks){
            String data = c.getUsername() + "-" + c.getPassword() + "-" + c.getPoints(); // create a row of data
            writeToFile(data, true);
            // true means it will append new data to the end
            // its adding on the ones selected
        }

    }
    
    @FXML
    private void clickBack(ActionEvent event) throws IOException {
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
    
    
    //method for writing into file
    public void writeToFile(String details, boolean append) throws IOException{
        // writes arguements into customers txt file
        String filename = "customers.txt";
        FileWriter text = new FileWriter(filename, append); //true means append new data to exist , false or none means overwrite
            text.write(details+"\n"); // goes to next line to make sure data isnt mixed up
            text.close();

    }
    
    // for updating the table after a Customer has purchased something in the Customer Screen
    public void afterPurchase(Customer c) throws IOException{
        // loops through our temp data
        for(int i=0; i<data.size(); i++){
            //for each iteration it finds the corresponding customer then it updates the points of that customer
            if(data.get(i).getUsername().equals(c.getUsername()) && 
                    data.get(i).getPassword().equals(c.getPassword())){
                data.get(i).setPoints(c.getPoints());
            }
        }
        
        // rewrites the data into the text file
        new FileWriter("customers.txt", false).close(); // oepns file writer to write and immediatelt closes it
        
        for(Customer b : data){
            // create the string 
            String stuff = b.getUsername() + "-" + b.getPassword() + "-" + b.getPoints(); // username,password,points
            writeToFile(stuff, true);
        }
        
    }
    
    // method to load data from file
    // maybe run when saved
    public void createCustsFromFile(){
        try{
         File text = new File ("customers.txt");
         Scanner myreader = new Scanner(text); 
         while(myreader.hasNextLine()){
             
             String[] s = myreader.nextLine().split("-"); // seperate by -
             String u = s[0]; 
             String p = s[1]; 
             int points = Integer.parseInt(s[2]); 
             
             // for ech line create a new customer and add to dummy data
             Customer c = new Customer (u, p); 
             c.setPoints(points);
             
             // adds it into the temp data that populates the table view
             data.add(c);
         }
         }catch(IOException e){
              System.out.println("An error occurred.");         
            e.printStackTrace();  
         }
    }
    
    
    
    
    
}
