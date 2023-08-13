/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package book_store;

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
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author biyen
 */
public class Customer_Cost_ScreenController implements Initializable {
    

    @FXML
    private Label totalCost;
    @FXML
    private Label points;
    @FXML
    private Label status;
    @FXML
    private Button logout;
    
    int total_cost =0;
    private Stage stage;
    private Scene scene;
    private Parent root;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        points.setText("");
    }    

    @FXML
    private void clickLogout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
            root = loader.load();

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            String css = this.getClass().getResource("app.css").toExternalForm();
            scene = new Scene(root);

            scene.getStylesheets().add(css);


            stage.setScene(scene);
            stage.show();
    }
    
    public void Buy_getTC(Customer c, int p, boolean ap) throws IOException{
        // similates buying the book and updates the details for the customer
        total_cost = c.buy_Book(p,ap); 
        String stat ="";
        if(c.getPoints() > 1000){
             stat ="Gold";
        } else{
            stat="Silver";
        }
        
        // set the new customer details to display on the page
        totalCost.setText("TC = "+total_cost);
        points.setText("Points = "+c.getPoints());
        status.setText("Your Status = " + stat);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminCustomers.fxml"));
        Parent root = loader.load();
        
        // update the Customer Admin page
        AdminCustomersController controller = loader.getController(); // use controller to get the fxml page/class
        controller.afterPurchase(c); // re writes the data to Customer tableview
        
    }
            
            
    
    
}
