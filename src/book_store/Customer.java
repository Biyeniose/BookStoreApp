/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package book_store;

/**
 *
 * @author biyen
 */


// State Design Pattern used, Customer can have Status of GOLD or SILVER which are classes

public class Customer {
    String username, password; 
    int points=0;

    private Status status = new SilverStatus(); // has a silver status initially
    // from this status we can use methods depening on if status is Gold or Silver
    
    public Customer( String username, String password) { 
        this.username = username; 
        this.password = password; 
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
    public void setStatus(Status s){
        status = s;
    }
    
    public void switchStatus(){
        status.changeStatus(this); // use Status instance var to change the status
    }
    
    public String print_Status(){
        return status.getStatus(); // just uses get Status from the Status that is an instance var
    }
    
    // method that buys book and returns the totalcost
    public int buy_Book(int tc, boolean ap){
        // after the method is called the customer has bought the book and thier points have
        // been changed accordingly
        int price = status.Redeem_BuyBook(this,tc, ap);
        return price; // returns total cost
    }
    
}
