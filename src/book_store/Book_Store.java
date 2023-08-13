/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package book_store;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author biyen
 */
public class Book_Store extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        
        // stage.getScene().getWindow();
        String css = this.getClass().getResource("app.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            event.consume();
            logout(stage);
        });
    }
    
    private void logout(Stage stage) {
        // show laert message to confirm
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to Logout!");
        alert.setContentText("Are you sure you want to exit?");
        
        if(alert.showAndWait().get() == ButtonType.OK){
            // to get current stage that we are working with
                // no longer need to instantiate a stage
            //stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("Logged out");
            stage.close();
        }  
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
