/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package book_store;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author biyen
 */
public class LoginPageController implements Initializable {
    
    

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginTry;
    
    public ArrayList<Customer> activeList = new ArrayList<>();
    private Stage stage;
    private Scene scene;
    private Parent root;
    String user, pass, nextPage;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    

    @FXML
    private void clickLogin(ActionEvent event) throws IOException {
        user = username.getText();
        pass = password.getText();
        
        // checks for admin
        if((user.equals("admin")) && (pass.equals("admin"))){
            nextPage = "AdminStartScreen.fxml";
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nextPage));
            root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            String css = this.getClass().getResource("app.css").toExternalForm();
            scene = new Scene(root);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
            
            // check for a User
        } else if(checkUser(user, pass)){ // passes the typed in data and checks for user
            // if user is found then we fill the temp data with the data in the file
            
            try{
            // use created ArrayList to hold the current Customers in data
            activeList.removeAll(activeList); // empty list
            File text = new File("customers.txt");
            Scanner read = new Scanner(text);
            while(read.hasNextLine()){
                // split according to -
                String[] s = read.nextLine().split("-");
                String u = s[0];
                String pass_w = s[1];
                int p = Integer.parseInt(s[2]);
                
                //creates a new Customer before adding to data
                Customer cust = new Customer(u, pass_w);
                cust.setPoints(p);
                activeList.add(cust);
                // if 
            }
        } catch(IOException e){
            System.out.println("Error Occurd");
            e.printStackTrace();
        }
           
           
           Customer c = findCustomer(user, pass);
           nextPage = "CustomerStartScreen.fxml";
           
           FXMLLoader loader = new FXMLLoader(getClass().getResource(nextPage));
            root = loader.load();
            
            // sets the controller before swtiching to customer screen
            CustomerStartScreenController controller = loader.getController();
            controller.setCustomer(c); // sets constumer to the page

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            String css = this.getClass().getResource("app.css").toExternalForm();
            scene = new Scene(root);

            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        }
        
        else{
            loginTry.setText("Username and password do not exist");
        }
        
        
    }
    
    public boolean checkUser(String u, String p){ // boolean check to see if the names in arguement are equal to the one in file
        try{
            ArrayList<String[]> data = new ArrayList();
            // temp data for reference
            
            File f = new File("customers.txt");
            Scanner read = new Scanner(f);
            
            while(read.hasNextLine()){
                // goes through each line, splits the data into an array then adds to temp ArrayList data
                data.add(read.nextLine().split("-")); // splits each data 
                
            }
            
            // loops through the populated ArrayList temp data to see if the user existts
            for(String[] s: data){
                // s is a string that represetns each piece of data
                if ((s[0].equals(u)) && (s[1].equals(p))){
                    //check to see if it matches specific user
                    return true;
                }
            }
            read.close();
            return false;
            
        } catch (Exception e){
            System.out.println("Error occured");
            return false;
        }
    }
    
    public Customer findCustomer(String name, String pass){ // looks for customer and returns 
        // it reloads data from the file and reloads its temp data
        try{
            // use created ArrayList to hold the current Customers in data
            activeList.removeAll(activeList); // empty the list
            
            // reads current data from file
            File text = new File("customers.txt");
            Scanner read = new Scanner(text);
            while(read.hasNextLine()){
                String[] s = read.nextLine().split("-");
                // seperate data in file with "-"
                int p = Integer.parseInt(s[2]);
                
                Customer cust = new Customer(s[0], s[1]); //create Customer Object
                // before adding it into the list
                cust.setPoints(p);
                activeList.add(cust);// add to temp list
            }
        } catch(IOException e){
            System.out.println("Error Occurd");
            e.printStackTrace();
        }
        
        // loop for finding the Customer and retuns it
        for(Customer c : activeList){
            if((c.getUsername().equals(name)) && (c.getPassword().equals(pass))){
                return c;
            }
        }
        
        return null;
    }
    
    
    
    
    
}
