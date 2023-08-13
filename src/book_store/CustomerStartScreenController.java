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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author biyen
 */
public class CustomerStartScreenController implements Initializable {
    
    
    

    @FXML
    private Label label;
    @FXML
    private TableView<Book> table;
    @FXML
    private TableColumn<Book, String> bookName;
    @FXML
    private TableColumn<Book, Integer> price;
    @FXML
    private TableColumn<Book, CheckBox> select;
    @FXML
    private Button logout;
    @FXML
    private Button redeem;
    @FXML
    private Button buy;
    
    ObservableList<Book> data = FXCollections.observableArrayList();
    Customer a = null;
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bookName.setCellValueFactory(new PropertyValueFactory<>("BookName"));
        price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        select.setCellValueFactory(new PropertyValueFactory<>("Select"));
        // load the dummy data from the file
        loadData(); 
        
        table.setItems(data);
        if(a != null){
            setCustomer(a);
        }
    }    
    
    
    public void loadData(){
        try{
            File text = new File("books.txt");
            Scanner read = new Scanner(text);
            while(read.hasNextLine()){
                String [] s = read.nextLine().split("-"); //splits data according to -
                
                int p = Integer.parseInt(s[1]);
                // create new book and set a new check box to it
                Book b = new Book(s[0], p);
                CheckBox check = new CheckBox();
                b.setSelect(check);
                
                data.add(b);
            }
        } catch(IOException e){
            System.out.println("Error occured");
            e.printStackTrace();
        }
    }
    
    public void writeToFile(String details, boolean append) throws IOException{
        String file = "books.txt";
        FileWriter text = new FileWriter(file, append); 
        // go to next line after writing data
            text.write(details + "\n");
            text.close();
    }

    @FXML
    private void logoutClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
            root = loader.load();

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            String css = this.getClass().getResource("app.css").toExternalForm();
            scene = new Scene(root);

            scene.getStylesheets().add(css);


            stage.setScene(scene);
            stage.show();
    }

    @FXML
    private void redeemClick(ActionEvent event) throws IOException {
        purchaseBook(true); // true for redeem
        
    }
    
    public void setCustomer(Customer c){
        a = c; // set the Customer in arguement to be the Customer of this current screen

        if (c.getPoints() >= 1000){
            c.switchStatus();
        }
        String msg = "Welcome "+a.getUsername()+", You have "+a.getPoints()+" points, and Status is: "+a.print_Status();
        label.setText(msg);
    }
    
    public void purchaseBook(boolean ap) throws IOException{
        ObservableList<Book> removeList = FXCollections.observableArrayList();
        // dummy data array
        int totalcost =0;
        for(Book b : data){
            if(b.getSelect().isSelected()){
                removeList.add(b);
            }
        }
        
        data.removeAll(removeList);
        new FileWriter("books.txt", false).close();
        for(Book b : data){
            String stuff="";
            stuff = stuff + b.getBookName() + "-" + b.getPrice();
            writeToFile(stuff, true);
        }

        // loop to calculate the total cost of all books selected
        for(Book b: removeList){
            totalcost += b.getPrice();
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer_Cost_Screen.fxml"));
        Parent root = loader.load();
        
        // call the customercostcontroller class to set the data
        Customer_Cost_ScreenController controller = loader.getController();
        
        // it sends the total cost after all calculations
        controller.Buy_getTC(a, totalcost, ap); // this is a function from State Design Pattern
        
        Stage window = (Stage)redeem.getScene().getWindow();
        window.setScene(new Scene(root));

    }

    @FXML
    private void buyClick(ActionEvent event) throws IOException {
        purchaseBook(false); // false for just buy
    }
    
    
    
    
    
}
